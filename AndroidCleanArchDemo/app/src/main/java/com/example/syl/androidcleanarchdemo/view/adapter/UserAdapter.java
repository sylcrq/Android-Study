package com.example.syl.androidcleanarchdemo.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.syl.androidcleanarchdemo.data.User;


import java.util.List;

/**
 * Created by shenyunlong on 4/20/15.
 */
public class UserAdapter extends BaseAdapter {
    private List<User> mUserList;

    private Context mContext;

    public UserAdapter(Context context, List<User> userList) {
        this.mContext = context;
        this.mUserList = userList;
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

        String userId = mUserList.get(position).getId();
        int id = -1;
        //return Integer.getInteger(userId, -1);

        try {
            id = Integer.parseInt(userId);
        }catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return id;
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
