package com.suzlibtechnologies.covid_19stats;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;

public class introActivity extends AppIntro {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        addSlide(AppIntroFragment.newInstance("Features", "Build anything from console widgets\n"+"to mobile application, with our free\n"+
                        "and easy to use API. We provide\n"+"data on current global outbreaks,\n"+"including COVID-19 and influenza.",
                R.drawable.features, ContextCompat.getColor(getApplicationContext(),R.color.first)));

        addSlide(AppIntroFragment.newInstance("Multi-Source", "we source data from Johns Hopkins University,\n"+"the New York Times,Worldometers,and\n"+
                        "Apple reports to give\n"+"We also provide official government data\n"+"for some countries, more to be added soon.",
                R.drawable.multisource, ContextCompat.getColor(getApplicationContext(),R.color.second)));

        addSlide(AppIntroFragment.newInstance("Up to Date", "We update our data as soon\n"+"as our sources do.",
                R.drawable.update, ContextCompat.getColor(getApplicationContext(),R.color.third)));



        sharedPreferences = getApplicationContext().getSharedPreferences("Mypreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if(sharedPreferences!=null){
            boolean checkShared = sharedPreferences.getBoolean("checkStated",false);
            if(checkShared == true){
                startActivity(new Intent(getApplicationContext(),SplashScreen.class));
            }
        }


    }


    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
        startActivity(new Intent(getApplicationContext(),SplashScreen.class));
        editor.putBoolean("checkStated",false).commit();
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        startActivity(new Intent(getApplicationContext(),SplashScreen.class));
        editor.putBoolean("checkStated",true).commit();
        finish();
    }

}