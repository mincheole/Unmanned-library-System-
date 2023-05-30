package com.example.libraryapp;

import java.io.Serializable;

public class BookData implements Serializable {
        public int drawableId;
        public String title;

        public BookData(int drawableId, String price){
            this.drawableId = drawableId;
            this.title = price;
        }
}
