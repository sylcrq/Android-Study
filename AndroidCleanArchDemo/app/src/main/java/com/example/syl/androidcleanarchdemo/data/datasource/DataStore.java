package com.example.syl.androidcleanarchdemo.data.datasource;

import com.example.syl.androidcleanarchdemo.data.User;

import java.util.List;

/**
 * Created by shenyunlong on 4/24/15.
 */
public interface DataStore {

    // 回调接口
    interface UserListCallBack {
        void onLoadData(List<User> userList);
        void onError(String errMsg);
    }

    // 回调接口
    interface UserDetailCallBack {
        void onLoadData(User user);
        void onError(String errMsg);
    }

    void getUserList(UserListCallBack callBack);

    void getUserDetail(String id, UserDetailCallBack callBack);
}
