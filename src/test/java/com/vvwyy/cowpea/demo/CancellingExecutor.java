package com.vvwyy.cowpea.demo;

import java.util.concurrent.*;

public class CancellingExecutor extends ThreadPoolExecutor {

    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        if (callable instanceof CancellableTask)
            return newCancelTaskFor((CancellableTask<T>) callable);
        else
            return super.newTaskFor(callable);
    }

    private <T> RunnableFuture<T> newCancelTaskFor(final CancellableTask<T> callable) {
        return new FutureTask<T>(callable) {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                try {
                    callable.cancel();
                } finally {
                    return super.cancel(mayInterruptIfRunning);
                }
            }
        };
    }
}
