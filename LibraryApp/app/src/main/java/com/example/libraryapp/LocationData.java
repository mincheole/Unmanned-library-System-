package com.example.libraryapp;

public class LocationData {
    private String bookName;
    private String bookLocation;

    public LocationData(String bookName, String bookLocation) {
        this.bookName = bookName;
        this.bookLocation = bookLocation;
    }

    public String getBookName() {
        return bookName;
    }

    public String getBookLocation() {
        return bookLocation;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setBookLocation(String bookLocation) {
        this.bookLocation = bookLocation;
    }
}
