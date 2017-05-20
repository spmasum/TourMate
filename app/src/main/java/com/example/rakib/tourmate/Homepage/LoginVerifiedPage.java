package com.example.rakib.tourmate.Homepage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.rakib.tourmate.MainPage.MainActivity;
import com.example.rakib.tourmate.R;
import com.example.rakib.tourmate.WeatherUpdate.Weather;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginVerifiedPage extends AppCompatActivity {
    private FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    String uid=firebaseAuth.getCurrentUser().getUid().toString();
    private DatabaseReference reference=FirebaseDatabase.getInstance().getReferenceFromUrl("https://tourmate-da70f.firebaseio.com/"+uid).child("Profile");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_verified_page);
        setTitle("Username:"+reference.child("Masum").getKey());
//        firebaseAuth.signOut();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.login_verified,menu);
        return true;
    }

    public void travelEvent(View view) {
        startActivity(new Intent(LoginVerifiedPage.this,HomePage.class));
    }


    public void weatherForecast(View view) {
        startActivity(new Intent(LoginVerifiedPage.this,Weather.class));
    }

    public void goLogout(MenuItem item) {
        firebaseAuth.signOut();
        startActivity(new Intent(LoginVerifiedPage.this, MainActivity.class));
    }
}
