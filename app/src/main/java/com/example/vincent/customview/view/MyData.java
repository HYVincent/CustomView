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

public class MyData extends View {

    private static final String TAG = MyData.class.getSimpleName();
    private List<Integer> datas = new ArrayList<>();
    private Paint mPaint;
    private Path mPath;
    private int mColorData = Color.parseColor("#07aef5");
    //心电图的宽度
    private float line_width = 4f;
    private int view_width;
    private int view_height;
    private float baseLine;//基准线
    //小格子的宽度
    private float smailGridWith = 16f;
    private float bigGridWidth = smailGridWith * 5;
    private int bigGridNum;
    //表示每个小格子放五个数据
    private int dataNumber = 5;
    //屏幕能够显示的所有的点的个数 注意这个值设置为int会导致这个数值不准确
    private float maxSize = 0;
    //是否绘制头部 凸状物 true 画  false 不画
    private boolean isDrawHead = true;
    //绘制头部画笔
    private Paint mHeadPaint;
    //头部路径
    private Path mHeadPath;
    //头部的颜色
    private int mColorHead = Color.parseColor("#07aef5");
    //头部的宽度
    private float headPathWidth = 8f;
    //头部的总宽度为两个大格子
    private float headWidth = bigGridWidth * 2;

    private float mark = -1.0f;
    private boolean orientation = false;

    //暂停绘制 true暂停  false绘制
    private boolean pause = false;

    /** 数据倒置
     * true -1.0f false 1.0f
     */
    public void setMarkOrder(){
        if(orientation){
            orientation = false;
            mark = -1.0f;
            Log.d(TAG, "setMarkOrder: 正");
        }else {
            orientation = true;
            mark = 1.0f;
            Log.d(TAG, "setMarkOrder: 反");
        }
    }


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

    /**
     * 这个方法可以每隔格子放的数据数量
     * @param dataNumber
     */
    public void setDataNumber(int dataNumber) {
        this.dataNumber = dataNumber;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //控件宽度
        view_width = w;
        //控件高度
        view_height = h;
        //有多少个大格子
        bigGridNum = (int) (view_height / bigGridWidth);
        //基准线的位置
        baseLine = bigGridNum /2 * bigGridWidth;
        //125个数据占用为5个大格子，5个大格子有25个小格子，所以每个小格子放5个数据
        if(isDrawHead){
            maxSize =  (view_width*(1.0f)-headWidth) / (smailGridWith / (dataNumber*1.0f));
        }else {
            maxSize =  view_width*(1.0f) / (smailGridWith / (dataNumber*1.0f));
        }
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
         //画头部
        drawHead(canvas);
        //画数据
        drawData(canvas);
    }

    public void setDrawHead(boolean drawHead) {
        isDrawHead = drawHead;
    }

    private void drawData(Canvas canvas) {
        //清除路径
        mPath.reset();
        if(datas != null && datas.size()>0){
            if(isDrawHead){
                //绘制头部
                mPath.moveTo(headWidth,change(datas.get(0)));
                //1 s更新125个数据，125个数据占用为5个大格(25个小格)
                //1个小格子为5个数据  1个数据为16/5小格 1小格的宽度为16 1个数据的宽度是16/5
                for (int i = 0;i<datas.size();i++){
                    mPath.lineTo(i * smailGridWith /dataNumber+headWidth,change(datas.get(i)));
                }
                canvas.drawPath(mPath,mPaint);
//                Log.d(TAG, "drawData: has head");
            }else {
//                Log.d(TAG, "drawData: no head");
                //不绘制头部
                mPath.moveTo(0,change(datas.get(0)));
                //1 s更新125个数据，125个数据占用为5个大格(25个小格)
                //1个小格子为5个数据  1个数据为16/5小格 1小格的宽度为16 1个数据的宽度是16/5
                for (int i = 0;i<datas.size();i++){
                    mPath.lineTo(i * smailGridWith /dataNumber,change(datas.get(i)));
                }
                canvas.drawPath(mPath,mPaint);
            }
        }
//        isDrawFinish = true;
    }

    /**
     * 绘制头部
     * @param canvas
     */
    private void drawHead(Canvas canvas) {
        //控制是否画头部
        if(isDrawHead){
            //移动到基线的位置
            mHeadPath.moveTo(0,baseLine);
            for( int i = 0; i<headWidth;i++){
                if(i < bigGridWidth/2){
                    //开始部分一直到小格子一半的部分都是直线
                    mHeadPath.lineTo(i,baseLine);
                }else if(i > bigGridWidth/2 && i< bigGridWidth /2 * 3){
                    mHeadPath.lineTo(i,baseLine - bigGridWidth * 2);
                }if(i > bigGridWidth / 2 * 3){
                    mHeadPath.lineTo(i,baseLine);
                }
            }
            canvas.drawPath(mHeadPath,mHeadPaint);
        }
    }

    /**
     * 添加数据
     * @param data
     */
    public void addData(Integer data){
        if(datas.size() > maxSize){
            //如果这个集合大于maxSize（即表示屏幕上所能显示的点的个数）个点，那么就把第一个点移除
//            Log.d(TAG, "addData: "+maxSize);
            datas.remove(0);
        }
        datas.add(data);
        if(pause){
            return;
        }
        //这个方法会重新调用onDraw()方法 这个方法用在主线程
        invalidate();
        //这个方法用在异步线程
//        requestLayout();
    }

    /**
     * 添加所有数据
     * @param datas
     */
    public void addDatas(List<Integer> datas){
        if(datas != null && datas.size()>0){
            this.datas = datas;
            invalidate();
        }
    }

    /**
     * 是否暂停绘制
     * @param pause
     */
    public void onPauseDraw(boolean pause) {
        this.pause = pause;
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
        return (float) (mark) * data / 20 * smailGridWith + baseLine;
    }

    /**
     * 设置头部数据颜色值
     * @param mColorData
     */
    public void setmColorData(int mColorData) {
        this.mColorData = mColorData;
        if(mPaint != null){
            mPaint.setColor(mColorData);
        }
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
