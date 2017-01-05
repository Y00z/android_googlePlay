package com.itheima.googleplay.protocol;

import com.itheima.googleplay.bean.App;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yooz on 2016/3/13.
 */
public class HomeProtocol extends BaseProtocol<List<App>> {

    private List<String> picture;
    @Override
    public List<App> parseJson(String json) {
        ArrayList<App> lists = new ArrayList<App>();
        picture = new ArrayList<String>();
        try {
            JSONObject jsonObject=new JSONObject(json);
            JSONArray jsonArray2 = jsonObject.getJSONArray("picture");
            for(int i=0 ; i<jsonArray2.length() ; i++){
                String string = jsonArray2.getString(i);
                picture.add(string);
            }


            JSONArray jsonArray = jsonObject.getJSONArray("list");
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

    public List<String> getPictures() {
        return picture;
    }

    @Override
    public String getKey() {
        return "home";
    }
}
