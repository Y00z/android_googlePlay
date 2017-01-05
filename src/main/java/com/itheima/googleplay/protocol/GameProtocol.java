package com.itheima.googleplay.protocol;

import com.itheima.googleplay.bean.App;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yooz on 2016/3/16.
 */
public class GameProtocol extends BaseProtocol<List<App>> {
    @Override
    public List<App> parseJson(String json) {
        ArrayList<App> lists = new ArrayList<App>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String des = jsonObject.getString("des");
                String downloadUrl = jsonObject.getString("downloadUrl");
                String iconUrl = jsonObject.getString("iconUrl");
                long id = jsonObject.getLong("id");
                String name = jsonObject.getString("name");
                String packageName = jsonObject.getString("packageName");
                long size = jsonObject.getLong("size");
                float stars = Float.parseFloat(jsonObject.getString("stars"));
                App info=new App(id, name, packageName, iconUrl, stars, size, downloadUrl, des);
                lists.add(info);
            }
            return lists;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getKey() {
        return "game";
    }
}
