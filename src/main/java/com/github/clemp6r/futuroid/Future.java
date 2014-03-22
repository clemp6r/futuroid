package com.github.clemp6r.futuroid;

import com.google.common.base.Function;

public interface Future<T> extends java.util.concurrent.Future<T> {
    void addCallback(FutureCallback<T> callback);
    void addUiCallback(FutureCallback<T> callback);
    void onSuccess(SuccessCallback<T> callback);
    void onUiSuccess(SuccessCallback<T> callback);
    void onFailure(FailureCallback callback);
    void onUiFailure(FailureCallback callback);
    <U> Future<U> map(AsyncFunction<T, U> function);
    <U> Future<U> map(Function<T, U> function);
    <U> Future<U> willReturn(U object);
}
