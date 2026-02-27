package com.infinity.interactive.scanqr.generateqr.activity;


import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Calendar;

import com.infinity.interactive.scanqr.generateqr.R;
import com.infinity.interactive.scanqr.generateqr.data.constant.Constants;
import com.infinity.interactive.scanqr.generateqr.utility.AppUtils;


public class AfterSplash extends AppCompatActivity {


    ImageView information, settingSplash;
    LinearLayout removeAds;
    CardView scanCard, createCard, historyCard, templateCard;
    PendingIntent pendingIntent;
    FrameLayout bannerFrame;


    RelativeLayout relativeLayout;

    ImageView tutorial, locale;
    BottomSheetDialog bottomSheetDialog;
    private Activity mActivity;
    private Context mContext;

    TextView removeAdsTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_after_splash);

        initVars();
        initViews();
        initFunctionality();
        initListeners();
        scheduleNotification();
        View currentFocus = mActivity.getCurrentFocus();
        if (currentFocus != null) {
            currentFocus.clearFocus();
        }


    }

    private void initListeners() {

        scanCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent scan = new Intent(AfterSplash.this, DashboardActivity.class);
                scan.putExtra("fragmentVal", 0);
//                AppPreference.getInstance(mContext).setBoolean(PrefKey.ActivityFirstRun, false);
//                AppPreference.getInstance(mContext).setInteger(PrefKey.FragmentVal, 0);
                scan.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(scan);

            }
        });

        createCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent create = new Intent(AfterSplash.this, DashboardActivity.class);
                create.putExtra("fragmentVal", 1);
//                AppPreference.getInstance(mContext).setInteger(PrefKey.FragmentVal, 1);
//                AppPreference.getInstance(mContext).setBoolean(PrefKey.ActivityFirstRun, false);
                create.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(create);

            }
        });

        historyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent history = new Intent(AfterSplash.this, DashboardActivity.class);
                history.putExtra("fragmentVal", 2);
//                AppPreference.getInstance(mContext).setInteger(PrefKey.FragmentVal, 3);
//                AppPreference.getInstance(mContext).setBoolean(PrefKey.ActivityFirstRun, false);
                history.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(history);
            }
        });

        templateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent template = new Intent(AfterSplash.this, DashboardActivity.class);
                template.putExtra("fragmentVal", 3);
//                AppPreference.getInstance(mContext).setInteger(PrefKey.FragmentVal, 2);
//                AppPreference.getInstance(mContext).setBoolean(PrefKey.ActivityFirstRun, false);
                template.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(template);
            }
        });

        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.showPermissionDialog(mActivity, mContext);
            }
        });

        settingSplash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String url = "https://docs.google.com/document/d/1TUGt3wOVriLg3pDxz1eBDaTewzshtDNv/edit?usp=sharing&ouid=103839944119900727008&rtpof=true&sd=true";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (Exception ignored) {

                }
            }
        });

        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AfterSplash.this, SlideActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
//                finish();
            }
        });

        locale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.LocalizeApp(mActivity, Constants.selectedIndex, true);
            }
        });

//        removeAdsTxt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(AfterSplash.this, RemoveAdsActivity.class));
//            }
//        });


    }


    private void initFunctionality() {

        final ValueAnimator anim = ValueAnimator.ofFloat(1f, 1.1f);
        anim.setDuration(1000);
        anim.addUpdateListener(animation -> {
            scanCard.setScaleX((Float) animation.getAnimatedValue());
            scanCard.setScaleY((Float) animation.getAnimatedValue());
        });
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.start();

        LoadBottomSheetDialog();


//        removeAds.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                startActivity(new Intent(AfterSplash.this, RemoveAdsActivity.class));
//
//            }
//
//
//        });


        Constants.check = false;
        Constants.templateUnlockGenerator = false;


    }


    private void initVars() {

        mActivity = AfterSplash.this;
        mContext = mActivity.getApplicationContext();

        bottomSheetDialog = new BottomSheetDialog(this);
    }


    private void initViews() {
        scanCard = findViewById(R.id.scan_card);
        createCard = findViewById(R.id.create_card);
        historyCard = findViewById(R.id.history_card);
        templateCard = findViewById(R.id.template_card);

        information = findViewById(R.id.info);
        settingSplash = findViewById(R.id.settings_after_splash);
        removeAds = findViewById(R.id.remove);


        bannerFrame = findViewById(R.id.linear_layout_adsview);

        relativeLayout = findViewById(R.id.ads_relative);

        tutorial = findViewById(R.id.tutorial);
        locale = findViewById(R.id.locale_icon);

        removeAdsTxt = findViewById(R.id.removeADS);

    }

    public void scheduleNotification() {

        Calendar mfiringCal = Calendar.getInstance();
        Calendar mcurrentCal = Calendar.getInstance();

        mfiringCal.set(Calendar.HOUR_OF_DAY, 19);
        mfiringCal.set(Calendar.MINUTE, 0);
        mfiringCal.set(Calendar.SECOND, 0);

        long intendedTime = mfiringCal.getTimeInMillis();
        long currentTime = mcurrentCal.getTimeInMillis();


        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (intendedTime >= currentTime) {


            Intent alarmIntent = new Intent(AfterSplash.this, AlarmReceiver.class);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                pendingIntent = PendingIntent.getBroadcast(AfterSplash.this, 0, alarmIntent, PendingIntent.FLAG_IMMUTABLE);

            } else {
                pendingIntent = PendingIntent.getBroadcast(AfterSplash.this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            }

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, mfiringCal.getTimeInMillis()
                    , AlarmManager.INTERVAL_DAY * 2, pendingIntent);
        } else {


            Intent alarmIntent = new Intent(AfterSplash.this, AlarmReceiver.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                pendingIntent = PendingIntent.getBroadcast(AfterSplash.this, 0, alarmIntent, PendingIntent.FLAG_IMMUTABLE);

            } else {
                pendingIntent = PendingIntent.getBroadcast(AfterSplash.this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            }


            mfiringCal.add(Calendar.DAY_OF_MONTH, 1);
            intendedTime = mfiringCal.getTimeInMillis();

            alarmManager.setRepeating(AlarmManager.RTC, intendedTime, AlarmManager.INTERVAL_DAY * 2, pendingIntent);
        }


    }


    @Override
    public void onBackPressed() {


        try {
//            FancyAlertDialog.Builder
//                    .with(AfterSplash.this)
//                    .setTitle(getResources().getString(R.string.app_name))
////                .setBackgroundColor(Color.parseColor("#303F9F"))  // for @ColorRes use setBackgroundColorRes(R.color.colorvalue)
//                    .setMessage(getResources().getString(R.string.exit))
//                    .setNegativeBtnText("Yes")
//                    .setPositiveBtnBackground(Color.parseColor("#FFA9A7A8"))  // for @ColorRes use setPositiveBtnBackgroundRes(R.color.colorvalue)
//                    .setPositiveBtnText("Cancel")
//                    .setNegativeBtnBackground(Color.parseColor("#f47961"))  // for @ColorRes use setNegativeBtnBackgroundRes(R.color.colorvalue)
//                    .setAnimation(Animation.POP)
//                    .isCancellable(true)
//                    .setBackgroundColor(Color.parseColor("#172331"))
////                .setIcon(Integer.parseInt("Exit?"),View.VISIBLE)
//                    .setIcon(R.drawable.alert, View.VISIBLE)
//                    .onPositiveClicked(dialog ->
//                            FancyAlertDialog.Builder.with(this).build().dismiss())
//                    .onNegativeClicked(dialog -> finishAffinity())
//                    .build()
//                    .show();

            if (bottomSheetDialog != null) {
                bottomSheetDialog.show();
            }


        } catch (Exception ignored) {

        }

    }

    private void LoadBottomSheetDialog() {


        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);
        bottomSheetDialog.setCancelable(false);


        FrameLayout nativeFrame = bottomSheetDialog.findViewById(R.id.fl_adplaceholder);
        TextView exitApp = bottomSheetDialog.findViewById(R.id.app_exit);
        RelativeLayout rateUsExit = bottomSheetDialog.findViewById(R.id.rateUs);


        ImageView closeImg;

        closeImg = bottomSheetDialog.findViewById(R.id.close);
        assert nativeFrame != null;
        assert rateUsExit != null;


        assert closeImg != null;
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });


        assert exitApp != null;
        exitApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });


    }
}