package com.itheima.googleplay.tools;

import android.os.Environment;

import java.io.File;

/**
 * Created by Yooz on 2016/3/13.
 */
public class FileUtils {

    public static final String ROOT = "GooglePlay";
    public static final String CACHE = "cache";
    public static final String ICON = "icon";
    public static final String DOWNLOAD = "downloadAPK";

    public static File getCacheDir(){
        return getDir(CACHE);
    }

    public static File getDownLoad(){
        return getDir(DOWNLOAD);
    }

    public static File getIconDir(){
        return getDir(ICON);
    }

    public static File getDir(String dir) {
        StringBuilder builder = new StringBuilder();
        if (isSDAvailable()) {
            builder.append(Environment.getExternalStorageDirectory().getAbsolutePath());
            builder.append(File.separator);
            builder.append(ROOT);
            builder.append(File.separator);
            builder.append(dir);
        } else {
            File cacheDir = UiUtils.getContext().getCacheDir();
            builder.append(cacheDir.getAbsolutePath());
            builder.append(File.separator);
            builder.append(dir);
        }


        File file = new File(builder.toString());
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        return file;
    }

    private static boolean isSDAvailable() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }
}
