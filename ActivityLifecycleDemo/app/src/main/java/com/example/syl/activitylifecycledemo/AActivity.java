package com.example.syl.activitylifecycledemo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class AActivity extends ActionBarActivity {

    public static final String TAG = "Activity A";

    private TextView mMethodList;
    private TextView mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        mMethodList = (TextView)findViewById(R.id.lifecycle_list);
        mStatus = (TextView)findViewById(R.id.status);

        Log.d(TAG, getString(R.string.onCreate));

        ActivityStatus.setStatus(TAG, getString(R.string.onCreate));
        ActivityStatus.printStatus(mMethodList, mStatus);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d(TAG, getString(R.string.onRestart));

        ActivityStatus.setStatus(TAG, getString(R.string.onRestart));
        ActivityStatus.printStatus(mMethodList, mStatus);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, getString(R.string.onStart));

        ActivityStatus.setStatus(TAG, getString(R.string.onStart));
        ActivityStatus.printStatus(mMethodList, mStatus);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, getString(R.string.onResume));

        ActivityStatus.setStatus(TAG, getString(R.string.onResume));
        ActivityStatus.printStatus(mMethodList, mStatus);
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, getString(R.string.onPause));

        ActivityStatus.setStatus(TAG, getString(R.string.onPause));
        ActivityStatus.printStatus(mMethodList, mStatus);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, getString(R.string.onDestroy));

        ActivityStatus.setStatus(TAG, getString(R.string.onDestroy));
        ActivityStatus.printStatus(mMethodList, mStatus);
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, getString(R.string.onStop));

        ActivityStatus.setStatus(TAG, getString(R.string.onStop));
        ActivityStatus.printStatus(mMethodList, mStatus);
    }

    public void startActivityB(View view) {
        Intent intent = new Intent(this, BActivity.class);
        startActivity(intent);
    }

    public void startActivityC(View view) {
        Intent intent = new Intent(this, CActivity.class);
        startActivity(intent);
    }

    public void finishActivityA(View view) {
        finish();
    }


    public void startDialog(View view) {
        //DialogFragment dialogFragment = new MyDialogFragment();
        //dialogFragment.show(getFragmentManager(), "Dialog");

        Intent intent = new Intent(this, DialogActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_a, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
