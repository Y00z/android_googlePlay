package com.itheima.googleplay.protocol;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yooz on 2016/3/26.
 */
public class HotProtocol extends BaseProtocol<List<String>> {
    @Override
    public List<String> parseJson(String json) {
        System.out.println("lists:" + json);
        ArrayList<String> lists = new ArrayList<String>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                String string = jsonArray.getString(i);
                lists.add(string);
            }
            System.out.println("lists:" + lists);
            return lists;
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("lists:没找到" );
            return null;
        }
    }

    @Override
    public String getKey() {
        return "hot";
    }
}
