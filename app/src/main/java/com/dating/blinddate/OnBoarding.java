package com.dating.blinddate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.dating.blinddate.Adapter.OnBoardAdapter;
import com.dating.blinddate.Model.Theam;
import com.dating.blinddate.cache.SharedPreferencesHelper;
import com.dating.blinddate.databinding.ActivityOnBoardingBinding;


    /*=========================================================================================*/
    /*---------------------------- Class Level-----------------------------------------*/
    /*=========================================================================================*/
public class OnBoarding extends AppCompatActivity {


        /*=========================================================================================*/
        /*---------------------------- Variable  Declear-----------------------------------------*/
        /*=========================================================================================*/
    ActivityOnBoardingBinding binding;
    SharedPreferencesHelper App_data;


        /*=========================================================================================*/
        /*---------------------------- OnCreate (Main Method)-----------------------------------------*/
        /*=========================================================================================*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnBoardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //-------------------------------Variable  Initialization---------------------
        App_data = new SharedPreferencesHelper(OnBoarding.this);

        //-------------------------------Setting Top and Bottom Bar Of System---------------------
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.windowbarD));

        //-------------------------------Day & night Mode---------------------
        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_NO:
                getWindow().setNavigationBarColor(getResources().getColor(R.color.windowbarD));

                 //Toast.makeText(this, "Day", Toast.LENGTH_SHORT).show();
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                getWindow().setNavigationBarColor(getResources().getColor(R.color.windowbarN));
                // Toast.makeText(this, "Night", Toast.LENGTH_SHORT).show();
                break;
            default:
               // Toast.makeText(this, "Bichka", Toast.LENGTH_SHORT).show();
                break;
        }

        //-------------------------------Setting Adapter  For ViewPager---------------------
        OnBoardAdapter adapter = new OnBoardAdapter(this);
        binding.onBordPager.setAdapter(adapter);
        binding.OnBoardIndicator.setupWithViewPager(binding.onBordPager);

        //-------------------------------click Listener---------------------
        binding.JumpToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App_data.setOnBoarding();
                startActivity(new Intent(OnBoarding.this, SignIn.class));
                finish();
            }
        });
        binding.skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App_data.setOnBoarding();
                startActivity(new Intent(OnBoarding.this, SignIn.class));
                finish();
            }
        });
        binding.onBordPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(binding.onBordPager.getCurrentItem()==2){
                    binding.JumpToHome.setVisibility(View.VISIBLE);
                }
                else {
                    binding.JumpToHome.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}