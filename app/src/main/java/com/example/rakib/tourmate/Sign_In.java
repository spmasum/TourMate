package com.example.rakib.tourmate;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.rakib.tourmate.Homepage.HomePage;
import com.example.rakib.tourmate.Homepage.LoginVerifiedPage;
import com.example.rakib.tourmate.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Sign_In extends AppCompatActivity {
    private ActivitySignInBinding signInBinding;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        signInBinding= DataBindingUtil.setContentView(this,R.layout.activity_sign__in);
        setTitle("Sign In");
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth=FirebaseAuth.getInstance();

    }
    //--go to home page through login

    public void login(View view) {
        String email=signInBinding.loginEmailET.getText().toString().trim();
        String pass=signInBinding.loginPassET.getText().toString();
        if (!email.isEmpty()&&!pass.isEmpty()){
            firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(Sign_In.this,LoginVerifiedPage.class));
                }
                else {
                    signInBinding.loginEmailET.setError("Enter Valid Email");
                    Toast.makeText(Sign_In.this,""+task.getException().getMessage(),Toast.LENGTH_SHORT).show();


                }
                }
            });

            }
        else {
            //sign up validation

            if (email.isEmpty()){
                signInBinding.loginEmailET.setError("Email can't be empty");
            }
            if (pass.isEmpty()){
                signInBinding.loginPassET.setError("Password can't be empty");
            }
        }


        }


    //--go to sign up page
    public void signUp(View view) {
        startActivity(new Intent(Sign_In.this,SignUp.class));
    }
}
