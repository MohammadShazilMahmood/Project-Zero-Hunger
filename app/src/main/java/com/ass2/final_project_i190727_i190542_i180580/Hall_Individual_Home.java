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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Hall_Individual_Home extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
//    ImageView profilePicture;
    ImageView donateMoney, donateFood, pendingRequest, acceptedRequest;
    CircleImageView profilePicture;
    String profilePictureURL="";
    TextView name, profile, settings, donationHistory, aboutUs, ourTeam, ContactUS, signOut;

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
        setContentView(R.layout.activity_hall_individual_home);
        profilePicture=findViewById(R.id.ProfilePic);
        name=findViewById(R.id.username);
        signOut=findViewById(R.id.SignOut);
        profile=findViewById(R.id.Profile);
        settings=findViewById(R.id.Settings);
        donationHistory=findViewById(R.id.DonationHistory);
        aboutUs=findViewById(R.id.About_PZH);
        ourTeam=findViewById(R.id.OurTeam);
        ContactUS=findViewById(R.id.ContactUS);
        donateMoney=findViewById(R.id.DonateMoney);
        donateFood=findViewById(R.id.DonateFood);
        pendingRequest=findViewById(R.id.PendingRequest);
        acceptedRequest=findViewById(R.id.AcceptedRequest);

        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        boolean localData= sharedPreferences.getBoolean("localData",false);
        if (localData)
        {
            Toast.makeText(Hall_Individual_Home.this, "Local Data", Toast.LENGTH_SHORT).show();
            String nameVal = sharedPreferences.getString("name", "");
            name.setText(nameVal);
        }


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Hall_Individual_Home.this, appSettings.class); //For Testing only
                startActivity(i);
                finish();
            }
        });

        donateMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Hall_Individual_Home.this, donateMoney.class); //For Testing only
                startActivity(i);
                finish();
            }
        });

        donationHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Hall_Individual_Home.this, donationHistoryHall.class); //For Testing only
                i.putExtra("profileType", "Hall");
                startActivity(i);
                finish();
            }
        });

        ContactUS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Hall_Individual_Home.this, contact_us.class); //For Testing only
                startActivity(i);
                finish();
            }
        });

        donateFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Hall_Individual_Home.this, donateFood.class); //For Testing only
                startActivity(i);
                finish();
            }
        });

        acceptedRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Hall_Individual_Home.this, acceptedRequestHall.class); //For Testing only
                startActivity(i);
                finish();
            }
        });

        pendingRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Hall_Individual_Home.this, pendingRequestHall.class); //For Testing only
                startActivity(i);
                finish();
            }
        });

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Hall_Individual_Home.this, aboutPZH.class); //For Testing only
                startActivity(i);
                finish();
            }
        });

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Hall_Individual_Home.this, viewProfilePicture.class); //For Testing only
                startActivity(i);
                finish();
            }
        });

        ourTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Hall_Individual_Home.this, ourTeam.class); //For Testing only
                startActivity(i);
                finish();
            }
        });


        if (isNetworkAvailable() && localData==false)
        {
            //Load Name
            mDatabase.child("users").child(userID).child("profile_information").child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                    else
                    {
                        name.setText(String.valueOf(task.getResult().getValue()));
                    }
                }
            });
        }


        //Load Profile Picture
        mDatabase.child("users").child(userID).child("profile_picture").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else
                {
                    profilePictureURL=String.valueOf(task.getResult().getValue());
                    Picasso.get().load(profilePictureURL).into(profilePicture);
//                    Toast.makeText(Hall_Individual_Home.this, profilePictureURL, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Sign Out
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                myEdit.putBoolean("localData", false);
                myEdit.putBoolean("loggedIn", false);
                Intent i = new Intent(Hall_Individual_Home.this, loginScreen.class); //For Testing only
                startActivity(i);
                finish();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Hall_Individual_Home.this, viewProfile.class); //For Testing only
                startActivity(i);
                finish();
            }
        });

    }
}