package com.itheima.googleplay.holder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.googleplay.R;
import com.itheima.googleplay.bean.CategoryInfo;
import com.itheima.googleplay.http.HttpHelper;
import com.itheima.googleplay.tools.BitmapHelper;
import com.itheima.googleplay.tools.UiUtils;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by Yooz on 2016/3/24.
 */
public class CategoryHolder extends BaseHolder<CategoryInfo> {

    ImageView[] ivs;
    TextView[] tvs;

    @Override
    public View initView() {
        View view = View.inflate(UiUtils.getContext(), R.layout.item_category_content, null);
        ivs = new ImageView[3];
        tvs = new TextView[3];
        ivs[0] = (ImageView) view.findViewById(R.id.iv_1);
        ivs[1] = (ImageView) view.findViewById(R.id.iv_2);
        ivs[2] = (ImageView) view.findViewById(R.id.iv_3);
        tvs[0] = (TextView) view.findViewById(R.id.tv_1);
        tvs[1] = (TextView) view.findViewById(R.id.tv_2);
        tvs[2] = (TextView) view.findViewById(R.id.tv_3);
        return view;
    }

    @Override
    public void refreshView(CategoryInfo data) {
        BitmapUtils bitmapUtils = BitmapHelper.getBitmapUtils();
        if (!TextUtils.isEmpty(data.getName1()) && !TextUtils.isEmpty(data.getUrl1())) {
            bitmapUtils.display(ivs[0] , HttpHelper.URL + "image?name=" + data.getUrl1());
            tvs[0].setText(data.getName1());
        } else {
            ivs[0].setVisibility(View.INVISIBLE);
            tvs[0].setVisibility(View.INVISIBLE);
        }

        if (!TextUtils.isEmpty(data.getName2()) && !TextUtils.isEmpty(data.getUrl2())) {
            bitmapUtils.display(ivs[1] , HttpHelper.URL + "image?name=" + data.getUrl2());
            tvs[1].setText(data.getName2());
        } else {
            ivs[1].setVisibility(View.INVISIBLE);
            tvs[1].setVisibility(View.INVISIBLE);
        }

        if (!TextUtils.isEmpty(data.getName3()) && !TextUtils.isEmpty(data.getUrl3())) {
            bitmapUtils.display(ivs[2] , HttpHelper.URL + "image?name=" + data.getUrl3());
            tvs[2].setText(data.getName3());
        } else {
            ivs[2].setVisibility(View.INVISIBLE);
            tvs[2].setVisibility(View.INVISIBLE);
        }
    }
}
