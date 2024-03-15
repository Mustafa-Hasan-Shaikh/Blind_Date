package com.dating.blinddate.Notification.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.dating.blinddate.Home;
import com.dating.blinddate.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
        public void onMessageReceived(@NonNull RemoteMessage message) {
            super.onMessageReceived(message);

        if (message.getData().size() > 0) {
            // Extract data from the received message
            String title = message.getData().get("title");
            String subtitle = message.getData().get("body");

            // Create an intent to open your app when notification is clicked
            Intent intent = new Intent(this, Home.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

            // Build notification
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
                    .setSmallIcon(R.drawable.add)
                    .setContentTitle(title)
                    .setContentText(subtitle)
                    .setAutoCancel(true)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.add))
                    .setContentIntent(pendingIntent);

            // Display notification
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notificationBuilder.build());
        }
        }
}


