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
import com.example.rakib.tourmate.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {
    private ActivitySignUpBinding signUpBinding;//data binding
    private FirebaseAuth firebaseAuth;//firebase authentication
    private FirebaseUser firebaseUser;//firebase user checkin
    private DatabaseReference mdatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        signUpBinding= DataBindingUtil.setContentView(this,R.layout.activity_sign_up);//data binding
        setTitle("Sign Up");

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
    }

    public void signUp(View view) {
        final String email=signUpBinding.signUpEmailET.getText().toString().trim();
        String pass=signUpBinding.signUpPassET.getText().toString();
        if (!email.isEmpty()&&!pass.isEmpty()){
            if (pass.length()<8){
                signUpBinding.signUpPassET.setError("Use at least 8 characters");
            }

                else {
                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {



                            if (task.isSuccessful()){
                                Toast.makeText(SignUp.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUp.this, LoginVerifiedPage.class));
                                //--create Database
                                String uid=firebaseAuth.getCurrentUser().getUid().toString();
                                mdatabaseReference= FirebaseDatabase.getInstance().getReference().child(""+uid);
                                String name=signUpBinding.signUpNameET.getText().toString();
                                String phone=signUpBinding.signUpPhoneET.getText().toString();
                                String address=signUpBinding.signUpAdrsET.getText().toString();
                                HashMap<String,String> datamap=new HashMap<String, String>();
                                datamap.put("Name",name);
                                datamap.put("Phone",phone);
                                datamap.put("Address",address);
                                mdatabaseReference.child("Profile").setValue(datamap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(SignUp.this,"Profile Created",Toast.LENGTH_SHORT).show();
//                                            FirebaseDatabase.getInstance().getReference().getKey().equals(""+firebaseAuth.getCurrentUser().getUid());
                                        }
                                        else {
                                            Toast.makeText(SignUp.this,"Not Successful",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                            else {
                                signUpBinding.signUpEmailET.setError("Enter Valid Email");
                                Toast.makeText(SignUp.this,""+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }





        }
        else {
            //sign up validation

            if (email.isEmpty()){
                signUpBinding.signUpEmailET.setError("Email can't be empty");
            }
            if (pass.isEmpty()){
                signUpBinding.signUpPassET.setError("Password can't be empty");
            }
        }

    }
}
