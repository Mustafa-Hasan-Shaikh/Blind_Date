package com.dating.blinddate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dating.blinddate.Fragment.Calls;
import com.dating.blinddate.Fragment.Chats;
import com.dating.blinddate.Fragment.Find;
import com.dating.blinddate.Fragment.Notification;
import com.dating.blinddate.Fragment.SideFragment.AboutUs;
import com.dating.blinddate.Fragment.SideFragment.AccountSetting;
import com.dating.blinddate.Fragment.SideFragment.ContactUs;
import com.dating.blinddate.Fragment.SideFragment.FriendProfile.FriendProfile;
import com.dating.blinddate.Fragment.SideFragment.MyProfile;
import com.dating.blinddate.Fragment.SideFragment.Privacy_Policy;
import com.dating.blinddate.Fragment.SideFragment.Terms_Conditions;
import com.dating.blinddate.Fragment.Stroies;
import com.dating.blinddate.Fragment.Other.UploadPost;
import com.dating.blinddate.Model.User;
import com.dating.blinddate.cache.SharedPreferencesHelper;
import com.dating.blinddate.databinding.ActivityHomeBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity {

    ActivityHomeBinding binding;
    private static boolean isInitialized = false;
    FirebaseAuth auth;
    User userDetails;
    String location = "Find";
    public static String FragName;
    FragmentManager fragmentManager = getSupportFragmentManager();


    private boolean backPressedOnce = false , Home= true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        //-------------------------------Setting Top and Bottom Bar Of System---------------------
        getWindow().setNavigationBarColor(getResources().getColor(R.color.bottomAppBar));
        getWindow().setStatusBarColor(getResources().getColor(R.color.bottomAppBar));

        auth = FirebaseAuth.getInstance();

        binding.bottomNavigationView.setBackground(null);

        //-------------------------------Ui Initialize---------------------

        // Profile initialize
       userDetails = new SharedPreferencesHelper(this).getUserDetails();
        if(userDetails.getProfilepic()!=null) {
            CircleImageView userDP = binding.navigationView.getHeaderView(0).findViewById(R.id.drawerHeaderDp);
            Picasso.get().load(userDetails.getProfilepic()).placeholder(R.drawable.logo).into(userDP);
        }
        if(userDetails.getUserName()!=null){
            TextView name = binding.navigationView.getHeaderView(0).findViewById(R.id.drawerHeaderName);
            name.setText(userDetails.getUserName());
        }
        if(auth.getCurrentUser().getEmail()!=null){
            TextView mail = binding.navigationView.getHeaderView(0).findViewById(R.id.drawerHeaderEmail);
            mail.setText(auth.getCurrentUser().getEmail());
        }

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

                if(item.getItemId()==R.id.chat) {
                    location = "Chat";
                    binding.find.setImageResource(R.drawable.plus);
                    replaceFragment(new Chats(),"Chats");
                } else if (item.getItemId()==R.id.stories) {
                    location = "Stories";
                    binding.find.setImageResource(R.drawable.post_button);
                    replaceFragment(new Stroies(),"Stroies");
                } else if (item.getItemId()==R.id.calls) {
                    location = "Calls";
                    binding.find.setImageResource(R.drawable.call);
                    replaceFragment(new Calls(),"Calls");
                } else if (item.getItemId()==R.id.notification) {
                    location = "Notification";
                    binding.find.setImageResource(R.drawable.notification);
                    replaceFragment(new Notification(),"Notification");
                } else {
                    location = "Find";
                    replaceFragment(new Find(),"Find");
                }
            return true;
        });
        binding.find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (location.equals("Chat")){ replaceFragment(new Chats(),"Chats");}
                else if (location.equals("Stories")) { sideReplaceFragment(new UploadPost(Home.this,Home,binding.find,binding.bottomAppBar,binding.drawerLayout,fragmentManager));}
                else if (location.equals("Calls")){replaceFragment(new Calls(),"Calls");}
                else if (location.equals("Notification")){replaceFragment(new Notification(),"Notification");}
                else if (location.equals("Find")){replaceFragment(new Find(),"Find");}
            }
        });

        binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.myProfile) {
                    sideReplaceFragment(new MyProfile());
                  } else if (item.getItemId() == R.id.accountSettings) {
                    sideReplaceFragment(new AccountSetting());
                } else if (item.getItemId() == R.id.contactUs) {
                    sideReplaceFragment(new ContactUs());
                } else if (item.getItemId() == R.id.aboutUs) {
                    sideReplaceFragment(new AboutUs());
                } else if (item.getItemId() == R.id.TermsConditions) {
                    sideReplaceFragment(new Terms_Conditions());
                } else if (item.getItemId() == R.id.privacyPolicy) {
                    sideReplaceFragment(new Privacy_Policy());
                } else if (item.getItemId() == R.id.shareApp) {
                    Intent myIntent = new Intent(Intent.ACTION_SEND);
                    myIntent.setType("text/plain");
                    String body = "Hi Join me on blind date APPLICATION....IT WILL EASILY AVAILABLE ON PLAY STORE ." +
                            "AND IF U WANT TO DOWNLOAD GO THROUGH LINK https://play.google.com/store/apps/details?id=com.ash.photobomb";
                    String sub = "Your Subject";
                    myIntent.putExtra(Intent.EXTRA_SUBJECT, sub);
                    myIntent.putExtra(Intent.EXTRA_TEXT, body);
                    startActivity(Intent.createChooser(myIntent, "Share Using"));
                } else if (item.getItemId() == R.id.logOut) {
                    FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getUid())
                                    .child("Personal_details").child("device_Token")
                            .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    new SharedPreferencesHelper(Home.this).logOutUser();
                                    auth.signOut();
                                    startActivity(new Intent(Home.this, SignIn.class));
                                }
                            });

                    }
                return true;
            }
        });
        isInitialized = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        replaceFragment(new Find(),"Find");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String fragmentTag = intent.getStringExtra("FRAGMENT_TO_LOAD");
         if (fragmentTag != null) {

            if (fragmentTag.equals("FriendsList")) { sideReplaceFragment(new FriendProfile(getIntent().getStringExtra("senderID"))); }
        }
    }


    //-------------------------------Method  For Replacing Fragment---------------------
    public void replaceFragment(Fragment fragment,String name) {
        Home = false;
        binding.find.setVisibility(View.VISIBLE);
        binding.bottomAppBar.setVisibility(View.VISIBLE);
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragHolder, fragment,name);
        fragmentTransaction.commit();

        FragName = name;
    }
    public void sideReplaceFragment(Fragment fragment) {
        Home = false;
        binding.find.setVisibility(View.GONE);
        binding.bottomAppBar.setVisibility(View.GONE);
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragHolder, fragment);
        fragmentTransaction.commit();
    }

    //OnBackpressed Method
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        if(Home == false){
            location = "Find";
        replaceFragment(new Find(),"Find");
            this.Home = true;
        }else {
            int count = getSupportFragmentManager().getBackStackEntryCount();
            if (count > 0) {
                super.onBackPressed();
            } else {
                if (backPressedOnce) {
                    super.onBackPressed();
                    replaceFragment(new Find(),"Find");
                    return;
                }
                backPressedOnce = true;
                showExitSnackbar();

                     // Reset backPressedOnce after 2 seconds
                     new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        backPressedOnce = false;
                    }
                }, 2000);
                }
            }
        }
    //SnackBar method for backpressed
    private void showExitSnackbar() {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Press back again to exit", Snackbar.LENGTH_SHORT);
        snackbar.setAction("Exit", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        snackbar.show();
    }
}