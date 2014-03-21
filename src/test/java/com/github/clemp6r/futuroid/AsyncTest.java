package com.github.clemp6r.futuroid;


import com.google.common.base.Function;
import com.google.common.util.concurrent.FutureCallback;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class AsyncTest {
    /**
     * Returns a simple Future to run on the network executor.
     * The Future will return the Thread in which it has been run.
     */
    private Future<Thread> createNetworkTask() {
        return Async.submit(new Callable<Thread>() {
            @Override
            public Thread call() throws Exception {
                return Thread.currentThread();
            }
        });
    }

    /**
     * Generic reference holder.
     * Used to store asynchronous results.
     */
    public static class Holder<T> {
        public T o;
    }

    @Test
    public void shouldRunNetworkTaskOnOtherThread() throws ExecutionException, InterruptedException {
        // check that the network task has been executed on another thread
        Thread taskThread = createNetworkTask().get();
        assertNotSame(Thread.currentThread(), taskThread);
    }

    @Test
    public void shouldExecuteUiCallbackOnUiThread() throws ExecutionException, InterruptedException {
        Future<Thread> future = createNetworkTask();

        final Holder result = new Holder();

        // wait until it is finished
        future.get();

        future.addUiCallback(new FutureCallback<Thread>() {
            @Override
            public void onSuccess(Thread object) {
                result.o = Thread.currentThread();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });

        // check that the callback has been executed on the main thread
        assertSame(Thread.currentThread(), result.o);
    }

    /**
     * Checks a network task callback is executed.
     */
    @Test
    public void shouldExecuteCallback() throws ExecutionException, InterruptedException {
        Future<Thread> future = createNetworkTask();

        final Holder<Boolean> result = new Holder<Boolean>();
        result.o = false;

        synchronized (result) {
            future.addCallback(new FutureCallback<Thread>() {
                @Override
                public void onSuccess(Thread object) {
                    result.o = true;

                    synchronized (result) {
                        result.notify();
                    }
                }
                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();

                    synchronized (result) {
                        result.notify();
                    }
                }
            });

            result.wait(2000);
        }

        // check that the callback has been executed
        assertTrue("The callback has not been executed", result.o);
    }

    private static <T> Future<T> createFuture(final T result) {
        return Async.submit(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return result;
            }
        });
    }

    @Test
    public void shouldMapFutures() throws ExecutionException, InterruptedException {
        Future<String> futureA = createFuture("A");
        Future<String> futureAB = futureA.map(new AsyncFunction<String, String>() {
            @Override
            public Future<String> apply(final String input) throws Exception {
                return createFuture(input + "B");
            }
        });

        String result = futureAB.get();
        assertEquals("AB", result);
    }

    @Test
    public void shouldMapFutureResult() throws ExecutionException, InterruptedException {
        Future<Integer> strLengthFuture = createFuture("ABC").map(new Function<String, Integer>() {
            @Override
            public Integer apply(String input) {
                return input.length();
            }
        });

        int strLength = strLengthFuture.get();
        assertEquals(3, strLength);
    }

    @Test
    public void shouldReturnImmediateResult() throws ExecutionException, InterruptedException {
        Future<String> future = Async.immediate("A");
        assertTrue(future.isDone());
        assertEquals("A", future.get());
    }

    @Test
    public void shouldReturnImmediateFailure() {
        Exception exception = new Exception("an exception");
        Future<Object> future = Async.immediateFail(exception);
        assertTrue(future.isDone());

        try {
            future.get();
            fail();
        } catch (InterruptedException e) {
            fail();
        } catch (ExecutionException e) {
            assertEquals(exception, e.getCause());
        }
    }

    @Test
    public void shouldReturnProvidedObject() throws ExecutionException, InterruptedException {
        Future<String> future = createFuture("A").willReturn("B");
        assertEquals("B", future.get());
    }
}
