package com.pyxis.androidAgilelyTimer;


import android.content.Context;
import android.content.SharedPreferences;

public class AgilelyTimerPreferencesAccessor implements  AgilelyTimerPreferences{

    public final static int DEFAULT_VALUE = -1;
    public final static String PREFS_NAME = "AGILELY_TIMER_TAG";
    final static String STANDUP_MEETING_LENGTH_TAG = "STANDUP_MEETING_LENGTH_TAG";
    final static String STANDUP_MEMBERS_COUNT_TAG = "STANDUP_MEMBERS_COUNT_TAG";
    final static String TIMEBOX_MEETING_LENGTH_TAG = "TIMEBOX_MEETING_LENGTH_TAG";


    private SharedPreferences systemPreferences;
    private int standupLengthOfMeeting;
    private int standupMembersCount;
    private int timeboxMeetinLength;

    public AgilelyTimerPreferencesAccessor(Context context) {
        standupLengthOfMeeting =  DEFAULT_VALUE;
        standupMembersCount = DEFAULT_VALUE;
        timeboxMeetinLength = DEFAULT_VALUE;

        systemPreferences = context.getSharedPreferences(PREFS_NAME, context.MODE_WORLD_WRITEABLE);
    }

    public void setStandupPreferences(int lenghtOfMeeting, int numberOfMembers) {
        standupLengthOfMeeting = lenghtOfMeeting;
        standupMembersCount = numberOfMembers;

        SharedPreferences.Editor editor = systemPreferences.edit();

        editor.putInt(STANDUP_MEETING_LENGTH_TAG,standupLengthOfMeeting);
        editor.putInt(STANDUP_MEMBERS_COUNT_TAG,standupMembersCount);
        editor.commit();
    }

    public int getStandupLenghtOfMeeting() {

        standupLengthOfMeeting = systemPreferences.getInt( STANDUP_MEETING_LENGTH_TAG , DEFAULT_VALUE);
        return standupLengthOfMeeting;
    }

    public int getStandupNumberOfMembers() {

        standupMembersCount = systemPreferences.getInt( STANDUP_MEMBERS_COUNT_TAG , DEFAULT_VALUE);
        return standupMembersCount;
     }

    public void setTimeboxPreferences(int lenghtOfMeeting) {
        timeboxMeetinLength = lenghtOfMeeting;

        SharedPreferences.Editor editor = systemPreferences.edit();
        editor.putInt(TIMEBOX_MEETING_LENGTH_TAG,timeboxMeetinLength);
        editor.commit();
    }

    public int getTimeboxMeetingLength() {
        timeboxMeetinLength= systemPreferences.getInt(TIMEBOX_MEETING_LENGTH_TAG, DEFAULT_VALUE);
        return timeboxMeetinLength;
    }

    public void resetPreferences(){

        setStandupPreferences(DEFAULT_VALUE,DEFAULT_VALUE);
        setTimeboxPreferences(DEFAULT_VALUE);
    }
}
