package sample.zhihu;

import java.util.List;

import sample.OschinaBlog;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

/** cctv 2017/8/23 */
@TargetUrl("https://www.zhihu.com/question/\\d+")
//@HelpUrl("https://github.com/\\w+")
public class TopQuestion implements AfterExtractor {
  @ExtractBy("//title")
  private String title;

  @ExtractBy(value = "div.BlogContent", type = ExtractBy.Type.Css)
  private String content;

  @ExtractBy(value = "//div[@class='tags']/a/text()")
  private List<String> tags;

  @Override
  public void afterProcess(Page page) {}

  public static void main(String[] args) {
    OOSpider.create(Site.me(), new ConsolePageModelPipeline(), OschinaBlog.class)
        .addUrl("https://www.zhihu.com/question/64169134")
        .run();
  }
}
