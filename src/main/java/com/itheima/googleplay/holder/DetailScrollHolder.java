package com.itheima.googleplay.holder;

import android.view.View;
import android.widget.ImageView;

import com.itheima.googleplay.R;
import com.itheima.googleplay.bean.App;
import com.itheima.googleplay.http.HttpHelper;
import com.itheima.googleplay.tools.BitmapHelper;
import com.itheima.googleplay.tools.UiUtils;

import java.util.ArrayList;

/**
 * Created by Yooz on 2016/3/22.
 */
public class DetailScrollHolder extends BaseHolder<App> {

    private ImageView ivs[];


    @Override
    public View initView() {
        View view = View.inflate(UiUtils.getContext(), R.layout.detail_screen, null);
        ivs = new ImageView[5];
        ivs[0] = (ImageView) view.findViewById(R.id.screen_1);
        ivs[1] = (ImageView) view.findViewById(R.id.screen_2);
        ivs[2] = (ImageView) view.findViewById(R.id.screen_3);
        ivs[3] = (ImageView) view.findViewById(R.id.screen_4);
        ivs[4] = (ImageView) view.findViewById(R.id.screen_5);
        return view;
    }

    @Override
    public void refreshView(App app) {
        ArrayList<String> screen = app.getScreen();
        for (int i = 0; i < 5; i++) {
            if (i < screen.size()) {
                ivs[i].setVisibility(View.VISIBLE);
                BitmapHelper.getBitmapUtils().display(ivs[i], HttpHelper.URL + "image?name=" + screen.get(i));
            } else {
                ivs[i].setVisibility(View.GONE);
            }
        }
    }
}
