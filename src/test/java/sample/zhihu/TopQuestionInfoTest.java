package sample.zhihu;

import org.junit.Test;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;
import service.SpiderServiceFactory;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.XpathSelector;

/** cctv 2017/9/18 */
@Slf4j
public class TopQuestionInfoTest {
  private String page =
      "<div class=\"QuestionHeader-content\"><div class=\"QuestionHeader-main\"><div class=\"QuestionHeader-tags\"><div " +
              "class=\"QuestionHeader-topics\"><div class=\"Tag QuestionTopic\" data-za-module=\"TopicItem\" data-za-module-info=\"{&quot;" +
              "card&quot;:{&quot;content&quot;:{&quot;type&quot;:&quot;Topic&quot;,&quot;token&quot;:&quot;19550875&quot;}}}\"><span " +
              "class=\"Tag-content\"><a class=\"TopicLink\" href=\"/topic/19550875\"><div class=\"Popover\"><div id=\"Popover-76521-21670-toggle\" " +
              "aria-haspopup=\"true\" aria-expanded=\"false\" aria-owns=\"Popover-76521-21670-content\">电脑游戏</div><!-- react-empty: 71 " +
              "--></div></a></span></div><div class=\"Tag QuestionTopic\" data-za-module=\"TopicItem\" data-za-module-info=\"{&quot;card&quot;" +
              ":{&quot;content&quot;:{&quot;type&quot;:&quot;Topic&quot;,&quot;token&quot;:&quot;19550994&quot;}}}\"><span " +
              "class=\"Tag-content\"><a class=\"TopicLink\" href=\"/topic/19550994\"><div class=\"Popover\"><div id=\"Popover-76521-77324-toggle\" " +
              "aria-haspopup=\"true\" aria-expanded=\"false\" aria-owns=\"Popover-76521-77324-content\">游戏</div><!-- react-empty: 77 " +
              "--></div></a></span></div><div class=\"Tag QuestionTopic\" data-za-module=\"TopicItem\" data-za-module-info=\"{&quot;card&quot;" +
              ":{&quot;content&quot;:{&quot;type&quot;:&quot;Topic&quot;,&quot;token&quot;:&quot;19552144&quot;}}}\"><span " +
              "class=\"Tag-content\"><a class=\"TopicLink\" href=\"/topic/19552144\"><div class=\"Popover\"><div id=\"Popover-76522-18726-toggle\" " +
              "aria-haspopup=\"true\" aria-expanded=\"false\" aria-owns=\"Popover-76522-18726-content\">家用主机游戏</div><!-- react-empty: 83 " +
              "--></div></a></span></div><div class=\"Tag QuestionTopic\" data-za-module=\"TopicItem\" data-za-module-info=\"{&quot;card&quot;" +
              ":{&quot;content&quot;:{&quot;type&quot;:&quot;Topic&quot;,&quot;token&quot;:&quot;19553732&quot;}}}\"><span " +
              "class=\"Tag-content\"><a class=\"TopicLink\" href=\"/topic/19553732\"><div class=\"Popover\"><div id=\"Popover-76522-83214-toggle\" " +
              "aria-haspopup=\"true\" aria-expanded=\"false\" aria-owns=\"Popover-76522-83214-content\">单机游戏</div><!-- react-empty: 89 " +
              "--></div></a></span></div></div></div><h1 class=\"QuestionHeader-title\"><!-- react-text: 91 -->有哪些十分阴暗、令人感到不适的游戏？<!-- /react-text " +
              "--></h1><div class=\"QuestionHeader-detail\"><div class=\"QuestionRichText QuestionRichText--expandable " +
              "QuestionRichText--collapsed\"><div><span class=\"RichText\" itemprop=\"text\">不限于恐怖类游戏，最好是近代的，不要远古时代的游戏。平台的话PC和家用机均可 ~ 对偶问题 " +
              "<a href=\"https://www.zhihu.com/question/39892538\" class=\"internal\">有哪些十分阳光、让人感到愉悦的游戏？ - 电脑游戏</a>…</span><button class=\"Button " +
              "QuestionRichText-more Button--plain\" type=\"button\"><!-- react-text: 97 -->显示全部<!-- /react-text --><svg viewBox=\"0 0 10 6\" " +
              "class=\"Icon QuestionRichText-more-icon Icon--arrow\" width=\"10\" height=\"16\" aria-hidden=\"true\" style=\"height: 16px; width: " +
              "10px;\"><title></title><g><path d=\"M8.716.217L5.002 4 1.285.218C.99-.072.514-.072.22.218c-.294.29-.294.76 0 1.052l4.25 " +
              "4.512c.292.29.77.29 1.063 0L9.78 1.27c.293-.29.293-.76 0-1.052-.295-.29-.77-.29-1.063 " +
              "0z\"></path></g></svg></button></div></div></div></div><div class=\"QuestionHeader-side\"><div " +
              "class=\"QuestionHeader-follow-status\"><div class=\"QuestionFollowStatus\"><div class=\"NumberBoard " +
              "QuestionFollowStatus-counts\"><button class=\"Button NumberBoard-item Button--plain\" type=\"button\"><div " +
              "class=\"NumberBoard-name\">关注者</div><div class=\"NumberBoard-value\">27269</div></button><div " +
              "class=\"NumberBoard-divider\"></div><div class=\"NumberBoard-item\"><div class=\"NumberBoard-name\">被浏览</div><div " +
              "class=\"NumberBoard-value\">2494323</div></div></div><!-- react-empty: 112 --></div></div></div></div><div " +
              "class=\"QuestionHeader-content\"><div class=\"QuestionHeader-main\"><div class=\"QuestionHeader-tags\"><div " +
              "class=\"QuestionHeader-topics\"><div class=\"Tag QuestionTopic\" data-za-module=\"TopicItem\" data-za-module-info=\"{&quot;" +
              "card&quot;:{&quot;content&quot;:{&quot;type&quot;:&quot;Topic&quot;,&quot;token&quot;:&quot;19550875&quot;}}}\"><span " +
              "class=\"Tag-content\"><a class=\"TopicLink\" href=\"/topic/19550875\"><div class=\"Popover\"><div id=\"Popover-76521-21670-toggle\" " +
              "aria-haspopup=\"true\" aria-expanded=\"false\" aria-owns=\"Popover-76521-21670-content\">电脑游戏</div><!-- react-empty: 71 " +
              "--></div></a></span></div><div class=\"Tag QuestionTopic\" data-za-module=\"TopicItem\" data-za-module-info=\"{&quot;card&quot;" +
              ":{&quot;content&quot;:{&quot;type&quot;:&quot;Topic&quot;,&quot;token&quot;:&quot;19550994&quot;}}}\"><span " +
              "class=\"Tag-content\"><a class=\"TopicLink\" href=\"/topic/19550994\"><div class=\"Popover\"><div id=\"Popover-76521-77324-toggle\" " +
              "aria-haspopup=\"true\" aria-expanded=\"false\" aria-owns=\"Popover-76521-77324-content\">游戏</div><!-- react-empty: 77 " +
              "--></div></a></span></div><div class=\"Tag QuestionTopic\" data-za-module=\"TopicItem\" data-za-module-info=\"{&quot;card&quot;" +
              ":{&quot;content&quot;:{&quot;type&quot;:&quot;Topic&quot;,&quot;token&quot;:&quot;19552144&quot;}}}\"><span " +
              "class=\"Tag-content\"><a class=\"TopicLink\" href=\"/topic/19552144\"><div class=\"Popover\"><div id=\"Popover-76522-18726-toggle\" " +
              "aria-haspopup=\"true\" aria-expanded=\"false\" aria-owns=\"Popover-76522-18726-content\">家用主机游戏</div><!-- react-empty: 83 " +
              "--></div></a></span></div><div class=\"Tag QuestionTopic\" data-za-module=\"TopicItem\" data-za-module-info=\"{&quot;card&quot;" +
              ":{&quot;content&quot;:{&quot;type&quot;:&quot;Topic&quot;,&quot;token&quot;:&quot;19553732&quot;}}}\"><span " +
              "class=\"Tag-content\"><a class=\"TopicLink\" href=\"/topic/19553732\"><div class=\"Popover\"><div id=\"Popover-76522-83214-toggle\" " +
              "aria-haspopup=\"true\" aria-expanded=\"false\" aria-owns=\"Popover-76522-83214-content\">单机游戏</div><!-- react-empty: 89 " +
              "--></div></a></span></div></div></div><h1 class=\"QuestionHeader-title\"><!-- react-text: 91 -->有哪些十分阴暗、令人感到不适的游戏？<!-- /react-text " +
              "--></h1><div class=\"QuestionHeader-detail\"><div class=\"QuestionRichText QuestionRichText--expandable " +
              "QuestionRichText--collapsed\"><div><span class=\"RichText\" itemprop=\"text\">不限于恐怖类游戏，最好是近代的，不要远古时代的游戏。平台的话PC和家用机均可 ~ 对偶问题 " +
              "<a href=\"https://www.zhihu.com/question/39892538\" class=\"internal\">有哪些十分阳光、让人感到愉悦的游戏？ - 电脑游戏</a>…</span><button class=\"Button " +
              "QuestionRichText-more Button--plain\" type=\"button\"><!-- react-text: 97 -->显示全部<!-- /react-text --><svg viewBox=\"0 0 10 6\" " +
              "class=\"Icon QuestionRichText-more-icon Icon--arrow\" width=\"10\" height=\"16\" aria-hidden=\"true\" style=\"height: 16px; width: " +
              "10px;\"><title></title><g><path d=\"M8.716.217L5.002 4 1.285.218C.99-.072.514-.072.22.218c-.294.29-.294.76 0 1.052l4.25 " +
              "4.512c.292.29.77.29 1.063 0L9.78 1.27c.293-.29.293-.76 0-1.052-.295-.29-.77-.29-1.063 " +
              "0z\"></path></g></svg></button></div></div></div></div><div class=\"QuestionHeader-side\"><div " +
              "class=\"QuestionHeader-follow-status\"><div class=\"QuestionFollowStatus\"><div class=\"NumberBoard " +
              "QuestionFollowStatus-counts\"><button class=\"Button NumberBoard-item Button--plain\" type=\"button\"><div " +
              "class=\"NumberBoard-name\">关注者</div><div class=\"NumberBoard-value\">27269</div></button><div " +
              "class=\"NumberBoard-divider\"></div><div class=\"NumberBoard-item\"><div class=\"NumberBoard-name\">被浏览</div><div " +
              "class=\"NumberBoard-value\">2494323</div></div></div><!-- react-empty: 112 --></div></div></div></div>\n" +
              "<div class=\"List-header\"><h4 class=\"List-headerText\"><span><!-- react-text: 176 -->1147<!-- /react-text --><!-- react-text: 177 " +
              "--> 个回答<!-- /react-text --></span></h4><div class=\"List-headerOptions\"><div class=\"Popover\"><button class=\"Button Select-button" +
              " Select-plainButton Button--plain\" role=\"combobox\" aria-expanded=\"false\" type=\"button\" id=\"Popover-76530-37609-toggle\" " +
              "aria-haspopup=\"true\" aria-owns=\"Popover-76530-37609-content\"><!-- react-text: 181 -->默认排序<!-- /react-text --><svg viewBox=\"0 0 " +
              "8 13\" class=\"Icon Select-arrow Icon--select\" width=\"8\" height=\"16\" aria-hidden=\"true\" style=\"height: 16px; width: 8px;" +
              "\"><title></title><g><path d=\"M4 11.183L1.284 8.218c-.293-.29-.77-.29-1.064 0-.293.29-.293.76 0 1.052l3.25 3.512c.292.29.768.29 " +
              "1.062 0L7.78 9.27c.293-.29.293-.76 0-1.052-.295-.29-.77-.29-1.064 0L4 11.182zM4 1.818L1.284 4.782c-.293.29-.77.29-1.064 " +
              "0-.293-.29-.293-.76 0-1.052L3.47.218c.292-.29.768-.29 1.062 0L7.78 3.73c.293.29.293.76 0 1.052-.295.29-.77.29-1.064 0L4 " +
              "1.82z\"></path></g></svg></button><!-- react-empty: 185 --></div></div></div>";
  @Test
  public void testPage() throws Exception {
    Html html = new Html(page);
    XpathSelector selector1 =
        new XpathSelector("h1[@class='QuestionHeader-title']/regex(-->(.*),1)");
    log.info(selector1.select(html.getDocument()));
    /*selector1 =
            new XpathSelector("h1[@class='QuestionHeader-title']/html()");
    String str = selector1.select(html.getDocument());
    log.info(str);
    log.info(PatternUtils.groupOnlyOne(str,">(.*)"));*/
    selector1 =
            new XpathSelector("div[@class='QuestionFollowStatus-counts']/button/div[@class='NumberBoard-value']/html()");
    log.info(selector1.select(html.getDocument()));
    selector1 =
            new XpathSelector("div[@class='QuestionFollowStatus-counts']/div[@class='NumberBoard-item']/div[@class='NumberBoard-value']/html()");
    log.info(selector1.select(html.getDocument()));
    selector1 =
            new XpathSelector("h4[@class='List-headerText']/span/regex(-->(.*),1)");
    log.info(selector1.select(html.getDocument()));
    selector1 =
            new XpathSelector("span[@class='Tag-content']/a/div/div/html()");
    log.info(selector1.select(html.getDocument()));
  }

  @Test
  public void testRegex() throws Exception {
    Html html =
        new Html(
            "<h1 class=\"QuestionHeader-title\"><!-- react-text: 91 -->有哪些十分阴暗、令人感到不适的游戏？<!-- /react-text --></h1>");
    XpathSelector selector1 =
        new XpathSelector("h1[@class='QuestionHeader-title']/regex(html(),-->([\\S]+)<!--,0)");
    //      XpathSelector selector1 = new XpathSelector("h1/regex(class,t,0)");
    String str = selector1.select(html.getDocument());
    log.info(str);
    /*selector1 = new XpathSelector("ul[@class='movieInfoList']/li[1]/html()");
    str = selector1.select(html.getDocument());
    log.info(str);*/
  }

  @Test
  public void testHelpUrl() throws Exception {
    log.info(Arrays.deepToString(TopQuestionInfo.class.getAnnotation(HelpUrl.class).value()));
  }

  @Test
  public void testStart() throws Exception {
    SpiderServiceFactory.create(TopQuestionInfo.spiderPlan).startAsync();
  }
}
