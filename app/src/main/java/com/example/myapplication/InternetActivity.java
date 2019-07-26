package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class InternetActivity extends AppCompatActivity {
    String url = "https://www.googleapis.com/books/v1/volumes?q=pride+prejudice&maxResults=5&printType=books";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);

        BookTask bookTask = new BookTask();
        bookTask.execute(url);
    }
}
