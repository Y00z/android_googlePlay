package com.itheima.googleplay.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by Yooz on 2016/3/31.
 */
public class RecommendProtocol extends BaseProtocol<List<String>> {

    @Override
    public List<String> parseJson(String json) {
        Gson gson = new Gson();
        return  gson.fromJson(json , new TypeToken<List<String>>(){}.getType());
    }

    @Override
    public String getKey() {
        return "recommend";
    }
}
