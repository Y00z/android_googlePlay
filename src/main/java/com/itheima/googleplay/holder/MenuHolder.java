package com.itheima.googleplay.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itheima.googleplay.R;
import com.itheima.googleplay.bean.UserInfo;
import com.itheima.googleplay.http.HttpHelper;
import com.itheima.googleplay.manager.ThreadManager;
import com.itheima.googleplay.protocol.UserProtocol;
import com.itheima.googleplay.tools.UiUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Yooz on 2016/3/19.
 */
public class MenuHolder extends BaseHolder<UserInfo> implements View.OnClickListener {
    @ViewInject(R.id.photo_layout)
    private RelativeLayout photo_layout;

    @ViewInject(R.id.image_photo)
    private ImageView image_photo;

    @ViewInject(R.id.user_name)
    private TextView user_name;

    @ViewInject(R.id.image_over)
    private TextView image_over;

    @ViewInject(R.id.user_email)
    private TextView user_email;


    @Override
    public View initView() {
        View view = UiUtils.inflate(R.layout.menu_holder);
        ViewUtils.inject(this, view);
        photo_layout.setOnClickListener(this);
        return view;
    }

    @Override
    public void refreshView(UserInfo data) {
        user_email.setText("yooz@line.com");
        user_name.setText("悠哉");
        bitmapUtils.display(image_photo, HttpHelper.URL + "image?name=" + data.getUrl());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo_layout:
                ThreadManager.getInstance().createLongPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        UserProtocol userProtocol = new UserProtocol();
                        final UserInfo load = userProtocol.load(0);
                        UiUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setData(load);
                            }
                        });
                    }
                });
                break;
        }
    }
}
