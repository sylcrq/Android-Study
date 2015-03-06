package com.example.syl.displayingbitmapsdemo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by shenyunlong on 3/5/15.
 */
public class ImageWorker {

    private static final String TAG = "ImageWorker";

    private ImageCache mImageCache;
    private Bitmap mLoadingBitmap;
    protected Resources mResources;


    public ImageWorker(Context context) {
        mResources = context.getResources();
    }

    /**
     * 核心函数
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
                bitmapWorkerTask.cancel(true);
            }else {
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

            if(mImageCache != null && !isCancelled() && getAttachedImageView() != null) {
                bitmap = mImageCache.getBitmapFromDiskCache();
            }

            if(bitmap != null) {
                drawable = new BitmapDrawable(mResources, bitmap);
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
}
