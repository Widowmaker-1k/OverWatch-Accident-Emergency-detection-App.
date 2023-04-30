package com.example.overwatch;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public class AutoEmergency extends AppCompatActivity {
    int counter = 0, counter1 = 0, counter3 = 0, counter2 = 0;
    int check = 0;


    public static String username = "username";

    private static final long COUNTDOWN_TIME = 15000; // 15 seconds
    private static final long COUNTDOWN_TIME_2 = 5000; // 5 seconds
    private Button mButton;
    private AlertDialog mAlertDialog;
    private AlertDialog mAlertDialog1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        ImageView background = findViewById(R.id.imageView18);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);



        ActivityCompat.requestPermissions(AutoEmergency.this, new String[]{android.Manifest.permission.SEND_SMS, android.Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

        username = getIntent().getStringExtra("username");
        showAlertDialog();
        startCountDownTimer();

    }


    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Alert")
                .setMessage("Have you been in an accident ?")

                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        counter2++;
                        sendAccidentMessage();
                        Toast.makeText(AutoEmergency.this, "Sending emergency message...", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        showAlertDialog1();
                        startCountDownTimer1();

                        //startSecondCountDownTimer();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        counter3++;
                        Toast.makeText(AutoEmergency.this, "Emergency message was not sent...", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        mAlertDialog.dismiss();
                        Intent intent = new Intent(AutoEmergency.this, Home.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                    }
                });
        mAlertDialog = builder.create();
        mAlertDialog.show();


    }

    private void showAlertDialog1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Alert")
                .setMessage("")

                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        counter1++;
                        sendAccidentMessageTo();
                        Toast.makeText(AutoEmergency.this, "Sending emergency message to desiginated contacts", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AutoEmergency.this, Home.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                        dialog.cancel();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        counter++;
                        dialog.cancel();
                        Toast.makeText(AutoEmergency.this, "Your designated contacts were not notified", Toast.LENGTH_LONG).show();
                        mAlertDialog1.dismiss();
                        Intent intent = new Intent(AutoEmergency.this, Home.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                    }
                });
        mAlertDialog1 = builder.create();
        mAlertDialog1.show();
    }


    private void sendAccidentMessage() {
        String message = "This user might have been in an accident.";
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Location permission not granted", Toast.LENGTH_SHORT).show();
            return;
        }
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (lastKnownLocation != null) {
            double latitude = lastKnownLocation.getLatitude();
            double longitude = lastKnownLocation.getLongitude();
            message += "Location: " + latitude + "," + longitude;
        } else {
            message += "Location not available";
        }

        //Ghana Ambulance
        String phoneNumber1 = "0248911456";
        //Fire Service
        String phoneNumber2 = "0269531605";
        //Ghana Service Service
        String phoneNumber3 = "0540960243";


        sendSms(phoneNumber1, message);
        sendSms(phoneNumber2, message);
        sendSms(phoneNumber3, message);

    }

    private void sendAccidentMessageTo() {
        String message = "This user might have been in an accident.";
        String phoneNumber1 = "1234567890";
        //Fire Service
        String phoneNumber2 = "0269531605";
        //Ghana Service Service
        String phoneNumber3 = "0540960243";

        //  sendSms(person4Phone, message);
        // sendSms(person5Phone, message);
        // sendSms(person6Phone, message);
        sendSms(phoneNumber1, message);
        sendSms(phoneNumber2, message);
        sendSms(phoneNumber3, message);
    }


    private void sendSms(String phoneNumber, String message) {


        SmsManager mySmsManager = SmsManager.getDefault();
        mySmsManager.sendTextMessage(phoneNumber, null, message, null, null);


    }


    private void startCountDownTimer() {
        CountDownTimer downcount;
        downcount = new CountDownTimer(COUNTDOWN_TIME, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsLeft = (int) millisUntilFinished / 1000;
                String message = "Are you sure there is an emergency? \n\n" + " Sending Emergency message in " + secondsLeft + " seconds.";

                mAlertDialog.setMessage(message);
            }

            public void onFinish() {

                if (counter2 == 0 && counter3 == 0) {
                    sendAccidentMessage();

                }


                mAlertDialog.dismiss();

            }

        }.start();

    }

    private void startCountDownTimer1() {
        CountDownTimer downcount1;
        downcount1 = new CountDownTimer(COUNTDOWN_TIME_2, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsLeft = (int) millisUntilFinished / 1000;
                String message = "Do you want your designated contact to be alerted?\n\n" + " Sending notification in " + secondsLeft + " seconds.";
                mAlertDialog1.setMessage(message);
            }

            @Override
            public void onFinish() {
                if (counter == 0 && counter1 == 0) {
                    sendAccidentMessageTo();
                    mAlertDialog1.dismiss();
                    Toast.makeText(AutoEmergency.this, "Your designated contacts were notified", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AutoEmergency.this, Home.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                }
            }
        }.start();
    }

}