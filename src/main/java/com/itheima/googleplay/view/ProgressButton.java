package com.itheima.googleplay.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Yooz on 2016/3/31.
 */
public class ProgressButton extends Button {
    private boolean progressEnable;
    private long mMax = 100;
    private long mProgress;


    public void setProgressEnable(boolean progressEnable) {
        this.progressEnable = progressEnable;
    }

    public boolean isProgressEnable() {
        return progressEnable;
    }

    public long getmMax() {
        return mMax;
    }

    public void setmMax(long mMax) {
        this.mMax = mMax;
    }

    public long getmProgress() {
        return mProgress;
    }

    public void setmProgress(long mProgress) {
        this.mProgress = mProgress;
    }

    public ProgressButton(Context context) {
        super(context);
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(progressEnable) {
            ColorDrawable drawable = new ColorDrawable(Color.RED);
            int left = 0;
            int top = 0;
            int right = (int) (mProgress *1.0f / mMax * getMeasuredWidth() + .5f);
            int bottom = getBottom();
            drawable.setBounds(left, top, right, bottom);
            drawable.draw(canvas);
        }
        super.onDraw(canvas);
    }
}
