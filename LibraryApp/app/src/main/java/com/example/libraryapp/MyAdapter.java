package com.example.libraryapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<BookData> bookDataArrayList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPicture;
        TextView tvPrice;

        MyViewHolder(View view){
            super(view);
            ivPicture = view.findViewById(R.id.iv_picture);
            tvPrice = view.findViewById(R.id.tv_title);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    Intent intent = new Intent(view.getContext(),BookInfo.class);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    Bitmap bitmap = ((BitmapDrawable)ivPicture.getDrawable()).getBitmap();
                    float scale = (float) (1024/(float)bitmap.getWidth());
                    int image_w = (int) (bitmap.getWidth() * scale);
                    int image_h = (int) (bitmap.getHeight() * scale);
                    Bitmap resize = Bitmap.createScaledBitmap(bitmap, image_w, image_h, true);
                    resize.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("bookimage",byteArray);
                    intent.putExtra("title",bookDataArrayList.get(pos).title);
                    intent.putExtra("author",bookDataArrayList.get(pos).author);
                    intent.putExtra("publisher",bookDataArrayList.get(pos).publisher);
                    intent.putExtra("location",bookDataArrayList.get(pos).location);
                    intent.putExtra("summary",bookDataArrayList.get(pos).summary);
                    view.getContext().startActivity(intent);
                }
            });
        }
    }


    MyAdapter(ArrayList<BookData> bookDataArrayList){
        this.bookDataArrayList = bookDataArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.ivPicture.setImageBitmap(bookDataArrayList.get(position).drawableId);
        myViewHolder.tvPrice.setText(bookDataArrayList.get(position).title);
    }

    @Override
    public int getItemCount() {
        return bookDataArrayList.size();
    }
}

