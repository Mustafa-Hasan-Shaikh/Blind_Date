package com.dating.blinddate.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dating.blinddate.Fragment.Find;
import com.dating.blinddate.Model.User;
import com.dating.blinddate.R;
import com.squareup.picasso.Picasso;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {


    public CardAdapter(List<User> cardlist, Context context, CardStackView cardStackView,CardStackLayoutManager stackLayoutManager) {
        this.cardlist = cardlist;
        this.context = context;
        this.cardStackView = cardStackView;
        /*this.stackLayoutManager = stackLayoutManager;*/
    }

    List<User> cardlist;
    Context context;
    CardStackView cardStackView;
    ImageView like;
    /*CardStackLayoutManager stackLayoutManager;*/
    @NonNull
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_layout_sample,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        User user = cardlist.get(position);
        Picasso.get().load(user.getProfilepic()).placeholder(R.drawable.logo).into(holder.image);
        holder.name.setText(user.getUserName());

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Rejected", Toast.LENGTH_SHORT).show();
            }
        });
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "Like", Toast.LENGTH_SHORT).show();
            }
        });
        holder.superLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Super", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image,reject,superLike,like;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.dp);
            reject = itemView.findViewById(R.id.reject);
            superLike = itemView.findViewById(R.id.superLike);
            this.like = itemView.findViewById(R.id.Cardlike);
            like = itemView.findViewById(R.id.Cardlike);
            name = itemView.findViewById(R.id.profileUserNameLayout);
        }
    }
   /* public List<CardModel> getItems() {
        return cardlist;
    }

    public void setItems(List<CardModel> items) {
        this.cardlist = items;
    }*/
}
