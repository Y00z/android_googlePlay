package com.itheima.googleplay.tools;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.itheima.googleplay.BaseActivity;
import com.itheima.googleplay.BaseApplication;

public class UiUtils {
    private static boolean runInMainThread;

    /**
     * 获取到字符数组
     *
     * @param tabNames 字符数组的id
     */
    public static String[] getStringArray(int tabNames) {
        return getResource().getStringArray(tabNames);
    }

    public static Resources getResource() {
        return BaseApplication.getApplication().getResources();
    }

    public static int getDimens(int i){
        return (int) getResource().getDimension(i);
    }

    /**
     * dip转换px
     */
    public static int dip2px(int dip) {
        final float scale = getResource().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * pxz转换dip
     */

    public static int px2dip(int px) {
        final float scale = getResource().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static Context getContext() {
        return BaseApplication.getApplication();
    }

    /**
     * 把Runnable 方法提交到主线程运行
     */
    public static void runOnUiThread(Runnable runnable) {
        if (android.os.Process.myTid() == BaseApplication.getMainId()) {
            runnable.run();
        } else {
            BaseApplication.getHandler().post(runnable);
        }
    }

    public static View inflate(int id) {
        return View.inflate(getContext(), id, null);
    }

    public static Drawable getDrawable(int id) {
        return getResource().getDrawable(id);
    }

    /**
     * 延迟执行 任务
     * @param run   任务
     * @param time  延迟的时间
     */
    public static void postDelayed(Runnable autoRunTask, int time) {
        BaseApplication.getHandler().postDelayed(autoRunTask,time);
    }

    /**
     * 取消任务
     * @param auToRunTask
     */
    public static void cancel(Runnable autoRunTask) {
        BaseApplication.getHandler().removeCallbacks(autoRunTask);
    }


    public static void startActivity(Intent intent) {
        if(BaseActivity.activity==null){
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        } else {
            BaseActivity.activity.startActivity(intent);
        }
    }

    public static boolean isRunInMainThread() {


        return android.os.Process.myPid() == getMainThreadId();
    }


    public static int getMainThreadId() {
        return BaseApplication.getMainId();
    }
}
