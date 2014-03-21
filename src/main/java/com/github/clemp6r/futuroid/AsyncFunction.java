package com.github.clemp6r.futuroid;

/**
 * Specialization of {@link com.google.common.util.concurrent.AsyncFunction} that returns {@link Future}s.
 */
public interface AsyncFunction<I, O> {
    Future<O> apply(I input) throws Exception;
}
