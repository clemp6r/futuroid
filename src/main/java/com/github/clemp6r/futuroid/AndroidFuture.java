package com.github.clemp6r.futuroid;

import com.google.common.base.Function;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.ListenableFuture;

public interface AndroidFuture<T> extends ListenableFuture<T> {
    void addCallback(FutureCallback<T> callback);
    void addUiCallback(FutureCallback<T> callback);
    <U> AndroidFuture<U> map(AsyncFunction<T, U> function);
    <U> AndroidFuture<U> map(Function<T, U> function);
    <U> AndroidFuture<U> willReturn(U object);
}
