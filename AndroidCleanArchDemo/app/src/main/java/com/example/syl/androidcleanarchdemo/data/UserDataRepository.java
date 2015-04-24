package com.example.syl.androidcleanarchdemo.data;

import com.example.syl.androidcleanarchdemo.data.datasource.CloudDataStore;
import com.example.syl.androidcleanarchdemo.data.datasource.DataStore;

import java.util.List;

/**
 * Created by shenyunlong on 4/21/15.
 */
public class UserDataRepository {

    // singleton 单例
    private static UserDataRepository mUserDataRepository;

    private UserDataRepository() {}

    public static UserDataRepository getInstance() {
        if(mUserDataRepository == null)
            mUserDataRepository = new UserDataRepository();

        return mUserDataRepository;
    }

    // User List 回调接口
    public interface UserListCallBack {
        void onLoadData(List<User> userList);
        void onError(String errMsg);
    }

    // User Detail 回调接口
    public interface UserDetailCallBack {
        void onLoadData(User user);
        void onError(String errMsg);
    }

    public void getUserList(final UserListCallBack callBack) {

        DataStore dataStore = CloudDataStore.getInstance();
        dataStore.getUserList(new DataStore.UserListCallBack() {
            @Override
            public void onLoadData(List<User> userList) {
                callBack.onLoadData(userList);
            }

            @Override
            public void onError(String errMsg) {
                callBack.onError(errMsg);
            }
        });
    }

    public void getUserDetail(String id, final UserDetailCallBack callBack) {

        DataStore dataStore;
        // TODO: if cached, get from disk
        dataStore = CloudDataStore.getInstance();

        dataStore.getUserDetail(id, new DataStore.UserDetailCallBack() {

            @Override
            public void onLoadData(User user) {
                callBack.onLoadData(user);
            }

            @Override
            public void onError(String errMsg) {
                callBack.onError(errMsg);
            }
        });
    }
}
