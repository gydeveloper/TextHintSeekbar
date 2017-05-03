package com.gydeveloper.texthintseekbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class TextHintSeekBar extends LinearLayout
{
    private TextView textView;
    private SeekBar seekBar;

    public TextHintSeekBar(Context context)
    {
        this(context, null);
    }

    public TextHintSeekBar(Context context, AttributeSet attrs)
    {
        this(context, attrs, android.R.attr.seekBarStyle);
    }

    public TextHintSeekBar(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_scroll_bar, this, true);
        textView = (TextView) getChildAt(0);
        seekBar = (SeekBar) getChildAt(1);

        // Obtain the style attributes and apply xml defined attributes on the widgets
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextHintSeekBar, 0, 0);
        int max = a.getInt(R.styleable.TextHintSeekBar_android_max, 100);
        int defaultProgress = a.getInt(R.styleable.TextHintSeekBar_android_progress, 0);
        int textSize = a.getDimensionPixelSize(R.styleable.TextHintSeekBar_android_textSize, 0);
        Drawable textDrawableResource = a.getDrawable(R.styleable.TextHintSeekBar_textDrawable);
        int textDrawableTint = a.getInt(R.styleable.TextHintSeekBar_textDrawableTint, -1);
        int textColor = a.getColor(R.styleable.TextHintSeekBar_textColor, Color.BLACK);
        a.recycle();

        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER_VERTICAL);
        if (textDrawableResource != null)
        {
            textView.setBackground(textDrawableResource);
        }

        textView.setTextColor(textColor);

        if (textDrawableTint != -1) {
            textView.setBackgroundTintList(getContext().getResources().getColorStateList(android.R.color.holo_red_light));
        }

        SeekbarChangeListener seekbarChangeListener = new SeekbarChangeListener();
        seekBar.setOnSeekBarChangeListener(seekbarChangeListener);
        seekBar.setMax(max);
        seekBar.setProgress(defaultProgress);

        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        textView.setText(String.valueOf(seekBar.getProgress()));
        updateTextViewPosition(seekBar.getProgress());
    }

    private void updateTextViewPosition(int progress) {
        textView.setText(Integer.toString(progress));
        int actualWidth = seekBar.getWidth()
                - seekBar.getPaddingLeft()
                - seekBar.getPaddingRight();
        float x = seekBar.getPaddingLeft() + (actualWidth * seekBar.getProgress()) / seekBar.getMax();
        textView.setX(x);
    }

    private class SeekbarChangeListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {
            updateTextViewPosition(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar)
        {
            textView.setVisibility(VISIBLE);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar)
        {
            textView.setVisibility(INVISIBLE);
        }
    }
}
