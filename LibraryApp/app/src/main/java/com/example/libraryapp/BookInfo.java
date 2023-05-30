package com.example.libraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class BookInfo extends AppCompatActivity {
    ImageView imageView;
    TextView title;
    ArrayList<BookData> bookDataArrayList;

    public BookInfo() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.view_bookinfo);
        this.imageView = (ImageView)this.findViewById(R.id.BookImage);
        this.title = (TextView)this.findViewById(R.id.BookTitle);
        Intent intent = this.getIntent();
        this.bookDataArrayList = (ArrayList)intent.getSerializableExtra("bookData");
        int num = intent.getExtras().getInt("index");
        this.imageView.setImageResource(((BookData)this.bookDataArrayList.get(num)).drawableId);
        this.title.setText(((BookData)this.bookDataArrayList.get(num)).title);
    }
}
