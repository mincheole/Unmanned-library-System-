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
                        spinner.setVisibility(View.GONE);// 검색버튼 삭제
                        break;
                    case R.id.action_bookinfo: //세번째 선택 시 대여정보 프래그먼트
                        setFrag(2);
                        searchView.setVisibility(View.GONE);    // 상동
                        btn.setVisibility(View.GONE);
                        spinner.setVisibility(View.GONE);// 상동
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
            jdata = connect();  //Jsp로 부터 값을 전달 받음
            if(jdata.equals("실패")){ //Jsp 연결 실패 시 Toast 메세지로 띄워줌
                //Toast.makeText(this, "책정보 로딩 실패", Toast.LENGTH_SHORT).show();
            }else{//성공 시
                try {
                    jsonArray = new JSONArray(jdata);//현 Jsp서버는 JsonArray형태 이므로 넘겨받은 String을 Array형식으로 저장
                    for(int i=0; i<jsonArray.length(); i++){//넘겨받은 Array를 모두 추출
                        JSONObject jsonObject = jsonArray.getJSONObject(i);//Array안에 Key:Values형태의 Object로 추출
                        imageUrl = jsonObject.getString("도서이미지");//책 이미지 URL 저장
                        tm = jsonObject.getString("제목");           //책 제목 저장
                        String author = jsonObject.getString("저자");//책 저자 저장
                        String publisher = jsonObject.getString("출판사");//출판사 저장
                        if(jsonObject.getString("층").equals("0")){//책장에 책이 없으면 "층"데이터에 0이 들어감
                            location = "대여중";                         //책이 없으면 "위치"를 대여중으로 실시간 표시함
                        }
                        else{//책장에 책이 존재하면 정상적으로 위치를 출력
                            location = jsonObject.getString("층") +"층 "+ jsonObject.getString("줄") +"줄 "+ jsonObject.getString("칸")+"칸";
                        }
                        String summary = jsonObject.getString("도서소개");//책 소개 저장
                        URL url1 = new URL(imageUrl);
                        HttpURLConnection conn = (HttpURLConnection)url1.openConnection();//책 URL로 Http 통신
                        conn.setDoInput(true); // 서버로부터 응답 수신
                        conn.connect(); //연결된 곳에 접속할 때 (connect() 호출해야 실제 통신 가능함)
                        InputStream is = conn.getInputStream(); //inputStream 값 가져오기
                        mybitmap = BitmapFactory.decodeStream(is);//이미지 Stream을 비트맵으로 저장
                        bookDataArrayList.add(new BookData(mybitmap,tm,author,publisher,location,summary));//값들을 List에 저장
                    }
                    fs.setData(bookDataArrayList);//검색화면으로 데이터 List를 전달
                    setFrag(3);//App Ui 화면 전환

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
        String urlPath = "http://whereisthebook.kro.kr:8080/DbConn1/f_bookdb.jsp?keyword=" + text + "&mode=" + mode;   // 제목,저자 입력받아야함(한글 X)
        try {
            URL url = new URL(urlPath);//Jsp 경로를 변수에 저장
            HttpURLConnection con = (HttpURLConnection) url.openConnection();//안드로이드 Http 통신으로 연결
            if(con != null){
                con.setRequestMethod("POST");    // Http를 GET 또는 POST 방식으로 설정
                con.setDoInput(true);
                con.setDoOutput(true);
                int code = con.getResponseCode();   // CODE가 200이면 접속성공(단지 사이트 접속여부, 로그인여부X)
                Log.i("mytag", "RESOPNSE_CODE"+code);   // 로그에서 코드 실행결과(사이트 접속시 code200) 확인코드

                InputStream input = con.getInputStream(); //Stream 형식으로 전환
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));//버퍼에 저장

                String str = reader.readLine();//버퍼의 데이터를 String 형식으로 전환
                while(str!=null){
                    buf.append(str);//버퍼의 모든 데이터를 변수에 저장
                    str = reader.readLine();
                }

                reader.close();
            }
            con.disconnect();//Http 연결 해제
            Log.i("mytag", "buf "+ buf.toString());     // 로그에서 코드 실행결과(buf값) 확인코드
            return buf.toString();    // buf를 string 타입으로(json 값들을 받기 위해해)

       }catch (Exception e){
            Log.i("mytag", e.getLocalizedMessage());    // 로그에서 코드 실행결과 확인코드
        }
        return "실패";
    }
}