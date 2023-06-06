package com.example.libraryapp;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.net.HttpURLConnection;

public class Frag_Rentalinfo extends Fragment { //대여정보 프래그먼트

    ArrayList<rentalData> rentaldata;    //어레이형식
    ListView rentalList;
    String result;
    static String ID;   // LoginActivity로 부터 사용자ID 받아올 정적변수

    private static CustomAdapter customAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.frag_rentalinfo, container, false); //레이아웃 inflate로 객체화

        rentaldata = new ArrayList<>();
        LoginActivity logAct  = new LoginActivity();
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                logAct.id = ID;     // LoginActivity에서 받아온 사용자id를 다시 LoginActivity로 넘겨주기
                result = logAct.connect(2  );   // 2일때 유저의 대여정보에 접속(JSP)
            }
        });
        t2.start();

        // 쓰레드 안에 넣는법 찾아야함
//        try { 
//            JSONArray jsonArray = new JSONArray(result);
//            for(int i = 0; i<jsonArray.length(); i++){
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                rentaldata.add(new rentalData(jsonObject.getString("책제목"), jsonObject.getString("대여일"), jsonObject.getString("반납일")));
//            }
//
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }


        rentaldata.add(new rentalData("니체의말", "김민철", "2020"));
//        rentaldata.add(new rentalData("명품C++", "남태웅", "2024"));
//        rentaldata.add(new rentalData("자바의 정석", "남궁성", "2030"));
//        rentaldata.add(new rentalData("소프트웨어 공학", "장성진", "2050"));
        rentalList = view.findViewById(R.id.rental_List);
        customAdapter = new CustomAdapter(getContext(), rentaldata);
        rentalList.setAdapter(customAdapter);

        return view;
    }
}

class rentalData{
    private String bookName;
    private String startRental;
    private String endRental;

    public rentalData(String bookName, String startRental, String endRental) {
        this.bookName = bookName;
        this.startRental = startRental;
        this.endRental = endRental;
    }

    public String getBookName() {
        return bookName;
    }

    public String getstartRental() {
        return startRental;
    }

    public String getendRental() {
        return endRental;
    }
}