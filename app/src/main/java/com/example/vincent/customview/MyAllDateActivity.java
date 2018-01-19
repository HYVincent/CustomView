package com.example.vincent.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.vincent.customview.utils.ReadAssetsFileUtils;
import com.example.vincent.customview.view.MyData;
import com.example.vincent.customview.view.MyDataAll;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 2018/1/18.
 */

public class MyAllDateActivity extends AppCompatActivity {


    private static final String TAG = MyAllDateActivity.class.getSimpleName();
    //数据源
    private List<Integer> allDatas = new ArrayList<>();
    private MyDataAll dataAll;
    private MyData dataShow;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_all);
        changeData(ReadAssetsFileUtils.readAssetsTxt(this,"StarCareData"));
        dataAll = findViewById(R.id.myDataAll);
        dataShow = findViewById(R.id.mydata_show);
        dataAll.addAllData(allDatas);
        dataAll.setListener(new MyDataAll.MoveDataListener() {
            @Override
            public void showDatas(List<Integer> datas) {
                dataShow.addAllData(datas);
            }
        });
    }

    private void initData() {

    }
    private void changeData(String starCareData) {
        String[] strDatas = starCareData.split("\n");
        for (int i = 0;i<strDatas.length;i++){
            allDatas.add(Integer.valueOf(strDatas[i].replace("\r","")));
        }
        Log.d(TAG, "changeData: allDatas size is "+String.valueOf(allDatas.size()));
//        myData.addAllData(allDatas);
    }
}
