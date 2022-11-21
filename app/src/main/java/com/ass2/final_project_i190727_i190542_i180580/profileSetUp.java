package com.ass2.final_project_i190727_i190542_i180580;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class profileSetUp extends AppCompatActivity {
    ImageView NGO, Hall, Individual, next;
    boolean NGO_Selected = false, Hall_Selected = false, Individual_Selected = false;
    EditText name, address, city, identity_number;
    String profile_type;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
//    String indentity_number_type;
//    RadioButton cnic, ntn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_set_up);
        NGO=findViewById(R.id.profileSetUp_NGO);
        Hall=findViewById(R.id.profileSetUp_Restaurant);
        next=findViewById(R.id.continue_profile);
        Individual=findViewById(R.id.profileSetUp_Individual);
        name=findViewById(R.id.profileSetUpName);
        address=findViewById(R.id.profileSetUpAddress);
        city=findViewById(R.id.profileSetUpCity);
        identity_number=findViewById(R.id.profileSetUpIdentityNumber);
        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

//        cnic=findViewById(R.id.radio_NIC);
//        ntn=findViewById(R.id.radio_NIC);

        NGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NGO_Selected=true;
                Hall_Selected=false;
                Individual_Selected=false;
                profile_type="NGO";
                Hall.setImageResource(R.drawable.curved_square);
                Individual.setImageResource(R.drawable.curved_square);
                NGO.setImageResource(R.drawable.selected_curved_square);
            }
        });

        Hall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NGO_Selected=false;;
                Hall_Selected=true;
                Individual_Selected=false;
                profile_type="Hall";
                Hall.setImageResource(R.drawable.selected_curved_square);
                NGO.setImageResource(R.drawable.curved_square);
                Individual.setImageResource(R.drawable.curved_square);
            }
        });

        Individual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NGO_Selected=false;;
                Hall_Selected=false;
                Individual_Selected=true;
                profile_type="Individual";
                Hall.setImageResource(R.drawable.curved_square);
                NGO.setImageResource(R.drawable.curved_square);
                Individual.setImageResource(R.drawable.selected_curved_square);
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean valid=true;
                String error_msg="";

                if(name.getText().toString().matches(""))
                {
                    valid=false;
                    error_msg=error_msg+"Empty Name";
                }

                if(address.getText().toString().matches(""))
                {
                    valid=false;
                    if(error_msg.matches(""))
                    {
                        error_msg=error_msg+"Empty Address";
                    }
                    else
                    {
                        error_msg=error_msg+"\nEmpty Address";
                    }
                }

                if(city.getText().toString().matches(""))
                {
                    valid=false;
                    if(error_msg.matches(""))
                    {
                        error_msg=error_msg+"Empty City";
                    }
                    else
                    {
                        error_msg=error_msg+"\nEmpty City";
                    }
                }

                if(identity_number.getText().toString().matches(""))
                {
                    valid=false;
                    if(error_msg.matches(""))
                    {
                        error_msg=error_msg+"Empty Identity Number";
                    }
                    else
                    {
                        error_msg=error_msg+"\nEmpty Identity Number";
                    }

                }

                if((NGO_Selected==false) && (Hall_Selected==false) && (Individual_Selected==false))
                {
                    valid=false;
                    if(error_msg.matches(""))
                    {
                        error_msg=error_msg+"Profile Type Not Selected";
                    }
                    else
                    {
                        error_msg=error_msg+"\nProfile Type Not Selected";
                    }
                }

//                if (ntn.isChecked())
//                {
//                    identity_number_type="NTN";
//                }
//                else if (cnic.isChecked())
//                {
//                    identity_number_type="CNIC";
//                }
//                else
//                {
//                    identity_number_type="";
//                }
//
//                if(identity_number_type.matches(""))
//                {
//                    valid=false;
//                    if(error_msg.matches(""))
//                    {
//                        error_msg=error_msg+"Identity Type Not Selected";
//                    }
//                    else
//                    {
//                        error_msg=error_msg+"\nIdentity Type Not Selected";
//                    }
//                }

                if (valid==false)
                {
                    Toast.makeText(profileSetUp.this, error_msg, Toast.LENGTH_SHORT).show();
                }

//                if(profile_type.matches("Individual") && (!(identity_number_type.matches("CNIC"))))
//                {
//                    Toast.makeText(profileSetUp.this, "Invalid ID Type for Individuals", Toast.LENGTH_SHORT).show();
//                    valid=false;
//                }
//
//                if((profile_type.matches("NGO") || profile_type.matches("Hall")) && (identity_number_type.matches("CNIC")))
//                {
//                    Toast.makeText(profileSetUp.this, "Invalid ID Type for Organisations", Toast.LENGTH_SHORT).show();
//                    valid=false;
//                }
//
                if (valid)
                {
                    if(profile_type.matches("Individual") && (identity_number.length()!=13))
                    {
                        Toast.makeText(profileSetUp.this, "For Individuals,\nEnters Valid 13 Digit CNIC", Toast.LENGTH_SHORT).show();
                        valid=false;
                    }

                    if((profile_type.matches("NGO")||profile_type.matches("Hall")) && (identity_number.length()!=7))
                    {
                        Toast.makeText(profileSetUp.this, "For NGO/Halls,\nEnters Valid 7 Digit NTN", Toast.LENGTH_SHORT).show();
                        valid=false;
                    }
                }

                if(valid)
                {
                    ProfileInformation profile=new ProfileInformation(
                            name.getText().toString(),
                            address.getText().toString(),
                            city.getText().toString(),
                            identity_number.getText().toString(),
                            profile_type
                    );

                    FirebaseUser user = mAuth.getCurrentUser();
                    String userID = user.getUid().toString();
                    mDatabase.child("users").child(userID).child("profile_information").setValue(profile);
                    mDatabase.child("users").child(userID).child("app_settings").child("notifications").setValue("True");
                    mDatabase.child("donations").child("donor").child(userID).child("Donation_Count").setValue("0");
                    Toast.makeText(profileSetUp.this, "Profile Info Added", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(profileSetUp.this, addProfilePicture.class); //For Testing only
                    startActivity(i);
                }
            }
        });
    }
}