package com.example.libraryapp;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class Frag_Rentalinfo extends Fragment { //대여정보 프래그먼트

    ArrayList<rentalData> rentaldata;    //어레이형식
    ListView rentalList;
    private static CustomAdapter customAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.frag_rentalinfo, container, false); //레이아웃 inflate로 객체화

        rentaldata = new ArrayList<>();
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
    private String author;
    private String deadLine;

    public rentalData(String bookName, String author, String deadLine) {
        this.bookName = bookName;
        this.author = author;
        this.deadLine = deadLine;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthor() {
        return author;
    }

    public String getDeadLine() {
        return deadLine;
    }
}