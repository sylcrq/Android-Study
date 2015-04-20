package com.example.syl.androidcleanarchdemo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.syl.androidcleanarchdemo.data.User;
import com.example.syl.androidcleanarchdemo.data.UserListData;


import java.util.List;

/**
 * Created by shenyunlong on 4/20/15.
 */
public class UserAdapter extends BaseAdapter {
    private static List<User> mUserList = UserListData.getUserList();

    private Context mContext;

    public UserAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mUserList.size();
    }

    @Override
    public Object getItem(int position) {
        return mUserList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        TextView userName;

        if(convertView != null) {
            userName = (TextView) convertView;
        }else {
            userName = new TextView(mContext);
        }

        userName.setTextColor(Color.BLACK);
        userName.setTextSize(20);
        userName.setText(mUserList.get(position).getName());

        return userName;
    }
}
