package com.example.foodmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;

public class ShowDataActivity extends AppCompatActivity  {

    ItemAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);



        Order order = (Order) getIntent().getSerializableExtra("order");
        if(order.getItemLists()!=null){
            adapter = new ItemAdapter(order.getItemLists());

            RecyclerView recyclerView = findViewById(R.id.recyclerViewItem);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            // Populate this with your order data


            recyclerView.setAdapter(adapter);
        }



    }
}