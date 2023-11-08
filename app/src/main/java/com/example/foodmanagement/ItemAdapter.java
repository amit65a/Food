package com.example.foodmanagement;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private ArrayList<ItemList> item;



    public ItemAdapter(ArrayList<ItemList> Item) {
        this.item = Item;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ItemList order = item.get(position);

        // Bind the order data to the ViewHolder's views
        holder.itemName.setText("Name");
        Log.e("name",order.getName());
//        Log.e("Qty",String.valueOf(order.getQty()));
        holder.itemName.setText(order.getName());
        holder.itemQty.setText(order.getQty());

        // Set other views as needed
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemQty;

        // Define other views here

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemQty = itemView.findViewById(R.id.itemQty);



            // Initialize other views here
        }
    }
}
