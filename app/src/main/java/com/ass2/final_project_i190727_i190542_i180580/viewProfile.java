package com.ass2.final_project_i190727_i190542_i180580;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
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
    ImageView back, updateContactInfo;
    String profileTypeBack;

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

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
        updateContactInfo=findViewById(R.id.updateContactInfo);

        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        if (isNetworkAvailable()==false)
        {
            boolean localData= sharedPreferences.getBoolean("localData",false);
            if (localData)
            {
                Toast.makeText(viewProfile.this, "Local Data", Toast.LENGTH_SHORT).show();
                profileTypeBack = sharedPreferences.getString("profileType", "");

                nameVal = sharedPreferences.getString("name", "");
                name.setText("Name: "+nameVal);

                String temp = nameVal = sharedPreferences.getString("profileType", "");
                profileType.setText("Profile Type: "+temp);

                identityNumberVal = sharedPreferences.getString("identityNumber", "");
                indentityNumber.setText("Identity No: "+identityNumberVal);

                emailVal = sharedPreferences.getString("email", "");
                email.setText("Email: "+emailVal);

                contactVal = sharedPreferences.getString("contact", "");
                contact.setText("Contact No: "+contactVal);

                addressVal = sharedPreferences.getString("address", "");
                address.setText("Address: "+addressVal);

                cityVal = sharedPreferences.getString("city", "");
                city.setText("City: "+cityVal);
            }
        }
        else
        {
            Toast.makeText(viewProfile.this, "Online", Toast.LENGTH_SHORT).show();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (profileTypeBack.matches("NGO"))
                {
                    Intent i = new Intent(viewProfile.this, NGO_Home.class); //For Testing only
                    startActivity(i);
                    finish();
                }
                else
                {
                    Intent i = new Intent(viewProfile.this, Hall_Individual_Home.class); //For Testing only
                    startActivity(i);
                    finish();
                }
            }
        });

        updateContactInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(viewProfile.this, updateContactInformation.class); //For Testing only
                startActivity(i);
                finish();
            }
        });

        if (isNetworkAvailable()) {

            FirebaseUser user = mAuth.getCurrentUser();
            String userID = user.getUid().toString();

            mDatabase.child("users").child(userID).child("profile_information").child("profileType").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        profileTypeBack = "" + String.valueOf(task.getResult().getValue());
                        profileType.setText("Profile Type: " + profileTypeBack);
                        myEdit.putString("profileType", profileTypeBack);
                        myEdit.commit();
                    }
                }
            });

            //Load Name
            mDatabase.child("users").child(userID).child("profile_information").child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        nameVal = "" + String.valueOf(task.getResult().getValue());
                        name.setText("Name: " + nameVal);
                        myEdit.putString("name", nameVal);
                        myEdit.commit();
                    }
                }
            });

            //Load Profile Type
            mDatabase.child("users").child(userID).child("profile_information").child("identityNumber").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        identityNumberVal = "" + String.valueOf(task.getResult().getValue());
                        indentityNumber.setText("Identity No: " + identityNumberVal);
                        myEdit.putString("identityNumber", identityNumberVal);
                        myEdit.commit();
                    }
                }
            });

            //Load Profile Type
            mDatabase.child("users").child(userID).child("contact_information").child("email").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        emailVal = "" + String.valueOf(task.getResult().getValue());
                        email.setText("Email: " + emailVal);
                        myEdit.putString("email", emailVal);
                        myEdit.commit();
                    }
                }
            });

            //Load Profile Type
            mDatabase.child("users").child(userID).child("contact_information").child("number").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        contactVal = "" + String.valueOf(task.getResult().getValue());
                        contact.setText("Contact No: " + contactVal);
                        myEdit.putString("contact", contactVal);
                        myEdit.commit();
                    }
                }
            });

            //Load Profile Type
            mDatabase.child("users").child(userID).child("profile_information").child("address").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        addressVal = "" + String.valueOf(task.getResult().getValue());
                        address.setText("Address: " + addressVal);
                        myEdit.putString("address", addressVal);
                        myEdit.commit();
                    }
                }
            });

            //Load Profile Type
            mDatabase.child("users").child(userID).child("profile_information").child("city").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        cityVal = "" + String.valueOf(task.getResult().getValue());
                        city.setText("City: " + cityVal);
                        myEdit.putString("city", cityVal);
                        myEdit.commit();
                    }
                }
            });

            myEdit.putString("userID", userID);
            myEdit.putBoolean("localData", true);
            myEdit.commit();
        }

    }
}