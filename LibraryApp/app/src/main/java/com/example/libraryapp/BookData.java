package com.example.libraryapp;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class BookData implements Serializable {
        public String drawableId;
        public String title;
        String author;
        String publisher;
        String isbn;
        String summary;

        public BookData(String drawableId, String price,String author,String publisher,String isbn,String summary){
            this.drawableId = drawableId;
            this.title = price;
            this.author = author;
            this.publisher = publisher;
            this.isbn = isbn;
            this.summary = summary;
        }


}
