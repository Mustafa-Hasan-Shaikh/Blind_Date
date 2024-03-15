package com.dating.blinddate.Fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dating.blinddate.Adapter.CardAdapter;
import com.dating.blinddate.Adapter.UserAdapter;
import com.dating.blinddate.Model.CardModel;
import com.dating.blinddate.Model.User;
import com.dating.blinddate.R;
import com.dating.blinddate.databinding.FragmentChatsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Chats extends Fragment {

    FragmentChatsBinding binding;
    FirebaseDatabase database;

    ArrayList<User> list = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            binding = FragmentChatsBinding.inflate(inflater,container,false);

            database = FirebaseDatabase.getInstance();

            UserAdapter adapter = new UserAdapter(list,getContext());
            binding.chatRecycle.setAdapter(adapter);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            binding.chatRecycle.setLayoutManager(layoutManager);

            database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        User user = dataSnapshot.getValue(User.class);
                        //user.getUserId(dataSnapshot.getKey());
                        list.add(user);
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        //LinearLayoutManager layoutManager = new LinearLayoutManager(getContext() ,LinearLayoutManager.HORIZONTAL, false);
           // binding.carRecycle.setLayoutManager(layoutManager);

        return binding.getRoot();
    }
}