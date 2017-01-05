package com.itheima.googleplay.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.itheima.googleplay.R;
import com.itheima.googleplay.bean.App;
import com.itheima.googleplay.http.HttpHelper;
import com.itheima.googleplay.tools.UiUtils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Yooz on 2016/3/21.
 */
public class DetailInfoHolder extends BaseHolder<App> {
    @ViewInject(R.id.item_icon)
    private ImageView item_icon;

    @ViewInject(R.id.item_title)
    private TextView item_title;

    @ViewInject(R.id.item_rating)
    private RatingBar item_rating;

    @ViewInject(R.id.item_download)
    private TextView item_download;

    @ViewInject(R.id.item_date)
    private TextView item_date;

    @ViewInject(R.id.item_size)
    private TextView item_size;

    @ViewInject(R.id.item_version)
    private TextView item_version;


    @Override
    public View initView() {
        View view = View.inflate(UiUtils.getContext(), R.layout.detail_app_info, null);
        ViewUtils.inject(this, view);
        System.out.println("initView");
        return view;
    }

    @Override
    public void refreshView(App app) {
        BitmapUtils bitmapUtils = new BitmapUtils(UiUtils.getContext());
        item_title.setText(app.getName());
        item_rating.setRating(app.getSize());
        item_download.setText(app.getDownloadNum());
        item_date.setText(app.getDate());
        item_version.setText(app.getVersion());
        item_size.setText(Formatter.formatShortFileSize(UiUtils.getContext(), app.getSize()));
        bitmapUtils.display(item_icon, HttpHelper.URL + "image?name=" + app.getIconUrl());
    }
}
