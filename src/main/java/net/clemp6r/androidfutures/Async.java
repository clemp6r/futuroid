package net.clemp6r.androidfutures;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Async {

    private static final ListeningExecutorService defaultExecutor = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(5));

    private Async() {
    }

    public static <T> AndroidFuture<T> submit(Callable<T> callable) {
        return submit(callable, defaultExecutor);
    }

    public static <T> AndroidFuture<T> submit(Callable<T> callable, ExecutorService executor) {
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executor);
        ListenableFuture<T> submit = listeningExecutorService.submit(callable);
        return AndroidFutureImpl.from(submit);
    }

    public static <T> AndroidFuture<T> immediate(T result) {
        return AndroidFutureImpl.from(Futures.immediateFuture(result));
    }

    public static <T> AndroidFuture<T> immediateFail(Throwable t) {
        return AndroidFutureImpl.from(Futures.<T>immediateFailedFuture(t));
    }
}
