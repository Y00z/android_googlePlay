package com.itheima.googleplay.bean;

/**
 * Created by Yooz on 2016/3/14.
 */
public class SubjectInfo {
    public String des;
    public String url;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public SubjectInfo(String des, String url) {
        this.des = des;
        this.url = url;
    }


    @Override
    public String toString() {
        return "SubjectInfo{" +
                "des='" + des + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
