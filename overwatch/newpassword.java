package com.example.overwatch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class newpassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpassword);
        String email = getIntent().getStringExtra("email");
        EditText editTextNewpassword = findViewById(R.id.new_password);
        EditText editTextOTP = findViewById(R.id.OTP) ;
        Button button = findViewById(R.id.button);
        ProgressBar progressBar = findViewById(R.id.progress);
        TextView spamchecker = findViewById(R.id.spamchecker);
        String message = " Kindly check your " + email +  " spam or junk folder for your reset OTP";
        spamchecker.setText(message);

        ImageButton imageButton = findViewById(R.id.resetback);
        imageButton.setOnClickListener(v -> {
            Intent formIntent = new Intent(newpassword.this, resetpassword.class);
            startActivity(formIntent);
        });
        TextView resend = findViewById(R.id.resend);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                resend.setVisibility(View.VISIBLE);
            }
        }, 5000); // Delay in milliseconds (5 seconds)

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String newPassword = editTextNewpassword.getText().toString().trim();
                String otp = editTextOTP.getText().toString().trim();

                // Check if any field is empty
                if (newPassword.isEmpty() || otp.isEmpty()) {
                    Toast.makeText(newpassword.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!isPasswordValid(newPassword)) {
                    Toast.makeText(getApplicationContext(),"Password must contain at least 5 characters,a letter,a number and special character ",Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                RequestQueue queue = Volley.newRequestQueue(newpassword.this);
                String url ="http://172.20.10.7/LoginRegister/newpassword.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                progressBar.setVisibility(View.GONE);
                                if (response.trim().equals("success")){
                                    Toast.makeText(getApplicationContext(),"New password set ",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(),login.class);
                                    startActivity(intent);
                                    finish();

                                }
                                else
                                    Toast.makeText(getApplicationContext(),response.trim(),Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("otp", otp);
                        paramV.put("new_password", newPassword);
                        paramV.put("email",email);

                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });
    }

    private boolean isPasswordValid(String password) {
        String pattern = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!.])(?=\\S+$).{5,}$";
        return password.matches(pattern);


    }

    }