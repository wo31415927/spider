package service;

import java.nio.file.Path;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pojo.Dic;

import static service.SpiderService.RES_DIR_NAME;

/** cctv 2017/9/14 */
@Getter
@Setter
@ToString
public class SpiderPlan {
  protected Dic.SpiderResType resType;
  protected boolean needClearDes = true;
  //是否需要续传
  protected boolean needGoOn = false;
  protected String initUrl;
  protected String startUrl;
  protected Path desPath;
  protected Path resPath;
  protected int spiderThreadCnt = 1;
  protected Class curClass;

  public SpiderPlan(
      boolean needClearDes,
      boolean needGoOn,
      String initUrl,
      String startUrl,
      Path desPath,
      int spiderThreadCnt,
      Class curClass,
      Dic.SpiderResType resType) {
    this.needClearDes = needClearDes;
    this.needGoOn = needGoOn;
    this.initUrl = initUrl;
    this.startUrl = startUrl;
    this.desPath = desPath;
    this.resType = resType;
    this.spiderThreadCnt = spiderThreadCnt;
    this.curClass = curClass;
    if (null != desPath) {
      resPath = desPath.resolve(RES_DIR_NAME);
    }
  }

  public SpiderPlan(
      String initUrl,
      String startUrl,
      Path desPath,
      int spiderThreadCnt,
      Class curClass,
      Dic.SpiderResType resType) {
    this(true, false, initUrl, startUrl, desPath, spiderThreadCnt, curClass, resType);
  }

  public SpiderPlan(
          boolean needGoOn,
          String initUrl,
          String startUrl,
          Path desPath,
          int spiderThreadCnt,
          Class curClass,
          Dic.SpiderResType resType) {
    this(true, needGoOn, initUrl, startUrl, desPath, spiderThreadCnt, curClass, resType);
  }
}
