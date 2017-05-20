package com.example.rakib.tourmate.MainPage;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.rakib.tourmate.R;
import com.example.rakib.tourmate.SignUp;
import com.example.rakib.tourmate.Sign_In;
import com.example.rakib.tourmate.WeatherUpdate.Weather;
import com.example.rakib.tourmate.WeatherUpdate.WeatherOne;
import com.example.rakib.tourmate.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
    }


    public void signUp(View view) {
        startActivity( new Intent(MainActivity.this,SignUp.class));
    }

    public void signIn(View view) {startActivity(new Intent(MainActivity.this, Sign_In.class)); }

    public void weatherForecast(View view) {
        startActivity(new Intent(MainActivity.this, WeatherOne.class));
    }
}
