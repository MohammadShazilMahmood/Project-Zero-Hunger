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
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onesignal.OneSignal;

public class MainActivity extends AppCompatActivity {
//    private FirebaseAuth mAuth;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    ImageView logo;
    String profileType, notify;
    Intent i;
    boolean logged_in;

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
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        boolean mboolean = false;
        SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
        mboolean = settings.getBoolean("FIRST_RUN", false);
        if (!mboolean) {
            // do the thing for the first time
            mAuth.signOut();
            settings = getSharedPreferences("PREFS_NAME", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("FIRST_RUN", true);
            editor.commit();
        }

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        boolean localData= sharedPreferences.getBoolean("localData",false);

        if (localData)
        {
            profileType = sharedPreferences.getString("profileType", "");
            logged_in = sharedPreferences.getBoolean("loggedIn", false);
        }

                
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isNetworkAvailable()) {

                    FirebaseUser user = mAuth.getCurrentUser();

                    if (user != null) {
                        String userID = user.getUid().toString();

                        //Load Profile Type
                        mDatabase.child("users").child(userID).child("profile_information").child("profileType").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (!task.isSuccessful()) {
                                    Log.e("firebase", "Error getting data", task.getException());
                                } else {
                                    profileType = "" + String.valueOf(task.getResult().getValue());
                                    myEdit.putString("profileType", profileType);
                                    myEdit.commit();

                                    //Load Notification Settings
                                    mDatabase.child("users").child(userID).child("app_settings").child("notifications").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                                            if (!task.isSuccessful()) {
                                                Log.e("firebase", "Error getting data", task.getException());
                                            } else {
                                                notify = "" + String.valueOf(task.getResult().getValue());
                                                myEdit.putString("notifications", notify);
                                                myEdit.commit();

                                                String playerid= OneSignal.getDeviceState().getUserId().toString();
                                                notifications notificationSetting = new notifications(
                                                        playerid,
                                                        "True",
                                                        notify
                                                );

                                                if (profileType.matches("NGO"))
                                                {
                                                    mDatabase.child("player_id").child("NGO").child(userID).setValue(notificationSetting);
                                                }
                                                else
                                                {
                                                    mDatabase.child("player_id").child("Hall").child(userID).setValue(notificationSetting);
                                                }

                                                myEdit.putString("player_id", playerid);
                                                myEdit.commit();

                                                if (profileType.matches("NGO")) {
                                                    i = new Intent(MainActivity.this, NGO_Home.class); //For Testing only
                                                } else {
                                                    i = new Intent(MainActivity.this, Hall_Individual_Home.class); //For Testing only
                                                }
                                                startActivity(i);
                                                finish();
                                            }
                                        }
                                    });


                                }
                            }
                        });
                    } else {
                        i = new Intent(MainActivity.this, loginScreen.class);
                        startActivity(i);
                        finish();
                    }
                }

                if (isNetworkAvailable()==false )
                {
                    if (localData) {
                        if (logged_in) {
                            if (profileType.matches("NGO")) {
                                i = new Intent(MainActivity.this, NGO_Home.class); //For Testing only
                            } else {
                                i = new Intent(MainActivity.this, Hall_Individual_Home.class); //For Testing only
                            }
                            startActivity(i);
                            finish();
                        } else {
                            i = new Intent(MainActivity.this, loginScreen.class);
                            startActivity(i);
                            finish();
                        }
                    }
                    else
                    {
                        i = new Intent(MainActivity.this, loginScreen.class);
                        startActivity(i);
                        finish();
                    }
                }
            }
        },2000);
//        }
    }
}