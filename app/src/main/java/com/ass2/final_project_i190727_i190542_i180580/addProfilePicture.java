package com.ass2.final_project_i190727_i190542_i180580;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class addProfilePicture extends AppCompatActivity {
    ImageView selectPicture, profilePicture;
    Uri image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile_picture);
        selectPicture=findViewById(R.id.select_profile_pic);
        profilePicture=findViewById(R.id.profilePicture);

        selectPicture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 23);
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
        }

//        if (requestCode==24 & resultCode==RESULT_OK)
//        {
//            song=data.getData();
//            songSelect.setText("Audio Selected");
//        }

    }
}