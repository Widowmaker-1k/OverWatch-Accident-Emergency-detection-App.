package com.example.overwatch;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;

public class Home extends AppCompatActivity {
    public static final String username = "username";

    private ImageView ImageView;
    private ImageView imageView;
    private ImageButton buttonChange;
    private ImageButton buttonChangeBack;
    private int[] images = {R.drawable.firstaid2, R.drawable.firstaid3, R.drawable.firstaid4, R.drawable.cildcpr, R.drawable.childchocking};
    private int currentIndex = 0;
    private Handler handler = new Handler();

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            showNextImage();
            handler.postDelayed(this, 10000); // change image every 5 seconds
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);


        File directory = getApplicationContext().getDir("imageDir", Context.MODE_PRIVATE);
        //  Bitmap selectedImage = getIntent().getParcelableExtra("image");
        String fileName = "image.jpg";
        File file = new File(directory, fileName);
// Check if the file exists
        if (file.exists()) {
            // Load the image into the ImageView
            Bitmap scaledBitmap = loadImageFromInternalStorage(100, 100);
            ImageView home_dp = findViewById(R.id.home_dp);
            home_dp.setImageBitmap(scaledBitmap);
        }


        TextView usernameTextView = findViewById(R.id.user_greetings);
        ImageButton logset = findViewById(R.id.home_settings_btn);

        // retrieve the username value from the extras bundle
        String username = getIntent().getStringExtra("username");
        String contact1 = getIntent().getStringExtra("contact_one");
        String contact2 = getIntent().getStringExtra("contact_two");
        String contact3 = getIntent().getStringExtra("contact_three");
        // ; //putExtra method is used to pass the data
        // startActivity(intent);

        // get the current hour and AM/PM indicator using the Calendar class
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR);
        int amPm = calendar.get(Calendar.AM_PM);

        // set the greeting message based on the current time
        String greeting;
        if (amPm == Calendar.AM) greeting = "Good Morning ,";
        else {
            if (currentHour < 6) {
                greeting = "Good Afternoon ,";
            } else {
                greeting = "Good Evening ,";
            }
        }

        // set the greeting message and username in the TextView
        String message = greeting + " " + username;
        usernameTextView.setText(message);


        logset.setOnClickListener(v -> {
            Intent intent2 = new Intent(Home.this, Settings.class);
            intent2.putExtra("username", username);
            intent2.putExtra("PICK_IMAGE", 1);
            startActivity(intent2);
        });
        Button emerg = findViewById(R.id.emergency_btn);
        emerg.setOnClickListener(v -> {
            Intent intent3 = new Intent(Home.this, ManualEmergency.class);
            intent3.putExtra("contact_one", (CharSequence) contact1);
            intent3.putExtra("contact_two", (CharSequence) contact2);
            intent3.putExtra("contact_three", (CharSequence)  contact3);
            intent3.putExtra("username", username);
            startActivity(intent3);
        });
        ImageView = findViewById(R.id.home_dp);

        ImageView.setOnClickListener(v -> {
            Intent intent2 = new Intent(Home.this,MainActivity2.class);
            intent2.putExtra("username", username);
            startActivity(intent2);
        });
        //  ImageView.setImageBitmap(selectedImage);

        imageView = findViewById(R.id.firstaid_infograp);
        buttonChange = findViewById(R.id.back_btn);
        buttonChangeBack = findViewById(R.id.foward_btn);
        handler.postDelayed(runnable, 10000); // start automatic image change
        buttonChange.setOnClickListener(view -> showNextImage());
        buttonChangeBack.setOnClickListener(view -> showPreviousImage());
    }

    private void showNextImage() {
        currentIndex = (currentIndex + 1) % images.length;
        imageView.setImageResource(images[currentIndex]);
    }

    private void showPreviousImage() {
        currentIndex = (currentIndex - 1 + images.length) % images.length;
        imageView.setImageResource(images[currentIndex]);
    }
    // Load the saved image from internal storage
    private Bitmap loadImageFromInternalStorage(int targetWidth, int targetHeight) {
        // Get the file path from SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        String imagePath = sharedPref.getString("image_path", null);

        if (imagePath != null) {
            try {
                // Create a FileInputStream to read the image file
                FileInputStream fis = new FileInputStream(imagePath);

                // Create a Bitmap object from the image file
                Bitmap originalBitmap = BitmapFactory.decodeStream(fis);

                // Close the FileInputStream
                fis.close();

                // Create a scaled-down version of the Bitmap to fit the target ImageView
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, targetWidth, targetHeight, false);

                return scaledBitmap;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }



}










