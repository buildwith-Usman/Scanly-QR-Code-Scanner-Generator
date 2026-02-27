package com.infinity.interactive.scanqr.generateqr.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.infinity.interactive.scanqr.generateqr.R;
import com.infinity.interactive.scanqr.generateqr.adapter.SupportedAdapter;
import com.infinity.interactive.scanqr.generateqr.data.constant.SupportedModelClass;


public class GenerateSupported extends Fragment {

    Activity mActivity;
    Context mContext;
    List<SupportedModelClass> supportedModelClassList;
    SupportedAdapter supportedAdapter;
    RecyclerView recyclerView;


    public GenerateSupported() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVars();
    }

    private void initVars() {
        mActivity = getActivity();
        mContext = mActivity.getApplicationContext();
        supportedModelClassList = new ArrayList<>();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.scan_supported, container, false);
        initView(rootView);
        initFunctionality();
        return rootView;

    }

    private void initFunctionality() {

        initSupportedModelClassList();
        Collections.shuffle(supportedModelClassList);

        supportedAdapter = new SupportedAdapter(mContext, supportedModelClassList);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        recyclerView.setAdapter(supportedAdapter);
        recyclerView.setHasFixedSize(true);
        supportedAdapter.notifyDataSetChanged();

    }

    private void initSupportedModelClassList() {
        supportedModelClassList.add(new SupportedModelClass("QR CODE", R.drawable.correct_2, R.drawable.qrcode));
        supportedModelClassList.add(new SupportedModelClass("CODE_39", R.drawable.correct_2, R.drawable.code_39));
        supportedModelClassList.add(new SupportedModelClass("CODE_128", R.drawable.correct_2, R.drawable.code_128));
        supportedModelClassList.add(new SupportedModelClass("PDF417", R.drawable.wrong_2, R.drawable.pdf_417));
        supportedModelClassList.add(new SupportedModelClass("CODABAR", R.drawable.correct_2, R.drawable.codabar));
        supportedModelClassList.add(new SupportedModelClass("CODE_93", R.drawable.correct_2, R.drawable.code_93));
        supportedModelClassList.add(new SupportedModelClass("EAN_8", R.drawable.correct_2, R.drawable.ean_8));
        supportedModelClassList.add(new SupportedModelClass("DataMatrix", R.drawable.wrong_2, R.drawable.data_matrix));
        supportedModelClassList.add(new SupportedModelClass("Micro QR-Code", R.drawable.wrong_2, R.drawable.micro_qr_code));
        supportedModelClassList.add(new SupportedModelClass("EAN_13", R.drawable.correct_2, R.drawable.ean_13));
        supportedModelClassList.add(new SupportedModelClass("ITF", R.drawable.correct_2, R.drawable.itf));
        supportedModelClassList.add(new SupportedModelClass("Maxicode", R.drawable.wrong_2, R.drawable.maxicode));
        supportedModelClassList.add(new SupportedModelClass("UPC_A", R.drawable.correct_2, R.drawable.upc_a));
        supportedModelClassList.add(new SupportedModelClass("UPC_E", R.drawable.correct_2, R.drawable.upc_e));
        supportedModelClassList.add(new SupportedModelClass("Aztec", R.drawable.wrong, R.drawable.aztec));
    }

    private void initView(View rootView) {

        recyclerView = rootView.findViewById(R.id.recycler_view);

    }


}

