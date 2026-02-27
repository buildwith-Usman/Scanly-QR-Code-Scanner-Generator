//package com.infinity.interactive.scanqr.generateqr.activity;
//
//import android.app.AlarmManager;
//import android.app.PendingIntent;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//
///**
// * @author Nilanchala
// *         <p/>
// *         Broadcast reciever, starts when the device gets starts.
// *         Start your repeating alarm here.
// */
//public class DeviceBootReceiver extends BroadcastReceiver {
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//
//
//        if(intent.getAction() != null) {
//            if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
//                /* Setting the alarm here */
//                Intent alarmIntent = new Intent(context, AlarmReceiver.class);
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_IMMUTABLE);
//
//                AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//                int interval = 8000;
//                manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
//
//
//            }
//        }
//
//
//    }
//
//
//}
