package com.dating.blinddate.Notification.Services;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.dating.blinddate.Home;
import com.dating.blinddate.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final Integer READ_CODE = 100;
    int App_Icon = R.drawable.notification;
    Integer NotificationID;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        // Extract data from the received message
        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.profile_circlepng);
        String currentActivityName = "Blank ";
        currentActivityName = ActivityUtils.getCurrentActivityName(this);

        if (message.getData().size() > 0) {

            String title = message.getData().get("type");
            String senderId = message.getData().get("senderID");

            // if (!message.getData().get("userPic").isEmpty()) { bitmap = getBitmapFromURL(message.getData().get("senderPic"));}
            String userName = message.getData().get("senderName");
            String subtitle = message.getData().get("message");
            System.out.println(message.getData());
            System.out.println("Notification Received");
            System.out.println(Home.FragName);
            System.out.println(currentActivityName);

            //Generating Notification on the basis of present data
            if(currentActivityName.equals("com.dating.blinddate.Home")){
                if(Home.FragName.equals("Notification")){
                    System.out.println("You are on notification screen so, Notification popUp method will not be displayed");
                }
            }else {
                SimpleNotification(senderId,bitmap,title,userName,subtitle);
            }
        }

    }

    void SimpleNotification(String senderId, Bitmap bitmap, String title, String userName, String message) {
        NotificationID = (int) System.currentTimeMillis();

        Bitmap largIcon = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.profile_circlepng);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        System.out.println("noti received");
        Intent intent = new Intent(getApplicationContext(), Home.class);
        intent.setAction("com.example.action.NOTIFICATION_CLICK"); // Action set karein
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("FRAGMENT_TO_LOAD", "FriendsList"); // Fragment ka tag pass karein
        intent.putExtra("senderID", senderId);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, READ_CODE, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);


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
        } else {
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

        nm.notify(NotificationID, notification);
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
    public static class ActivityUtils{
        // Method to get the name of the current activity
        public static String getCurrentActivityName(Context context) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager != null) {
                // Get the top running activity
                ComponentName componentName = activityManager.getRunningTasks(1).get(0).topActivity;
                if (componentName != null) {
                    return componentName.getClassName(); // Return the class name of the current activity
                }
            }
            return null; // Return null if no activity is found
        }
    }


}

