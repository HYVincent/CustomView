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
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = CustomView.class.getSimpleName();
    private MyBG myBG;
    private MyData myData;
    private MyData myData2;
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
//                    dataaaaa.add(integer);
                    break;
                default:break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myBG = findViewById(R.id.mybg);
        myData = findViewById(R.id.mydata);
        myData2 = findViewById(R.id.mydata2);
        myData2.setDataNumber(10);
        changeData(ReadAssetsFileUtils.readAssetsTxt(this,"StarCareData"));
        times();

    }




    private void times(){
        final int index = -1;
        final AtomicInteger atomicInteger = new AtomicInteger(index);

        final ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(10);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    final int mark = atomicInteger.incrementAndGet();
                    if(mark >= allDatas.size()){
                        scheduledExecutorService.shutdownNow();
                        return;
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            myData.addData(allDatas.get(mark));
                            myData2.addData(allDatas.get(mark));
                            Log.d(TAG, "run: index = " + String.valueOf(mark));
                        }

                    });
//                    Thread.sleep(300);
                }catch (Exception e){

                }

            }
        },100,1000/125L,TimeUnit.MILLISECONDS);
    }












    /**
     * 保证index++的连续性 不会跳数
     * @return
     */
    public int getIndex() {
        synchronized (Integer.class){
            return index++;
        }
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
