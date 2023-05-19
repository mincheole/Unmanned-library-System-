package com.example.libraryapp;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frag_Rentalinfo extends Fragment { //대여정보 프래그먼트

    public Frag_Rentalinfo() {

    }

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_rentalinfo, container, false); //레이아웃 inflate로 객체화

        String[] value = new String[] {"이동호", "김민철", "남태웅", "김동호", "박민철", "이태웅"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, value);

        return view;
    }
}
