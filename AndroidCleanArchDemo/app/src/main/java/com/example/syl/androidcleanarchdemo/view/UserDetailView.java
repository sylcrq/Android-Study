package com.example.syl.androidcleanarchdemo.view;

import com.example.syl.androidcleanarchdemo.data.User;
import com.example.syl.androidcleanarchdemo.view.LoadDataView;

/**
 * Created by shenyunlong on 4/22/15.
 */
public interface UserDetailView extends LoadDataView {

    void renderUserDetail(User user);

    void showUserDetailLayout();

    void hideUserDetailLayout();
}
