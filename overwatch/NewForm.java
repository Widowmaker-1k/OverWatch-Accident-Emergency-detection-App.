package com.example.overwatch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;

public class NewForm extends AppCompatActivity {
    public static String username = "username";
    EditText person1name,person1phone,person1relationship,person2name, person2phone,person2relationship, person3name,person3phone, person3relationship,id ;
    ProgressBar progressBar;
    private boolean hasSubmitted = false;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = getIntent().getStringExtra("username");

        setContentView(R.layout.activity_new_form);
        sharedPreferences = getSharedPreferences("form_data", MODE_PRIVATE);
        hasSubmitted = sharedPreferences.getBoolean("hasSubmitted", false);


        ImageButton form_back = findViewById(R.id.imageButton);
        form_back.setOnClickListener(v -> {
            Intent formIntent = new Intent(NewForm.this, Settings.class);
            formIntent.putExtra("username", username);
            startActivity(formIntent);
        });


        person1name = findViewById(R.id.person1_name);
        person1phone = findViewById(R.id.person1_phone);
        person1relationship = findViewById(R.id.person1_relationship);
        person2name = findViewById(R.id.person2_name);
        person2phone = findViewById(R.id.person2_phone);
        person2relationship = findViewById(R.id.person2_relationship);
        person3name = findViewById(R.id.person3_name);
        person3phone = findViewById(R.id.person3_phone);
        person3relationship = findViewById(R.id.person3_relationship);
        progressBar = findViewById(R.id.progressBar2);
        id=findViewById(R.id.textView13);
        Button Submit = findViewById(R.id.button4);
        Submit.setOnClickListener(v -> {

            if (hasSubmitted) {
                Toast.makeText(getApplicationContext(), "You have already submitted the form.", Toast.LENGTH_SHORT).show();
                return;
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("hasSubmitted", true);
            editor.apply();
            Intent formIntent = new Intent(NewForm.this, Home.class);
            String username = getIntent().getStringExtra("username");
            startActivity(formIntent);

            String person1, contact1, relationship1, person2, contact2, relationship2, person3, contact3, relationship3, newid;


            person1 = person1name.getText().toString();
            contact1 = person1phone.getText().toString();
            relationship1 = person1relationship.getText().toString();
            person2 = person2name.getText().toString();
            contact2 = person2phone.getText().toString();
            relationship2 = person2relationship.getText().toString();
            person3 = person3name.getText().toString();
            contact3 = person3phone.getText().toString();
            relationship3 = person3relationship.getText().toString();
            newid = id.getText().toString();


            progressBar.setVisibility(View.INVISIBLE);
            if (!person1.equals("") && !contact1.equals("") && !relationship1.equals("") && !person2.equals("") && !contact2.equals("") && !relationship2.equals("") && !person3.equals("") && !contact3.equals("") && !relationship3.equals("")) {
                progressBar.setVisibility(View.VISIBLE);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {

                    String[] field = new String[10];
                    //  int[] field1 = new int[1];

                    field[0] = "person1";
                    field[1] = "contact1";
                    field[2] = "relationship1";
                    field[3] = "person2";
                    field[4] = "contact2";
                    field[5] = "relationship2";
                    field[6] = "person3";
                    field[7] = "contact3";
                    field[8] = "relationship3";
                    field[9] = "newid";

                    //Creating array for data
                    String[] data = new String[10];
                    data[0] = person1;
                    data[1] = contact1;
                    data[2] = relationship1;
                    data[3] = person2;
                    data[4] = contact2;
                    data[5] = relationship2;
                    data[6] = person3;
                    data[7] = contact3;
                    data[8] = relationship3;
                    data[9] = newid;


                    PutData putData = new PutData("http:172.20.10.7/LoginRegister/addData.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            progressBar.setVisibility(View.GONE);
                            String result = putData.getResult();
                            if (result.equals("Data added")) {
                                saveFormData();
                                // Save data to file
                                //counter to see if a user has already added
                                String fileName = "form_data.txt";
                                String fileContents = person1phone.getText().toString() + "\n" +
                                        person2phone.getText().toString() + "\n" +
                                        person3phone.getText().toString();
                                try {
                                    FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                                    fos.write(fileContents.getBytes());
                                    fos.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                Intent formIntenT = new Intent(NewForm.this,Home.class);
                                formIntenT.putExtra("username", username);
                                startActivity(formIntenT);
                                finish();
                                hasSubmitted = true;
                            }
                            else {
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                            }


                        }
                    }

                });

            } else {
                Toast.makeText(getApplicationContext(), "All fields required ", Toast.LENGTH_SHORT).show();
            }
            // }else{
            //  Toast.makeText(NewForm.this, "USERNAME DOES NOT MATCH!!", Toast.LENGTH_SHORT).show();
            //}


        });

    }
    private void saveFormData() {
        String person1 = person1name.getText().toString();
        String contact1 = person1phone.getText().toString();
        String relationship1 = person1relationship.getText().toString();
        String person2 = person2name.getText().toString();
        String contact2 = person2phone.getText().toString();
        String relationship2 = person2relationship.getText().toString();
        String person3 = person3name.getText().toString();
        String contact3 = person3phone.getText().toString();
        String relationship3 = person3relationship.getText().toString();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("person1", person1);
        editor.putString("contact1", contact1);
        editor.putString("relationship1", relationship1);
        editor.putString("person2", person2);
        editor.putString("contact2", contact2);
        editor.putString("relationship2", relationship2);
        editor.putString("person3", person3);
        editor.putString("contact3", contact3);
        editor.putString("relationship3", relationship3);
        editor.apply();
        sharedPreferences.edit().putBoolean("hasSubmitted", hasSubmitted).apply();


        Toast.makeText(this, "Form data saved", Toast.LENGTH_SHORT).show();
    }

    private void loadFormData() {
        String person1 = sharedPreferences.getString("person1", "");
        String contact1 = sharedPreferences.getString("contact1", "");
        String relationship1 = sharedPreferences.getString("relationship1", "");
        String person2 = sharedPreferences.getString("person2", "");
        String contact2 = sharedPreferences.getString("contact2", "");
        String relationship2 = sharedPreferences.getString("relationship2", "");
        String person3 = sharedPreferences.getString("person3", "");
        String contact3 = sharedPreferences.getString("contact3", "");
        String relationship3 = sharedPreferences.getString("relationship3", "");

        person1name.setText(person1);
        person1phone.setText(contact1);
        person1relationship.setText(relationship1);
        person2name.setText(person2);
        person2phone.setText(contact2);
        person2relationship.setText(relationship2);
        person3name.setText(person3);
        person3phone.setText(contact3);
        person3relationship.setText(relationship3);
    }
}