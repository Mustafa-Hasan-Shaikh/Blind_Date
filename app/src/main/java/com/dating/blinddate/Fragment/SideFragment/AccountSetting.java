    package com.dating.blinddate.Fragment.SideFragment;

    import android.app.ProgressDialog;
    import android.content.Context;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.os.Bundle;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import android.util.Log;
    import android.view.KeyEvent;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.CompoundButton;
    import android.widget.Toast;

    import com.dating.blinddate.Adapter.PeopleAdapter;
    import com.dating.blinddate.Fragment.Find;
    import com.dating.blinddate.Home;
    import com.dating.blinddate.Model.User;
    import com.dating.blinddate.R;
    import com.dating.blinddate.SignIn;
    import com.google.android.gms.tasks.OnFailureListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.firebase.auth.AuthResult;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;
    import com.google.firebase.storage.FirebaseStorage;
    import com.google.firebase.storage.ListResult;
    import com.google.firebase.storage.StorageException;
    import com.google.firebase.storage.StorageReference;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.Objects;


    public class AccountSetting extends Fragment {
        DatabaseReference database;
        FirebaseUser auth;
        FirebaseAuth authforlog;
        SharedPreferences data;
        String location = "Setting";

        com.dating.blinddate.databinding.FragmentAccountSettingBinding binding;
        //-------------------------------OnCreate (Main) Method---------------------
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            binding = com.dating.blinddate.databinding.FragmentAccountSettingBinding.inflate(inflater,container,false);
            data = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
            auth = FirebaseAuth.getInstance().getCurrentUser();
            authforlog = FirebaseAuth.getInstance();
            database = FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getUid());
            database.child("PrivacySetting").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        if (snapshot.child("Private").exists()) {
                            binding.mode.setChecked(snapshot.child("Private").getValue(Boolean.class));
                        }
                        if (snapshot.child("LastSeen").exists()) {
                            binding.lastSeen.setChecked(snapshot.child("LastSeen").getValue(Boolean.class));
                        }
                        if (snapshot.child("ReadReceipt").exists()) {
                            binding.readReceipt.setChecked(snapshot.child("ReadReceipt").getValue(Boolean.class));
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            //-------------------------------Code for Setting Page---------------------

            // Change Number Listener
            binding.changeNumberButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    location = "Change number";
                    binding.header.setText(location);
                    allGone(binding.changeNumberPage);
                }
            });
            // Password Button listener
            binding.passwordButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    location = "Change password";
                    binding.header.setText(location);
                    allGone(binding.changePasswordPage);
                }
            });
            // Friends List button
            binding.FriendListButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    location = "Friends list";
                    binding.header.setText(location);
                    allGone(binding.friendsPage);
                    ArrayList<User>  details = new ArrayList<>();
                    PeopleAdapter adapter = new PeopleAdapter(details,getContext(),"Friends");
                    binding.friendList.setAdapter(adapter);

                    LinearLayoutManager manager = new LinearLayoutManager(getContext());
                    binding.friendList.setLayoutManager(manager);
                    database.child("Connection").child("Friends").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                binding.noFriend.setVisibility(View.GONE);
                                binding.friendList.setVisibility(View.VISIBLE);
                                for (DataSnapshot snapshot1 : snapshot.getChildren())
                                {
                                    User user = snapshot1.getValue(User.class);
                                    user.setUserId(snapshot1.getKey());
                                    details.add(user);
                                }
                                adapter.notifyDataSetChanged();
                        }else {
                                binding.noFriend.setVisibility(View.VISIBLE);
                                binding.friendList.setVisibility(View.GONE);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            });
            // Request Sent Button
            binding.RequestSent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    location = "Reqest Sent";
                    binding.header.setText(location);
                    allGone(binding.requestSentPage);

                    ArrayList<User>  details = new ArrayList<>();
                    PeopleAdapter adapter = new PeopleAdapter(details,getContext(),"RequestSent");
                    binding.RequestSentList.setAdapter(adapter);

                    LinearLayoutManager manager = new LinearLayoutManager(getContext());
                    binding.RequestSentList.setLayoutManager(manager);
                    database.child("Connection").child("Request").child("Send").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                binding.requestSentEmpty.setVisibility(View.GONE);
                                binding.RequestSentList.setVisibility(View.VISIBLE);
                                for (DataSnapshot snapshot1 : snapshot.getChildren())
                                {
                                    User user = snapshot1.getValue(User.class);
                                    user.setUserId(snapshot1.getKey());
                                    details.add(user);
                                }
                                adapter.notifyDataSetChanged();
                            }else {
                                binding.requestSentEmpty.setVisibility(View.VISIBLE);
                                binding.RequestSentList.setVisibility(View.GONE);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            });
            // Request Received Button
            binding.RequestReceive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    location = "Reqest received";
                    binding.header.setText(location);
                    allGone(binding.requestReceivePage);

                    ArrayList<User>  details = new ArrayList<>();
                    PeopleAdapter adapter = new PeopleAdapter(details,getContext(),"RequestReceive");

                    LinearLayoutManager manager = new LinearLayoutManager(getContext());
                    binding.requestReceivelist.setLayoutManager(manager);

                    binding.requestReceivelist.setAdapter(adapter);
                    database.child("Connection").child("Request").child("Received").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                binding.requestReceiveEmpty.setVisibility(View.GONE);
                                binding.requestReceivelist.setVisibility(View.VISIBLE);
                                for (DataSnapshot snapshot1 : snapshot.getChildren())
                                {   User user = snapshot1.getValue(User.class);
                                    user.setUserId(snapshot1.getKey());
                                    details.add(user); }
                                adapter.notifyDataSetChanged(); }else {
                                binding.requestReceiveEmpty.setVisibility(View.VISIBLE);
                                binding.requestReceivelist.setVisibility(View.GONE);}

                        } @Override public void onCancelled(@NonNull DatabaseError error) {}
                    });
                }
            });
            // Request Received Button
            binding.saved.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    location = "Saved";
                    binding.header.setText(location);
                    allGone(binding.savedPage);

                    ArrayList<User>  details = new ArrayList<>();
                    PeopleAdapter adapter = new PeopleAdapter(details,getContext(),"saved");
                    binding.savedList.setAdapter(adapter);

                    LinearLayoutManager manager = new LinearLayoutManager(getContext());
                    binding.savedList.setLayoutManager(manager);
                    database.child("Connection").child("Saved").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                binding.savedEmpty.setVisibility(View.GONE);
                                binding.savedList.setVisibility(View.VISIBLE);
                                for (DataSnapshot snapshot1 : snapshot.getChildren())
                                {
                                    User user = snapshot1.getValue(User.class);
                                    user.setUserId(snapshot1.getKey());
                                    details.add(user);
                                }
                                adapter.notifyDataSetChanged();
                            }else {
                                binding.savedEmpty.setVisibility(View.VISIBLE);
                                binding.savedList.setVisibility(View.GONE);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            });
            // Reject List Button
            binding.RejectedListbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    location = "Rejected list";
                    binding.header.setText(location);
                    allGone(binding.rejectedPage);

                    ArrayList<User>  details = new ArrayList<>();
                    PeopleAdapter adapter = new PeopleAdapter(details,getContext(),"Rejected");
                    binding.rejectedList.setAdapter(adapter);

                    LinearLayoutManager manager = new LinearLayoutManager(getContext());
                    binding.rejectedList.setLayoutManager(manager);
                    database.child("Connection").child("Rejected").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                binding.noRejected.setVisibility(View.GONE);
                                binding.rejectedList.setVisibility(View.VISIBLE);
                                for (DataSnapshot snapshot1 : snapshot.getChildren())
                                {
                                    User user = snapshot1.getValue(User.class);
                                    user.setUserId(snapshot1.getKey());
                                    details.add(user);
                                }
                                adapter.notifyDataSetChanged();
                            }
                            else {
                                binding.noRejected.setVisibility(View.VISIBLE);
                                binding.rejectedList.setVisibility(View.GONE);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            });
            // Delete Account Button
            binding.deleteAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProgressDialog progressDialog;
                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setTitle("Creating Account");
                    progressDialog.setMessage("We are creating your account");


                    String userId = FirebaseAuth.getInstance().getUid();
                                FirebaseAuth.getInstance().signInWithEmailAndPassword(auth.getEmail(),"1234567")
                                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                            @Override
                                            public void onSuccess(AuthResult authResult) {
                                                // Removing User from Auth
                                                FirebaseAuth.getInstance().getCurrentUser().delete()
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                progressDialog.show();
                                                                // User deleted from Authentication
                                                                // Deleting user from Database
                                                                if (userId != null) {
                                                                    FirebaseDatabase.getInstance().getReference().child("Users")
                                                                            .child(userId).removeValue();

                                                                    // Removing user from Storage
                                                                    StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(userId).child("profilePic");
                                                                    storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void unused) {
                                                                            progressDialog.dismiss();
                                                                            startActivity(new Intent(getContext(),SignIn.class));
                                                                        }
                                                                    });

                                                                }
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                // Handle Auth deletion failure
                                                                Toast.makeText(getContext(), "Authentication deletion failed"+e, Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                }
            });

            //-------------------------------Code for  Number Page---------------------
            binding.updateNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    database.child("Personal_details").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String Number = snapshot.getValue().toString();
                            if( snapshot.child("phoneNo").getValue().toString().equals(binding.oldNumber.getText().toString())){
                                if(snapshot.child("password").getValue().toString().equals(binding.passForNumber.getText().toString())){
                                    database.child("Personal_details").child("phoneNo").setValue(Number);
                                    Toast.makeText(getContext(), "Number Updated", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(getContext(), "incorrect Number", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });

            //-------------------------------Code for  Password Page---------------------
            binding.updatePassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    database.child("Personal_details").child("password").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String Password = snapshot.getValue().toString();
                            if(Password.equals(binding.oldPass.getText().toString())){
                                database.child("Personal_details").child("password").setValue(binding.newPass.getText().toString());

                                FirebaseAuth.getInstance().signInWithEmailAndPassword(auth.getEmail(),binding.oldPass.getText().toString())
                                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        auth.updatePassword(binding.newPass.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(getContext(), "Password changed", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                throw new RuntimeException(e);
                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                            else {
                                Toast.makeText(getContext(), "incorrect Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });

            //-------------------------------Code for Privacy Setting---------------------
            binding.mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        database.child("PrivacySetting").child("Private").setValue(true);}
                    else {
                        database.child("PrivacySetting").child("Private").setValue(false);
                    }
                }
            });
            binding.lastSeen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        database.child("PrivacySetting").child("LastSeen").setValue(true);
                    }else {
                        database.child("PrivacySetting").child("LastSeen").setValue(false);
                    }
                }
            });
            binding.readReceipt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){ database.child("PrivacySetting").child("ReadReceipt").setValue(true);
                    }else {
                        database.child("PrivacySetting").child("ReadReceipt").setValue(false);
                    }
                }
            });

            //-------------------------------Code for Friend Page---------------------

            return binding.getRoot();
        }

        //-------------------------------Code for BackPressed---------------------
        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
                view.setFocusableInTouchMode(true);
                view.requestFocus();
                view.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                            if (!location.equals("Setting")){
                            location = "Setting";
                            binding.header.setText(location);
                            allGone(binding.settingPage);

                            return true; // Event ko consume karne ke liye true return karein
                        }
                        }
                        return false;   // setting false if don't want to execute back pressed
                    }
                });
            }

            //method for allpage Visibility gone
        void allGone(View view){
            binding.settingPage.setVisibility(View.GONE);
            binding.changeNumberPage.setVisibility(View.GONE);
            binding.changePasswordPage.setVisibility(View.GONE);
            binding.friendsPage.setVisibility(View.GONE);
            binding.requestSentPage.setVisibility(View.GONE);
            binding.requestReceivePage.setVisibility(View.GONE);
            binding.rejectedPage.setVisibility(View.GONE);
            binding.savedPage.setVisibility(View.GONE);

            view.setVisibility(View.VISIBLE);
        }
    }