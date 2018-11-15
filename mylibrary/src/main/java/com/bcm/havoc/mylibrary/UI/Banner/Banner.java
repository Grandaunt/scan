package com.bcm.havoc.mylibrary.UI.Banner;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bcm.havoc.mylibrary.R;
import com.bcm.havoc.mylibrary.UI.Activity.WebActivity;
import com.bcm.havoc.mylibrary.Utils.SystemPrintln;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Banner extends LinearLayout implements
        OnClickListener {
    // 声明控件
    private ViewPager mViewPager;
    private List<ImageView> mImageView;
    private List<View> pointslist;
    private TextView mTextView;
    private LinearLayout mLinearLayout;
    private Context context;
    private View view;
    // 图片个数
    private int bannerImagesMin_number = 4;
    //传递过来的原数据大小
    private int data_size = 0;
    // ===============================================================================
    // 存放image url
    private List<String> bannerTexts;

    // ViewPager适配器与监听器
    private BannerAdapter mAdapter;
    private BannerListener bannerListener;
    // 圆圈标志位
    private int pointIndex = 0;
    // 线程标志
    private static boolean isStop = false;
    private List<String> details_ur_llist;
    //详情页标题列表
    private List<String> title_list;

    public Banner(Context context, List<String> imageurllist, List<String> details_ur_llist, List<String> title_list) {
        super(context);
        // TODO Auto-generated constructor stub
        if (imageurllist == null)
            return;
        init(context, imageurllist);
        initData();
        initAction();
        this.details_ur_llist = details_ur_llist;
        this.title_list = title_list;
    }

    public void init(Context context, List<String> imageurllist) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(
                R.layout.view_banner, null);
        bannerTexts = imageurllist;
        if (bannerTexts == null || bannerTexts.size() == 0)
            bannerTexts.add("");
        data_size = bannerTexts.size();
        addView(view);

        mViewPager = (ViewPager) view
                .findViewById(R.id.GuangGaoLunBo_viewpager);
        mTextView = (TextView) view.findViewById(R.id.tv_bannertext);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.points);
    }

    /**
     * 初始化事件
     */
    private void initAction() {
        bannerListener = new BannerListener();
        mViewPager.setOnPageChangeListener(bannerListener);
        // 取中间数来作为起始位置
        int index = (Integer.MAX_VALUE / 2)
                - (Integer.MAX_VALUE / 2 % mImageView.size());
        // 用来出发监听器
        mViewPager.setCurrentItem(index);
        mLinearLayout.getChildAt(pointIndex).setEnabled(true);


        // 开启新线程，2秒一次更新Banner
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (!isStop) {
                    SystemClock.sleep(4000);
                    mViewPager.post(new Runnable() {

                        @Override
                        public void run() {
                            mViewPager.setCurrentItem(mViewPager
                                    .getCurrentItem() + 1);
                        }
                    });
                }
            }
        }).start();
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
        LayoutParams params;
        for (int i = 0; i < bannerImagesMin_number; i++) {
            // 设置广告图
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));


            // 加载图片
            String url = bannerTexts.get(i % bannerTexts.size());
            // new httpImage(url, imageView).start();
            Glide.with(context)
                    .load(url)
                    .into(imageView);
            SystemPrintln.out("java.net.URLEncoder.encode(url)" + url);
            imageView.setScaleType(ScaleType.FIT_XY);
            imageView.setOnClickListener(this);
            mImageView.add(imageView);
            // 设置圆圈点
            if (i < data_size) {
                view = new View(context);
                params = new LayoutParams(15, 15);
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
    class BannerListener implements OnPageChangeListener {

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

    public static void ondestroy() {
        // 关闭定时器
        isStop = true;
    }

    // 此处写广告轮播图片点击事件
    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        if (details_ur_llist == null)
            return;
        if (pointIndex < details_ur_llist.size()) {
            /*跳转h5详情*/
            if (details_ur_llist.get(pointIndex).startsWith("http"))
                getContext().startActivity(new Intent(getContext(), WebActivity.class).putExtra("url", details_ur_llist.get(pointIndex)).putExtra("title", title_list.get(pointIndex)));

        }
    }
}
