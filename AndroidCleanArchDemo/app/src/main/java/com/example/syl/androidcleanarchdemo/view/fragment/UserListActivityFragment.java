package com.example.syl.androidcleanarchdemo.view.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.syl.androidcleanarchdemo.view.activity.UserDetailActivity;
import com.example.syl.androidcleanarchdemo.view.adapter.UserAdapter;
import com.example.syl.androidcleanarchdemo.data.User;
import com.example.syl.androidcleanarchdemo.presenter.UserListPresenter;
import com.example.syl.androidcleanarchdemo.R;
import com.example.syl.androidcleanarchdemo.view.UserListView;

import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class UserListActivityFragment extends Fragment implements UserListView {

    public static final String TAG = "UserListActivityFrag";

    private ListView mUserListView;
    private ProgressBar mProgressBar;
    private Button mRetryButton;

    private UserAdapter mUserAdapter;

    UserListPresenter mUserListPresenter;

    public UserListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup UI
        mUserListView = (ListView) view.findViewById(R.id.user_list);
        mProgressBar = (ProgressBar) view.findViewById(R.id.loading_progress);
        mRetryButton = (Button) view.findViewById(R.id.retry_button);

        mUserListPresenter = new UserListPresenter(getActivity().getApplicationContext());
        mUserListPresenter.setUserListView(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        mUserListPresenter.loadUserList();
    }

    @Override
    public void onPause() { super.onPause(); }

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
    public void hideRetry() { mRetryButton.setVisibility(View.GONE); }

    @Override
    public void renderUserList(List<User> userList) {
        mUserAdapter = new UserAdapter(getActivity().getApplicationContext(), userList);

        mUserListView.setAdapter(mUserAdapter);
        mUserListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d(TAG, "position="+position+", id="+id);

                //Toast.makeText(getActivity().getApplicationContext(), "ok "+position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity().getApplicationContext(), UserDetailActivity.class);
                StringBuilder builder = new StringBuilder();
                builder.append(id);

                intent.putExtra(UserDetailActivity.USER_ID, builder.toString());
                startActivity(intent);
            }
        });
    }
}
