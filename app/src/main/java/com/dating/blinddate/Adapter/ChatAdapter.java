package com.dating.blinddate.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dating.blinddate.Model.MessageModel;
import com.dating.blinddate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {
    public ChatAdapter(ArrayList<MessageModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    ArrayList<MessageModel> list;
    Context context;
    String receivId;

    int SENDER_VIEW_TYPE = 1;

    public ChatAdapter(ArrayList<MessageModel> list, Context context, String receivId) {
        this.list = list;
        this.context = context;
        this.receivId = receivId;
    }

    int RECEIVER_VIEW_TYPE = 2;

    @Override
    public int getItemViewType(int position) {
        if(list.get(position).getMessageId().equals(FirebaseAuth.getInstance().getUid()))
        {
            return SENDER_VIEW_TYPE;
        }
        else {
            return RECEIVER_VIEW_TYPE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SENDER_VIEW_TYPE)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.sender_sample,parent,false);
            return new SenderViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.recevicer_sample,parent,false);
            return new RecieverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel messageModel = list.get(position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context).setTitle("Delete").setMessage("Are You Sure")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                String sender = FirebaseAuth.getInstance().getUid() + receivId;
                                database.getReference().child("Chats").child(sender)
                                        .child(messageModel.getMessageId())
                                        .setValue(null);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return false;
            }
        });

        if(holder.getClass() == SenderViewHolder.class)
        {
            ((SenderViewHolder)holder).senderMsg.setText(messageModel.getMessageText());
        }
        else {
            ((RecieverViewHolder)holder).receiverMsg.setText(messageModel.getMessageText());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecieverViewHolder extends RecyclerView.ViewHolder {
        TextView receiverMsg,ReceiverTime;
        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverMsg = itemView.findViewById(R.id.receiverMsg);
            ReceiverTime = itemView.findViewById(R.id.receiverTime);


        }
    }
    public class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView senderMsg,SenderTime;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMsg = itemView.findViewById(R.id.Sender_Message);
            SenderTime = itemView.findViewById(R.id.sender_Time);


        }
    }
}
