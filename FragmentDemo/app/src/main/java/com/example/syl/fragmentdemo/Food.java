package com.example.syl.fragmentdemo;

/**
 * Created by shenyunlong on 2/4/15.
 */
public class Food {

    private int mImageID;
    private String mTitle;

    public Food(int imageID, String title) {
        this.mImageID = imageID;
        this.mTitle = title;
    }

    public int getImageID() {
        return mImageID;
    }

    public String getTitle() {
        return mTitle;
    }

    // Data
    public static final Food[] FOODS = {
        new Food(R.drawable.p1, "This"),
        new Food(R.drawable.p2, "Is"),
        new Food(R.drawable.p3, "Food"),
        new Food(R.drawable.p4, "This"),
        new Food(R.drawable.p5, "Is"),
        new Food(R.drawable.p6, "Food"),
        new Food(R.drawable.p7, "This"),
        new Food(R.drawable.p8, "Is"),
        new Food(R.drawable.p9, "Food"),
        new Food(R.drawable.p10, "This"),
        new Food(R.drawable.p11, "Is")
    };
}
