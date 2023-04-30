package com.example.overwatch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private EditText emailEditText, passwordEditText;
    private CheckBox keepLoggedInCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        emailEditText = findViewById(R.id.login_username);
        passwordEditText = findViewById(R.id.login_password);
        keepLoggedInCheckbox = findViewById(R.id.keepLoggedInCheckbox);
        TextView forgotpass = findViewById(R.id.forgotpassword);
        Button login = findViewById(R.id.login_btn);
        TextView signup = findViewById(R.id.signup);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),resetpassword.class);
                startActivity(intent);
                finish();
            }
        });
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            String username = sharedPreferences.getString("username", "");
            launchHomeActivity(username);
        }

        signup.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), FormActivity.class);
            startActivity(intent);
        });

        login.setOnClickListener(view -> {
            String username = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (!username.isEmpty() && !password.isEmpty()) {
                progressBar.setVisibility(View.VISIBLE);
                String[] field = {"username", "password"};
                String[] data = {username, password};
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    PutData putData = new PutData("http:172.20.10.7/LoginRegister/login.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            progressBar.setVisibility(View.GONE);
                            String result = putData.getResult();
                            if (result.equals("Login Success ")) {
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                if (keepLoggedInCheckbox.isChecked()) {
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean("isLoggedIn", true);
                                    editor.putString("username", username);
                                    editor.apply();
                                }
                                launchHomeActivity(username);
                            } else {
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });

            } else {
                Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void launchHomeActivity(String username) {
        Intent intent = new Intent(this, Home.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }
}
