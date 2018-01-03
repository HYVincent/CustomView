package com.example.vincent.customview.utils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Vincent QQ:1032006226
 * @version v1.0
 * @name StartKangMedical_Android
 * @page com.vincent.mylibrary.util
 * @class describe
 * @date 2018/1/3 15:59
 */


public class TimeUtils {

    private static Timer timer;
    private static ScheduledExecutorService executor;

    /**
     * 计时 每隔intervalMillisecond毫秒之后执行某个函数
     * @param delayTimeStart
     * @param intervalMillisecond
     */
    public static void startTime(long delayTimeStart, long intervalMillisecond , final TimeListener timeListener){
       /* PriorityExecutor priorityExecutor = new PriorityExecutor(true);
        priorityExecutor.execute(new PriorityRunnable(Priority.HIGH, new Runnable() {
            @Override
            public void run() {

            }
        }));*/
        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //如果发生异常，将不会影响定时器
                            timeListener.doAction();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                delayTimeStart,
                intervalMillisecond,
                TimeUnit.MILLISECONDS);
    }



    /**
     * 计时 每隔intervalMillisecond毫秒之后执行某个函数
     * @param delayTimeStart
     * @param intervalMillisecond
     */
    public static void startTime2(long delayTimeStart, long intervalMillisecond , final TimeListener timeListener){
        timer = new Timer();
        TimerTask task = new TimerTask(){
            public void run() {
                timeListener.doAction();
            }
        };
        timer.schedule (task, delayTimeStart, intervalMillisecond);//第一个参数表示延时 ，第二个参数表示间隔多少秒之后执行
    }

    public static void cancelTime(){
        if(timer != null){
            timer.cancel();
        }
        if(executor != null && !executor.isShutdown()){
            executor.shutdown();
        }
    }


    public interface TimeListener{

        void doAction();

    }

}
