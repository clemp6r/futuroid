package com.github.clemp6r.futuroid;

/**
 * A Future failure callback.
 * See {@link Future#onFailure(FailureCallback)} or {@link Future#onUiFailure(FailureCallback)}.
 */
public interface FailureCallback {
    void onFailure(Throwable t);
}
