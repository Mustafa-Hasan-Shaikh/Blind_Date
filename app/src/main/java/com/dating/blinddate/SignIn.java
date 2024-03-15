package com.dating.blinddate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dating.blinddate.Model.User;
import com.dating.blinddate.cache.SharedPreferencesHelper;
import com.dating.blinddate.databinding.SignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.util.concurrent.atomic.AtomicReference;


/*=========================================================================================*/
    /*---------------------------- Class  Level-----------------------------------------*/
    /*=========================================================================================*/
public class SignIn extends AppCompatActivity {


        /*=========================================================================================*/
        /*---------------------------- Variable  Level-----------------------------------------*/
        /*=========================================================================================*/
    FirebaseAuth auth;
    DatabaseReference database;
    SignInBinding binding;
    ProgressDialog progressDialog;


        /*=========================================================================================*/
        /*---------------------------- OnCreate MainMethod-----------------------------------------*/
        /*=========================================================================================*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //-------------------------------Variable Initialization---------------------
        auth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(SignIn.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We are creating your account");

        //-------------------------------Listener Method---------------------
        binding.signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                auth.signInWithEmailAndPassword(binding.EmailId.getText().toString(),binding.password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            database = FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getUid()).child("Personal_details");
                            database.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    User details = new User();
                                    details.setLoged(true);
                                    details.setUserId(auth.getUid());

                                    if(snapshot.exists()) {
                                        if (snapshot.child("profilepic").exists()) {
                                            details.setProfilepic(snapshot.child("profilepic").getValue().toString());
                                        }
                                        details.setPhoneNo(snapshot.child("phoneNo").getValue().toString());
                                        details.setUserName(snapshot.child("userName").getValue().toString());

                                        setLogged(details,progressDialog);
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                        else {
                            Toast.makeText(SignIn.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this,SignUp.class));
                System.gc();
                finish();
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();

    }
    void setLogged(User details,ProgressDialog progressDialog) {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        //Saving Detils to login State
                        String  token = task.getResult();
                        database.child("device_Token").setValue(String.valueOf(token));

                        //Saving Details in Chase memory
                        String json = new Gson().toJson(details);
                        new SharedPreferencesHelper(this).setUserDetails(json);

                        progressDialog.dismiss();
                        startActivity(new Intent(SignIn.this,Home.class));
                    } else {
                        Toast.makeText(this, "LogIn Fail", Toast.LENGTH_SHORT).show();
                     }
                });
    }
}
