package com.example.foodmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Details extends AppCompatActivity {
    Button btnsender,btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
         btnsender=findViewById(R.id.buttonss);
         btn2=findViewById(R.id.button3);



        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Details.this,Sender.class);
                intent.putExtra("role","Receiver");
                startActivity(intent);

            }
        });


        btnsender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Details.this,Sender.class);
                intent.putExtra("role","Sender");
                startActivity(intent);
            }
        });
    }
}