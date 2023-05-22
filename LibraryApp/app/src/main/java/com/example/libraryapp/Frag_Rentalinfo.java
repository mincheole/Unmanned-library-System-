package com.example.libraryapp;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class Frag_Rentalinfo extends Fragment { //대여정보 프래그먼트

    private SearchView searchView;

    public Frag_Rentalinfo() {

    }

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_rentalinfo, container, false); //레이아웃 inflate로 객체화

        String[] value = new String[] {"이동호", "김민철", "남태웅", "김동호", "박민철", "이태웅"};

//        List<String> data = new ArrayList<>();    //어레이형식
//        data.add("이동호");
//        data.add("김민철");
//        data.add("남태웅");

        ListView rentalList = view.findViewById(R.id.rental_List);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, value);
        rentalList.setAdapter(adapter);
        return view;
    }
}
