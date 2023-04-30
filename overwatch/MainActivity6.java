package com.example.overwatch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MainActivity6 extends AppCompatActivity{
       // view.setImageURI(imageUri);

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        imageView = findViewById(R.id.imageViewsee);

        // Retrieve the image URI from the intent
        String imageUriString = getIntent().getStringExtra("imageUri");
        Uri imageUri = Uri.parse(imageUriString);

        // Load the image into the ImageView using Glide or other image loading libraries
        Glide.with(this)
                .load(imageUri)
                .into(imageView);
        ImageButton returnb = findViewById(R.id.imageButton14);
        returnb.setOnClickListener(v -> {
            Intent Intent = new Intent(MainActivity6.this, Home.class);
            Intent.putExtra("imageUri",imageUri.toString());
            startActivity(Intent);
        });
    }

}
