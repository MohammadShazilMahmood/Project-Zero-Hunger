package com.ass2.final_project_i190727_i190542_i180580;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ourTeam extends AppCompatActivity {
    ImageView back;
    CircleImageView Shazil, Talal, Hassan;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    String profilePictureURL="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_team);

        back=findViewById(R.id.back);
        Shazil=findViewById(R.id.Shazil);
        Talal=findViewById(R.id.Talal);
        Hassan=findViewById(R.id.Hassan);

        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ourTeam.this, Hall_Individual_Home.class); //For Testing only
                startActivity(i);
                finish();
            }
        });

        mDatabase.child("team").child("profile_pictures").child("Talal").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else
                {
                    profilePictureURL=String.valueOf(task.getResult().getValue());
                    Picasso.get().load(profilePictureURL).into(Talal);
                    profilePictureURL="";
                }
            }
        });

        mDatabase.child("team").child("profile_pictures").child("Shazil").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else
                {
                    profilePictureURL=String.valueOf(task.getResult().getValue());
                    Picasso.get().load(profilePictureURL).into(Shazil);
                    profilePictureURL="";
                }
            }
        });
    }
}