package com.infinity.interactive.scanqr.generateqr.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.infinity.interactive.scanqr.generateqr.R;
import com.infinity.interactive.scanqr.generateqr.data.constant.Constants;


public class QRImageActivity extends AppCompatActivity {


    ImageView resultImg, finalEditorImage, back;

    Activity activity;
    Context mContext;


    FrameLayout frameLayout;
    RelativeLayout inLineBannerAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrimage);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        initVars();
        initViews();
        initFunctionality();
        initListener();


    }


    private void initVars() {

        activity = this;
        mContext = activity.getApplicationContext();

    }

    private void initViews() {
        resultImg = findViewById(R.id.finalImage);
        finalEditorImage = findViewById(R.id.finalImageEditor);
        back = findViewById(R.id.editBackButtonFromQR);

//        nativeFrame = findViewById(R.id.fl_adplaceholder);
//         banner = (Banner) findViewById(R.id.start_up_banner);
//        nativeRel = findViewById(R.id.native_ads_relative);
        frameLayout = findViewById(R.id.bannerAdsFrame);
        inLineBannerAds = findViewById(R.id.banner_ads_relative);

    }

    private void initFunctionality() {

        if (!Constants.removeAds) {

//            AdsManagerQ.getInstance().loadFreshBannerAd(mContext, frameLayout, inLineBannerAds, AdSize.MEDIUM_RECTANGLE,getResources().getString(R.string.banner_ad_unit_id_inline));

        } else {

            inLineBannerAds.setVisibility(View.GONE);
        }


        if (Constants.finalImageEditor == 1) {
            resultImg.setVisibility(View.GONE);
            finalEditorImage.setVisibility(View.VISIBLE);
            finalEditorImage.setImageBitmap(Constants.finalBitmap);

        } else {
            finalEditorImage.setVisibility(View.GONE);
            resultImg.setVisibility(View.VISIBLE);
            resultImg.setImageBitmap(Constants.finalBitmap);
        }


    }

    private void initListener() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}