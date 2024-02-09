package com.sp.madproject.startup;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

import com.sp.madproject.R;


public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.sound);
        mediaPlayer.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, UserSelectActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}