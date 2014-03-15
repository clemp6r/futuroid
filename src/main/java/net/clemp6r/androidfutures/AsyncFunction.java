package net.clemp6r.androidfutures;

/**
 * Specialization of {@link com.google.common.util.concurrent.AsyncFunction} that returns {@link AndroidFuture}s.
 */
public interface AsyncFunction<I, O> extends com.google.common.util.concurrent.AsyncFunction<I, O> {
    @Override
    AndroidFuture<O> apply(I input) throws Exception;
}
