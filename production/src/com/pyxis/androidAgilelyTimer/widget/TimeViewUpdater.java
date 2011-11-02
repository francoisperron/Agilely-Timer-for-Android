package com.pyxis.androidAgilelyTimer.widget;

import android.os.Handler;
import com.pyxis.androidAgilelyTimer.CountDownListener;

public class TimeViewUpdater implements CountDownListener {

    private TimeView view;
    private Handler handler;

    public TimeViewUpdater(final TimeView view, final int initialTimeInSeconds) {
        this.handler = new Handler();
        this.view = view;
        this.view.displayTime(initialTimeInSeconds);
    }

    public void update(final int timeInSeconds) {
        handler.post(new Runnable() {
            public void run() {
                view.displayTime(timeInSeconds);
            }
        });
    }
}
