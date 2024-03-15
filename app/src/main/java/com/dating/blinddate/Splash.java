package com.dating.blinddate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.WindowManager;
import android.widget.Toast;

import com.dating.blinddate.Model.Theam;
import com.dating.blinddate.cache.SharedPreferencesHelper;
import com.dating.blinddate.databinding.ActivitySplashBinding;

public class Splash extends AppCompatActivity {

    //-------------------------------Variable  Declearation---------------------
    float v = 0;
    TextToSpeech myTTS;
    ActivitySplashBinding binding;

//-------------------------------OnCreate (Main ) Method ---------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_splash);

        //Setting colors on the bassis of Mode
        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_NO:
                binding.splacebg.setBackgroundColor(getResources().getColor(R.color.ActivityBgD));
                 break;
            case Configuration.UI_MODE_NIGHT_YES:
                binding.splacebg.setBackgroundColor(getResources().getColor(R.color.ActivityBgN));
                break;
            default:
                 break;
        }
       //
        // removing top bar
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Code for Delaying Screen
        int SPLASH_SCREEN_TIME_OUT = 2500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferencesHelper App_data = new SharedPreferencesHelper(Splash.this);
                Intent next;
                if(!App_data.isOnBoarding()){
                    next = new Intent(Splash.this, OnBoarding.class);

                }
                else if(!App_data.getUserloged()) {
                      next = new Intent(getApplicationContext(), SignIn.class);
                }
                else {
                    next = new Intent(getApplicationContext(), Home.class);

                }
                finish();
                startActivity(next);

                finish();

            }
        }, SPLASH_SCREEN_TIME_OUT);

    }

}