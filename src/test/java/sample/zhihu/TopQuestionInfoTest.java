package sample.zhihu;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.XpathSelector;

/**
 * cctv
 * 2017/9/18
 */
@Slf4j
public class TopQuestionInfoTest {
  @Test
  public void name() throws Exception {
      Html html = new Html("<h1 class=\"QuestionHeader-title\"><!-- react-text: 91 -->有哪些十分阴暗、令人感到不适的游戏？<!-- /react-text --></h1>");
      XpathSelector selector1 = new XpathSelector("h1[@class='QuestionHeader-title']/regex(html(),-->([\\S]+)<!--,0)");
//      XpathSelector selector1 = new XpathSelector("h1/regex(class,t,0)");
      String str = selector1.select(html.getDocument());
      log.info(str);
      /*selector1 = new XpathSelector("ul[@class='movieInfoList']/li[1]/html()");
      str = selector1.select(html.getDocument());
      log.info(str);*/
  }
}