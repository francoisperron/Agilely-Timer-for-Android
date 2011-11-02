package com.pyxis.androidAgilelyTimer.activities;


import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import com.pyxis.androidAgilelyTimer.AgilelyTimerPreferencesAccessor;
import com.pyxis.androidAgilelyTimer.R;
import kankan.wheel.widget.NumericWheelAdapter;
import kankan.wheel.widget.WheelView;

public class StandupStartActivity extends BaseMenuActivity {

    static final int BEGIN_NUMBER = 2;
    
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setVolumeControlStream(AudioManager.STREAM_ALARM);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.standup_start);

        final WheelView mins = (WheelView) findViewById(R.id.mins);
        mins.setAdapter(new NumericWheelAdapter(BEGIN_NUMBER, 60, "%02d"));
        mins.setLabel(getString(R.string.minutes_label));

        final WheelView members = (WheelView) findViewById(R.id.members);
        members.setAdapter(new NumericWheelAdapter(BEGIN_NUMBER, 15, "%02d"));
        members.setLabel(getString(R.string.members_label));

        final AgilelyTimerPreferencesAccessor prefs = new AgilelyTimerPreferencesAccessor(this);
        mins.setCurrentItem( prefs.getStandupLenghtOfMeeting()-BEGIN_NUMBER);
        members.setCurrentItem( prefs.getStandupNumberOfMembers()-BEGIN_NUMBER);

        final Button startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                final Intent countdownIntent = new Intent(StandupStartActivity.this, StandupCountdownActivity.class);
                final int minutes = Integer.parseInt(mins.getAdapter().getItem(mins.getCurrentItem()));
                final int nbMembers = Integer.parseInt(members.getAdapter().getItem(members.getCurrentItem()));

                countdownIntent.putExtra(getString(R.string.countdown_value), minutes);
                countdownIntent.putExtra(getString(R.string.members_value), nbMembers);

                prefs.setStandupPreferences(minutes, nbMembers );

                startActivity(countdownIntent);
            }
        });

        final ImageView grownByPyxisLogo = (ImageView) findViewById(R.id.grown_by_pyxis_logo);
        grownByPyxisLogo.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                final String url = getString(R.string.pyxis_website_uri);
                final Intent uriActionIntent = new Intent(Intent.ACTION_VIEW);
                uriActionIntent.setData(Uri.parse(url));
                startActivity(uriActionIntent);
            }
        });
    }
}