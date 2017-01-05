package com.itheima.googleplay.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.itheima.googleplay.DetailActivity;
import com.itheima.googleplay.adapter.ListBaseAdapter;
import com.itheima.googleplay.bean.App;
import com.itheima.googleplay.protocol.AppProtocol;
import com.itheima.googleplay.tools.UiUtils;
import com.itheima.googleplay.view.BaseListView;
import com.itheima.googleplay.view.LoadingPage;

import java.util.List;

public class AppFragment extends BaseFragment {

    private List<App> mDatas;

    @Override
    public View createSuccessView() {
        BaseListView listview = new BaseListView(UiUtils.getContext());
        listview.setAdapter(new ListBaseAdapter(mDatas , listview) {
            @Override
            protected List<App> onload() {
                AppProtocol protocol=new AppProtocol();
                List<App> load = protocol.load(mDatas.size());
                datas.addAll(load);
                return load;
            }

            @Override
            public void onInnerItemClick(int position) {
                super.onInnerItemClick(position);
                Toast.makeText(UiUtils.getContext(), "position:" + position, Toast.LENGTH_SHORT).show();

                App app = mDatas.get(position);
                Intent intent = new Intent(UiUtils.getContext() , DetailActivity.class);
                intent.putExtra("packageName" , app.getPackageName());
                UiUtils.startActivity(intent);
            }
        });
        return listview;
    }
    
//    private class MyAdapter extends BaseAdapter {
//        @Override
//        public int getCount() {
//            return mDatas.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return mDatas.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
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
//            App appInfoList = mDatas.get(position);
//            holder.item_title.setText(appInfoList.getName());
//            holder.item_size.setText(Formatter.formatShortFileSize(UiUtils.getContext(), appInfoList.getSize()));
//            holder.item_bottom.setText(appInfoList.getDes());
//            utils.display(holder.item_icon, HttpHelper.URL + "image?name=" + appInfoList.getIconUrl());
//            return convertView;
//        }
//    }
//    static class ViewHolder {
//        ImageView item_icon;
//        TextView item_title;
//        RatingBar item_rating;
//        TextView item_size;
//        TextView item_bottom;
//        TextView action_txt;
//    }
    

    @Override
    protected LoadingPage.LoadResult load() {
        AppProtocol appProtocol = new AppProtocol();
        mDatas = appProtocol.load(0);
        return checkData(mDatas);
    }

}
