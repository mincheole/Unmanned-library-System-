package com.example.libraryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter {
    private Context context;
    private List list;

    class ViewHolder{
        public TextView tv_bookName;
        public TextView tv_startRental;
        public TextView tv_endRental;
    }

    public CustomAdapter(@NonNull Context context, ArrayList list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;

        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.listview_item, parent, false);
        }

        viewHolder = new ViewHolder();
        viewHolder.tv_bookName = convertView.findViewById(R.id.bookName);
        viewHolder.tv_startRental = convertView.findViewById(R.id.statRental);
        viewHolder.tv_endRental = convertView.findViewById(R.id.endRental);

        final rentalData rentalData = (rentalData) list.get(position);
        viewHolder.tv_bookName.setText(rentalData.getBookName());
        viewHolder.tv_startRental.setText(rentalData.getstartRental());
        viewHolder.tv_endRental.setText(rentalData.getendRental());

        return convertView;
    }
}
