package com.example.vincent.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
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
    //包含的所有数据
    private List<Integer> allDatas = new ArrayList<>();
    //需要展示的部分数据
    private List<Integer> showDatas = new ArrayList<>();
    //心电图画笔
    private Paint mPaint;
    //心电图路径
    private Path mPath;
    private int mColorData = Color.parseColor("#07aef5");
    //心电图线的宽度
    private float line_width = 4f;
    //View的宽度
    private int view_width;
    //View的高度
    private int view_height;
    //每个数据的宽度
    private float item_width;

    //画矩形
    private Paint rectanglPaint;
    //矩形的宽度
    private float rectangl_width = 60 * 4;
    //绘制矩形的左上角坐标X
    private float startX = 0;
    //绘制矩形的左上角坐标Y
    private float startY = 0;
    //绘制矩形的右下角坐标X
    private float endX = 0;
    //绘制矩形的右下角坐标Y
    private float endY = 0;
    //手指触摸View时的X坐标
    private float touchX = 0;
    //x轴偏移量
    private float offset_x;
    //X轴的最大偏移量
    private float offset_x_max;

    private MoveDataListener listener;


    public void setListener(MoveDataListener listener) {
        this.listener = listener;
    }

    public MyDataAll(Context context) {
        super(context);
        init();
        setBackgroundColor(ContextCompat.getColor(context,android.R.color.darker_gray));
    }
    public MyDataAll(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        setBackgroundColor(ContextCompat.getColor(context,android.R.color.darker_gray));
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //控件宽度
        view_width = w;
        //控件高度
        view_height = h;
        //每个数据的宽度
        item_width = (1.0f) * view_width/allDatas.size();
        //X轴的最大偏移量 = 屏幕宽度 - 控件的宽度
        offset_x_max = view_width - rectangl_width;
        Log.d(TAG, "onSizeChanged: item_width = "+String.valueOf(item_width));
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        endX = rectangl_width;
        endY = view_height;
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //获取手指触摸屏幕的点
                touchX = getX();
                break;
            case MotionEvent.ACTION_MOVE:
                offset_x = event.getX()-touchX;
                //防止超出屏幕
                if(offset_x > offset_x_max){
                    offset_x = offset_x_max;
                }
                //防止向左滑动到屏幕最左边的时候有空隙
                if(offset_x < rectangl_width/2){
                    offset_x = 0;
                }
                startX = (int)(offset_x);
                endY = (int)(rectangl_width + getX());
                endX = startX + rectangl_width;
                listener.showDatas(getShowDatas());
                invalidate();
                break;
            case MotionEvent.ACTION_UP:


                break;
            default:break;
        }
        return true;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画数据
        drawData(canvas);
        drawRectangl(canvas);
    }

    /**
     * 绘制透明矩形
     * @param canvas
     */
    private void drawRectangl(Canvas canvas) {
        //RectF 靠左上角的左边和右下角的坐标来确定
        canvas.drawRect(startX,startY,endX,endY,rectanglPaint);
    }

    private void drawData(Canvas canvas) {
        //清除路径
        mPath.reset();
        if(allDatas != null && allDatas.size()>0){
            //绘制头部
            mPath.moveTo(0,change(allDatas.get(0)));
            //1 s更新125个数据，125个数据占用为5个大格(25个小格)
            //1个小格子为5个数据  1个数据为16/5小格 1小格的宽度为16 1个数据的宽度是16/5
            for (int i = 0;i<allDatas.size();i++){
                mPath.lineTo(i * item_width,change(allDatas.get(i)));
            }
            canvas.drawPath(mPath,mPaint);
            Log.d(TAG, "drawData: has head");
        }
//        isDrawFinish = true;
    }


    public void addAllData(List<Integer> datas){
        this.allDatas.addAll(datas);
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
        return (float) (-1.0f) * data / 20 * 16 + view_height/2;
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mColorData);
        mPaint.setStrokeWidth(line_width);
        mPath = new Path();;

        rectanglPaint = new Paint();
        //设置两种图形的效果，
        rectanglPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
        rectanglPaint.setColor(Color.RED);
        //设置透明度 范围是0-255 数值越小越透明
        rectanglPaint.setAlpha(130);
    }


    private List<Integer> getShowDatas(){
        //ratio 表示的是遮罩部分宽度占View宽度的比值
        float ratio  = rectangl_width/view_width;
        //datas数组的长度
        int dataSize = allDatas.size();
        //屏幕上需要显示的数据个数
        int showDataSize = (int)(dataSize * ratio);
        //第一个点的下标
        int firstDotIndex = (int) (dataSize * (startX/view_height));
        int showSize = firstDotIndex+showDataSize;
        if(showSize > allDatas.size()-1){
            showSize = allDatas.size()-1;
        }
        for(int i = firstDotIndex;i<showSize;i++){
            showDatas.add(allDatas.get(i));
        }
        return showDatas;
    }

    public interface MoveDataListener{
        void showDatas(List<Integer> datas);
    }

}
