package com.example.libraryapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Frag_Rentalinfo extends Fragment implements View.OnClickListener{ //대여정보 프래그먼트
    private ArrayList<rentalData> rentaldata;    //어레이형식
    private Handler handler;
    private ListView rentalList;
    private static CustomAdapter customAdapter;
    private Button btn_allrentalinfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        handler = new Handler(Looper.getMainLooper());  // 쓰레드 사용을 위한 핸들러 생성(getMainLooper() 메인쓰레드호출)
        View view = inflater.inflate(R.layout.frag_rentalinfo, container, false); //레이아웃 inflate로 객체화
        btn_allrentalinfo = view.findViewById(R.id.btn_allRentalInfo);  // 프래그먼트 안에 버튼 생성을 위함
        btn_allrentalinfo.setOnClickListener(this);
        rentaldata = new ArrayList<>();

        new BackgroundThread().start();    // 쓰레드 호출
        return view;
    }

    @Override
    public void onClick(View view) {
        ((MainActivity)requireActivity()).setFrag(5);   // 모든 대출기록 호출
    }

    private class BackgroundThread extends Thread {
        @Override
        public void run() {
            // 백그라운드 작업 수행
            ServerConnector.ConnectionParams params = new ServerConnector.ConnectionParams();
            params.setOption(2);
            String result = ServerConnector.connect(params);

            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String bookName = jsonObject.getString("제목");
                    String startRental = jsonObject.getString("대여일");
                    String endRental = jsonObject.getString("반납예정일");
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