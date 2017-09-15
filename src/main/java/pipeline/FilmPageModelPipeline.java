package pipeline;

import com.google.common.base.Strings;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import pojo.IVideoInfo;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.HttpClientDownloader;

/** cctv 2017/8/25 */
@Slf4j
@Getter
@Setter
public class FilmPageModelPipeline extends AbstractPageModelPipeline<IVideoInfo> {
  protected AtomicInteger cnt = new AtomicInteger();
  protected AtomicLong binLen = new AtomicLong();
  protected HttpClientDownloader httpClientDownloader = new HttpClientDownloader();

  public FilmPageModelPipeline(Path wkPath) {
    this.wkPath = wkPath;
  }

  @Override
  public void process(IVideoInfo videoInfo, Task task) {
    //有的html无法打开
    if (Strings.isNullOrEmpty(videoInfo.getTitle().trim())) {
      log.warn("Found one empty IVideoInfo,This'll be omit!");
    } else {
      Path videoPath = wkPath.resolve(videoInfo.getTitle());
      cnt.incrementAndGet();
      try {
        Request request = new Request(videoInfo.getUrl());
        byte[] bytes = null;
        bytes = httpClientDownloader.download(request, task).getBytes();
        if (null != bytes) {
          Files.write(videoPath, bytes, StandardOpenOption.CREATE_NEW);
        }
        binLen.addAndGet(bytes.length);
      } catch (Exception e) {
        log.warn("下载Video[{}]异常,跳过", videoInfo.getTitle(), e);
      }
    }
  }

  @Slf4j
  public static class CalcTimer {
    private Timer calcTimer = new Timer("Timer-calc", true);
    private long cnt = 0;
    private double binLen = 0;
    protected final FilmPageModelPipeline pipeline;

    public CalcTimer(final FilmPageModelPipeline pipeline) {
      this.pipeline = pipeline;
      calcTimer.schedule(
          new TimerTask() {
            @Override
            public void run() {
              System.out.println("FilmTotal：" + cnt);
              System.out.println("DownloadTotal(m)：" + binLen);
              long curCnt = pipeline.getCnt().get();
              double curBinLen = pipeline.getBinLen().get() / 1000.0 / 1000.0;
              System.out.println("InstantaneousDownloadSpeed(m/s)：" + (curBinLen - binLen) / 5.0);
              binLen = curBinLen;
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
