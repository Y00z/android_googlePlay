package com.itheima.googleplay.manager;

import com.itheima.googleplay.bean.App;
import com.itheima.googleplay.bean.DownloadInfo;
import com.itheima.googleplay.http.HttpHelper;
import com.itheima.googleplay.tools.CommonUtils;
import com.itheima.googleplay.tools.FileUtils;
import com.itheima.googleplay.tools.IOUtils;
import com.itheima.googleplay.tools.UiUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Yooz on 2016/3/30.
 */
public class DownloadManager {

    public static final int STATE_UNDOWNLOAD = 0;        // 未下载
    public static final int STATE_DOWNLOADING = 1;       // 下载中
    public static final int STATE_PAUSEDOWNLOAD = 2;     // 暂停下载
    public static final int STATE_WAITINGDOWNLOAD = 3;   // 等待下载
    public static final int STATE_DOWNLOADFAILED = 4;    // 下载失败
    public static final int STATE_DOWNLOADED = 5;        // 下载完成
    public static final int STATE_INSTALLED = 6;         // 已安装


    public static DownloadManager instance;
    public HashMap<String, DownloadInfo> sMaps = new HashMap<String, DownloadInfo>();

    private DownloadManager() {
    }

    public static DownloadManager getInstance() {
        if (instance == null) {
            synchronized (DownloadManager.class) {
                if (instance == null) {
                    instance = new DownloadManager();
                }
            }
        }
        return instance;
    }

    public void download(DownloadInfo data) {
        sMaps.put(data.packageName, data);
        data.state = STATE_UNDOWNLOAD;
        notifyObservers(data);

        data.state = STATE_WAITINGDOWNLOAD;
        notifyObservers(data);
        DownloadTask task = new DownloadTask(data);
        data.task = task;
        ThreadManager.createShortPool().execute(task);
    }

    public DownloadInfo getDownloadInfo(App app) {

        //已安装
        if (CommonUtils.isInstalled(UiUtils.getContext(), app.getPackageName())) {
            DownloadInfo downloadInfo = generateDownLoadInfo(app);
            downloadInfo.state = STATE_INSTALLED;
            notifyObservers(downloadInfo);
            return downloadInfo;
        }

        //下载完成
        DownloadInfo downloadInfo = generateDownLoadInfo(app);
        File apkPath = new File(downloadInfo.path);
        if (apkPath.exists()) {
            if (apkPath.length() == app.getSize()) {
                downloadInfo.state = STATE_DOWNLOADED;
                notifyObservers(downloadInfo);
                return downloadInfo;
            }
        }


        /**
         下载中
         暂停下载
         等待下载
         下载失败
         */
        DownloadInfo downloadInfo1 = sMaps.get(app.getPackageName());
        if (downloadInfo1 != null) {
            return downloadInfo1;
        }


        //未下载
        DownloadInfo info = generateDownLoadInfo(app);
        info.state = STATE_UNDOWNLOAD;
        notifyObservers(info);
        return info;
    }

    private DownloadInfo generateDownLoadInfo(App app) {
        File downLoad = FileUtils.getDownLoad();
        File apkPath = new File(downLoad, app.getName() + ".apk");

        DownloadInfo downloadInfo = new DownloadInfo();

        downloadInfo.url = app.getDownloadUrl();
        downloadInfo.path = apkPath.getAbsolutePath();
        downloadInfo.packageName = app.getPackageName();
        downloadInfo.appSize = app.getSize();
        downloadInfo.currentSize = 0;
        return downloadInfo;
    }

    public void pause(DownloadInfo downloadInfo) {
        downloadInfo.state = STATE_PAUSEDOWNLOAD;
        notifyObservers(downloadInfo);
    }

    public void cancel(DownloadInfo downloadInfo) {
        Runnable task = downloadInfo.task;
        new ThreadManager.ThreadPoolProxy().cancel(task);

        downloadInfo.state = STATE_UNDOWNLOAD;
        notifyObservers(downloadInfo);
    }


    private class DownloadTask implements Runnable {
        private DownloadInfo info;

        public DownloadTask(DownloadInfo info) {
            this.info = info;
        }

        @Override
        public void run() {
            HttpUtils httpUtils = new HttpUtils();
            InputStream input = null;
            FileOutputStream out = null;
            try {
                info.state = STATE_DOWNLOADING;
                notifyObservers(info);

                long initRange = 0;
                File file = new File(info.path);
                if (file.exists()) {
                    initRange = file.length();
                }
                info.currentSize = initRange;

                RequestParams params = new RequestParams();
                params.addQueryStringParameter("name", info.url);
                params.addQueryStringParameter("range", initRange + "");
                ResponseStream responseStream = httpUtils.sendSync(HttpRequest.HttpMethod.GET, HttpHelper.DOWNLOADURL, params);
                if (responseStream.getStatusCode() == 200) {
                    input = responseStream.getBaseStream();
                    out = new FileOutputStream(info.path, true);
                    byte[] arr = new byte[1024];
                    int len;
                    boolean ispause = false;
                    while ((len = input.read(arr)) != -1) {
                        if (info.state == STATE_PAUSEDOWNLOAD) {
                            ispause = true;
                            break;
                        }
                        out.write(arr, 0, len);
                        info.currentSize += len;
                        info.state = STATE_DOWNLOADING;
                        notifyObservers(info);
                    }
                    if (ispause) {
                        info.state = STATE_PAUSEDOWNLOAD;
                        notifyObservers(info);
                    } else {
                        info.state = STATE_DOWNLOADED;
                        notifyObservers(info);
                    }
                } else {
                    info.state = STATE_DOWNLOADFAILED;
                    notifyObservers(info);
                }
            } catch (Exception e) {
                e.printStackTrace();
                info.state = STATE_DOWNLOADFAILED;
                notifyObservers(info);
            } finally {
                IOUtils.close(input);
                IOUtils.close(out);
            }
        }
    }

    public interface DownloadObserver {
        void onDownloadInfoChange(DownloadInfo info);
    }

    ArrayList<DownloadObserver> observers = new ArrayList<DownloadObserver>();

    public void addObserver(DownloadObserver observer) {
        if (observer == null) {
            throw new RuntimeException("观察者为空");
        }
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public synchronized void deleteObserver(DownloadObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(DownloadInfo info) {
        for (DownloadObserver observer : observers) {
            observer.onDownloadInfoChange(info);
        }
    }
}
