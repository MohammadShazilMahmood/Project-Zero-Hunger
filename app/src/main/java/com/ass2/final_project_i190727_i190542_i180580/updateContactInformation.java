package com.ass2.final_project_i190727_i190542_i180580;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class updateContactInformation extends AppCompatActivity {
    EditText number, address, city;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    ImageView update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact_information);

        number=findViewById(R.id.number);
        address=findViewById(R.id.address);
        city=findViewById(R.id.city);
        update=findViewById(R.id.update);

        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid().toString();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valid=true;

                String error_msg="";

                if (number.getText().toString().matches(""))
                {
                    error_msg=error_msg+"Empty Number";
                    valid=false;
                }

                if (address.getText().toString().matches(""))
                {
                    if(error_msg.matches(""))
                    {
                        error_msg=error_msg+"Empty Address";
                    }
                    else
                    {
                        error_msg=error_msg+"\nEmpty Address";
                    }

                    valid=false;
                }

                if (city.getText().toString().matches(""))
                {
                    if(error_msg.matches(""))
                    {
                        error_msg=error_msg+"Empty City";
                    }
                    else
                    {
                        error_msg=error_msg+"\nEmpty City";
                    }
                    valid=false;
                }

                if(valid==false){
                    Toast.makeText(updateContactInformation.this, error_msg, Toast.LENGTH_SHORT).show();
                }

                if (valid)
                {
                    mDatabase.child("users").child(userID).child("contact_information").child("number").setValue(number.getText().toString());
                    mDatabase.child("users").child(userID).child("profile_information").child("address").setValue(address.getText().toString());
                    mDatabase.child("users").child(userID).child("profile_information").child("city").setValue(city.getText().toString());

                    Intent i = new Intent(updateContactInformation.this, viewProfile.class); //For Testing only
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}