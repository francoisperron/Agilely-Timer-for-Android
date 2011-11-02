package com.pyxis.androidAgilelyTimer;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TimeboxCountdown implements CountDown {

    private static final int ONE_SECOND = 1000;

    private Timer timer = new Timer("One second timer");
    private int countdownInSeconds;
    private final List<CountDownListener> listeners = new ArrayList<CountDownListener>();

    public void start(final int countdownInSeconds) {
        this.countdownInSeconds = countdownInSeconds;

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                tick();
            }
        }, ONE_SECOND, ONE_SECOND);
    }

    public void stop() {
        timer.cancel();
    }

    private void tick() {
        countdownInSeconds--;
        updateListeners(countdownInSeconds);
    }

    public void addListener(final CountDownListener listener) {
        listeners.add(listener);
    }

    private void updateListeners(int countdownInSeconds) {
        for (CountDownListener listener : listeners) {
            listener.update(countdownInSeconds);
        }
    }
}
