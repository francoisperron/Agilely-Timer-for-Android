package com.pyxis.androidAgilelyTimer;

/**
 * Used to start and stop a countdown.
 */
public interface CountDown {
    
    void start(final int countdownInSeconds);

    void stop();
}
