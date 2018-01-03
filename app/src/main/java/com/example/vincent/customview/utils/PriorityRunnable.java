package com.example.vincent.customview.utils;

/**
 * @author Administrator QQ:1032006226
 * @version v1.0
 * @name CustomView
 * @page com.example.vincent.customview.utils
 * @class describe 带有优先级的Runnable类型
 * @date 2018/1/3 23:09
 */

public class PriorityRunnable implements Runnable {

    public final Priority priority;//任务优先级
    private final Runnable runnable;//任务真正执行者
    /*package*/ long SEQ;//任务唯一标示

    public PriorityRunnable(Priority priority, Runnable runnable) {
        this.priority = priority == null ? Priority.NORMAL : priority;
        this.runnable = runnable;
    }

    @Override
    public final void run() {
        this.runnable.run();
    }
}
