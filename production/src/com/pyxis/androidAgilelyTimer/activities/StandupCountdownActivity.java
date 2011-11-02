package com.pyxis.androidAgilelyTimer.activities;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.pyxis.androidAgilelyTimer.*;
import com.pyxis.androidAgilelyTimer.widget.MemberTextFieldUpdater;
import com.pyxis.androidAgilelyTimer.widget.TimeTextView;
import com.pyxis.androidAgilelyTimer.widget.TimeViewUpdater;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StandupCountdownActivity extends Activity {

    private static final String LOG_TAG = "StandupCountdownActivity";

    private static final int SHAKE_THRESHOLD = 2;

    private SensorManager sensorManager;
    private float accel; // acceleration apart from gravity
    private float accelCurrent; // current acceleration including gravity
    private float accelLast; // last acceleration including gravity

    private final SensorEventListener sensorListener = new SensorEventListener() {

        public void onSensorChanged(final SensorEvent se) {
            final float x = se.values[0];
            final float y = se.values[1];
            final float z = se.values[2];
            accelLast = accelCurrent;
            accelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            final float delta = accelCurrent - accelLast;
            accel = accel * 0.9f + delta; // perform low-cut filter
            if (accel > SHAKE_THRESHOLD) {
                memberDone();
            }
        }

        public void onAccuracyChanged(final Sensor sensor, final int accuracy) {
        }
    };

    private TextView membersTextView;

    private StandupCountDown countdownMeeting;

    private AvailableTimeCalculator availableTimeCalculator;
    private MemberTextFieldUpdater memberTextFieldUpdater;
    private int initialMemberTimeInSeconds;
    private int membersCount;
    private int currentMember;
    private Button imDoneButton;


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setVolumeControlStream(AudioManager.STREAM_ALARM);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.standup_countdown);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initShakeFeature();

        final int countdownInMinutes = getIntent().getExtras().getInt(getString(R.string.countdown_value));
        membersCount = getIntent().getExtras().getInt(getString(R.string.members_value));
        currentMember = 1;

        initializeSound();


        TimeTextView remainingTimeTextView = (TimeTextView) findViewById(R.id.total_remaining_time_textview);
        int initialTimeInSeconds = countdownInMinutes * 60;

        availableTimeCalculator = new AvailableTimeCalculator(initialTimeInSeconds, membersCount);

        initialMemberTimeInSeconds = availableTimeCalculator.nextMemberAvailableTime();
        countdownMeeting = new StandupCountDown(initialTimeInSeconds, initialMemberTimeInSeconds);

        TimeTextView remainingMemberTimeTextView = (TimeTextView) findViewById(R.id.remaining_time_textview);

        TimeViewUpdater meetingTimeDiplay = new TimeViewUpdater(remainingTimeTextView, initialTimeInSeconds);
        TimeViewUpdater membersTimeDiplay = new TimeViewUpdater(remainingMemberTimeTextView, initialMemberTimeInSeconds);

        final Alarm alarmMeeting = new Alarm(1);
        final Alarm alarmMembers = new Alarm(2);

        membersTextView = (TextView) findViewById(R.id.members_label);
        memberTextFieldUpdater = new MemberTextFieldUpdater(membersTextView, getString(R.string.countdown_members_caption), membersCount);

        countdownMeeting.addMeetingListener(new CountdownSnooze(30, alarmMeeting));
        countdownMeeting.addMeetingListener(meetingTimeDiplay);
        countdownMeeting.addMeetingListener(availableTimeCalculator);

        final String hints = getString(R.string.hints);
        final StringTokenizer tokenizer = new StringTokenizer(hints, "|");
        final List<String> hintList = new ArrayList<String>(10);
        while (tokenizer.hasMoreTokens()) {
            hintList.add(tokenizer.nextToken());
        }
        HintsPopUper popuper = new HintsPopUper(this, hintList);
        countdownMeeting.addMeetingListener(popuper);

        countdownMeeting.addMembersListener(new CountdownSnooze(30, alarmMembers));
        countdownMeeting.addMembersListener(membersTimeDiplay);

        Log.d(LOG_TAG, Integer.toString(countdownInMinutes));

        imDoneButton = (Button) findViewById(R.id.end_member_button);
        imDoneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                memberDone();
            }
        });

        final Button endButton = (Button) findViewById(R.id.end_meeting_button);
        endButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                finish();

            }
        });

        startCountdown(initialTimeInSeconds);
    }

    private void initShakeFeature() {
        /* do this in onCreate */
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(sensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        accel = 0.00f;
        accelCurrent = SensorManager.GRAVITY_EARTH;
        accelLast = SensorManager.GRAVITY_EARTH;
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void memberDone() {
        currentMember++;

        if (currentMember <= membersCount) {
            final int calculatedAvailableTimeInSeconds = availableTimeCalculator.membersDone();
            final int availableTimeInSeconds = initialMemberTimeInSeconds < calculatedAvailableTimeInSeconds ? initialMemberTimeInSeconds : calculatedAvailableTimeInSeconds;

            countdownMeeting.memberDone(availableTimeInSeconds);
            memberTextFieldUpdater.memberDone();
        } else if (currentMember > membersCount) {
            final int openDiscussionsTimeInSeconds = availableTimeCalculator.membersDone();
            countdownMeeting.memberDone(openDiscussionsTimeInSeconds);
            membersTextView.setText(getString(R.string.open_discussion));
            imDoneButton.setEnabled(false);
        } else {
            // Already in open discussions, can't do nothing...
        }
    }

    private void initializeSound() {
        SoundManager.getInstance();
        SoundManager.initSounds(this);
        SoundManager.loadSounds();
    }

    private void startCountdown(final int countdownInSeconds) {
        countdownMeeting.start(countdownInSeconds);
    }

    @Override
    protected void onPause() {
        super.onPause();
        countdownMeeting.stop();
    }

    @Override
    protected void onStop() {
        sensorManager.unregisterListener(sensorListener);
        countdownMeeting.stop();
        finish();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SoundManager.stopSound(1);
        SoundManager.cleanup();
    }

}