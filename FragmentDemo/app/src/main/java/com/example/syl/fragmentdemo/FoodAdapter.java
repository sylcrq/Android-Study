package com.example.syl.fragmentdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by shenyunlong on 2/4/15.
 */
public class FoodAdapter extends BaseAdapter {

    private LayoutInflater mInflater;

    public FoodAdapter(LayoutInflater inflater) {
        this.mInflater = inflater;
    }

    @Override
    public int getCount() {
        return Food.FOODS.length;
    }

    @Override
    public Object getItem(int position) {
        return Food.FOODS[position];
    }

    @Override
    public long getItemId(int position) {
        return Food.FOODS[position].getImageID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if(convertView == null) {
            view = mInflater.inflate(R.layout.item_food_grid, parent, false);
        }else {
            view = convertView;
        }

        // bind
        ImageView iv = (ImageView)view.findViewById(R.id.image);
        TextView tv = (TextView)view.findViewById(R.id.title);

        Food food = (Food)getItem(position);

        iv.setImageResource(food.getImageID());
        tv.setText(food.getTitle());

        return view;
    }
}
