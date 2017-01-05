package com.itheima.googleplay.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.itheima.googleplay.protocol.RecommendProtocol;
import com.itheima.googleplay.tools.UiUtils;
import com.itheima.googleplay.view.LoadingPage;
import com.itheima.googleplay.view.flyoutin.ShakeListener;
import com.shitou.googleplay.lib.randomlayout.StellarMap;

import java.util.List;
import java.util.Random;

/**
 * Created by Yooz on 2016/3/31.
 */
public class RecommendFragment extends BaseFragment {

    private List<String> mData;
    private ShakeListener shakeListener;
    private StellarMap stellarMap;
    private RecommendAdapter recommendAdapter;

    @Override
    public View createSuccessView() {
        stellarMap = new StellarMap(UiUtils.getContext());
        recommendAdapter = new RecommendAdapter();
        stellarMap.setAdapter(recommendAdapter);
        stellarMap.setGroup(0, true);
        stellarMap.setRegularity(15, 30);
        //摇一摇
        shakeListener = new ShakeListener(UiUtils.getContext());
        shakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                int currentGroup = stellarMap.getCurrentGroup();
                if (currentGroup == recommendAdapter.getGroupCount() - 1) {
                    currentGroup = 0;
                } else {
                    currentGroup ++;
                }
                stellarMap.setGroup(currentGroup, true);
            }
        });
        return stellarMap;
    }

    @Override
    public void onResume() {
        if (shakeListener != null) {
            shakeListener.resume();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (shakeListener != null) {
            shakeListener.pause();
        }
        super.onPause();
    }

    @Override
    protected LoadingPage.LoadResult load() {
        RecommendProtocol recommendProtocol = new RecommendProtocol();
        mData = recommendProtocol.load(0);
        return checkData(mData);
    }

    private class RecommendAdapter implements StellarMap.Adapter {
        public static final int PAGER_SIZE = 15;

        @Override
        public int getGroupCount() {
            int groupCount = mData.size() / PAGER_SIZE;
            if (mData.size() % PAGER_SIZE > 0) {
                System.out.println("index--"+mData.size() % PAGER_SIZE);
                groupCount++;
            }
            return groupCount;
        }

        @Override
        public int getCount(int group) {
            if (group == getGroupCount()-1) {
                System.out.println("index++"+mData.size() % PAGER_SIZE);
                if (mData.size() % PAGER_SIZE > 0) {
                    return mData.size() % PAGER_SIZE;
                }
            }
            return PAGER_SIZE;
        }

        @Override
        public View getView(int group, int position, View convertView) {
            TextView textView = new TextView(UiUtils.getContext());
            int index = group * getCount(group) + position;
//            if(stellarMap.getCurrentGroup() == getGroupCount()-1){
//                index = mData.size() % PAGER_SIZE;
//            }
            System.out.println("index+" + mData.get(index));
            System.out.println("index+" + index);
            textView.setText(mData.get(index));
            Random random = new Random();
            textView.setTextSize(random.nextInt(8) + 20); //20-28
            int alphe = 255;
            int red = random.nextInt(180) + 30;// 30-210
            int green = random.nextInt(180) + 30;
            int blue = random.nextInt(180) + 30;
            int argb = Color.argb(alphe, red, green, blue);
            textView.setTextColor(argb);
            int padding = UiUtils.dip2px(5);
            textView.setPadding(padding, padding, padding, padding);
            return textView;
        }

        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            return  (group + 1) % getGroupCount();
        }
    }
}
