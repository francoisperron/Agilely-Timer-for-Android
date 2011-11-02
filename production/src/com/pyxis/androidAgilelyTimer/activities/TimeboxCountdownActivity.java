package com.pyxis.androidAgilelyTimer.activities;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import com.pyxis.androidAgilelyTimer.*;
import com.pyxis.androidAgilelyTimer.widget.*;


public class TimeboxCountdownActivity extends Activity {

    private TimeboxCountdown countdown = new TimeboxCountdown();

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureView();

        final int initialTimeInSeconds = getIntent().getExtras().getInt(getString(R.string.countdown_value));

        initializeSound();
        initializeTimeView(initialTimeInSeconds);
        initializeProgressView(initialTimeInSeconds);
        initializeEndButton();

        startCountdown(initialTimeInSeconds);
    }

    @Override
    protected void onStop() {
        super.onStop();
        countdown.stop();
        finish();   // to unstack the activity
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SoundManager.stopSound(1);
        SoundManager.cleanup();
    }

    private void configureView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setVolumeControlStream(AudioManager.STREAM_ALARM);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.timebox_countdown);
    }

    private void initializeSound() {
        SoundManager.getInstance();
        SoundManager.initSounds(this);
        SoundManager.loadSounds();
        countdown.addListener(new CountdownSnooze(30, new Alarm(1)));
    }

    private void initializeTimeView(final int initialTimeInSeconds) {
        final TimeView remainingTimeTextView = (TimeTextView) findViewById(R.id.remaining_time_textview);
        final TimeViewUpdater timeViewUpdater = new TimeViewUpdater(remainingTimeTextView, initialTimeInSeconds);
        countdown.addListener(timeViewUpdater);
    }

    private void initializeProgressView(final int initialTimeInSeconds) {
        ProgressBarWithText remainingTimeProgressBar = (ProgressBarWithText) findViewById(R.id.progress_bar);
        final ProgressViewUpdater progressViewUpdater = new ProgressViewUpdater(remainingTimeProgressBar, initialTimeInSeconds);
        countdown.addListener(progressViewUpdater);
    }

    private void initializeEndButton() {
        final Button endButton = (Button) findViewById(R.id.end_meeting_button);
        endButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                finish();
            }
        });
    }

    private void startCountdown(final int countdownInSeconds) {
        countdown.start(countdownInSeconds);
    }
}

