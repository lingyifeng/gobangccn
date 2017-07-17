package com.jiang.common.photo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiang.common.R;
import com.jiang.common.base.CommonActivity;
import com.jiang.common.utils.FileUtil;
import com.jiang.common.utils.imageloader.ImageLoaderUtils;
import com.jiang.common.widget.MyPopWindow;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by liuheng on 2015/8/19.
 */
public class ViewPagerActivity extends Activity {

    private ViewPager mPager;

    private String[] imgUrl;
    private int pos;

    public static void startAction(CommonActivity activity, String url) {
        String[] urls = new String[]{url};
        startAction(activity, urls, -1);
    }

    public static void startAction(CommonActivity activity, String[] urls, int pos) {
        Intent intent = new Intent(activity, ViewPagerActivity.class);
        intent.putExtra("imgs", urls);
        intent.putExtra("pos", pos);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imgUrl = getIntent().getStringArrayExtra("imgs");
        pos = getIntent().getIntExtra("pos", 0);

        setContentView(R.layout.activity_view_pager);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
        mPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imgUrl.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                PhotoView view = new PhotoView(ViewPagerActivity.this);
                view.enable();
                view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                ImageLoaderUtils.getBitMap(ViewPagerActivity.this, view, imgUrl[position]);
                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
//                        Bitmap bm = ((BitmapDrawable) ((ImageView) v).getDrawable()).getBitmap();
//                        FileUtil.saveBitmapReturnStr(bm);
                        showDown((ImageView) v);
                        return true;
                    }
                });
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        mPager.setCurrentItem(pos);
    }

    private void showDown(final ImageView img) {
        final MyPopWindow mPopWindow;
        final View view = LayoutInflater.from(ViewPagerActivity.this).inflate(R.layout.layout_pop_picdown, null);
        mPopWindow = new MyPopWindow(view, MATCH_PARENT, WRAP_CONTENT, true);
        ColorDrawable cd = new ColorDrawable(0x90000000);
        mPopWindow.setBackgroundDrawable(cd);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setFocusable(true);
        TextView tvSave = (TextView) view.findViewById(R.id.tv_pop_save);
        final TextView tvCancel = (TextView) view.findViewById(R.id.tv_pop_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveImageToSysAlbum(img);
                mPopWindow.dismiss();
            }
        });
        mPopWindow.showAtLocation(mPager, Gravity.BOTTOM, 0, 0);
    }

    private void SaveImageToSysAlbum(ImageView img) {
        if (FileUtil.isSdcardExist()) {
            BitmapDrawable bmpDrawable = (BitmapDrawable) img.getDrawable();
            Bitmap bmp = bmpDrawable.getBitmap();
            if (bmp != null) {
                try {
                    ContentResolver cr = getContentResolver();
                    String url = MediaStore.Images.Media.insertImage(cr, bmp,
                            String.valueOf(System.currentTimeMillis()), "");
                    Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
//                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            }
        } else {
//            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
//        sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment
//                .getExternalStorageDirectory())));
        MediaScannerConnection.scanFile(this, new String[]{Environment.getExternalStoragePublicDirectory(Environment
                .DIRECTORY_DCIM).getPath()}, null, null);
    }
}
