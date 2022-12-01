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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class detailedCompletedRequest extends AppCompatActivity {
    ImageView back, foodPic;
    TextView foodDetails, donationTime, donationID, donorName, donorAddress, donorCity, donorNumber, donorEmail;
    TextView acceptTime, NGOName, NGOCity, NGOAddress, NGONumber, NGOEmail, collectTime;
    String foodPicURL, profileType;
    ProgressBar loadingImage;

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
        setContentView(R.layout.activity_detailed_completed_request);
        back = findViewById(R.id.back);
        foodPic=findViewById(R.id.foodPicture);
        loadingImage=findViewById(R.id.loadingImage);
        foodDetails=findViewById(R.id.foodDetails);
        donationTime=findViewById(R.id.donationTime);
        donationID=findViewById(R.id.donationID);
        donorName=findViewById(R.id.donorName);
        donorAddress=findViewById(R.id.donorAddress);
        donorCity=findViewById(R.id.donorCity);
        donorNumber=findViewById(R.id.donorNumber);
        donorEmail=findViewById(R.id.donorEmail);
        acceptTime=findViewById(R.id.acceptTime);
        NGOName=findViewById(R.id.NGOName);
        NGOCity=findViewById(R.id.NGOCity);
        NGOAddress=findViewById(R.id.NGOAddress);
        NGONumber=findViewById(R.id.NGONumber);
        NGOEmail=findViewById(R.id.NGOEmail);
        collectTime=findViewById(R.id.collectTime);

        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid().toString();

        boolean localData= sharedPreferences.getBoolean("localData",false);

        completedRequest req = getIntent().getParcelableExtra("completedDonationRequest");

        if (localData)
        {
            profileType = sharedPreferences.getString("profileType", "");
        }

        if (isNetworkAvailable() && (localData==false)) {
            mDatabase.child("users").child(userID).child("profile_information").child("profileType").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        profileType = "" + String.valueOf(task.getResult().getValue());
                    }
                }
            });
        }

        foodPicURL=req.getAccepted_request().getRequest().getFoodPicURL();
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(detailedCompletedRequest.this, donationHistoryHall.class);
                i.putExtra("profileType", profileType);
                startActivity(i);
                finish();
            }
        });

        foodDetails.setText("Description: "+req.getAccepted_request().getRequest().getFoodDetails());
        donationTime.setText("Request Date and Time: "+req.getAccepted_request().getRequest().getTime());
        donationID.setText("Donation ID: "+req.getAccepted_request().getRequest().getDonorName());
        donorName.setText("Name: "+req.getAccepted_request().getRequest().getDonorName());
        donorAddress.setText("Address: "+req.getAccepted_request().getRequest().getDonorAddress());
        donorCity.setText("City: "+req.getAccepted_request().getRequest().getDonorCity());
        donorNumber.setText("Contact: "+req.getAccepted_request().getRequest().getNumber());
        donorEmail.setText("Email: "+req.getAccepted_request().getRequest().getEmail());
        acceptTime.setText("Accepting Date and Time: "+req.getAccepted_request().acceptedTime);
        NGOName.setText("Name: "+req.getAccepted_request().getNGO_Name());
        NGOCity.setText("City: "+req.getAccepted_request().getNGO_City());
        NGOAddress.setText("Address: "+req.getAccepted_request().getNGO_Address());
        NGONumber.setText("Contact: "+req.getAccepted_request().getNGO_Number());
        NGOEmail.setText("Email: "+req.getAccepted_request().getNGO_Email());
        collectTime.setText("Collection Date and Time: "+req.getCompletion_time());
    }
}