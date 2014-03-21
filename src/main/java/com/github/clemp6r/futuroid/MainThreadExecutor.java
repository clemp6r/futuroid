package com.github.clemp6r.futuroid;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 * An executor for UI/main thread tasks.
 * Forwards commands to a handler on the main looper.
 */
class MainThreadExecutor implements Executor {

    /** Main thread handler */
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(Runnable command) {
        mainThreadHandler.post(command);
    }

    @Override
    public String toString() {
        return "[main thread executor]";
    }
}
