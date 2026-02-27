package com.infinity.interactive.scanqr.generateqr.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import java.util.ArrayList;
import java.util.List;
import com.infinity.interactive.scanqr.generateqr.R;



/**
 * Created by hsn on 28/11/2017.
 */


public class IntroAdapter extends PagerAdapter {

    private final Context mContext;
    private List<Integer> slideList = new ArrayList<Integer>();

    TextView upgradeToPro;

    public IntroAdapter(Context mContext, List<Integer> stringList) {
        this.slideList = stringList;
        this.mContext = mContext;
    }


    @Override
    public int getCount() {
        return slideList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.item_slide_1, container, false);
        switch (slideList.get(position)) {
            case 1:
                view = layoutInflater.inflate(R.layout.item_slide_1, container, false);


                break;
            case 2:
                view = layoutInflater.inflate(R.layout.item_slide_2, container, false);


                break;
            case 3:
                view = layoutInflater.inflate(R.layout.item_slide_3, container, false);
//                upgradeToPro = view.findViewById(R.id.remove_ads);

//                upgradeToPro.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mContext.startActivity(new Intent(mContext, RemoveAdsActivity.class));
//                    }
//                });

//                view.findViewById(R.id.removeADS).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mContext.startActivity(new Intent(mContext, RemoveAdsActivity.class));
//                    }
//                });
                break;

        }


        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }


    @Override
    public float getPageWidth(int position) {
        return 1f;
    }

    @Override
    public void destroyItem(@NonNull View arg0, int arg1, @NonNull Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);

    }

    @Override
    public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
        return arg0 == arg1;

    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

}
