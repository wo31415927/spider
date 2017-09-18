package startup;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import sample.av.Hhh.HhhVideoInfo;
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
    SpiderServiceFactory.create(HhhVideoInfo.spiderPlan).startAsync();
  }
}
