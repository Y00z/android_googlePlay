package com.itheima.googleplay.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.itheima.googleplay.protocol.HotProtocol;
import com.itheima.googleplay.tools.DrawableUtils;
import com.itheima.googleplay.tools.UiUtils;
import com.itheima.googleplay.view.Flowlayout;
import com.itheima.googleplay.view.LoadingPage;

import java.util.List;
import java.util.Random;

public class TopFragment extends BaseFragment {

    private List<String> data;

    @Override
    public View createSuccessView() {
        ScrollView scrollView = new ScrollView(UiUtils.getContext());
        Flowlayout linearLayout = new Flowlayout(UiUtils.getContext());
        int padding = UiUtils.dip2px(13);
        linearLayout.setPadding(padding,padding,padding,padding);
        //默认图片
        GradientDrawable normalDrawable = DrawableUtils.createGradientDrawable(0xffcecece);
        for (int i = 0; i < data.size(); i++) {
            TextView textView = new TextView(UiUtils.getContext());
            textView.setText(data.get(i));
            Random random = new Random();
            int red = random.nextInt(200) + 22;
            int green = random.nextInt(200) + 22;
            int blue = random.nextInt(200) + 22;
            int color = Color.rgb(red,green,blue);
            //按下图片
            GradientDrawable pressedDrawable = DrawableUtils.createGradientDrawable(color);
            //状态选择器
            StateListDrawable selectDrawable = DrawableUtils.getSelectDrawable(normalDrawable,pressedDrawable);
            textView.setTextColor(Color.WHITE);
            textView.setBackgroundDrawable(selectDrawable);
            int textPaddingH = UiUtils.dip2px(7);
            int textPaddingW = UiUtils.dip2px(4);
            textView.setPadding(textPaddingH, textPaddingW, textPaddingH, textPaddingW);
//            textView.setTextSize(18);
            linearLayout.addView(textView);
        }
        scrollView.addView(linearLayout);
        return scrollView;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        HotProtocol hotProtocol = new HotProtocol();
        data = hotProtocol.load(0);
        return checkData(data);
    }
}
