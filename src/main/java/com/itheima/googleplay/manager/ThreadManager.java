package com.itheima.googleplay.manager;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadManager {

    static ThreadPoolProxy loolProxy;
    static ThreadPoolProxy shortPool;

    public ThreadManager() {
    }

    private static ThreadManager instance = new ThreadManager();

    public static ThreadManager getInstance() {
        return instance;
    }


    public static ThreadPoolProxy createLongPool() {
        if (loolProxy == null) {
            synchronized (ThreadPoolProxy.class) {
                if (loolProxy == null) {
                    loolProxy = new ThreadPoolProxy(5, 5, 5000);
                }
            }
        }
        return loolProxy;
    }


    public static ThreadPoolProxy createShortPool() {
        if (shortPool == null) {
            synchronized (ThreadPoolProxy.class) {
                if (shortPool == null) {
                    shortPool = new ThreadPoolProxy(3, 3, 5000);
                }
            }
        }
        return shortPool;
    }

    public static class ThreadPoolProxy {
        ThreadPoolExecutor mExecutor;            // 只需创建一次
        int mCorePoolSize;
        int mMaximumPoolSize;
        long mKeepAliveTime;

        public ThreadPoolProxy(int corePoolSize, int maximumPollSize, long alinetime) {
            super();
            mCorePoolSize = corePoolSize;
            mMaximumPoolSize = maximumPollSize;
            mKeepAliveTime = alinetime;
        }

        private ThreadPoolExecutor initThreadPoolExecutor() {//双重检查加锁
            if (mExecutor == null) {
                synchronized (ThreadPoolProxy.class) {
                    if (mExecutor == null) {
                        TimeUnit unit = TimeUnit.MILLISECONDS;
                        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();// 无界队列
                        ThreadFactory threadFactory = Executors.defaultThreadFactory();
                        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();// 丢弃任务并抛出RejectedExecutionException异常。
                        mExecutor = new ThreadPoolExecutor(//
                                mCorePoolSize,// 核心的线程数
                                mMaximumPoolSize,// 最大的线程数
                                mKeepAliveTime, // 保持时间
                                unit, // 保持时间对应的单位
                                workQueue,// 缓存队列/阻塞队列
                                threadFactory, // 线程工厂
                                handler// 异常捕获器
                        );
                    }
                }
            }
            return mExecutor;
        }

        public ThreadPoolProxy() {
        }


        public void execute(Runnable runnable) {
            initThreadPoolExecutor();
            mExecutor.execute(runnable);
        }


        public void cancel(Runnable runnable) {
            initThreadPoolExecutor();
            mExecutor.remove(runnable);
        }
    }
}
