package com.pyxis.androidAgilelyTimer;

public class AvailableTimeCalculator implements CountDownListener{

    private final int membersCount;
    private int membersDone;
    private int countdownInSeconds;

    public AvailableTimeCalculator(final int countdownInSeconds, final int membersCount) {
        this.countdownInSeconds = countdownInSeconds;
        this.membersCount = membersCount;
    }

    public void update(final int countdownInSeconds) {
        this.countdownInSeconds = countdownInSeconds;
    }

    public int membersDone() {
        membersDone++;
        return nextMemberAvailableTime();
    }

    public int nextMemberAvailableTime() {
        if (membersDone < membersCount){
            return calculateMemberAvailableTime(countdownInSeconds, membersDone, membersCount);
        }
        else {
            return countdownInSeconds;
        }
    }

    private int calculateMemberAvailableTime(final int timeRemainingInSecs, final int currentMember, final int membersCount) {
        return timeRemainingInSecs /(membersCount - currentMember );
    }
}
