package com.github.clemp6r.futuroid;


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

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class AsyncTest {
    /**
     * Returns a simple Future to run on the network executor.
     * The Future will return the Thread in which it has been run.
     */
    private AndroidFuture<Thread> createNetworkTask() {
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
        AndroidFuture<Thread> future = createNetworkTask();

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
        AndroidFuture<Thread> future = createNetworkTask();

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

    @Test
    public void shouldMapFutures() throws ExecutionException, InterruptedException {
        AndroidFuture<String> futureA = Async.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "A";
            }
        });

        AndroidFuture<String> futureAB = futureA.map(new AsyncFunction<String, String>() {
            @Override
            public AndroidFuture<String> apply(final String input) throws Exception {
                return Async.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return input + "B";
                    }
                });
            }
        });

        assertEquals("AB", futureAB.get());
    }
}
