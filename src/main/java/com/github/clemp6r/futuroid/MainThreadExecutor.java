package com.github.clemp6r.futuroid;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 * An executor for UI/main thread tasks.
 * Forwards commands to a handler on the main looper.
 */
class MainThreadExecutor implements Executor {

    /** Android application main looper */
    private final Looper mainLooper = Looper.getMainLooper();

    /** Android application main thread */
    private final Thread mainThread = mainLooper.getThread();

    /** Main thread handler */
    private final Handler mainThreadHandler = new Handler(mainLooper);

    @Override
    public void execute(Runnable command) {
        if (Thread.currentThread() == mainThread) {
            command.run();
        } else {
            mainThreadHandler.post(command);
        }
    }

    @Override
    public String toString() {
        return "[main thread executor]";
    }
}
