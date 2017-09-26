package com.example.aditijoshi.personalcapital.Core;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by aditijoshi on 9/24/17.
 */

public class UIHelper {


    private static UIHelper mInstance;

    public static UIHelper getInstance() {

        if (mInstance == null) {
            mInstance = new UIHelper();
        }
        return mInstance;
    }

    // cache images in memory and expire every 48 hours so that if the image already exists we
    // can skip having to download it again.
    public DisplayImageOptions rectangularDisplayOption = new DisplayImageOptions.Builder()
//            .showImageOnLoading(R.drawable)
//            .showImageForEmptyUri(R.drawable.bg_channel)
//            .showImageOnFail(R.drawable.bg_channel)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .displayer(new SimpleBitmapDisplayer())
            .imageScaleType(ImageScaleType.EXACTLY)
            .build();



    public void displayImage(String url, ImageView view, DisplayImageOptions displayOptions, ImageLoadingListener listener) {

        File cacheDir = StorageUtils.getCacheDirectory(view.getContext());
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(view.getContext());
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024);

        // Expire image disk cache after 48 hours
        config.diskCache(new LimitedAgeDiskCache(cacheDir, 48 * 60 * 60));
        config.tasksProcessingOrder(QueueProcessingType.LIFO);

        ImageLoader.getInstance().init(config.build());
        ImageLoader.getInstance().displayImage(url, view, displayOptions, null);

    }

    public int getColor(Context context, int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getColor(colorId);
        } else
            return ResourcesCompat.getColor(context.getResources(), colorId, null);
    }


    public String formatDate(String date) {

        // Date formatter to convert the date returned from the xml to a readable string.
        SimpleDateFormat gsdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d");
        try {
            Date d = gsdf.parse(date);
            return sdf.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }
}
