package com.example.syl.androidcleanarchdemo.domain;

import android.os.Handler;
import android.os.Looper;

import com.example.syl.androidcleanarchdemo.data.User;
import com.example.syl.androidcleanarchdemo.data.UserDataRepository;

import java.util.List;

/**
 * Created by shenyunlong on 4/20/15.
 */
public class GetUserListUseCaseImpl implements GetUserListUseCase {

    private CallBack mCallBack;

    private UserDataRepository mUserDataRepository = UserDataRepository.getInstance();

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(CallBack callBack) {
        this.mCallBack = callBack;

        // TODO: THREAD POOL
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        mUserDataRepository.getUserList(new UserDataRepository.UserListCallBack() {
            @Override
            public void onLoadData(final List<User> userList) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallBack.onLoadData(userList);
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
