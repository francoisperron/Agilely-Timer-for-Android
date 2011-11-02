package com.pyxis.androidAgilelyTimer.widget;

import android.os.Handler;
import com.pyxis.androidAgilelyTimer.CountDownListener;

public class ProgressViewUpdater implements CountDownListener {

    private ProgressView view;
    private Handler handler;

    public ProgressViewUpdater(final ProgressView view, final int initialTimeInSeconds) {
        this.handler = new Handler();
        this.view = view;
        this.view.setInitialValue(initialTimeInSeconds);
    }

    public void update(final int timeInSeconds) {
        handler.post(new Runnable() {
            public void run() {
                view.updateProgress(timeInSeconds);
            }
        });
    }
}
