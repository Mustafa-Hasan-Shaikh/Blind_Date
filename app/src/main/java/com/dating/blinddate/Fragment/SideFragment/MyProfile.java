package com.dating.blinddate.Fragment.SideFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dating.blinddate.Model.User;
import com.dating.blinddate.R;
import com.dating.blinddate.cache.SharedPreferencesHelper;
import com.dating.blinddate.databinding.FragmentMyProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class MyProfile extends Fragment {
    DatabaseReference reference;
    FirebaseStorage storage;
    FirebaseUser auth;
    FragmentMyProfileBinding binding;
    User userDetails;
    String picUrl;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //-------------------------------Variable Initialization---------------------
        binding = FragmentMyProfileBinding.inflate(inflater,container,false);
        userDetails = new SharedPreferencesHelper(getContext()).getUserDetails();

       // String AuthId = data.getString("auth",null);
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userDetails.getUserId()).child("Personal_details");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("profilepic").exists()){
                picUrl = snapshot.child("profilepic").getValue().toString();
                if(picUrl!=null) {
                    Picasso.get().load(picUrl).placeholder(R.drawable.logo).into(binding.profileDpDisplay);
                }
                }
               binding.profileUserNameDisplay.setText( snapshot.child("userName").getValue().toString());
                if(snapshot.child("about").exists() && !snapshot.child("about").getValue().toString().isEmpty()){
               binding.profileUserAboutLayout.setHint(snapshot.child("about").getValue().toString());}else {
                    binding.profileUserAboutLayout.setHint("Hi! I am serching for date.");
                }
               binding.profileUserNameLayout.setHint(snapshot.child("userName").getValue().toString());
               binding.profileUserNumberLayout.setHint(snapshot.child("phoneNo").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //-------------------------------Listner Methods---------------------
        binding.profileChangePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,33);
            }
        });
        binding.profileDetailsSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation()!=null){
                   reference.updateChildren(validation());}
               // User details = new User(picUrl,us,binding.profileUserNameEdit.getText().toString(),binding.profileUserNumberEidt.getText().toString(),binding.profileUserEmailEdit.getText().toString());
              //  reference.setValue(details);
            }
        });
        return binding.getRoot();
    }

    //OnActivityresult for changing Pic
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getData() != null) {

            Uri sFile = data.getData();
            binding.profileDpDisplay.setImageURI(sFile);

            final StorageReference reference = storage.getReference().child(userDetails.getUserId())
                    .child("profilePic");
            reference.putFile(sFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            changeDP(uri.toString());
                        }
                    });
                    Toast.makeText(getContext(), "Image Chnaged", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    //Method for Storing Pic
    public void changeDP(String uri){
        this.reference.child("profilepic").setValue(uri);
    }
    Map validation(){
        Map<String, Object> updates = new HashMap<>();

        if(!picUrl.isEmpty()){
        updates.put("profilepic",picUrl);}
        if(!binding.profileUserAboutEdit.getText().toString().isEmpty()) {
            updates.put("about", binding.profileUserAboutEdit.getText().toString());
        }
        if(!binding.profileUserNameEdit.getText().toString().isEmpty()){
        updates.put("userName",binding.profileUserNameEdit.getText().toString());}
        if(!binding.profileUserNumberEidt.getText().toString().isEmpty()) {
            updates.put("phoneNo", binding.profileUserNumberEidt.getText().toString());
        }

        return updates;
    }
}