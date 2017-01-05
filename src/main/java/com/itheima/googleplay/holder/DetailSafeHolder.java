package com.itheima.googleplay.holder;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itheima.googleplay.R;
import com.itheima.googleplay.bean.App;
import com.itheima.googleplay.http.HttpHelper;
import com.itheima.googleplay.tools.BitmapHelper;
import com.itheima.googleplay.tools.UiUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by Yooz on 2016/3/22.
 */
public class DetailSafeHolder extends BaseHolder<App> implements View.OnClickListener {
    @ViewInject(R.id.safe_layout)
    private RelativeLayout safe_layout;
    @ViewInject(R.id.safe_content)
    private LinearLayout safe_content;
    @ViewInject(R.id.safe_title_layout)
    private RelativeLayout safe_title_layout;
    @ViewInject(R.id.safe_arrow)
    private ImageView safe_arrow;

    ImageView ivs[];

    ImageView iv_des[];
    TextView tv_des[];

    LinearLayout des_layout[];

    int mHeaderViewHeight;

    @Override
    public View initView() {
        View view = View.inflate(UiUtils.getContext(), R.layout.detail_safe, null);
        ViewUtils.inject(this, view);
        ivs = new ImageView[4];
        ivs[0] = (ImageView) view.findViewById(R.id.iv_1);
        ivs[1] = (ImageView) view.findViewById(R.id.iv_2);
        ivs[2] = (ImageView) view.findViewById(R.id.iv_3);
        ivs[3] = (ImageView) view.findViewById(R.id.iv_4);

        iv_des = new ImageView[4];
        iv_des[0] = (ImageView) view.findViewById(R.id.des_iv_1);
        iv_des[1] = (ImageView) view.findViewById(R.id.des_iv_2);
        iv_des[2] = (ImageView) view.findViewById(R.id.des_iv_3);
        iv_des[3] = (ImageView) view.findViewById(R.id.des_iv_4);

        tv_des = new TextView[4];
        tv_des[0] = (TextView) view.findViewById(R.id.des_tv_1);
        tv_des[1] = (TextView) view.findViewById(R.id.des_tv_2);
        tv_des[2] = (TextView) view.findViewById(R.id.des_tv_3);
        tv_des[3] = (TextView) view.findViewById(R.id.des_tv_4);

        des_layout = new LinearLayout[4];
        des_layout[0] = (LinearLayout) view.findViewById(R.id.des_layout_1);
        des_layout[1] = (LinearLayout) view.findViewById(R.id.des_layout_2);
        des_layout[2] = (LinearLayout) view.findViewById(R.id.des_layout_3);
        des_layout[3] = (LinearLayout) view.findViewById(R.id.des_layout_4);

        safe_title_layout.setOnClickListener(this);

        return view;
    }

    @Override
    public void refreshView(App app) {
        ArrayList<String> safeDes = app.getSafeDes();
        ArrayList<Integer> safeDesColor = app.getSafeDesColor();
        ArrayList<String> safeDesUrl = app.getSafeDesUrl();
        ArrayList<String> safeUrl = app.getSafeUrl();
        for (int i = 0; i < 4; i++) {
            if (i < safeUrl.size() && i < safeDes.size() && i < safeDesUrl.size() && i < safeDesColor.size()) {
                ivs[i].setVisibility(View.VISIBLE);
                BitmapHelper.getBitmapUtils().display(ivs[i], HttpHelper.URL + "image?name=" + safeUrl.get(i));
                BitmapHelper.getBitmapUtils().display(iv_des[i], HttpHelper.URL + "image?name=" + safeDesUrl.get(i));
                tv_des[i].setText(safeDes.get(i));

                // 根据服务器数据显示不同的颜色
                int color;
                int colorType = safeDesColor.get(i);
                if (colorType >= 1 && colorType <= 3) {
                    color = Color.rgb(255, 153, 0); // 00 00 00
                } else if (colorType == 4) {
                    color = Color.rgb(0, 177, 62);
                } else {
                    color = Color.rgb(122, 122, 122);
                }
                tv_des[i].setTextColor(color);

            } else {
                ivs[i].setVisibility(View.GONE);
            }
        }

    }


    private boolean flag = false;
    private int startHeight;
    private int targetHeight;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View v) {
        if (!flag) {            //展开
//            safe_content.setVisibility(View.GONE);
            safe_arrow.setImageResource(R.drawable.arrow_up);
            flag = !flag;
            startHeight = getMeasureHeight();
            targetHeight = 0;
        } else {                //关闭
//            safe_content.setVisibility(View.VISIBLE);
            safe_arrow.setImageResource(R.drawable.arrow_down);
            flag = !flag;
            startHeight = 0;
            targetHeight = getMeasureHeight();
        }

        final ViewGroup.LayoutParams layoutParams = safe_content.getLayoutParams();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(startHeight, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                layoutParams.height = value;
                safe_content.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.setDuration(500);
        valueAnimator.start();
    }


    //onMeasure()  制定测量的规则
    // measure() 实际测量

    /**
     * 获取控件实际的高度
     */
    public int getMeasureHeight() {
        int width = safe_content.getMeasuredWidth();
        int WidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.EXACTLY, width);
        int HeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.AT_MOST, 1000);
        safe_content.measure(WidthMeasureSpec, HeightMeasureSpec);
        return safe_content.getMeasuredHeight();
    }

}
