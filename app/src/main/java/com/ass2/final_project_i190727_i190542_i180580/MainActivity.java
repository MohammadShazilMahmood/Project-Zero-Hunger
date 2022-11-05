package com.ass2.final_project_i190727_i190542_i180580;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
//    private FirebaseAuth mAuth;

    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = mAuth.getCurrentUser();
//        if(user != null) {
//            Intent i = new Intent(MainActivity.this, T_Screen1.class);
//            startActivity(i);
//            finish();
//        } else {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, loginScreen.class);
//                Intent i = new Intent(MainActivity.this, profileSetUp.class);
                startActivity(i);
                finish();
            }
        },2000);
//        }
    }
}