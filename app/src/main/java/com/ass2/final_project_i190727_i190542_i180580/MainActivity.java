package com.ass2.final_project_i190727_i190542_i180580;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {
//    private FirebaseAuth mAuth;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    ImageView logo;
    String profileType;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();
//        mAuth.signOut();  //For Testing only

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = mAuth.getCurrentUser();

                if (user != null) {
                    String userID = user.getUid().toString();

                    //Load Profile Type
                    mDatabase.child("users").child(userID).child("profile_information").child("profileType").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()) {
                                Log.e("firebase", "Error getting data", task.getException());
                            }
                            else
                            {
                                profileType=""+String.valueOf(task.getResult().getValue());
                                if (profileType.matches("NGO"))
                                {
                                    Toast.makeText(MainActivity.this, "NGO NGO NGO", Toast.LENGTH_SHORT).show();
                                    i = new Intent(MainActivity.this, NGO_Home.class); //For Testing only
                                }
                                else
                                {
                                    i = new Intent(MainActivity.this, Hall_Individual_Home.class); //For Testing only
                                }
                                startActivity(i);
                                finish();
                            }
                        }
                    });
                } else {
                    i = new Intent(MainActivity.this, loginScreen.class);
                    startActivity(i);
                    finish();
                }
            }
        },2000);
//        }
    }
}