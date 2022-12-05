package com.ass2.final_project_i190727_i190542_i180580;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

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

public class NGO_Home extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    DrawerLayout drawer;

    CircleImageView profilePicture;
    String profilePictureURL="";
    TextView name, profile, settings, donationHistory, aboutUs, ourTeam, ContactUS, signOut;
    ImageView donationRequest, acceptedRequest, moneyReceived, menu;

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
        setContentView(R.layout.activity_ngo_home);
        profilePicture=findViewById(R.id.ProfilePic);
        name=findViewById(R.id.username);
        signOut=findViewById(R.id.SignOut);
        profile=findViewById(R.id.Profile);
        settings=findViewById(R.id.Settings);
        donationHistory=findViewById(R.id.DonationHistory);
        aboutUs=findViewById(R.id.About_PZH);
        ourTeam=findViewById(R.id.OurTeam);
        ContactUS=findViewById(R.id.ContactUS);
        drawer=findViewById(R.id.drawer);
        menu=findViewById(R.id.Menu);

        donationRequest=findViewById(R.id.FoodDonationRequest);
        acceptedRequest=findViewById(R.id.AcceptedRequest);
        moneyReceived=findViewById(R.id.MoneyReceived);

        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        boolean localData= sharedPreferences.getBoolean("localData",false);
        if (localData)
        {
            String nameVal = sharedPreferences.getString("name", "");
            name.setText(nameVal);
        }

        donationRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable())
                {
                    Intent i = new Intent(NGO_Home.this, pendingRequestNGO.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Toast.makeText(NGO_Home.this, "No Internet Available", Toast.LENGTH_SHORT).show();
                }

            }
        });

        ContactUS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NGO_Home.this, contact_us.class);
                startActivity(i);
                finish();
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawer.isOpen()==false)
                {
                    drawer.open();
                }
            }
        });

        donationHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()) {
                    Intent i = new Intent(NGO_Home.this, donationHistoryHall.class);
                    i.putExtra("profileType", "NGO");
                    startActivity(i);
                    finish();
                }
                else
                {
                    Toast.makeText(NGO_Home.this, "No Internet Available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        moneyReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NGO_Home.this, moneyReceived.class);
                startActivity(i);
                finish();
            }
        });

        acceptedRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()) {
                    Intent i = new Intent(NGO_Home.this, acceptedRequestNGO.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Toast.makeText(NGO_Home.this, "No Internet Available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NGO_Home.this, appSettings.class); //For Testing only
                startActivity(i);
                finish();
            }
        });

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NGO_Home.this, aboutPZH.class); //For Testing only
                startActivity(i);
                finish();
            }
        });

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NGO_Home.this, viewProfilePicture.class); //For Testing only
                startActivity(i);
                finish();
            }
        });

        ourTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NGO_Home.this, ourTeam.class); //For Testing only
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
                }
            }
        });

        //Sign Out
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()) {
                    mAuth.signOut();
                    myEdit.putBoolean("localData", false);
                    myEdit.putBoolean("loggedIn", false);
                    myEdit.commit();
                    mDatabase.child("users").child(userID).child("logged_in").setValue("False");
                    Intent i = new Intent(NGO_Home.this, loginScreen.class); //For Testing only
                    startActivity(i);
                    finish();
                }
                else
                {
                    Toast.makeText(NGO_Home.this, "No Internet Available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child("users").child(userID).child("logged_in").setValue("False");
                Intent i = new Intent(NGO_Home.this, viewProfile.class); //For Testing only
                startActivity(i);
                finish();
            }
        });

    }
}