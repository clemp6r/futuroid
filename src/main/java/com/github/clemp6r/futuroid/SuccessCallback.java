package com.github.clemp6r.futuroid;

/**
 * A Future success callback.
 * See {@link Future#addSuccessCallback(SuccessCallback)} or {@link Future#addSuccessUiCallback(SuccessCallback)}.
 */
public interface SuccessCallback<T> {
    void onSuccess(T result);
}
