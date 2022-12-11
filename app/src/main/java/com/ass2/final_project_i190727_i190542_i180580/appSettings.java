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
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class appSettings extends AppCompatActivity {
    ImageView back, saveSettings;
    Switch notifications;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    String current_state="";
    String profileType="";

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
        setContentView(R.layout.activity_app_settings);

        back=findViewById(R.id.back);
        saveSettings=findViewById(R.id.saveSettings);
        notifications=findViewById(R.id.notificationSettings);

        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        boolean localData= sharedPreferences.getBoolean("localData",false);

        if (localData)
        {
            profileType = sharedPreferences.getString("profileType", "");
            current_state = sharedPreferences.getString("notifications","True");

            if (current_state.matches("True"))
            {
                notifications.setChecked(true);
            }
            else
            {
                notifications.setChecked(false);
            }
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

            //Load Previous State
            mDatabase.child("users").child(userID).child("app_settings").child("notifications").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                    else
                    {
                        current_state=""+String.valueOf(task.getResult().getValue());
                        if (current_state.matches("True"))
                        {
                            notifications.setChecked(true);
                        }
                        else
                        {
                            notifications.setChecked(false);
                        }
                    }
                }
            });
        }




        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (profileType.matches("NGO"))
                {
                    Intent i = new Intent(appSettings.this, NGO_Home.class); //For Testing only
                    startActivity(i);
                    finish();
                }
                else
                {
                    Intent i = new Intent(appSettings.this, Hall_Individual_Home.class); //For Testing only
                    startActivity(i);
                    finish();
                }
            }
        });



        saveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()) {

                    if (notifications.isChecked())
                    {
                        mDatabase.child("users").child(userID).child("app_settings").child("notifications").setValue("True");
                        current_state = "True";
                        myEdit.putString("notifications", "True");
                        myEdit.commit();
                        if (profileType.matches("NGO"))
                        {
                            mDatabase.child("player_id").child("NGO").child(userID).child("notificationSettings").setValue("True");
                        }
                        else
                        {
                            mDatabase.child("player_id").child("Hall").child(userID).child("notificationSettings").setValue("True");
                        }
                    }
                    else
                    {
                        mDatabase.child("users").child(userID).child("app_settings").child("notifications").setValue("False");
                        current_state = "False";
                        myEdit.putString("notifications", "False");
                        myEdit.commit();
                        if (profileType.matches("NGO"))
                        {
                            mDatabase.child("player_id").child("NGO").child(userID).child("notificationSettings").setValue("False");
                        }
                        else
                        {
                            mDatabase.child("player_id").child("Hall").child(userID).child("notificationSettings").setValue("False");
                        }
                    }
                    Toast.makeText(appSettings.this, "Settings Saved", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(appSettings.this, "No Internet Available", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (profileType.matches("NGO"))
        {
            Intent i = new Intent(appSettings.this, NGO_Home.class); //For Testing only
            startActivity(i);
            finish();
        }
        else
        {
            Intent i = new Intent(appSettings.this, Hall_Individual_Home.class); //For Testing only
            startActivity(i);
            finish();
        }
    }
}