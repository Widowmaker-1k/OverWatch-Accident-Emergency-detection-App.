package com.example.overwatch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class Settings extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        String username = getIntent().getStringExtra("username");

        ImageButton Back = findViewById(R.id.imageButton);
        Back.setOnClickListener(v -> {
            Intent formIntent = new Intent(Settings.this, Home.class);
            formIntent.putExtra("username", username);
            startActivity(formIntent);
        });

        ImageButton EditProfile = findViewById(R.id.toolbar);
        EditProfile.setOnClickListener(v -> {
            Intent formIntent = new Intent(Settings.this, NewForm.class);
            formIntent.putExtra("username", username);
            startActivity(formIntent);
        });

        ImageButton viewProfile = findViewById(R.id.view_profile);
        viewProfile.setOnClickListener(v -> {
            Intent formIntent = new Intent(Settings.this, MainActivity2.class);
            formIntent.putExtra("username", username);

            startActivity(formIntent);
        });

        ImageButton logoutButton = findViewById(R.id.logout);
        logoutButton.setOnClickListener(v -> logout());
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);



        ImageButton connectwearable = findViewById(R.id.connect_wearable);
        connectwearable.setOnClickListener(v -> {
            Intent formIntent = new Intent(Settings.this,connect_wearable.class);
            formIntent.putExtra("username", username);

            startActivity(formIntent);
        });

        ImageButton help = findViewById(R.id.help);
        help.setOnClickListener(v -> {
            Intent formIntent = new Intent(Settings.this,connect_wearable.class);
            formIntent.putExtra("username", username);

            startActivity(formIntent);
        });

        ImageButton sharedata = findViewById(R.id.sharedata);
        sharedata.setOnClickListener(v -> {
            Intent formIntent = new Intent(Settings.this,connect_wearable.class);
            formIntent.putExtra("username", username);

            startActivity(formIntent);
        });

        ImageButton tellafriend = findViewById(R.id.tellafriend);
        tellafriend.setOnClickListener(v -> {
            Intent formIntent = new Intent(Settings.this,connect_wearable.class);
            formIntent.putExtra("username", username);

            startActivity(formIntent);
        });
    }

    private void logout() {
        // Clear user session

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.remove("username");
        editor.apply();
        File directory = getApplicationContext().getDir("imageDir", Context.MODE_PRIVATE);
        String fileName = "image.jpg";
        File file = new File(directory, fileName);
        if (file.exists()) {
            boolean success = file.delete();
        }
        // Redirect the user to the login activity
        Intent intent = new Intent(Settings.this, login.class);
        startActivity(intent);
        finish(); // Remove this activity from the back stack
    }

}


