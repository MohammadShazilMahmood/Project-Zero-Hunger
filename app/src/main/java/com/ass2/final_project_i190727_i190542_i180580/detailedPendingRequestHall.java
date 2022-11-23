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

public class detailedPendingRequestHall extends AppCompatActivity {
    ImageView back, foodPic;
    TextView name, address, city, number, email, foodDetails, time, donationID;
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
        setContentView(R.layout.activity_detailed_pending_request_hall);
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

        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid().toString();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(detailedPendingRequestHall.this, pendingRequestHall.class);
                startActivity(i);
                finish();
            }
        });

        donationRequest req = getIntent().getParcelableExtra("pendingDonationRequestHall");

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