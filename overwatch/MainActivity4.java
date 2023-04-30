package com.example.overwatch;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity4 extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private static final long COUNTDOWN_TIME = 15000; // 15 seconds
    private AlertDialog mAlertDialog;
    private boolean mMessageSent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        android.widget.Button button = findViewById(R.id.confirm);
        button.setOnClickListener(view -> showAlertDialog());
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert")
                .setMessage("Are you sure you want to send an accident message?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    sendAccidentMessage();
                    startCountDownTimer();
                    Toast.makeText(MainActivity4.this, "Sending accident message...", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", (dialog, id) -> dialog.cancel());
        mAlertDialog = builder.create();
        mAlertDialog.show();
    }

    private void sendAccidentMessage() {
        String message = "This user has been in an accident.";
        String phoneNumber1 = "+233248911456";
        String phoneNumber2 = "+233275129740";
        String phoneNumber3 = "+233540960243";

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            sendSms(phoneNumber1, message);
            sendSms(phoneNumber2, message);
            sendSms(phoneNumber3, message);
            mMessageSent = true;
        }
    }

    private void sendSms(String phoneNumber, String message) {
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
        smsIntent.putExtra("sms_body", message);
        startActivity(smsIntent);
    }

    private void startCountDownTimer() {
        CountDownTimer mCountDownTimer = new CountDownTimer(COUNTDOWN_TIME, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsLeft = (int) millisUntilFinished / 1000;
                String message = "Sending accident message in " + secondsLeft + " seconds.";
                mAlertDialog.setMessage(message);
            }

            @Override
            public void onFinish() {
                if (!mMessageSent) {
                    sendAccidentMessage();
                    Toast.makeText(MainActivity4.this, "Sending accident message...", Toast.LENGTH_SHORT).show();
                }
                mAlertDialog.dismiss();
            }
        }.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED);
            }
        }
    }
}