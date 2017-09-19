package sample.zhihu;

import com.google.common.base.Strings;

import org.eclipse.jetty.util.ConcurrentHashSet;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import pipeline.AbstractPageModelPipeline;
import pojo.Dic;
import service.SpiderPlan;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import utils.db.DbConnection;

import static utils.db.DbUtils.queryRunner;
import static utils.db.ResourceLoader.dbLoadingCache;

/**
 * cctv 2017/8/23
 */
@TargetUrl("https://www.zhihu.com/question/35597444")
@HelpUrl({"https://www.zhihu.com/topic/\\d+", "https://www.zhihu.com/topic/19553732/top-answers", "https://www.zhihu" +
        ".com/topic/19553732/top-answers?page=2"})
@Getter
@Setter
@ToString
public class TopQuestionInfo {
    public static final SpiderPlan spiderPlan =
            new SpiderPlan(
                    false,
                    false,
                    "https://www.zhihu.com/topics",
                    "https://www.zhihu.com/topics",
                    Paths.get("F:\\照片与视频\\spider\\zhihu"),
                    1,
                    TopQuestionInfo.class,
                    Dic.SpiderResType.INFO);
    //需要进一步过滤注释内容
    @ExtractBy("h1[@class='QuestionHeader-title']/regex(-->(.*),1)")
    protected String title;
    //关注者数量
    @ExtractBy(value = "div[@class='QuestionFollowStatus-counts']/button/div[@class='NumberBoard-value']/html()")
    protected int followerCnt;
    protected String url;
    //浏览次数
    @ExtractBy(value = "div[@class='QuestionFollowStatus-counts']/div[@class='NumberBoard-item']/div[@class='NumberBoard-value']/html()")
    protected int brosweCnt;
    //回答数量
    @ExtractBy(value = "h4[@class='List-headerText']/span/regex(-->(.*),1)")
    protected int answerCnt;
    @ExtractBy(value = "span[@class='Tag-content']/a/div/div/html()")
    protected List<String> tags;
    protected String sql = "insert into ";


    @Slf4j
    @RequiredArgsConstructor
    @Getter
    @Setter
    public class PageModelPipeline extends AbstractPageModelPipeline<TopQuestionInfo> {
        protected final DbConnection dbInfo;
        protected Set<TopQuestionInfo> infoSet = new ConcurrentHashSet<>();

        @Override
        public void process(TopQuestionInfo questionInfo, Task task) {
            //有的html无法打开
            if (!Strings.isNullOrEmpty(questionInfo.getTitle().trim())) {
                pageCnt.incrementAndGet();
                infoSet.add(questionInfo);
                try(Connection con = dbLoadingCache.getUnchecked(dbInfo).getConnection()){
                    queryRunner.update(con,)
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
