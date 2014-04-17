package com.github.clemp6r.futuroid;

import com.google.common.base.Function;

import java.util.concurrent.Executor;

/**
 * A Future represents the result of an asynchronous computation.
 * It is returned by {@link com.github.clemp6r.futuroid.Async} static methods.
 * After a client gets a Future object, it can register listeners to be notified when the
 * asynchronous computation is finished.
 *
 * @param <T> the Future result type
 *
 * @see java.util.concurrent.Future
 */
public interface Future<T> extends java.util.concurrent.Future<T> {

    /**
     * Registers a callback that will be run on the given executor.
     */
    void addCallback(FutureCallback<T> callback, Executor executor);

    /**
     * Registers a callback that will be run on the UI/main thread.
     */
    void addUiCallback(FutureCallback<T> callback);

    /**
     * Registers a success callback that will be run on the UI/main thread.
     * It will be called only if the asynchronous computation finishes without failure.
     */
    void addSuccessUiCallback(SuccessCallback<T> callback);

    /**
     * Registers a failure callback that will be run on the UI/main thread.
     * It will be called only if the asynchronous computation fails.
     */
    void addFailureUiCallback(FailureCallback callback);

    /**
     * Returns a new Future whose result is the product of applying the given Function to this Future.
     * @param function a function to transform the result of this Future
     * @param <U> the new result type
     */
    <U> Future<U> map(Function<T, U> function);

    /**
     * Returns a new Future whose result is asynchronously derived from the result of this Future.
     * @param function a function to create a new Future from the result of this Future.
     * @param <U> the new result type
     */
    <U> Future<U> map(AsyncFunction<T, U> function);

    /**
     * Transform this Future into another Future that will return the given object after completion.
     * @param object the object to return if this Future succeed
     * @param <U> the new result type
     */
    <U> Future<U> willReturn(U object);
}
