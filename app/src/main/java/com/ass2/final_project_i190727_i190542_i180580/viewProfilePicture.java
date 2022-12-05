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
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class viewProfilePicture extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    ImageView profilePicture, upload, selectPicture, back;
    String profilePictureURL="";
    boolean imageSelected=false;
    Uri image;
    String profileType="";
    ProgressBar loadingImage;

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
        setContentView(R.layout.activity_view_profile_picture);

        profilePicture=findViewById(R.id.profilePicture);
        upload=findViewById(R.id.upload_profile_pic);
        selectPicture=findViewById(R.id.select_profile_pic);
        back=findViewById(R.id.back);
        loadingImage=findViewById(R.id.loadingImage);

        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        boolean localData= sharedPreferences.getBoolean("localData",false);

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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (profileType.matches("NGO"))
                {
                    Intent i = new Intent(viewProfilePicture.this, NGO_Home.class); //For Testing only
                    startActivity(i);
                    finish();
                }
                else
                {
                    Intent i = new Intent(viewProfilePicture.this, Hall_Individual_Home.class); //For Testing only
                    startActivity(i);
                    finish();
                }
            }
        });

        //Load Profile Picture
        mDatabase.child("users").child(userID).child("profile_picture").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else
                {
                    profilePictureURL=String.valueOf(task.getResult().getValue());
                    if (isNetworkAvailable()) {
                        loadingImage.setVisibility(View.VISIBLE);
                        loadingImage.bringToFront();
                        loadingImage.invalidate();
                        Picasso.get().load(profilePictureURL).into(profilePicture, new Callback() {
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

//                    Toast.makeText(Hall_Individual_Home.this, profilePictureURL, Toast.LENGTH_SHORT).show();
                }
            }
        });

        selectPicture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 20);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valid=true;
                String error_msg="";

                if (imageSelected==false)
                {
                    error_msg=error_msg+"New Profile Picture Not Selected";
                    valid=false;
                }

                if(isNetworkAvailable()==false)
                {
                    valid=false;
                    if(error_msg.matches(""))
                    {
                        error_msg=error_msg+"No Internet Available";
                    }
                    else
                    {
                        error_msg=error_msg+"\nNo Internet Available";
                    }
                }

                if (valid==false)
                {
                    Toast.makeText(viewProfilePicture.this, error_msg, Toast.LENGTH_SHORT).show();
                }

                if (valid)
                {
                    FirebaseStorage storage= FirebaseStorage.getInstance();
                    StorageReference ref=storage.getReference().child("profile_pictures/"+userID+"/dp.jpg");

                    ProgressDialog progressDialog = new ProgressDialog(viewProfilePicture.this);
                    progressDialog.show();

                    ref.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> task=taskSnapshot.getStorage().getDownloadUrl();
                            task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    String userID = user.getUid().toString();
                                    mDatabase.child("users").child(userID).child("profile_picture").setValue(uri.toString());

                                    Toast.makeText(viewProfilePicture.this, "Profile Picture Updated", Toast.LENGTH_SHORT).show();
                                    imageSelected=false;
                                    progressDialog.dismiss();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(viewProfilePicture.this, "Failed to upload", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                            int currentProgress = (int)progress;
                            progressDialog.setMessage("Uploaded "+currentProgress+"%");
                        }
                    });
//                    mDatabase.child("users").child(userID).child("profile_information").setValue(profile);

//                    Toast.makeText(addProfilePicture.this, "Profile Picture Uploaded", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==20 & resultCode==RESULT_OK)
        {
            image=data.getData();
            profilePicture.setImageURI(image);
            imageSelected=true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (profileType.matches("NGO"))
        {
            Intent i = new Intent(viewProfilePicture.this, NGO_Home.class); //For Testing only
            startActivity(i);
            finish();
        }
        else
        {
            Intent i = new Intent(viewProfilePicture.this, Hall_Individual_Home.class); //For Testing only
            startActivity(i);
            finish();
        }
    }
}