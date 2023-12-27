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

import java.util.ArrayList;

public class Frag_AllRentalInfo extends Fragment {
    private ArrayList<AllrentalinfoData> rentalDateList = new ArrayList<>();
    private RentalAdapter rentalAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        for (int i = 0; i<5; i++) {
            rentalDateList.add(new AllrentalinfoData("AAA", "BBB", "CCC"));
        }

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.frag_allrental, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_allrentalinfo);   // 리사이클러 초기화
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), DividerItemDecoration.VERTICAL));   // 구분선 옵션

        rentalAdapter = new RentalAdapter(getActivity(), rentalDateList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(rentalAdapter);

        return rootView;
    }


}
