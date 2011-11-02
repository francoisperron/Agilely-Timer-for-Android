package com.pyxis.androidAgilelyTimer;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.util.List;

public class HintsPopUper implements CountDownListener {

    private final List<String> hints;

    private final int[] VerticalGravities = {Gravity.CENTER_HORIZONTAL, Gravity.RIGHT, Gravity.LEFT};
    private final int[] HorizontalGravities  = {Gravity.CENTER_VERTICAL, Gravity.TOP, Gravity.BOTTOM, };
    private int popupCount;
    private Activity activity;


    public HintsPopUper(final Activity activity, final List<String> hints){
        this.activity = activity;
        this.hints = hints;
    }

    public void update(final int countdownInSeconds) {
        popupCount++;
         Log.d(HintsPopUper.class.getName(), "Update");
        if(popupCount % 10 == 0)
        {
            Log.d(HintsPopUper.class.getName(), "POPUP!!");
            popUp();
        }
    }

    private void popUp() {
       activity.runOnUiThread(new Runnable() {
            public void run() {
                Context context = activity.getApplicationContext();
                Toast toast = Toast.makeText(context, selectHint(), Toast.LENGTH_LONG);
                toast.setGravity(selectGravity(), 0, 0);
                toast.show();
            }
        });
    }

    private int selectGravity() {
        return VerticalGravities[generateRandomNumberBetween(0, VerticalGravities.length - 1)] |
               HorizontalGravities[generateRandomNumberBetween(0, HorizontalGravities.length - 1)];
    }

    private CharSequence selectHint() {
        return hints.get(generateRandomNumberBetween(0, hints.size() - 1));
    }

    public static int generateRandomNumberBetween(int min, int max)
    {
        return min + (int)Math.round((Math.random() * (max - min)));
    }
}
