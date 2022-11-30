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
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class acceptedRequestNGO extends AppCompatActivity {
    ImageView back;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    RecyclerView rv;
    List<acceptedRequest> ls;
    AcceptedRequestNGOAdapter adapter;

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
        setContentView(R.layout.activity_accepted_request_ngo);
        back=findViewById(R.id.back);
        rv=findViewById(R.id.rv);

        ls=new ArrayList<>();
        adapter=new AcceptedRequestNGOAdapter(ls,acceptedRequestNGO.this);

        RecyclerView.LayoutManager lm=new LinearLayoutManager(acceptedRequestNGO.this);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        boolean localData= sharedPreferences.getBoolean("localData",false);

        mAuth= FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid().toString();

        mDatabase= FirebaseDatabase.getInstance().getReference().child("donations").child("NGO").child(userID).child("accepted_request");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ls.clear();
                for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                    acceptedRequest request = datasnapshot.getValue(acceptedRequest.class);
                    ls.add(request);
                }
                adapter = new AcceptedRequestNGOAdapter(ls, acceptedRequestNGO.this);
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(acceptedRequestNGO.this, NGO_Home.class); //For Testing only
                startActivity(i);
                finish();
            }
        });

    }
}