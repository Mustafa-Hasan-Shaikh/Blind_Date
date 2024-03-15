package com.dating.blinddate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dating.blinddate.Model.CallsModel;
import com.dating.blinddate.R;

import java.util.ArrayList;

public class CallsAdapter extends RecyclerView.Adapter<CallsAdapter.ViewHolder> {


    public CallsAdapter(ArrayList<CallsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    ArrayList<CallsModel> list;
    Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.call_layout_sample,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CallsModel calls = list.get(position);
        holder.Name.setText(calls.getName());
        holder.Time.setText(calls.getTime());
        if(calls.getCallVia()=="Id555"){
            holder.callVia.setImageResource(R.drawable.out_going);
        }else {
            holder.callVia.setImageResource(R.drawable.missed_call);
        }

        if(calls.getCallType()=="Video"){
            holder.callType.setImageResource(R.drawable.video_video);
        }else {
            holder.callType.setImageResource(R.drawable.audio_call);
        }

       // Picasso.get().load(list2.getProfilepic()).placeholder(R.drawable.logo).into(holder.image);
       /* holder.Name.setText(user.getUserName());

        FirebaseDatabase.getInstance().getReference().child("Chats")
                        .child(FirebaseAuth.getInstance().getUid() + user.getUserId())
                                .orderByChild("timestamp")
                                        .limitToLast(1)
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        if(snapshot.hasChildren()){
                                                            for(DataSnapshot snapshot1 : snapshot.getChildren()){
                                                                holder.Message.setText(snapshot1.child("messageText")
                                                                        .getValue().toString());
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatBox.class);
                intent.putExtra("userId",user.getUserId());
                intent.putExtra("profilePic",user.getProfilepic());
                intent.putExtra("userName",user.getUserName());

                context.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image,callVia,callType;
        TextView Name , Time ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.people_dp);
            Name = itemView.findViewById(R.id.people_name);
            Time = itemView.findViewById(R.id.notifi_title);
            callVia = itemView.findViewById(R.id.call_via);
            callType = itemView.findViewById(R.id.call_type);


        }
    }
}
