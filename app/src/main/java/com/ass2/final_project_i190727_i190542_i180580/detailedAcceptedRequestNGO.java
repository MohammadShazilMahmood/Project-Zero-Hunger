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
import com.onesignal.OneSignal;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class detailedAcceptedRequestNGO extends AppCompatActivity {
    ImageView back, foodPic, collectDonation;
    TextView name, address, city, number, email, foodDetails, time, donationID;
    String foodPicURL;
    ProgressBar loadingImage;

    String NGO_Name, NGO_Address, NGO_City, NGO_Number, NGO_Email;

    String notifications="";
    String logged_in="";

    JSONObject json;

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
                if (isNetworkAvailable()) {
                    Intent i = new Intent(detailedAcceptedRequestNGO.this, acceptedRequestNGO.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Intent i = new Intent(detailedAcceptedRequestNGO.this, NGO_Home.class);
                    startActivity(i);
                    finish();
                }
            }
        });

        collectDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()) {
                    mDatabase.child("donations").child("donor").child(req.getRequest().getDonorID()).child("accepted_request").child(req.getRequest().getDonationID()).removeValue();
                    mDatabase.child("donations").child("NGO").child(req.getNGO_ID()).child("accepted_request").child(req.getRequest().getDonationID()).removeValue();

                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss");
                    Date date = new Date();
                    String currentDate = formatter.format(date);

                    completedRequest complete_request = new completedRequest(
                            req,
                            currentDate
                    );

                    mDatabase.child("donations").child("donor").child(req.getRequest().getDonorID()).child("completed_request").child(req.getRequest().getDonationID()).setValue(complete_request);
                    mDatabase.child("donations").child("NGO").child(req.getNGO_ID()).child("completed_request").child(req.getRequest().getDonationID()).setValue(complete_request);

                    mDatabase.child("player_id").child("Hall").child(req.getRequest().getDonorID()).child("loggedIN").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()) {
                                Log.e("firebase", "Error getting data", task.getException());
                            } else {
                                logged_in = "" + String.valueOf(task.getResult().getValue());
                            }
                        }
                    });

                    mDatabase.child("player_id").child("Hall").child(req.getRequest().getDonorID()).child("notificationSettings").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()) {
                                Log.e("firebase", "Error getting data", task.getException());
                            } else {
                                notifications = "" + String.valueOf(task.getResult().getValue());
                            }
                        }
                    });

                    mDatabase.child("player_id").child("Hall").child(req.getRequest().getDonorID()).child("playerID").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()) {
                                Log.e("firebase", "Error getting data", task.getException());
                            } else {
                                String pid = "" + String.valueOf(task.getResult().getValue());
                                if (logged_in.toString().matches("True") && notifications.toString().matches("True")) {
                                    String message = "Donation ID: " + req.getRequest().getDonationID() + "\nCollected By: " + req.getNGO_Name() + "\n" + currentDate;
                                    String heading = "PZH Donation Collected";

                                    try {
                                        json = new JSONObject("{ 'include_player_ids': [ '" + pid + "' ]," +
                                                "'contents': { 'en' : '" + message + "' } ," +
                                                " 'headings' :{'en':'" + heading + "'} }");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    OneSignal.postNotification(json, new OneSignal.PostNotificationResponseHandler() {
                                        @Override
                                        public void onSuccess(JSONObject jsonObject) {

                                        }

                                        @Override
                                        public void onFailure(JSONObject jsonObject) {

                                        }
                                    });
                                }
                            }
                        }
                    });

                    Intent i = new Intent(detailedAcceptedRequestNGO.this, acceptedRequestNGO.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Toast.makeText(detailedAcceptedRequestNGO.this, "No Internet Access", Toast.LENGTH_SHORT).show();
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

        name.setText("Name: "+req.getRequest().getDonorName());
        address.setText("Address: "+req.getRequest().getDonorAddress());
        city.setText("City: "+req.getRequest().getDonorCity());
        number.setText("Contact: "+req.getRequest().getNumber());
        email.setText("Email: "+req.getRequest().getEmail());
        foodDetails.setText("Donation Details: "+req.getRequest().getFoodDetails());
        time.setText("Date & Time: "+req.getRequest().getTime());
        donationID.setText("Donation ID: "+req.getRequest().getDonationID());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isNetworkAvailable()) {
            Intent i = new Intent(detailedAcceptedRequestNGO.this, acceptedRequestNGO.class);
            startActivity(i);
            finish();
        }
        else
        {
            Intent i = new Intent(detailedAcceptedRequestNGO.this, NGO_Home.class);
            startActivity(i);
            finish();
        }
    }
}