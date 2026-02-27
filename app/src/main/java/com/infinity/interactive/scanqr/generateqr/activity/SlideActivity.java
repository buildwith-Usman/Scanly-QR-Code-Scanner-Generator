package com.infinity.interactive.scanqr.generateqr.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;

import com.infinity.interactive.scanqr.generateqr.R;
import com.infinity.interactive.scanqr.generateqr.adapter.ClickableViewPager;
import com.infinity.interactive.scanqr.generateqr.adapter.IntroAdapter;
import com.infinity.interactive.scanqr.generateqr.data.preference.AppPreference;
import com.infinity.interactive.scanqr.generateqr.data.preference.PrefKey;


public class SlideActivity extends AppCompatActivity {

    private final List<Integer> slideList = new ArrayList<>();
    ImageView goBackSlide;
    ImageView next;
    TextView Skipped;
    IntroAdapter slide_adapter;
    DotsIndicator view_pager_indicator;
    RelativeLayout relative_layout_slide;
    Context mContext;
    Activity mActivity;
    Intent intent;
    int actLaunch;
    private ClickableViewPager view_pager_slide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        mActivity = SlideActivity.this;
        mContext = mActivity.getApplicationContext();


        slideList.add(1);
        slideList.add(2);
        slideList.add(3);


        this.Skipped = findViewById(R.id.skipeddd);
        this.goBackSlide = findViewById(R.id.goBackk);
        this.next = findViewById(R.id.next);

        this.view_pager_indicator = findViewById(R.id.dots_indicatorrr);
        this.view_pager_slide = findViewById(R.id.view_pager_slide);
        this.relative_layout_slide = findViewById(R.id.relative_layout_slide);

        slide_adapter = new IntroAdapter(SlideActivity.this, slideList);
        view_pager_slide.setAdapter(this.slide_adapter);
        view_pager_slide.setOffscreenPageLimit(1);
        //view_pager_slide.setPageTransformer(false, new CarouselEffectTransformer(IntroActivity.this)); // Set transformer

        intent = getIntent();

        if (intent != null) {
            actLaunch = intent.getIntExtra("activityLaunch", 0);
        }

        view_pager_slide.setOnItemClickListener(new ClickableViewPager.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position < 4) {
                    view_pager_slide.setCurrentItem(position + 1);
                } else {
//
//                    Intent scan = new Intent(mContext, MainActivity.class);
//                    scan.putExtra("fragmentVal", 1);
////                    AppPreference.getInstance(mContext).setBoolean(PrefKey.ActivityFirstRun, false);
////        AppPreference.getInstance(RemoveAdsActivity.this).setInteger(PrefKey.FragmentVal, 1);
//                    scan.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(scan);
//                    startActivity(new Intent(mContext, RemoveAdsActivity.class));


                }
            }
        });


        this.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (view_pager_slide.getCurrentItem() == 1) {
                    Intent scan = new Intent(mContext, DashboardActivity.class);
                    scan.putExtra("fragmentVal", 1);
//                    AppPreference.getInstance(mContext).setBoolean(PrefKey.ActivityFirstRun, false);
//        AppPreference.getInstance(RemoveAdsActivity.this).setInteger(PrefKey.FragmentVal, 1);
                    scan.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(scan);

//                    startActivity(new Intent(mContext, RemoveAdsActivity.class));
                } else if (view_pager_slide.getCurrentItem() < slideList.size()) {
//                    Log.d("CheckLoop",view_pager_slide.getCurrentItem()+"");
                    view_pager_slide.setCurrentItem(view_pager_slide.getCurrentItem() + 1);
                    return;
                }
            }
        });


        view_pager_slide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                goBackSlide.setVisibility(View.GONE);
            }

            @Override
            public void onPageSelected(int position) {

                if (position + 1 == slideList.size()) {
//                    text_view_next_done.setVisibility(View.VISIBLE);
//                    text_view_next_done.setText("DONE");
                } else {
//                    text_view_next_done.setVisibility(View.GONE);
//                    text_view_next_done.setText("Continue");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        this.Skipped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (actLaunch == 2) {
                    finish();
                } else {
                    Intent scan = new Intent(mContext, DashboardActivity.class);
                    scan.putExtra("fragmentVal", 0);
                    AppPreference.getInstance(mContext).setBoolean(PrefKey.ActivityFirstRun, false);
//        AppPreference.getInstance(RemoveAdsActivity.this).setInteger(PrefKey.FragmentVal, 1);
                    scan.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(scan);
//                    startActivity(new Intent(mContext, RemoveAdsActivity.class));
                }

            }
        });

        goBackSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (view_pager_slide.getCurrentItem() > 0) {

                    view_pager_slide.setCurrentItem(view_pager_slide.getCurrentItem() - 1);

                }

            }
        });
        this.view_pager_slide.setClipToPadding(false);
        this.view_pager_slide.setPageMargin(0);
        view_pager_indicator.setViewPager(view_pager_slide);
    }

    @Override
    public void onBackPressed() {

        if (view_pager_slide.getCurrentItem() > 0) {
            view_pager_slide.setCurrentItem(view_pager_slide.getCurrentItem() - 1);
        } else {
            finish();
        }
    }
}
