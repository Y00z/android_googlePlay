package com.itheima.googleplay.protocol;

import com.itheima.googleplay.bean.App;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Yooz on 2016/3/21.
 */
public class DetailProtocol extends BaseProtocol<App> {

    private String packageName;

    public DetailProtocol(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public App parseJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            long id = jsonObject.getLong("id");
            String name = jsonObject.getString("name");
            String author = jsonObject.getString("author");
            String date = jsonObject.getString("date");
            String des = jsonObject.getString("des");
            String downloadNum = jsonObject.getString("downloadNum");
            String downloadUrl = jsonObject.getString("downloadUrl");
            String iconUrl = jsonObject.getString("iconUrl");
            String packageName = jsonObject.getString("packageName");
            float stars = Float.parseFloat(jsonObject.getString("stars"));
            String version = jsonObject.getString("version");
            long size = jsonObject.getLong("size");

            ArrayList<String> screen = new ArrayList<String>();
            JSONArray JsonScreen = jsonObject.getJSONArray("screen");
            for (int i = 0; i < JsonScreen.length(); i++) {
                screen.add(JsonScreen.getString(i));
            }

            ArrayList<String> safeDes = new ArrayList<String>();
            ArrayList<Integer> safeDesColor = new ArrayList<Integer>();
            ArrayList<String> safeDesUrl = new ArrayList<String>();
            ArrayList<String> safeUrl = new ArrayList<String>();
            JSONArray safe = jsonObject.getJSONArray("safe");
            for (int i = 0; i < safe.length(); i++) {
                JSONObject jsonObject1 = safe.getJSONObject(i);
                safeDes.add(jsonObject1.getString("safeDes"));
                safeDesColor.add(jsonObject1.getInt("safeDesColor"));
                safeDesUrl.add(jsonObject1.getString("safeDesUrl"));
                safeUrl.add(jsonObject1.getString("safeUrl"));
            }

            App appInfo = new App(id, name, packageName, iconUrl,
                    stars, size, downloadUrl, des, downloadNum, version, date,
                    author, screen, safeUrl, safeDesUrl, safeDes, safeDesColor);

            System.out.println("appinfo" + appInfo);
            return appInfo;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getKey() {
        return "detail";
    }

    public String getParams() {
        return "&packageName=" + packageName;
    }
}
