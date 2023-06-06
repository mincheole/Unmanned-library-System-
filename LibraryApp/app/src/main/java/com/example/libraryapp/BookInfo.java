package com.example.libraryapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class BookInfo extends AppCompatActivity {
    ImageView imageView;
    TextView title;
    TextView author;
    TextView publisher;
    TextView location;
    TextView summary;
    ArrayList<BookData> bookDataArrayList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.view_bookinfo);
        this.imageView = (ImageView)this.findViewById(R.id.BookImage);
        this.title = (TextView)this.findViewById(R.id.BookTitle);
        this.author = (TextView)this.findViewById(R.id.Author);
        this.publisher = (TextView)this.findViewById(R.id.Publisher);
        this.location = (TextView)this.findViewById(R.id.Location);
        this.summary = (TextView)this.findViewById(R.id.Summary);
        Intent intent = this.getIntent();
        String tit = intent.getStringExtra("title");
        byte[] byteArray = getIntent().getByteArrayExtra("bookimage");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        this.imageView.setImageBitmap(bitmap);
        this.title.setText(tit);
        this.author.setText(intent.getStringExtra("author"));
        this.publisher.setText(intent.getStringExtra("publisher"));
        this.location.setText(intent.getStringExtra("location"));
        this.summary.setText(intent.getStringExtra("summary"));
    }
}
