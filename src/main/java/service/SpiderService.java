package service;

import com.google.common.base.Preconditions;
import com.google.gson.reflect.TypeToken;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.message.BufferedHeader;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pipeline.AbstractPageModelPipeline;
import pipeline.LinkedPageModelPipeline;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.scheduler.DuplicateRemovedScheduler;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.component.HashSetDuplicateRemover;
import utils.IOUtils;
import utils.JsonUtils;
import utils.PatternUtils;
import utils.Utils;

import static startup.SpiderMain.executorService;

/** cctv 2017/8/28 */
@Slf4j
@Getter
@RequiredArgsConstructor
public abstract class SpiderService extends AbstractNormalService {
  //url去重集合转换的json文件名称
  public static final String DUP_FILE_NAME = "dup.json";
  public static final String QUEUE_NAME = "queue.json";
  public static final String RES_DIR_NAME = "res";
  protected final SpiderPlan spiderPlan;
  protected Spider spider;
  protected final Class curClass;
  protected AbstractPageModelPipeline pageModelPipeline;

  protected abstract Downloader downloader();

  @Override
  protected Executor executor() {
    return executorService;
  }

  @Override
  protected String getThreadName() {
    return this.getClass().getSimpleName();
  }

  @Override
  protected void startUp() throws Exception {
    String host = initHost(new URL(spiderPlan.getInitUrl()));
    this.pageModelPipeline = create(spiderPlan);
    //需要先initHost再初始化Spider，因为初始化Spider时会用到HhhInfo.class的注解
    this.spider =
        OOSpider.create(
                Site.me().setSleepTime(0).setRetryTimes(3).setRetrySleepTime(500),
                new LinkedPageModelPipeline(pageModelPipeline),
                curClass)
            .thread(spiderPlan.getSpiderThreadCnt())
            .addUrl(String.format(spiderPlan.getStartUrl(), host));
    if (null != downloader()) {
      spider.setDownloader(downloader());
    }
    pageModelPipeline.setSpider(spider);
    if (spiderPlan.isNeedClearDes()) {
      IOUtils.mkdirD(spiderPlan.getDesPath());
    }
    if (!spiderPlan.getResPath().toFile().exists()) {
      Files.createDirectory(spiderPlan.getResPath());
    }
    initContinuous(spider, spiderPlan.getDesPath());
  }

  @Override
  protected void shutDown() {
    if (null != spider) {
      spider.stop();
    }
  }

  /**
   * 获取变动的host
   *
   * @param url
   * @return
   * @throws IOException
   * @throws NoSuchFieldException
   * @throws IllegalAccessException
   * @throws NoSuchMethodException
   */
  public String initHost(URL url)
      throws IOException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException {
    log.info("当前Url: " + url);

    HttpResponse httpResponse = IOUtils.http(url.toString());
    Header[] headers = httpResponse.getHeaders("Set-Cookie");
    String host = url.getHost();
    if (null != headers && headers.length > 0) {
      BufferedHeader bufferedHeader = (BufferedHeader) headers[0];
      log.info("Set-Cookie: " + bufferedHeader.getValue());
      HeaderElement[] elements = bufferedHeader.getElements();
      String domain = elements[1].getParameterByName("domain").getValue();
      String pattern = "\\.(.+\\.com)$";
      if (PatternUtils.find(domain, pattern)) {
        host = "www." + PatternUtils.groupOnlyOne(domain, pattern);
        log.info("获取新Host: " + host);
      }
    }

    final TargetUrl targetUrl = (TargetUrl) curClass.getAnnotation(TargetUrl.class);
    final HelpUrl helpUrl = (HelpUrl) curClass.getAnnotation(HelpUrl.class);
    Utils.changeAnnotationValue(
        targetUrl, "value", new String[] {String.format(targetUrl.value()[0], host)});
    Utils.changeAnnotationValue(
        helpUrl, "value", new String[] {String.format(helpUrl.value()[0], host)});
    log.info(((TargetUrl) curClass.getAnnotation(TargetUrl.class)).value()[0]);
    log.info(((HelpUrl) curClass.getAnnotation(HelpUrl.class)).value()[0]);
    return host;
  }

  /*
   *
   * 实现断点续传
   *
   */
  public void initContinuous(final Spider spider, final Path desPath) throws IOException {
    final Path queuePath = desPath.resolve(QUEUE_NAME);
    final Path dupFilePath = desPath.resolve(DUP_FILE_NAME);
    final Queue<Request> queue = ((QueueScheduler) spider.getScheduler()).getQueue();
    final Set<String> urls =
        ((HashSetDuplicateRemover)
                ((DuplicateRemovedScheduler) spider.getScheduler()).getDuplicateRemover())
            .getUrls();
    if (!spiderPlan.isNeedClearDes() && spiderPlan.isNeedGoOn()) {
      if (Files.exists(queuePath)) {
        try (Reader reader = Files.newBufferedReader(queuePath, Charset.defaultCharset())) {
          queue.addAll(
              (List<Request>)
                  JsonUtils.gson.fromJson(reader, new TypeToken<List<Request>>() {}.getType()));
          Preconditions.checkState(queue.size() > 1, "queue.json is empty!");
          queue.poll();
        }
        log.info("Load queue json finished!");
      }
      if (Files.exists(dupFilePath)) {
        try (Reader reader = Files.newBufferedReader(dupFilePath, Charset.defaultCharset())) {
          urls.addAll(JsonUtils.gson.fromJson(reader, Set.class));
        }
        log.info("Load dup json finished!");
      }
    }
    Runtime.getRuntime()
        .addShutdownHook(
            new Thread() {
              public void run() {
                spider.stop();
                System.out.println("ThreadAlive: " + spider.getThreadPool().getThreadAlive());
                while (spider.getThreadAlive() > 0) {
                  try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("等待所有的Spider-Exec线程结束！");
                  } catch (InterruptedException e) {
                    e.printStackTrace();
                  }
                }
                try {
                  Files.deleteIfExists(queuePath);
                  Files.write(
                      queuePath,
                      JsonUtils.gson.toJson(queue, Queue.class).getBytes(),
                      StandardOpenOption.CREATE_NEW);
                } catch (IOException e) {
                  System.out.println("Dump queue failed!");
                  e.printStackTrace();
                  return;
                }
                System.out.println("shutdown task1 completed.");

                try {
                  Files.deleteIfExists(dupFilePath);
                  Files.write(
                      dupFilePath,
                      JsonUtils.gson.toJson(urls, Set.class).getBytes(),
                      StandardOpenOption.CREATE_NEW);
                } catch (IOException e) {
                  System.out.println("Dump dupFile failed!");
                  e.printStackTrace();
                }
                System.out.println("shutdown task2 completed.");
              }
            });
  }

  protected abstract AbstractPageModelPipeline create(SpiderPlan spiderPlan);
}
