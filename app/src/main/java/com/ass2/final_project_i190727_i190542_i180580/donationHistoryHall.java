package com.ass2.final_project_i190727_i190542_i180580;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class donationHistoryHall extends AppCompatActivity {

    ImageView back;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    String profileType;

    RecyclerView rv;
    List<completedRequest> ls;
    CompleteRequestAdapter adapter;

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
        setContentView(R.layout.activity_donation_history_hall);

        back=findViewById(R.id.back);
        rv=findViewById(R.id.rv);

        ls=new ArrayList<>();
        adapter=new CompleteRequestAdapter(ls,donationHistoryHall.this);

        RecyclerView.LayoutManager lm=new LinearLayoutManager(donationHistoryHall.this);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        boolean localData= sharedPreferences.getBoolean("localData",false);

        mAuth= FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid().toString();

        Intent temp = getIntent();
        profileType = temp.getStringExtra("profileType");

        if (isNetworkAvailable()) {
            if (profileType.matches("NGO"))
            {
                mDatabase= FirebaseDatabase.getInstance().getReference().child("donations").child("NGO").child(userID).child("completed_request");
            }
            else
            {
                mDatabase= FirebaseDatabase.getInstance().getReference().child("donations").child("donor").child(userID).child("completed_request");
            }

            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ls.clear();
                    for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                        completedRequest request = datasnapshot.getValue(completedRequest.class);
                        ls.add(request);
                    }
                    adapter = new CompleteRequestAdapter(ls, donationHistoryHall.this);
                    rv.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (profileType.matches("NGO"))
                {
                    Intent i = new Intent(donationHistoryHall.this, NGO_Home.class); //For Testing only
                    startActivity(i);
                    finish();
                }
                else
                {
                    Intent i = new Intent(donationHistoryHall.this, Hall_Individual_Home.class); //For Testing only
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (profileType.matches("NGO"))
        {
            Intent i = new Intent(donationHistoryHall.this, NGO_Home.class); //For Testing only
            startActivity(i);
            finish();
        }
        else
        {
            Intent i = new Intent(donationHistoryHall.this, Hall_Individual_Home.class); //For Testing only
            startActivity(i);
            finish();
        }
    }
}