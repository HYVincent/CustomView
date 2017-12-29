package com.example.vincent.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = ElectrocardiogramBgView.class.getSimpleName();
    private ElectrocardiogramBgView bgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bgView = findViewById(R.id.egb);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onResume:width--> "+bgView.getmWidth()+",height-->"+bgView.getmHeight());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume:width--> "+bgView.getmWidth()+",height-->"+bgView.getmHeight());
    }
}
