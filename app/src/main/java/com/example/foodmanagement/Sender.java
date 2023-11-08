package com.example.foodmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sender extends AppCompatActivity {
    EditText ed1, ed2, ed4, ed5, ed6,addressTextView;

    Button sender;
    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender);




       try {
          role = getIntent().getStringExtra("role");
          Log.e("role",role);

       }catch (Exception e){
           e.printStackTrace();
       }



        ed1 = findViewById(R.id.name);
        addressTextView = findViewById(R.id.addressTextView);

        ed4 = findViewById(R.id.number);
        ed5 = findViewById(R.id.email);
        ed6 = findViewById(R.id.Pass);
        sender = findViewById(R.id.buttonsender);


        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        sender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforAuths();
            }
        });
    }


    private void PerforAuths() {

        sender.setText("Submitting");

        String name = ed1.getText().toString();
        String address = addressTextView.getText().toString();

        String contact = ed4.getText().toString();
        String email = ed5.getText().toString();
        String password = ed6.getText().toString();


        if (name.isEmpty() || address.isEmpty() || contact.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please fill All the details", Toast.LENGTH_SHORT).show();
        } else if (!email.matches(emailpattern)) {
            ed5.setError("Enter coreect email");
        } else if (password.isEmpty() || password.length() < 6) {
            ed6.setError("Enter proper password");
        } else {


            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        updateProfile(name,email,contact,address,task.getResult().getUser().getUid());


                        Toast.makeText(getApplicationContext(), "Details is Submitted Successsfully", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(Sender.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                        sender.setText("Submit");
                    }
                }
            });

        }
    }

    private void updateProfile(String username,String email,String phone,String addr,String userid) {
       try {
           DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
           UserData userData = new UserData(username, email, phone, addr,role);
           databaseReference.child("users").child(userid).setValue(userData);
           sender.setText("Submitted");
           sendUserToNextActicity();


       }catch (Exception e){
           e.printStackTrace();
           sender.setText("Submit");
       }
    }

    private void sendUserToNextActicity() {
        Intent  intent=new Intent(Sender.this,ReceiverLogin.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}