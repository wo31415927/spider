package sample;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

/** cctv 2017/8/23 */
@Slf4j
@TargetUrl("https://my.oschina.net/tinyframework/blog/\\d+")
//@HelpUrl("https://my.oschina.net/tinyframework/blog/\\wsort=time\\wp=\\w")
@HelpUrl("https://my.oschina.net/tinyframework/blog\\?sort=time&p=\\d+")
public class OschinaBlog implements AfterExtractor {

  @ExtractBy("//title")
  private String title;

  /*@ExtractBy(value = "div.BlogContent", type = ExtractBy.Type.Css)
  private String content;*/

  @ExtractBy(value = "//div[@class='tags']/a/text()")
  private List<String> tags;

  @Override
  public void afterProcess(Page page) {}

  public static void main(String[] args) {
    long begin = System.currentTimeMillis();
    final AtomicInteger cnt = new AtomicInteger();
    OOSpider.create(Site.me().setSleepTime(0).setRetryTimes(3).setRetrySleepTime(500), new ConsolePageModelPipeline(){
      @Override
      public void process(Object o, Task task) {
        super.process(o, task);
        cnt.incrementAndGet();
      }
    }, OschinaBlog.class)
        .addUrl("https://my.oschina.net/tinyframework/blog")
        .thread(16)
        .run();
    log.info("完成{}篇文章的爬取,耗时{}秒",cnt.get(),(System.currentTimeMillis() - begin) / 1000.0);
  }
}
