package com.ass2.final_project_i190727_i190542_i180580;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class loginScreen extends AppCompatActivity {

    ImageView newAccount, signin;
    EditText email, password;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    String profileType="", name="", identityNumber="", emailFetched, contactNum="", addressFetched="", cityFetched="" ;
    boolean profileWait=false, nameWait=false, idWait=false, emailWait=false, contactWait=false, addressWait=false, cityWait=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        newAccount=findViewById(R.id.newSignup);
        signin=findViewById(R.id.login_app);
        email=findViewById(R.id.login_email);
        password=findViewById(R.id.login_password);
        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putBoolean("localData", false);
        myEdit.commit();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean valid=true;
                String error_msg="";
                if(email.getText().toString().matches(""))
                {
                    error_msg=error_msg+"Empty Email";
                    valid=false;
                }

                if(password.getText().toString().matches(""))
                {
                    if(error_msg.matches(""))
                    {
                        error_msg=error_msg+"Empty Password";
                    }
                    else
                    {
                        error_msg=error_msg+"\nEmpty Password";
                    }

                    valid=false;
                }

                if(valid==false){
                    Toast.makeText(loginScreen.this, error_msg, Toast.LENGTH_SHORT).show();
                }

                if (valid)
                {
                    mAuth.signInWithEmailAndPassword(
                            email.getText().toString(),
                            password.getText().toString()
                    ).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = mAuth.getCurrentUser();
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
                                    }
                                }
                            });

                            Toast.makeText(loginScreen.this, "Sign In", Toast.LENGTH_SHORT).show();
                            if (profileType.matches("NGO"))
                            {
                                Toast.makeText(loginScreen.this, "NGO NGO NGO", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(loginScreen.this, NGO_Home.class); //For Testing only
                                startActivity(i);
                                finish();
                            }
                            else
                            {
                                Intent i = new Intent(loginScreen.this, Hall_Individual_Home.class); //For Testing only
                                startActivity(i);
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(loginScreen.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
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