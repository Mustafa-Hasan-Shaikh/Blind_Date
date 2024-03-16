package com.dating.blinddate.Fragment.SideFragment.FriendProfile;

import android.content.Intent;
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
import com.dating.blinddate.databinding.FragmentFriendProfileBinding;
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

public class FriendProfile extends Fragment {
    DatabaseReference reference;
    String friendID;
    FragmentFriendProfileBinding binding;
    String picUrl;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //-------------------------------Variable Initialization---------------------
        binding = FragmentFriendProfileBinding.inflate(inflater,container,false);
        binding.profileUserNameDisplay.setText(friendID);
       // reference = FirebaseDatabase.getInstance().getReference().child("Users").child(friendID).child("Personal_details");
    /*    reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


               *//* if(snapshot.child("about").exists() && !snapshot.child("about").getValue().toString().isEmpty()){
                    binding.profileUserAboutLayout.setHint(snapshot.child("about").getValue().toString());}else {
                    binding.profileUserAboutLayout.setHint("Hi! I am serching for date.");
                }
                binding.profileUserNameLayout.setHint(snapshot.child("userName").getValue().toString());
                binding.profileUserNumberLayout.setHint(snapshot.child("phoneNo").getValue().toString());
                if(snapshot.child("profilepic").exists()){
                    picUrl = snapshot.child("profilepic").getValue().toString();
                    if(picUrl!=null) {
                        Picasso.get().load(picUrl).placeholder(R.drawable.profile_circle).into(binding.profileDpDisplay);
                    }
                }*//*
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/


        binding.acceptRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(String.valueOf( friendID));
                Toast.makeText(getContext(),String.valueOf( friendID), Toast.LENGTH_SHORT).show();
               // reference.setValue("details");
            }
        });
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friend_profile, container, false);
    }

    public FriendProfile(String friendID) {
        this.friendID = friendID;
    }
}