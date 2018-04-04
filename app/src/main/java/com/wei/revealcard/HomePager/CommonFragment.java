package com.wei.revealcard.HomePager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wei.revealcard.R;

/**
 * draglayout和cardview的页面
 */
public class CommonFragment extends Fragment implements DragLayout.GotoDetailListener {

    private ImageView imageView;
    private View head1, head2, line_home;
    private TextView title, lable;
    private RatingBar ratingBar;
    private String imageUrl;
    private int num;
    private LinearLayout ll_introduction_pre;

    public static CommonFragment newInstance(int number){
        CommonFragment fragment = new CommonFragment();
        Bundle args = new Bundle();
        args.putInt("number", number);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null)
            num = bundle.getInt("number");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_common, null);

        DragLayout dragLayout = rootView.findViewById(R.id.drag_layout);
        imageView =  dragLayout.findViewById(R.id.image);
        ImageLoader.getInstance().displayImage(imageUrl, imageView);
//        Glide.with(this).load().into(imageView);
        head1 = dragLayout.findViewById(R.id.head1);
        head2 = dragLayout.findViewById(R.id.head2);
        title = dragLayout.findViewById(R.id.title);
        lable = dragLayout.findViewById(R.id.lable);
        line_home = dragLayout.findViewById(R.id.line_home);
        ratingBar =  dragLayout.findViewById(R.id.rating);
        ll_introduction_pre =  dragLayout.findViewById(R.id.ll_introduction_pre);
        dragLayout.setGotoDetailListener(this);

        initView();
        return rootView;
    }

    private void initView() {
        switch (num) {
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

    @Override
    public void gotoDetail() {
        Activity activity = (Activity) getContext();
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                new Pair(imageView, DetailActivity.IMAGE_TRANSITION_NAME),
                new Pair(title, DetailActivity.TITLE_TRANSITION_NAME),
                new Pair(lable, DetailActivity.LABLE_TRANSITION_NAME),
                new Pair(ratingBar, DetailActivity.RATINGBAR_TRANSITION_NAME),
                new Pair(ll_introduction_pre, DetailActivity.INTRODUCTION_TRANSITION_NAME),
                new Pair(head1, DetailActivity.HEAD1_TRANSITION_NAME),
                new Pair(head2, DetailActivity.HEAD2_TRANSITION_NAME),
                new Pair(line_home, DetailActivity.LINE_TRANSITION_NAME)
        );
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_IMAGE_URL, imageUrl);
        intent.putExtra("position", num + "");
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    public void bindData(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
