package com.example.vincent.customview;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = CustomView.class.getSimpleName();
    private MyBG myBG;
    private MyData myData;
    private List<Integer> allDatas = new ArrayList<>();
    private List<Integer> dataaaaa = new ArrayList<>();

    private int index = -1;
    private CountDown countDown;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x11:
                    Integer integer = allDatas.get(index);
                    myData.addData(integer);
                    Log.d(TAG, "everySecondAction-->取数据:index = "+String.valueOf(index)+"，data = "+allDatas.get(index));
                    dataaaaa.add(integer);
                    break;
                default:break;
            }
        }
    };

    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myBG = findViewById(R.id.mybg);
        myData = findViewById(R.id.mydata);
        changeData(ReadAssetsFileUtils.readAssetsTxt(this,"StarCareData"));
//        myBG.addDatas(datas);
//        myData.addData(allDatas.get(0));
        TimeUtils.startTime(0, 100L, new TimeUtils.TimeListener() {
            @Override
            public void doAction() {
                if(index == 0){
                    startTime = System.currentTimeMillis();
                }
                index ++;
                if(index == 125){
                    Log.d(TAG, "doAction: "+String.valueOf(System.currentTimeMillis()-startTime));
                    Log.d(TAG, "doAction: size is "+dataaaaa.size()+"\n"+JSONArray.toJSONString(dataaaaa));
                    Log.d(TAG, "doAction: allData size is "+allDatas.size()+"\n"+JSONArray.toJSONString(allDatas));
                    TimeUtils.cancelTime();
                    return;
                }
                Message message = Message.obtain();
                message.what = 0x11;
                handler.sendMessage(message);
            }
        });
    }

    private void changeData(String starCareData) {
        String[] strDatas = starCareData.split("\n");
        for (int i = 0;i<strDatas.length;i++){
            allDatas.add(Integer.valueOf(strDatas[i].replace("\r","")));
        }
        Log.d(TAG, "changeData: allDatas size is "+String.valueOf(allDatas.size()));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
