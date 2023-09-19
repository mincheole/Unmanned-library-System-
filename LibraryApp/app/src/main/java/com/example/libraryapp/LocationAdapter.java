package com.example.libraryapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
    ArrayList<LocationData> locationArraylist;

    public LocationAdapter(ArrayList<LocationData> Arraylist) {
        this.locationArraylist = Arraylist;
    }

    @NonNull
    @Override
    public LocationAdapter.LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booklocation_item, parent, false);
        LocationViewHolder locationViewHolder = new LocationViewHolder(view);
        return locationViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.LocationViewHolder holder, int position) {
        holder.tv_locationbookname.setText(locationArraylist.get(position).getBookName());
        holder.tv_locationbook.setText(locationArraylist.get(position).getBookLocation());
    }

    @Override
    public int getItemCount() {
        return (null != locationArraylist ? locationArraylist.size() : 0);
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView tv_locationbookname, tv_locationbook;
        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_locationbook = itemView.findViewById(R.id.tv_locaticationbook);
            tv_locationbookname = itemView.findViewById(R.id.tv_locationbookname);
        }
    }
}
