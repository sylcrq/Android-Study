package com.example.syl.androidcleanarchdemo.data.datasource;

/**
 * Created by shenyunlong on 4/24/15.
 */
public class CloudDataStore implements DataStore {

    // Singleton
    private static CloudDataStore mCloudDataStore;

    private CloudDataStore() {}

    public static CloudDataStore getInstance() {
        if(mCloudDataStore == null) {
            mCloudDataStore = new CloudDataStore();
        }

        return mCloudDataStore;
    }

    @Override
    public void getUserList(UserListCallBack callBack) {
        // TODO: get from network
    }

    @Override
    public void getUserDetail(String id, UserDetailCallBack callBack) {
        // TODO: get from network
    }
}
