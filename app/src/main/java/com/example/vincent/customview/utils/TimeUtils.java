package com.example.vincent.customview.utils;

import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Vincent QQ:1032006226
 * @version v1.0
 * @name StartKangMedical_Android
 * @page com.vincent.mylibrary.util
 * @class describe
 * @date 2018/1/3 15:59
 */


public class TimeUtils {

    /**
     * 计时器，定时任务
     * @param index 初始值，
     * @param delayTime 延迟时间  延迟多少毫秒开始执行
     * @param interval 间隔时间，间隔多少时间执行一次
     * @param threadNum 线程池的数量
     * @param timeListener 监听器
     */
    public static void startTime(int index,long delayTime,long interval,  int threadNum, final TimeListener timeListener) {
        final AtomicInteger atomicInteger = new AtomicInteger(index);
        if (threadNum == 0) {
            threadNum = 1;
        }
        final ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(threadNum);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                atomicInteger.incrementAndGet();
                timeListener.doAction();
            }
        }, delayTime, interval, TimeUnit.MILLISECONDS);
    }

    public interface TimeListener{
        void doAction();
    }

}
