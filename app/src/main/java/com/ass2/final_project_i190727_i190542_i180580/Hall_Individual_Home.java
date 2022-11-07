package com.ass2.final_project_i190727_i190542_i180580;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class Hall_Individual_Home extends AppCompatActivity {
    FirebaseAuth mAuth;
    ImageView profilePicture;
    DatabaseReference mDatabase;
    String profilePictureURL="";
    TextView name, profile, settings, donationHistory, aboutUs, ourTeam, tutorial, signOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_individual_home);
        profilePicture=findViewById(R.id.ProfilePic);
        name=findViewById(R.id.username);
        signOut=findViewById(R.id.SignOut);

        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid().toString();


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
                Intent i = new Intent(Hall_Individual_Home.this, loginScreen.class); //For Testing only
                startActivity(i);
                finish();
            }
        });

    }
}