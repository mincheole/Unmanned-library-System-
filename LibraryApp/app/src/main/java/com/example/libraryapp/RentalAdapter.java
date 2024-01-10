package com.example.libraryapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RentalAdapter extends RecyclerView.Adapter<RentalAdapter.ViewHolder> {
    private ArrayList<AllrentalinfoData> AllData_arrayList;

    public RentalAdapter(FragmentActivity activity, ArrayList<AllrentalinfoData> allData_arrayList) {
        AllData_arrayList = allData_arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allrentalinfo_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bookName.setText(AllData_arrayList.get(position).getBookName());
        holder.startDate.setText(AllData_arrayList.get(position).getStartDate());
        holder.endDate.setText(AllData_arrayList.get(position).getEndDate());
    }

    @Override
    public int getItemCount() {
        return (null != AllData_arrayList ? AllData_arrayList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView bookName, startDate, endDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.allbookName);
            startDate = itemView.findViewById(R.id.allstatRental);
            endDate = itemView.findViewById(R.id.allendRental);
        }
    }
}
