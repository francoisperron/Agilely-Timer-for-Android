package com.pyxis.androidAgilelyTimer;

/**
 *
 */
public class CountdownAlarm implements CountDownListener{

    private final int alarmTime;
    private final SoundPlayer soundPlayer;

    public CountdownAlarm(final int alarmTime, final SoundPlayer soundPlayer) {
        this.alarmTime = alarmTime;
        this.soundPlayer = soundPlayer;
    }

    public void update(int countdownInSeconds) {

        if(countdownInSeconds == alarmTime)
        {
            soundPlayer.play(1);
        }
    }
}
