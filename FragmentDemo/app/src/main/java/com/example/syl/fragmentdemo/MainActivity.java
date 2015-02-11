package com.example.syl.fragmentdemo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            //getSupportFragmentManager().beginTransaction()
            //        .add(R.id.container, new PlaceholderFragment())
            //        .commit();

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container, FoodFragment.newInstance());
            fragmentTransaction.commit();
        }
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
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed");

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // 当BackStack不为空时，返回上一个fragment
        // TODO: 必须重写onBackPressed() ?? 不然没有效果
        if(fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            fragmentTransaction.commit();
        }
        else {
            super.onBackPressed();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    //public static class PlaceholderFragment extends Fragment {
    //
    //    public PlaceholderFragment() {
    //    }
    //
    //    @Override
    //    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    //                             Bundle savedInstanceState) {
    //        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
    //        return rootView;
    //    }
    //}
}
