package com.ass2.final_project_i190727_i190542_i180580;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class pendingRequestHall extends AppCompatActivity {
    ImageView back;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    RecyclerView rv;
    List<donationRequest> ls;
    PendingRequestHallAdapter adapter;

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
        setContentView(R.layout.activity_pending_request_hall);
        back=findViewById(R.id.back);
        rv=findViewById(R.id.rv);

        ls=new ArrayList<>();
        adapter=new PendingRequestHallAdapter(ls,pendingRequestHall.this);

        RecyclerView.LayoutManager lm=new LinearLayoutManager(pendingRequestHall.this);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        boolean localData= sharedPreferences.getBoolean("localData",false);

        mAuth= FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid().toString();

        mDatabase= FirebaseDatabase.getInstance().getReference().child("donations").child("donor").child(userID).child("pending_request");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ls.clear();
                for (DataSnapshot datasnapshot : snapshot.getChildren())
                {
                    donationRequest request = datasnapshot.getValue(donationRequest.class);
                    ls.add(request);
                }
                adapter = new PendingRequestHallAdapter(ls, pendingRequestHall.this);
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(pendingRequestHall.this, Hall_Individual_Home.class); //For Testing only
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(pendingRequestHall.this, Hall_Individual_Home.class); //For Testing only
        startActivity(i);
        finish();
    }
}