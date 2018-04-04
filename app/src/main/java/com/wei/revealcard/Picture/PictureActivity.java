package com.wei.revealcard.Picture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.wei.revealcard.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PictureActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ImageView mBlurView;
    private List<Integer> mList = new ArrayList<>();
    private CardScaleHelper mCardScaleHelper = null;
    private Runnable mBlurRunnable;
    private int mLastPos = -1;
    private String[] list_image;
    private List<Map<String, Object>> cateList;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_picture);
        init();
    }

    // 得到assets中图片的bitmap 已经做了优化，建议不要超过50张，否则会OOM
    // 得到assets中图片的bitmap 已经做了优化，建议不要超过50张，否则会OOM
    // 得到assets中图片的bitmap 已经做了优化，建议不要超过50张，否则会OOM
    private void initPic() {
        cateList = new ArrayList<>();
        try {
            //得到assets/picture/目录下的所有文件的文件名，以便后面打开操作时使用
            list_image = getAssets().list("picture");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        for (int i = 0; i < list_image.length; ++i) {
            InputStream open = null;
            try {
                String temp = "picture/" + list_image[i];
                open = getAssets().open(temp);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = false;//设置inJustDecodeBounds为true后，decodeFile并不分配空间，即，BitmapFactory解码出来的Bitmap为Null,但可计算出原始图片的长度和宽度
                options.inSampleSize = 1; //缩略图大小为原始图片大小的几分之一
                options.inPreferredConfig = Bitmap.Config.RGB_565;//对图片效果不是特别高的情况下使用RGB_565（565没有透明度属性）
                options.inPurgeable = true;//由此产生的位图将分配它的像素,这样他们可以被净化系统需要回收的内存。
                options.inInputShareable = true;
                bitmap = BitmapFactory.decodeStream(open, null, options);

                Map<String, Object> map = new HashMap<>();
                map.put("pic", bitmap);
//                map.put("name", list_image[i]);
//                map.put("id", i);
                cateList.add(map);
                // Assign the bitmap to an ImageView in this layout
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (open != null) {
                    try {
                        open.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void init() {

        //背景模糊图片
        for (int i = 0; i < 20; i++) {
            mList.add(R.drawable.bea1);
            mList.add(R.drawable.bea2);
            mList.add(R.drawable.bea3);
        }
        initPic();

        mRecyclerView = findViewById(R.id.recyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(new CardAdapter(cateList));
        // mRecyclerView绑定scale效果
        mCardScaleHelper = new CardScaleHelper();
        mCardScaleHelper.setCurrentItemPos(0);
        mCardScaleHelper.attachToRecyclerView(mRecyclerView);

        initBlurBackground();
    }

    private void initBlurBackground() {
        mBlurView = findViewById(R.id.blurView);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    notifyBackgroundChange();
                }
            }
        });

        notifyBackgroundChange();
    }

    private void notifyBackgroundChange() {
        if (mLastPos == mCardScaleHelper.getCurrentItemPos()) return;
        mLastPos = mCardScaleHelper.getCurrentItemPos();
        final int resId = mList.get(mCardScaleHelper.getCurrentItemPos());
        mBlurView.removeCallbacks(mBlurRunnable);
        mBlurRunnable = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId);
                ViewSwitchUtils.startSwitchBackgroundAnim(mBlurView, BlurBitmapUtils.getBlurBitmap(mBlurView.getContext(), bitmap, 15));
            }
        };
        mBlurView.postDelayed(mBlurRunnable, 300);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(bitmap != null && !bitmap.isRecycled()){
            bitmap.recycle();
            bitmap = null;
        }
    }

}
