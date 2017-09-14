package sample.av;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

/** cctv 2017/8/23 */
@Slf4j
@TargetUrl("https://my.oschina.net/tinyframework/blog/\\d+")
@HelpUrl("https://my.oschina.net/tinyframework/blog\\?sort=time&p=\\d+")
public class Motlun implements AfterExtractor {
  @ExtractBy("//title")
  private String title;

  /*@ExtractBy(value = "div.BlogContent", type = ExtractBy.Type.Css)
  private String content;*/

  @ExtractBy(value = "//div[@class='tags']/a/text()")
  private List<String> tags;

  @Override
  public void afterProcess(Page page) {}


}
