package com.infinity.interactive.scanqr.generateqr.activity;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

import com.infinity.interactive.scanqr.generateqr.R;


public class NotificationHelper {
    private static final String NOTIFICATION_CHANNEL_ID = "1001";
    private static final int notifid = 100;
    private final Context mContext;
    int val;
    PendingIntent pendingIntent;


    NotificationHelper(Context context) {
        mContext = context;
    }

    void createNotification() {

        scheduleNotification();

        Intent intent = new Intent(mContext, AfterSplash.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent resultPendingIntent;
//        PendingIntent resultPendingIntent = PendingIntent.getActivity(mContext,
//                0 /* Request code */, intent,
//                PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            resultPendingIntent = PendingIntent.getActivity(mContext,
                    0 /* Request code */, intent,
                    PendingIntent.FLAG_IMMUTABLE);
        } else {
            resultPendingIntent = PendingIntent.getActivity(mContext,
                    0 /* Request code */, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
        CharSequence message = "Scan QR codes to find Details of Products";

//
//        mBuilder.setSmallIcon(R.drawable.qr_code_logo);
        mBuilder.setContentTitle("QR code Scanner & Generator")
                .setContentText(message)
                .setSmallIcon(R.drawable.app_icon_splash)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setAutoCancel(false)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        val = getData(mContext);
        if (val < 0 || val == 0) {
            val = 100;
            saveData(mContext, val);
        }


        if (val != 0) {
            val = val + 1;
            saveData(mContext, val);
//            Log.d("notifyid", String.valueOf(val));

            if (val == 101) {
                Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(),
                        R.drawable.notifiy1);

                message = "Scan QR codes to find Details of Products\n";
                mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
                mBuilder.setSmallIcon(R.drawable.app_icon_splash);
                mBuilder.setContentTitle("QR code Scanner & Generator")
                        .setContentText(message)
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(icon))
                        .setAutoCancel(false)
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        .setContentIntent(resultPendingIntent);
            }
            if (val == 102) {
                Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(),
                        R.drawable.notify2);

                message = "Simplify details by Making Your Information \"QR Codes\"";
                mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
                mBuilder.setSmallIcon(R.drawable.app_icon_splash);
                mBuilder.setContentTitle("QR code Scanner & Generator")
                        .setContentText(message)
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(icon))

                        .setAutoCancel(false)
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        .setContentIntent(resultPendingIntent);
//                saveData(mContext,100);
            }
            if (val == 103) {
                Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(),
                        R.drawable.notifiy1);

                message = "QR Code is Unique way to pack your Information";
                mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
                mBuilder.setSmallIcon(R.drawable.app_icon_splash);
                mBuilder.setContentTitle("QR code Scanner & Generator")
                        .setContentText(message)
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(icon))

                        .setAutoCancel(false)
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        .setContentIntent(resultPendingIntent);
            }
            if (val == 104) {
                Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(),
                        R.drawable.notify2);

                message = "Create Customized QR Codes";
                mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
                mBuilder.setSmallIcon(R.drawable.app_icon_splash);
                mBuilder.setContentTitle("QR code Scanner & Generator")
                        .setContentText(message)
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(icon))

                        .setAutoCancel(false)
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        .setContentIntent(resultPendingIntent);
//                saveData(mContext,100);
            }
            if (val == 105) {
                Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(),
                        R.drawable.notifiy1);

                message = "You can find any previous QR Code record from History";
                mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
                mBuilder.setSmallIcon(R.drawable.app_icon_splash);
                mBuilder.setContentTitle("QR code Scanner & Generator")
                        .setContentText(message)
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(icon))

                        .setAutoCancel(false)
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        .setContentIntent(resultPendingIntent);
//                saveData(mContext,100);
            }
            if (val == 106) {
                Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(),
                        R.drawable.notify2);

                message = "You feedback is valuable to us. Please give Your feedback";
                mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
                mBuilder.setSmallIcon(R.drawable.app_icon_splash);
                mBuilder.setContentTitle("QR code Scanner & Generator")
                        .setContentText(message)
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(icon))

                        .setAutoCancel(false)
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        .setContentIntent(resultPendingIntent);
                val = 100;
//                saveData(mContext,100);
            }
//            if(val == 107)
//            {
//                message = "Love is a thing that is full of cares and fears.ِ";
//                mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
//                mBuilder.setSmallIcon(R.drawable.notification_icon);
//                mBuilder.setContentTitle("Love Stickers")
//                        .setContentText(message)
//                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//
//                        .setAutoCancel(false)
//                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                        .setContentIntent(resultPendingIntent);
////                saveData(mContext,100);
//            }
//            if(val == 108)
//            {
//                message = "Love is not love until love’s vulnerable.ِ";
//                mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
//                mBuilder.setSmallIcon(R.drawable.notification_icon);
//                mBuilder.setContentTitle("Love Stickers")
//                        .setContentText(message)
//                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//
//                        .setAutoCancel(false)
//                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                        .setContentIntent(resultPendingIntent);
////                saveData(mContext,100);
//            }
//            if(val == 109)
//            {
//                message = "Love is like the wind, you can’t see it but you can feel it.ِ";
//                mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
//                mBuilder.setSmallIcon(R.drawable.notification_icon);
//                mBuilder.setContentTitle("Love Stickers")
//                        .setContentText(message)
//                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//
//                        .setAutoCancel(false)
//                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                        .setContentIntent(resultPendingIntent);
////                saveData(mContext,100);
//            }
//            if(val == 110)
//            {
//                message = "Love is the magician that pulls man out of his own hat.ِ";
//                mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
//                mBuilder.setSmallIcon(R.drawable.notification_icon);
//                mBuilder.setContentTitle("Love Stickers")
//                        .setContentText(message)
//                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//
//                        .setAutoCancel(false)
//                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                        .setContentIntent(resultPendingIntent);
////                saveData(mContext,100);
//            }
//            if(val == 111)
//            {
//                message = "It is better to be hated for what you are than to be loved for what you are not.ِ";
//                mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
//                mBuilder.setSmallIcon(R.drawable.notification_icon);
//                mBuilder.setContentTitle("Love Stickers")
//                        .setContentText(message)
//                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//
//                        .setAutoCancel(false)
//                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                        .setContentIntent(resultPendingIntent);
////                saveData(mContext,100);
//            }
//            if(val == 112)
//            {
//                message = "This has been my life, I found it worth living.ِ";
//                mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
//                mBuilder.setSmallIcon(R.drawable.notification_icon);
//                mBuilder.setContentTitle("Love Stickers")
//                        .setContentText(message)
//                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//
//                        .setAutoCancel(false)
//                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                        .setContentIntent(resultPendingIntent);
////                saveData(mContext,100);
//            }
//            if(val == 113)
//            {
//                message = "We must be our own before we can be another’s.ِ";
//                mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
//                mBuilder.setSmallIcon(R.drawable.notification_icon);
//                mBuilder.setContentTitle("Love Stickers")
//                        .setContentText(message)
//                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//
//                        .setAutoCancel(false)
//                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                        .setContentIntent(resultPendingIntent);
//                val=100;
//                saveData(mContext, val);
//            }


        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }

//        Log.d("notifid", String.valueOf(notifid));

        assert mNotificationManager != null;
        mNotificationManager.notify(notifid /* Request Code */, mBuilder.build());
    }

    public void scheduleNotification() {
//        Log.d("check72479","called");
        Calendar mfiringCal = Calendar.getInstance();
        Calendar mcurrentCal = Calendar.getInstance();

        mfiringCal.set(Calendar.HOUR_OF_DAY, 19);
        mfiringCal.set(Calendar.MINUTE, 0);
        mfiringCal.set(Calendar.SECOND, 0);

        long intendedTime = mfiringCal.getTimeInMillis();
        long currentTime = mcurrentCal.getTimeInMillis();


        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

        if (intendedTime >= currentTime) {

//            Toast.makeText(mActivity, "call intended", Toast.LENGTH_SHORT).show();

//            Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
//            notificationIntent.addCategory("android.intent.category.DEFAULT");
            Intent alarmIntent = new Intent(mContext, AlarmReceiver.class);
//            pendingIntent = PendingIntent.getBroadcast(AfterSplash.this, 0, alarmIntent, 0);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                pendingIntent = PendingIntent.getBroadcast(mContext, 0, alarmIntent, PendingIntent.FLAG_IMMUTABLE);

            } else {
                pendingIntent = PendingIntent.getBroadcast(mContext, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            }
//            PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//            long futureInMillis = SystemClock. elapsedRealtime () + 5000 ;
//            assert alarmManager != null;
//            alarmManager.set(AlarmManager. ELAPSED_REALTIME_WAKEUP , mfiringCal.getTimeInMillis(), pendingIntent) ;
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, mfiringCal.getTimeInMillis()
                    , AlarmManager.INTERVAL_DAY * 2, pendingIntent);
        } else {
//            Toast.makeText(mActivity, "call current", Toast.LENGTH_SHORT).show();
//            Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
//            notificationIntent.addCategory("android.intent.category.DEFAULT");

//            PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Intent alarmIntent = new Intent(mContext, AlarmReceiver.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                pendingIntent = PendingIntent.getBroadcast(mContext, 0, alarmIntent, PendingIntent.FLAG_IMMUTABLE);

            } else {
                pendingIntent = PendingIntent.getBroadcast(mContext, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            }
//            pendingIntent = PendingIntent.getBroadcast(AfterSplash.this, 0, alarmIntent, 0);

            mfiringCal.add(Calendar.DAY_OF_MONTH, 1);
            intendedTime = mfiringCal.getTimeInMillis();

            alarmManager.setRepeating(AlarmManager.RTC, intendedTime, AlarmManager.INTERVAL_DAY * 2, pendingIntent);
        }


//
//                Calendar nFiringCal = Calendar.getInstance();
//        Calendar nCurrentCal = Calendar.getInstance();
//
//        nFiringCal.set(Calendar.HOUR_OF_DAY, 19);
//        nFiringCal.set(Calendar.MINUTE, 0);
//        nFiringCal.set(Calendar.SECOND, 0);
//
//        long intendedTime1 = nFiringCal.getTimeInMillis();
//        long currentTime1 = nCurrentCal.getTimeInMillis();
//
//        if (intendedTime1 >= currentTime1) {
//
//            Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
//            notificationIntent.addCategory("android.intent.category.DEFAULT");
//
//            PendingIntent broadcast = PendingIntent.getBroadcast(this, 101, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, nFiringCal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcast);
//        } else {
//
//            Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
//            notificationIntent.addCategory("android.intent.category.DEFAULT");
//
//            PendingIntent broadcast = PendingIntent.getBroadcast(this, 101, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//            nFiringCal.add(Calendar.DAY_OF_MONTH, 1);
//            intendedTime = nFiringCal.getTimeInMillis();
//
//            alarmManager.setRepeating(AlarmManager.RTC, intendedTime, AlarmManager.INTERVAL_DAY, broadcast);
//        }
    }

//    private Drawable resize(Drawable image) {
//        Bitmap b = ((BitmapDrawable) image).getBitmap();
//        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 50, 50, false);
//        return new BitmapDrawable(mContext.getResources(), bitmapResized);
//    }


    public void saveData(Context context, int notifid) {
        SharedPreferences sharedPref = getDefaultSharedPreferences(context);
        SharedPreferences.Editor spEditor = sharedPref.edit();
        spEditor.putInt("DATA", notifid);
        spEditor.commit();
    }

    public int getData(Context context) {
        SharedPreferences sharedPref = getDefaultSharedPreferences(context);
        int sPSavedData = sharedPref.getInt("DATA", 0);
        return sPSavedData;
    }


}
