package com.example.syl.fragmentbasicdemo;

//import android.app.FragmentManager;
//import android.app.FragmentTransaction;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements HeadlineFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // single-pane
        if(findViewById(R.id.container) != null) {

            //if(savedInstanceState != null)
            //    return;

            HeadlineFragment fragment = HeadlineFragment.newInstance("test");

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.container, fragment).commit();
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
    public void onFragmentInteraction(int position) {

        //FragmentManager fragmentManager = getFragmentManager();
        FragmentManager fragmentManager = getSupportFragmentManager();

        if(findViewById(R.id.container) != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            ArticleFragment articleFragment = ArticleFragment.newInstance(position);

            fragmentTransaction.replace(R.id.container, articleFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else {
            ArticleFragment fragment = (ArticleFragment)fragmentManager.findFragmentById(R.id.fragment_article);
            fragment.updateArticle(position);
        }
    }
}
