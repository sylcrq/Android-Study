package com.example.syl.androidcleanarchdemo.data.datasource;

import com.example.syl.androidcleanarchdemo.data.datasource.DataStore;

/**
 * Created by shenyunlong on 4/24/15.
 */
public class DiskDataStore implements DataStore {

    // Singleton
    private static DiskDataStore mDiskDataStore;

    private DiskDataStore() {}

    public static DiskDataStore getInstance() {
        if(mDiskDataStore == null) {
            mDiskDataStore = new DiskDataStore();
        }

        return mDiskDataStore;
    }

    @Override
    public void getUserList(UserListCallBack callBack) {
        // TODO: Always Get User List From Network
    }

    @Override
    public void getUserDetail(String id, UserDetailCallBack callBack) {
        // TODO: get from cache
    }
}
