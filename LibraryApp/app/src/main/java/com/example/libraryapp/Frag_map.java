package com.example.libraryapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frag_map extends Fragment implements View.OnClickListener{ //도서관 맵 프래그먼트
    private View view;

    private Button first_btn, second_btn, third_btn;

    private ImageView first_map, second_map, third_map;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_map, container, false); //레이아웃 inflate로 객체화

        first_btn = view.findViewById(R.id.first_btn);
        second_btn = view.findViewById(R.id.second_btn);
        third_btn = view.findViewById(R.id.third_btn);

        first_btn.setOnClickListener(this);
        second_btn.setOnClickListener(this);
        third_btn.setOnClickListener(this);

        first_map = view.findViewById(R.id.first_map);
        second_map = view.findViewById(R.id.second_map);
        third_map = view.findViewById(R.id.third_map);

        first_map.setImageResource(R.drawable.first_floor);
        second_map.setImageResource(R.drawable.second_floor);
        third_map.setImageResource(R.drawable.third_floor);

        first_map.setVisibility(View.VISIBLE);
        second_map.setVisibility(View.INVISIBLE);
        third_map.setVisibility(View.INVISIBLE);


        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.first_btn:
                first_map.setVisibility(View.VISIBLE);
                second_map.setVisibility(View.INVISIBLE);
                third_map.setVisibility(View.INVISIBLE);
                break;

            case R.id.second_btn:
                first_map.setVisibility(View.INVISIBLE);
                second_map.setVisibility(View.VISIBLE);
                third_map.setVisibility(View.INVISIBLE);
                break;

            case R.id.third_btn:
                first_map.setVisibility(View.INVISIBLE);
                second_map.setVisibility(View.INVISIBLE);
                third_map.setVisibility(View.VISIBLE);
        }
    }
}
