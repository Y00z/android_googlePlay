package com.itheima.googleplay.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.itheima.googleplay.adapter.DefaultAdapter;
import com.itheima.googleplay.bean.CategoryInfo;
import com.itheima.googleplay.holder.BaseHolder;
import com.itheima.googleplay.holder.CategoryHolder;
import com.itheima.googleplay.holder.CategoryTitleHolder;
import com.itheima.googleplay.protocol.CategoryProtocol;
import com.itheima.googleplay.tools.UiUtils;
import com.itheima.googleplay.view.BaseListView;
import com.itheima.googleplay.view.LoadingPage;

import java.util.List;

public class CategoryFragment extends BaseFragment {


    private List<CategoryInfo> data;
    public static int ITEM_TITLE = 2;

    @Override
    public View createSuccessView() {
        BaseListView listview = new BaseListView(UiUtils.getContext());
        listview.setAdapter(new CategoryAdapter(data, listview));
        return listview;
    }

    private class CategoryAdapter extends DefaultAdapter<CategoryInfo> {
        private int position;

        public CategoryAdapter(List<CategoryInfo> categoryInfos, ListView lv) {
            super(categoryInfos, lv);
        }

        @Override
        protected BaseHolder<CategoryInfo> getHolder() {
            if (!data.get(position).isTitle()) {
                return new CategoryHolder();
            } else {
                return new CategoryTitleHolder();
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            this.position = position;
            return super.getView(position, convertView, parent);
        }

        @Override
        protected List<CategoryInfo> onload() {
            return null;
        }

        @Override
        public boolean hasMore() {
            return false;
        }

        @Override
        public int getInnerItemViewType(int position) {
            if (data.get(position).isTitle()) {
                return ITEM_TITLE;
            } else {
                return super.getInnerItemViewType(position);
            }
        }

        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount() + 1;
        }
    }

    @Override
    protected LoadingPage.LoadResult load() {
        CategoryProtocol categoryProtocol = new CategoryProtocol();
        data = categoryProtocol.load(0);
        return checkData(data);
    }
}
