package com.github.clemp6r.futuroid;

import com.google.common.util.concurrent.SettableFuture;

/**
 * A Promise is an object that wraps a Future and can be used to provide a result to the Future.
 * Promises are mainly used for converting callback-based APIs to future-based APIs.
 */
public class Promise<T> {
    private final SettableFuture<T> settableFuture = SettableFuture.create();
    private final Future<T> future = FutureImpl.from(settableFuture);

    public Future<T> getFuture() {
        return future;
    }

    public void done(T result) {
        settableFuture.set(result);
    }

    public void fail(Throwable throwable) {
        settableFuture.setException(throwable);
    }
}
