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
        public TextView tv_author;
        public TextView tv_deadLine;
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
        viewHolder.tv_author = convertView.findViewById(R.id.author);
        viewHolder.tv_deadLine = convertView.findViewById(R.id.deadLine);

        final rentalData rentalData = (rentalData) list.get(position);
        viewHolder.tv_bookName.setText(rentalData.getBookName());
        viewHolder.tv_author.setText(rentalData.getAuthor());
        viewHolder.tv_deadLine.setText(rentalData.getDeadLine());

        return convertView;
    }
}
