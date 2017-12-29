package com.example.vincent.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author Vincent QQ:1032006226
 * @version v1.0
 * @name CustomView
 * @page com.example.vincent.customview
 * @class describe
 * @date 2017/12/29 17:25
 */


public class ElectrocardiogramBgView extends View {

    private static final String TAG = ElectrocardiogramBgView.class.getSimpleName();

    //View的宽度
    private int mWidth;
    //View的高度
    private int mHeight;


    public ElectrocardiogramBgView(Context context) {
        super(context);
        Log.d(TAG, "ElectrocardiogramBgView: 1");
    }

    public ElectrocardiogramBgView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "ElectrocardiogramBgView: 2");
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


    public int getmHeight() {
        return mHeight;
    }

}
