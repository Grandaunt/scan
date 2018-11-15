package com.bcm.havoc.mylibrary.UI.Widget;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bcm.havoc.mylibrary.R;

import java.util.List;

/**
 * Created by Administrator on 2017/10/12 0012.
 */

public class MainView extends LinearLayout {

    private RelativeLayout root;
    private RelativeLayout Content_root;
    private LinearLayout Bootom_root;
    private ViewGroup.LayoutParams llayoutParams;
    private ViewGroup.LayoutParams RlayoutParams;
    private List<Button> bottom_button;
    private List<View> page_View_list;
    private List<Bitmap> bitmapList;
    private List<Bitmap> bitmapList_;
    private int bootom_select_color;

    /**
     * 首页底部导航
     *
     * @param context             上下文
     * @param bottom_button       底部button 列表
     * @param page_View_list      底部button 对应的 上部控件
     * @param bitmapList          底部控件对应的图标
     * @param bitmapList_         底部控件对应的 选中图标
     * @param bootom_select_color 底部控件选中的控件字体颜色
     */
    public MainView(Context context, List<Button> bottom_button, List<View> page_View_list, List<Bitmap> bitmapList, List<Bitmap> bitmapList_, int bootom_select_color) {
        super(context);
        init_View();
        init_data(context, bottom_button, page_View_list, bitmapList, bitmapList_, bootom_select_color);
    }

    private void init_View() {
        root = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.view_main, null);
        RlayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        addView(root, RlayoutParams);
        llayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f);

        Content_root = (RelativeLayout) root.findViewById(R.id.Content_root);
        Bootom_root = (LinearLayout) root.findViewById(R.id.bootom_root);
    }

    private Drawable bitmapDrawable;

    private void init_data(Context context, final List<Button> bottom_button, final List<View> page_View_list, final List<Bitmap> bitmapList, final List<Bitmap> bitmapList_, final int bootom_select_color) {

        this.bootom_select_color = bootom_select_color;
        this.bottom_button = bottom_button;
        this.page_View_list = page_View_list;
        this.bitmapList = bitmapList;
        this.bitmapList_ = bitmapList_;
        WindowManager wm = ((Activity) getContext()).getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        final int w = width / 16;

        for (int i = 0; i < bottom_button.size(); i++) {
            if (page_View_list.get(i).getParent() == null)
                Content_root.addView(page_View_list.get(i), RlayoutParams);
            if (bottom_button.get(i).getParent() != null)
                ((ViewGroup) bottom_button.get(i).getParent()).removeView(bottom_button.get(i));
            Bootom_root.addView(bottom_button.get(i), llayoutParams);
            final int j = i;
            bottom_button.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (int i = 0; i < bottom_button.size(); i++) {

                        bitmapDrawable = new BitmapDrawable(bitmapList.get(i));
                        bitmapDrawable.setBounds(0, 0, w, w);
                        bottom_button.get(i).setCompoundDrawables(null, bitmapDrawable, null, null);
                        bottom_button.get(i).setTextColor(Color.argb(0xff, 0x22, 0x22, 0x22));
                        // page_View_list.get(i).setVisibility(GONE);
                    }
                    bitmapDrawable = new BitmapDrawable(bitmapList_.get(j));
                    bitmapDrawable.setBounds(0, 0, w, w);
                    bottom_button.get(j).setCompoundDrawables(null, bitmapDrawable, null, null);
                    bottom_button.get(j).setTextColor(getResources().getColor(bootom_select_color));
                    // page_View_list.get(j).setVisibility(VISIBLE);
                    page_View_list.get(j).bringToFront();
                    if(page_View_list.get(j).getParent().equals(Content_root))
                        MainView.this.bringToFront();
                    else
                    ((View)page_View_list.get(j).getParent()).bringToFront();
                }
            });

        }
      /*---------------------------------------------*/
        //设置初始底部选中
        for (int i = 0; i < bottom_button.size(); i++) {
            bitmapDrawable = new BitmapDrawable(bitmapList.get(i));
            bitmapDrawable.setBounds(0, 0, w, w);
            bottom_button.get(i).setCompoundDrawables(null, bitmapDrawable, null, null);
            bottom_button.get(i).setTextColor(Color.argb(0xff, 0x22, 0x22, 0x22));
            //page_View_list.get(i).setVisibility(GONE);
        }
        bitmapDrawable = new BitmapDrawable(bitmapList_.get(0));
        bitmapDrawable.setBounds(0, 0, w, w);
        bottom_button.get(0).setCompoundDrawables(null, bitmapDrawable, null, null);
        bottom_button.get(0).setTextColor(getResources().getColor(bootom_select_color));
        page_View_list.get(0).bringToFront();

        /*---------------------------------------------*/
    }

    public void setSelect(int index) {
        WindowManager wm = ((Activity) getContext()).getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        final int w = width / 16;

        //设置初始底部选中
        for (int i = 0; i < bottom_button.size(); i++) {
            bitmapDrawable = new BitmapDrawable(bitmapList.get(i));
            bitmapDrawable.setBounds(0, 0, w, w);
            bottom_button.get(i).setCompoundDrawables(null, bitmapDrawable, null, null);
            bottom_button.get(i).setTextColor(Color.argb(0xff, 0x22, 0x22, 0x22));
            // page_View_list.get(i).setVisibility(GONE);
        }
        bitmapDrawable = new BitmapDrawable(bitmapList_.get(index));
        bitmapDrawable.setBounds(0, 0, w, w);
        bottom_button.get(index).setCompoundDrawables(null, bitmapDrawable, null, null);
        bottom_button.get(index).setTextColor(getResources().getColor(bootom_select_color));
        page_View_list.get(index).bringToFront();
        if(page_View_list.get(index). getParent().equals(Content_root))
            MainView.this.bringToFront();
        else
            ((View)page_View_list.get(index).getParent()).bringToFront();
    }


}
