package com.patrick.inventorymanagementtask.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class JobRunner {

    private final static ExecutorService executorService = Executors.newFixedThreadPool(16);

    public static Future executeTask(Runnable threadedClassToRun) {

        Future future = executorService.submit(threadedClassToRun);

        return future;
    }

    public static <T> List<Future<T>> invokeAll(Collection<Callable<T>> tasks) throws InterruptedException {
        return executorService.invokeAll(tasks);
    }

    public static void executeTaskNow(Runnable threadedClassToRun) {
        executorService.execute(threadedClassToRun);
    }

    public static <T> void awaitAll(Collection<Callable<T>> solvers) throws InterruptedException {
        CompletionService<T> ecs = new ExecutorCompletionService<T>(executorService);
        int n = solvers.size();
        List<Future<T>> futures = new ArrayList<Future<T>>(n);
        T result = null;
        try {
            for (Callable<T> s : solvers)
                futures.add(ecs.submit(s));
            for (int i = 0; i < n; ++i) {
                try {
                    T r = ecs.take().get();
                    if (r != null) {
                        result = r;
                        break;
                    }
                } catch (ExecutionException ignore) {
                }
            }
        } finally {
            for (Future<T> f : futures)
                f.cancel(true);
        }
    }

    public static void stopJobs() {
        executorService.shutdown();
    }

}
