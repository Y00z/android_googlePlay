package com.itheima.googleplay.holder;

import android.view.View;
import android.widget.Button;

import com.itheima.googleplay.R;
import com.itheima.googleplay.bean.App;
import com.itheima.googleplay.bean.DownloadInfo;
import com.itheima.googleplay.manager.DownloadManager;
import com.itheima.googleplay.tools.CommonUtils;
import com.itheima.googleplay.tools.UiUtils;
import com.itheima.googleplay.view.ProgressButton;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;

/**
 * Created by Yooz on 2016/3/23.
 */

public class DetailBottomHolder extends BaseHolder<App> implements View.OnClickListener, DownloadManager.DownloadObserver {

    @ViewInject(R.id.bottom_favorites)
    private Button bottom_favorites;

    @ViewInject(R.id.bottom_share)
    private Button bottom_share;

    @ViewInject(R.id.progress_btn)
    private ProgressButton progress_btn;

    private App mData;

    @Override
    public void refreshView(App app) {
        mData = app;
        DownloadInfo downloadInfo = DownloadManager.getInstance().getDownloadInfo(app);
        refreshProgressBtnUi(downloadInfo);
    }

    @Override
    public View initView() {
        View view = View.inflate(UiUtils.getContext(), R.layout.detail_bottom, null);
        ViewUtils.inject(this, view);
        bottom_favorites.setOnClickListener(this);
        bottom_share.setOnClickListener(this);
        progress_btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bottom_favorites:
                break;
            case R.id.bottom_share:
                break;
            case R.id.progress_btn:
                DownloadInfo downloadInfo = DownloadManager.getInstance().getDownloadInfo(mData);
                switch (downloadInfo.state) {
                    case DownloadManager.STATE_UNDOWNLOAD:
                        download(downloadInfo);
                        break;
                    case DownloadManager.STATE_DOWNLOADING:
                        pauseDownLoad(downloadInfo);
                        break;
                    case DownloadManager.STATE_PAUSEDOWNLOAD:
                        download(downloadInfo);
                        break;
                    case DownloadManager.STATE_WAITINGDOWNLOAD:
                        cancelDownLoad(downloadInfo);
                        break;
                    case DownloadManager.STATE_DOWNLOADFAILED:
                        download(downloadInfo);
                        break;
                    case DownloadManager.STATE_DOWNLOADED:
                        installApk(downloadInfo);
                        break;
                    case DownloadManager.STATE_INSTALLED:
                        openApk(downloadInfo);
                        break;
                }
                break;

        }
    }

    private void cancelDownLoad(DownloadInfo downloadInfo) {
        DownloadManager.getInstance().cancel(downloadInfo);
    }

    private void pauseDownLoad(DownloadInfo downloadInfo) {
        DownloadManager.getInstance().pause(downloadInfo);
    }

    private void installApk(DownloadInfo downloadInfo) {
        File path = new File(downloadInfo.path);
        CommonUtils.installApp(UiUtils.getContext(), path);
    }

    private void openApk(DownloadInfo downloadInfo) {
        CommonUtils.openApp(UiUtils.getContext(), downloadInfo.packageName);
    }

    private void download(DownloadInfo downloadInfo) {

//        new AsyncTask<Void, Integer, Void>() {
//            int progress;
//
//            @Override
//            protected Void doInBackground(Void... params) {
//                while (true) {
//                    SystemClock.sleep(100);
//                    if (progress >= 100) {
//                        break;
//                    }
//                    progress++;
//                    publishProgress(progress);
//                }
//                return null;
//            }
//
//            @Override
//            protected void onProgressUpdate(Integer... values) {
//                int value = values[0];
//                progress_btn.setmProgress(value);
//                progress_btn.setText(value + "%");
//            }
//        }.execute();
//
//        File file = new File(FileUtils.getDownLoad().getAbsolutePath(), mData.getName() + ".apk");
//        String savaFile = file.getAbsolutePath();
//        DownloadInfo downloadInfo = new DownloadInfo();
//        downloadInfo.path = savaFile;
//        downloadInfo.url = mData.getDownloadUrl();
//        downloadInfo.packageName = mData.getPackageName();
        DownloadManager.getInstance().download(downloadInfo);

    }

    public void refreshProgressBtnUi(DownloadInfo downloadInfo) {
        switch (downloadInfo.state) {
            case DownloadManager.STATE_UNDOWNLOAD:
                progress_btn.setText("下载");
                break;
            case DownloadManager.STATE_DOWNLOADING:
                progress_btn.setProgressEnable(true);
                progress_btn.setmMax(downloadInfo.appSize);
                progress_btn.setmProgress(downloadInfo.currentSize);
//                (mProgress *1.0f / mMax * getMeasuredWidth() + .5f)
                int cureent = (int) (downloadInfo.currentSize * 100.f / downloadInfo.appSize + .5f);
                progress_btn.setText(cureent + "%");
                break;
            case DownloadManager.STATE_PAUSEDOWNLOAD:
                progress_btn.setText("继续下载");
                break;
            case DownloadManager.STATE_WAITINGDOWNLOAD:
                progress_btn.setText("等待下载");
                break;
            case DownloadManager.STATE_DOWNLOADFAILED:
                progress_btn.setText("重试");
                break;
            case DownloadManager.STATE_DOWNLOADED:
                progress_btn.setProgressEnable(false);
                progress_btn.setText("安装");
                break;
            case DownloadManager.STATE_INSTALLED:
                progress_btn.setProgressEnable(false);
                progress_btn.setText("打开");
                break;
        }
    }


    @Override
    public void onDownloadInfoChange(final DownloadInfo info) {
        //过滤
        if (!info.packageName.equals(mData.getPackageName())) {
            return;
        }
        UiUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                refreshProgressBtnUi(info);
            }
        });
    }

    //返回刷新界面
    public void addObserverAndRefresh() {
        DownloadManager.getInstance().addObserver(this);
        DownloadInfo downloadInfo = DownloadManager.getInstance().getDownloadInfo(mData);
        DownloadManager.getInstance().notifyObservers(downloadInfo);
    }
}
