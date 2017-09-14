package service;

import com.google.common.util.concurrent.MoreExecutors;

/**
 * cctv
 * 2017/9/14
 */
public abstract class AbstractNormalService extends AbstractUDService {
    @Override
    protected void doStart() {
        executor().execute(new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                Thread.currentThread()
                        .setName(getThreadName());
                try {
                    addListener(new CommonListener(), MoreExecutors.directExecutor());
                    addListener(new LogListener(AbstractNormalService.this), MoreExecutors.directExecutor());
                    startUp();
                    //starting状态下，若发生异常触发stopAsync(),不会执行doStop
                    notifyStarted();
                    exec();
                    notifyStopped();
                } catch (Exception e) {
                    notifyFailed(e);
                } finally {
                    Thread.currentThread().setName(name);
                }
            }
        });
    }

    protected abstract void exec();
}
