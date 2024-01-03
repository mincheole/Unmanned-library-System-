package com.example.libraryapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Frag_AllRentalInfo extends Fragment {
    private ArrayList<AllrentalinfoData> rentalDateList = new ArrayList<>();
    private RentalAdapter rentalAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.frag_allrental, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_allrentalinfo);   // 리사이클러 초기화
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), DividerItemDecoration.VERTICAL));   // 구분선 옵션

//        rentalAdapter = new RentalAdapter(getActivity(), rentalDateList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setAdapter(rentalAdapter);

        new BackgroundThread().start();    // 쓰레드 호출
        return rootView;
    }

    private class BackgroundThread extends Thread {
        @Override
        public void run() {
            // 백그라운드 작업 수행
            ServerConnector.ConnectionParams params = new ServerConnector.ConnectionParams();
            params.setOption(5);
            String result = ServerConnector.connect(params);

            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String bookName = jsonObject.getString("제목");
                    String startRental = jsonObject.getString("대여일");
                    String endRental = jsonObject.getString("반납일");
                    rentalDateList.add(new AllrentalinfoData(bookName, startRental, endRental));
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            // UI 업데이트를 위해 메인 쓰레드에 메시지 전송
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // UI 업데이트 작업 수행
                    rentalAdapter = new RentalAdapter(getActivity(), rentalDateList);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(rentalAdapter);
                }
            });
        }
    }

}
