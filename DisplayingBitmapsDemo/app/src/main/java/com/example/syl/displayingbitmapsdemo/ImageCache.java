package com.example.syl.displayingbitmapsdemo;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.LruCache;

/**
 * Created by shenyunlong on 3/5/15.
 * 单例
 */
public class ImageCache {
    private static final String TAG = "ImageCache";

    private static final int DEFAULT_MEM_CACHE_SIZE = 1024 * 1024 * 5;  // 5MB

    private LruCache<String, BitmapDrawable> mMemCache;

    /**
     * 构造函数
     */
    private ImageCache() {
        mMemCache = new LruCache<String, BitmapDrawable>(DEFAULT_MEM_CACHE_SIZE) {
            @Override
            protected int sizeOf(String key, BitmapDrawable value) {
                Bitmap bitmap = value.getBitmap();

                return bitmap.getByteCount();
            }
        };
    }

    /**
     * 获取单例
     */
    public static ImageCache getInstance(FragmentManager fragmentManager) {
        final RetainFragment retainFragment = getRetainFragment(fragmentManager);

        ImageCache imageCache = (ImageCache)retainFragment.getObject();
        if(imageCache == null) {
            imageCache = new ImageCache();
            retainFragment.setObject(imageCache);
        }

        return imageCache;
    }

    private static RetainFragment getRetainFragment(FragmentManager fragmentManager) {
        RetainFragment fragment = (RetainFragment)fragmentManager.findFragmentByTag(TAG);
        if(fragment == null) {
            fragment = new RetainFragment();
            fragmentManager.beginTransaction().add(fragment, TAG).commit();
        }

        return fragment;
    }

    public BitmapDrawable getBitmapDrawableFromMemCache(String data) {
        BitmapDrawable drawable = null;

        if(mMemCache != null) {
            drawable = mMemCache.get(data);
        }

        return drawable;
    }

    //public void setBitmapDrawableToMemCache(String data, BitmapDrawable drawable) {
    //    mMemCache.put(data, drawable);
    //}

    // TODO: Get Disk Cache
    public Bitmap getBitmapFromDiskCache() {
        Bitmap bitmap = null;

        return bitmap;
    }

    /**
     * Inner Static Class, 内部静态类
     * 维护一个ImageCache类的单例, 并且不会受到Configuration Changes的影响
     * http://developer.android.com/training/displaying-bitmaps/cache-bitmap.html#config-changes
     */
    public static class RetainFragment extends Fragment {
        private Object mObject;

        public RetainFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Control whether a fragment instance is retained across Activity re-creation (such as from a configuration change).
            setRetainInstance(true);
        }

        public Object getObject() {
            return mObject;
        }

        public void setObject(Object object) {
            this.mObject = object;
        }
    }
}
