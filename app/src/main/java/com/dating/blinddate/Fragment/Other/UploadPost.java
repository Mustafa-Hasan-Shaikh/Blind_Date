package com.dating.blinddate.Fragment.Other;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.Manifest;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dating.blinddate.Fragment.Find;
import com.dating.blinddate.Fragment.Stroies;
import com.dating.blinddate.R;
import com.dating.blinddate.cache.SharedPreferencesHelper;
import com.dating.blinddate.databinding.FragmentUploadPostBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 */
public class UploadPost extends Fragment{
    FragmentUploadPostBinding binding;
    FirebaseDatabase database;
    Context context;
    Boolean Home;
    View find,bottomAppBar;
    DrawerLayout drawerLayout;
    FragmentManager fragmentManager;
    FirebaseStorage storage;
    FirebaseAuth auth;
    Uri sFile;


    public UploadPost(Context context, Boolean home, View find, View bottomAppBar, DrawerLayout drawerLayout, FragmentManager fragmentManager) {
        this.context = context;
        Home = home;
        this.find = find;
        this.bottomAppBar = bottomAppBar;
        this.drawerLayout = drawerLayout;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUploadPostBinding.inflate(inflater);
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.dropdown_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);

        binding.back.setOnClickListener(v -> {sideReplaceFragment(new Find());});
        binding.post.setOnClickListener(v -> {
            StorageReference storageReference = storage.getReference().child("User").child(auth.getUid()).child("Post")
                    .child(String.valueOf(System.currentTimeMillis()));
            storageReference.putFile(sFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String shareType = binding.spinner.getSelectedItem().toString();
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Map<String, Object> updates = new HashMap<>();
                            updates.put("shareTYPE",shareType);
                            updates.put("postURL",uri.toString());
                            updates.put("postText",binding.postText.getText().toString());
                            database.getReference().child("Users").child(auth.getUid()).child("Account")
                                    .child("Post").child(String.valueOf(System.currentTimeMillis()))
                                    .setValue(updates);
                        }
                    });
                }
            });
            sideReplaceFragment(new Stroies());
        });

        binding.plus.setOnClickListener(v -> {
            String text = binding.hashTag.getText().toString().trim();
            if (!text.isEmpty()) {
                Chip chip = new Chip(getContext());
                chip.setText("#" + text);
                chip.setCloseIconVisible(true);
                chip.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.flowLayout.removeView(chip);
                    }
                });
                binding.flowLayout.addView(chip);
                binding.hashTag.setText("");
            }
        });
        binding.file.setOnClickListener(v -> openFilePicker());
        // Attach event listener to EditText
        return binding.getRoot();
    }

    private void openFilePicker() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,33);
       }
    //OnActivityresult for changing Pic
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getData() != null) {
            sFile = data.getData();
            binding.yourPost.setImageURI(sFile);
        }
    }

    public void sideReplaceFragment(Fragment fragment) {
        Home = false;
        find.setVisibility(View.VISIBLE);
        bottomAppBar.setVisibility(View.VISIBLE);
        drawerLayout.closeDrawer(GravityCompat.START);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragHolder, fragment);
        fragmentTransaction.commit();
    }
}