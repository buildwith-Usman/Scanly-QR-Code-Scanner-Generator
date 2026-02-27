package com.infinity.interactive.scanqr.generateqr.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.infinity.interactive.scanqr.generateqr.R;


public class HelpActivity extends AppCompatActivity {


    RelativeLayout help1, help2, help3;

    Context context;
    Activity activity;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        initVars();
        initViews();
        initFunctionality();
        initListener();


    }


    private void initVars() {
        activity = HelpActivity.this;
        context = activity.getApplicationContext();
    }

    private void initViews() {

        help1 = findViewById(R.id.help_r1);
        help2 = findViewById(R.id.help_r2);
        help3 = findViewById(R.id.help_r3);
        back=findViewById(R.id.backButton);
    }

    private void initFunctionality() {


    }

    private void initListener() {

            help1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, SupportedCodes.class);
                    startActivity(intent);
                }
            });

            help2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, TipsActivity.class);
                    startActivity(intent);
                }
            });

            help3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ScanningNotWork.class);
                    startActivity(intent);
                }
            });

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
    }


}