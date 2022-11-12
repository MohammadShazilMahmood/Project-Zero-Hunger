package com.ass2.final_project_i190727_i190542_i180580;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class donateFood extends AppCompatActivity {
    ImageView selectPicture, foodPicture, addRequest, back;
    EditText foodDetails;
    Uri image;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    boolean imageSelected=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_food);

        selectPicture=findViewById(R.id.select_food_pic);
        foodPicture=findViewById(R.id.foodPicture);
        addRequest=findViewById(R.id.addDonation);
        back=findViewById(R.id.back);
        foodDetails=findViewById(R.id.foodDetails);

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

                if (valid==false)
                {
                    Toast.makeText(donateFood.this, error_msg, Toast.LENGTH_SHORT).show();
                }

                if (valid)
                {
                    Toast.makeText(donateFood.this, "Donation Request Added", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(donateFood.this, Hall_Individual_Home.class); //For Testing only
                    startActivity(i);
                    finish();
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