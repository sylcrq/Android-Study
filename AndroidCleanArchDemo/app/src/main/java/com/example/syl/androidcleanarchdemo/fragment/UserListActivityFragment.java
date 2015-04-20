package com.example.syl.androidcleanarchdemo.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.syl.androidcleanarchdemo.R;
import com.example.syl.androidcleanarchdemo.activity.UserDetailActivity;
import com.example.syl.androidcleanarchdemo.adapter.UserAdapter;


/**
 * A placeholder fragment containing a simple view.
 */
public class UserListActivityFragment extends Fragment implements AdapterView.OnItemClickListener {

    public UserListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ListView userList = (ListView) view.findViewById(R.id.user_list);
        UserAdapter userAdapter = new UserAdapter(getActivity().getApplicationContext());

        userList.setAdapter(userAdapter);
        userList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity().getApplicationContext(), UserDetailActivity.class);
        intent.putExtra(UserDetailActivity.USER_ID, position);
        startActivity(intent);
    }

//    public interface onFragmentInteractionListener {
//        public void onFragmentInteraction(int position);
//    }
}
