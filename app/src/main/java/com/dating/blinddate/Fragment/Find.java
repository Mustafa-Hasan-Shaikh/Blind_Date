package com.dating.blinddate.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.dating.blinddate.Adapter.CardAdapter;
import com.dating.blinddate.Model.User;
import com.dating.blinddate.Notification.API.RetrofitClient;
import com.dating.blinddate.Notification.API.RetrofitInterface;
import com.dating.blinddate.Notification.Model.NotificationRequest;
import com.dating.blinddate.R;
import com.dating.blinddate.cache.SharedPreferencesHelper;
import com.dating.blinddate.databinding.FragmentFindBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
*  Here is the referance for Card view Adding
*  Adding Library :- https://youtu.be/9KqSGI2W6Iw?si=0vQmUpuI4EWCI15o
*  GitHub :- CardAdapter adapter
*  Youtube :- https://www.youtube.com/watch?v=PU0Oc1KdusM&t=18s&ab_channel=JabirDeveloper
*/
public class Find extends Fragment {
    FragmentFindBinding binding;

    List<User> list = new ArrayList<>();
    FirebaseDatabase database;
    FirebaseAuth auth;

    CardAdapter adapter;
    CardStackLayoutManager stackLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         binding = FragmentFindBinding.inflate(inflater,container,false);
         database = FirebaseDatabase.getInstance();
         auth = FirebaseAuth.getInstance();
        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                   User user = dataSnapshot.child("Personal_details").getValue(User.class);
                        user.setUserId(dataSnapshot.getKey());
                    if(!user.getUserId().equals(auth.getUid())){
                    list.add(user);}
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter = new CardAdapter(list,getContext(),binding.cardStack,stackLayoutManager);

        stackLayoutManager = new CardStackLayoutManager(getContext(), new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {

               // Log.d("TAG", "onCardDragging: d=" + direction.name() + " ratio=" + ratio);
            }

            @Override
            public void onCardSwiped(Direction direction) {
              User user =  new SharedPreferencesHelper(getContext()).getUserDetails();
              //  Log.d("TAG", "onCardSwiped: p=" + stackLayoutManager.getTopPosition() + " d=" + direction);
                if (direction == Direction.Right){
                   friendDetails("Like");
                   savingRequest(friendDetails("Like"));
                   sendingRequest(userDetails("Like"));

                    Map<String, String> dataPayload = new HashMap<>();
                    dataPayload.put("type", "Notification");
                    dataPayload.put("senderID",user.getUserId());
                    dataPayload.put("senderPic", String.valueOf(user.getProfilepic()));
                    dataPayload.put("senderName",user.getUserName());
                    dataPayload.put("message", "  "+ user.getUserName() + " like your profile");

                   sendingNotification(userDetails("Like"),dataPayload);
                 }
                if (direction == Direction.Top){
                    savingRequest(friendDetails("Super Like"));
                    sendingRequest(userDetails("Super Like"));
                }
                if (direction == Direction.Left){
                    //Saving request
                    database.getReference().child("Users").child(auth.getUid()).child("Connection").child("Rejected")
                            .child(String.valueOf(list.get(stackLayoutManager.getTopPosition()-1).getUserId()))
                            .setValue(friendDetails("Rejected"));
                }
                if (direction == Direction.Bottom){

                    //Saving request
                    database.getReference().child("Users").child(auth.getUid()).child("Connection").child("Saved")
                            .child(String.valueOf(list.get(stackLayoutManager.getTopPosition()-1).getUserId()))
                            .setValue(friendDetails("Saved"));
                }

                // Paginating
                if (stackLayoutManager.getTopPosition() == adapter.getItemCount() - 5){
                 /*   paginate();*/
                }
            }
            @Override
            public void onCardRewound() {}
            @Override
            public void onCardCanceled() {}
            @Override
            public void onCardAppeared(View view, int position) {}
            @Override
            public void onCardDisappeared(View view, int position) {}

        });
        stackLayoutManager.setStackFrom(StackFrom.Bottom);
        stackLayoutManager.setVisibleCount(3);
        stackLayoutManager.setTranslationInterval(8.0f);
        stackLayoutManager.setScaleInterval(0.95f);
        stackLayoutManager.setSwipeThreshold(0.3f);
        stackLayoutManager.setMaxDegree(20.0f);
        stackLayoutManager.setDirections(Direction.FREEDOM);
        stackLayoutManager.setCanScrollHorizontal(true);
        stackLayoutManager.setSwipeableMethod(SwipeableMethod.Manual);
        stackLayoutManager.setOverlayInterpolator(new LinearInterpolator());

        binding.cardStack.setLayoutManager(stackLayoutManager);
        binding.cardStack.setAdapter(adapter);
        binding.cardStack.setItemAnimator(new DefaultItemAnimator());

        ImageView like = getActivity().findViewById(R.id.Cardlike);
        if(like!=null){
        like.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) { Toast.makeText(getContext(), "Like", Toast.LENGTH_SHORT).show();}});}

        binding.cardStack.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {}});
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
  /*  private void paginate() {
        List<CardModel> old = adapter.getItems();
        List<CardModel> baru = new ArrayList<>(list);
        CardStackCallback callback = new CardStackCallback(old, baru);
        DiffUtil.DiffResult hasil = DiffUtil.calculateDiff(callback);
        adapter.setItems(baru);
        hasil.dispatchUpdatesTo(adapter);
    }*/
/*
    public void show(){
        SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(new AccelerateInterpolator())
                .build();
        stackLayoutManager.setSwipeAnimationSetting(setting);
        binding.cardStack.swipe();
    }*/
    Map friendDetails(String requestType){
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("profilepic", String.valueOf(list.get(stackLayoutManager.getTopPosition()-1).getProfilepic()));
        dataMap.put("userName",String.valueOf(list.get(stackLayoutManager.getTopPosition()-1).getUserName()));
        dataMap.put("about",String.valueOf(list.get(stackLayoutManager.getTopPosition()-1).getAbout()));
        dataMap.put("requestType",requestType);
        dataMap.put("time", String.valueOf(ServerValue.TIMESTAMP));

        return dataMap;
    }

    Map userDetails(String requestType){
       // Updating Map with current user Details
        Map<String, Object> userDetails = new HashMap<>();
        User user = new SharedPreferencesHelper(getContext()).getUserDetails();
        userDetails.put("profilepic", String.valueOf(user.getProfilepic()));
        userDetails.put("userName",String.valueOf(user.getUserName()));
        userDetails.put("about",String.valueOf(user.getAbout()));
        userDetails.put("requestType",requestType);
        userDetails.put("time",String.valueOf(ServerValue.TIMESTAMP));

        return userDetails;
    }
    void savingRequest(Map friendsDetails){
        database.getReference().child("Users").child(auth.getUid()).child("Connection").child("Request")
                .child("Send")
                .child(String.valueOf(list.get(stackLayoutManager.getTopPosition()-1).getUserId()))
                .setValue(friendsDetails);
    }
    void sendingRequest(Map userDetails){
        database.getReference().child("Users").child(String.valueOf(list.get(stackLayoutManager.getTopPosition()-1).getUserId()))
                .child("Connection").child("Request")
                .child("Received")
                .child(auth.getUid())
                .setValue(userDetails);
    }
    void sendingNotification(Map userDetails,Map dataPayload){
        String time = String.valueOf(System.currentTimeMillis());
        String path = time + auth.getUid();
                System.out.println(path);
        //Sending Notification
        // TODO: Danger Code Review it
       /* database.getReference().child("Users").child(String.valueOf(list.get(stackLayoutManager.getTopPosition()-1).getUserId()))
                .child("Notification")
                .child(path)
                .setValue(userDetails);*/

        sendNotification(list.get(stackLayoutManager.getTopPosition()-1).getDevice_Token(), dataPayload);
    }

    public void sendNotification(String device_Token,Map dataPayload) {
        // Create a data object for custom payload

        RetrofitInterface retrofitInterface = RetrofitClient.getClient("https://fcm.googleapis.com/").create(RetrofitInterface.class);
        NotificationRequest notificationRequest = new NotificationRequest(device_Token, dataPayload);

        Call<JsonObject> call = retrofitInterface.sendNotification(notificationRequest);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                        JSONObject jsonResponse = null;
                    if (response.body() != null) {
                        JsonObject jsonObject = response.body();

                        try {
                            jsonResponse = new JSONObject(jsonObject.toString());
                            Toast.makeText(getContext(), "Notification Sent", Toast.LENGTH_SHORT).show();
                        //   System.out.println(String.valueOf(jsonResponse));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }else {
                    System.out.println("Notification not sent");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
}