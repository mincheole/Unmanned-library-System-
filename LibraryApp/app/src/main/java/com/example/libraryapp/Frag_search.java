package com.example.libraryapp;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

public class Frag_search extends Fragment { //검색화면 프래그먼트

    private View view;
    private String login;
    private String jdata;
    private JSONArray jsonArray;
    private String imageUrl;
    private String tm;
    private Bitmap mybitmap;
    ArrayList<BookData> foodInfoArrayList;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    public void setData(ArrayList<BookData> bookDataArrayList){
        this.foodInfoArrayList=bookDataArrayList;
    }
    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_search, container, false); //레이아웃 inflate로 객체화
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        MyAdapter myAdapter = new MyAdapter(foodInfoArrayList);
        mRecyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

        return view;
    }

//    Thread t1 = new Thread(new Runnable() {     // 로그인 기능은 메인 쓰레드에서 불가능(새로운 쓰레드 생성)
//        @Override
//        public void run() {
//            jdata = connect();  // DB접속 함수(return값 login 변수에 저장)
//            if(jdata.equals("실패")){
//                Toast.makeText(getActivity(), "책정보 로딩 실패", Toast.LENGTH_SHORT).show();
//            }else{//성공 시
//                try {
//                    jsonArray = new JSONArray(jdata);
//                    for(int i=0; i<jsonArray.length(); i++){
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        imageUrl = jsonObject.getString("이미지");
//                        tm = jsonObject.getString("제목");
//                        /*URL url1 = new URL("https://i.ibb.co/ZXNZkB8/image.jpg");
//                        HttpURLConnection conn = (HttpURLConnection)url1.openConnection();
//                        conn.setDoInput(true); // 서버로부터 응답 수신
//                        conn.connect(); //연결된 곳에 접속할 때 (connect() 호출해야 실제 통신 가능함)
//                        InputStream is = conn.getInputStream(); //inputStream 값 가져오기
//                        mybitmap = BitmapFactory.decodeStream(is);*/
//                    }
//                    /*Handler handler = new Handler(Looper.getMainLooper());  // Toast기능 MainActivity 이외에서 사용할 시 Handler 생성 필수!
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getActivity(), tm, Toast.LENGTH_SHORT).show();
//                        }
//                    });*/
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
//    });
//
//    private String connect() {     // JSP와 통신 메소드
//        StringBuffer buf = new StringBuffer();  // JSP 화면에 뜨는 정보들 저장할 변수
//        String urlPath = "http://112.157.208.197:8080/DbConn1/f_bookdb.jsp";   // id, pw 입력받아야함(한글 X)
//        try {
//            URL url = new URL(urlPath);
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            if(con != null){
//                con.setRequestMethod("POST");    // GET 또는 POST
//                con.setDoInput(true);
//                con.setDoOutput(true);
//                int code = con.getResponseCode();   // CODE가 200이면 접속성공(단지 사이트 접속여부, 로그인여부X)
//                Log.i("mytag", "RESOPNSE_CODE"+code);   // 로그에서 코드 실행결과(사이트 접속시 code200) 확인코드
//
//                InputStream input = con.getInputStream();   // 검색필요
//                BufferedReader reader = new BufferedReader(new InputStreamReader(input));   // 검색필요
//
//                String str = reader.readLine();
//                while(str!=null){
//                    buf.append(str);
//                    str = reader.readLine();
//                }
//
//                reader.close();
//            }
//            con.disconnect();
//            Log.i("mytag", "buf "+ buf.toString());     // 로그에서 코드 실행결과(buf값) 확인코드
//            return buf.toString();    // buf를 int 타입으로 변경(1을 리턴하기 위해)
//
//        }catch (Exception e){
//            Log.i("mytag", e.getLocalizedMessage());    // 로그에서 코드 실행결과 확인코드
//        }
//        return "실패";
//    }
}