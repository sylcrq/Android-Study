package com.example.syl.testdemo;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;


public class TestProgressBarActivity extends ActionBarActivity {

    private static final String TAG = "TestProgressBarActivity";

    private Handler mHandler = new Handler();

    private ProgressBar mProgressBar;
    private int mProgressStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_progress_bar);

        Log.d(TAG, "UI Thread ID: "+Thread.currentThread().getId());

        mProgressBar = (ProgressBar)findViewById(R.id.loading);

        new Thread(new Runnable() {
            @Override
            public void run() {

                while(mProgressStatus < 100) {
                    mProgressStatus = doWork();

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "Thread "+Thread.currentThread().getId()+", handler post");
                            mProgressBar.setProgress(mProgressStatus);
                        }
                    });
                }

            }
        }).start();

    }

    /**
     * 在非UI线程中，做某些耗时的工作
     */
    private int doWork() {
        long threadId = Thread.currentThread().getId();

        Log.d(TAG, "Thread "+threadId+": doWork, "+mProgressStatus);

        try {
            Thread.sleep(1000);  // 1s
            mProgressStatus += 10;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return mProgressStatus;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_progress_bar, menu);
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
