package com.github.clemp6r.futuroid;

/**
 * A function that returns a Future.
 * Typically used by {@link Future#map(AsyncFunction)}.
 */
public interface AsyncFunction<I, O> {
    Future<O> apply(I input) throws Exception;
}
