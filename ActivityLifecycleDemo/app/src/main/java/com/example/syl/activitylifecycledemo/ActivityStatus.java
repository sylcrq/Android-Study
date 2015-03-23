package com.example.syl.activitylifecycledemo;

import android.os.Handler;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by syl on 15/3/15.
 */
public class ActivityStatus {

    private static final String TAG = "ActivityStatus";

    // ArrayList & LinkedHashMap
    private static List<String> mLifecycleList = new ArrayList<>();
    private static Map<String, String> mStatus = new LinkedHashMap<>();

    // Handler
    private static Handler mHandler = new Handler();

    public static void setStatus(String activity, String status) {
        mLifecycleList.add(activity + " : " + status);

        if(mStatus.containsKey(activity))
            mStatus.remove(activity);

        mStatus.put(activity, status);
    }

    // Handler postDelayed()
    // StringBuilder
    public static void printStatus(final TextView list, final TextView status) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                StringBuilder builder = new StringBuilder();

                for(String str : mLifecycleList) {
                    builder.append(str + "\n");
                }

                list.setText(builder.toString());

                StringBuilder builder1 = new StringBuilder();

                for(String key : mStatus.keySet()) {
                    builder1.append(key + " : " + mStatus.get(key) + "\n");
                }

                status.setText(builder1.toString());
            }
        }, 500);

    }
}
