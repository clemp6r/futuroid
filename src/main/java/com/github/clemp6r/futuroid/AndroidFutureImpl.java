package com.github.clemp6r.futuroid;

import com.google.common.base.Function;
import com.google.common.util.concurrent.*;

import java.util.concurrent.Executor;

/**
* Custom Future type that allows adding callbacks to be executed on the UI thread.
*/
class AndroidFutureImpl<T> extends ForwardingListenableFuture<T> implements AndroidFuture<T> {

    private final ListenableFuture<T> delegate;

    /**
     * UI/main thread executor.
     */
    private static final Executor main = new MainThreadExecutor();

    private AndroidFutureImpl(ListenableFuture<T> delegate) {
        this.delegate = delegate;
    }

    static <T> AndroidFuture<T> from(ListenableFuture<T> listenableFuture) {
        return new AndroidFutureImpl<T>(listenableFuture);
    }

    @Override
    protected ListenableFuture<T> delegate() {
        return delegate;
    }

    @Override
    public void addUiCallback(FutureCallback<T> callback) {
        addCallback(callback, main);
    }

    @Override
    public void addCallback(FutureCallback<T> callback) {
        Futures.addCallback(delegate, callback);
    }

    private void addCallback(FutureCallback<T> callback, Executor executor) {
        Futures.addCallback(delegate, callback, executor);
    }

    @Override
    public <U> AndroidFuture<U> map(AsyncFunction<T, U> function) {
        return from(Futures.transform(this, function));
    }

    @Override
    public <U> AndroidFuture<U> map(Function<T, U> function) {
        return from(Futures.transform(this, function));
    }

    @Override
    public <U> AndroidFuture<U> willReturn(final U object) {
        return map(new Function<T, U>() {
            @Override
            public U apply(T input) {
                return object;
            }
        });
    }

}
