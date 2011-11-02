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

public class TimeboxStartActivity extends BaseMenuActivity {

    private static final int MIN_MINUTES = 2;
    private static final int MAX_MINUTES = 60;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setVolumeControlStream(AudioManager.STREAM_ALARM);
        setContentView(R.layout.timebox_start);

        final WheelView minutesSpinner = (WheelView) findViewById(R.id.timebox_minutes_spinner);
        minutesSpinner.setAdapter(new NumericWheelAdapter(MIN_MINUTES, MAX_MINUTES, "%d"));
        minutesSpinner.setLabel(getString(R.string.minutes_label));

        final AgilelyTimerPreferencesAccessor prefs = new AgilelyTimerPreferencesAccessor(this);
        minutesSpinner.setCurrentItem(prefs.getTimeboxMeetingLength() - MIN_MINUTES);

        final Button startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                final Intent countdownIntent = new Intent(TimeboxStartActivity.this, TimeboxCountdownActivity.class);

                final int minutes = Integer.parseInt(minutesSpinner.getAdapter().getItem(minutesSpinner.getCurrentItem()));

                countdownIntent.putExtra(getString(R.string.countdown_value), minutes * 60);

                final AgilelyTimerPreferencesAccessor prefs = new AgilelyTimerPreferencesAccessor(TimeboxStartActivity.this);
                prefs.setTimeboxPreferences(minutes);

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
