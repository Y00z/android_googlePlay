package com.itheima.googleplay.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itheima.googleplay.R;
import com.itheima.googleplay.tools.FileUtils;
import com.itheima.googleplay.tools.UiUtils;
import com.itheima.googleplay.tools.ViewUtils;
import com.itheima.googleplay.view.LoadingPage;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by Yooz on 2016/3/12.
 */
public abstract class BaseFragment extends Fragment {


    private LoadingPage loadingPage;
    public BitmapUtils utils;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        utils = new BitmapUtils(UiUtils.getContext(), FileUtils.getCacheDir().getAbsolutePath(), 0.3f);
        utils.configDefaultLoadingImage(R.drawable.ic_default);

        if (loadingPage == null) {  // 之前的frameLayout 已经记录了一个爹了  爹是之前的ViewPager
            loadingPage = new LoadingPage(getActivity()) {
                @Override
                public View createSuccessView()              {
                    return BaseFragment.this.createSuccessView();
                }

                @Override
                protected LoadResult load() {
                    return BaseFragment.this.load();
                }
            };
        } else {
            ViewUtils.removeParent(loadingPage);// 移除frameLayout之前的爹
        }
//        show(); 根据服务器的数据 切换状态

        return loadingPage;   //  拿到当前viewPager 添加这个framelayout
    }


    public abstract View createSuccessView();

    protected abstract LoadingPage.LoadResult load();

    public void show() {
        if (loadingPage != null) {
            loadingPage.show();
        }
    }

    public LoadingPage.LoadResult checkData(List load) {
        if (load == null) {
            return LoadingPage.LoadResult.error;
        } else {
            if (load.size() == 0) {
                return LoadingPage.LoadResult.empty;
            } else {
                return LoadingPage.LoadResult.success;
            }
        }
    }


}
