package com.ass2.final_project_i190727_i190542_i180580;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

public class appSettings extends AppCompatActivity {
    ImageView back, saveSettings;
    Switch notifications;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_settings);

        back=findViewById(R.id.back);
        saveSettings=findViewById(R.id.saveSettings);
        notifications=findViewById(R.id.notificationSettings);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(appSettings.this, Hall_Individual_Home.class); //For Testing only
                startActivity(i);
                finish();
            }
        });

    }
}