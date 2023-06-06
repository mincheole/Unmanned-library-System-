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

import java.util.ArrayList;
import java.util.List;
import java.net.HttpURLConnection;

public class Frag_Rentalinfo extends Fragment { //대여정보 프래그먼트

    ArrayList<rentalData> rentaldata;    //어레이형식
    ListView rentalList;
    String result;

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
                result = logAct.connect(2  );
            }
        });
        t2.start();

//        et_id = view.findViewById(R.id.et_id);
//        Log.i("mytag", result);

        rentaldata.add(new rentalData("파이썬", "이동호", "2023"));
        rentaldata.add(new rentalData("니체의말", "김민철", "2020"));
        rentaldata.add(new rentalData("명품C++", "남태웅", "2024"));
        rentaldata.add(new rentalData("자바의 정석", "남궁성", "2030"));
        rentaldata.add(new rentalData("소프트웨어 공학", "장성진", "2050"));
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