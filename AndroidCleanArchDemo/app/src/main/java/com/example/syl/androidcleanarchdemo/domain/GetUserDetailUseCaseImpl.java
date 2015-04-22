package com.example.syl.androidcleanarchdemo.domain;

import android.os.Handler;
import android.os.Looper;

import com.example.syl.androidcleanarchdemo.data.User;
import com.example.syl.androidcleanarchdemo.data.UserDataRepository;

/**
 * Created by shenyunlong on 4/22/15.
 */
public class GetUserDetailUseCaseImpl implements GetUserDetailUseCase{

    private CallBack mCallBack;

    private UserDataRepository mUserDataRepository = UserDataRepository.getInstance();

    private String mUserId;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    public GetUserDetailUseCaseImpl(String userId) {
        this.mUserId = userId;
    }

    @Override
    public void execute(CallBack callBack) {
        this.mCallBack = callBack;

        // TODO: THREAD POOL
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        mUserDataRepository.getUserDetail(mUserId, new UserDataRepository.UserDetailCallBack() {
            @Override
            public void onLoadData(final User user) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallBack.onLoadData(user);
                    }
                });
            }

            @Override
            public void onError(final String errMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallBack.onError(errMsg);
                    }
                });
            }
        });
    }
}
