package com.bcm.havoc.mylibrary.UI.Banner;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bcm.havoc.mylibrary.R;
import com.bcm.havoc.mylibrary.UI.BaseActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片浏览
 * 需要传入 image_url_list
 * 和初始选中索引 startIndex
 * Created by Administrator on 2018/1/20 0020.
 */

public class PictureBrowsActivity extends BaseActivity implements
        View.OnClickListener {
    // 声明控件
    private ViewPager mViewPager;
    private List<ImageView> mImageView;
    private List<View> pointslist;
    private TextView mTextView;
    private LinearLayout mLinearLayout;
    private Context context;

    // 图片个数
    private int bannerImagesMin_number = 4;
    //传递过来的原数据大小
    private int data_size = 0;
    // ===============================================================================
    // 存放image url
    private List<String> bannerTexts;

    // ViewPager适配器与监听器
    private BannerAdapter mAdapter;
    private  BannerListener bannerListener;
    // 圆圈标志位
    private int pointIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTitle_toolbar().setVisibility(View.GONE);
        setContentView(R.layout.view_banner);
        init( getIntent().getStringArrayListExtra("image_url_list"));
        initData();
        initAction(Integer.valueOf(getIntent().getStringExtra("startIndex")));
    }



    public void init( List<String> imageurllist) {
        this.context = this;

        bannerTexts = imageurllist;
        if (bannerTexts == null || bannerTexts.size() == 0)
            bannerTexts.add("");
        data_size = bannerTexts.size();

        mViewPager = (ViewPager)
                findViewById(R.id.GuangGaoLunBo_viewpager);
        mTextView = (TextView) findViewById(R.id.tv_bannertext);
        mLinearLayout = (LinearLayout) findViewById(R.id.points);
    }

    /**
     * 初始化事件
     */
    private void initAction(int startIndex) {
        bannerListener = new BannerListener();
        mViewPager.setOnPageChangeListener(bannerListener);
        mViewPager.setCurrentItem(startIndex);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        pointslist = new ArrayList<View>();
        mImageView = new ArrayList<ImageView>();
        // 因为该控件 图片小于四时会出现问题因此

        if (bannerImagesMin_number < bannerTexts.size())
            //当图片大于四时取原来的大小
            bannerImagesMin_number = bannerTexts.size();
        else if (bannerTexts.size() == 1) {
            // 如果为图片个数为1时直接设置为4
            bannerImagesMin_number = 4;
        } else {
            //当图片小于四时将原来图片个数翻倍
            bannerTexts.addAll(bannerTexts);
            bannerImagesMin_number = bannerTexts.size();
        }

        View view;
        LinearLayout.LayoutParams params;
        for (int i = 0; i < bannerImagesMin_number; i++) {
            // 设置广告图
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));


            // 加载图片
            String url = bannerTexts.get(i % bannerTexts.size());
            // new httpImage(url, imageView).start();
            Glide.with(context)
                    .load(url)
                    .into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setOnTouchListener(new BrowsImageOnTouchListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) v.getContext()).finish();
                }
            }));
            mImageView.add(imageView);

            // 设置圆圈点
            if (i < data_size) {
                view = new View(context);
                params = new LinearLayout.LayoutParams(15, 15);
                params.leftMargin = 10;
                view.setBackgroundResource(R.drawable.pointsgray);
                view.setLayoutParams(params);

                pointslist.add(view);

                mLinearLayout.addView(view);
            }
        }
        mAdapter = new BannerAdapter(mImageView);
        mViewPager.setAdapter(mAdapter);
    }


    // 实现VierPager监听器接口
    class BannerListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            int newPosition = position % data_size;

            for (View p : pointslist)
                p.setBackgroundResource(R.drawable.pointsgray);
            pointslist.get(newPosition).setBackgroundResource(
                    R.drawable.pointswhite);
            // 更新标志位
            pointIndex = newPosition;

        }

    }

    // 此处写广告轮播图片点击事件
    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub


    }
}
