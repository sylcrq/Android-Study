package com.example.syl.testdemo;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class TestAsyncTaskActivity extends ActionBarActivity {

    private static final String TAG = "TestAsyncTaskActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_async_task);

        // 从LOG中可以看到，默认情况下，所有AsyncTask是顺序执行的
        // 所以一般情况下不会有多线程的问题
        // 具体参照源码实现
        // http://developer.android.com/reference/android/os/AsyncTask.html
        new FirstAsyncTask().execute(1);
        new FirstAsyncTask().execute(2);
        new FirstAsyncTask().execute(3);
        new FirstAsyncTask().execute(4);

        new SecondAsyncTask().execute(4);
        new SecondAsyncTask().execute(3);
        new SecondAsyncTask().execute(2);
        new SecondAsyncTask().execute(1);

        new ThirdAsyncTask().execute(3);
        new ThirdAsyncTask().execute(1);
        new ThirdAsyncTask().execute(4);
        new ThirdAsyncTask().execute(2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_async_task, menu);
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

    /**
     * 三个自定义AsyncTask
     */
    private class FirstAsyncTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... params) {
            Log.d(TAG, "FirstAsyncTask " + params[0] + " start");

            try {
                Thread.sleep(5000);  // 5s
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Log.d(TAG, "FirstAsyncTask " + params[0] + " end");
            return null;
        }
    }

    private class SecondAsyncTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... params) {
            Log.d(TAG, "SecondAsyncTask " + params[0] + " start");

            try {
                Thread.sleep(5000);  // 5s
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Log.d(TAG, "SecondAsyncTask " + params[0] + " end");
            return null;
        }
    }

    private class ThirdAsyncTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... params) {
            Log.d(TAG, "ThirdAsyncTask " + params[0] + " start");

            try {
                Thread.sleep(5000);  // 5s
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Log.d(TAG, "ThirdAsyncTask " + params[0] + " end");
            return null;
        }
    }
}
