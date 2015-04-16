package com.example.syl.butterknifedemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by shenyunlong on 4/16/15.
 */
public class MyAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;

    public MyAdapter(Context context) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        }else {
            convertView = inflater.inflate(R.layout.my_list_content, parent, false);

            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        }

        viewHolder.name.setText("Linux");

        return convertView;
    }

    public static class ViewHolder {
        @InjectView(R.id.name)
        TextView name;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
