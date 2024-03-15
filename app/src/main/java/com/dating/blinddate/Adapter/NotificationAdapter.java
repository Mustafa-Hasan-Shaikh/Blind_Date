package com.dating.blinddate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dating.blinddate.Model.NotificationModel;
import com.dating.blinddate.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {


    public NotificationAdapter(ArrayList<NotificationModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    ArrayList<NotificationModel> list;
    Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_layout_sample,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationModel notifi = list.get(position);
        if (!notifi.getProfile().isEmpty()){
            Picasso.get().load(notifi.getProfile()).placeholder(R.drawable.profile_circle).into(holder.profile);}   holder.Name.setText(notifi.getName());
        holder.Time.setText(notifi.getTime());
        if(notifi.getIcon().equals("Like")){
            holder.icon.setImageResource(R.drawable.love);
        } else if (notifi.getIcon().equals("Super Like")) {
            holder.icon.setImageResource(R.drawable.vibes);
        } else if (notifi.getIcon().equals("Match")) {
            holder.icon.setImageResource(R.drawable.ic_launcher);
        } else if (notifi.getIcon().equals("Reported")) {
            holder.icon.setImageResource(R.drawable.person);
        }
        if(!notifi.getNotification().isEmpty()){
            holder.Notification.setText(notifi.getNotification());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profile,icon;
        TextView Name , Notification,Time ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.people_dp);
            Name = itemView.findViewById(R.id.people_name);
            Notification = itemView.findViewById(R.id.notifi_title);
            icon = itemView.findViewById(R.id.notifi_icon);
            Time = itemView.findViewById(R.id.notifi_Time);

        }
    }
}
