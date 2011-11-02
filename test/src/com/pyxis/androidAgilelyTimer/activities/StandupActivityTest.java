package com.pyxis.androidAgilelyTimer.activities;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.TextView;
import com.pyxis.androidAgilelyTimer.R;

/**
 *
 */
public class StandupActivityTest extends ActivityInstrumentationTestCase2<StandupCountdownActivity> {

    private StandupCountdownActivity activity;
    private String membersCaption;

    public StandupActivityTest() {
        super("com.pyxis.androidAgilelyTimer", StandupCountdownActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        setActivityInitialTouchMode(false);

        Intent intent = new Intent();
        intent.putExtra("countdownInMinutes", 2);
        intent.putExtra("membersNumber", 4);
        setActivityIntent(intent);

        activity = getActivity();
        
        membersCaption = activity.getString(R.string.countdown_members_caption);

    }

    public void testMemberCountWhenOneMemberIsDone() {
        TextView view = (TextView)activity.findViewById(R.id.members_label);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                Button button = (Button)activity.findViewById(R.id.end_member_button);
                button.requestFocus();
                button.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        assertEquals(membersCaption + " 2 / 4", view.getText());

    }

    public void testMemberCountWhenNoOneIsDone() {
        TextView view = (TextView)activity.findViewById(R.id.members_label);

        assertEquals(membersCaption + " 1 / 4", view.getText());

    }

    public void testMemberCountWhenMoreThanTheMembersCountAreDone() {
        TextView view = (TextView)activity.findViewById(R.id.members_label);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                Button button = (Button)activity.findViewById(R.id.end_member_button);
                button.requestFocus();
                button.performClick();
                button.performClick();
                button.performClick();
                button.performClick();
                button.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        assertEquals("Open Discussions", view.getText());

    }
}
