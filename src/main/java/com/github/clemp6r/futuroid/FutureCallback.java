package com.github.clemp6r.futuroid;

/**
 * A callback for accepting the results of a {@link com.github.clemp6r.futuroid.Future}
 * computation asynchronously.
 */
public interface FutureCallback<T> {
    /**
     * Invoked with the result of the {@code Future} computation when it is
     * successful.
     */
    void onSuccess(T result);

    /**
     * Invoked when a {@code Future} computation fails or is canceled.
     *
     * <p>If the future's {@link java.util.concurrent.Future#get() get} method throws an {@link
     * java.util.concurrent.ExecutionException}, then the cause is passed to this method. Any other
     * thrown object is passed unaltered.
     */
    void onFailure(Throwable t);
}
