package com.example.overwatch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class connect_wearable extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connet_wearable);
        ImageView message = findViewById(R.id.imageView4);

        String username = getIntent().getStringExtra("username");


        ImageButton Back = findViewById(R.id.imageButton);
        Back.setOnClickListener(v -> {
            Intent formIntent = new Intent(connect_wearable.this,Settings.class);
            formIntent.putExtra("username", username);
            startActivity(formIntent);
        });


    }
}