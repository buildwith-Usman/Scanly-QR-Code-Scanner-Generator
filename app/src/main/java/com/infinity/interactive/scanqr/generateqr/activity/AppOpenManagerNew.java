//package scanner.app.scan.qrcode.reader.activity;
//
//import static androidx.lifecycle.Lifecycle.Event.ON_START;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.lifecycle.LifecycleObserver;
//import androidx.lifecycle.OnLifecycleEvent;
//import androidx.lifecycle.ProcessLifecycleOwner;
//
//import com.google.android.gms.ads.AdError;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.FullScreenContentCallback;
//import com.google.android.gms.ads.LoadAdError;
//import com.google.android.gms.ads.appopen.AppOpenAd;
//import com.google.firebase.analytics.FirebaseAnalytics;
//
//import java.util.Date;
//
//import app.stickerwhatsapp.stickermaker.AdEvents.AdController;
//import scanner.app.scan.qrcode.reader.AdEvents.AdEvent;
//import scanner.app.scan.qrcode.reader.R;
//import scanner.app.scan.qrcode.reader.data.constant.Constants;
//import scanner.app.scan.qrcode.reader.data.preference.AppPreference;
//import scanner.app.scan.qrcode.reader.data.preference.PrefKey;
//
//
///**
// * Prefetches App Open Ads.
// */
//public class AppOpenManagerNew implements LifecycleObserver, MyApp.ActivityLifecycleCallbacks {
//
//    private static final String LOG_TAG = "AppOpenManager";
//
//    private static boolean isShowingAd = false;
//    private final MyApp myApplication;
//    private Activity currentActivity;
//    private long loadTime = 0;
//    private AppOpenAd appOpenAd = null;
//
//    /**
//     * Constructor
//     */
//    public AppOpenManagerNew(MyApp myApplication) {
//        this.myApplication = myApplication;
//        this.myApplication.registerActivityLifecycleCallbacks(this);
//        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
//
//    }
//
//    /**
//     * LifecycleObserver methods
//     */
//    @OnLifecycleEvent(ON_START)
//    public void onStart() {
//        boolean ShowAd = AppPreference.getInstance(currentActivity.getApplicationContext()).getBoolean(PrefKey.RemoveAdsPrefs, false);
//        if (!ShowAd) {
//            if (!Constants.isSelectingFile) {
//                showAdIfAvailable();
//            }
//            else
//            {
//                Constants.isSelectingFile = false;
//            }
//
////            showAdIfAvailable();
//        }
////        showAdIfAvailable();
//
//        Log.d(LOG_TAG, "onStart");
//    }
//
//    /**
//     * Shows the ad if one isn't already showing.
//     */
//    public void showAdIfAvailable() {
//        // Only show ad if there is not already an app open ad currently showing
//        // and an ad is available.
//        if (!isShowingAd && isAdAvailable()) {
//            Log.d(LOG_TAG, "Will show ad.");
//
//            FullScreenContentCallback fullScreenContentCallback =
//                    new FullScreenContentCallback() {
//                        @Override
//                        public void onAdDismissedFullScreenContent() {
//                            // Set the reference to null so isAdAvailable() returns false.
//                            AppOpenManagerNew.this.appOpenAd = null;
//                            isShowingAd = false;
//                            fetchAd();
//                        }
//
//                        @Override
//                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
//                        }
//
//                        @Override
//                        public void onAdShowedFullScreenContent() {
//
//                            isShowingAd = true;
//                            AdEvent.AdAnalysisInterstitial(AdController.AdType.APP_OPEN,currentActivity);
//                        }
//                    };
//            appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
//            appOpenAd.show(currentActivity);
//
//        } else {
//            Log.d(LOG_TAG, "Can not show ad.");
//            fetchAd();
//        }
//    }
//
//    /**
//     * Request an ad
//     */
//    public void fetchAd() {
//// Have unused ad, no need to fetch another.
//        if (isAdAvailable()) {
//            return;
//        }
//
//        // Handle the error.
//        AppOpenAd.AppOpenAdLoadCallback loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
//            /**
//             * Called when an app open ad has loaded.
//             *
//             * @param ad the loaded app open ad.
//             */
//            @Override
//            public void onAdLoaded(@NonNull AppOpenAd ad) {
//                super.onAdLoaded(ad);
//                AppOpenManagerNew.this.appOpenAd = ad;
//                AppOpenManagerNew.this.loadTime = (new Date()).getTime();
//                AppOpenManagerNew.this.appOpenAd.setOnPaidEventListener(adValue -> {
//                    try {
//                        Bundle bundle = new Bundle();
//                        bundle.putString("currency", adValue.getCurrencyCode());
//                        bundle.putString("precision", String.valueOf(adValue.getPrecisionType()));
//                        bundle.putString("valueMicros", String.valueOf(adValue.getValueMicros()));
//                        FirebaseAnalytics.getInstance(MyApp.getInstance()).logEvent("paid_ad_impressions", bundle);
//
//                    } catch (Exception ignored) {
//
//
//                    }
//
//                });
//
//            }
//
//            /**
//             * Called when an app open ad has failed to load.
//             *
//             * @param loadAdError the error.
//             */
//            @Override
//            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                super.onAdFailedToLoad(loadAdError);
//                // Handle the error.
//            }
//
//        };
//        AdRequest request = getAdRequest();
//        AppOpenAd.load(
//                myApplication, currentActivity.getResources().getString(R.string.app_open_id), request,
//                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
//    }
//
//    /**
//     * Creates and returns ad request.
//     */
//    private AdRequest getAdRequest() {
//        return new AdRequest.Builder().build();
//    }
//
//    /**
//     * Utility method to check if ad was loaded more than n hours ago.
//     */
//    private boolean wasLoadTimeLessThanNHoursAgo(long numHours) {
//        long dateDifference = (new Date()).getTime() - this.loadTime;
//        long numMilliSecondsPerHour = 3600000;
//        return (dateDifference < (numMilliSecondsPerHour * numHours));
//    }
//
//    /**
//     * Utility method that checks if ad exists and can be shown.
//     */
//    public boolean isAdAvailable() {
//        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4);
//    }
//
//    @Override
//    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
//    }
//
//    @Override
//    public void onActivityStarted(@NonNull Activity activity) {
//        currentActivity = activity;
//    }
//
//    @Override
//    public void onActivityResumed(@NonNull Activity activity) {
//        currentActivity = activity;
//    }
//
//    @Override
//    public void onActivityPaused(@NonNull Activity activity) {
//
//    }
//
//    @Override
//    public void onActivityStopped(@NonNull Activity activity) {
//
//    }
//
//    @Override
//    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
//
//    }
//
//    @Override
//    public void onActivityDestroyed(@NonNull Activity activity) {
//        currentActivity = null;
//    }
//}
