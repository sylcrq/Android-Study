package com.example.syl.androidcleanarchdemo.domain;

import com.example.syl.androidcleanarchdemo.data.User;

import java.util.List;

/**
 * Created by shenyunlong on 4/21/15.
 */
public interface GetUserListUseCase extends Runnable {

    // 回调接口
    interface CallBack {
        void onLoadData(List<User> userList);
        void onError(String errMsg);
    }

    void execute(CallBack callBack);

    @Override
    void run();
}
