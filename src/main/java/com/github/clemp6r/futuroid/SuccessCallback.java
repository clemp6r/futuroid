package com.github.clemp6r.futuroid;

/**
 * A Future success callback.
 * See {@link Future#onSuccess(SuccessCallback)} or {@link Future#onUiSuccess(SuccessCallback)}.
 */
public interface SuccessCallback<T> {
    void onSuccess(T result);
}
