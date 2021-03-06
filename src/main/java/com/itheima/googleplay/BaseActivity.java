package com.itheima.googleplay;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import java.util.LinkedList;
import java.util.List;

/**
 * 抽取BaseActivity   管理所有activity 方便退出
 *
 * @author itcast
 */
public class BaseActivity extends ActionBarActivity {
    // 管理运行的所有的activity
    public final static List<BaseActivity> mActivities = new LinkedList<BaseActivity>();
    public static BaseActivity activity;


//	private KillAllReceiver receiver;
//	private class KillAllReceiver extends BroadcastReceiver{
//
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			finish();
//		}
//	}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		receiver=new KillAllReceiver();
//		IntentFilter filter=new IntentFilter("com.itheima.google.killall");
//		registerReceiver(receiver, filter);

        synchronized (mActivities) {
            mActivities.add(this);
        }
        init();
        initView();
        initActionBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (mActivities) {
            mActivities.remove(this);
        }
//		if(receiver!=null){
//			unregisterReceiver(receiver);
//			receiver=null;
//		}
    }

    public void killAll() {
        // 复制了一份mActivities 集合
        List<BaseActivity> copy;
        synchronized (mActivities) {
            copy = new LinkedList<BaseActivity>(mActivities);
        }
        for (BaseActivity activity : copy) {
            activity.finish();
        }
        // 杀死当前的进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    protected void initActionBar() {
    }

    protected void initView() {
    }

    protected void init() {
    }
}
