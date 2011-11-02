package com.pyxis.androidAgilelyTimer.activities;

import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.test.ActivityUnitTestCase;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import com.pyxis.androidAgilelyTimer.AgilelyTimerPreferencesAccessor;
import com.pyxis.androidAgilelyTimer.R;
import kankan.wheel.widget.WheelView;

public class TimeboxStartActivityTest extends ActivityUnitTestCase<TimeboxStartActivity> {
    public static final String LOG_TAG = "TimeboxStartActivityTest";
    private static final String INITIAL_POSITION_TEXT = "2";
    private static final String LAST_POSITION_TEXT = "60";

    private TimeboxStartActivity activity;
    private WheelView minutesSpinner;
    private Button startButton;
    private ImageView pyxisLogo;
    private Instrumentation instrumentation;


    private AgilelyTimerPreferencesAccessor prefs;

    public TimeboxStartActivityTest() {
        super(TimeboxStartActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        instrumentation = getInstrumentation();
        activity = startActivity(new Intent(Intent.ACTION_MAIN), null, null);
        minutesSpinner = (WheelView) activity.findViewById(R.id.timebox_minutes_spinner);
        startButton = (Button) activity.findViewById(R.id.start_button);
        pyxisLogo = (ImageView) activity.findViewById(R.id.grown_by_pyxis_logo);

        prefs = new AgilelyTimerPreferencesAccessor(activity);
        prefs.resetPreferences();

    }

    protected void tearDown() throws Exception {
        super.tearDown();
        prefs.resetPreferences();

    }


    public void testPreconditions() {
        assertNotNull(minutesSpinner);
        assertNotNull(startButton);
        assertNotNull(pyxisLogo);
    }

    public void testThatMinutesSpinnerInitialPositionReturnsRightText() {
        final String initialMinutes = minutesSpinner.getAdapter().getItem(minutesSpinner.getCurrentItem());
        assertEquals(INITIAL_POSITION_TEXT, initialMinutes);
    }

    public void testThatMinutesSpinnerSomeRandomMiddlePositionReturnsRightText() {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                minutesSpinner.requestFocus();
                minutesSpinner.setCurrentItem(15);
            }
        });
        instrumentation.waitForIdleSync();

        final String minutes = minutesSpinner.getAdapter().getItem(minutesSpinner.getCurrentItem());
        assertEquals("17", minutes);
    }

    public void testThatMinutesSpinnerLastPositionReturnsRightText() {
        final int itemsCount = minutesSpinner.getAdapter().getItemsCount();
        Log.d(LOG_TAG, "Item count : " + itemsCount);
        activity.runOnUiThread(new Runnable() {
            public void run() {
                minutesSpinner.requestFocus();
                minutesSpinner.setCurrentItem(itemsCount - 1);
            }
        });
        instrumentation.waitForIdleSync();

        final String minutes = minutesSpinner.getAdapter().getItem(minutesSpinner.getCurrentItem());
        assertEquals(LAST_POSITION_TEXT, minutes);
    }

    public void testThatMinutesSpinnerIsSetWithTheRightLabelFromResources() {
        final String resourceLabel = activity.getString(R.string.minutes_label);
        assertEquals(resourceLabel, minutesSpinner.getLabel());
    }

    public void testThatClickingThePyxisLogoStartsABrowserWithAUrlFromTheResources() {
        assertNull(getStartedActivityIntent());
        activity.runOnUiThread(new Runnable() {
            public void run() {
                pyxisLogo.requestFocus();
                pyxisLogo.performClick();
            }
        });
        instrumentation.waitForIdleSync();
        final Intent startedIntent = getStartedActivityIntent();
        assertNotNull(startedIntent);
        assertEquals(Intent.ACTION_VIEW, startedIntent.getAction());

        final String url = activity.getString(R.string.pyxis_website_uri);
        assertEquals(Uri.parse(url), startedIntent.getData());
    }

    public void testThatClickingTheStartButtonStartsTheCountdownActivityWithTheExtraHavingDefaultSpinnerValue() {
        checkStartedCountdownWasStartedWithExtras(2);
    }

    public void testThatClickingTheStartButtonStartsTheCountdownActivityWithTheExtraHavingChangeSpinnerValue() {
        testThatMinutesSpinnerSomeRandomMiddlePositionReturnsRightText();
        checkStartedCountdownWasStartedWithExtras(17);
    }

    public void testPreferencesAreSavedAfterRestartingTheApplication() {

/*  TODO this behaviour should be tested.
        testThatMinutesSpinnerSomeRandomMiddlePositionReturnsRightText();
        checkStartedCountdownWasStartedWithExtras(17);

        this.activity.finish();
        activity = null;
        activity = startActivity(new Intent(Intent.ACTION_MAIN), null, null);
        minutesSpinner = (WheelView) activity.findViewById(R.id.timebox_minutes_spinner);
        startButton = (Button) activity.findViewById(R.id.start_button);
        pyxisLogo = (ImageView) activity.findViewById(R.id.grown_by_pyxis_logo);
        checkStartedCountdownWasStartedWithExtras(17);
*/
    }

    private void checkStartedCountdownWasStartedWithExtras(final int countdownInMinutes) {
        assertNull(getStartedActivityIntent());
        activity.runOnUiThread(new Runnable() {
            public void run() {
                startButton.requestFocus();
                startButton.performClick();
            }
        });
        instrumentation.waitForIdleSync();
        final Intent startedIntent = getStartedActivityIntent();
        assertNotNull(startedIntent);

        final ComponentName intentComponentName = startedIntent.getComponent();
        assertEquals(activity.getPackageName(), intentComponentName.getPackageName());
        assertEquals(TimeboxCountdownActivity.class.getName(), intentComponentName.getClassName());

        final Bundle intentExtras = startedIntent.getExtras();
        final int countdownValueInSeconds = intentExtras.getInt(activity.getString(R.string.countdown_value));
        assertEquals(countdownInMinutes * 60, countdownValueInSeconds);
    }
}
