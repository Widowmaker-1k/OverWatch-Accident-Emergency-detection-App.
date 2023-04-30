package com.example.overwatch;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ManualEmergency extends AppCompatActivity {
    int counter = 0, counter1 = 0, counter3 = 0, counter2 = 0;
    int check = 0;
    List<String> designatedContacts = new ArrayList<>();

    public static String username = "username";
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private static final long COUNTDOWN_TIME = 15000; // 15 seconds
    private static final long COUNTDOWN_TIME_2 = 5000; // 5 seconds
    private Button mButton;
    private AlertDialog mAlertDialog;
    private AlertDialog mAlertDialog1;
    private AlertDialog mAlertDialog2;

    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        ImageView background = findViewById(R.id.imageView18);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        // Load data from file



        ActivityCompat.requestPermissions(ManualEmergency.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

        username = getIntent().getStringExtra("username");


        showAlertDialog();
        startCountDownTimer();

    }



    private void showAlertDialog() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert")
                .setMessage("Are you sure you want to send an accident message?")

                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        counter2++;
                        sendAccidentMessage();
                        Toast.makeText(ManualEmergency.this, "Sending emergency message...", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        stopSound();
                        cancelvibrate();
                        showAlertDialog1();
                        startCountDownTimer1();

                        //startSecondCountDownTimer();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        counter3++;
                        Toast.makeText(ManualEmergency.this, "Emergency message was not sent...", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        stopSound();
                        cancelvibrate();
                        mAlertDialog.dismiss();
                        Intent intent = new Intent(ManualEmergency.this, Home.class);
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
                        Toast.makeText(ManualEmergency.this, "Sending emergency message to desiginated contacts", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ManualEmergency.this, Home.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                        dialog.cancel();
                        stopSound();
                        cancelvibrate();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        counter++;
                        dialog.cancel();
                        stopSound();
                        cancelvibrate();
                        Toast.makeText(ManualEmergency.this, "Your designated contacts were not notified", Toast.LENGTH_LONG).show();
                        mAlertDialog1.dismiss();
                        Intent intent = new Intent(ManualEmergency.this, Home.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                    }
                });
        mAlertDialog1 = builder.create();
        mAlertDialog1.show();
    }


    private void sendAccidentMessage() {
        String message = "This user might have been in an emergency.";
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Location permission not granted", Toast.LENGTH_SHORT).show();
            return;
        }
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (lastKnownLocation != null) {
            double latitude = lastKnownLocation.getLatitude();
            double longitude = lastKnownLocation.getLongitude();
            message += "https://maps.google.com/?q="+latitude+","+longitude+"";
        } else {
            message += " Location not available";
        }

        //Ghana Ambulance
        String phoneNumber1 = "0275129740";
        //Fire Service
        String phoneNumber2 = "0594953928";
        //Ghana Service Service
        String phoneNumber3 = "0243603080";


        sendSms(phoneNumber1, message);
        sendSms(phoneNumber2, message);
        sendSms(phoneNumber3, message);

    }

    private void sendAccidentMessageTo() {
        String message = "This user might have been in an emergency.";
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Location permission not granted", Toast.LENGTH_SHORT).show();
            return;
        }
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (lastKnownLocation != null) {
            double latitude = lastKnownLocation.getLatitude();
            double longitude = lastKnownLocation.getLongitude();
            message += "https://maps.google.com/?q="+latitude+","+longitude+"";
        } else {
            message += " Location not available";
        }

        String fileName = "form_data.txt";
        try {
            FileInputStream fis = openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String person1phone = br.readLine();
            String person2phone = br.readLine();
            String person3phone = br.readLine();
            sendSms(person1phone, message);
            sendSms(person2phone, message);
            sendSms( person3phone , message);



            br.close();
            isr.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void sendSms(String phoneNumber, String message) {


        SmsManager mySmsManager = SmsManager.getDefault();
        mySmsManager.sendTextMessage(phoneNumber, null, message, null, null);


    }


    private void startCountDownTimer() {
        playSound();
        vibrate();
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
                    stopSound();
                    cancelvibrate();
                    Toast.makeText(ManualEmergency.this, "Sending emergency message to desiginated contacts", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ManualEmergency.this, Home.class);
                    intent.putExtra("username", username);
                    startActivity(intent);

                }


                mAlertDialog.dismiss();

            }

        }.start();

    }

    private void startCountDownTimer1() {

        vibrate();
        playSound();
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
                    stopSound();
                    cancelvibrate();
                    mAlertDialog1.dismiss();
                    Toast.makeText(ManualEmergency.this, "Your designated contacts were notified", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ManualEmergency.this, Home.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                }
            }
        }.start();
    }




    public void playSound()
    {
        Uri sos_code = getAlarmUri();
        mediaPlayer = MediaPlayer.create(ManualEmergency.this, R.raw.sos2);

        // Play an alarm ringtone
        if(mediaPlayer != null)
        {

            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }


    public void stopSound()
    {
        if(mediaPlayer != null && mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();

            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    // Get an alarm sound. Try for an alarm. If none set, try notification, otherwise, ringtone.
    private Uri getAlarmUri()
    {
        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        if(alert == null)
        {
            alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            if(alert == null)
            {
                alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }

        return alert;
}
public void vibrate()
{
    Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        VibrationEffect vibrationEffect = VibrationEffect.createWaveform(new long[]{
                0, 300, 200, 300, 200, 300,
                0, 300, 200, 300, 200, 300,
                0, 300, 200, 300, 200, 300,
                0, 300, 200, 300, 200, 300,
                0, 300, 200, 300, 200, 300,
                0, 300, 200, 300, 200, 300,
                0, 300, 200, 300, 200, 300,
                0, 300, 200, 300, 200, 300,
                0, 300, 200, 300, 200, 300,
                0, 300, 200, 300, 200, 300,
                0,300,200}, -1);
        vibrator.vibrate(vibrationEffect);
    } else {
        //deprecated in API 26
        vibrator.vibrate(new long[]{
                0, 300, 200, 300, 200, 300,
                0, 300, 200, 300, 200, 300,
                0, 300, 200, 300, 200, 300,
                0, 300, 200, 300, 200, 300,
                0, 300, 200, 300, 200, 300,
                0, 300, 200, 300, 200, 300,
                0, 300, 200, 300, 200, 300,
                0, 300, 200, 300, 200, 300,
                0, 300, 200, 300, 200, 300,
                0, 300, 200, 300, 200, 300,
                0,300,200}, -1);
    }
}
public void cancelvibrate(){
    Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    vibrator.cancel();
}
}