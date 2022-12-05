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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class contact_us extends AppCompatActivity {
    EditText messageText;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    ImageView send, back;
    String profileType, name, address, city, number, email, identityNumber;

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
        setContentView(R.layout.activity_contact_us);

        messageText=findViewById(R.id.messageText);
        send=findViewById(R.id.send);
        back=findViewById(R.id.back);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid().toString();

        boolean localData= sharedPreferences.getBoolean("localData",false);

        if (localData)
        {
            profileType = sharedPreferences.getString("profileType", "");
            name = sharedPreferences.getString("name", "");
            city = sharedPreferences.getString("city", "");
            number = sharedPreferences.getString("contact","");
            email = sharedPreferences.getString("email","");
            address = sharedPreferences.getString("address", "");
            identityNumber=sharedPreferences.getString("identityNumber","");
        }

        if (isNetworkAvailable() && (localData==false))
        {
            mDatabase.child("users").child(userID).child("profile_information").child("profileType").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        profileType = "" + String.valueOf(task.getResult().getValue());
                        myEdit.putString("profileType", profileType);
                        myEdit.commit();
                    }
                }
            });

            mDatabase.child("users").child(userID).child("profile_information").child("address").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        address = "" + String.valueOf(task.getResult().getValue());
                        myEdit.putString("address", address);
                        myEdit.commit();
                    }
                }
            });

            mDatabase.child("users").child(userID).child("profile_information").child("city").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        city = "" + String.valueOf(task.getResult().getValue());
                        myEdit.putString("city", city);
                        myEdit.commit();
                    }
                }
            });

            mDatabase.child("users").child(userID).child("profile_information").child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        name = "" + String.valueOf(task.getResult().getValue());
                        myEdit.putString("name", name);
                        myEdit.commit();
                    }
                }
            });

            mDatabase.child("users").child(userID).child("contact_information").child("email").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        email = "" + String.valueOf(task.getResult().getValue());
                        myEdit.putString("email", email);
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
                        number = "" + String.valueOf(task.getResult().getValue());
                        myEdit.putString("contact", number);
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
                        identityNumber = "" + String.valueOf(task.getResult().getValue());
                        myEdit.putString("identityNumber", identityNumber);
                        myEdit.commit();
                    }
                }
            });
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (profileType.matches("NGO"))
                {
                    Intent i = new Intent(contact_us.this, NGO_Home.class); //For Testing only
                    startActivity(i);
                    finish();
                }
                else
                {
                    Intent i = new Intent(contact_us.this, Hall_Individual_Home.class); //For Testing only
                    startActivity(i);
                    finish();
                }
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valid=true;
                String error_msg="";

                if(messageText.getText().toString().matches(""))
                {
                    valid=false;
                    error_msg=error_msg+"Empty Message";
                }

                if(isNetworkAvailable()==false)
                {
                    valid=false;
                    if(error_msg.matches(""))
                    {
                        error_msg=error_msg+"No Internet Available";
                    }
                    else
                    {
                        error_msg=error_msg+"\nNo Internet Available";
                    }
                }

                if (valid==false)
                {
                    Toast.makeText(contact_us.this, error_msg, Toast.LENGTH_SHORT).show();
                }

                if (valid)
                {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss");
                    Date date = new Date();
                    String currentDate=formatter.format(date);

                    Calendar c=Calendar.getInstance();
                    String messageID= userID+String.valueOf(c.getTimeInMillis());

                    message messageObj=new message(
                            name,
                            identityNumber,
                            profileType,
                            address,
                            city,
                            number,
                            email,
                            messageText.getText().toString(),
                            currentDate,
                            userID);

                    mDatabase.child("messageToTeam").child(messageID).setValue(messageObj);
                    Toast.makeText(contact_us.this, "Message Sent", Toast.LENGTH_SHORT).show();

                    if (profileType.matches("NGO"))
                    {
                        Intent i = new Intent(contact_us.this, NGO_Home.class); //For Testing only
                        startActivity(i);
                        finish();
                    }
                    else
                    {
                        Intent i = new Intent(contact_us.this, Hall_Individual_Home.class); //For Testing only
                        startActivity(i);
                        finish();
                    }
                }




            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (profileType.matches("NGO"))
        {
            Intent i = new Intent(contact_us.this, NGO_Home.class); //For Testing only
            startActivity(i);
            finish();
        }
        else
        {
            Intent i = new Intent(contact_us.this, Hall_Individual_Home.class); //For Testing only
            startActivity(i);
            finish();
        }
    }
}