package com.infinity.interactive.scanqr.generateqr.utility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


/**
 * Created by Bezruk on 16/10/18.
 */
public class ActivityUtils {

    private static ActivityUtils sActivityUtils = null;

    public static ActivityUtils getInstance() {
        if (sActivityUtils == null) {
            sActivityUtils = new ActivityUtils();
        }
        return sActivityUtils;
    }

    public void invokeActivity(Activity activity, Class<?> tClass, boolean shouldFinish, int resultForFragment, int positionForHistoryFragm) {
        Intent intent = new Intent(activity, tClass);
        Bundle b = new Bundle();
        b.putInt("key", resultForFragment); //id
        b.putInt("position", positionForHistoryFragm);
        intent.putExtras(b);
        activity.startActivity(intent);
        if (shouldFinish) {
            activity.finish();
        }
    }



    public void restartActivity(Activity activity, Class<?> tClass, boolean shouldFinish) {
        Intent intent = new Intent(activity, tClass);
        activity.startActivity(intent);
        if (shouldFinish) {
            activity.finish();
        }
    }




}
