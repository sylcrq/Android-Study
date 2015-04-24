package com.example.syl.androidcleanarchdemo.view.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.syl.androidcleanarchdemo.R;
import com.example.syl.androidcleanarchdemo.presenter.UserDetailPresenter;
import com.example.syl.androidcleanarchdemo.view.UserDetailView;
import com.example.syl.androidcleanarchdemo.view.activity.UserDetailActivity;
import com.example.syl.androidcleanarchdemo.data.User;


/**
 * A placeholder fragment containing a simple view.
 */
public class UserDetailActivityFragment extends Fragment implements UserDetailView{

    public static final String TAG = "UserDetailActivityFrag";

    private LinearLayout mUserDetailLayout;

    private TextView mNameTv;
    private TextView mEmailTv;
    private TextView mFollowerTv;
    private TextView mDescriptionTv;

    private ProgressBar mProgressBar;
    private Button mRetryButton;

    private UserDetailPresenter mUserDetailPresenter;

    public UserDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup UI
        mProgressBar = (ProgressBar) view.findViewById(R.id.loading_progress);
        mRetryButton = (Button) view.findViewById(R.id.retry_button);

        mUserDetailLayout = (LinearLayout) view.findViewById(R.id.user_detail);

        mNameTv = (TextView) view.findViewById(R.id.user_name);
        mEmailTv = (TextView) view.findViewById(R.id.user_email);
        mFollowerTv = (TextView) view.findViewById(R.id.user_followers);
        mDescriptionTv = (TextView) view.findViewById(R.id.user_description);
    }

    @Override
    public void onResume() {
        super.onResume();

        UserDetailActivity activity = (UserDetailActivity) getActivity();
        String id = activity.getUserId();

        mUserDetailPresenter = new UserDetailPresenter(getActivity().getApplicationContext(), id);
        mUserDetailPresenter.setUserDetailView(this);

        mUserDetailPresenter.loadUserDetail();
    }

    @Override
    public void renderUserDetail(User user) {
        mNameTv.setText(user.getName());
        mEmailTv.setText(user.getEmail());
        mFollowerTv.setText(user.getFollowers());
        mDescriptionTv.setText(user.getDescription());
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showRetry() {
        mRetryButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        mRetryButton.setVisibility(View.GONE);
    }

    @Override
    public void showUserDetailLayout() {
        mUserDetailLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideUserDetailLayout() {
        mUserDetailLayout.setVisibility(View.GONE);
    }
}
