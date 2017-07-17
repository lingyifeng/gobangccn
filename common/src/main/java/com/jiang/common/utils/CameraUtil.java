package com.jiang.common.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.jiang.common.base.CommonActivity;
import com.jiang.common.base.CommonFragment;

/**
 * Created by jiang on 2017/5/26.
 */

public class CameraUtil {

    public static void startImageZoom(CommonActivity activity, int outputX, int outputY, Uri uri, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        initIntent(intent, outputX, outputY, uri);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void startImageZoom(CommonFragment fragment, int outputX, int outputY, Uri uri, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        initIntent(intent, outputX, outputY, uri);
        fragment.startActivityForResult(intent, requestCode);
    }

    private static void initIntent(Intent intent, int outputX, int outputY, Uri uri) {
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
    }
}
