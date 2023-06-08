package com.example.libraryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView; //하단 바
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Frag_home fh; //홈 화면 객체 fh
    private Frag_map fmp; //도서관 맵 화면 객체 fmp
    private Frag_Rentalinfo fb; //대여정보 화면 객체 fb
    private Frag_search fs; //검색화면 객체 fs
    private Button btn;//검색창 옆 검색 버튼
    private String jdata;
    private JSONArray jsonArray;
    private String imageUrl;
    private String tm;
    private Bitmap mybitmap;
    private BookInfo bi;
    private String[] items = {"제목","저자"};
    private String text,mode;
    private String location;
    ArrayList<BookData> bookDataArrayList;

    private EditText searchView;



    @Override
    protected void onCreate(Bundle savedInstanceState) { //기본 Main 함수
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.searchView);
        Spinner spinner = findViewById(R.id.Search_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (items[i].equals("제목")){
                    mode = "1";
                }
                else{
                    mode = "2";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mode = "1";
            }
        });

        btn = (Button) findViewById(R.id.search_button);//id와 일치하는 버튼 호출
        btn.setOnClickListener(new View.OnClickListener() { //클릭 리스너
            @Override
            public void onClick(View view) {
                text = searchView.getText().toString();
                bookDataArrayList = new ArrayList<>();
                new t1().start();
            }
        });



        bottomNavigationView = findViewById(R.id.bottomNavi); //하단 바 (하단 네비게이션 바)
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) { //하단바 각 버튼에 프래그먼트 매칭
                switch (item.getItemId()){
                    case R.id.action_home: //첫 번째 선택 시 홈 프래그먼트
                        setFrag(0);
                        searchView.setVisibility(View.VISIBLE);     // 검색창 표시 (메인 레이아웃에서 레이아웃으로 검색창,버튼 묶어서 처리하기)
                        btn.setVisibility(View.VISIBLE);
                        spinner.setVisibility(View.VISIBLE);// 검색버튼 표시
                        break;
                    case R.id.action_map: //두번째 선택 시 도서관 맵 프래그먼트
                        setFrag(1);
                        searchView.setVisibility(View.GONE);    // 검색창 삭제
                        btn.setVisibility(View.GONE);
                        spinner.setVisibility(View.VISIBLE);// 검색버튼 삭제
                        break;
                    case R.id.action_bookinfo: //세번째 선택 시 대여정보 프래그먼트
                        setFrag(2);
                        searchView.setVisibility(View.GONE);    // 상동
                        btn.setVisibility(View.GONE);
                        spinner.setVisibility(View.VISIBLE);// 상동
                        break;
                }
                return true;
            }
        });
        fh = new Frag_home();
        fmp = new Frag_map();
        fb = new Frag_Rentalinfo();
        fs = new Frag_search();
        setFrag(0); //시작 fragment 화면 지정
    }

    //fragment 교체 함수
    //fragment 사용시 필수인듯 함...암튼 써야댐
    private void setFrag(int n){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n){
            case 0:
                ft.replace(R.id.main_frame, fh);
                ft.commit();
                break;
            case 1:

                ft.replace(R.id.main_frame, fmp);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, fb);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.main_frame, fs);
                ft.commit();
                break;
        }
    }

    class t1 extends Thread{
        public void run() {
            jdata = connect();  // DB접속 함수(return값 login 변수에 저장)
            if(jdata.equals("실패")){
                //Toast.makeText(getActivity(), "책정보 로딩 실패", Toast.LENGTH_SHORT).show();
            }else{//성공 시
                try {
                    jsonArray = new JSONArray(jdata);
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        imageUrl = jsonObject.getString("도서이미지");
                        tm = jsonObject.getString("제목");
                        String author = jsonObject.getString("저자");
                        String publisher = jsonObject.getString("출판사");
                        if(jsonObject.getString("층").equals("0")){
                            location = "대여중";
                        }
                        else{
                            location = jsonObject.getString("층") +"층 "+ jsonObject.getString("줄") +"줄 "+ jsonObject.getString("칸")+"칸";
                        }
                        String summary = jsonObject.getString("도서소개");
                        URL url1 = new URL(imageUrl);
                        HttpURLConnection conn = (HttpURLConnection)url1.openConnection();
                        conn.setDoInput(true); // 서버로부터 응답 수신
                        conn.connect(); //연결된 곳에 접속할 때 (connect() 호출해야 실제 통신 가능함)
                        InputStream is = conn.getInputStream(); //inputStream 값 가져오기
                        mybitmap = BitmapFactory.decodeStream(is);
                        bookDataArrayList.add(new BookData(mybitmap,tm,author,publisher,location,summary));
                    }
                    fs.setData(bookDataArrayList);
                    setFrag(3);

                } catch (JSONException | MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
            }

        }
    }

    private String connect() {     // JSP와 통신 메소드
        StringBuffer buf = new StringBuffer();  // JSP 화면에 뜨는 정보들 저장할 변수
        String urlPath = "http://172.20.10.5:8080/DbConn1/f_bookdb.jsp?keyword=" + text + "&mode=" + mode;   // id, pw 입력받아야함(한글 X)
        try {
            URL url = new URL(urlPath);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            if(con != null){
                con.setRequestMethod("POST");    // GET 또는 POST
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
            return buf.toString();    // buf를 int 타입으로 변경(1을 리턴하기 위해)

        }catch (Exception e){
            Log.i("mytag", e.getLocalizedMessage());    // 로그에서 코드 실행결과 확인코드
        }
        return "실패";
    }
}