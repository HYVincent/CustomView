package com.example.vincent.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

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

public class MyData extends View {

    private static final String TAG = MyData.class.getSimpleName();
    private Handler mHandler = new Handler(){};

    private List<Integer> datas = new ArrayList<>();
    private Paint mPaint;
    private Path mPath;
    private int color = Color.RED;
    private float line_width = 2f;
    private int view_width;
    private int view_height;
    private int baseLine;//基准线
    private int smailGridWith = 16;
    private int bigGridWidth = smailGridWith * 5;
    private int bigGridNum;


    public MyData(Context context) {
        super(context);
        init();
        setBackgroundColor(ContextCompat.getColor(context,android.R.color.transparent));
    }
    public MyData(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        setBackgroundColor(ContextCompat.getColor(context,android.R.color.transparent));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        view_width = w;
        view_height = h;
        bigGridNum = view_height / bigGridWidth;
        baseLine = bigGridNum /2 * bigGridWidth;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(datas != null && datas.size()>0){
            canvas.drawPath(mPath,mPaint);
            mPath.moveTo(0,change(datas.get(0)));
            //1 s更新125个数据，125个数据占用为5个大格(25个小格)
            //1个小格子为25个数据  1个数据为25分之1小格 1小格的宽度为16
            if(datas.size() == 125){
                Log.d(TAG, "onDraw: data size is "+datas.size()+"\n"+ JSONArray.toJSONString(datas));
            }
            for (int i = 0;i<datas.size();i++){
//                mPath.lineTo(i,change(datas.get(i)));
                mPath.lineTo(i * bigGridWidth * 5/125,change(datas.get(i)));
            }
            canvas.drawPath(mPath,mPaint);
        }
    }

    /**
     * 添加数据
     * @param data
     */
    public void addData(final Integer data){
        datas.add(data);
        //这个方法会重新调用onDraw()方法 这个方法用在主线程
        invalidate();
        //这个方法用在异步线程
//        requestLayout();
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
        return (-1) * data / 20 * smailGridWith + baseLine;
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setColor(color);
        mPaint.setStrokeWidth(line_width);

        mPath = new Path();
    }

}
