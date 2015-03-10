package com.example.syl.displayingbitmapsdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.LruCache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by shenyunlong on 3/5/15.
 * 单例
 */
public class ImageCache {
    private static final String TAG = "ImageCache";

    private static final int DEFAULT_MEM_CACHE_SIZE = 1024 * 1024 * 5;  // 5MB
    private static final int DEFAULT_DISK_CACHE_SIZE = 1024 * 1024 * 10;  // 10MB
    private static final Bitmap.CompressFormat DEFAULT_COMPRESS_FORMAT = Bitmap.CompressFormat.JPEG;
    private static final int DEFAULT_COMPRESS_QUALITY = 70;

    private static ImageCacheParams mCacheParams;

    private LruCache<String, BitmapDrawable> mMemCache;
    private DiskLruCache mDiskCache;

    // 线程同步
    private final Object mDiskCacheLock = new Object();

    /**
     * private构造函数
     */
    private ImageCache(ImageCacheParams cacheParams) {
        mCacheParams = cacheParams;

        // Create Memory Cache
        mMemCache = new LruCache<String, BitmapDrawable>(mCacheParams.mMemCacheSize) {
            @Override
            protected int sizeOf(String key, BitmapDrawable value) {
                // TODO: how to compute the size of bitmap
                Bitmap bitmap = value.getBitmap();

                return bitmap.getByteCount();
            }
        };

        // Create Disk Cache
        // Should NOT do here !
    }

    /**
     * 获取单例
     */
    public static ImageCache getInstance(FragmentManager fragmentManager, ImageCacheParams cacheParams) {
        // TODO: ImageCacheParams DO NOT Change?
        final RetainFragment retainFragment = findOrCreateRetainFragment(fragmentManager);

        ImageCache imageCache = (ImageCache)retainFragment.getObject();
        if(imageCache == null) {
            imageCache = new ImageCache(cacheParams);
            retainFragment.setObject(imageCache);
        }

        return imageCache;
    }

    private static RetainFragment findOrCreateRetainFragment(FragmentManager fragmentManager) {
        RetainFragment fragment = (RetainFragment)fragmentManager.findFragmentByTag(TAG);
        if(fragment == null) {
            fragment = new RetainFragment();
            fragmentManager.beginTransaction().add(fragment, TAG).commit();
        }

        return fragment;
    }

    /**
     * Get BitmapDrawable From Memory Cache
     */
    public BitmapDrawable getBitmapDrawableFromMemCache(String data) {
        BitmapDrawable drawable = null;

        if(mMemCache != null) {
            drawable = mMemCache.get(data);
        }

        return drawable;
    }

    /**
     * Get Bitmap From Disk Cache
     */
    public Bitmap getBitmapFromDiskCache(String data) {
        Bitmap bitmap = null;

        String key = hashKeyForDisk(data);
        InputStream inputStream = null;

        synchronized (mDiskCacheLock) {
            // TODO: thread sync

            if (mDiskCache != null) {
                try {
                    final DiskLruCache.Snapshot snapshot = mDiskCache.get(key);

                    if (snapshot != null) {
                        inputStream = snapshot.getInputStream(0);

                        if (inputStream != null) {
                            // TODO: optimize
                            bitmap = BitmapFactory.decodeStream(inputStream);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    // 关闭inputStream
                    try {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return bitmap;
        }
    }

    /**
     * Add BitmapDrawable To Mem & Disk Cache
     */
    public void addBitmapToCache(String data, BitmapDrawable value) {
        if(data == null || value == null) {
            return;
        }
        // Add to Memory Cache
        if(mMemCache != null) {
            mMemCache.put(data, value);
        }

        String key = hashKeyForDisk(data);
        OutputStream outputStream = null;

        // Add to Disk Cache
        synchronized (mDiskCacheLock) {
            if (mDiskCache != null) {
                try {
                    final DiskLruCache.Snapshot snapshot = mDiskCache.get(key);
                    // 不在Disk Cache中
                    if (snapshot == null) {
                        final DiskLruCache.Editor editor = mDiskCache.edit(key);

                        if (editor != null) {
                            outputStream = editor.newOutputStream(0);

                            if (outputStream != null) {
                                value.getBitmap().compress(mCacheParams.mCompressFormat, mCacheParams.mCompressQuality, outputStream);
                                editor.commit();
                                outputStream.close();
                            }
                        }
                    } else {
                        // close
                        snapshot.getInputStream(0).close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    // 关闭outputStream
                    try {
                        if (outputStream != null) {
                            outputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Create Disk Cache
     */
    public void initDiskCache() {
        synchronized (mDiskCacheLock) {
            if (mDiskCache == null || mDiskCache.isClosed()) {

                File diskCacheDir = mCacheParams.mDiskCacheDir;

                if (diskCacheDir != null) {

                    // 如果目录不存在, 创建
                    if (!diskCacheDir.exists()) {
                        diskCacheDir.mkdirs();
                    }

                    try {
                        mDiskCache = DiskLruCache.open(diskCacheDir, 1, 1, mCacheParams.mDiskCacheSize);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            // TODO: thread sync
        }
    }

    /**
     * Disk Cache目录
     */
    private static File getDiskCacheDir(Context context, String name) {
        String path;

        // Check sdcard
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
           !Environment.isExternalStorageRemovable()) {
            // 外部Cache Path
            path = context.getExternalCacheDir().getPath();
        }else {
            // 内部Cache Path
            path = context.getCacheDir().getPath();
        }

        return new File(path + File.separator + name);
    }

    /**
     * 为Disk Cache生成唯一的Hash Key
     * MessageDigest (not thread-safe)
     */
    private static String hashKeyForDisk(String data) {
        String key;

        try {
            // MD5算法
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(data.getBytes());

            key = bytesToHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {

            key = String.valueOf(data.hashCode());
        }

        return key;
    }

    /**
     * 二进制数据 To 16进制字符串
     * StringBuilder, Interger.toHexString()
     */
    private static String bytesToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();

        for(int i=0; i<bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);

            if(hex.length() == 1) {
                stringBuilder.append('0');
            }
            stringBuilder.append(hex);
        }

        return stringBuilder.toString();
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

    /**
     * Inner Static Class, 内部静态类
     * ImageCache的配置参数
     */
    public static class ImageCacheParams {

        public int mMemCacheSize = DEFAULT_MEM_CACHE_SIZE;
        public int mDiskCacheSize = DEFAULT_DISK_CACHE_SIZE;
        public Bitmap.CompressFormat mCompressFormat = DEFAULT_COMPRESS_FORMAT;
        public int mCompressQuality = DEFAULT_COMPRESS_QUALITY;
        public File mDiskCacheDir;

        public ImageCacheParams(Context context, String name) {
            mDiskCacheDir = getDiskCacheDir(context, name);
        }
    }

    /**
     * Clear Memory & Disk Cache
     */
    public void clearCache() {
        if(mMemCache != null) {
            mMemCache.evictAll();
        }

        synchronized (mDiskCacheLock) {
            try {
                if(mDiskCache != null && !mDiskCache.isClosed()) {
                    mDiskCache.delete();
                    mDiskCache = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void flush() {
        synchronized (mDiskCacheLock) {
            try {
                if (mDiskCache != null) {
                    mDiskCache.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        synchronized (mDiskCacheLock) {
            try {
                if(mDiskCache != null && !mDiskCache.isClosed()) {
                    mDiskCache.close();
                    mDiskCache = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
