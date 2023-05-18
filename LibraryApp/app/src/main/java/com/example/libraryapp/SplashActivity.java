package com.example.libraryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

// 첫 로딩화면(스플래시 화면)
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        moveMain(1);
    }

    private void moveMain(int Sec){     // 메인화면으로 이동을 위한 함수
        new Handler().postDelayed(new Runnable() {      // 작업을 전달하는 매개체(중간다리)?
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);    // 주석필요!

                startActivity(intent);

                finish();
            }
        }, 1000 * Sec);     // 로딩시간
    }
}