package com.itheima.googleplay.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.itheima.googleplay.R;
import com.itheima.googleplay.bean.App;
import com.itheima.googleplay.bean.DownloadInfo;
import com.itheima.googleplay.http.HttpHelper;
import com.itheima.googleplay.manager.DownloadManager;
import com.itheima.googleplay.tools.CommonUtils;
import com.itheima.googleplay.tools.UiUtils;
import com.itheima.googleplay.view.CycloProgressButton;

import java.io.File;

public class ListBaseHolder extends BaseHolder<App> implements DownloadManager.DownloadObserver, View.OnClickListener {
    ImageView item_icon;
    TextView item_title, item_size, item_bottom;
    RatingBar item_rating;
    private CycloProgressButton cyclo_progress;
    private App mData;


    @Override
    public View initView() {
        View contentView = View.inflate(UiUtils.getContext(), R.layout.item_home, null);
        this.item_icon = (ImageView) contentView.findViewById(R.id.item_icon);
        this.item_title = (TextView) contentView.findViewById(R.id.item_title);
        this.item_size = (TextView) contentView.findViewById(R.id.item_size);
        this.item_bottom = (TextView) contentView.findViewById(R.id.item_bottom);
        this.item_rating = (RatingBar) contentView.findViewById(R.id.item_rating);
        this.cyclo_progress = (CycloProgressButton) contentView.findViewById(R.id.cyclo_progress);
        return contentView;
    }

    public void refreshView(App data) {
        mData = data;
        this.item_title.setText(data.getName());// 设置应用程序的名字
        String size = Formatter.formatFileSize(UiUtils.getContext(), data.getSize());
        this.item_size.setText(size);
        this.item_bottom.setText(data.getDes());
        float stars = data.getStars();
        this.item_rating.setRating(stars); // 设置ratingBar的值
        String iconUrl = data.getIconUrl();  //http://127.0.0.1:8090/image?name=app/com.youyuan.yyhl/icon.jpg
        // 显示图片的控件
        bitmapUtils.display(this.item_icon, HttpHelper.URL + "image?name=" + iconUrl);
        cyclo_progress.setOnClickListener(this);
        DownloadInfo downloadInfo = DownloadManager.getInstance().getDownloadInfo(data);
        refreshProgressViewUI(downloadInfo);
    }

    private void refreshProgressViewUI(DownloadInfo downloadInfo) {
        switch (downloadInfo.state) {
            case DownloadManager.STATE_UNDOWNLOAD:
                cyclo_progress.setTv_note("下载");
                cyclo_progress.setIv_icon(R.drawable.ic_download);
                break;
            case DownloadManager.STATE_DOWNLOADING:
                cyclo_progress.setIsEnable(true);
                cyclo_progress.setmMax(downloadInfo.appSize);
                cyclo_progress.setmProgress(downloadInfo.currentSize);
                int cureent = (int) (downloadInfo.currentSize * 100.f / downloadInfo.appSize + .5f);
                cyclo_progress.setTv_note(cureent + "%");
                cyclo_progress.setIv_icon(R.drawable.ic_pause);
                break;
            case DownloadManager.STATE_PAUSEDOWNLOAD:
                cyclo_progress.setTv_note("继续下载");
                cyclo_progress.setIv_icon(R.drawable.ic_resume);
                break;
            case DownloadManager.STATE_WAITINGDOWNLOAD:
                cyclo_progress.setTv_note("等待下载");
                cyclo_progress.setIv_icon(R.drawable.ic_pause);
                break;
            case DownloadManager.STATE_DOWNLOADFAILED:
                cyclo_progress.setTv_note("重试");
                cyclo_progress.setIv_icon(R.drawable.ic_redownload);
                break;
            case DownloadManager.STATE_DOWNLOADED:
                cyclo_progress.setTv_note("安装");
                cyclo_progress.setIsEnable(false);
                cyclo_progress.setIv_icon(R.drawable.ic_install);
                break;
            case DownloadManager.STATE_INSTALLED:
                cyclo_progress.setTv_note("打开");
                cyclo_progress.setIv_icon(R.drawable.ic_install);
                break;
        }
    }


    /*=============== 收到数据改变,更新ui ===============*/
    @Override
    public void onDownloadInfoChange(final DownloadInfo info) {
        if (!info.packageName.equals(mData.getPackageName())) {
            return;
        }
        UiUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                refreshProgressViewUI(info);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cyclo_progress:
                DownloadInfo downloadInfo = DownloadManager.getInstance().getDownloadInfo(mData);
                switch (downloadInfo.state) {
                    case DownloadManager.STATE_UNDOWNLOAD:
                        System.out.println("去下载");
                        doDownload(downloadInfo);
                        break;
                    case DownloadManager.STATE_DOWNLOADING:
                        System.out.println("暂停下载");
                        pausedDownload(downloadInfo);
                        break;
                    case DownloadManager.STATE_PAUSEDOWNLOAD:
                        System.out.println("继续下载");
                        doDownload(downloadInfo);
                        break;
                    case DownloadManager.STATE_WAITINGDOWNLOAD:
                        System.out.println("取消下载");
                        canceDownload(downloadInfo);
                        break;
                    case DownloadManager.STATE_DOWNLOADFAILED:
                        System.out.println("重新下载");
                        doDownload(downloadInfo);
                        break;
                    case DownloadManager.STATE_DOWNLOADED:
                        System.out.println("安装");
                        installAPK(downloadInfo);
                        break;
                    case DownloadManager.STATE_INSTALLED:
                        System.out.println("运行");
                        startAPK(downloadInfo);
                        break;
                }
                break;
        }
    }

    //打开程序
    private void startAPK(DownloadInfo downloadInfo) {
        CommonUtils.openApp(UiUtils.getContext(), downloadInfo.packageName);
    }

    //安装
    private void installAPK(DownloadInfo downloadInfo) {
        CommonUtils.installApp(UiUtils.getContext(), new File(downloadInfo.path));
    }

    //取消下载
    private void canceDownload(DownloadInfo downloadInfo) {
        DownloadManager.getInstance().cancel(downloadInfo);
    }

    //暂停
    private void pausedDownload(DownloadInfo downloadInfo) {
        DownloadManager.getInstance().pause(downloadInfo);
    }

    //去下载
    private void doDownload(DownloadInfo downloadInfo) {
        DownloadManager.getInstance().download(downloadInfo);
    }
}
