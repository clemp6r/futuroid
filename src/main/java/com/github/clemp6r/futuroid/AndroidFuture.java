package com.github.clemp6r.futuroid;

import com.google.common.base.Function;
import com.google.common.util.concurrent.FutureCallback;

import java.util.concurrent.Future;

public interface AndroidFuture<T> extends Future<T> {
    void addCallback(FutureCallback<T> callback);
    void addUiCallback(FutureCallback<T> callback);
    <U> AndroidFuture<U> map(AsyncFunction<T, U> function);
    <U> AndroidFuture<U> map(Function<T, U> function);
    <U> AndroidFuture<U> willReturn(U object);
}
