package com.pyxis.androidAgilelyTimer;

public interface AgilelyTimerPreferences {

    public void resetPreferences();

    //Sandup
    public void setStandupPreferences(int lenghtOfMeeting, int numberOfMembers);
    public int getStandupLenghtOfMeeting();
    public int getStandupNumberOfMembers();

    //Timebox
    public void setTimeboxPreferences(int lenghtOfMeeting);
    public int getTimeboxMeetingLength();

}
