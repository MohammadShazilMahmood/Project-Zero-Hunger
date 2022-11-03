package com.ass2.final_project_i190727_i190542_i180580;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class profileSetUp extends AppCompatActivity {
    ImageView NGO, Hall, Individual, next;
    boolean NGO_Selected = false, Hall_Selected = false, Individual_Selected = false;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_set_up);
        NGO=findViewById(R.id.profileSetUp_NGO);
        Hall=findViewById(R.id.profileSetUp_Restaurant);
        next=findViewById(R.id.continue_profile);
        Individual=findViewById(R.id.profileSetUp_Individual);

        NGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NGO_Selected=true;
                Hall_Selected=false;
                Individual_Selected=false;
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
                Individual_Selected=false;
                Hall.setImageResource(R.drawable.curved_square);
                NGO.setImageResource(R.drawable.curved_square);
                Individual.setImageResource(R.drawable.selected_curved_square);
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(profileSetUp.this, addProfilePicture.class); //For Testing only
                startActivity(i);
            }
        });
    }
}