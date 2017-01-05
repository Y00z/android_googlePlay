package com.itheima.googleplay.protocol;

import com.itheima.googleplay.bean.SubjectInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yooz on 2016/3/14.
 */
public class SubjectProtocol extends BaseProtocol<List<SubjectInfo>> {

    @Override
    public List<SubjectInfo> parseJson(String json) {
        ArrayList<SubjectInfo> lists = new ArrayList<SubjectInfo>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String des = jsonObject.getString("des");
                String url = jsonObject.getString("url");
                SubjectInfo subjectInfo = new SubjectInfo(des,url);
                lists.add(subjectInfo);
            }
            return lists;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public String getKey() {
        return "subject";
    }
}
