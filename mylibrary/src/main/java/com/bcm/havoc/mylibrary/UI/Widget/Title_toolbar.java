package com.bcm.havoc.mylibrary.UI.Widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bcm.havoc.mylibrary.R;


/**
 * 头部工具栏
 */

public class Title_toolbar extends LinearLayout {
    /**
     * 只显示标题
     *  @param context
     *
     */
    public Title_toolbar(Context context, String title) {
        super(context);
        // TODO Auto-generated constructor stub
        addView(context);
        baseInit(title, true, false, null);
    }

    /**
     * 显示标题显示返回
     *
     * @param context
     * @param title
     * @param show_back
     */
    public Title_toolbar(Context context, String title, boolean show_back) {
        super(context);
        // TODO Auto-generated constructor stub
        addView(context);
        baseInit(title, show_back, false, null);

    }

    /**
     * 显示标题 是否显示返回是否显示右侧控件
     *
     * @param context
     * @param title
     * @param show_back
     * @param show_right
     */
    public Title_toolbar(Context context, String title, boolean show_back, boolean show_right) {
        super(context);
        // TODO Auto-generated constructor stub
        addView(context);
        baseInit(title, show_back, show_right, null);

    }

    /**
     * 显示标题 是否显示返回是否显示右侧控件
     *
     * @param context
     * @param title
     * @param show_back
     * @param show_right
     * @param bitmap     title 的背景图片
     */
    public Title_toolbar(Context context, String title, boolean show_back, boolean show_right, Bitmap bitmap) {
        super(context);
        // TODO Auto-generated constructor stub
        addView(context);
        baseInit(title, show_back, show_right, bitmap);

    }

    private void addView(Context context) {
        addView(LayoutInflater.from(context).inflate(R.layout.head_toolbar,
                null), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    /**
     * 设置背景颜色
     */
    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
        this.getChildAt(0).setBackgroundColor(color);
    }

    /**
     * 设置深色主题
     *
     * @return
     */
    public Title_toolbar setDarkTheme() {
        titleName.setTextColor(Color.BLACK);
        right_text.setTextColor(Color.BLACK);
        this.getChildAt(0).setBackgroundColor(Color.WHITE);
        ((ImageView) ((ViewGroup) back).getChildAt(0)).setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.left_d));
        return this;
    }

    /**
     * 设置右部按钮图片和点击事件
     *
     * @param id_image_Resources
     * @param onClickListener
     */
    public void setRight_image_click(int id_image_Resources, OnClickListener onClickListener) {
        ((ViewGroup) right.getParent()).setVisibility(View.VISIBLE);
        right_text.setVisibility(GONE);

        right.setBackgroundResource(id_image_Resources);
        right.setVisibility(VISIBLE);
        ((View)right.getParent()).setOnClickListener(onClickListener);
    }

    /**
     * 设置右侧文字
     */
    public void setRight_text_click(String title, OnClickListener onClickListener) {
        ((ViewGroup) right.getParent()).setVisibility(View.GONE);
        right_text.setText(title);
        right_text.setVisibility(VISIBLE);
        right_text.setOnClickListener(onClickListener);
    }

    /**
     * 设置右侧字体颜色
     *
     * @param color
     */
    public void setRight_text_color(int color) {
        right_text.setTextColor(getResources().getColor(color));

    }

    public View getTopLin() {
        return top_lin;
    }

    /**
     * 设置右侧控件点击事件
     *
     * @param OnClick
     */
    public void setRightContentClick(OnClickListener OnClick) {
        right.setOnClickListener(OnClick);
    }

    //设置标题
    public void set_title(String title) {
        titleName.setText(title);
    }

    public ImageView left_view;
    public ImageView right_view;
    public View back;
    public ImageView right;
    public TextView right_text;
    public TextView titleName;
    public TextView top_lin;

    // 初始化
    @SuppressLint("NewApi")
    private void baseInit(String title, boolean show_back, boolean show_right,
                          Bitmap bitmap) {
        back = findViewById(R.id.back);
        right = (ImageView) findViewById(R.id.right);
        titleName = (TextView) findViewById(R.id.title);
        left_view = (ImageView) findViewById(R.id.left_view);
        right_view = (ImageView) findViewById(R.id.right_view);
        right_text = (TextView) findViewById(R.id.right_text);

        top_lin = findViewById(R.id.top_lin);
        titleName.setText(title);

        if (show_back)
            back.setVisibility(View.VISIBLE);
        else
            back.setVisibility(View.INVISIBLE);

        if (show_right)
            right.setVisibility(View.VISIBLE);
        else
            right.setVisibility(View.INVISIBLE);

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) getContext()).finish();
            }
        });
        if (bitmap != null)
            titleName.setBackground(new BitmapDrawable(bitmap));
    }


}
