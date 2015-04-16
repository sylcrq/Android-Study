package com.example.syl.butterknifedemo;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * A placeholder fragment containing a simple view.
 */
public class MyListActivityFragment extends Fragment {

    @InjectView(R.id.title)
    TextView title;

    public MyListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_list, container, false);

        ButterKnife.inject(this, view);

        title.setText("Android Studio");
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ListView listView = (ListView) view.findViewById(R.id.listView);

        MyAdapter myAdapter = new MyAdapter(getActivity());
        listView.setAdapter(myAdapter);
    }
}
