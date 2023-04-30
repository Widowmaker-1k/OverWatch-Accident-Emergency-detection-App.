package com.example.overwatch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity2 extends AppCompatActivity {
    public static String username = "username";
    public static String name = "name";
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String IMAGE_KEY = "image_key";









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = getIntent().getStringExtra("username");
        name = getIntent().getStringExtra("name");
        setContentView(R.layout.activity_main2);

        File directory = getApplicationContext().getDir("imageDir", Context.MODE_PRIVATE);
        //  Bitmap selectedImage = getIntent().getParcelableExtra("image");
        String fileName = "image.jpg";
        File file = new File(directory, fileName);
// Check if the file exists
        if (file.exists()) {
            // Load the image into the ImageView
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            ImageView settings_dp = findViewById(R.id.imageView2);
            settings_dp.setImageBitmap(bitmap);
        }

        ImageButton Back = findViewById(R.id.backback);
        Back.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this,Settings.class);
            intent.putExtra("username", username);

            startActivity(intent);
        });
        Button edit_profile = findViewById(R.id.edit_profile);
        edit_profile.setOnClickListener(v -> {
            Intent formIntent = new Intent(MainActivity2.this, MainActivity3.class);
            formIntent.putExtra("username", username);
            formIntent.putExtra("fullname", name);
            startActivity(formIntent);
        });


        ImageView settings_dp = findViewById(R.id.imageView2);
        settings_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();


            }
        });


        // Build the URL for the PHP script with the username value in the query string
        String url = "http://172.20.10.7/LoginRegister/view.php?username=" + username;

        // Send an HTTP GET request to the PHP script
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        // Get the user data from the JSON response
                        String name = response.getString("fullname");
                        String email = response.getString("email");
                        String contact = response.getString("phone_number");

                        // Display the user data in TextViews
                        TextView Fullname1 = findViewById(R.id.textView);
                        Fullname1.setText(name);
                        TextView Fullname = findViewById(R.id.Fullname_display);
                        Fullname.setText(name);

                        TextView Email2 = findViewById(R.id.textView2);
                        Email2.setText(email);

                        TextView Email = findViewById(R.id.email_status);
                        Email.setText(email);

                        TextView phoneNumber = findViewById(R.id.Number_view);
                        phoneNumber.setText(contact);

                        TextView Username = findViewById(R.id.Username_insettngs);
                        Username.setText(username);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        showToast("Connection Failed");
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        showToast("No Data Found");

                    }

                    Log.d("MainActivity2", "Response: " + response.toString());
                },
                error -> error.printStackTrace());
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void openGallery() {
        // Create an Intent to open the phone's gallery
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // Start the image selection activity
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView settings_dp = findViewById(R.id.imageView2);

        // Check if the result is for the image selection request
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            // Get the URI of the selected image
            Uri imageUri = data.getData();

            try {
                // Get the Bitmap of the selected image
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

                // Set the Bitmap as the image resource of the settings_dp ImageView
                settings_dp.setImageBitmap(bitmap);
                saveImageToInternalStorage(bitmap);



            } catch (IOException e) {
                e.printStackTrace();





            }
        }


    }
    // Save the selected image to internal storage
    private void saveImageToInternalStorage(Bitmap bitmap) {
        // Get the directory where the image file will be saved
        File directory = getApplicationContext().getDir("imageDir", Context.MODE_PRIVATE);

        // Create a file name for the image file
        String fileName = "image.jpg";

        try {
            // Create a file object with the directory and file name
            File file = new File(directory, fileName);

            // Create a FileOutputStream to write the image to the file
            FileOutputStream fos = new FileOutputStream(file);

            // Compress the image to JPEG format and write it to the FileOutputStream
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

            // Close the FileOutputStream
            fos.close();

            // Save the file path to SharedPreferences
            SharedPreferences sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("image_path", file.getAbsolutePath());
            editor.apply();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


