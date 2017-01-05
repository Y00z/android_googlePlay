package com.itheima.googleplay.tools;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by Yooz on 2016/3/26.
 */
public class DrawableUtils {
    public static GradientDrawable createGradientDrawable(int color) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(color);
        gradientDrawable.setCornerRadius(UiUtils.dip2px(8));
        return gradientDrawable;
    }


    public static StateListDrawable getSelectDrawable(Drawable pressedDrawable, Drawable normalDrawable) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed},pressedDrawable);
        stateListDrawable.addState(new int[]{},normalDrawable);
        return stateListDrawable;
    }

}
