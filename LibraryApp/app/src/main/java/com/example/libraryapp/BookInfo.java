package com.example.libraryapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookInfo extends AppCompatActivity {
    ImageView imageView;
    TextView title;
    TextView author;
    TextView publisher;
    TextView summary;
    ArrayList<LocationData> locationList;   // 데이터를 담기 위한 어레이리스트
    LocationAdapter locationAdapter;        // 어댑터
    LinearLayoutManager linearLayoutManager;
    RecyclerView locationRecyclerView;
    Dialog locationDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.view_bookinfo);

        locationDialog = new Dialog(BookInfo.this);     // Dialog(도서 위치 확인을 위함)
        locationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        locationDialog.setContentView(R.layout.dialog_booklocation);

        findViewById(R.id.btn_findLocation).setOnClickListener(new View.OnClickListener() {     // 위치보기 버튼 클릭시 Dialog 창 출력
            @Override
            public void onClick(View view) {
                locationRecyclerView =  (RecyclerView) locationDialog.findViewById(R.id.recycler_booklocation);       // 리사이클러뷰 설정(다이얼로그 안에 리사이클러 연결하는법)
                locationRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));     // 구분선 넣어주는 옵션
                linearLayoutManager = new LinearLayoutManager(BookInfo.this);    // 레이아웃 매니저
                locationRecyclerView.setLayoutManager(linearLayoutManager);     // 리사이클러뷰에 레이아웃 매니저 설정
                locationList= new ArrayList<>();    // 어레이리스트 초기화
                locationAdapter = new LocationAdapter(locationList);    // 어댑터에 어레이리스트 set
                locationRecyclerView.setAdapter(locationAdapter);   // 리사이클러뷰에 어댑터 set

                for(int i = 0; i < 5; i++){
                    LocationData locationData = new LocationData(i + "번 책", i + "-" + i + "-" + i);
                    locationList.add(locationData);
                    locationAdapter.notifyDataSetChanged();
                }
                showLocation();
            }
        });



        this.imageView = (ImageView)this.findViewById(R.id.BookImage);
        this.title = (TextView)this.findViewById(R.id.BookTitle);
        this.author = (TextView)this.findViewById(R.id.Author);
        this.publisher = (TextView)this.findViewById(R.id.Publisher);
        this.summary = (TextView)this.findViewById(R.id.Summary);

        Intent intent = this.getIntent();
        String tit = intent.getStringExtra("title");
        byte[] byteArray = getIntent().getByteArrayExtra("bookimage");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        this.imageView.setImageBitmap(bitmap);
        this.title.setText(tit);
        this.author.setText(intent.getStringExtra("author"));
        this.publisher.setText(intent.getStringExtra("publisher"));
        this.summary.setText(intent.getStringExtra("summary"));
    }

    protected void showLocation(){
        locationDialog.show();      // 도서위치 다이얼로그 띄우기

        locationDialog.findViewById(R.id.btn_locationYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationDialog.dismiss();
            }
        });
    }
}
