package service;

import java.nio.file.Path;

import lombok.extern.slf4j.Slf4j;
import pipeline.AbstractPageModelPipeline;
import pipeline.ImgGroupPageModelPipeline;

/** cctv 2017/9/14 */
@Slf4j
public class SpiderImgService extends SpiderService {
  protected ImgGroupPageModelPipeline.CalcTimer calcTimer;

  public SpiderImgService(SpiderPlan spiderPlan,Class curClass) {
    super(spiderPlan,curClass);
  }

  @Override
  protected AbstractPageModelPipeline create(SpiderPlan spiderPlan) {
    //总目录下存放图片的目录
    final Path picPath = spiderPlan.getDesPath().resolve(RES_DIR_NAME);
    return new ImgGroupPageModelPipeline(picPath);
  }
  @Override
  protected void shutDown() {
    calcTimer.cancel();
    super.shutDown();
  }
  @Override
  protected void startUp() throws Exception {
    super.startUp();
    calcTimer =
        new ImgGroupPageModelPipeline.CalcTimer(
            ImgGroupPageModelPipeline.class.cast(pageModelPipeline));
  }

  @Override
  protected void exec() {
    long begin = System.currentTimeMillis();
    spider.run();
    ImgGroupPageModelPipeline imgGroupPageModelPipeline =
        ImgGroupPageModelPipeline.class.cast(pageModelPipeline);
    log.warn(
        "完成{}组图片共{}张的爬取,耗时{}秒",
        imgGroupPageModelPipeline.getPicGroupCnt().get(),
        imgGroupPageModelPipeline.getPicCnt().get(),
        (System.currentTimeMillis() - begin) / 1000.0);
  }
}
