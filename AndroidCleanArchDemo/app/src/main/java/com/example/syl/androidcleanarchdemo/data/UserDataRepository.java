package com.example.syl.androidcleanarchdemo.data;

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

    public void getUserList(UserListCallBack callBack) {
        // FIXME: TEST DATA
        List<User> userList = UserListData.getUserList();

        if(userList != null) {
            callBack.onLoadData(userList);
        }else {
            callBack.onError("Get User List Failed!");
        }
    }

    public void getUserDetail(String id, UserDetailCallBack callBack) {
        // FIXME: TEST DATA
        User user = UserListData.getUserById(id);

        if(user != null) {
            callBack.onLoadData(user);
        }else {
            callBack.onError("Get User Detail Failed!");
        }
    }
}
