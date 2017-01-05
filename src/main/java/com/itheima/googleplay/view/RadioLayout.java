package com.itheima.googleplay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by Yooz on 2016/3/24.
 */
public class RadioLayout extends FrameLayout {

    private float ratio = 3.43f;

    public RadioLayout(Context context) {
        super(context);
    }

    public RadioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RadioLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int width = size - getPaddingLeft() - getPaddingRight();

        int mode1 = MeasureSpec.getMode(heightMeasureSpec);
        int size1 = MeasureSpec.getSize(heightMeasureSpec);
        int height = size1 - getPaddingBottom() - getPaddingTop();

        if (mode == MeasureSpec.EXACTLY && mode1 != MeasureSpec.EXACTLY) {
            height = (int) (width / ratio + 0.5f);
        } else if (mode != MeasureSpec.EXACTLY && mode1 == MeasureSpec.EXACTLY) {
            width = (int) (height / ratio + 0.5f);
        }

        widthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY,
                width + getPaddingLeft() + getPaddingRight());

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY,
                height + getPaddingTop() + getPaddingBottom());

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
