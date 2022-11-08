package com.ass2.final_project_i190727_i190542_i180580;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class viewProfile extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    String nameVal="", profileTypeVal="", identityNumberVal="", contactVal="", addressVal="", cityVal="", emailVal="";
    TextView name, profileType, indentityNumber, contact, address, city, email;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        name=findViewById(R.id.name);
        profileType=findViewById(R.id.profileType);
        indentityNumber=findViewById(R.id.identity);
        contact=findViewById(R.id.contactNo);
        address=findViewById(R.id.address);
        city=findViewById(R.id.city);
        email=findViewById(R.id.email);
        back=findViewById(R.id.back);

        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid().toString();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(viewProfile.this, Hall_Individual_Home.class); //For Testing only
                startActivity(i);
                finish();
            }
        });

        //Load Name
        mDatabase.child("users").child(userID).child("profile_information").child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else
                {
                    name.setText("Name: "+String.valueOf(task.getResult().getValue()));
                    nameVal=name.getText().toString();
                }
            }
        });

         //Load Profile Type
        mDatabase.child("users").child(userID).child("profile_information").child("profileType").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else
                {
                    profileType.setText("Profile Type: "+String.valueOf(task.getResult().getValue()));
                    profileTypeVal=profileType.getText().toString();
                }
            }
        });

        //Load Profile Type
        mDatabase.child("users").child(userID).child("profile_information").child("identityNumber").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else
                {
                    indentityNumber.setText("Identity No: "+ String.valueOf(task.getResult().getValue()));
                    identityNumberVal=indentityNumber.getText().toString();
                }
            }
        });

        //Load Profile Type
        mDatabase.child("users").child(userID).child("contact_information").child("email").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else
                {
                    email.setText("Email: "+ String.valueOf(task.getResult().getValue()));
                    emailVal=email.getText().toString();
                }
            }
        });

        //Load Profile Type
        mDatabase.child("users").child(userID).child("contact_information").child("number").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else
                {
                    contact.setText("Contact No: " +  String.valueOf(task.getResult().getValue()));
                    contactVal=contact.getText().toString();
                }
            }
        });

        //Load Profile Type
        mDatabase.child("users").child(userID).child("profile_information").child("address").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                    Toast.makeText(viewProfile.this, "e", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    address.setText("Address: "+String.valueOf(task.getResult().getValue()));
                    addressVal=address.getText().toString();
                }
            }
        });

        //Load Profile Type
        mDatabase.child("users").child(userID).child("profile_information").child("city").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                    Toast.makeText(viewProfile.this, "e", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    city.setText("City: "+ String.valueOf(task.getResult().getValue()));
                    cityVal=city.getText().toString();
                }
            }
        });
    }
}