package com.ass2.final_project_i190727_i190542_i180580;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

public class addProfilePicture extends AppCompatActivity {
    ImageView selectPicture, profilePicture, next;
    Uri image;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    boolean imageSelected=false;
    String profileType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile_picture);
        selectPicture=findViewById(R.id.select_profile_pic);
        profilePicture=findViewById(R.id.profilePicture);
        next=findViewById(R.id.continue_profile_pic);

        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid().toString();

        //Load Profile Type
        mDatabase.child("users").child(userID).child("profile_information").child("profileType").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else
                {
                    profileType=""+String.valueOf(task.getResult().getValue());
                }
            }
        });

        selectPicture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 23);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valid=true;

                if (imageSelected==false)
                {
                    Toast.makeText(addProfilePicture.this, "Image not selected", Toast.LENGTH_SHORT).show();
                    valid=false;
                }

                if (valid)
                {
                    FirebaseStorage storage= FirebaseStorage.getInstance();
                    StorageReference ref=storage.getReference().child("profile_pictures/"+userID+"/dp.jpg");

                    ProgressDialog progressDialog = new ProgressDialog(addProfilePicture.this);
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
                                    progressDialog.dismiss();
                                    mDatabase.child("users").child(userID).child("profile_picture").setValue(uri.toString());

                                    Toast.makeText(addProfilePicture.this, "Profile Picture Uploaded", Toast.LENGTH_SHORT).show();
                                    if (profileType.matches("NGO"))
                                    {
                                        Intent i = new Intent(addProfilePicture.this, NGO_Home.class); //For Testing only
                                        startActivity(i);
                                        finish();
                                    }
                                    else
                                    {
                                        Intent i = new Intent(addProfilePicture.this, Hall_Individual_Home.class); //For Testing only
                                        startActivity(i);
                                        finish();
                                    }
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(addProfilePicture.this, "Failed to upload", Toast.LENGTH_SHORT).show();
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
        if (requestCode==23 & resultCode==RESULT_OK)
        {
            image=data.getData();
            profilePicture.setImageURI(image);
            imageSelected=true;
        }

//        if (requestCode==24 & resultCode==RESULT_OK)
//        {
//            song=data.getData();
//            songSelect.setText("Audio Selected");
//        }
    }


}