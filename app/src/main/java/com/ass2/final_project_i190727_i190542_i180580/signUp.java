package com.ass2.final_project_i190727_i190542_i180580;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signUp extends AppCompatActivity {
    ImageView existingAccount, signup;
    EditText email, number, password;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

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
        setContentView(R.layout.activity_sign_up);
        existingAccount=findViewById(R.id.existingAccount);
        signup=findViewById(R.id.signUp_app);
        email=findViewById(R.id.signup_email);
        number=findViewById(R.id.signup_number);
        password=findViewById(R.id.signup_password);
        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

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

                boolean valid=true;
                String error_msg="";
                if(email.getText().toString().matches(""))
                {
                    error_msg=error_msg+"Empty Email";
                    valid=false;
                }


                if(number.getText().toString().matches(""))
                {
                    if(error_msg.matches(""))
                    {
                        error_msg=error_msg+"Empty Number";
                    }
                    else
                    {
                        error_msg=error_msg+"\nEmpty Number";
                    }

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

                if(isNetworkAvailable()==false)
                {
                    valid=false;
                    if(error_msg.matches(""))
                    {
                        error_msg=error_msg+"No Internet Available";
                    }
                    else
                    {
                        error_msg=error_msg+"\nNo Internet Available";
                    }
                }

                if(valid==false){
                    Toast.makeText(signUp.this, error_msg, Toast.LENGTH_SHORT).show();
                }

                if(valid)
                {


                    mAuth.createUserWithEmailAndPassword(
                                email.getText().toString(),
                                password.getText().toString()
                        )
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String userID = user.getUid().toString();
                                    ContactInformation contact=new ContactInformation(email.getText().toString(), number.getText().toString());
                                    mDatabase.child("users").child(userID).child("contact_information").setValue(contact);

                                    Toast.makeText(signUp.this, "Successful Sign Up "+userID, Toast.LENGTH_SHORT).show();

                                    Intent i = new Intent(signUp.this, profileSetUp.class); //For Testing only
                                    i.putExtra("email", email.getText().toString());
                                    i.putExtra("number",number.getText().toString());
                                    startActivity(i);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(signUp.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }
        });

    }
}