package com.dating.blinddate.Notification.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.res.ResourcesCompat;

import com.dating.blinddate.Home;
import com.dating.blinddate.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public Integer NotificationID = (int)System.currentTimeMillis();
    public static final Integer READ_CODE = 100;
    int App_Icon = R.drawable.notification;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        // Extract data from the received message
        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.profile_circlepng);
            if (message.getData().size() > 0) {
                String title = message.getData().get("type");
                String senderId = message.getData().get("senderID");
                if (!message.getData().get("userPic").isEmpty()) { bitmap = getBitmapFromURL(message.getData().get("senderPic"));}
                String userName = message.getData().get("senderName");
                String subtitle = message.getData().get("message");

        //Generating Notification on the basis of present data
            simpleNotification(senderId,bitmap, title, userName, subtitle);
        }
    }

    void simpleNotification(String senderId,Bitmap bitmap,String title,String userName,String message){

        Bitmap largIcon = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.profile_circlepng);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent intent = new Intent(getApplicationContext(), Home.class);
        intent.setAction("com.example.action.NOTIFICATION_CLICK"); // Action set karein
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("FRAGMENT_TO_LOAD", "FriendsList"); // Fragment ka tag pass karein
        intent.putExtra("senderID",senderId);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,READ_CODE,intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);


        Notification notification;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(title, "MESSAGE", NotificationManager.IMPORTANCE_HIGH);

            notification = new Notification.Builder(this)
                    .setSmallIcon(App_Icon)
                    .setLargeIcon(bitmap)
                    .setSubText(title)
                    .setContentTitle(userName)
                    .setContentText(message)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setChannelId(title)
                    .build();
            nm.createNotificationChannel(channel);
        }
        else {
            notification = new Notification.Builder(this)
                    .setSmallIcon(App_Icon)
                    .setLargeIcon(largIcon)
                    .setSubText(title)
                    .setContentTitle(userName)
                    .setContentText(message)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setOngoing(true)
                    .build();
        }

        nm.notify(NotificationID,notification);
    }

    void multiMessage(Bitmap bitmap,String title,String userName,String message){
        Drawable drawable = ResourcesCompat.getDrawable(getResources(),R.drawable.add,null);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap largIcon = bitmapDrawable.getBitmap();

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification;

        Intent intent = new Intent(getApplicationContext(), Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.InboxStyle inboxStyle = new Notification.InboxStyle().addLine("A")
                .addLine("B")
                .addLine("C")
                .addLine("D")
                .addLine("E")
                .addLine("F")
                .addLine("G")
                .addLine("H")
                .addLine("I")
                .addLine("J")
                .setBigContentTitle(title)
                .setSummaryText("Bohot Saare Messge from raman");


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(title, "MESSAGE", NotificationManager.IMPORTANCE_HIGH);

            notification = new Notification.Builder(this)
                    .setLargeIcon(largIcon)
                    .setSmallIcon(R.drawable.add)
                    .setContentText("New Update")
                    .setSubText("Check new Updates")
                    .setContentIntent(pendingIntent)
                    .setStyle(inboxStyle)
                    .setAutoCancel(false)
                    .setChannelId(title)
                    .build();
            nm.createNotificationChannel(channel);
        }
        else {
            notification = new Notification.Builder(this)
                    .setLargeIcon(largIcon)
                    .setSmallIcon(R.drawable.add)
                    .setContentText("New Update")
                    .setSubText("Check new Updates")
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(false)
                    .setOngoing(true)
                    .setStyle(inboxStyle)
                    .build();
        }

        nm.notify(NotificationID,notification);
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

