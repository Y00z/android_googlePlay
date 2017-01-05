package com.itheima.googleplay.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.itheima.googleplay.DetailActivity;
import com.itheima.googleplay.R;
import com.itheima.googleplay.adapter.ListBaseAdapter;
import com.itheima.googleplay.bean.App;
import com.itheima.googleplay.holder.HomePictureHolder;
import com.itheima.googleplay.protocol.HomeProtocol;
import com.itheima.googleplay.tools.UiUtils;
import com.itheima.googleplay.view.BaseListView;
import com.itheima.googleplay.view.LoadingPage;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;

import java.util.List;

public class HomeFragment extends BaseFragment {

    private List<App> mDatas;
    private List<String> pictures;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        show();
    }

    public View createSuccessView() {
//        TextView tv = new TextView(UiUtils.getContext());
//        tv.setText("加载成功了....");
//        tv.setTextColor(Color.BLACK);
//        tv.setTextSize(30);
        BaseListView list_view = new BaseListView(UiUtils.getContext());
        HomePictureHolder homePictureHolder = new HomePictureHolder();
        homePictureHolder.setData(pictures);
        View contentView = homePictureHolder.getContentView();
//        contentView.setLayoutParams(new AbsListView.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT));
        list_view.addHeaderView(contentView);

        list_view.setBackgroundColor(Color.GRAY);
//        myAdapter = new MyAdapter(mDatas);
        list_view.setAdapter(new ListBaseAdapter(mDatas, list_view) {
            @Override
            protected List<App> onload() {
                HomeProtocol protocol = new HomeProtocol();
                List<App> load = protocol.load(mDatas.size());
                datas.addAll(load);
                return load;
            }

            @Override
            public void onInnerItemClick(int position) {
                super.onInnerItemClick(position);
                Toast.makeText(UiUtils.getContext(), "position:" + position, 0).show();

                App app = mDatas.get(position);

                Intent intent = new Intent(UiUtils.getContext(), DetailActivity.class);
                intent.putExtra("packageName", app.getPackageName());

                UiUtils.startActivity(intent);
            }
        });


        // 第二个参数 慢慢滑动的时候是否加载图片 false  加载   true 不加载
        //  第三个参数  飞速滑动的时候是否加载图片  true 不加载
        list_view.setOnScrollListener(new PauseOnScrollListener(utils, false, true));
        utils.configDefaultLoadingImage(R.drawable.ic_default);  // 设置如果图片加载中显示的图片
        utils.configDefaultLoadFailedImage(R.drawable.ic_default);// 加载失败显示的图片
        return list_view;
    }

//    class MyAdapter extends DefaultAdapter<AppInfo.AppInfoList> {
//
//
//        public MyAdapter(List<AppInfo.AppInfoList> appInfoLists) {
//            super(appInfoLists);
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder holder;
//            if (convertView == null) {
//                convertView = View.inflate(UiUtils.getContext(), R.layout.item_home, null);
//                holder = new ViewHolder();
//                holder.item_icon = (ImageView) convertView.findViewById(R.id.item_icon);//图标
//                holder.item_title = (TextView) convertView.findViewById(R.id.item_title);//名称
//                holder.item_rating = (RatingBar) convertView.findViewById(R.id.item_rating);
//                holder.item_size = (TextView) convertView.findViewById(R.id.item_size);//大小
//                holder.item_bottom = (TextView) convertView.findViewById(R.id.item_bottom);//详细
//                holder.action_txt = (TextView) convertView.findViewById(R.id.action_txt);//下载
//                convertView.setTag(holder);
//            } else {
//                holder = (ViewHolder) convertView.getTag();
//            }
//            AppInfo.AppInfoList appInfoList = mDatas.get(position);
//            holder.item_title.setText(appInfoList.name);
//            holder.item_size.setText(Formatter.formatShortFileSize(UiUtils.getContext(), appInfoList.size));
//            holder.item_bottom.setText(appInfoList.des);
//            utils.display(holder.item_icon, HttpHelper.URL + "image?name=" + appInfoList.iconUrl);
//            return convertView;
//        }
//
//        @Override
//        protected BaseHolder<AppInfo.AppInfoList> getHolder() {
//            return null;
//        }
//
//        @Override
//        protected List<AppInfo.AppInfoList> onload() {
//            return null;
//        }
//    }
//
//    static class ViewHolder {
//        ImageView item_icon;
//        TextView item_title;
//        RatingBar item_rating;
//        TextView item_size;
//        TextView item_bottom;
//        TextView action_txt;
//    }

    public LoadingPage.LoadResult load() {
        HomeProtocol protocol = new HomeProtocol();
        mDatas = protocol.load(0);
        pictures = protocol.getPictures();
        return checkData(mDatas);
    }

}
