package pipeline;

import com.google.common.base.Strings;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import pojo.ImgGroupInfo;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import utils.IOUtils;
import utils.PatternUtils;

/** cctv 2017/8/25 */
@Slf4j
@Getter
@Setter
public class ImgGroupPageModelPipeline extends AbstractPageModelPipeline<ImgGroupInfo>{
  protected final Path wkPath;
  //从http url中抽取img的名称
  protected String imgPattern = ".+/([\\S]+\\.[a-zA-Z]+)$";
  protected AtomicInteger picCnt = new AtomicInteger();
  protected AtomicInteger picGroupCnt = new AtomicInteger();

  public ImgGroupPageModelPipeline(Path wkPath){
    this.wkPath = wkPath;
  }

  @Override
  public void process(ImgGroupInfo imgGroupInfo, Task task) {
    /*picGroupCnt.incrementAndGet();
    picCnt.addAndGet(imgGroupInfo.getPicList().size());
    try {
      TimeUnit.SECONDS.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }*/
    //有的html无法打开
    if (Strings.isNullOrEmpty(imgGroupInfo.getTitle())) {
      log.warn("Found one empty ImgGroupInfo,This'll be omit!");
    } else {
      //        System.out.println(cnt.get() + "-" + o.toString());
      picGroupCnt.incrementAndGet();
      Path titleDir = null;
      try {
        //图片组名称可能包含乱码
        titleDir = wkPath.resolve(imgGroupInfo.getTitle());
        IOUtils.mkdirD(titleDir);
        int cnt = 0;
        for (String imgHttpPath : imgGroupInfo.getPicList()) {
          Request request = new Request(imgHttpPath);
          byte[] imgInfo = null;
          try {
            imgInfo = spider.getDownloader().download(request, task).getBytes();
          } catch (Exception e) {
            log.warn("下载图片{}异常", imgHttpPath, e);
            continue;
          }
          cnt++;
          if (null != imgInfo) {
            String imgName = PatternUtils.groupOnlyOne(imgHttpPath, imgPattern);
            Path imgFilePath = titleDir.resolve(imgName);
            try {
              Files.write(imgFilePath, imgInfo, StandardOpenOption.CREATE_NEW);
            } catch (Exception e) {
              log.warn("保存图片{}异常", imgFilePath, e);
              continue;
            }
          }
        }
        picCnt.addAndGet(cnt);
      } catch (Exception e) {
        log.warn("创建图片组目录{}异常,跳过该图片组的处理", null == titleDir ? "null" : titleDir, e);
      }
    }
  }

  @Slf4j
  public static class CalcTimer {
    private Timer calcTimer = new Timer("Timer-calc", true);
    private long picCnt = 0;
    private long picGroupCnt = 0;
    protected final ImgGroupPageModelPipeline pipeline;

    public CalcTimer(final ImgGroupPageModelPipeline pipeline) {
      this.pipeline = pipeline;
      calcTimer.schedule(
          new TimerTask() {
            @Override
            public void run() {
              System.out.println("PicTotal：" + picCnt);
              System.out.println("picGroupTotal：" + picGroupCnt);
              long curPicCnt = pipeline.getPicCnt().get();
              long curPicGroupCnt = pipeline.getPicGroupCnt().get();
              System.out.println("InstantaneousPicSpeed(s)：" + (curPicCnt - picCnt) / 5.0);
              picCnt = curPicCnt;
              System.out.println(
                  "InstantaneousPicGroupSpeed(s)：" + (curPicGroupCnt - picGroupCnt) / 5.0);
              picGroupCnt = curPicGroupCnt;
              System.out.println("---");
            }
          },
          1000,
          5000);
    }

    public void cancel() {
      calcTimer.cancel();
    }
  }
}
