package com.ass2.final_project_i190727_i190542_i180580;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginScreen extends AppCompatActivity {

    ImageView newAccount, signin;
    EditText email, password;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        newAccount=findViewById(R.id.newSignup);
        signin=findViewById(R.id.login_app);
        email=findViewById(R.id.login_email);
        password=findViewById(R.id.login_password);
        mAuth= FirebaseAuth.getInstance();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signInWithEmailAndPassword(
                        email.getText().toString(),
                        password.getText().toString()
                ).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
//                        Intent i = new Intent(loginScreen.this, signUp.class); //For Testing only
//                        startActivity(i);
//                        finish();
                        Toast.makeText(loginScreen.this, "Sign In", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(loginScreen.this, Hall_Individual_Home.class); //For Testing only
                        startActivity(i);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(loginScreen.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



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