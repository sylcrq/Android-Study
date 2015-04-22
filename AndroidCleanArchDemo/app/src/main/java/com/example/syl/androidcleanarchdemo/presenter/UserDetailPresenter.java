package com.example.syl.androidcleanarchdemo.presenter;

import android.content.Context;
import android.widget.Toast;

import com.example.syl.androidcleanarchdemo.data.User;
import com.example.syl.androidcleanarchdemo.domain.GetUserDetailUseCase;
import com.example.syl.androidcleanarchdemo.domain.GetUserDetailUseCaseImpl;
import com.example.syl.androidcleanarchdemo.view.UserDetailView;

/**
 * Created by shenyunlong on 4/22/15.
 */
public class UserDetailPresenter {

    private Context mContext;

    private UserDetailView mUserDetailView;

    private GetUserDetailUseCaseImpl mGetUserDetailUseCase;

    public UserDetailPresenter(Context context, String userId) {
        this.mContext = context;
        this.mGetUserDetailUseCase = new GetUserDetailUseCaseImpl(userId);
    }

    private GetUserDetailUseCaseImpl.CallBack mCallBack = new GetUserDetailUseCase.CallBack() {
        @Override
        public void onLoadData(User user) {
            mUserDetailView.hideLoading();
            mUserDetailView.hideRetry();
            mUserDetailView.showUserDetailLayout();
            mUserDetailView.renderUserDetail(user);
        }

        @Override
        public void onError(String errMsg) {
            mUserDetailView.hideLoading();
            mUserDetailView.hideUserDetailLayout();
            mUserDetailView.showRetry();

            Toast.makeText(mContext, errMsg, Toast.LENGTH_SHORT).show();
        }
    };

    public void loadUserDetail() {
        mUserDetailView.hideRetry();
        mUserDetailView.hideUserDetailLayout();
        mUserDetailView.showLoading();

        mGetUserDetailUseCase.execute(mCallBack);
    }

    public void setUserDetailView(UserDetailView userDetailView) {
        this.mUserDetailView = userDetailView;
    }
}
