package com.example.overwatch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;


public class MainActivity3 extends AppCompatActivity {
    public static String username = "username";
    private EditText fullnameEditText, emailEditText, phoneEditText, passwordEditText, oldpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile);
        //
        File directory = getApplicationContext().getDir("imageDir", Context.MODE_PRIVATE);
        //  Bitmap selectedImage = getIntent().getParcelableExtra("image");
        String fileName = "image.jpg";
        File file = new File(directory, fileName);
// Check if the file exists
        if (file.exists()) {
            // Load the image into the ImageView
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            ImageView imageView = findViewById(R.id.imageView5);
            imageView.setImageBitmap(bitmap);
        }
        username = getIntent().getStringExtra("username");

        fullnameEditText = findViewById(R.id.Fullname_display);
        emailEditText = findViewById(R.id.Email_Change);
        phoneEditText = findViewById(R.id.Number_view);
        passwordEditText = findViewById(R.id.password_new);
        oldpassword = findViewById(R.id.old_password);
        Button updateButton = findViewById(R.id.button2);

        updateButton.setOnClickListener(v -> {


            String fullname = fullnameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String phone = phoneEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String old_password = oldpassword.getText().toString().trim();

            if (!fullname.equals("") && !password.equals("") && !email.equals("") && !phone.equals("")) {
                if (isPasswordValid(password)) {
                    String[] field = {"fullname", "old_password", "password", "email", "phone_number", "username"};
                    String[] data = {fullname, old_password, password, email, phone, username};
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(() -> {

                        PutData putData = new PutData("http:172.20.10.7/LoginRegister/update.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                if (result.equals("Success")) {
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                    Intent Intent = new Intent(MainActivity3.this,Home.class);
                                    Intent.putExtra("username", username);
                                    startActivity(Intent);
                                    // launchHomeActivity(username);
                                } else {
                                    Log.d("resetpassword", "Response: " + result);

                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });


                } else {
                    Toast.makeText(getApplicationContext(), "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton back2 = findViewById(R.id.back_btn2);
        back2.setOnClickListener(v -> {
            Intent formIntent = new Intent(MainActivity3.this, Settings.class);
            formIntent.putExtra("username", username);
            startActivity(formIntent);
        });

    }

    private boolean isPasswordValid(String password) {
        String pattern = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!.])(?=\\S+$).{5,}$";
        return password.matches(pattern);
    }
}