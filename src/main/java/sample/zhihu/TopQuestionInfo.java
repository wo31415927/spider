package sample.zhihu;

import java.nio.file.Paths;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pojo.Dic;
import service.SpiderPlan;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

/** cctv 2017/8/23 */
@TargetUrl("https://www.zhihu.com/question/35597444")
@HelpUrl("https://www.zhihu.com/topic/\\d+," +
        "https://www.zhihu.com/topic/19553732/top-answers," +
        "https://www.zhihu.com/topic/19553732/top-answers?page=2" +
        "")
@Getter
@Setter
@ToString
public class TopQuestionInfo{
  //需要进一步过滤注释内容
  @ExtractBy("h1[@class='QuestionHeader-title']/html()")
  protected String title;
  //关注者数量
  @ExtractBy(value = "div[@class='QuestionFollowStatus-counts']/button/div[@class='NumberBoard-value']")
  protected int followerCnt;
  //浏览次数
  @ExtractBy(value = "div[@class='QuestionFollowStatus-counts']/div[@class='NumberBoard-value']")
  protected int brosweCnt;
  //问题数量
  protected int answerCnt;

  @ExtractBy(value = "//div[@class='tags']/a/text()")
  protected List<String> tags;

  public static final SpiderPlan spiderPlan =
          new SpiderPlan(
                  false,
                  false,
                  "https://www.zhihu.com/topics",
                  "https://www.zhihu.com/topics",
                  Paths.get("F:\\照片与视频\\spider\\zhihu"),
                  16,
                  TopQuestionInfo.class,
                  Dic.SpiderResType.INFO);



}
