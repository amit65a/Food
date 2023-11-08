package com.example.foodmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TestActivity extends AppCompatActivity {
String[] arrData = {"Ram","Shyam","harry"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

//        ListView listView = findViewById(R.id.listView);
////
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.list_item,R.id.textViewItem,arrData);
//        listView.setAdapter(adapter);

    }
}