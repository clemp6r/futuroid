package com.github.clemp6r.futuroid;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Utility class for submitting asynchronous tasks and creating Futures.
 * <br/>
 * <br/>Sample asynchronous method:
 *  <pre> {@code
 * public Future<User> findUser(long id) {
 *     return Async.submit(new Callable<User>() {
 *         public User call() {
 *             return dao.findUser(id);
 *         }
 *     });
 * }
 * }
 * </pre>
 * Example usage of such service in UI client code:
 * <pre> {@code
 * findUser(id).addUiCallback(new FutureCallback<User>() {
 *     public void onSuccess(User user) {
 *         displayUserInfo(user);
 *     }
 *
 *     public void onFailure(Throwable t) {
 *         showErrorDialog(t);
 *     }
 * });
 * } </pre>
 */
public class Async {

    /**
     * Default executor. Uses a fixed thread pool of 5 threads.
     */
    private static final ListeningExecutorService defaultExecutor = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(5));

    private Async() {
    }

    /**
     * Submits a value-returning task for execution and returns a Future representing the pending results of the task.
     * The task will be run using the default executor.
     * @param task the task to submit
     * @param <T> the result type
     * @return a {@link Future} representing pending completion of the task
     */
    public static <T> Future<T> submit(Callable<T> task) {
        return submit(task, defaultExecutor);
    }

    /**
     * Submits a value-returning task for execution and returns a Future representing the pending results of the task.
     * The task will be run on the provided executor.
     * @param task the task to submit
     * @param executor an {@link java.util.concurrent.ExecutorService} that will be used to run the task
     * @param <T> the result type
     * @return a {@link Future} representing pending completion of the task
     */
    public static <T> Future<T> submit(Callable<T> task, ExecutorService executor) {
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executor);
        ListenableFuture<T> submit = listeningExecutorService.submit(task);
        return FutureImpl.from(submit);
    }

    /**
     * Creates a Future which has its value set immediately upon construction.
     * @param result the Future result
     */
    public static <T> Future<T> immediate(T result) {
        return FutureImpl.from(Futures.immediateFuture(result));
    }

    /**
     * Creates a Future which has an exception set immediately upon construction.
     */
    public static <T> Future<T> immediateFail(Throwable t) {
        return FutureImpl.from(Futures.<T>immediateFailedFuture(t));
    }
}
