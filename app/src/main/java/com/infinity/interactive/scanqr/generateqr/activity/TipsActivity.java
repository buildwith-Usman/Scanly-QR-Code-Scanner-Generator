package com.infinity.interactive.scanqr.generateqr.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.infinity.interactive.scanqr.generateqr.R;
import com.infinity.interactive.scanqr.generateqr.adapter.TipsAdapter;
import com.infinity.interactive.scanqr.generateqr.data.constant.SupportedModelClass;


public class TipsActivity extends AppCompatActivity {


    Context context;
    Activity activity;
    ImageView back;
    RecyclerView recyclerView;
    TipsAdapter tipsAdapter;
    List<SupportedModelClass> supportedModelClassList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        initVars();
        initViews();
        initFunctionlaity();
        initListener();
    }


    private void initVars() {
        activity = TipsActivity.this;
        context = activity.getApplicationContext();
        supportedModelClassList = new ArrayList<>();
    }

    private void initViews() {

        back = findViewById(R.id.backButton);
        recyclerView = findViewById(R.id.recycler_view);

    }

    private void initFunctionlaity() {

        initSupportedModelClassList();

        tipsAdapter = new TipsAdapter(context, supportedModelClassList);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView.setAdapter(tipsAdapter);
        recyclerView.setHasFixedSize(true);


    }

    private void initSupportedModelClassList() {

        supportedModelClassList.add(new SupportedModelClass("Orientation 90", R.drawable.correct_2, R.drawable.code_128));
        supportedModelClassList.add(new SupportedModelClass("Orientation 0", R.drawable.correct_2, R.drawable.code_128));
        supportedModelClassList.add(new SupportedModelClass("Other Orientation", R.drawable.wrong_2, R.drawable.code_128));
        supportedModelClassList.add(new SupportedModelClass("Light or Shadow", R.drawable.wrong_2, R.drawable.light_effect));
        supportedModelClassList.add(new SupportedModelClass("Too close/blurry", R.drawable.wrong_2, R.drawable.blur_effect));
        supportedModelClassList.add(new SupportedModelClass("Led When Dark", R.drawable.correct_2, R.drawable.led_dark));
        supportedModelClassList.add(new SupportedModelClass("Low Contrast", R.drawable.wrong_2, R.drawable.low_contrast));


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