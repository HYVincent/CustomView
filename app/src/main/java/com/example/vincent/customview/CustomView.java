package com.example.vincent.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author Vincent QQ:1032006226
 * @version v1.0
 * @name CustomView
 * @page com.example.vincent.customview
 * @class describe
 * @date 2017/12/29 17:25
 */


public class CustomView extends View {

    private static final String TAG = CustomView.class.getSimpleName();
    private Context mContext;

    //View的宽度
    private int mWidth;
    //View的高度
    private int mHeight;

    private int lastX;
    private  int lastY;

    private int bg_color = Color.parseColor("#FF4081");

    public CustomView(Context context) {
        super(context);
        Log.d(TAG, "CustomView: 1");
        mContext = context;
        setBackgroundColor(bg_color);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "CustomView: 2");
        mContext = context;
        setBackgroundColor(bg_color);
    }

    @Override
    protected void onFinishInflate() {
        Log.d(TAG, "onFinishInflate: 3");
        super.onFinishInflate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure: 4");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        Log.d(TAG, "onSizeChanged: 5");
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: 6");
        super.onDraw(canvas);
    }

    public int getmWidth() {
        return mWidth;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //X轴的偏移量
                int offsetX = x - lastX;
                //Y轴的偏移量
                int offsetY = y - lastY;
                layout(getLeft() + offsetX,
                        getTop()+offsetY,
                        getRight() + offsetX,
                        getBottom() + offsetY);
                break;
                default:break;
        }
        return true;
    }

    public int getmHeight() {
        return mHeight;
    }

}
