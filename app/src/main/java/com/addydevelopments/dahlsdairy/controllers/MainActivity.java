package com.addydevelopments.dahlsdairy.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.addydevelopments.dahlsdairy.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hides the action bar
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        //Attempts to launch routes activity
        Button routesLaunchButton = findViewById(R.id.routesButton);
        routesLaunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), RoutesActivity.class);
                startActivity(startIntent);
            }
        });

        //Attemps to launch customer list activity
        Button customerActivityButton = findViewById(R.id.customerButton);
        customerActivityButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), CustomerList.class);
                startActivity(startIntent);
            }
        });

    }
}
