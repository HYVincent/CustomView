package com.example.vincent.customview;

import java.util.Timer;
import java.util.TimerTask;

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

    /**
     * 计时 每隔intervalMillisecond毫秒之后执行某个函数
     * @param delayTimeStart
     * @param intervalMillisecond
     */
    public static void startTime(long delayTimeStart, long intervalMillisecond , final TimeListener timeListener){
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
    }


    public interface TimeListener{

        void doAction();

    }

}
