package com.itheima.googleplay.bean;

import com.itheima.googleplay.manager.DownloadManager;

/**
 * Created by Yooz on 2016/3/30.
 */
public class DownloadInfo {
    public String packageName;
    public long appSize;
    public long currentSize;
    public int state	= DownloadManager.STATE_UNDOWNLOAD;;
    public String url;
    public String path;
    public Runnable task;


    @Override
    public String toString() {
        return "DownloadInfo{" +
                "packageName='" + packageName + '\'' +
                ", appSize=" + appSize +
                ", currentSize=" + currentSize +
                ", state=" + state +
                ", url='" + url + '\'' +
                ", path='" + path + '\'' +
                ", task=" + task +
                '}';
    }
}
