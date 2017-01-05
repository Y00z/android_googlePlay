package com.itheima.googleplay.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.itheima.googleplay.R;
import com.itheima.googleplay.bean.CategoryInfo;
import com.itheima.googleplay.tools.UiUtils;

/**
 * Created by Yooz on 2016/3/25.
 */
public class CategoryTitleHolder extends BaseHolder<CategoryInfo> {

    private TextView textView;

    @Override
    public View initView() {
        textView = new TextView(UiUtils.getContext());
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(18);
        textView.setBackgroundResource(R.drawable.grid_item_bg);
        return textView;
    }

    @Override
    public void refreshView(CategoryInfo data) {
        textView.setText(data.getTitle());
    }
}
