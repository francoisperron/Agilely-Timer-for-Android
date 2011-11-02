package com.pyxis.androidAgilelyTimer;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class StandupCountDown implements CountDown{

    private static final int ONE_SECOND = 1000;

    private Timer timer = new Timer("One second timer");
    private int meetingCountdownInSeconds;
    private int membersCountdownInSeconds;

    private final List<CountDownListener> meetingListeners = new ArrayList<CountDownListener>();
    private final List<CountDownListener> membersListeners = new ArrayList<CountDownListener>();

    public StandupCountDown(final int meetingCountdownInSeconds, final int membersCountdownInSeconds) {
        this.meetingCountdownInSeconds = meetingCountdownInSeconds;
        this.membersCountdownInSeconds = membersCountdownInSeconds;
    }

    public void start(final int countdownInSeconds) {

        timer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                tick();
            }
        }, ONE_SECOND, ONE_SECOND);
    }

    public void addMembersListener(final CountDownListener listener){
        membersListeners.add(listener);
    }

    public void addMeetingListener(final CountDownListener listener){
        meetingListeners.add(listener);
    }

    public void stop() {
        timer.cancel();
    }

    private void tick() {
        meetingCountdownInSeconds--;
        membersCountdownInSeconds--;
        updateListeners();
    }


    private void updateListeners() {
        for (CountDownListener listener : meetingListeners) {
            listener.update(meetingCountdownInSeconds);
        }


        for  (CountDownListener listener : membersListeners) {
            listener.update(membersCountdownInSeconds);
        }
    }

    public void memberDone(final int membersCountdownInSeconds) {
        this.membersCountdownInSeconds = membersCountdownInSeconds;
        updateListeners();
    }
}
