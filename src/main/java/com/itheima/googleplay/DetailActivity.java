package com.itheima.googleplay;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import com.itheima.googleplay.bean.App;
import com.itheima.googleplay.holder.DetailBottomHolder;
import com.itheima.googleplay.holder.DetailDesHolder;
import com.itheima.googleplay.holder.DetailInfoHolder;
import com.itheima.googleplay.holder.DetailSafeHolder;
import com.itheima.googleplay.holder.DetailScrollHolder;
import com.itheima.googleplay.manager.DownloadManager;
import com.itheima.googleplay.protocol.DetailProtocol;
import com.itheima.googleplay.tools.UiUtils;
import com.itheima.googleplay.view.LoadingPage;

public class DetailActivity extends BaseActivity {
    private String packageName;
    private App mDatas;
    private DetailInfoHolder detailInfoHolder;
    private DetailBottomHolder detailBottomHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        packageName = getIntent().getStringExtra("packageName");
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initView() {
        LoadingPage loadingPage = new LoadingPage(this) {

            @Override
            public View createSuccessView() {
                return DetailActivity.this.createSuccessView();
            }

            @Override
            protected LoadResult load() {
                return DetailActivity.this.load();
            }
        };
        loadingPage.show();
        setContentView(loadingPage);
    }

    private FrameLayout bottom_layout, detail_info, detail_safe, detail_des;
    private HorizontalScrollView detail_screen;

    public View createSuccessView() {
        View view = View.inflate(UiUtils.getContext(), R.layout.activity_detail, null);

        detail_info = (FrameLayout) view.findViewById(R.id.detail_info);
        detailInfoHolder = new DetailInfoHolder();
        detailInfoHolder.setData(mDatas);
        detail_info.addView(detailInfoHolder.getContentView());

        detail_safe = (FrameLayout) view.findViewById(R.id.detail_safe);
        DetailSafeHolder detailSafeHolder = new DetailSafeHolder();
        detailSafeHolder.setData(mDatas);
        detail_safe.addView(detailSafeHolder.getContentView());

        detail_des = (FrameLayout) view.findViewById(R.id.detail_des);
        DetailDesHolder detailDesHolder = new DetailDesHolder();
        detailDesHolder.setData(mDatas);
        detail_des.addView(detailDesHolder.getContentView());

        detail_screen = (HorizontalScrollView) view.findViewById(R.id.detail_screen);
        DetailScrollHolder detailScrollHolder = new DetailScrollHolder();
        detailScrollHolder.setData(mDatas);
        detail_screen.addView(detailScrollHolder.getContentView());


        bottom_layout = (FrameLayout) view.findViewById(R.id.bottom_layout);
        detailBottomHolder = new DetailBottomHolder();
        detailBottomHolder.setData(mDatas);
        bottom_layout.addView(detailBottomHolder.getContentView());

        DownloadManager.getInstance().addObserver(detailBottomHolder);

        return view;
    }

    public LoadingPage.LoadResult load() {
        DetailProtocol detailProtocol = new DetailProtocol(packageName);
        mDatas = detailProtocol.load(0);
        if (mDatas == null) {
            return LoadingPage.LoadResult.error;
        } else {
            return LoadingPage.LoadResult.success;
        }
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onPause() {
        if (detailBottomHolder != null) {
            DownloadManager.getInstance().deleteObserver(detailBottomHolder);
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (detailBottomHolder != null) {
            DownloadManager.getInstance().addObserver(detailBottomHolder);
            detailBottomHolder.addObserverAndRefresh();
//            DownloadInfo downloadInfo = DownloadManager.getInstance().getDownloadInfo(mDatas);
//            detailBottomHolder.refreshProgressBtnUi(downloadInfo);
        }
        super.onResume();
    }
}
