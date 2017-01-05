package com.itheima.googleplay.protocol;

import com.itheima.googleplay.bean.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Yooz on 2016/3/19.
 */
public class UserProtocol extends BaseProtocol<UserInfo> {
    @Override
    public UserInfo parseJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            String name = jsonObject.getString("name");
            String email = jsonObject.getString("email");
            String url = jsonObject.getString("url");
            UserInfo userInfo = new UserInfo(name, url, email);
            return userInfo;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public String getKey() {
        return "user";
    }
}
