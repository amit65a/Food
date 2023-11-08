package com.example.foodmanagement;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    String URL = "https://foodapi-tsul.onrender.com/api/v1/bookFood";
    private ArrayList<Order> orders;
    private Context context;
    public interface OnItemClickListener {
        void onItemClick(Order order);
    }

    private OnItemClickListener onItemClickListener;

    public OrderAdapter(ArrayList<Order> orders, OnItemClickListener onItemClickListener,Context context) {
        this.orders = orders;
        this.onItemClickListener = onItemClickListener;
        this.context = context;
    }


    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("profile", null);
        Gson gson = new Gson();
        SessionDetails sessionDetails = gson.fromJson(json,SessionDetails.class);




        if(!order.isBooked() && !sessionDetails.getEmail().toString().equals(order.getEmail().toString())){
            holder.bookBtn.setVisibility(View.VISIBLE);
        }
        // Bind the order data to the ViewHolder's views
        holder.emailTextView.setText(order.getEmail());
        holder.phoneTextView.setText(order.getPhone());
        holder.nameTextView.setText(order.getName());
        holder.addressTextView.setText(order.getAddress());
        holder.bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.bookBtn.setText("Please wait,Booking");
                JSONObject jsonParams = new JSONObject();
                try {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    String json = sharedPreferences.getString("profile", null);
                    Gson gson = new Gson();
                    SessionDetails sessionDetails = gson.fromJson(json,SessionDetails.class);

                    jsonParams.put("useremail", sessionDetails.getEmail());
                    jsonParams.put("id", order.getId());


                    // Add more key-value pairs as needed
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonParams,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Handle the response
                               if(response!=null){

                                   try {
                                       boolean res = response.getBoolean("success");
                                       if(res){
                                           holder.bookBtn.setText("Booked");
                                       }else {
                                           holder.bookBtn.setText("Not Booked");
                                       }
                                   } catch (JSONException e) {
                                       holder.bookBtn.setText("Error");
                                   }
                               }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Handle errors
                                error.printStackTrace();
                            }
                        });


// Add the request to the request queue
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(jsonObjectRequest);



            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(order); // Pass the clicked order to the listener
                }
            }
        });
        // Set other views as needed
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView emailTextView;
        TextView nameTextView;
        TextView phoneTextView;
        TextView addressTextView;
        Button bookBtn;

        // Define other views here

        public OrderViewHolder(View itemView) {
            super(itemView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView);
            bookBtn =itemView.findViewById(R.id.bookButton);
            addressTextView = itemView.findViewById(R.id.addressTextView);


            // Initialize other views here
        }
    }
}

 class Order implements Serializable {
    private String id;
    private String email;
    private String name;
    private String phone;
    private boolean isBooked;
    private String bookedBy;
    private String createdAt;
    private String updatedAt;
    private  String address;

    private ArrayList<ItemList> itemLists;

    public Order(String id, String email, String name, String phone, boolean isBooked, String bookedBy, String createdAt, String updatedAt,ArrayList<ItemList> itemLists,String address) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.isBooked = isBooked;
        this.bookedBy = bookedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.itemLists = itemLists;
        this.address = address;
    }

     public ArrayList<ItemList> getItemLists() {
         return itemLists;
     }

     public void setItemLists(ArrayList<ItemList> itemLists) {
         this.itemLists = itemLists;
     }
// Getters and setters for the fields

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

     public String getAddress() {
         return address;
     }

     public void setAddress(String address) {
         this.address = address;
     }

     public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public String getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(String bookedBy) {
        this.bookedBy = bookedBy;
    }


    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
