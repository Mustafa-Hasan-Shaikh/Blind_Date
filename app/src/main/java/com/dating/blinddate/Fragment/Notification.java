package com.dating.blinddate.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dating.blinddate.Adapter.CallsAdapter;
import com.dating.blinddate.Adapter.NotificationAdapter;
import com.dating.blinddate.Model.CallsModel;
import com.dating.blinddate.Model.NotificationModel;
import com.dating.blinddate.R;
import com.dating.blinddate.databinding.FragmentNotificationBinding;
import com.dating.blinddate.databinding.NotificationLayoutSampleBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 *
 *
 */
public class Notification extends Fragment {
    FragmentNotificationBinding binding;
    ArrayList<NotificationModel> notification_list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            binding = FragmentNotificationBinding.inflate(inflater,container,false);
            FirebaseDatabase.getInstance().getReference().child("Users")
                 .child(FirebaseAuth.getInstance().getUid())
                 .child("Notification")
                 .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                binding.noNotification.setVisibility(View.GONE);
                                binding.notificationRecycle.setVisibility(View.VISIBLE);
                                String Dp = "";

                                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                                    if(snapshot.child("profilepic").exists()){
                                        Dp =snapshot.child("profilepic").getValue().toString();
                                    }
                                    // Date aur samay ke roop mein samay-stamp ko parivartit karne ke liye SimpleDateFormat ka upayog karein
                                    notification_list.add(new NotificationModel(
                                            Dp,
                                            snapshot1.child("requestType").getValue().toString(),
                                            snapshot1.child("userName").getValue().toString(),
                                            snapshot1.child("requestType").getValue().toString(),
                                            time(snapshot1.child("time").getValue(Long.class))
                                    ));

                                }
                                NotificationAdapter adapter = new NotificationAdapter(notification_list,getContext());
                                binding.notificationRecycle.setAdapter(adapter);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                                binding.notificationRecycle.setLayoutManager(layoutManager);

                            }else {
                                binding.noNotification.setVisibility(View.VISIBLE);
                                binding.notificationRecycle.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
    String time(Long time){
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a", Locale.getDefault()); // Aap apne anusaar date aur samay ka format chunein

        // Parivartit kiye gaye samay-stamp ko chune hue format ke anusaar string mein pradarshit karein
        String formattedDate = sdf.format(date);
        return formattedDate;
    }
}