package com.github.clemp6r.futuroid;

/**
 * Specialization of {@link com.google.common.util.concurrent.AsyncFunction} that returns {@link AndroidFuture}s.
 */
public interface AsyncFunction<I, O> {
    AndroidFuture<O> apply(I input) throws Exception;
}
