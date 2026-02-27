//package scanner.app.scan.qrcode.reader.utility;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.RatingBar;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//
//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdLoader;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdSize;
//import com.google.android.gms.ads.LoadAdError;
//import com.google.android.gms.ads.MobileAds;
//import com.google.android.gms.ads.VideoController;
//import com.google.android.gms.ads.VideoOptions;
//import com.google.android.gms.ads.admanager.AdManagerAdRequest;
//import com.google.android.gms.ads.admanager.AdManagerAdView;
//import com.google.android.gms.ads.admanager.AdManagerInterstitialAd;
//import com.google.android.gms.ads.admanager.AdManagerInterstitialAdLoadCallback;
//import com.google.android.gms.ads.nativead.NativeAd;
//import com.google.android.gms.ads.nativead.NativeAdOptions;
//import com.google.android.gms.ads.nativead.NativeAdView;
//import com.google.android.gms.ads.rewarded.RewardedAd;
//import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
//import com.google.firebase.analytics.FirebaseAnalytics;
//
//import java.util.Locale;
//import java.util.Objects;
//
//import app.stickerwhatsapp.stickermaker.AdEvents.AdController;
//import scanner.app.scan.qrcode.reader.AdEvents.AdEvent;
//import scanner.app.scan.qrcode.reader.R;
//import scanner.app.scan.qrcode.reader.data.constant.Constants;
//
//
//public class AdsManagerQ {
//
//
//    static int countForBannerLimitRequest = 5;
//
//    private static AdManagerAdRequest adRequestInterstitial;
//
//    private static AdsManagerQ singleton;
//    private static int countForBannerRequest;
//
//    public AdManagerInterstitialAd mAdManagerInterstitialAd;
//    public RewardedAd rewardedAds;
//    public NativeAd mNativeAd;
//    int countForInterstitialRequest = 0;
//    int countForInterstitialRequestLimit = 1;
//    private AdManagerAdView freshAdView;
//
//    public AdsManagerQ() {
//
//    }
//
//
//    /***
//     * returns an instance of this class. if singleton is null create an instance
//     * else return  the current instance
//     */
//    public static AdsManagerQ getInstance() {
//
//
//        if (singleton == null) {
//
//
//            adRequestInterstitial = new AdManagerAdRequest.Builder().build();
//            singleton = new AdsManagerQ();
//
//        }
//
//        return singleton;
//    }
//
//
//    public void InterstitialAd(Context context, String InterstitialID) {
//
//
//        MobileAds.initialize(
//                context,
//                status -> {
//
//
//                });
//        // Create an ad.
//
//
//        AdManagerInterstitialAd.load(context, InterstitialID, adRequestInterstitial, new AdManagerInterstitialAdLoadCallback() {
//            @Override
//            public void onAdLoaded(@NonNull AdManagerInterstitialAd interstitialAd) {
//                countForInterstitialRequest++;
//
//
//                if (countForInterstitialRequest > countForInterstitialRequestLimit) {
//                    countForInterstitialRequest = 0;
//
//                    InterstitialAd(context, InterstitialID);
//
//                }
//                mAdManagerInterstitialAd = interstitialAd;
//
//
//            }
//
//            @Override
//            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//
//
//            }
//
//        });
//    }
//
//
//    public AdManagerInterstitialAd getAd() {
//
//
//        return mAdManagerInterstitialAd;
//
//    }
//
//
//    public void rewardedAd(Context context, String id) {
//        AdRequest adRequest = new AdRequest.Builder().build();
//
//        RewardedAd.load(context, id,
//                adRequest, new RewardedAdLoadCallback() {
//                    @Override
//                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                        // Handle the error.
//
//                        rewardedAds = null;
//                    }
//
//                    @Override
//                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
//
//                        rewardedAds = rewardedAd;
//
//                    }
//                });
//
//
//    }
//
//
//    public void loadFreshBannerAd(Context context, FrameLayout view, RelativeLayout relativeLayout, AdSize adSize, String bannerAdID) {
//        // This is a one element array because it needs to be declared final
//        // TODO: you should probably load the default value from somewhere because of activity restarts
//
//
//        freshAdView = new AdManagerAdView(context);
//        freshAdView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
//        freshAdView.setAdUnitId(bannerAdID);
//        view.removeAllViews();
//        view.addView(freshAdView);
//        freshAdView.setAdSizes(adSize);
//
//
//        freshAdView.setAdListener(new AdListener() {
//            @Override
//            public void onAdOpened() {
//                super.onAdOpened();
//
//
//                // TODO: save currentBannerClick[0] somewhere, see previous TODO comment
//            }
//
//            @Override
//            public void onAdLoaded() {
//                super.onAdLoaded();
//
//                countForBannerRequest++;
//                relativeLayout.setVisibility(View.GONE);
//
//                freshAdView.setOnPaidEventListener(adValue -> {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("currency", adValue.getCurrencyCode());
//                    bundle.putString("precision", String.valueOf(adValue.getPrecisionType()));
//                    bundle.putString("valueMicros", String.valueOf(adValue.getValueMicros()));
//                    try {
//                        FirebaseAnalytics.getInstance(context).logEvent("paid_ad_impressions", bundle);
//                    } catch (Exception ignored) {
//
//                    }
//                });
//
//                AdEvent.AdAnalysisBanner(AdController.BannerAdTypes.BANNER,context);
//
//                if (countForBannerRequest > countForBannerLimitRequest) {
//                    countForBannerRequest = 0;
//
//
//                    loadFreshBannerAd(context, view, relativeLayout, adSize, bannerAdID);
//
//                }
//
//            }
//
//            @Override
//            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                super.onAdFailedToLoad(loadAdError);
//
//
//            }
//        });
//
//        if (countForBannerRequest <= countForBannerLimitRequest) {
//
//            AdRequest adRequest = new AdRequest.Builder().build();
//            freshAdView.setVisibility(View.VISIBLE);
//            freshAdView.loadAd(adRequest);
//        } else {
//            freshAdView.setVisibility(View.INVISIBLE);
//        }
//    }
//
//    public void loadInlineFreshBannerAd(Context context, FrameLayout view, RelativeLayout relativeLayout, AdSize adSize, String bannerAdID, ImageView cross) {
//        // This is a one element array because it needs to be declared final
//        // TODO: you should probably load the default value from somewhere because of activity restarts
//
//
//        freshAdView = new AdManagerAdView(context);
//        freshAdView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
//        freshAdView.setAdUnitId(bannerAdID);
//        view.removeAllViews();
//        view.addView(freshAdView);
//        freshAdView.setAdSizes(adSize);
//
//
//        freshAdView.setAdListener(new AdListener() {
//            @Override
//            public void onAdOpened() {
//                super.onAdOpened();
//
//
//                // TODO: save currentBannerClick[0] somewhere, see previous TODO comment
//            }
//
//            @Override
//            public void onAdLoaded() {
//                super.onAdLoaded();
//
//                countForBannerRequest++;
//                relativeLayout.setVisibility(View.GONE);
//                cross.setVisibility(View.VISIBLE);
//
//                freshAdView.setOnPaidEventListener(adValue -> {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("currency", adValue.getCurrencyCode());
//                    bundle.putString("precision", String.valueOf(adValue.getPrecisionType()));
//                    bundle.putString("valueMicros", String.valueOf(adValue.getValueMicros()));
//                    try {
//                        FirebaseAnalytics.getInstance(context).logEvent("paid_ad_impressions", bundle);
//                    } catch (Exception ignored) {
//
//                    }
//                });
//
//                AdEvent.AdAnalysisBanner(AdController.BannerAdTypes.MEDIUM,context);
//
//                if (countForBannerRequest > countForBannerLimitRequest) {
//                    countForBannerRequest = 0;
//
//
//                    loadFreshBannerAd(context, view, relativeLayout, adSize, bannerAdID);
//
//                }
//
//            }
//
//            @Override
//            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                super.onAdFailedToLoad(loadAdError);
//
//
//            }
//        });
//
//        if (countForBannerRequest <= countForBannerLimitRequest) {
//
//            AdRequest adRequest = new AdRequest.Builder().build();
//            freshAdView.setVisibility(View.VISIBLE);
//            freshAdView.loadAd(adRequest);
//        } else {
//            freshAdView.setVisibility(View.INVISIBLE);
//        }
//    }
//
//    private void populateNativeAdView(NativeAd nativeAd, NativeAdView adView) {
//        // Set the media view.
//        adView.setMediaView(adView.findViewById(R.id.ad_media));
//
//        // Set other ad assets.
//        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
//        adView.setBodyView(adView.findViewById(R.id.ad_body));
//        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
//        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
//        adView.setPriceView(adView.findViewById(R.id.ad_price));
//        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
//        adView.setStoreView(adView.findViewById(R.id.ad_store));
//        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
//
//        // The headline and mediaContent are guaranteed to be in every NativeAd.
//        ((TextView) Objects.requireNonNull(adView.getHeadlineView())).setText(nativeAd.getHeadline());
//        Objects.requireNonNull(adView.getMediaView()).setMediaContent(nativeAd.getMediaContent());
//
//        // These assets aren't guaranteed to be in every NativeAd, so it's important to
//        // check before trying to display them.
//        if (nativeAd.getBody() == null) {
//            Objects.requireNonNull(adView.getBodyView()).setVisibility(View.INVISIBLE);
//        } else {
//            Objects.requireNonNull(adView.getBodyView()).setVisibility(View.VISIBLE);
//            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
//        }
//
//        if (nativeAd.getCallToAction() == null) {
//            Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.INVISIBLE);
//        } else {
//            Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.VISIBLE);
//            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
//        }
//
//        if (nativeAd.getIcon() == null) {
//            Objects.requireNonNull(adView.getIconView()).setVisibility(View.GONE);
//        } else {
//            ((ImageView) Objects.requireNonNull(adView.getIconView())).setImageDrawable(
//                    nativeAd.getIcon().getDrawable());
//            adView.getIconView().setVisibility(View.VISIBLE);
//        }
//
//        if (nativeAd.getPrice() == null) {
//            Objects.requireNonNull(adView.getPriceView()).setVisibility(View.INVISIBLE);
//        } else {
//            Objects.requireNonNull(adView.getPriceView()).setVisibility(View.VISIBLE);
//            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
//        }
//
//        if (nativeAd.getStore() == null) {
//            Objects.requireNonNull(adView.getStoreView()).setVisibility(View.INVISIBLE);
//        } else {
//            Objects.requireNonNull(adView.getStoreView()).setVisibility(View.VISIBLE);
//            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
//        }
//
//        if (nativeAd.getStarRating() == null) {
//            Objects.requireNonNull(adView.getStarRatingView()).setVisibility(View.INVISIBLE);
//        } else {
//            ((RatingBar) Objects.requireNonNull(adView.getStarRatingView()))
//                    .setRating(nativeAd.getStarRating().floatValue());
//            adView.getStarRatingView().setVisibility(View.VISIBLE);
//        }
//
//        if (nativeAd.getAdvertiser() == null) {
//            Objects.requireNonNull(adView.getAdvertiserView()).setVisibility(View.INVISIBLE);
//        } else {
//            ((TextView) Objects.requireNonNull(adView.getAdvertiserView())).setText(nativeAd.getAdvertiser());
//            adView.getAdvertiserView().setVisibility(View.VISIBLE);
//        }
//
//        // This method tells the Google Mobile Ads SDK that you have finished populating your
//        // native ad view with this native ad.
//        adView.setNativeAd(nativeAd);
//
//        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
//        // have a video asset.
//        VideoController vc = Objects.requireNonNull(nativeAd.getMediaContent()).getVideoController();
//
//        // Updates the UI to say whether or not this ad has a video asset.
//        if (nativeAd.getMediaContent() != null && nativeAd.getMediaContent().hasVideoContent()) {
//
//            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
//            // VideoController will call methods on this object when events occur in the video
//            // lifecycle.
//            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
//                @Override
//                public void onVideoEnd() {
//                    // Publishers should allow native ads to complete video playback before
//                    // refreshing or replacing them with another ad in the same UI location.
//                    super.onVideoEnd();
//                }
//            });
//        }
//    }
//
//    public void showNativeAd(Activity context, String admobNativeID, FrameLayout frameLayout) {
//
//
//        AdLoader.Builder builder = new AdLoader.Builder(context, admobNativeID);
//
//        builder.forNativeAd(
//                new NativeAd.OnNativeAdLoadedListener() {
//                    // OnLoadedListener implementation.
//                    @Override
//                    public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {
//                        Log.d("NativeError", "native loaded");
//                        // If this callback occurs after the activity is destroyed, you must call
//                        // destroy and return or you may get a memory leak.
//                        boolean isDestroyed = false;
////                        refresh.setEnabled(true);
//                        isDestroyed = context.isDestroyed();
//                        if (isDestroyed || context.isFinishing() || context.isChangingConfigurations()) {
//                            nativeAd.destroy();
//                            return;
//                        }
//                        // You must call destroy on old ads when you are done with them,
//                        // otherwise you will have a memory leak.
//                        if (mNativeAd != null) {
//                            mNativeAd.destroy();
//                        }
//                        mNativeAd = nativeAd;
//
//                        NativeAdView adView =
//                                (NativeAdView) context.getLayoutInflater().inflate(R.layout.ad_unified, frameLayout, false);
//                        adView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
//                        populateNativeAdView(nativeAd, adView);
//                        frameLayout.removeAllViews();
//                        frameLayout.addView(adView);
//                        Constants.nativeLoaded = true;
//                        AdEvent.AdAnalysisBanner(AdController.BannerAdTypes.NATIVE,context);
//                    }
//                });
//
//        VideoOptions videoOptions =
//                new VideoOptions.Builder().setStartMuted(false).build();
//
//        NativeAdOptions adOptions =
//                new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
//
//        builder.withNativeAdOptions(adOptions);
//
//        AdLoader adLoader =
//                builder
//                        .withAdListener(
//                                new AdListener() {
//                                    @Override
//                                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//
//                                        String error =
//                                                String.format(
//                                                        Locale.getDefault(),
//                                                        "domain: %s, code: %d, message: %s",
//                                                        loadAdError.getDomain(),
//                                                        loadAdError.getCode(),
//                                                        loadAdError.getMessage());
//
//                                        Log.d("NativeError", error);
//                                    }
//                                })
//                        .build();
//
//        adLoader.loadAd(new AdRequest.Builder().build());
//
//
//    }
//
//    public NativeAd getmNativeAd() {
//
//
//        return mNativeAd;
//
//    }
//
//
//}