package com.example.syl.displayingbitmapsdemo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by shenyunlong on 3/5/15.
 */
public abstract class ImageWorker {

    private static final String TAG = "ImageWorker";

    private static final int MESSAGE_INIT_DISK_CACHE = 0;
    private static final int MESSAGE_CLEAR = 1;
    private static final int MESSAGE_FLUSH = 2;
    private static final int MESSAGE_CLOSE = 3;

    private ImageCache mImageCache;
    private ImageCache.ImageCacheParams mCacheParams;
    private Bitmap mLoadingBitmap;
    protected Resources mResources;

    /**
     * 构造函数
     */
    public ImageWorker(Context context) {
        mResources = context.getResources();
    }

    public void addImageCache(FragmentManager fragmentManager, ImageCache.ImageCacheParams cacheParams) {
        mCacheParams = cacheParams;
        mImageCache = ImageCache.getInstance(fragmentManager, mCacheParams);

        // Create Disk Cache
        new CacheAsyncTask().execute(MESSAGE_INIT_DISK_CACHE);
    }

    /**
     * loadImage
     */
    public void loadImage(Object data, ImageView imageView) {
        if(data == null)
            return;

        BitmapDrawable drawable = null;

        if(mImageCache != null) {
            drawable = mImageCache.getBitmapDrawableFromMemCache(String.valueOf(data));
        }

        if(drawable != null) {
            imageView.setImageDrawable(drawable);
        }
        else if(cancelPotentialWork(data, imageView)) {
            BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(data, imageView);
            AsyncDrawable asyncDrawable = new AsyncDrawable(mResources, mLoadingBitmap, bitmapWorkerTask);

            imageView.setImageDrawable(asyncDrawable);
            bitmapWorkerTask.execute();
        }
    }

    private boolean cancelPotentialWork(Object data, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if(bitmapWorkerTask != null) {
            if(bitmapWorkerTask.mData == null || !bitmapWorkerTask.mData.equals(data)) {
                // Cancel
                bitmapWorkerTask.cancel(true);
            }else {
                // 已经在执行了
                return false;
            }
        }

        return true;
    }

    private BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if(imageView != null) {
            final Drawable drawable = imageView.getDrawable();

            if (drawable instanceof AsyncDrawable) {
                return ((AsyncDrawable) drawable).getBitmapWorkerTask();
            }
        }

        return null;
    }

    /**
     * The actual AsyncTask that will asynchronously process the image.
     */
    private class BitmapWorkerTask extends AsyncTask<Void,Void,BitmapDrawable> {
        private Object mData;
        private final WeakReference<ImageView> mImageViewWeakReference;

        public BitmapWorkerTask(Object data, ImageView imageView) {
            mData = data;
            mImageViewWeakReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected BitmapDrawable doInBackground(Void... params) {
            Bitmap bitmap = null;
            BitmapDrawable drawable = null;

            String dataString = String.valueOf(mData);

            if(mImageCache != null && !isCancelled() && getAttachedImageView() != null) {
                bitmap = mImageCache.getBitmapFromDiskCache(dataString);
            }

            if(bitmap != null) {
                drawable = new BitmapDrawable(mResources, bitmap);

                if(mImageCache != null) {
                    mImageCache.addBitmapToCache(dataString, drawable);
                }
            }

            return drawable;
        }

        @Override
        protected void onPostExecute(BitmapDrawable bitmapDrawable) {
            if(isCancelled())
                return;

            final ImageView imageView = getAttachedImageView();
            if(bitmapDrawable != null && imageView != null) {
                imageView.setImageDrawable(bitmapDrawable);
            }
        }

        @Override
        protected void onCancelled(BitmapDrawable bitmapDrawable) {
            super.onCancelled(bitmapDrawable);
        }

        private ImageView getAttachedImageView() {
            final ImageView imageView = mImageViewWeakReference.get();
            final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

            if(this == bitmapWorkerTask)
                return imageView;

            return null;
        }
    }

    /**
     * 子类实现
     */
    protected abstract Bitmap processBitmap();

    /**
     * A custom Drawable that will be attached to the imageView while the work is in progress.
     * Contains a reference to the actual worker task, so that it can be stopped if a new binding is
     * required, and makes sure that only the last started worker process can bind its result,
     * independently of the finish order.
     */
    private class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTask> mBitmapWorkerTaskWeakReference;

        public AsyncDrawable(Resources res, Bitmap bitmap, BitmapWorkerTask bitmapWorkerTask) {
            super(res, bitmap);
            mBitmapWorkerTaskWeakReference = new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return mBitmapWorkerTaskWeakReference.get();
        }
    }

    protected void initDiskCacheInternal() {
        if(mImageCache != null) {
            mImageCache.initDiskCache();
        }
    }

    protected void clearCacheInternal() {
        // TODO: clear cache
    }

    protected void flushCacheInternal() {
        // TODO: flush Cache
    }

    protected void closeCacheInternal() {
        // TODO: close Cache
    }


    /**
     *
     */
    private class CacheAsyncTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            switch (params[0]) {
                case MESSAGE_INIT_DISK_CACHE:
                    initDiskCacheInternal();
                    break;
                case MESSAGE_CLEAR:
                    clearCacheInternal();
                    break;
                case MESSAGE_FLUSH:
                    flushCacheInternal();
                    break;
                case MESSAGE_CLOSE:
                    closeCacheInternal();
                    break;
            }

            return null;
        }
    }
}
