package com.jiang.common.utils.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.util.Util;
import com.jiang.common.R;
import com.jiang.common.base.Configure;
import com.jiang.common.utils.DisplayUtil;

import java.io.File;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Description : 图片加载工具类 使用glide框架封装
 * <p>
 * throw new IllegalArgumentException("argument error");
 * 抛异常导致崩溃，改成 return
 */
public class ImageLoaderUtils {

    public static void display(Context context, ImageView imageView, String url, int placeholder) {
        if (imageView == null) {
            return;
        }
        url = urlFormat(url);
        if (Util.isOnMainThread()) {
            Glide.with(context.getApplicationContext()).load(url).placeholder(placeholder).error(placeholder)
                    .crossFade().into(imageView);
        }
    }

    public static void display(Context context, ImageView imageView, String url, int placeholder, int error) {
        if (imageView == null) {
            return;
        }
        url = urlFormat(url);
        if (Util.isOnMainThread()) {
            Glide.with(context.getApplicationContext()).load(url).placeholder(placeholder)
                    .error(error).crossFade().into(imageView);
        }
    }

    public static void display(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            return;
        }
        url = urlFormat(url);
        if (Util.isOnMainThread()) {
            Glide.with(context.getApplicationContext()).load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(R.drawable.ic_image_loading)
                    .error(R.drawable.ic_empty_picture)
                    .crossFade()
                    .into(imageView);
        }
    }

    public static void displayByCompleteUrl(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            return;
        }

        if (Util.isOnMainThread()) {
            Glide.with(context.getApplicationContext()).load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(R.drawable.ic_image_loading)
                    .error(R.drawable.ic_empty_picture)
                    .crossFade()
                    .into(imageView);
        }
    }

    public static void displayBlurAndRound(Context context, ImageView imageView, String url, int radius) {
        if (imageView == null) {
            return;
        }
        url = urlFormat(url);
        if (Util.isOnMainThread()) {
            Glide.with(context).load(url)
                    .bitmapTransform(new BlurTransformation(imageView.getContext(), radius)
                            , new RoundedCornersTransformation(imageView.getContext(), DisplayUtil.dip2px(6), 0))
                    .placeholder(R.drawable.video)
                    .error(R.drawable.video)
                    .into(imageView);
        }
    }


    public static void displayBlur(Context context, ImageView imageView, String url, int radius) {
        if (imageView == null) {
            return;
        }
        url = urlFormat(url);
        if (Util.isOnMainThread()) {
            Glide.with(context).load(url)
                    .bitmapTransform(new BlurTransformation(imageView.getContext(), radius))
                    .into(imageView);
        }
    }

    public static void display(Context context, ImageView imageView, String url, Drawable placeholder) {
        if (imageView == null) {
            return;
        }
        url = urlFormat(url);
        if (Util.isOnMainThread()) {
            Glide.with(context.getApplicationContext()).load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(placeholder)
                    .crossFade()
                    .into(imageView);
        }
    }

    public static void displayVideoBackgroud(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            return;
        }
        url = urlFormat(url);
        if (Util.isOnMainThread()) {
            Glide.with(context.getApplicationContext()).load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .error(new ColorDrawable(0x000000))
                    .placeholder(new ColorDrawable(0x000000))
                    .into(imageView);
        }
    }

    public static void display(Context context, ImageView imageView, File url) {
        //本地临时图片不做缓存
        if (imageView == null) {
            return ;
        }
        Glide.with(context.getApplicationContext()).load(url)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_empty_picture)
                .crossFade().into(imageView);
        return ;
    }


    public static void displaySmallPhoto(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            return ;
        }
        url = urlFormat(url);
        if (Util.isOnMainThread()) {
            Glide.with(context.getApplicationContext()).load(url).asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_image_loading)
                    .error(R.drawable.ic_empty_picture)
                    .thumbnail(0.5f)
                    .into(imageView);
        }
    }

    public static void displayBlurPhoto(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            return ;
        }
        url = urlFormat(url);
        if (Util.isOnMainThread()) {
            Glide.with(context.getApplicationContext()).load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .bitmapTransform(new BlurTransformation(context, 50))
                    .into(imageView);
        }
    }

    public static void displayBlurPhoto(Context context, ImageView imageView, String url, Drawable
            placeholder) {
        if (imageView == null) {
            return ;
        }
        url = urlFormat(url);
        if (Util.isOnMainThread()) {
            Glide.with(context.getApplicationContext()).load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(placeholder)
                    .bitmapTransform(new BlurTransformation(context, 50))
                    .into(imageView);
        }
    }


    public static void displayRoundedCornersAvatar(Context context, ImageView imageView, String url,
                                                   Drawable placeholder) {
        if (imageView == null) {
            return ;
        }
        url = urlFormat(url);
        if (Util.isOnMainThread()) {
            Glide.with(context.getApplicationContext()).load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(placeholder)
                    .centerCrop()
                    .bitmapTransform(new RoundedCornersTransformation(context, DisplayUtil.dip2px(7), 1))
                    .into(imageView);
        }
    }

    public static void displayCircleAvatar(Context context, ImageView imageView, String url, Drawable
            placeholder) {
        if (imageView == null) {
            return ;
        }
        url = urlFormat(url);
        if (Util.isOnMainThread()) {
            Glide.with(context.getApplicationContext()).load(url)
//                .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(placeholder)
                    .error(placeholder)
                    .centerCrop()
                    .crossFade(0)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(imageView);
        }
    }

    public static void displayCircleAvatar(Context context, ImageView imageView, File file, Drawable
            placeholder) {
        if (imageView == null) {
            return ;
        }

        if (Util.isOnMainThread()) {
            Glide.with(context.getApplicationContext()).load(file)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(placeholder)
                    .error(placeholder)
                    .centerCrop()
                    .crossFade(0)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(imageView);
        }
    }

    public static void displayBigPhoto(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            return ;
        }
        url = urlFormat(url);
        if (Util.isOnMainThread()) {
            Glide.with(context.getApplicationContext()).load(url).asBitmap()
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(R.drawable.ic_image_loading)
                    .error(R.drawable.ic_empty_picture)
                    .into(imageView);
        }
    }


    public static void displayBigPhoto(Context context, ImageView imageView, File url) {
        if (imageView == null) {
            return ;
        }
        if (Util.isOnMainThread()) {
            Glide.with(context.getApplicationContext()).load(url).asBitmap()
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(R.drawable.ic_image_loading)
                    .error(R.drawable.ic_empty_picture)
                    .into(imageView);
        }
    }

    public static void display(Context context, ImageView imageView, int url) {
        if (imageView == null) {
            return ;
        }
        if (Util.isOnMainThread()) {
            Glide.with(context.getApplicationContext()).load(url)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                .centerCrop()
//                .placeholder(R.drawable.ic_image_loading)
//                .error(R.drawable.ic_empty_picture)
                    .crossFade().into(imageView);
        }
    }

    public static void displayGif(Context context, ImageView imageView, int url) {
        if (imageView == null) {
            return ;
        }
        if (Util.isOnMainThread()) {
            Glide.with(context.getApplicationContext())
                    .load(url)
                    .asGif()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imageView);
        }
    }

    public static void displayRound(Context context, ImageView imageView, String url, int placeImg, float radio) {
        if (imageView == null) {
            return;
        }
        url = urlFormat(url);
        if (Util.isOnMainThread()) {
            Glide.with(context.getApplicationContext()).load(url)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .error(placeImg)
                    .centerCrop()
                    .bitmapTransform(new RoundedCornersTransformation(context, DisplayUtil.dip2px(radio), 0))
                    .into(imageView);
        }
    }

    public static void displayRound(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            return;
        }
        url = urlFormat(url);
        if (Util.isOnMainThread()) {
            Glide.with(context.getApplicationContext()).load(url)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .error(R.drawable.video)
                    .centerCrop()
                    .bitmapTransform(new RoundedCornersTransformation(context, DisplayUtil.dip2px(6), 0))
                    .into(imageView);
        }
    }

    public static void displayRound(Context context, ImageView imageView, String url, float radio) {
        if (imageView == null) {
            return;
        }
        url = urlFormat(url);
        if (Util.isOnMainThread()) {
            Glide.with(context.getApplicationContext()).load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.video)
                    .centerCrop()
                    .bitmapTransform(new RoundedCornersTransformation(context, DisplayUtil.dip2px(radio), 0))
                    .into(imageView);
        }
    }

    public static void displayRound(Context context, ImageView imageView, String url, @DrawableRes int placeImg) {
        if (imageView == null) {
            return;
        }
        url = urlFormat(url);
        if (Util.isOnMainThread()) {
            Glide.with(context.getApplicationContext()).load(url)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .error(placeImg)
                    .centerCrop()
                    .bitmapTransform(new RoundedCornersTransformation(context, DisplayUtil.dip2px(6), 0))
                    .into(imageView);
        }
    }

    public static void getBitMap(Context context, final ImageView imageView, String url) {

        Bitmap myBitmap = null;
//        try {
//            myBitmap = Glide.with(context)
//                    .load(url)
//                    .asBitmap()
//                    .centerCrop()
//                    .into(500, 500)
//                    .get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
        url = urlFormat(url);
        Glide.with(context)
                .load(url)
                .asBitmap()
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_empty_picture)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        imageView.setImageBitmap(resource);
                    }
                });
    }

    public static String urlFormat(String url) {
        return Configure.domain + url;
    }
}
