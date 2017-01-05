package com.itheima.googleplay.protocol;

import com.itheima.googleplay.http.HttpHelper;
import com.itheima.googleplay.tools.FileUtils;
import com.lidroid.xutils.util.IOUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringWriter;

/**
 * Created by Yooz on 2016/3/14.
 */
public abstract class BaseProtocol<T> {


    public T load(int index) {
        String json = loadLocal(index);
        System.out.println("load" + json);
        if (json == null) {
            json = loadServer(index);
            if (json != null) {
                saveLocal(json, index);
            }
        } else {
            System.out.println("复用了本地缓存");
        }

        if (json != null) {
            return parseJson(json);
        } else {
            return null;
        }
    }


    private void saveLocal(String json, int index) {
        BufferedWriter writer = null;
        try {
            File cacheDir = FileUtils.getCacheDir();
            File file = new File(cacheDir, getKey() + "_" + index + getParams());
            FileWriter fileWriter = new FileWriter(file);
            writer = new BufferedWriter(fileWriter);
            writer.write(System.currentTimeMillis() + 1000 * 100 + "");
            writer.newLine();
            writer.write(json);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }

    private String loadLocal(int index) {
        File cacheDir = FileUtils.getCacheDir();
        File file = new File(cacheDir, getKey() + "_" + index + getParams());
        FileReader fr;
        BufferedReader reader;
        try {
            fr = new FileReader(file);
            reader = new BufferedReader(fr);
            long outofData = Long.parseLong(reader.readLine());
            if (System.currentTimeMillis() > outofData) {
                return null;
            } else {
                String line = null;
                StringWriter stringWriter = new StringWriter();
                while ((line = reader.readLine()) != null) {
                    stringWriter.write(line);
                }
                return stringWriter.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("loadLocal");
            return null;
        }
    }

    private String loadServer(int index) {
        HttpHelper.HttpResult httpResult = HttpHelper.get(HttpHelper.URL + getKey()
                + "?index=" + index + getParams());
        if (httpResult != null) {
            return httpResult.getString();
        } else {
            return null;
        }
    }

    public String getParams() {
        return "";
    }

    public abstract T parseJson(String json);

    public abstract String getKey();
}
