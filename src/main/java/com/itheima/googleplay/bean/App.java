package com.itheima.googleplay.bean;

import java.util.ArrayList;

/**
 * Created by Yooz on 2016/3/16.
 */
public class App {
    private long id;
    private String name;
    private String packageName;
    private String iconUrl;
    private float stars;
    private long size;
    private String downloadUrl;
    private String des;

//    ----------------------------------

    private String author;
    private String date;
    private String version;
    private String downloadNum;

    private ArrayList<String> screen;


    private ArrayList<String> safeDes;
    private ArrayList<Integer> safeDesColor;
    private ArrayList<String> safeDesUrl;
    private ArrayList<String> safeUrl;

    public ArrayList<String> getScreen() {
        return screen;
    }

    public void setScreen(ArrayList<String> screen) {
        this.screen = screen;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(String downloadNum) {
        this.downloadNum = downloadNum;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public App(long id, String name, String packageName, String iconUrl,
               float stars, long size, String downloadUrl, String des,
               String downloadNum, String version, String date, String author,
               ArrayList<String> screen, ArrayList<String> safeUrl, ArrayList<String> safeDesUrl,
               ArrayList<String> safeDes, ArrayList<Integer> safeDesColor) {
        super();
        this.id = id;
        this.name = name;
        this.packageName = packageName;
        this.iconUrl = iconUrl;
        this.stars = stars;
        this.size = size;
        this.downloadUrl = downloadUrl;
        this.des = des;
        this.downloadNum = downloadNum;
        this.version = version;
        this.date = date;
        this.author = author;
        this.screen = screen;
        this.safeUrl = safeUrl;
        this.safeDesUrl = safeDesUrl;
        this.safeDes = safeDes;
        this.safeDesColor = safeDesColor;
    }

    public App(long id, String name, String packageName, String iconUrl,
               float stars, long size, String downloadUrl, String des) {
        super();
        this.id = id;
        this.name = name;
        this.packageName = packageName;
        this.iconUrl = iconUrl;
        this.stars = stars;
        this.size = size;
        this.downloadUrl = downloadUrl;
        this.des = des;
    }


    public ArrayList<String> getSafeDes() {
        return safeDes;
    }

    public void setSafeDes(ArrayList<String> safeDes) {
        this.safeDes = safeDes;
    }

    public ArrayList<Integer> getSafeDesColor() {
        return safeDesColor;
    }

    public void setSafeDesColor(ArrayList<Integer> safeDesColor) {
        this.safeDesColor = safeDesColor;
    }

    public ArrayList<String> getSafeDesUrl() {
        return safeDesUrl;
    }

    public void setSafeDesUrl(ArrayList<String> safeDesUrl) {
        this.safeDesUrl = safeDesUrl;
    }

    public ArrayList<String> getSafeUrl() {
        return safeUrl;
    }

    public void setSafeUrl(ArrayList<String> safeUrl) {
        this.safeUrl = safeUrl;
    }


    @Override
    public String toString() {
        return "App{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", packageName='" + packageName + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", stars=" + stars +
                ", size=" + size +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", des='" + des + '\'' +
                ", author='" + author + '\'' +
                ", date='" + date + '\'' +
                ", version='" + version + '\'' +
                ", downloadNum='" + downloadNum + '\'' +
                ", screen=" + screen +
                ", safeDes=" + safeDes +
                ", safeDesColor=" + safeDesColor +
                ", safeDesUrl=" + safeDesUrl +
                ", safeUrl=" + safeUrl +
                '}';
    }
}
