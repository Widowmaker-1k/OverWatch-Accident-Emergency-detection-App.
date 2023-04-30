package com.example.overwatch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


    public class resetpassword extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_resetpassword);
            ProgressBar progressBar = findViewById(R.id.progress);

            EditText editText = findViewById(R.id.new_password);
            Button button= findViewById(R.id.button);

            ImageButton Back = findViewById(R.id.imageButton);
            Back.setOnClickListener(v -> {
                Intent formIntent = new Intent(resetpassword.this, login.class);
                startActivity(formIntent);
            });
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    RequestQueue queue = Volley.newRequestQueue(resetpassword.this);
                    String url ="http:172.20.10.7/LoginRegister/resetpassword.php";

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(resetpassword.this,newpassword.class);
                            intent.putExtra("email",editText.getText().toString());
                            startActivity(intent);
                            finish();
                        }
                    }, 10000);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressBar.setVisibility(View.GONE);
                                    Log.d("resetpassword", "Response: " + response.toString());

                                    if (response.trim().equals("success")){
                                        Toast.makeText(resetpassword.this,"OTP Sent",Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(resetpassword.this,newpassword.class);
                                        intent.putExtra("email",editText.getText().toString());
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(),response.trim(),Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }, error -> error.printStackTrace()){
                        protected Map<String, String> getParams(){
                            Map<String, String> paramV = new HashMap<>();
                            paramV.put("email", editText.getText().toString());
                            return paramV;
                        }
                    };
                    queue.add(stringRequest);
                }
            });

        }
    }
