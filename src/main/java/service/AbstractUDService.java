package service;

import com.google.common.util.concurrent.AbstractService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Service;

import java.util.concurrent.Executor;

import lombok.extern.slf4j.Slf4j;

/**
 * 适用于处理启动、外部终止、异常终止但不会自己结束的服务
 * cctv
 * 2017/5/23
 */
@Slf4j
public abstract class AbstractUDService extends AbstractService {
    protected abstract String getThreadName();

    @Override
    protected void doStop() {
        notifyStopped();
    }

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
                    addListener(new LogListener(AbstractUDService.this), MoreExecutors.directExecutor());
                    startUp();
                    //starting状态下，若发生异常触发stopAsync(),不会执行doStop
                    notifyStarted();
                } catch (Exception e) {
                    notifyFailed(e);
                } finally {
                    Thread.currentThread().setName(name);
                }
            }
        });
    }

    protected abstract Executor executor();

    protected abstract void startUp() throws Exception;

    protected abstract void shutDown();

    protected class CommonListener extends Listener {
        @Override
        public void starting() {
            super.starting();
        }

        @Override
        public void running() {
            super.running();
        }

        @Override
        public void stopping(State from) {
            super.stopping(from);
        }

        @Override
        public void terminated(State from) {
            shutDown();
        }

        @Override
        public void failed(State from, Throwable failure) {
            shutDown();
        }
    }

    protected class LogListener extends Listener {
        protected Service service;

        public LogListener(Service service) {
            this.service = service;
        }

        @Override
        public void starting() {
            //log.info(service.toString());
        }

        @Override
        public void running() {
            log.info(service.toString());
        }

        @Override
        public void stopping(State from) {
            //log.info(service + ",From:" + from);
        }

        @Override
        public void terminated(State from) {
            log.info(service + " is terminated from " + from);
        }

        @Override
        public void failed(State from, Throwable failure) {
            log.error(service + " is failed from " + from, failure);
        }
    }
}
