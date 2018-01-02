package com.example.vincent.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = CustomView.class.getSimpleName();
    private MyBG myBG;
    private List<Integer> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myBG = findViewById(R.id.mybg);
        changeData(ReadAssetsFileUtils.readAssetsTxt(this,"StarCareData"));
//        myBG.addDatas(datas);
    }

    private void changeData(String starCareData) {
        String[] strDatas = starCareData.split("\n");
        for (int i = 0;i<strDatas.length;i++){
//            datas.add(Integer.valueOf(strDatas[i].replace("\r","")));
            myBG.addData(Integer.valueOf(strDatas[i].replace("\r","")));
        }
        Log.d(TAG, "changeData: size is "+datas.size()+",detail:"+ JSONArray.toJSONString(datas));
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Log.d(TAG, "onResume:width--> "+bgView.getmWidth()+",height-->"+bgView.getmHeight());
    }
}
