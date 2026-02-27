package com.infinity.interactive.scanqr.generateqr.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.infinity.interactive.scanqr.generateqr.data.preference.AppPreference
import com.infinity.interactive.scanqr.generateqr.data.preference.PrefKey
import com.infinity.interactive.scanqr.generateqr.utility.AppUtils
import java.util.Date

class EntryActivity : AppCompatActivity() {
    private lateinit var sharedpreferences: SharedPreferences
    var mActivity: Activity? = null
    var checkState = false
    private var mContext: Context? = null
    var myDate: Date? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        mActivity = this@EntryActivity
        mContext = (mActivity as EntryActivity).applicationContext
        super.onCreate(savedInstanceState)

//
//        LanguageAndTemplateUnlock();
//
//
//        Constants.isSelectingFile = true;
//
//
//        Constants.check = true;

//
//        AdsManagerQ adsManagerQ = AdsManagerQ.getInstance();
//
//        adsManagerQ.InterstitialAd(EntryActivity.this, getResources().getString(R.string.InterstitialOn));

//        AdsManagerQ.getInstance().rewardedAd(EntryActivity.this, getResources().getString(R.string.rewarded));

//        boolean firstRun = AppPreference.getInstance(this).getBoolean(PrefKey.ActivityFirstRun, true);
//
//        checkState = AppPreference.getInstance(this).getBoolean(PrefKey.RemoveAdsPrefs, false);
//
//
//        if (checkState) {
//
//            Constants.removeAds = true;
////            Constants.lockTemplate = 0;
//        }

//        Intent startMainActivity = new Intent(EntryActivity.this, AfterSplash.class);
//        startActivity(startMainActivity);


//        Log.d("firstRun", firstRun + " ");
//
//        if (firstRun) {
//            Log.d("firstRun", "inside this block ");
//
//            try {
//
//
//                ActivityUtils.getInstance().invokeActivity(EntryActivity.this, SlideActivity.class, true, 0, 0);
//
//            } catch (Exception ignored) {
//
//            }
//        } else {
//
//            int valFrag = AppPreference.getInstance(mContext).getInteger(PrefKey.FragmentVal);
//
//
//            Intent startMainActivity = new Intent(EntryActivity.this, DashboardActivity.class);
//            startMainActivity.putExtra("fragmentVal", valFrag);
//            startMainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(startMainActivity);
//
//
//        }
        val startMainActivity = Intent(this@EntryActivity, DashboardActivity::class.java)
        //        startMainActivity.putExtra("fragmentVal", valFrag);
        startMainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(startMainActivity)
    }

    private fun LanguageAndTemplateUnlock() {
        sharedpreferences = getSharedPreferences(
            PrefKey.APP_PREF_NAME,
            MODE_PRIVATE
        )
        if (sharedpreferences.contains(PrefKey.languageKey)) {
            AppUtils.changeLang(
                this@EntryActivity,
                AppPreference.getInstance(mContext).getString(PrefKey.languageKey)
            )
        }
        try {
            if (!checkState) {
                if (sharedpreferences.contains(PrefKey.DateTime)) {
                    myDate = Date(AppPreference.getInstance(mContext).getLong(PrefKey.DateTime))
                    val date = Date(System.currentTimeMillis()) //or simply new Date();
                    val diff = date.time - myDate!!.time
                    val seconds = diff / 1000
                    val minutes = seconds / 60
                    val hours = minutes / 60
                    val days = hours / 24
                }
            }
        } catch (ignored: Exception) {
        }
    }
}
