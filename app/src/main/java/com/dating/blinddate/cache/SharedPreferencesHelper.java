package com.dating.blinddate.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.dating.blinddate.Model.User;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.gson.Gson;

public class SharedPreferencesHelper {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    FirebaseStorage storage;
    FirebaseDatabase database;

    //-------------------------------Common  Method---------------------
    public SharedPreferencesHelper(Context context) {
        this.context = context;
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
    }
    public void SharedPreferencesHelper(String fileName) {
        sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    //-------------------------------Specific Method---------------------

    // saving onBoarding details of user for data manipulation
    public void setOnBoarding( ) {
        SharedPreferencesHelper("App_Data");
        editor.putBoolean("is_intro_done",true);
        editor.apply();
    }
    // fetching signUp details of user
    public Boolean isOnBoarding() {

        SharedPreferencesHelper("App_Data");
        return sharedPreferences.getBoolean("is_intro_done",false);
    }

    // User Login
    public void setUserDetails(String UserDetails){
        SharedPreferencesHelper("User_Details");
        editor.putBoolean("loged",true);
        editor.putString("user_details",UserDetails);
        editor.commit();
        editor.apply();
    }
    public User getUserDetails(){
        SharedPreferencesHelper("User_Details");
        String json = sharedPreferences.getString("user_details", "");
        return new Gson().fromJson(json,User.class);
    }
    public void logOutUser(){
        SharedPreferencesHelper("User_Details");
        editor.putBoolean("loged",false);
        editor.putString("user_details",null);
        editor.commit();
        editor.apply();
    }
    public Boolean getUserloged(){
        SharedPreferencesHelper("User_Details");
        return sharedPreferences.getBoolean("loged",false);

    }

}
