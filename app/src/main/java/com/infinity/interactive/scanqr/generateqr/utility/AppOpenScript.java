//package scanner.app.scan.qrcode.reader.utility;
//
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.lifecycle.Lifecycle;
//import androidx.lifecycle.LifecycleObserver;
//import androidx.lifecycle.OnLifecycleEvent;
//import androidx.lifecycle.ProcessLifecycleOwner;
//
//import com.google.android.gms.ads.AdError;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.FullScreenContentCallback;
//import com.google.android.gms.ads.LoadAdError;
//import com.google.android.gms.ads.appopen.AppOpenAd;
//
//import java.util.Date;
//
//
//
//import app.stickerwhatsapp.stickermaker.AdEvents.AdController;
//import scanner.app.scan.qrcode.reader.AdEvents.AdEvent;
//import scanner.app.scan.qrcode.reader.activity.MyApp;
//import scanner.app.scan.qrcode.reader.data.constant.Constants;
//import scanner.app.scan.qrcode.reader.data.preference.AppPreference;
//import scanner.app.scan.qrcode.reader.data.preference.PrefKey;
//
//public class AppOpenScript implements LifecycleObserver, MyApp.ActivityLifecycleCallbacks {
//
//    private static final long MAX_AD_EXPIRATION_TIME = 3600000; // 1 hour
//    private static final String TAG = "AppOpenAdManager";
//    private final MyApp myApplication;
//    //    private final Context context;
//    private final AdRequest adRequest;
//    private final String[] adKeys;
//    final long mLoadTime = 0;
//    private Activity currentActivity;
//    private AppOpenAd appOpenAd = null;
//    private long loadTime = 0;
//    private int currentAdKeyIndex = 0;
//
//    public AppOpenScript(MyApp context, String[] adKeys) {
//        this.myApplication = context;
//        this.adRequest = new AdRequest.Builder().build();
//        this.adKeys = adKeys;
//        init();
//    }
//
//    private void init() {
//        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_START)
//    public void onStart() {
////        if(!Constant.removeAds) {
////            if (!isSelectingFile) {
////
////            } else if (isSelectingFile)
////                isSelectingFile = false;
////        }
//
//        boolean checkState = AppPreference.getInstance(myApplication).getBoolean(PrefKey.RemoveAdsPrefs, false);
//
//        if (!checkState) {
//
//            if (!Constants.isSelectingFile) {
//                showAdIfAvailable();
//            }
//            else
//            {
//                Constants.isSelectingFile = false;
//            }
//
//        }
//
//
//
////        Log.d(TAG, "onStart: app started");
//    }
//
//    private void showAdIfAvailable() {
//        if (appOpenAd != null && wasLoadTimeLessThanNHoursAgo(MAX_AD_EXPIRATION_TIME)) {
////            Log.d(TAG, "showAdIfAvailable: Ad is available and not expired, will show ad now");
//            FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
//                @Override
//                public void onAdDismissedFullScreenContent() {
//                    super.onAdDismissedFullScreenContent();
////                    Log.d(TAG, "onAdDismissedFullScreenContent: Ad dismissed");
//                    fetchAd();
//                }
//
//                @Override
//                public void onAdFailedToShowFullScreenContent(AdError adError) {
//                    super.onAdFailedToShowFullScreenContent(adError);
//                    Log.d("errorrrrrappopen", "" + adError);
//
////                    Log.d(TAG, "onAdFailedToShowFullScreenContent: Ad failed to show");
//                }
//
//                @Override
//                public void onAdShowedFullScreenContent() {
//                    super.onAdShowedFullScreenContent();
//                    AdEvent.AdAnalysisInterstitial(AdController.AdType.APP_OPEN,currentActivity);
////                    Log.d(TAG, "onAdShowedFullScreenContent: Ad showed");
//                    appOpenAd = null;
//                }
//            };
//            appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
//            appOpenAd.show(currentActivity);
//        } else {
////            Log.d(TAG, "showAdIfAvailable: Ad is not available or expired, will fetch new ad");
//            fetchAd();
//        }
//    }
//
//    private void fetchAd() {
//      /*  if (wasLoadTimeLessThanNHoursAgo(MAX_AD_EXPIRATION_TIME)) {
//            Log.d(TAG, "fetchAd: Ad is already loaded within last 4 hours");
//            return;
//        }*/
//        if (currentAdKeyIndex == adKeys.length) {
//            currentAdKeyIndex = 0;
////            Log.d(TAG, "fetchAd: All ad keys are exhausted, will start from the beginning");
//        }
//        Log.d("checkCurrentAdIndex",currentAdKeyIndex + " index " + adKeys.length);
////        String adUnitId=adKeys[0];
////
////        if(currentAdKeyIndex<adKeys.length){
////         String  adUnitId = adKeys[currentAdKeyIndex];
////        }
//        String  adUnitId = adKeys[currentAdKeyIndex];
////        AdLoader.Builder builder = new AdLoader.Builder(context, adUnitId);
//        AppOpenAd.load(myApplication, adUnitId, adRequest, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, new AppOpenAd.AppOpenAdLoadCallback() {
//
//            //        builder.forAppOpenAd(adRequest, new AppOpenAd.AppOpenAdLoadCallback() {
//            @Override
//            public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
//                super.onAdLoaded(appOpenAd);
//                AppOpenScript.this.appOpenAd = appOpenAd;
//                AppOpenScript.this.loadTime = (new Date()).getTime();
////                Log.d(TAG, "onAdLoaded: Ad loaded successfully"+"with index::::"+currentAdKeyIndex);
//            }
//
//            @Override
//            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                super.onAdFailedToLoad(loadAdError);
//                Log.d(TAG, "onAdFailedToLoad: Ad failed to load" + loadAdError);
//                currentAdKeyIndex++;
////                fetchAd();
//            }
//
//        });
//
//    }
//
//    private boolean wasLoadTimeLessThanNHoursAgo(long numHours) {
//        long timeDifference = System.currentTimeMillis() - mLoadTime;
//        long numMillis = numHours * 60 * 60 * 1000;
//        return timeDifference < numMillis;
//    }
//
//    @Override
//    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
//
//    }
//
//    @Override
//    public void onActivityStarted(@NonNull Activity activity) {
//        currentActivity = activity;
//
//    }
//
//    @Override
//    public void onActivityResumed(@NonNull Activity activity) {
//        currentActivity = activity;
//
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
//
//    }
//
//}