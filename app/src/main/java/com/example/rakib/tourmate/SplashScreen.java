package com.example.rakib.tourmate;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rakib.tourmate.MainPage.MainActivity;

import java.util.Timer;
import java.util.TimerTask;


public class SplashScreen extends AppCompatActivity
{
    private Timer timer;
    private ProgressBar progressBar;
    private int i=0;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        progressBar.setProgress(0);

        textView=(TextView)findViewById(R.id.textView);
        textView.setText("");
        final long period = 100;
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //this repeats every 100 ms
                if (i<100){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(String.valueOf(i)+"%");
                            if(!haveConnection()){
                                timer.cancel();
                                Toast.makeText(SplashScreen.this,"Check your internet connetion",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    progressBar.setProgress(i);
                    i+=10;
                }else{
                    //closing the timer
                    timer.cancel();
                    Intent intent =new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    // close this activity
                    finish();
                }
            }
        }, 0, period);

    }

    public boolean haveConnection(){
        boolean haveWifi=false;
        boolean haveMobile=false;

        ConnectivityManager cm= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos=cm.getAllNetworkInfo();

        for(NetworkInfo ni:networkInfos){
            if(ni.getTypeName().equalsIgnoreCase("WIFI")){
                if(ni.isConnected())
                    haveWifi=true;
            }
            if(ni.getTypeName().equalsIgnoreCase("MOBILE")){
                if (ni.isConnected()){
                    haveMobile=true;
                }
            }
        }
        return haveMobile||haveWifi;


    }

}
