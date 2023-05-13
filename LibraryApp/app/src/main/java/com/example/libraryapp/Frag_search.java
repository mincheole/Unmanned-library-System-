package com.example.libraryapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Frag_search extends Fragment { //검색화면 프래그먼트

    ArrayList<Book> books; //Book 클래스 Arraylist
    ListView customListView; //리스트뷰 선언
    private static CustomAdapter customAdapter; //리스트뷰 쓸때 사용되는 어댑터
    private View view; // 뷰 선언

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_search, container, false); //레이아웃 inflate로 객체화
        books = new ArrayList<>();
        //데이터 직접 넣는 부분
        books.add(new Book("달러구트 꿈 백화점","@drawable/img","책 설명_1"));
        books.add(new Book("돌이킬 수 없는 약속","@drawable/img_1","책 설명_2"));
        books.add(new Book("순리자","@drawable/img_2","책 설명_3"));
        books.add(new Book("팩트풀니스","@drawable/img_3","책 설명_4"));
        books.add(new Book("도둑맞은 집중력","@drawable/img_4","책 설명_5"));
        books.add(new Book("고래","@drawable/img_5","책 설명_6"));
        books.add(new Book("품","@drawable/img_6","책 설명_7"));

        customListView = (ListView) view.findViewById(R.id.Book_ListView); //id와 일치하는 레이아웃 호출
        customAdapter = new CustomAdapter(getContext(), books);
        customListView.setAdapter(customAdapter);//리스트뷰에 어댑터 달기
        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                //각 아이템을 분간 할 수 있는 position과 뷰
                String selectedItem = (String) view.findViewById(R.id.textView_name).getTag().toString();
                Toast.makeText(getContext(), "Clicked: " + position +" " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}

class Book { //Book 데이터 담는 클래스
    private String name; // 책 제목
    private String summary; //책 내용
    private String thumb_url; //이미지

    public Book(String name, String thumb_url, String summary) { //초기화
        this.name = name;
        this.summary = summary;
        this.thumb_url = thumb_url;
    }

    public String getName() { //각 멤버들 값 리턴
        return name;
    }

    public String getSummary() {
        return summary;
    }

    public String getThumb_url() {
        return thumb_url;
    }
}