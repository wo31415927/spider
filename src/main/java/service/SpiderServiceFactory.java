package service;

/** cctv 2017/9/14 */
public class SpiderServiceFactory {
  public static SpiderService create(SpiderPlan spiderPlan) {
    switch (spiderPlan.getResType()) {
      case IMG:
        return new SpiderImgService(spiderPlan,spiderPlan.getCurClass());
      default:
        throw new IllegalArgumentException(spiderPlan.getResType().name());
    }
  }
}
