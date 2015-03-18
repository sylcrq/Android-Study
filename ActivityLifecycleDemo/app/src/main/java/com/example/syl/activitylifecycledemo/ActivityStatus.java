package com.example.syl.activitylifecycledemo;

import android.app.Activity;
import android.os.Handler;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by syl on 15/3/15.
 */
public class ActivityStatus {

    private static final String TAG = "ActivityStatus";

    private static List<String> mLifecycleList = new ArrayList<>();
    private static HashMap<String, String> mStatus = new HashMap<>();

    //private static Handler mHandler = new Handler();

    public static void setStatus(String activity, String status) {
        //mLifecycleList.add(activity + ":" + status);

        //String value = mStatus.get(activity);
        //if(value == null) {
        //    mStatus.put(activity, status);
        //}else {
        //    value = status;
        //}
        //mStatus.put(activity, status);
    }

    //public static void printStatus(Activity activity) {
    public static void printStatus(TextView list, TextView status) {
        //activity.findViewById(R.id);

//        TextView textView = (TextView)activity.findViewById(R.id.lifecycle_list);
//
//        StringBuilder builder = new StringBuilder();
//        for (int i=0; i<mLifecycleList.size(); i++) {
//            builder.append(mLifecycleList.get(i) + "\n");
//        }
//
//        textView.setText(mLifecycleList.toString());
//
//        TextView textView1 = (TextView)activity.findViewById(R.id.status);
//        StringBuilder builder1 = new StringBuilder();
//        builder1.append(mStatus.get(AActivity.TAG) + "\n");
//        builder1.append(mStatus.get(BActivity.TAG) + "\n");
//        builder1.append(mStatus.get(CActivity.TAG) + "\n");
//
//        textView1.setText(builder1.toString());
    }
}
