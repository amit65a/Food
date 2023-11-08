package com.example.foodmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SenderChooseActivity extends AppCompatActivity {

    Button addNewOrderBtn,showMyOrderBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender_choose);

        addNewOrderBtn = findViewById(R.id.addOrderBtn);
        showMyOrderBtn = findViewById(R.id.showMyOrderBtn);

        addNewOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SenderChooseActivity.this,SenderHome.class));
            }
        });

        showMyOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SenderChooseActivity.this,ShowMyOrder.class));
            }
        });

    }
}