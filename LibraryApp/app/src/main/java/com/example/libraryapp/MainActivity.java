package com.example.libraryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView; //하단 바
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Frag_home fh; //홈 화면 객체 fh
    private Frag_map fmp; //도서관 맵 화면 객체 fmp
    private Frag_bookinfo fb; //대여정보 화면 객체 fb
    private Frag_search fs; //검색화면 객체 fs
    private Button btn;//검색창 옆 검색 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) { //기본 Main 함수
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.search_button);//id와 일치하는 버튼 호출
        btn.setOnClickListener(new View.OnClickListener() { //클릭 리스너
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Frag_search()).commit();
            }
        });

        bottomNavigationView = findViewById(R.id.bottomNavi); //하단 바 (하단 네비게이션 바)
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) { //하단바 각 버튼에 프래그먼트 매칭
                switch (item.getItemId()){
                    case R.id.action_home: //첫 번째 선택 시 홈 프래그먼트
                        setFrag(0);
                        break;
                    case R.id.action_map: //두번째 선택 시 도서관 맵 프래그먼트
                        setFrag(1);
                        break;
                    case R.id.action_bookinfo: //세번째 선택 시 대여정보 프래그먼트
                        setFrag(2);
                        break;
                }
                return true;
            }
        });
        fh = new Frag_home();
        fmp = new Frag_map();
        fb = new Frag_bookinfo();
        fs = new Frag_search();
        setFrag(0); //시작 fragment 화면 지정
    }

    //fragment 교체 함수
    //fragment 사용시 필수인듯 함...암튼 써야댐
    private void setFrag(int n){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n){
            case 0:
                ft.replace(R.id.main_frame, fh);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, fmp);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, fb);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.main_frame, fs);
                ft.commit();
                break;
        }
    }
}