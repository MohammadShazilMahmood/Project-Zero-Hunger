package com.ass2.final_project_i190727_i190542_i180580;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class addProfilePicture extends AppCompatActivity {
    ImageView selectPicture, profilePicture, next;
    Uri image;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    boolean imageSelected=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile_picture);
        selectPicture=findViewById(R.id.select_profile_pic);
        profilePicture=findViewById(R.id.profilePicture);
        next=findViewById(R.id.continue_profile_pic);
        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

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
                    FirebaseUser user = mAuth.getCurrentUser();
                    String userID = user.getUid().toString();

                    FirebaseStorage storage= FirebaseStorage.getInstance();
                    StorageReference ref=storage.getReference().child("profile_pictures/"+userID+"/dp.jpg");
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

                                    Toast.makeText(addProfilePicture.this, "Profile Picture Uploaded", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(addProfilePicture.this, "Failed to upload", Toast.LENGTH_SHORT).show();
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