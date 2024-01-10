package com.example.libraryapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookInfo extends AppCompatActivity {
    private ImageView imageView;
    private TextView title;
    private String isbn;
    private TextView author;
    private TextView publisher;
    private TextView summary;
    private ArrayList<LocationData> locationList;   // 데이터를 담기 위한 어레이리스트
    private LocationAdapter locationAdapter;        // 어댑터
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView locationRecyclerView;
    private Dialog locationDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.view_bookinfo);

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
        isbn = intent.getStringExtra("isbn");

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

                new BackgroundThread().start();    // 쓰레드 호출

                showLocation();     // 도서위치 다이얼로그
            }
        });

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

    private class BackgroundThread extends Thread {
        @Override
        public void run() {
            // 백그라운드 작업 수행
            ServerConnector.ConnectionParams params = new ServerConnector.ConnectionParams();
            params.setOption(6).setIsbn(isbn);
            String result = ServerConnector.connect(params);

            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String bookNumber = jsonObject.getString("도서번호");
                    String bookLocation = jsonObject.getString("위치");
                    locationList.add(new LocationData(bookNumber, bookLocation));
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            // UI 업데이트를 위해 메인 쓰레드에 메시지 전송
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // UI 업데이트 작업 수행
                    locationAdapter = new LocationAdapter(locationList);    // 어댑터에 어레이리스트 set
                    locationRecyclerView.setAdapter(locationAdapter);   // 리사이클러뷰에 어댑터 set
                    locationAdapter.notifyDataSetChanged();
                }
            });
        }
    }
}
