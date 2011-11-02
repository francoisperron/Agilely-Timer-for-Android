package com.pyxis.androidAgilelyTimer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.pyxis.androidAgilelyTimer.R;

public class BaseMenuActivity extends Activity {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.applicationmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case R.id.standup:
                if (getClass() != StandupStartActivity.class) {
                    final Intent standupIntent = new Intent(this, StandupStartActivity.class);
//                    standupIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(standupIntent);
                    finish();
                }
                break;
            case R.id.timebox:
                if (getClass() != TimeboxStartActivity.class) {
                    final Intent timeboxIntent = new Intent(this, TimeboxStartActivity.class);
//                    timeboxIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(timeboxIntent);
                    finish();
                }
                break;
        }
        return true;
    }

}
