package com.ass2.final_project_i190727_i190542_i180580;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class signUp extends AppCompatActivity {
    ImageView existingAccount, signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        existingAccount=findViewById(R.id.existingAccount);
        signup=findViewById(R.id.signUp_app);
        existingAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(signUp.this, loginScreen.class); //For Testing only
                startActivity(i);
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(signUp.this, profileSetUp.class); //For Testing only
                startActivity(i);
                finish();
            }
        });

    }
}