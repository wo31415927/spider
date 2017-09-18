package sample.av.Hhh;

import java.nio.file.Paths;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import pojo.Dic;
import pojo.IVideoInfo;
import service.SpiderPlan;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import utils.PatternUtils;

/** cctv 2017/8/23 */
@Getter
@Setter
@Slf4j
@TargetUrl("https://%s/htm/down6/\\d+.htm")
@HelpUrl("https://%s/htm/downlist6/\\d+.htm")
public class HhhVideoInfo implements IVideoInfo{
  @ExtractBy(value = "ul[@class='movieInfoList']/li[1]/html()")
  protected String title;

  @ExtractBy("ul[@id='downUL']/li[1]/p/html()")
  protected String url;

  public static final SpiderPlan spiderPlan =
          new SpiderPlan(
                  false,
                  true,
                  "https://www.uuu955.com/",
                  "https://%s/htm/downlist6/index.htm",
                  Paths.get("F:\\照片与视频\\spider\\hhh_new"),
                  4,
                  HhhVideoInfo.class,
                  Dic.SpiderResType.FILM);

  @Override
  public String toString() {
    return "HhhInfo{"
        + (null != title
            ? ("title='"
                //有的title不符合标准格式，直接返回title
                + PatternUtils.groupOnlyOne(title, ".+(\\[.+\\])")
                + '\'')
            : "Empty")
        +
        //            ", picList=" + picList +
        '}';
  }
}
