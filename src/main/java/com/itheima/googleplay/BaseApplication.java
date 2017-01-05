package com.itheima.googleplay;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * 代表当前应用程序
 * @author itcast
 *
 */
public class BaseApplication extends Application {
	private static BaseApplication application;
	private static int mainId;
	private static Handler handler;


	//  在主线程运行的
	@Override
	public void onCreate() {
		super.onCreate();
		application=this;
		mainId = android.os.Process.myTid();
		handler = new Handler();
	}
	public static Context getApplication() {
		return application;
	}

	//返回主线程id
	public static int getMainId() {
		return mainId;
	}

	public static Handler getHandler() {
		return handler;
	}
}
