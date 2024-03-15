package com.dating.blinddate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dating.blinddate.Model.StoryModel;
import com.dating.blinddate.R;

import java.util.ArrayList;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {


    public StoryAdapter(ArrayList<StoryModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    ArrayList<StoryModel> list;
    Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.story_layout_sample,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            StoryModel storyModel = list.get(position);

            holder.profile.setImageResource(R.drawable.profile_circle);
            holder.Name.setText(storyModel.getName());
            holder.Time.setText(storyModel.getTime());
            holder.Story.setImageResource(R.drawable.post);
            holder.Like.setImageResource(R.drawable.like);
            holder.Comment.setImageResource(R.drawable.comment);
            holder.Share.setImageResource(R.drawable.sharegif);
            holder.Save.setImageResource(R.drawable.savegif);
            holder.React.setText("69 Like 48 Comments");
            holder.decription.setText("Posting my happiness");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profile,Story,Like,Comment,Share,Save;
        TextView Name , Time ,React,decription;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profile = itemView.findViewById(R.id.story_profile);
            Name = itemView.findViewById(R.id.story_Name);
            Time = itemView.findViewById(R.id.story_time);
            Story = itemView.findViewById(R.id.story_context);
            Like = itemView.findViewById(R.id.story_like);
            Comment = itemView.findViewById(R.id.story_comment);
            Share = itemView.findViewById(R.id.story_share);
            Save = itemView.findViewById(R.id.story_save);
            React = itemView.findViewById(R.id.story_react);
            decription = itemView.findViewById(R.id.story_description);


        }
    }
}
