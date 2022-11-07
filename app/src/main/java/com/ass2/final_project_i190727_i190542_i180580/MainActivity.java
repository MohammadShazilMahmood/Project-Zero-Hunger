package com.ass2.final_project_i190727_i190542_i180580;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
//    private FirebaseAuth mAuth;
    FirebaseAuth mAuth;
    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
//        mAuth.signOut();  //For Testing only

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = mAuth.getCurrentUser();
                Intent i;
                if (user != null) {
                    i = new Intent(MainActivity.this, Hall_Individual_Home.class);
                } else {
                    i = new Intent(MainActivity.this, loginScreen.class);
                }
                startActivity(i);
                finish();
            }
        },2000);
//        }
    }
}