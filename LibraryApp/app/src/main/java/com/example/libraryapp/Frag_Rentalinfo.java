package com.example.libraryapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Frag_Rentalinfo extends Fragment { //대여정보 프래그먼트
    ArrayList<rentalData> rentaldata;    //어레이형식
    private Handler handler;
    ListView rentalList;
    String result;
    static String ID;   // LoginActivity로 부터 사용자ID 받아올 정적변수
    private static CustomAdapter customAdapter;
    LoginActivity logAct;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        handler = new Handler(Looper.getMainLooper());  // 쓰레드 사용을 위한 핸들러 생성(getMainLooper() 메인쓰레드호출)
        logAct = new LoginActivity();
        View view = inflater.inflate(R.layout.frag_rentalinfo, container, false); //레이아웃 inflate로 객체화
        rentaldata = new ArrayList<>();

        new BackgroundThread().start();    // 쓰레드 호출
//        rentaldata.add(new rentalData("니체의말", "김민철", "2020"));
        return view;
    }

    private class BackgroundThread extends Thread {
        @Override
        public void run() {
            // 백그라운드 작업 수행

            logAct.id = ID;
            String result = logAct.connect(2);

            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String bookName = jsonObject.getString("책제목");
                    String startRental = jsonObject.getString("대여일");
                    String endRental = jsonObject.getString("반납일");
                    rentaldata.add(new rentalData(bookName, startRental, endRental));
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            // UI 업데이트를 위해 메인 쓰레드에 메시지 전송
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // UI 업데이트 작업 수행
                    rentalList = getView().findViewById(R.id.rental_List);
                    customAdapter = new CustomAdapter(getContext(), rentaldata);
                    rentalList.setAdapter(customAdapter);
                }
            });
        }
    }

}

class rentalData{
    private String bookName;
    private String startRental;
    private String endRental;

    public rentalData(String bookName, String startRental, String endRental) {
        this.bookName = bookName;
        this.startRental = startRental;
        this.endRental = endRental;
    }

    public String getBookName() {
        return bookName;
    }

    public String getstartRental() {
        return startRental;
    }

    public String getendRental() {
        return endRental;
    }
}