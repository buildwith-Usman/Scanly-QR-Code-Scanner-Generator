package com.infinity.interactive.scanqr.generateqr.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
//import com.android.billingclient.api.BillingClient
//import com.android.billingclient.api.BillingClientStateListener
//import com.android.billingclient.api.BillingResult
//import com.android.billingclient.api.Purchase
import com.infinity.interactive.scanqr.generateqr.R
import com.infinity.interactive.scanqr.generateqr.activity.SlideActivity
import com.infinity.interactive.scanqr.generateqr.data.constant.Constants
import com.infinity.interactive.scanqr.generateqr.data.preference.AppPreference
import com.infinity.interactive.scanqr.generateqr.data.preference.PrefKey
import com.infinity.interactive.scanqr.generateqr.utility.AppUtils

class QRSettingsFragment : Fragment(), OnTouchListener {
    var s = 0
    var app_version: TextView? = null
    var billingTag = "billingTag"
//    var billingClient: BillingClient? = null
//    var productKey = "app.qrcodegenerator.removeads"

    //    private LinearLayout personalize_ads;
    var rate_icon: ImageView? = null
    var share_icon: ImageView? = null
    var privacy_icon: ImageView? = null
    var localize_icon: ImageView? = null

    //    String productKey = "android.test.purchased";
    var mActivity: Activity? = null
    var mContext: Context? = null

    //    private SwitchButton switch_vibrate, switch_autofocus, switch_auto_url;
    private var switch_vibrate: SwitchCompat? = null
    private var switch_autofocus: SwitchCompat? = null
    private var switch_auto_url: SwitchCompat? = null
    private var rate_us: LinearLayout? = null
    private var share_app: LinearLayout? = null
    private var privacy_app: LinearLayout? = null
    private var localize_app: LinearLayout? = null
    private var restore: LinearLayout? = null
    private var intro: LinearLayout? = null
    private var appPermission: LinearLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVar()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_settings, container, false)
        initView(rootView)
        if (Constants.removeAds) {
            restore!!.visibility = View.GONE
        }
        initListener()
        return rootView
    }

    private fun initVar() {
        mActivity = requireActivity()
        mContext = (mActivity as FragmentActivity).getApplicationContext()
    }

    private fun initView(rootView: View) {
        switch_vibrate = rootView.findViewById(R.id.switch_vibrate)
        switch_autofocus = rootView.findViewById(R.id.switch_autofocus)
        switch_auto_url = rootView.findViewById(R.id.switch_auto_url)
        rate_us = rootView.findViewById(R.id.rate_us)
        share_app = rootView.findViewById(R.id.share_app)
        privacy_app = rootView.findViewById(R.id.privacy)
        localize_app = rootView.findViewById(R.id.localize_app)
        restore = rootView.findViewById(R.id.restorePurchase)
        intro = rootView.findViewById(R.id.intro)
        appPermission = rootView.findViewById(R.id.app_permission)
        rate_icon = rootView.findViewById(R.id.rate__icon)
        share_icon = rootView.findViewById(R.id.share_icon)
        privacy_icon = rootView.findViewById(R.id.privacy_icon)
        localize_icon = rootView.findViewById(R.id.locale_icon)
        switch_vibrate!!.setChecked(
            AppPreference.getInstance(mContext).getBoolean(PrefKey.SETTINGS_VIBRATE, true)
        )
        switch_autofocus!!.setChecked(
            AppPreference.getInstance(mContext).getBoolean(PrefKey.SETTINGS_AUTOFOCUS, true)
        )
        switch_auto_url!!.setChecked(
            AppPreference.getInstance(mContext).getBoolean(PrefKey.SETTINGS_AUTO_URL, false)
        )
        app_version = rootView.findViewById(R.id.app_version)
    }

//    fun checkProducts() {
//        billingClient = BillingClient.newBuilder(mContext!!).enablePendingPurchases()
//            .setListener { billingResult: BillingResult?, list: List<Purchase?>? -> }
//            .build()
//        val finalBillingClient = billingClient!!
//        billingClient!!.startConnection(object : BillingClientStateListener {
//            override fun onBillingServiceDisconnected() {}
//            override fun onBillingSetupFinished(billingResult: BillingResult) {
//                Log.d(billingTag, "Billing Setup Finished" + billingResult.responseCode)
//                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
//                    finalBillingClient.queryPurchasesAsync(BillingClient.SkuType.INAPP) { billingResult1: BillingResult?, list: List<Purchase> ->
//                        if (list.size == 0) {
//                        } else {
//                            for (purchase in list) {
//                                if (purchase.skus[0] == productKey) {
//                                    AppPreference.getInstance(mContext)
//                                        .setBoolean(PrefKey.RemoveAdsPrefs, true)
//                                    ActivityUtils.getInstance()
//                                        .restartActivity(mActivity, EntryActivity::class.java, true)
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        })
//    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListener() {

//        switch_vibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
////                Log.d("SSSSS1",String.valueOf(b));
//                AppPreference.getInstance(mContext).setBoolean(PrefKey.SETTINGS_VIBRATE, b);
//            }
//        });
        switch_vibrate!!.setOnCheckedChangeListener { buttonView, isChecked ->
            AppPreference.getInstance(
                mContext
            ).setBoolean(PrefKey.SETTINGS_VIBRATE, isChecked)
        }

//        switch_vibrate.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
//                AppPreference.getInstance(mContext).setBoolean(PrefKey.SETTINGS_VIBRATE, isChecked);
//            }
//        });

//        switch_autofocus.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
//                AppPreference.getInstance(mContext).setBoolean(PrefKey.SETTINGS_AUTOFOCUS, isChecked);
//            }
//        });
        switch_autofocus!!.setOnCheckedChangeListener { buttonView, isChecked ->
            AppPreference.getInstance(
                mContext
            ).setBoolean(PrefKey.SETTINGS_AUTOFOCUS, isChecked)
        }

//        switch_autofocus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
////                Log.d("SSSSS2",String.valueOf(b));
//                AppPreference.getInstance(mContext).setBoolean(PrefKey.SETTINGS_AUTOFOCUS, b);
//            }
//        });

//        switch_auto_url.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
//                AppPreference.getInstance(mContext).setBoolean(PrefKey.SETTINGS_AUTO_URL, isChecked);
//            }
//        });
        switch_auto_url!!.setOnCheckedChangeListener { buttonView, isChecked ->
            AppPreference.getInstance(
                mContext
            ).setBoolean(PrefKey.SETTINGS_AUTO_URL, isChecked)
        }

//        personalize_ads.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
//            @Override
//            public void onClick(View v) {
//                AppUtils.showEUPermissionDialog(mActivity,mContext);
//            }
//        });

//        switch_auto_url.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
////                Log.d("SSSSS3",String.valueOf(b));
//                AppPreference.getInstance(mContext).setBoolean(PrefKey.SETTINGS_AUTO_URL, b);
//            }
//        });
        rate_us!!.setOnTouchListener(this)
        share_app!!.setOnTouchListener(this)
        privacy_app!!.setOnTouchListener(this)
        localize_app!!.setOnTouchListener(this)
//        restore!!.setOnClickListener { checkProducts() }
        intro!!.setOnClickListener {
            val intent = Intent(mContext, SlideActivity::class.java)
            intent.putExtra("activityLaunch", 2)
            startActivity(intent)
        }
        appPermission!!.setOnClickListener { AppUtils.showPermissionDialog(mActivity, mContext) }
        try {
            val pInfo = mContext!!.packageManager.getPackageInfo(
                mContext!!.packageName, 0
            )
            val version = pInfo.versionName
            app_version!!.text = "Version $version"
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {}
            MotionEvent.ACTION_UP -> if (v.id == R.id.rate_us) {
                AppUtils.showSettingsRateDialog(mActivity)
            } else if (v.id == R.id.share_app) {
                AppUtils.shareApp(mActivity)
            } else if (v.id == R.id.privacy) {
                val url =
                    resources.getString(R.string.pp_link)
                val i = Intent(Intent.ACTION_VIEW)
                i.setData(Uri.parse(url))
                startActivity(i)
            } else if (v.id == R.id.localize_app) {
                s = AppPreference.getInstance(mContext).getInteger(PrefKey.languageKPos)
                if (s == -1) {
                    s = 0
                }
                AppUtils.LocalizeApp(mActivity, s, true)
            }
        }
        return true
    } //    @Override
    //    public void setMenuVisibility(final boolean visible) {
    //        super.setMenuVisibility(visible);
    //        if (visible) {
    //            rate_icon.setColorFilter(ContextCompat.getColor(mContext,
    //                    R.color.materialcolorpicker__lightgrey));
    //            share_icon.setColorFilter(ContextCompat.getColor(mContext,
    //                    R.color.materialcolorpicker__lightgrey));
    //        }
    //    }
}
