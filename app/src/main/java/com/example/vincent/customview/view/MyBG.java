package com.example.vincent.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.vincent.customview.R;

/**
 * Created by Vincent on 2018/1/2.
 */

public class MyBG extends View {

    private Paint mPaint;
    //View的宽度
    private int view_width;
    //View的高度
    private int view_height;
    //基准线 在View的垂直居中的位置
    protected int baseLine;
    //小格子的宽度
    private int smallGrid = 16;
    //大个字的宽度 一个大格子里面包含5个小格子
    private int bigGrid = smallGrid * 5;
    //小格子的线的颜色
    private int smallGridColor = Color.parseColor("#53bfed");
    //大格子的线的颜色
    private int bigGridColor = Color.parseColor("#53bfed");
    //View的背景颜色
    private int BGColor = Color.WHITE;

    private static final String TAG = MyBG.class.getSimpleName();

    public MyBG(Context context) {
        super(context);
        setBackgroundColor(BGColor);

    }

    public MyBG(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(BGColor);
        init(context, attrs);
    }

    public MyBG(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(BGColor);
        init(context, attrs);
    }

    /**
     * 初始化
     */
    private void init(Context context, AttributeSet attrs) {
        //获取自定义属性的值
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.ECG);
        smallGrid = (int)a.getDimension(R.styleable.ECG_small_grid_width,16);
        mPaint = new Paint();
        mPaint.setStrokeWidth(2);
        mPaint.setAntiAlias(true);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        view_width = w;
        view_height = h;
//        baseLine = view_height/2;//基准线位于view的一半位置
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawGridBackground(canvas);
    }


    /**
     * 绘制格子背景
     * @param canvas
     */
    private void drawGridBackground(Canvas canvas) {
        //画小格子
        //注意，小格子横线的个数
        int hSmallGridNum = view_height/smallGrid;
        mPaint.setColor(smallGridColor);
        mPaint.setStrokeWidth(1);
        //取余，得到多的不足smallGrid高度的部分
//        int a = view_height % smallGrid;
        //这里画的横线部分
        for(int i = 1;i<hSmallGridNum*2;i++){
            canvas.drawLine(0,//起点x
                    i * smallGrid,//起点y
                    view_width,//终点x
                    i * smallGrid,//终点Y
                    mPaint);
        }
        //这里获取小格子竖线有多少
        int sSmallGridNum = view_width/smallGrid;
        for(int i = 0;i<sSmallGridNum + 1;i++) {
            canvas.drawLine(i * smallGrid,
                    0,
                    i * smallGrid,
                    view_height,
                    mPaint);
        }
        //下面画大格子
        mPaint.setStrokeWidth(2);
        mPaint.setColor(bigGridColor);
        //得到大格子的个数，实际上这里只是拿到了上部分的大格子个数
        int hBigGridNum = view_height/bigGrid;
        //这里画的横线部分
        for(int i = 0;i<hBigGridNum*2+2;i++){
            if(i == hBigGridNum/2){
                //基准线的位置
                baseLine = i * bigGrid;
                mPaint.setColor(Color.RED);
            }else {
                mPaint.setColor(bigGridColor);
            }
            canvas.drawLine(0,//起点x
                    i * bigGrid ,//起点y
                    view_width,//终点x
                    i * bigGrid,//终点Y
                    mPaint);
        }
        //画大格子竖线
        int sBigGridNum = view_width / bigGrid;
        for (int i = 0;i <sBigGridNum + 1;i++){
            canvas.drawLine(i * bigGrid,
                    0,
                    i * bigGrid,
                    view_height,
                    mPaint);
        }

    }

}
