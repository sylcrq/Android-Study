package com.example.syl.androidcleanarchdemo.view;

import com.example.syl.androidcleanarchdemo.data.User;
import com.example.syl.androidcleanarchdemo.view.LoadDataView;

import java.util.List;

/**
 * Created by shenyunlong on 4/20/15.
 */
public interface UserListView extends LoadDataView {

    void renderUserList(List<User> userList);
}
