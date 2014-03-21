package com.github.clemp6r.futuroid;

import com.google.common.base.Function;
import com.google.common.util.concurrent.FutureCallback;

public interface Future<T> extends java.util.concurrent.Future<T> {
    void addCallback(FutureCallback<T> callback);
    void addUiCallback(FutureCallback<T> callback);
    <U> Future<U> map(AsyncFunction<T, U> function);
    <U> Future<U> map(Function<T, U> function);
    <U> Future<U> willReturn(U object);
}
