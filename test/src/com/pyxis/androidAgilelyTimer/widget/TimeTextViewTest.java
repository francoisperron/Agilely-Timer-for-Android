package com.pyxis.androidAgilelyTimer.widget;

import android.test.AndroidTestCase;

public class TimeTextViewTest extends AndroidTestCase {

    private TimeTextView view;

    public void setUp(){
        view = new TimeTextView(getContext());
    }

    public void testSecondsAreDisplayedProperly(){
        view.displayTime(10);
        assertEquals("Time is formatted properly", "0:10", view.getText());
    }

    public void testMinutesAndSecondsAreDisplayedProperly(){
        view.displayTime(114);
        assertEquals("Time is formatted properly", "1:54", view.getText());
    }

    public void testTenMinutesIsDisplayedProperly(){
        view.displayTime(600);
        assertEquals("Time is formatted properly", "10:00", view.getText());
    }

    public void testNegativeTimeIsDisplayedProperly(){
        view.displayTime(-600);
        assertEquals("Time is formatted properly", "-10:00", view.getText());
    }

    public void testPositiveTimeIsDisplayedInGreen(){
        view.displayTime(10);
        assertEquals("Time is shown in green", TimeTextView.GREEN, view.getCurrentTextColor());
    }

    public void testNegativeTimeIsDisplayedInRed(){
        view.displayTime(-10);
        assertEquals("Time is shown in red", TimeTextView.RED, view.getCurrentTextColor());
    }

    public void testZeroIsDisplayedInRed(){
        view.displayTime(0);
        assertEquals("Time is shown in red", TimeTextView.RED, view.getCurrentTextColor());
    }
}
