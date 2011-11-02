package com.pyxis.androidAgilelyTimer.widget;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.widget.TextView;

public class TimeTextView extends TextView implements TimeView {

    protected static final int RED = 0xFFFF0000;
    protected static final int GREEN = 0XFF000000;
    private static final String MINUTES_SECONDS_FORMAT = "m:ss";

    public TimeTextView(Context context) {
        super(context);
    }

    public TimeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void displayTime(final int timeInSeconds) {
        final boolean isTimedout = timeInSeconds <= 0;
        setTextColor(isTimedout ? RED : GREEN);

        final CharSequence remainingTime = formatMinutesSeconds(timeInSeconds);
        setText(remainingTime);
    }

    private static CharSequence formatMinutesSeconds(final int timeInSeconds) {
        final CharSequence formatedTime = DateFormat.format(MINUTES_SECONDS_FORMAT, Math.abs(timeInSeconds * 1000));
        return timeInSeconds < 0 ? "-" + formatedTime : formatedTime;
    }
}
