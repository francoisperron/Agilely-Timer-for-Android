package com.pyxis.androidAgilelyTimer.widget;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import com.pyxis.androidAgilelyTimer.R;

public class ProgressBarWithText extends ProgressBar implements ProgressView {
    private String text;
    private Paint textPaint;
    private int initialValue;

    public ProgressBarWithText(Context context) {
        super(context);
        text = "0 %";
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        setProgressDrawable(getResources().getDrawable(R.drawable.progress_color));
    }

    public ProgressBarWithText(Context context, AttributeSet attrs) {
        super(context, attrs);
        text = "0 %";
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
    }

    public ProgressBarWithText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        text = "0 %";
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        // First draw the regular progress bar, then custom draw our text
        super.onDraw(canvas);
        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        int x = getWidth() - bounds.width();
        int y = getHeight() / 2 - bounds.centerY();
        canvas.drawText(text, x, y, textPaint);
    }

    private void setText(String text) {
        this.text = text;
        drawableStateChanged();
    }

    private void setDefaultBold(boolean on) {
        if (on) {
            textPaint.setTypeface(Typeface.DEFAULT_BOLD);
            textPaint.setAntiAlias(true);

        } else {
            textPaint.setTypeface(Typeface.DEFAULT);
            textPaint.setAntiAlias(true);
        }
    }

    private void setTextColor(int color) {
        textPaint.setColor(color);
        drawableStateChanged();
    }

    public void setInitialValue(int initialValue) {
        this.initialValue = initialValue;
        setMax(initialValue);
        setText("2:00");
        setDefaultBold(true);
        setProgress(0);
    }

    public void updateProgress(int updatedValue) {
        setProgress(initialValue - updatedValue);
        refreshDrawableState();
    }
}
