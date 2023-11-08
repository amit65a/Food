package com.example.foodmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1=findViewById(R.id.button);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              ;

                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                String json = sharedPreferences.getString("profile", null);

                if (json != null) {
                    Gson gson = new Gson();
                    SessionDetails sessionDetails = gson.fromJson(json,SessionDetails.class);


                    // Now, you can use the retrievedObject
                    String name = sessionDetails.getEmail();
                    String role = sessionDetails.getRole();

                    if(role!=null){
                        if(role.equals("Sender")){
                            Intent intent = new Intent(MainActivity.this,SenderChooseActivity.class);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(MainActivity.this,ReceiverChooseActivity.class);
                            startActivity(intent);
                        }
                    }else {
                        startActivity(new Intent(MainActivity.this,ChooseLoginActivity.class));
                    }

                }

                else {
                    startActivity(new Intent(MainActivity.this,ChooseLoginActivity.class));

                }
//                startActivity(new Intent(MainActivity.this,Details.class));
            }
        });
    }
}