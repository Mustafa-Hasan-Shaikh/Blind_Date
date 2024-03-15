package com.dating.blinddate.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dating.blinddate.ChatBox;
import com.dating.blinddate.Model.User;
import com.dating.blinddate.R;
import com.dating.blinddate.cache.SharedPreferencesHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {
    public PeopleAdapter(ArrayList<User> list, Context context,String type) {
        this.list = list;
        this.context = context;
        this.type = type;
    }

    ArrayList<User> list;
    String type;
    Context context;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.people_list_sample, parent, false);
            return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(type.equals("Friends")) {
            User user = list.get(position);
            Picasso.get().load(user.getProfilepic()).placeholder(R.drawable.logo).into(holder.profilepic);
            holder.Name.setText(user.getUserName());
            holder.add.setVisibility(View.GONE);
            holder.remove.setVisibility(View.VISIBLE);
            holder.remove.setText("UnFriend");
            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Removing From Friend
                    FirebaseDatabase.getInstance().getReference()
                            .child("Users")
                            .child(list.get(position).getUserId())
                            .child("Connection")
                            .child("Friends")
                            .child(auth.getUid())
                            .removeValue();

                    //Removing from Own
                    FirebaseDatabase.getInstance().getReference()
                            .child("Users")
                            .child(auth.getUid())
                            .child("Connection")
                            .child("Friends")
                            .child(list.get(position).getUserId())
                            .removeValue();
                }
            });
        }
        else if (type.equals("RequestSent")) {
            User user = list.get(position);
            Picasso.get().load(user.getProfilepic()).placeholder(R.drawable.logo).into(holder.profilepic);
            holder.Name.setText(user.getUserName());
            holder.add.setVisibility(View.GONE);
            holder.remove.setText("Unsend");
            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Removing From Friend
                    FirebaseDatabase.getInstance().getReference()
                            .child("Users")
                            .child(list.get(position).getUserId())
                            .child("Connection")
                            .child("Request")
                            .child("Received")
                            .child(auth.getUid())
                            .removeValue();

                    //Removing from Own
                    FirebaseDatabase.getInstance().getReference().child("Users")
                            .child(auth.getUid())
                            .child("Connection")
                            .child("Request")
                            .child("Send")
                            .child(list.get(position).getUserId())
                            .removeValue();

                    notifyDataSetChanged();
                }
            });
        }
        else if(type.equals("RequestReceive")) {
            User user = list.get(position);
            Picasso.get().load(user.getProfilepic()).placeholder(R.drawable.logo).into(holder.profilepic);
            holder.Name.setText(user.getUserName());

            holder.add.setText("Accept");
            holder.remove.setText("Reject");
            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Removing From Friend request list
                    FirebaseDatabase.getInstance().getReference()
                            .child("Users")
                            .child(auth.getUid())
                            .child("Connection")
                            .child("Request")
                            .child("Received")
                            .child(list.get(position).getUserId())
                            .removeValue();

                    //Removing from Own request list
                    FirebaseDatabase.getInstance().getReference().child("Users")
                            .child(list.get(position).getUserId())
                            .child("Connection")
                            .child("Request")
                            .child("Send")
                            .child(auth.getUid())
                            .removeValue();

                    //Sender Details
                    Map<String, Object> dataMap = new HashMap<>();
                    dataMap.put("userName",list.get(position).getUserName());
                    if(list.get(position).getProfilepic()!=null){
                    dataMap.put("profilepic",list.get(position).getProfilepic());}

                    //Adding to own Friend list
                    FirebaseDatabase.getInstance().getReference().child("Users")
                            .child(auth.getUid())
                            .child("Connection")
                            .child("Friends")
                            .child(list.get(position).getUserId())
                            .setValue(dataMap);

                    //Updating details
                    User userdetail = new SharedPreferencesHelper(context).getUserDetails();
                    dataMap.put("userName",userdetail.getUserName());
                    if(userdetail.getProfilepic()!=null){
                        dataMap.put("profilepic",userdetail.getProfilepic());}
                    //Adding to Candidate Friends List
                    FirebaseDatabase.getInstance().getReference().child("Users")
                            .child(auth.getUid())
                            .child("Personal_details")
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Map<String, Object> myDetails = new HashMap<>();
                                            myDetails.put("userName",snapshot.child("userName").getValue().toString());
                                            if(snapshot.child("profilepic").exists()){
                                                myDetails.put("profilepic",snapshot.child("profilepic").getValue().toString());}

                                            //Adding to Sender Friend list
                                            FirebaseDatabase.getInstance().getReference().child("Users")
                                                    .child(list.get(position).getUserId())
                                                    .child("Connection")
                                                    .child("Friends")
                                                    .child(auth.getUid())
                                                    .setValue(myDetails);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                }
            });
            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Removing From Friend
                    FirebaseDatabase.getInstance().getReference()
                            .child("Users")
                            .child(auth.getUid())
                            .child("Connection")
                            .child("Request")
                            .child("Received")
                            .child(list.get(position).getUserId())
                            .removeValue();

                    //Removing from Own
                    FirebaseDatabase.getInstance().getReference().child("Users")
                            .child(list.get(position).getUserId())
                            .child("Connection")
                            .child("Request")
                            .child("Send")
                            .child(auth.getUid())
                            .removeValue();

                    Map<String, Object> dataMap = new HashMap<>();
                    dataMap.put("profilepic", String.valueOf(list.get(position).getProfilepic()));
                    dataMap.put("userName",String.valueOf(list.get(position).getUserName()));
                    dataMap.put("about",String.valueOf(list.get(position).getAbout()));

                    //Saving request
                    database.child("Users").child(auth.getUid()).child("Connection").child("Rejected")
                            .child(String.valueOf(list.get(position).getUserId()))
                            .setValue(dataMap);

                }
            });
        }
        else if(type.equals("saved")) {
            User user = list.get(position);
            Picasso.get().load(user.getProfilepic()).placeholder(R.drawable.logo).into(holder.profilepic);
            holder.Name.setText(user.getUserName());

            holder.add.setText("Add Friends");
            holder.remove.setText("Remove");
            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Removing from Own
                    FirebaseDatabase.getInstance().getReference()
                            .child("Users")
                            .child(auth.getUid())
                            .child("Connection")
                            .child("Saved")
                            .child(list.get(position).getUserId())
                            .removeValue();

                    Map<String, Object> dataMap = new HashMap<>();
                    dataMap.put("profilepic", String.valueOf(list.get(position).getProfilepic()));
                    dataMap.put("userName",String.valueOf(list.get(position).getUserName()));
                    dataMap.put("about",String.valueOf(list.get(position).getAbout()));
                    dataMap.put("requestType","Like");

                    //Saving request
                    database.child("Users").child(auth.getUid()).child("Connection").child("Request")
                            .child("Send")
                            .child(String.valueOf(list.get(position).getUserId()))
                            .setValue(dataMap);

                    //Updating Map with current user Details
                    User user = new SharedPreferencesHelper(context).getUserDetails();
                    dataMap.put("profilepic", String.valueOf(user.getProfilepic()));
                    dataMap.put("userName",String.valueOf(user.getUserName()));
                    dataMap.put("about",String.valueOf(user.getAbout()));
                    dataMap.put("requestType","Like");

                    //sending request
                    database.child("Users").child(String.valueOf(list.get(position).getUserId()))
                            .child("Connection").child("Request")
                            .child("Received")
                            .child(auth.getUid())
                            .setValue(dataMap);
                }
            });
            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Removing from Own
                    FirebaseDatabase.getInstance().getReference()
                            .child("Users")
                            .child(auth.getUid())
                            .child("Connection")
                            .child("Saved")
                            .child(list.get(position).getUserId())
                            .removeValue();
                }
            });
        }
        else if (type.equals("Rejected")){
            User user = list.get(position);
            Picasso.get().load(user.getProfilepic()).placeholder(R.drawable.logo).into(holder.profilepic);
            holder.Name.setText(user.getUserName());

            holder.remove.setVisibility(View.GONE);
            holder.add.setVisibility(View.VISIBLE);
            holder.add.setText("Add Friends");
            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Removing from Own
                    FirebaseDatabase.getInstance().getReference()
                            .child("Users")
                            .child(auth.getUid())
                            .child("Connection")
                            .child("Rejected")
                            .child(list.get(position).getUserId())
                            .removeValue();

                    Map<String, Object> dataMap = new HashMap<>();
                    dataMap.put("profilepic", String.valueOf(list.get(position).getProfilepic()));
                    dataMap.put("userName",String.valueOf(list.get(position).getUserName()));
                    dataMap.put("about",String.valueOf(list.get(position).getAbout()));
                    dataMap.put("requestType","Like");

                    //Saving request
                    database.child("Users").child(auth.getUid()).child("Connection").child("Request")
                            .child("Send")
                            .child(String.valueOf(list.get(position).getUserId()))
                            .setValue(dataMap);

                    //Updating Map with current user Details
                    User user = new SharedPreferencesHelper(context).getUserDetails();
                    dataMap.put("profilepic", String.valueOf(user.getProfilepic()));
                    dataMap.put("userName",String.valueOf(user.getUserName()));
                    dataMap.put("about",String.valueOf(user.getAbout()));
                    dataMap.put("requestType","Like");

                    //sending request
                    database.child("Users").child(String.valueOf(list.get(position).getUserId()))
                            .child("Connection").child("Request")
                            .child("Received")
                            .child(auth.getUid())
                            .setValue(dataMap);
                }
            });

                   }
        else {

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profilepic;
        Button add,remove;
        TextView Name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profilepic = itemView.findViewById(R.id.people_dp);
            Name = itemView.findViewById(R.id.people_name);
            add = itemView.findViewById(R.id.positive);
            remove = itemView.findViewById(R.id.negitive);


        }
    }
}
