package pipeline;

import java.nio.file.Path;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

/**
 * cctv 2017/9/14
 */
@Getter
@Setter
public abstract class AbstractPageModelPipeline<T> implements PageModelPipeline<T> {
    protected Spider spider;
    protected Path wkPath;
    protected AtomicInteger pageCnt = new AtomicInteger();

    @Slf4j
    public static class CalcTimer {
        protected final AbstractPageModelPipeline pipeline;
        protected Timer calcTimer = new Timer("Timer-calc", true);
        protected long pageCnt = 0;
        protected int delay = 1;
        protected int interval = 5;

        public CalcTimer(final AbstractPageModelPipeline pipeline) {
            this.pipeline = pipeline;
        }

        public CalcTimer(AbstractPageModelPipeline pipeline, int delay, int interval) {
            this.pipeline = pipeline;
            this.delay = delay;
            this.interval = interval;
        }

        public void start() {
            calcTimer.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            System.out.println("Total：" + pageCnt);
                            long curPageCnt = pipeline.getPageCnt().get();
                            System.out.println("InstantaneousPageSpeed(s)：" + (curPageCnt - pageCnt) / (double) interval);
                            pageCnt = curPageCnt;
                            System.out.println("---");
                        }
                    },
                    delay * 1000,
                    interval * 1000);
        }

        public void stop() {
            calcTimer.cancel();
        }
    }
}
