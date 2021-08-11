package com.example.kotlindemo.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kotlindemo.R;
import com.example.kotlindemo.widget.ProgressbarView;


public class ProgressBarActivity extends AppCompatActivity {

    private ProgressbarView mProgressbarView;
    private int progress = 0;
    private int progress2 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);
        mProgressbarView = ((ProgressbarView) findViewById(R.id.progressbar));
//        mProgressbarView.setProgress(progress += 10);//  ,true);
//        mProgressbarView.setProgress(progress += 10);//  ,true);
//        mProgressbarView.setProgress(progress += 10);//  ,true);


    }

    public void delProgress(View view) {
        if(progress > 0){
            progress -= 10;
            mProgressbarView.removeProgress(progress);
        }
    }

    public void addProgress(View view) {
        if(progress < 100){
            mProgressbarView.setProgress(progress += 10, true);
        }
    }

    public void addProgress2(View view) {
        if (progress < 100) {
            mProgressbarView.setProgress2(progress2 += 10, true);
        }
    }

}
