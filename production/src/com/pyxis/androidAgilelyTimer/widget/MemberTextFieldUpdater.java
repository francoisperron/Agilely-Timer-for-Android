package com.pyxis.androidAgilelyTimer.widget;

import android.widget.TextView;

public class MemberTextFieldUpdater {

    private final TextView textView;
    private final String memberCaption;
    private final int membersCount;
    private int membersDone;

    public MemberTextFieldUpdater(final TextView textView, final String memberCaption, final int membersCount) {

        this.textView = textView;
        this.memberCaption = memberCaption;
        this.membersCount = membersCount;

        memberDone();
    }

    public void memberDone() {
        if (membersDone < membersCount) {
            membersDone++;
            textView.setText(memberCaption + " " + membersDone + " / " + membersCount);
        }
    }
}
