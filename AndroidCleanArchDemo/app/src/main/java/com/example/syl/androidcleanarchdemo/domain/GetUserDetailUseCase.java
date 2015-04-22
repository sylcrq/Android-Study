package com.example.syl.androidcleanarchdemo.domain;

import com.example.syl.androidcleanarchdemo.data.User;

/**
 * Created by shenyunlong on 4/22/15.
 */
public interface GetUserDetailUseCase extends Runnable {

    // 回调接口
    interface CallBack {
        void onLoadData(User user);
        void onError(String errMsg);
    }

    void execute(CallBack callBack);

    @Override
    void run();
}
