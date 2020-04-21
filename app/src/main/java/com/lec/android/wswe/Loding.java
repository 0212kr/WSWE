package com.lec.android.wswe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lec.android.wswe.ui.home.HomeFragment;

public class Loding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loding);

        ImageView load = (ImageView)findViewById(R.id.logindView);
        Glide.with(this).load(R.raw.loding3).into(load);

        Handler mhandler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);  // 화면 전환
                finish();   // intro 화면은 종료료

            }
        } ;



        mhandler.sendEmptyMessageDelayed(1,2850);
    }
}
