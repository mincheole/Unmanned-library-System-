package com.example.libraryapp;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private Button login_btn;   // 로그인 버튼
    private EditText et_id, et_pw;
    protected String id, pw;
    private String login;      // 로그인 성공여부 판별 변수


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id = findViewById(R.id.et_id);   // EditText(아이디 입력칸) id연결
        et_pw = findViewById(R.id.et_pw);   // EditText(비밀번호 입력칸) id연결
        login_btn = findViewById(R.id.login_btn);   // 로그인 버튼 id 연결

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = et_id.getText().toString();    // 사용자가 입력한 id값 저장
                pw = et_pw.getText().toString();    // 사용자가 입력한 pw값 저장

                Thread t1 = new Thread(new Runnable() {     // 로그인 기능(통신)은 메인 쓰레드에서 불가능(새로운 쓰레드 생성)
                    @Override
                    public void run() {
                        ServerConnector.ConnectionParams params = new ServerConnector.ConnectionParams();   // ConnectionParams 타입 변수 생성 및 선언
                        params.setOption(1).setId(id).setPw(pw);  // 매개변수로 jsp옵션, id, pw 넘김
                        login = ServerConnector.connect(params); // Option 1 = 로그인
                        if(login.equals("1")){
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);    // 로그인 버튼 클릭시 메인 액티비티로 이동(서버랑 통신해야함)
                            startActivity(intent);
                            finish();   // 로그인 화면 종료
                        }else{
                            Handler handler = new Handler(Looper.getMainLooper());  // Toast기능 MainActivity 이외에서 사용할 시 Handler 생성 필수!
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                                    MainActivity mainActivity = new MainActivity();

                                }
                            });
                        }
                    }
                });
                t1.start();     // 스레드 실행

            }
        });

    }

}