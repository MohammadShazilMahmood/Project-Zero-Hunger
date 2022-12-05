package com.ass2.final_project_i190727_i190542_i180580;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class detailedAcceptedRequestHall extends AppCompatActivity {
    ImageView back, foodPic;
    TextView NGOName, NGOAddress, address, NGOCity, NGONumber, NGOEmail, foodDetails, time, donationID, acceptTime;
    String foodPicURL;
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
        setContentView(R.layout.activity_detailed_accepted_request_hall);
        back=findViewById(R.id.back);
        NGOName=findViewById(R.id.NGOName);
        address=findViewById(R.id.donorAddress);
        NGOCity=findViewById(R.id.NGOCity);
        NGONumber=findViewById(R.id.NGONumber);
        NGOEmail=findViewById(R.id.NGOEmail);
        NGOAddress=findViewById(R.id.NGOAddress);
        foodDetails=findViewById(R.id.foodDetails);
        acceptTime=findViewById(R.id.acceptTime);
        time=findViewById(R.id.donationTime);
        donationID=findViewById(R.id.donationID);
        foodPic=findViewById(R.id.foodPicture);
        loadingImage=findViewById(R.id.loadingImage);

        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid().toString();

        boolean localData= sharedPreferences.getBoolean("localData",false);

        acceptedRequest req = getIntent().getParcelableExtra("acceptedDonationRequestHall");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()) {
                    Intent i = new Intent(detailedAcceptedRequestHall.this, acceptedRequestHall.class);
//                i.putExtra("canceledID", "");
                    startActivity(i);
                    finish();
                }
                else
                {
                    Intent i = new Intent(detailedAcceptedRequestHall.this, Hall_Individual_Home.class);
//                i.putExtra("canceledID", "");
                    startActivity(i);
                    finish();
                }
            }
        });

        foodPicURL=req.getRequest().getFoodPicURL();
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

        NGOName.setText("Name: "+req.getNGO_Name());
        address.setText("Address: "+req.getRequest().getDonorAddress());
        NGOAddress.setText("Address: "+req.getNGO_Address());
        NGOCity.setText("City: "+req.getNGO_City());
        NGONumber.setText("Contact: "+req.getNGO_Number());
        NGOEmail.setText("Email: "+req.getNGO_Email());
        foodDetails.setText("Donation Details: "+req.getRequest().getFoodDetails());
        time.setText("Request Date and Time: "+req.getRequest().getTime());
        acceptTime.setText("Accepting Date and Time: "+req.acceptedTime);
        donationID.setText("Donation ID: "+req.getRequest().getDonationID());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isNetworkAvailable()) {
            Intent i = new Intent(detailedAcceptedRequestHall.this, acceptedRequestHall.class);
//                i.putExtra("canceledID", "");
            startActivity(i);
            finish();
        }
        else
        {
            Intent i = new Intent(detailedAcceptedRequestHall.this, Hall_Individual_Home.class);
//                i.putExtra("canceledID", "");
            startActivity(i);
            finish();
        }
    }
}