package com.wei.revealcard.HomePager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wei.revealcard.Picture.PictureActivity;
import com.wei.revealcard.R;
import com.wei.revealcard.Video.VideoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 详情的页面
 */
public class DetailActivity extends FragmentActivity implements View.OnClickListener {

    protected static final float FLIP_DISTANCE = 400;
    public static final String EXTRA_IMAGE_URL = "detailImageUrl";
    public static final String IMAGE_TRANSITION_NAME = "transitionImage";
    public static final String TITLE_TRANSITION_NAME = "title";
    public static final String LABLE_TRANSITION_NAME = "lable";
    public static final String RATINGBAR_TRANSITION_NAME = "ratingBar";
    public static final String INTRODUCTION_TRANSITION_NAME = "ll_introduction_pre";
    public static final String HEAD1_TRANSITION_NAME = "head1";
    public static final String HEAD2_TRANSITION_NAME = "head2";
    public static final String LINE_TRANSITION_NAME = "line";

    @BindView(R.id.image) ImageView imageView;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.lable) TextView lable;
    @BindView(R.id.pic) ImageView pic;
    @BindView(R.id.video) ImageView video;
    @BindView(R.id.rating) RatingBar ratingBar;
    @BindView(R.id.ll_introduction) LinearLayout ll_introduction;
    @BindView(R.id.rv_pic) RelativeLayout rv_pic;
    @BindView(R.id.rv_video) RelativeLayout rv_video;
    @BindView(R.id.line_detail) View line_detail;


    private GestureDetector mDetector;
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        String imageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
        ImageLoader.getInstance().displayImage(imageUrl, imageView);
//        Glide.with(this).load(imageUrl).into(imageView);
        String sID = getIntent().getStringExtra("position");
        id = Integer.parseInt(sID);

        ViewCompat.setTransitionName(imageView, IMAGE_TRANSITION_NAME);
        ViewCompat.setTransitionName(title, TITLE_TRANSITION_NAME);
        ViewCompat.setTransitionName(lable, LABLE_TRANSITION_NAME);
        ViewCompat.setTransitionName(ratingBar, RATINGBAR_TRANSITION_NAME);
        ViewCompat.setTransitionName(ll_introduction, INTRODUCTION_TRANSITION_NAME);
        ViewCompat.setTransitionName(pic, HEAD1_TRANSITION_NAME);
        ViewCompat.setTransitionName(video, HEAD2_TRANSITION_NAME);
        ViewCompat.setTransitionName(line_detail, LINE_TRANSITION_NAME);

        rv_pic.setOnClickListener(this);
        rv_video.setOnClickListener(this);
        initView();
        initListener();
    }

    private void initView() {
        switch (id) {
            case 0:
                title.setText("一曲新词酒一杯");
                lable.setText("num1");
                ratingBar.setRating((float)2.5);
                break;
            case 1:
                title.setText("去年天气旧亭台");
                lable.setText("num2");
                ratingBar.setRating(3);
                break;
            case 2:
                title.setText("夕阳西下几时回");
                lable.setText("num3");
                ratingBar.setRating((float)3.5);
                break;
            case 3:
                title.setText("无可奈何花落去");
                lable.setText("num4");
                ratingBar.setRating(4);
                break;
            case 4:
                title.setText("似曾相识燕归来");
                lable.setText("num5");
                ratingBar.setRating((float)4.5);
                break;
            case 5:
                title.setText("小园香径独徘徊");
                lable.setText("num6");
                ratingBar.setRating(5);
                break;
        }
    }

    // 控制下滑返回首页
    private void initListener() {
        mDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (e1 != null && e2 != null) {
                    if (e2.getY() - e1.getY() > FLIP_DISTANCE) {
                        onBackPressed();
                        return true;
                    }
                }
                Log.d("TAG", e2.getX() + " " + e2.getY());
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rv_pic:
                if(id == 5){
                    startActivity(new Intent(this,PictureActivity.class));
                }
                break;
            case R.id.rv_video:
//                startActivity(new Intent(this,VideoActivity.class));
                break;
        }
    }
}
