package com.example.syl.androidcleanarchdemo.presenter;

import android.content.Context;
import android.widget.Toast;

import com.example.syl.androidcleanarchdemo.data.User;
import com.example.syl.androidcleanarchdemo.domain.GetUserListUseCase;
import com.example.syl.androidcleanarchdemo.domain.GetUserListUseCaseImpl;
import com.example.syl.androidcleanarchdemo.view.UserListView;

import java.util.List;

/**
 * Created by shenyunlong on 4/20/15.
 */
public class UserListPresenter {

    private Context mContext;

    private UserListView mUserListView;

    private GetUserListUseCaseImpl mGetUserListUseCase = new GetUserListUseCaseImpl();

    public UserListPresenter(Context context) {
        this.mContext = context;
    }

    private GetUserListUseCase.CallBack mCallBack = new GetUserListUseCase.CallBack() {
        @Override
        public void onLoadData(List<User> userList) {
            mUserListView.hideRetry();
            mUserListView.hideLoading();
            mUserListView.renderUserList(userList);
        }

        @Override
        public void onError(String errMsg) {
            mUserListView.hideLoading();
            mUserListView.showRetry();
            Toast.makeText(mContext, errMsg, Toast.LENGTH_SHORT).show();
        }
    };

    public void loadUserList() {
        mUserListView.hideRetry();
        mUserListView.showLoading();
        mGetUserListUseCase.execute(mCallBack);
    }

    public void setUserListView(UserListView userListView) {
        this.mUserListView = userListView;
    }

}
