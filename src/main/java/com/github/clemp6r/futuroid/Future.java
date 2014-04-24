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
     * Registers a success callback that will be run on the UI/main thread.
     * It will be called only if the asynchronous computation finishes without failure.
     */
    void addSuccessCallback(SuccessCallback<? super T> callback);

    /**
     * Registers a failure callback that will be run on the UI/main thread.
     * It will be called only if the asynchronous computation fails.
     */
    void addFailureCallback(FailureCallback callback);

    /**
     * Registers a callback that will be run on the UI/main thread.
     */
    void addCallback(FutureCallback<? super T> callback);

    /**
     * Registers a callback that will be run on the given executor.
     */
    void addCallback(FutureCallback<? super T> callback, Executor executor);

    /**
     * Returns a new Future whose result is the product of applying the given Function to this Future.
     * @param function a function to transform the result of this Future
     * @param <U> the new result type
     */
    <U> Future<U> map(Function<? super T, U> function);

    /**
     * Returns a new Future whose result is asynchronously derived from the result of this Future.
     * @param function a function to create a new Future from the result of this Future.
     * @param <U> the new result type
     */
    <U> Future<U> map(AsyncFunction<? super T, U> function);

    /**
     * Transform this Future into another Future that will return the given object after completion.
     * @param object the object to return if this Future succeed
     * @param <U> the new result type
     */
    <U> Future<U> willReturn(U object);
}
