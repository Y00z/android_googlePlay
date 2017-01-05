package com.itheima.googleplay.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itheima.googleplay.R;

/**
 * Created by Yooz on 2016/4/8.
 */
public class CycloProgressButton extends LinearLayout {


    private TextView tv_note;
    private ImageView iv_icon;

    private long mProgress;
    private long mMax;
    private boolean isEnable = false;

    public void setmMax(long mMax) {
        this.mMax = mMax;
    }

    public void setIsEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public void setmProgress(long mProgress) {
        this.mProgress = mProgress;
        invalidate();
    }

    public void setTv_note(String text) {
        tv_note.setText(text);
        tv_note.setTextColor(Color.BLACK);
        tv_note.setTextSize(12);
    }


    public void setIv_icon(int resId) {
        iv_icon.setImageResource(resId);
    }

    public CycloProgressButton(Context context) {
        super(context);
        initView();
    }

    public CycloProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CycloProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView() {
        View inflate = View.inflate(getContext(), R.layout.item_progressbutton, this);
        tv_note = (TextView) inflate.findViewById(R.id.tv_note);
        iv_icon = (ImageView) inflate.findViewById(R.id.iv_icon);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (isEnable) {
            RectF rectF = new RectF(iv_icon.getLeft(), iv_icon.getTop(), iv_icon.getRight(), iv_icon.getBottom());
            float startAngle = -90;
            float sweepAngle = mProgress * 360.f / mMax;
            boolean useCenter = false;
            Paint paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);
            paint.setAntiAlias(true);
            canvas.drawArc(rectF, startAngle, sweepAngle, useCenter, paint);
        }
    }
}
