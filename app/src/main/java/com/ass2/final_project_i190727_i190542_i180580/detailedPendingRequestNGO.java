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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Response;

public class detailedPendingRequestNGO extends AppCompatActivity {
    ImageView back, foodPic, acceptDonation;
    TextView name, address, city, number, email, foodDetails, time, donationID;
    String foodPicURL;
    ProgressBar loadingImage;

    String NGO_Name, NGO_Address, NGO_City, NGO_Number, NGO_Email;

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
        setContentView(R.layout.activity_detailed_pending_request_ngo);
        back=findViewById(R.id.back);
        name=findViewById(R.id.donorName);
        address=findViewById(R.id.donorAddress);
        city=findViewById(R.id.donorCity);
        number=findViewById(R.id.donorNumber);
        email=findViewById(R.id.donorEmail);
        foodDetails=findViewById(R.id.foodDetails);
        time=findViewById(R.id.donationTime);
        donationID=findViewById(R.id.donationID);
        foodPic=findViewById(R.id.foodPicture);
        loadingImage=findViewById(R.id.loadingImage);
        acceptDonation=findViewById(R.id.acceptDonation);

        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid().toString();

        boolean localData= sharedPreferences.getBoolean("localData",false);

        if (localData)
        {
//            profileType = sharedPreferences.getString("profileType", "");
            NGO_Name = sharedPreferences.getString("name", "");
            NGO_City = sharedPreferences.getString("city", "");
            NGO_Number = sharedPreferences.getString("contact","");
            NGO_Email = sharedPreferences.getString("email","");
            NGO_Address = sharedPreferences.getString("address", "");
        }

        if (isNetworkAvailable() && (localData==false))
        {
            mDatabase.child("users").child(userID).child("profile_information").child("address").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        NGO_Address = "" + String.valueOf(task.getResult().getValue());
                        myEdit.putString("address", NGO_Address);
                        myEdit.commit();
                    }
                }
            });

            mDatabase.child("users").child(userID).child("profile_information").child("city").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        NGO_City = "" + String.valueOf(task.getResult().getValue());
                        myEdit.putString("city", NGO_City);
                        myEdit.commit();
                    }
                }
            });

            mDatabase.child("users").child(userID).child("profile_information").child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        NGO_Name = "" + String.valueOf(task.getResult().getValue());
                        myEdit.putString("name", NGO_Name);
                        myEdit.commit();
                    }
                }
            });

            mDatabase.child("users").child(userID).child("contact_information").child("email").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        NGO_Email = "" + String.valueOf(task.getResult().getValue());
                        myEdit.putString("email", NGO_Email);
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
                        NGO_Number = "" + String.valueOf(task.getResult().getValue());
                        myEdit.putString("contact", NGO_Number);
                        myEdit.commit();
                    }
                }
            });

        }

        donationRequest req = getIntent().getParcelableExtra("pendingDonationRequestNGO");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(detailedPendingRequestNGO.this, pendingRequestNGO.class);
//                i.putExtra("canceledID", "");
                startActivity(i);
                finish();
            }
        });

        foodPicURL=req.getFoodPicURL();
        if (isNetworkAvailable())
        {
            loadingImage.setVisibility(View.VISIBLE);
            loadingImage.bringToFront();
            loadingImage.invalidate();
            Picasso.get().load(foodPicURL).into(foodPic, new Callback() {
                @Override
                public void onSuccess() {
                    loadingImage.setVisibility(View.GONE);
                    loadingImage.bringToFront();
                    loadingImage.invalidate();
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }

        acceptDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()) {
                    mDatabase.child("donations").child("donor").child(req.donorID).child("pending_request").child(req.getDonationID()).removeValue();
                    mDatabase.child("donations").child("donor").child("all_Pending_Request").child(req.getDonationID()).removeValue();

                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss");
                    Date date = new Date();
                    String currentDate = formatter.format(date);

                    acceptedRequest accept = new acceptedRequest(
                            req,
                            NGO_Name,
                            userID,
                            NGO_Address,
                            NGO_City,
                            currentDate,
                            NGO_Number,
                            NGO_Email
                    );

                    mDatabase.child("donations").child("donor").child(req.donorID).child("accepted_request").child(req.getDonationID()).setValue(accept);
                    mDatabase.child("donations").child("NGO").child(userID).child("accepted_request").child(req.getDonationID()).setValue(accept);

                    Intent i = new Intent(detailedPendingRequestNGO.this, pendingRequestNGO.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Toast.makeText(detailedPendingRequestNGO.this, "No Internet Access", Toast.LENGTH_SHORT).show();
                }
            }
        });

        name.setText("Name: "+req.getDonorName());
        address.setText("Address: "+req.getDonorAddress());
        city.setText("City: "+req.getDonorCity());
        number.setText("Contact: "+req.getNumber());
        email.setText("Email: "+req.getEmail());
        foodDetails.setText("Donation Details: "+req.getFoodDetails());
        time.setText("Date & Time: "+req.getTime());
        donationID.setText("Donation ID: "+req.getDonationID());
    }
}