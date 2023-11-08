package com.example.foodmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class ReceiverLogin extends AppCompatActivity {
    EditText ed1, ed2;
    Button btn;
    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_login);
        ed1 = findViewById(R.id.Username);
        ed2 = findViewById(R.id.Password);
        btn = findViewById(R.id.buttonLogin);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perforLogin();
            }
        });
    }

    private void perforLogin() {

        String email = ed1.getText().toString();
        String password = ed2.getText().toString();


        if (!email.matches(emailpattern)) {
            ed1.setError("Enter coreect email");
        } else if (password.isEmpty() || password.length() < 6) {
            ed2.setError("Enter proper password");
        } else {

            btn.setText("Logging in");

mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()){
//
            SaveUserSession(task.getResult().getUser().getUid());
        }
        else{
//            Toast.makeText(ReceiverLogin.this,""+task.getException(),Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
        }

    }
});

        }
    }
    private void sendUserToNextActicity(String role) {
        if(role.equals("Sender")){
            Intent intent=new Intent(ReceiverLogin.this,SenderChooseActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else {
            Intent intent=new Intent(ReceiverLogin.this,ReceiverChooseActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }

    }


    public  void SaveUserSession(String userId){


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userReference = databaseReference.child("users").child(userId);

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.e("here","here");
                    UserData userData = dataSnapshot.getValue(UserData.class);
                    // Use userData to access the user's information
                    String name = userData.getName();
                    String email = userData.getEmail();
                    String phoneNumber = userData.getPhoneNumber();
                    String address = userData.getAddress();
                    String role = userData.getRole();
                    SessionDetails sessionDetails = new SessionDetails(name,email,phoneNumber,address,role);
                    Gson gson = new Gson();
                    String myData = gson.toJson(sessionDetails);
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("profile", myData);
                    editor.apply();
                    btn.setText("Login Successful");
                    sendUserToNextActicity(role);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
            }
        });
    }
}