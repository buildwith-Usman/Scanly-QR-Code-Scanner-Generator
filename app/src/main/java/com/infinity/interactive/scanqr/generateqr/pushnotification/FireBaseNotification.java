//package com.infinity.interactive.scanqr.generateqr.pushnotification;
//
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.BitmapFactory;
//import android.os.Build;
//
//import androidx.annotation.NonNull;
//import androidx.core.app.NotificationCompat;
//import androidx.localbroadcastmanager.content.LocalBroadcastManager;
//
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
//
//import com.infinity.interactive.scanqr.generateqr.R;
//import com.infinity.interactive.scanqr.generateqr.activity.EntryActivity;
//
//
//public class FireBaseNotification extends FirebaseMessagingService {
//
//    private static final String TAG = "FireBaseNotification";
//    String title;
//    String mesage;
//    PendingIntent pendingIntent;
//
//
//    @Override
//    public void onNewToken(@NonNull String s) {
//        super.onNewToken(s);
//
//    }
//
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//
//
//        // TODO(developer): Handle FCM messages here.
//
//        if (remoteMessage.getNotification() != null) {
//
//
//            title = "" + remoteMessage.getNotification().getTitle();
//
//            mesage = "" + remoteMessage.getNotification().getBody();
//            sendNotification(title, mesage, remoteMessage);
//
//        }
//    }
//
//    private void sendNotification(String title, String mesage, RemoteMessage remoteMessage) {
//
//        Intent intent = new Intent(getApplicationContext(), EntryActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
//        notificationIntent.addCategory("android.intent.category.DEFAULT");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
//
//        } else {
//
//            pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
//        }
//
//        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel mChannel = new NotificationChannel(getResources().getString(R.string.app_name),
//                    getResources().getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);
//            mChannel.enableLights(true);
//            mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
//            notificationManager.createNotificationChannel(mChannel);
//        }
//
//        NotificationCompat.Builder notificationBuilder;
//        notificationBuilder = new NotificationCompat.Builder(this, getResources().getString(R.string.app_name))
//                .setAutoCancel(true)
//                .setSmallIcon(R.drawable.app_icon_splash)
//                .setColor(getResources().getColor(R.color.white))
//                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
//                        R.drawable.app_icon_splash))
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setContentTitle(title)
//                .setContentText(mesage)
//                .setChannelId(getResources().getString(R.string.app_name))
//                .setFullScreenIntent(pendingIntent, true);
//        notificationManager.notify(1, notificationBuilder.build());
//
//
//
//    }
//
//    private void broadcastNewNotification() {
//        Intent intent = new Intent("new_notification");
//        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
//    }
//
//}