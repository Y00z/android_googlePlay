package com.itheima.googleplay.bean;

/**
 * Created by Yooz on 2016/3/19.
 */
public class UserInfo {
    private String name;
    private String url;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


    public UserInfo(String name, String url, String email) {
        this.name = name;
        this.url = url;
        this.email = email;
    }
}
