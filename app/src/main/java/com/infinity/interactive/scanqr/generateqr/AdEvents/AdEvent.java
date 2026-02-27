package com.infinity.interactive.scanqr.generateqr.AdEvents;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;



import app.stickerwhatsapp.stickermaker.AdEvents.AdController;

public class AdEvent {

    public static void AdAnalysisBanner(AdController.BannerAdTypes adType, Context context) {


        switch (adType) {
            case BANNER:
                AdAnalysis("ad_banner", context);

                break;

            case MEDIUM:
                AdAnalysis("ad_medium_banner", context);

                break;


            case NATIVE:
                AdAnalysis("ad_native", context);

                break;

        }

    }


    public static void AdAnalysisInterstitial(AdController.AdType adType, Context context) {

        switch (adType) {
            case APP_OPEN:
                AdAnalysis("ad_appopen", context);


                break;

            case STATIC:
                AdAnalysis("ad_static", context);


                break;


            case INTERSTITIAL:
                AdAnalysis("ad_interstitial", context);


                break;


            case REWARDED:
                AdAnalysis("ad_rewarded", context);


                break;

            case REWARDED_INTERSTITIAL:
                AdAnalysis("ad_rewarded_interstitial", context);

                break;

            case NO_AD:
                AdAnalysis("ad_no_reward", context);

                break;
        }
    }

    public static void AdAnalysis(String params, Context context) {
        //return; //C_K Stop sending Events

        Bundle bundle = new Bundle();
        bundle.putString(params, params);

        try {
            //FirebaseAnalytics.LogEvent("Ads_Analysis", // Sending Analytics to firebase
            // new Parameter("Ads", adType) // sending ads type send from Adcontroller
            // );

//            FirebaseAnalytics.getInstance(context).logEvent("Ads_Analysis", bundle); // sending ads type send from Adcontroller

        } catch (Exception e) {
            Log.d("ADSAnalysisError", e.toString());
        }


    }

}
