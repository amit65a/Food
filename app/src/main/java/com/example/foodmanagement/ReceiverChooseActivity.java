package com.example.foodmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReceiverChooseActivity extends AppCompatActivity {

    Button bookBtn,showBookedBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_choose);
        bookBtn = findViewById(R.id.bookOrderBtn);
        showBookedBtn = findViewById(R.id.showBookOrderBtn);

        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(new Intent(ReceiverChooseActivity.this,Home.class));
                startActivity(intent);
            }
        });
        showBookedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(new Intent(ReceiverChooseActivity.this,ShowBookedActivity.class));
                startActivity(intent);

            }
        });


    }
}