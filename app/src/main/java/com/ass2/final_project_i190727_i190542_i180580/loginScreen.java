package com.ass2.final_project_i190727_i190542_i180580;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
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

                if (isNetworkAvailable()==false)
                {
                    if(error_msg.matches(""))
                    {
                        error_msg=error_msg+"No Internet Available";
                    }
                    else
                    {
                        error_msg=error_msg+"\nNo Internet Available";
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

                            if (isNetworkAvailable()) {
                                //Load Name
                                mDatabase.child("users").child(userID).child("profile_information").child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (!task.isSuccessful()) {
                                            Log.e("firebase", "Error getting data", task.getException());
                                        } else {
                                            name = "" + String.valueOf(task.getResult().getValue());
                                            myEdit.putString("name", name);
                                            myEdit.commit();
                                        }
                                    }
                                });

                                //Load Profile Type
                                mDatabase.child("users").child(userID).child("profile_information").child("identityNumber").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (!task.isSuccessful()) {
                                            Log.e("firebase", "Error getting data", task.getException());
                                        } else {
                                            identityNumber = "" + String.valueOf(task.getResult().getValue());
                                            myEdit.putString("identityNumber", identityNumber);
                                            myEdit.commit();
                                        }
                                    }
                                });

                                //Load Profile Type
                                mDatabase.child("users").child(userID).child("contact_information").child("email").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (!task.isSuccessful()) {
                                            Log.e("firebase", "Error getting data", task.getException());
                                        } else {
                                            emailFetched = "" + String.valueOf(task.getResult().getValue());
                                            myEdit.putString("email", emailFetched);
                                            myEdit.commit();
                                        }
                                    }
                                });

                                //Load Profile Type
                                mDatabase.child("users").child(userID).child("contact_information").child("number").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (!task.isSuccessful()) {
                                            Log.e("firebase", "Error getting data", task.getException());
                                        } else {
                                            contactNum = "" + String.valueOf(task.getResult().getValue());
                                            myEdit.putString("contact", contactNum);
                                            myEdit.commit();
                                        }
                                    }
                                });

                                //Load Profile Type
                                mDatabase.child("users").child(userID).child("profile_information").child("address").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (!task.isSuccessful()) {
                                            Log.e("firebase", "Error getting data", task.getException());
                                        } else {
                                            addressFetched = "" + String.valueOf(task.getResult().getValue());
                                            myEdit.putString("address", addressFetched);
                                            myEdit.commit();
                                        }
                                    }
                                });

                                //Load Profile Type
                                mDatabase.child("users").child(userID).child("profile_information").child("city").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (!task.isSuccessful()) {
                                            Log.e("firebase", "Error getting data", task.getException());
                                        } else {
                                            cityFetched = "" + String.valueOf(task.getResult().getValue());
                                            myEdit.putString("city", cityFetched);
                                            myEdit.commit();
                                        }
                                    }
                                });

                                mDatabase.child("donations").child("donor").child(userID).child("Donation_Count").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (!task.isSuccessful()) {
                                            Log.e("firebase", "Error getting data", task.getException());
                                        } else {
                                            String donationCount = "" + String.valueOf(task.getResult().getValue());
                                            if (donationCount==null)
                                            {
                                                donationCount="0";
                                            }

                                            myEdit.putInt("donationCount", Integer.valueOf(donationCount));
                                            myEdit.commit();
                                        }
                                    }
                                });

                                myEdit.putString("userID", userID);
                                myEdit.putBoolean("localData", true);
                                myEdit.putBoolean("loggedIn", true);
                                myEdit.commit();

                                mDatabase.child("users").child(userID).child("profile_information").child("profileType").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (!task.isSuccessful()) {
                                            Log.e("firebase", "Error getting data", task.getException());
                                        } else {
                                            profileType=""+String.valueOf(task.getResult().getValue());
                                            myEdit.putString("profileType", profileType);
                                            myEdit.commit();

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
                                    }
                                });

//                                Toast.makeText(loginScreen.this, "Sign In", Toast.LENGTH_SHORT).show();
//                                if (profileType.matches("NGO"))
//                                {
//                                    Toast.makeText(loginScreen.this, "NGO NGO NGO", Toast.LENGTH_SHORT).show();
//                                    Intent i = new Intent(loginScreen.this, NGO_Home.class); //For Testing only
//                                    startActivity(i);
//                                    finish();
//                                }
//                                else
//                                {
//                                    Intent i = new Intent(loginScreen.this, Hall_Individual_Home.class); //For Testing only
//                                    startActivity(i);
//                                    finish();
//                                }
                            }



//                            //Load Profile Type
//                            mDatabase.child("users").child(userID).child("profile_information").child("profileType").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<DataSnapshot> task) {
//                                    if (!task.isSuccessful()) {
//                                        Log.e("firebase", "Error getting data", task.getException());
//                                    }
//                                    else
//                                    {
//                                        profileType=""+String.valueOf(task.getResult().getValue());
//                                    }
//                                }
//                            });

//                            Toast.makeText(loginScreen.this, "Sign In", Toast.LENGTH_SHORT).show();
//                            if (profileType.matches("NGO"))
//                            {
//                                Toast.makeText(loginScreen.this, "NGO NGO NGO", Toast.LENGTH_SHORT).show();
//                                Intent i = new Intent(loginScreen.this, NGO_Home.class); //For Testing only
//                                startActivity(i);
//                                finish();
//                            }
//                            else
//                            {
//                                Intent i = new Intent(loginScreen.this, Hall_Individual_Home.class); //For Testing only
//                                startActivity(i);
//                                finish();
//                            }
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