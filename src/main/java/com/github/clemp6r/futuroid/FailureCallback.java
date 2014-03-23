package com.github.clemp6r.futuroid;

/**
 * A Future failure callback.
 * See {@link Future#addFailureCallback(FailureCallback)} or {@link Future#addFailureUiCallback(FailureCallback)}.
 */
public interface FailureCallback {
    void onFailure(Throwable t);
}
