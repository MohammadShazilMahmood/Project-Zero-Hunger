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

public class detailedAcceptedRequestNGO extends AppCompatActivity {
    ImageView back, foodPic, collectDonation;
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
        setContentView(R.layout.activity_detailed_accepted_request_ngo);
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
        collectDonation=findViewById(R.id.collectDonation);

        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid().toString();

        boolean localData= sharedPreferences.getBoolean("localData",false);

        acceptedRequest req = getIntent().getParcelableExtra("acceptedDonationRequestNGO");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(detailedAcceptedRequestNGO.this, acceptedRequestNGO.class);
//                i.putExtra("canceledID", "");
                startActivity(i);
                finish();
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

        name.setText("Name: "+req.getRequest().getDonorName());
        address.setText("Address: "+req.getRequest().getDonorAddress());
        city.setText("City: "+req.getRequest().getDonorCity());
        number.setText("Contact: "+req.getRequest().getNumber());
        email.setText("Email: "+req.getRequest().getEmail());
        foodDetails.setText("Donation Details: "+req.getRequest().getFoodDetails());
        time.setText("Date & Time: "+req.getRequest().getTime());
        donationID.setText("Donation ID: "+req.getRequest().getDonationID());
    }
}