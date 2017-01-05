package com.itheima.googleplay.protocol;

import com.itheima.googleplay.bean.CategoryInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yooz on 2016/3/24.
 */
public class CategoryProtocol extends BaseProtocol<List<CategoryInfo>> {
    @Override
    public List<CategoryInfo> parseJson(String json) {
        ArrayList<CategoryInfo> info = new ArrayList<CategoryInfo>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("title");
                CategoryInfo categoryInfo1 = new CategoryInfo();
                categoryInfo1.setTitle(title);
                categoryInfo1.setIstitle(true);
                info.add(categoryInfo1);

                JSONArray infos = jsonObject.getJSONArray("infos");
                for (int j = 0; j < infos.length(); j++) {
                    JSONObject jsonObject1 = infos.getJSONObject(j);
                    String name1 = jsonObject1.getString("name1");
                    String name2 = jsonObject1.getString("name2");
                    String name3 = jsonObject1.getString("name3");

                    String url1 = jsonObject1.getString("url1");
                    String url2 = jsonObject1.getString("url2");
                    String url3 = jsonObject1.getString("url3");


                    CategoryInfo categoryInfo = new CategoryInfo(title, name1, name2, name3, url1, url2, url3,false);
                    info.add(categoryInfo);
                }
            }
            return info;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getKey() {
        return "category";
    }
}
