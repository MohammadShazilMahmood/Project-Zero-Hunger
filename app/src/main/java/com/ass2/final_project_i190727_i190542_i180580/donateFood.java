package com.ass2.final_project_i190727_i190542_i180580;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;

public class donateFood extends AppCompatActivity {
    ImageView selectPicture, foodPicture, addRequest, back;
    EditText foodDetails, address;
    Uri image;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    String profileTypeBack="", addressVal="", name="", city="";
    String finalAddress="";
    Integer donationCount=0;
    boolean imageSelected=false;

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
        setContentView(R.layout.activity_donate_food);

        selectPicture=findViewById(R.id.select_food_pic);
        foodPicture=findViewById(R.id.foodPicture);
        addRequest=findViewById(R.id.addDonation);
        back=findViewById(R.id.back);
        foodDetails=findViewById(R.id.foodDetails);
        address=findViewById(R.id.address);

        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid().toString();

        boolean localData= sharedPreferences.getBoolean("localData",false);

        if (localData)
        {
            profileTypeBack = sharedPreferences.getString("profileType", "");
            name = sharedPreferences.getString("name", "");
            city = sharedPreferences.getString("city", "");

            addressVal = sharedPreferences.getString("address", "");
            address.setHint("Address: "+addressVal);

            donationCount = sharedPreferences.getInt("donationCount",0);
        }

        if (isNetworkAvailable() && (localData==false))
        {
            mDatabase.child("users").child(userID).child("profile_information").child("profileType").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        profileTypeBack = "" + String.valueOf(task.getResult().getValue());
                        myEdit.putString("profileType", profileTypeBack);
                        myEdit.commit();
                    }
                }
            });

            mDatabase.child("users").child(userID).child("profile_information").child("address").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        addressVal = "" + String.valueOf(task.getResult().getValue());
                        address.setHint("Address: "+addressVal);
                        myEdit.putString("address", addressVal);
                        myEdit.commit();
                    }
                }
            });

            mDatabase.child("donations").child("donor").child(userID).child("Donation_Count").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        String donationCount = "" + String.valueOf(task.getResult().getValue());
                        myEdit.putInt("donationCount", Integer.valueOf(donationCount));
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
                        city = "" + String.valueOf(task.getResult().getValue());
                        myEdit.putString("city", city);
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
                        name = "" + String.valueOf(task.getResult().getValue());
                        myEdit.putString("name", name);
                        myEdit.commit();
                    }
                }
            });
        }

        selectPicture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 21);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(donateFood.this, Hall_Individual_Home.class); //For Testing only
                startActivity(i);
                finish();
            }
        });

        addRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valid=true;
                String error_msg="";

                if(foodDetails.getText().toString().matches(""))
                {
                    valid=false;
                    error_msg=error_msg+"Empty Food Details";
                }

                if(imageSelected==false)
                {
                    valid=false;
                    if(error_msg.matches(""))
                    {
                        error_msg=error_msg+"Picture Not Selected";
                    }
                    else
                    {
                        error_msg=error_msg+"\nPicture Not Selected";
                    }
                }

                if (isNetworkAvailable()==false)
                {
                    valid=false;
                    if(error_msg.matches(""))
                    {
                        error_msg=error_msg+"Internet Not Available";
                    }
                    else
                    {
                        error_msg=error_msg+"\nInternet Not Available";
                    }
                }

                if (valid==false)
                {
                    Toast.makeText(donateFood.this, error_msg, Toast.LENGTH_SHORT).show();
                }

                if (valid)
                {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss");
                    Date date = new Date();
                    String currentDate=formatter.format(date);
                    String donationID= String.valueOf(donationCount)+"_"+userID;

                    if (address.getText().toString().matches(""))
                    {
                        finalAddress=addressVal;
                    }
                    else
                    {
                        finalAddress=address.getText().toString();
                    }

                    FirebaseStorage storage= FirebaseStorage.getInstance();
                    StorageReference ref=storage.getReference().child("foodDonations/"+userID+"/"+donationID+"/foodPic.jpg");

                    ProgressDialog progressDialog = new ProgressDialog(donateFood.this);
                    progressDialog.show();

                    ref.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> task=taskSnapshot.getStorage().getDownloadUrl();
                            task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    progressDialog.dismiss();
                                    String ImageURL="";
                                    ImageURL=uri.toString();
                                    donationRequest donation= new donationRequest(donationID,
                                            name,
                                            userID,
                                            finalAddress,
                                            city,
                                            ImageURL,
                                            foodDetails.getText().toString(),
                                            currentDate);

                                    mDatabase.child("donations").child("donor").child(userID).child("pending_request").child(donationID).setValue(donation);
                                    donationCount=donationCount+1;
                                    mDatabase.child("donations").child("donor").child(userID).child("Donation_Count").setValue(String.valueOf(donationCount));
                                    myEdit.putInt("donationCount", donationCount);
                                    myEdit.commit();

                                    Toast.makeText(donateFood.this, "Donation Request Added", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(donateFood.this, Hall_Individual_Home.class); //For Testing only
                                    startActivity(i);
                                    finish();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(donateFood.this, "Failed to upload", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                            int currentProgress = (int)progress;
                            progressDialog.setMessage("Uploaded "+currentProgress+"%");
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==21 & resultCode==RESULT_OK)
        {
            image=data.getData();
            foodPicture.setImageURI(image);
            imageSelected=true;
        }
    }
}