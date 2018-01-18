package com.example.vincent.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vincent QQ:1032006226
 * @version v1.0
 * @name CustomView
 * @page com.example.vincent.customview
 * @class describe
 * @date 2018/1/2 18:08
 */

public class MyDataAll extends View {

    private static final String TAG = MyDataAll.class.getSimpleName();
    private List<Integer> datas = new ArrayList<>();
    private Paint mPaint;
    private Path mPath;
    private int mColorData = Color.parseColor("#07aef5");
    //心电图的宽度
    private float line_width = 4f;
    private int view_width;
    private int view_height;

    //绘制头部画笔
    private Paint mHeadPaint;
    //头部路径
    private Path mHeadPath;
    //头部的颜色
    private int mColorHead = Color.parseColor("#07aef5");
    //头部的宽度
    private float headPathWidth = 8f;
    
    public MyDataAll(Context context) {
        super(context);
        init();
        setBackgroundColor(ContextCompat.getColor(context,android.R.color.transparent));
    }
    public MyDataAll(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        setBackgroundColor(ContextCompat.getColor(context,android.R.color.transparent));
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //控件宽度
        view_width = w;
        //控件高度
        view_height = h;

        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    //绘制完毕
//    private boolean isDrawFinish = false;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画数据
        drawData(canvas);
    }
    private void drawData(Canvas canvas) {
        //清除路径
        mPath.reset();
        if(datas != null && datas.size()>0){
                //绘制头部
                mPath.moveTo(0,change(datas.get(0)));
                //1 s更新125个数据，125个数据占用为5个大格(25个小格)
                //1个小格子为5个数据  1个数据为16/5小格 1小格的宽度为16 1个数据的宽度是16/5
                for (int i = 0;i<datas.size();i++){
                    mPath.lineTo(i,change(datas.get(i)));
                }
                canvas.drawPath(mPath,mPaint);
                Log.d(TAG, "drawData: has head");
        }
//        isDrawFinish = true;
    }


    public void addAllData(List<Integer> datas){
        this.datas.addAll(datas);
        invalidate();
    }



    /**
     * 把数据转化为对应的坐标  1大格表示的数据值为0.5毫伏，1毫伏= 200(数据) 1大格表示的数据 = 0.5 *200 1小格表示的数据 = 0.5*200/5 = 20
     * 1 小格的数据 表示为20 1小格的高度为16
     * @param data
     * @return
     */
    private float change(Integer data){
//        Log.d(TAG, "change: datas size is "+datas.size());
//        Log.d(TAG, "change-->取到的数据: "+String.valueOf(data)+"    集合大小: "+String.valueOf(datas.size()));
        return (float) (-1.0f) * data / 20;
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mColorData);
        mPaint.setStrokeWidth(line_width);

        mPath = new Path();
        mHeadPath = new Path();

        mHeadPaint = new Paint();
        mHeadPaint.setStyle(Paint.Style.STROKE);
        mHeadPaint.setAntiAlias(true);
        mHeadPaint.setColor(mColorHead);
        mHeadPaint.setStrokeWidth(headPathWidth);
    }

}
