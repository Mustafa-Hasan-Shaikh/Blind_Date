package com.dating.blinddate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dating.blinddate.Model.User;
import com.dating.blinddate.cache.SharedPreferencesHelper;
import com.dating.blinddate.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class SignUp extends AppCompatActivity {

    DatabaseReference database;
    FirebaseAuth auth;
    ActivitySignUpBinding binding;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference().child("Users");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We are creating your account");

        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                auth.createUserWithEmailAndPassword(binding.email.getText().toString(),binding.confirmPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    User details = new User();
                                    details.setAbout("Hi! I searching for date");
                                    details.setUserName(binding.user.getText().toString());
                                    details.setPhoneNo(binding.mobileNumber.getText().toString());

                                    setLogged(details,progressDialog,database,task.getResult().getUser().getUid());
                                }
                                else {
                                    Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });
        binding.SingnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this,SignIn.class));
                finish();
                System.gc();
            }
        });
    }
    void setLogged(User details,ProgressDialog progressDialog,DatabaseReference database,String id) {
        //Fetching FCM token
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        //Saving Detils to login State
                        details.setDevice_Token(task.getResult());

                        //Saving Details to database
                        database.child(id).child("Personal_details").setValue(details);
                        
                        //Saving for chase
                        details.setUserId(id);

                        //Saving Details in Chase memory
                        String json = new Gson().toJson(details);
                        new SharedPreferencesHelper(getApplicationContext()).setUserDetails(json);

                        //Action after all done
                        progressDialog.dismiss();
                        startActivity(new Intent(SignUp.this,Home.class));
                        finish();
                        //Clearing Memory
                        System.gc();
                    } else {
                        Toast.makeText(this, "LogIn Fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}