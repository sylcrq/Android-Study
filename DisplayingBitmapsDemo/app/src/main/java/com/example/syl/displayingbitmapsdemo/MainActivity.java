package com.example.syl.displayingbitmapsdemo;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ImageView;

import java.lang.ref.WeakReference;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "MainActivity";

    private Bitmap mPlaceHolderBitmap;

    private LruCache<String, Bitmap> mMemoryCache;
    // DiskLruCache

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlaceHolderBitmap =BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

        final int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);
        final int cachesize = maxMemory / 8;

        Log.d(TAG, "MaxMemory="+maxMemory+", CacheSize="+cachesize);

        mMemoryCache = new LruCache<String, Bitmap>(cachesize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //return super.sizeOf(key, value);
                return value.getByteCount() / 1024;
            }
        };

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            ImageView iv = (ImageView)rootView.findViewById(R.id.image);
            Log.d(TAG, "ImageView=["+iv.getMeasuredWidth()+","+iv.getHeight()+"]");

            //MainActivity mainActivity = (MainActivity)getActivity();
            //mainActivity.loadBitmap(R.drawable.rio2_jewel_icon, iv);
            if(MainActivity.class.isInstance(getActivity())) {
                ((MainActivity)getActivity()).loadBitmap(R.drawable.rio2_jewel_icon, iv);
            }

            //iv.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.rio2_jewel_icon, 100, 100));

            //BitmapFactory.Options options = new BitmapFactory.Options();
            //options.inJustDecodeBounds = true;
            //BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher, options);
            //int imageWidth = options.outWidth;
            //int imageHeight = options.outHeight;
            //String imageType = options.outMimeType;
            //
            //Log.i(TAG, "["+imageWidth+","+imageHeight+"],"+imageType);

            return rootView;
        }
    }

    // 1. Loading Large Bitmaps Efficiently
    public static int calculateInSampleSize(BitmapFactory.Options options, int width, int height) {
        final int imageWidth = options.outWidth;
        final int imageHeight = options.outHeight;

        int inSampleSize = 1;

        while((imageWidth/(inSampleSize*2)) > width &&
              (imageHeight/(inSampleSize*2)) > height) {
            inSampleSize *= 2;
        }

        return inSampleSize;
    }

    // 1. Loading Large Bitmaps Efficiently
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int width, int height) {
        // Read Bitmap Dimensions and Type
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        Log.d(TAG, "["+options.outWidth+","+options.outHeight+"], "+options.outMimeType);

        options.inSampleSize = calculateInSampleSize(options, width, height);

        Log.d(TAG, "inSampleSize="+options.inSampleSize);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    // 2. Processing Bitmaps Off the UI Thread
    public void loadBitmap(int resId, ImageView imageView) {
        //BitmapWorkerTask task = new BitmapWorkerTask(imageView);
        //task.execute(resId);

        final String key = String.valueOf(resId);

        final Bitmap bitmap = getBitmapFromMemoryCache(key);

        if(bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }else {
            if (cancelPotentialWork(resId, imageView)) {
                BitmapWorkerTask task = new BitmapWorkerTask(imageView);

                AsyncDrawable asyncDrawable = new AsyncDrawable(getResources(), mPlaceHolderBitmap, task);
                imageView.setImageDrawable(asyncDrawable);

                task.execute(resId);
            }
        }
    }

    // 2. Processing Bitmaps Off the UI Thread
    public static boolean cancelPotentialWork(int resId, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if(bitmapWorkerTask != null) {
            int bitmapData = bitmapWorkerTask.data;

            if(bitmapData == 0 || bitmapData != resId) {
                bitmapWorkerTask.cancel(true);
            }else {
                return false;
            }
        }

        return true;
    }

    // 2. Processing Bitmaps Off the UI Thread
    public static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if(imageView != null) {
            final Drawable drawable = imageView.getDrawable();

            if(drawable instanceof AsyncDrawable) {
                return ((AsyncDrawable) drawable).getBitmapWorkerTask();
            }
        }

        return null;
    }

    // 2. Processing Bitmaps Off the UI Thread
    private class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {

        private final WeakReference<ImageView> imageViewReference;
        private int data;

        public BitmapWorkerTask(ImageView imageView) {
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            data = params[0];
            Bitmap bitmap = decodeSampledBitmapFromResource(getResources(), data, 300, 300);

            addBitmapToMemoryCache(String.valueOf(data), bitmap);

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(isCancelled()) {
                bitmap = null;
            }

            if(bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

                if (imageView != null && bitmapWorkerTask == this) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

    // 2. Processing Bitmaps Off the UI Thread
    private static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

        public AsyncDrawable(Resources res, Bitmap bitmap, BitmapWorkerTask bitmapWorkerTask) {
            super(res, bitmap);
            bitmapWorkerTaskReference = new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }

    // 3. Caching Bitmaps
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if(getBitmapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    // 3. Caching Bitmaps
    public Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }
}
