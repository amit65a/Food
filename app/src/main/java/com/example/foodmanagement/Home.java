package com.example.foodmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Home extends AppCompatActivity implements OrderAdapter.OnItemClickListener {

    String URL = "https://foodapi-tsul.onrender.com/api/v1/getFood";
    ArrayList<Order> orders = new ArrayList<>();
    ProgressBar progressBar;

    OrderAdapter adapter;

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        progressBar = findViewById(R.id.loadingSpinner);
         adapter = new OrderAdapter(orders,this,this);
        parseData();

    }


    public  void parseData(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Res",response);
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("Foods");
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

                            adapter.notifyDataSetChanged();
                        }

                        progressBar.setVisibility(ListView.GONE);
                        Log.e("orders",orders.get(0).getEmail());
                        RecyclerView recyclerView = findViewById(R.id.recyclerView);
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setLayoutManager(new LinearLayoutManager(Home.this));

                        // Populate this with your order data


                        recyclerView.setAdapter(adapter);
                    }else {
                        Toast.makeText(Home.this, "No item is found", Toast.LENGTH_SHORT).show();
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(Home.this, "error", Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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