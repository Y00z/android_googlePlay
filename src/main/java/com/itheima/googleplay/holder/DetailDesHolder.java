package com.itheima.googleplay.holder;

import android.animation.ValueAnimator;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.itheima.googleplay.R;
import com.itheima.googleplay.bean.App;
import com.itheima.googleplay.tools.UiUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Yooz on 2016/3/23.
 */
public class DetailDesHolder extends BaseHolder<App> implements View.OnClickListener {
    @ViewInject(R.id.des_titile)
    private TextView des_titile;
    @ViewInject(R.id.des_content)
    private TextView des_content;
    @ViewInject(R.id.des_author)
    private TextView des_author;
    @ViewInject(R.id.des_arrow)
    private ImageView des_arrow;
    @ViewInject(R.id.des_layout)
    private RelativeLayout des_layout;

    private ScrollView scroll;
    @Override
    public View initView() {
        View view = View.inflate(UiUtils.getContext(), R.layout.detail_des, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void refreshView(App app) {
        des_content.setText(app.getDes());
        des_author.setText("作者:" + app.getAuthor());
        des_layout.setOnClickListener(this);
        ViewGroup.LayoutParams layoutParams = des_content.getLayoutParams();
        layoutParams.height = getMeasureShortHeight();
        des_content.setLayoutParams(layoutParams);
    }


    private boolean flag = true;
    int startHeight;
    int targetHeigth;

    @Override
    public void onClick(View v) {
        scroll = getScrollView(des_layout);
        if (!flag) {          //关
//            des_content.setVisibility(View.GONE);
            System.out.println("关了阿");
            startHeight = getMeasureLongHeight();
            targetHeigth = getMeasureShortHeight();
            flag = !flag;
        } else {            //开
//            des_content.setVisibility(View.VISIBLE);
            flag = !flag;
            startHeight = getMeasureShortHeight();
            targetHeigth = getMeasureLongHeight();
        }


        final ViewGroup.LayoutParams layoutParams = des_content.getLayoutParams();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(startHeight, targetHeigth);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                layoutParams.height = value;
                des_content.setLayoutParams(layoutParams);
                scroll.scrollTo(0 , scroll.getMeasuredHeight());
            }
        });
        valueAnimator.setDuration(500);
        valueAnimator.start();
    }


    public int getMeasureLongHeight() {
        int measuredWidth = des_content.getMeasuredWidth();
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.EXACTLY, measuredWidth);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.AT_MOST, 1000);
        des_content.measure(widthMeasureSpec, heightMeasureSpec);
        return des_content.getMeasuredHeight();
    }


    public int getMeasureShortHeight() {
        TextView textView = new TextView(UiUtils.getContext());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        textView.setMaxLines(7);
        textView.setLines(7);

        int measuredWidth = des_content.getMeasuredWidth();
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.EXACTLY, measuredWidth);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.AT_MOST, 1000);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredHeight();
    }


    /**
     * 获取到界面的ScollView,
     */
    public ScrollView getScrollView(View view){
        ViewParent parent = view.getParent();
        if(parent instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) parent;
            if(group instanceof ScrollView){
                return (ScrollView) group;
            } else {
                return getScrollView(group);  //递归
            }
        } else {
            return  null;
        }
    }
}
