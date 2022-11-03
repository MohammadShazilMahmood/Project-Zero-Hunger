package com.ass2.final_project_i190727_i190542_i180580;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class loginScreen extends AppCompatActivity {

    ImageView newAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        newAccount=findViewById(R.id.newSignup);
        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(loginScreen.this, signUp.class); //For Testing only
                startActivity(i);
                finish();
            }
        });

    }
}