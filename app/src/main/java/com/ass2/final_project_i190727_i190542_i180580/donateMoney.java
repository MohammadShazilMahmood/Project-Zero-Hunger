package com.ass2.final_project_i190727_i190542_i180580;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class donateMoney extends AppCompatActivity {
    ImageView back, foodPicture;
    EditText foodDetails, address;
    Uri image;
    String profileTypeBack;
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
        setContentView(R.layout.activity_donate_money);
        back=findViewById(R.id.back);
//        foodDetails=findViewById(R.id.foodDetails);
//        address=findViewById(R.id.address);
//        foodPicture=findViewById(R.id.foodPicture);

        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();


//        address.setText("TESTING");
//        if (isNetworkAvailable()==false)
//        {
//            boolean localData= sharedPreferences.getBoolean("localData",false);
//            if (localData)
//            {
//                Toast.makeText(donateMoney.this, "Local Data", Toast.LENGTH_SHORT).show();
//                profileTypeBack = sharedPreferences.getString("profileType", "");
//
//                String addressVal = sharedPreferences.getString("address", "");
//                address.setText("TESTING");
//            }
//        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(donateMoney.this, Hall_Individual_Home.class); //For Testing only
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(donateMoney.this, Hall_Individual_Home.class); //For Testing only
        startActivity(i);
        finish();
    }
}