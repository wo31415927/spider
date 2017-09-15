package startup;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.nio.file.Paths;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import pojo.Dic;
import sample.av.Hhh.HhhVideoInfo;
import service.SpiderPlan;
import service.SpiderServiceFactory;

/** cctv 2017/9/14 */
public class SpiderMain {
  public static AtomicInteger threadIndex = new AtomicInteger(0);
  public static ThreadPoolExecutor executorDelegate =
      new ThreadPoolExecutor(
          0,
          1024,
          600L,
          TimeUnit.SECONDS,
          new SynchronousQueue<Runnable>(),
          new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
              return new Thread(r, "Spider-Exec-" + threadIndex.incrementAndGet());
            }
          });
  public static ListeningExecutorService executorService =
      MoreExecutors.listeningDecorator(executorDelegate);

  public static void main(String[] args) {
    /*SpiderPlan spiderPlan =
    new SpiderPlan(
            false,
        true,
        "https://www.uuu955.com/",
        "https://%s/htm/piclist1/",
        Paths.get("F:\\照片与视频\\spider\\hhh_new"),
        16,
        HhhPicInfo.class,
        Dic.SpiderResType.IMG);*/
    SpiderPlan spiderPlan =
        new SpiderPlan(
            false,
            false,
            "https://www.uuu955.com/",
            "https://%s/htm/downlist6/index.htm",
            Paths.get("F:\\照片与视频\\spider\\hhh_new"),
            16,
            HhhVideoInfo.class,
            Dic.SpiderResType.FILM);
    SpiderServiceFactory.create(spiderPlan).startAsync();
  }
}
