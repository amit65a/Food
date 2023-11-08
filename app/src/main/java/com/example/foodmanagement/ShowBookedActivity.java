package com.example.foodmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowBookedActivity extends AppCompatActivity  implements  OrderAdapter.OnItemClickListener{

    OrderAdapter orderAdapter;
    String URL="https://foodapi-tsul.onrender.com/api/v1/getFoodByUser";
    ArrayList<Order> orders = new ArrayList<>();
    Button bookedBtn;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_booked);
        progressBar = findViewById(R.id.loadingSpinner);


        orderAdapter = new OrderAdapter(orders,this,this);
        parseData();
    }



    public  void parseData(){

        JSONObject jsonParams = new JSONObject();
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            String json = sharedPreferences.getString("profile", null);
            Gson gson = new Gson();
            SessionDetails sessionDetails = gson.fromJson(json,SessionDetails.class);

            jsonParams.put("useremail", sessionDetails.getEmail());

            // Add more key-value pairs as needed
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, URL,jsonParams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Res",response.toString());

                try {

                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONArray array = jsonObject.getJSONArray("foodItem");
                    if(array.length()>0){
                        for (int i = 0; i<array.length();i++){
                            JSONObject object = array.getJSONObject(i);
                            String id = object.getString("id");
                            String name = object.getString("name");
                            String email = object.getString("email");
                            String phone = object.getString("phone");
                            Boolean isBooked = object.getBoolean("isBooked");
                            String bookedBy = object.getString("bookedBy");
                            String createdAt= object.getString("createdAt");
                            String updatedAt =object.getString("updatedAt");
                            String address = object.getString("address");

                            Log.e("name",name);
                            JSONArray itemArr = object.getJSONArray("foods");
                            ArrayList<ItemList> itemLists = new ArrayList<>();
                            for (int j =0;j<itemArr.length();j++){
                                JSONObject itmObj = itemArr.getJSONObject(j);
                                String itemName = itmObj.getString("name");
                                String itemQty = itmObj.getString("qty");
                                itemLists.add(new ItemList(itemName,itemQty));
                            }
                            orders.add(new Order(id,email,name,phone,isBooked,bookedBy,createdAt,updatedAt,itemLists,address));

                            orderAdapter.notifyDataSetChanged();
                        }
                        progressBar.setVisibility(View.GONE);
                        Log.e("orders",orders.get(0).getEmail());
                        RecyclerView recyclerView = findViewById(R.id.recyclerViewBookedItem);
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ShowBookedActivity.this));

                        // Populate this with your order data


                        recyclerView.setAdapter(orderAdapter);
                    }else {

                        Toast.makeText(ShowBookedActivity.this,"No item Found",Toast.LENGTH_SHORT).show();
                    }

                }catch (JSONException e){
                    Log.e("here","Here");
                    e.printStackTrace();
                    Toast.makeText(ShowBookedActivity.this,"Internal Server Error",Toast.LENGTH_SHORT).show();


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("here","Here3");

                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onItemClick(Order order) {
        Intent intent = new Intent(this, ShowDataActivity.class);
        intent.putExtra("order", order);
        startActivity(intent);
    }
}