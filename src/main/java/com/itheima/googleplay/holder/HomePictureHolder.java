package com.itheima.googleplay.holder;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.itheima.googleplay.R;
import com.itheima.googleplay.http.HttpHelper;
import com.itheima.googleplay.tools.UiUtils;
import com.itheima.googleplay.view.InnerViewPager;

import java.util.List;

public class HomePictureHolder extends BaseHolder<List<String>> {
    /*当new HomePictureHolder()就会调用该方法 */
    private InnerViewPager viewPager;
    private List<String> datas;

    @Override
    public View initView() {
//        FrameLayout frameLayout = new FrameLayout(UiUtils.getContext());
//        frameLayout.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, 240));
        viewPager = new InnerViewPager(UiUtils.getContext());
        viewPager.setLayoutParams(new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, UiUtils.getDimens(R.dimen.home_picture_height)));
//        frameLayout.addView(viewPager);
        viewPager.setPageTransformer(true , new DepthPageTransformer() );
        return viewPager;
    }

    /*当 holder.setData 才会调用*/
    @Override
    public void refreshView(List<String> datas) {
        this.datas = datas;
        viewPager.setAdapter(new HomeAdapter());
        //viewPager.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        viewPager.setCurrentItem(1000 * datas.size());
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        runTsak.stop();
                        break;
                    case MotionEvent.ACTION_UP:
                        runTsak.start();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        runTsak.start();
                        break;
                }
                return false;
            }
        });
        runTsak = new AutoRunTask();
        runTsak.start();
    }

    private AutoRunTask runTsak;
    boolean flag;

    public class AutoRunTask implements Runnable {

        @Override
        public void run() {
            if (flag) {
                UiUtils.cancel(this);
                int currentItem = viewPager.getCurrentItem();
                currentItem++;
                viewPager.setCurrentItem(currentItem);
                UiUtils.postDelayed(this, 2000);
            }
        }

        public void start() {
            if (!flag) {
                UiUtils.cancel(this);
                flag = true;
                UiUtils.postDelayed(this, 2000);
            }
        }

        public void stop() {
            flag = false;
            UiUtils.cancel(this);
        }
    }

    class HomeAdapter extends PagerAdapter {
        // 当前viewPager里面有多少个条目
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        /*判断返回的对象和 加载view对象的关系*/
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            int index = position % datas.size();

            ImageView imageView = new ImageView(UiUtils.getContext());
            bitmapUtils.display(imageView, HttpHelper.URL + "image?name=" + datas.get(index));
            container.addView(imageView);  //加载的view对象
            return imageView; // 返回的对象
        }
    }





    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}
