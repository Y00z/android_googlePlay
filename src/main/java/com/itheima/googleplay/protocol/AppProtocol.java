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
public class AppProtocol extends BaseProtocol<List<App>> {
    @Override
    public List<App> parseJson(String json) {
        ArrayList<App> lists = new ArrayList<App>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject object=jsonArray.getJSONObject(i);
                long id=object.getLong("id");
                String name=object.getString("name");
                String packageName=object.getString("packageName");
                String iconUrl = object.getString("iconUrl");
                float stars=Float.parseFloat(object.getString("stars"));
                long size=object.getLong("size");
                String downloadUrl = object.getString("downloadUrl");
                String des = object.getString("des");
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
        return "app";
    }
}
