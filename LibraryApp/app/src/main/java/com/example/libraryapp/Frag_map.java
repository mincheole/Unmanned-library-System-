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

public class Frag_map extends Fragment implements View.OnClickListener{ //도서관 맵 프래그먼트(버튼 이용을 위해 OnClickListener 인터페이스 이용)
    private View view;

    private Button first_btn, second_btn, third_btn;

    private ImageView first_map, second_map, third_map;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_map, container, false); //레이아웃 inflate로 객체화

        // 층별 버튼 view 연결(id)
        first_btn = view.findViewById(R.id.first_btn);
        second_btn = view.findViewById(R.id.second_btn);
        third_btn = view.findViewById(R.id.third_btn);

        // 주석필요!
        first_btn.setOnClickListener(this);
        second_btn.setOnClickListener(this);
        third_btn.setOnClickListener(this);

        first_map = view.findViewById(R.id.first_map);
        second_map = view.findViewById(R.id.second_map);
        third_map = view.findViewById(R.id.third_map);

        // Drawable에 있는 이미지 설정
        first_map.setImageResource(R.drawable.first_floor);
        second_map.setImageResource(R.drawable.second_floor);
        third_map.setImageResource(R.drawable.third_floor);

        // 초기화면 맵 설정(1층)
        first_map.setVisibility(View.VISIBLE);
        second_map.setVisibility(View.INVISIBLE);
        third_map.setVisibility(View.INVISIBLE);


        return view;
    }

    @Override
    public void onClick(View view) {    // 버튼 클릭을 위한 함수
        switch (view.getId()){      // 조건문(버튼 클릭 값을 아래의 case와 비교 후 조건에 맞는 case를 실행)
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
