package sample.av.Hhh;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import pojo.IImgGroupInfo;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import utils.PatternUtils;

/** cctv 2017/8/23 */
@Getter
@Setter
@Slf4j
@TargetUrl("https://%s/htm/pic1/\\d+.htm")
@HelpUrl("https://%s/htm/piclist1/\\d+.htm")
public class HhhPicInfo implements IImgGroupInfo {
  @ExtractBy(value = "//div[@class='picContent']/text(1)")
  protected String title;

  @ExtractBy(
    value =
        "//div[@class='picContent']/img/regex('<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>',1)"
  )
  protected List<String> picList;

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
