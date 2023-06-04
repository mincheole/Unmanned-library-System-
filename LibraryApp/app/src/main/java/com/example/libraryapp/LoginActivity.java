package com.example.libraryapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private Button login_btn;   // 로그인 버튼
    private EditText et_id, et_pw;
    private String id, pw;
    private int login;      // 추후 boolean 타입으로 변경

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

                Thread t1 = new Thread(new Runnable() {     // 로그인 기능은 메인 쓰레드에서 불가능(새로운 쓰레드 생성)
                    @Override
                    public void run() {
                        login = connect();  // DB접속 함수(return값 login 변수에 저장)
                        if(login == 1){
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);    // 로그인 버튼 클릭시 메인 액티비티로 이동(서버랑 통신해야함)
                            startActivity(intent);
                            finish();
                        }else{
                            Handler handler = new Handler(Looper.getMainLooper());  // Toast기능 MainActivity 이외에서 사용할 시 Handler 생성 필수!
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                });
                t1.start();     // 스레드 실행

            }
        });
    }

    private int connect() {     // JSP와 통신 메소드
        StringBuffer buf = new StringBuffer();  // JSP 화면에 뜨는 정보들 저장할 변수
        String urlPath = "http://112.157.208.197:8080/DbConn1/f_login.jsp?userid=김민철&userpw="+pw;   // id, pw 입력받아야함(한글 X)
        try {
            URL url = new URL(urlPath);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            if(con != null){
                con.setRequestMethod("GET");    // GET 또는 POST
                con.setDoInput(true);
                con.setDoOutput(true);
                int code = con.getResponseCode();   // CODE가 200이면 접속성공(단지 사이트 접속여부, 로그인여부X)
                Log.i("mytag", "RESOPNSE_CODE"+code);   // 로그에서 코드 실행결과(사이트 접속시 code200) 확인코드

                InputStream input = con.getInputStream();   // 검색필요
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));   // 검색필요

                String str = reader.readLine();
                while(str!=null){
                    buf.append(str);
                    str = reader.readLine();
                }

                reader.close();
            }
            con.disconnect();
            Log.i("mytag", "buf "+ buf.toString());     // 로그에서 코드 실행결과(buf값) 확인코드
            return Integer.parseInt(buf.toString());    // buf를 int 타입으로 변경(1을 리턴하기 위해)

        }catch (Exception e){
            Log.i("mytag", e.getLocalizedMessage());    // 로그에서 코드 실행결과 확인코드
        }
        return 0;
    }

}