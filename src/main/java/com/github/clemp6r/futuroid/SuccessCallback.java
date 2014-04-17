package com.github.clemp6r.futuroid;

/**
 * A Future success callback.
 * See {@link Future#addSuccessCallback(SuccessCallback)} or {@link Future#addSuccessCallback(SuccessCallback)}.
 */
public interface SuccessCallback<T> {
    void onSuccess(T result);
}
