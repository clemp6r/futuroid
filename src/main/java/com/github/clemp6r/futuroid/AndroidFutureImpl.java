package com.github.clemp6r.futuroid;

import com.google.common.base.Function;
import com.google.common.util.concurrent.ForwardingListenableFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;

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
    public <U> AndroidFuture<U> map(final AsyncFunction<T, U> function) {
        ListenableFuture<T> listenableFuture = toGuavaFuture(this);
        return from(Futures.transform(listenableFuture, toGuavaAsyncFunction(function)));
    }

    private static <T, U> com.google.common.util.concurrent.AsyncFunction<T, U> toGuavaAsyncFunction(
            final AsyncFunction<T, U> function) {
        return new com.google.common.util.concurrent.AsyncFunction<T, U>() {
            @Override
            public ListenableFuture<U> apply(T input) throws Exception {
                return toGuavaFuture(function.apply(input));
            }
        };
    }

    private static <T> ListenableFuture<T> toGuavaFuture(AndroidFuture<T> androidFuture) {
        final SettableFuture<T> promise = SettableFuture.create();
        androidFuture.addCallback(new FutureCallback<T>() {
            @Override
            public void onSuccess(T result) {
                promise.set(result);
            }

            @Override
            public void onFailure(Throwable t) {
                promise.setException(t);
            }
        });

        return promise;
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
