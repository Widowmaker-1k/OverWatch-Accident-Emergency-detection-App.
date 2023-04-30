package com.example.overwatch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {






        Button signUpButton;
        Button signinButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            signUpButton = findViewById(R.id.signUpButton);
            signinButton = findViewById(R.id.signinButton);

            signUpButton.setOnClickListener(v -> {
                Intent formIntent = new Intent(MainActivity.this, FormActivity.class);
                startActivity(formIntent);

            });
            signinButton.setOnClickListener(v -> {
                Intent formIntent = new Intent(MainActivity.this, login.class);
                startActivity(formIntent);
            });

        }

    }

