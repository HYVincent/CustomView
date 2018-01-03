package com.example.vincent.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/12/31.
 */

public class CustomView extends View {

    private int lastX;
    private int lastY;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取手指触摸x的点的横坐标和中坐标
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //计算移动的速度
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                //调用layout方法来重新设置它的位置
                layout(getLeft() + offsetX,getTop() + offsetY,getRight()+offsetX,getBottom()+offsetX);
                break;
        }


        return true;
    }
}
