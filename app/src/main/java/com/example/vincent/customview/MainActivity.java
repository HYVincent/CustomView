package com.example.vincent.customview;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.vincent.customview.utils.CountDown;
import com.example.vincent.customview.utils.PriorityExecutor;
import com.example.vincent.customview.utils.ReadAssetsFileUtils;
import com.example.vincent.customview.utils.TimeUtils;
import com.example.vincent.customview.view.CustomView;
import com.example.vincent.customview.view.MyBG;
import com.example.vincent.customview.view.MyData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = CustomView.class.getSimpleName();
    private MyBG myBG;
    private MyData myData;
    private List<Integer> allDatas = new ArrayList<>();
    private List<Integer> dataaaaa = new ArrayList<>();

    private int index = -1;

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
        TimeUtils.startTime(0, 1000/125L*2, new TimeUtils.TimeListener() {
            @Override
            public void doAction() {
                index ++;
                if(index >= allDatas.size()-2){
                    TimeUtils.cancelTime();
                    return;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myData.addData(allDatas.get(index));
                        Log.d(TAG, "run: index = " +String.valueOf(index));
                    }
                });
               /* PriorityExecutor priorityExecutor = new PriorityExecutor(true);
                priorityExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Message message = Message.obtain();
                        message.what = 0x11;
                        handler.sendMessage(message);
                    }
                });*/

               /* Message message = Message.obtain();
                message.what = 0x11;
                handler.sendMessage(message);*/
            }
        });
    }

    private void changeData(String starCareData) {
        String[] strDatas = starCareData.split("\n");
        for (int i = 0;i<strDatas.length;i++){
            allDatas.add(Integer.valueOf(strDatas[i].replace("\r","")));
        }
        Log.d(TAG, "changeData: allDatas size is "+String.valueOf(allDatas.size()));
//        myData.addAllData(allDatas);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
