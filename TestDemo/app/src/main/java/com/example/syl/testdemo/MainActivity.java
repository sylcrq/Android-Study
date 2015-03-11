package com.example.syl.testdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{

    private static final String TAG = "MainActivity";

    private static final String[] mDemos = new String[] {
        "AsyncTask Test",
        "ProgressBar Test"
    };

    private static final Class[] mDemoActivities = new Class[] {
        TestAsyncTaskActivity.class,
        TestProgressBarActivity.class
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyAdapter adapter = new MyAdapter(this);

        ListView listView = (ListView)findViewById(R.id.demos);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        final Intent intent = new Intent(this, mDemoActivities[position]);
        startActivity(intent);
    }

    /**
     * 自定义Adapter
     */
    private class MyAdapter extends BaseAdapter {
        private Context mContext;

        private MyAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return mDemos.length;
        }

        @Override
        public Object getItem(int position) {
            return mDemos[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(mContext);
            textView.setText(mDemos[position]);
            textView.setTextColor(Color.BLACK);

            return textView;
        }
    }
}
