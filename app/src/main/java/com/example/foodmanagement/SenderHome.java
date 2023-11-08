package com.example.foodmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

public class SenderHome extends AppCompatActivity {
 Button btn;
 EditText ed1,ed2,ed3,ed4;
 EditText qty1,qty2,qty3,qty4;




 ArrayList<Order> orders = new ArrayList<>();
    String URL = "https://foodapi-tsul.onrender.com/api/v1/addFood";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender_home);
        ed1 = findViewById(R.id.item1);
        ed2 = findViewById(R.id.item2);
        ed3 = findViewById(R.id.item3);
        ed4 = findViewById(R.id.item4);

        qty1 = findViewById(R.id.qty1);
        qty2 = findViewById(R.id.qty2);
        qty3 = findViewById(R.id.qty3);
        qty4 = findViewById(R.id.qty4);


btn=findViewById(R.id.button5);

btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
//
        sendData();


    }
});




    }

    public  void sendData(){

        try {
            // Create a JSON object to represent your data
            btn.setText("Submitting");
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            String json = sharedPreferences.getString("profile", null);
            Gson gson = new Gson();
            SessionDetails sessionDetails = gson.fromJson(json, SessionDetails.class);

            Log.e("session",sessionDetails.toString());

//        }catch (Exception e){
//            e.printStackTrace();
//        }


            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("name", sessionDetails.getName());
            jsonRequest.put("email", sessionDetails.getEmail());
            jsonRequest.put("phone", sessionDetails.getPhone());
            jsonRequest.put("address",sessionDetails.getAddress());
//            jsonRequest.put("phone", "Raj");

            // Create a JSON array for the 'foods' key
            JSONArray foodsArray = new JSONArray();

            // Create JSON objects for each food item
            if(!ed1.getText().toString().isEmpty() && !qty1.getText().toString().isEmpty()){
                JSONObject foodItem1 = new JSONObject();
                foodItem1.put("name", ed1.getText().toString());
                foodItem1.put("qty", qty1.getText().toString());
                foodsArray.put(foodItem1);
            }

            if(!ed2.getText().toString().isEmpty() && !qty2.getText().toString().isEmpty()){
                JSONObject foodItem2 = new JSONObject();
                foodItem2.put("name", ed2.getText().toString());
                foodItem2.put("qty",  qty2.getText().toString());
                foodsArray.put(foodItem2);
            }

            if(!ed3.getText().toString().isEmpty() && !qty3.getText().toString().isEmpty()){
                JSONObject foodItem3 = new JSONObject();
                foodItem3.put("name", ed3.getText().toString());
                foodItem3.put("qty", qty3.getText().toString());
                foodsArray.put(foodItem3);
            }

            if(!ed4.getText().toString().isEmpty() && !qty4.getText().toString().isEmpty()){
                JSONObject foodItem4 = new JSONObject();
                foodItem4.put("name", ed4.getText().toString());
                foodItem4.put("qty",  qty4.getText().toString());
                foodsArray.put(foodItem4);
            }










            // Add the food items to the 'foods' array





            // Add the 'foods' array to the main JSON object
            jsonRequest.put("foods", foodsArray);

            // Create a JsonObjectRequest with a POST request method
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonRequest,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Handle the response from the server
                            Log.e("response",response.toString());

                            try {
                                if(response.getBoolean("success")){
                                    btn.setText("Submitted");
                                }
                            } catch (JSONException e) {
                               e.printStackTrace();
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

            // Add the request to the Volley request queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}






 class Foods {
    private String name;
    private int qty;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }


// Getters and setters for the fields
}


class ResponseData {
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    private  List<Order> orders;

}


//ed1=findViewById(R.id.item1);
////ed2=findViewById(R.id.item1);
////ed1=findViewById(R.id.item1);
////ed1=findViewById(R.id.item1);
//
//        qty1=findViewById(R.id.qty1);




//btn.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//
//
//    }
//});