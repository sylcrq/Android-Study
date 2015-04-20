package com.example.syl.androidcleanarchdemo.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.syl.androidcleanarchdemo.R;
import com.example.syl.androidcleanarchdemo.activity.UserDetailActivity;
import com.example.syl.androidcleanarchdemo.data.User;
import com.example.syl.androidcleanarchdemo.data.UserListData;


/**
 * A placeholder fragment containing a simple view.
 */
public class UserDetailActivityFragment extends Fragment {

    public static final String TAG = "UserDetailActivityFrag";

    private TextView mNameTv;
    private TextView mEmailTv;
    private TextView mFollowerTv;
    private TextView mDescriptionTv;

    public UserDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mNameTv = (TextView) view.findViewById(R.id.user_name);
        mEmailTv = (TextView) view.findViewById(R.id.user_email);
        mFollowerTv = (TextView) view.findViewById(R.id.user_followers);
        mDescriptionTv = (TextView) view.findViewById(R.id.user_description);
    }

    @Override
    public void onResume() {
        super.onResume();

        UserDetailActivity activity = (UserDetailActivity) getActivity();
        int id = activity.getUserId();

        User user = UserListData.getUserList().get(id);

        mNameTv.setText(user.getName());
        mEmailTv.setText(user.getEmail());
        mFollowerTv.setText(user.getFollowers());
        mDescriptionTv.setText(user.getDescription());
    }
}
