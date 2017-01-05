package com.itheima.googleplay.fragment;

import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.itheima.googleplay.adapter.DefaultAdapter;
import com.itheima.googleplay.bean.SubjectInfo;
import com.itheima.googleplay.holder.BaseHolder;
import com.itheima.googleplay.holder.SubjectHolder;
import com.itheima.googleplay.protocol.SubjectProtocol;
import com.itheima.googleplay.tools.UiUtils;
import com.itheima.googleplay.view.BaseListView;
import com.itheima.googleplay.view.LoadingPage;

import java.util.List;

public class SubjectFragment extends BaseFragment {

    private List<SubjectInfo> mData;

    @Override
    public View createSuccessView() {
        BaseListView listView = new BaseListView(UiUtils.getContext());
        listView.setAdapter(new SubjectAdapter(mData, listView) {
            @Override
            protected List<SubjectInfo> onload() {
                SubjectProtocol subjectProtocol = new SubjectProtocol();
                List<SubjectInfo> load = subjectProtocol.load(mData.size());
                mData.addAll(load);
                return load;
            }

            @Override
            public void onInnerItemClick(int position) {
                super.onInnerItemClick(position);
                Toast.makeText(UiUtils.getContext(), mData.get(position).getDes(), 0).show();

            }
        });
        return listView;
    }

    public abstract class SubjectAdapter extends DefaultAdapter<SubjectInfo> {

        public SubjectAdapter(List<SubjectInfo> datas, ListView lv) {
            super(datas, lv);
        }

        @Override
        protected BaseHolder getHolder() {
            return new SubjectHolder();
        }

        @Override
        protected List onload(){
            SubjectProtocol subjectProtocol = new SubjectProtocol();
            List<SubjectInfo> load = subjectProtocol.load(mData.size());
            mData.addAll(load);
            return load;
        }
    }


    //    public class MyAdapter extends BaseAdapter {
//
//        @Override
//        public int getCount() {
//            return mData.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return mData.get(position);
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
//                convertView = View.inflate(UiUtils.getContext(), R.layout.item_subject, null);
//                holder = new ViewHolder();
//                holder.item_icon = (ImageView) convertView.findViewById(R.id.item_icon);
//                holder.item_txt = (TextView) convertView.findViewById(R.id.item_txt);
//                convertView.setTag(holder);
//            } else {
//                holder = (ViewHolder) convertView.getTag();
//            }
//            SubjectInfo subjectInfo = mData.get(position);
//            holder.item_txt.setText(subjectInfo.des);
//            utils.display(holder.item_icon, HttpHelper.URL + "image?name=" + subjectInfo.url);
//
//            return convertView;
//        }
//    }
//
//    static class ViewHolder {
//        ImageView item_icon;
//        TextView item_txt;
//    }

    @Override
    protected LoadingPage.LoadResult load() {
        SubjectProtocol subjectProtocol = new SubjectProtocol();
        mData = subjectProtocol.load(0);

        return checkData(mData);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
