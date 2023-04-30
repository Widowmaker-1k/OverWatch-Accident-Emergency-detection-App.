package com.example.overwatch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FormActivity extends AppCompatActivity {

    EditText firstNameEditText,phoneEditText, emailEditText,passwordEditText,ConfpasswordEditText,emailEditText2;

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        firstNameEditText = findViewById(R.id.firstNameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        emailEditText = findViewById(R.id.emailEditText);
        emailEditText2 =findViewById(R.id.emailEditText2);
        passwordEditText = findViewById(R.id.password_toggleEditText);
        ConfpasswordEditText = findViewById(R.id.Confpassword_toggleEditText);
        TextView textlogin = findViewById(R.id.textlogin);
        progressBar = findViewById(R.id.progress);
        textlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
            }
        });

        Button CreateAccButton = findViewById(R.id.CreateAccButton);
        CreateAccButton.setOnClickListener(v -> {

            if(passwordEditText.getText().toString().contentEquals(ConfpasswordEditText.getText())){

                String fullname,username,password,email;
                String phone_number;
                fullname = firstNameEditText.getText().toString();
                username =  emailEditText.getText().toString();
                password = passwordEditText.getText().toString();
                email =  phoneEditText.getText().toString();
                phone_number = emailEditText2.getText().toString();

                progressBar.setVisibility(View.INVISIBLE);
                if (!fullname.equals("")&&!username.equals("")&&!password.equals("")&&!email.equals("")&&isPasswordValid(password)) {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(() -> {

                        String[] field = new String[5];
                        //  int[] field1 = new int[1];

                        field[0] = "fullname";
                        field[1] = "email";
                        field[2] = "username";
                        field[3] = "phone_number";
                        field[4] = "password";
                        //Creating array for data
                        String[] data = new String[5];
                        data[0] = fullname;
                        data[1] =username;
                        data[2] = password;
                        data[3] = email;
                        data[4] = phone_number;


                        PutData putData = new PutData("http:172.20.10.7/LoginRegister/signup.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                progressBar.setVisibility(View.GONE);
                                String result = putData.getResult();
                                if (result.equals("Sign Up Success")){
                                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
                                    Intent formIntent = new Intent(FormActivity.this, login.class);
                                    startActivity(formIntent);
                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
                                }


                            }
                        }

                    });

                }else {
                    Toast.makeText(getApplicationContext(),"Password must contain at least 5 characters include,a number special character ",Toast.LENGTH_SHORT).show();
                }
            }

            else{
                Toast.makeText(FormActivity.this,"Passwords do not match!",Toast.LENGTH_SHORT).show();
            } });





    }

    private boolean isPasswordValid(String password) {
        String pattern = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!.])(?=\\S+$).{5,}$";
        return password.matches(pattern);
    }
    }



