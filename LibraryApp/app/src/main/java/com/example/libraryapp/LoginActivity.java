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
                Frag_Rentalinfo.ID = id;

                Thread t1 = new Thread(new Runnable() {     // 로그인 기능은 메인 쓰레드에서 불가능(새로운 쓰레드 생성)
                    @Override
                    public void run() {
                        login = connect(1);
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
                                }
                            });
                        }
                    }
                });
                t1.start();     // 스레드 실행

            }
        });

    }

    protected String connect(int jspOption) {     // JSP와 통신 메소드
        StringBuffer buf = new StringBuffer();  // JSP 화면에 뜨는 정보들 저장할 변수
        String urlPath = null;    // id, pw 입력받아야함(한글 X)

        switch (jspOption) {     // 로그인 화면이랑 대여정보 화면에서 connect() 메소드를 사용하기 위함(1일때 로그인, 2일때 대여정보)
            case 1:
                urlPath = "http://whereisthebook.kro.kr:8080/DbConn1/f_login.jsp?userid=" + id + "&userpw=" + pw;
                break;
            case 2:
                urlPath = "http://whereisthebook.kro.kr:8080/DbConn1/f_userinfo.jsp?userid="+id;
                break;
        }
        try {
            URL url = new URL(urlPath);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            if (con != null) {
                con.setRequestMethod("GET");    // GET 또는 POST
                con.setDoInput(true);
                con.setDoOutput(true);

                InputStream input = con.getInputStream();   // 검색필요     //
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));   // 검색필요

                String str = reader.readLine();
                while (str != null) {
                    buf.append(str);
                    str = reader.readLine();
                }
                reader.close();
            }
            con.disconnect();
            Log.i("mytag", "buf " + buf.toString());     // 로그에서 코드 실행결과(buf값) 확인코드

            return buf.toString();    // 문자열 1을 반환하면 로그인 성공

        } catch (IOException e) {
            e.printStackTrace();    // 로그에서 코드 실행결과 확인코드
//            Log.i("mytag", "" + e.getLocalizedMessage());    // 로그에서 코드 실행결과 확인코드
        }
        return "로그인 실패";
    }

}