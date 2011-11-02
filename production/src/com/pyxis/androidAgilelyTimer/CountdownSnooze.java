package com.pyxis.androidAgilelyTimer;

/**
 *
 */
public class CountdownSnooze implements CountDownListener{

    private final int alarmTime;
    private final SoundPlayer soundPlayer;

    public CountdownSnooze(final int alarmTime, final SoundPlayer soundPlayer) {
        this.alarmTime = alarmTime;
        this.soundPlayer = soundPlayer;
    }

    public void update(int countdownInSeconds) {

        if ((countdownInSeconds <= alarmTime) && (countdownInSeconds % alarmTime == 0)){
            int coefNegative = countdownInSeconds <= 0 ? alarmTime : -alarmTime;
            countdownInSeconds= Math.abs(countdownInSeconds);
            countdownInSeconds += alarmTime + coefNegative;
            int nbTime = countdownInSeconds / alarmTime;
            soundPlayer.play(nbTime);
        }
    }

}