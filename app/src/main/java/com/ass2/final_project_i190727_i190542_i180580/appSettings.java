package com.ass2.final_project_i190727_i190542_i180580;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        mDatabase.child("users").child(userID).child("profile_information").child("profileType").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else
                {
                    profileType=""+String.valueOf(task.getResult().getValue());
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

        saveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notifications.isChecked())
                {
                    mDatabase.child("users").child(userID).child("app_settings").child("notifications").setValue("True");
                    current_state="True";
                }
                else
                {
                    mDatabase.child("users").child(userID).child("app_settings").child("notifications").setValue("False");
                    current_state="False";
                }
                Toast.makeText(appSettings.this, "Settings Saved", Toast.LENGTH_SHORT).show();
            }
        });

    }
}