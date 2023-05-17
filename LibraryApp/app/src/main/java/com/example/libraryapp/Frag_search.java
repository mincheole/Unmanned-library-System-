package com.example.libraryapp;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Frag_search extends Fragment { //검색화면 프래그먼트

    private View view;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_search, container, false); //레이아웃 inflate로 객체화
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<BookData> foodInfoArrayList = new ArrayList<>();
        foodInfoArrayList.add(new BookData(R.drawable.img, "달러구트 꿈 백화점"));
        foodInfoArrayList.add(new BookData(R.drawable.img_1, "돌이킬 수 없는 약속"));
        foodInfoArrayList.add(new BookData(R.drawable.img_2, "순리자"));
        foodInfoArrayList.add(new BookData(R.drawable.img_3, "팩트풀니스"));
        foodInfoArrayList.add(new BookData(R.drawable.img_4, "도둑맞은 집중력\n"));
        foodInfoArrayList.add(new BookData(R.drawable.img_5, "고래"));
        foodInfoArrayList.add(new BookData(R.drawable.img_6, "품"));

        MyAdapter myAdapter = new MyAdapter(foodInfoArrayList);

        mRecyclerView.setAdapter(myAdapter);
        return view;
    }
}