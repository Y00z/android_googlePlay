package com.itheima.googleplay.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.googleplay.R;
import com.itheima.googleplay.bean.SubjectInfo;
import com.itheima.googleplay.http.HttpHelper;
import com.itheima.googleplay.tools.BitmapHelper;
import com.itheima.googleplay.tools.UiUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Yooz on 2016/3/24.
 */
public class SubjectHolder extends BaseHolder<SubjectInfo> {
    @ViewInject(R.id.item_icon)
    private ImageView item_icon;
    @ViewInject(R.id.item_txt)
    private TextView item_txt;

    @Override
    public View initView() {
        View view = View.inflate(UiUtils.getContext() , R.layout.item_subject , null);
        ViewUtils.inject(this,view);
        return view;
    }

    @Override
    public void refreshView(SubjectInfo subjectInfo) {
        item_txt.setText(subjectInfo.getDes());
        BitmapHelper.getBitmapUtils().display(item_icon , HttpHelper.URL + "image?name=" + subjectInfo.getUrl());
    }
}
