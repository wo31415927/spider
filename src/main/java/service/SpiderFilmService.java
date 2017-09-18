package service;

import java.nio.file.Path;

import lombok.extern.slf4j.Slf4j;
import pipeline.AbstractPageModelPipeline;
import pipeline.FilmPageModelPipeline;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;

/** cctv 2017/9/14 */
@Slf4j
public class SpiderFilmService extends SpiderService {
  protected FilmPageModelPipeline.CalcTimer calcTimer;

  public SpiderFilmService(SpiderPlan spiderPlan, Class curClass) {
    super(spiderPlan,curClass);
  }

  @Override
  protected AbstractPageModelPipeline create(SpiderPlan spiderPlan) {
    //总目录下存放图片的目录
    final Path picPath = spiderPlan.getDesPath().resolve(RES_DIR_NAME);
    return new FilmPageModelPipeline(picPath);
  }
  @Override
  protected void shutDown() {
    if(null != calcTimer) {
      calcTimer.cancel();
    }
    super.shutDown();
  }

  @Override
  protected Downloader downloader() {
    System.setProperty("selenuim_config","/config.ini");
    return new SeleniumDownloader("D:\\chromedriver_win32\\chromedriver.exe");
  }

  @Override
  protected void startUp() throws Exception {
    super.startUp();
    /*calcTimer =
        new FilmPageModelPipeline.CalcTimer(
            FilmPageModelPipeline.class.cast(pageModelPipeline));*/
  }

  @Override
  protected void exec() {
    long begin = System.currentTimeMillis();
    spider.run();
    FilmPageModelPipeline FilmPageModelPipeline =
        FilmPageModelPipeline.class.cast(pageModelPipeline);
    log.warn(
        "完成{}个视频共{}M数据的爬取,耗时{}秒",
        FilmPageModelPipeline.getCnt().get(),
        FilmPageModelPipeline.getBinLen().get(),
        (System.currentTimeMillis() - begin) / 1000.0);
  }
}
