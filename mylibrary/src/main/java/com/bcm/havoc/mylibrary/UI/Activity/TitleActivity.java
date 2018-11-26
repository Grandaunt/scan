package com.bcm.havoc.mylibrary.UI.Activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.bcm.havoc.mylibrary.R;
import com.bcm.havoc.mylibrary.UI.Widget.Title_toolbar;
import com.githang.statusbar.StatusBarCompat;


/**
 * 提供标题栏-处理状态栏样式
 */
public class TitleActivity extends DialogActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar 顶部
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
          //Translucent navigation bar 底部
          //window.setFlags(
          //WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
          //WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE);
        }

        //去掉标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*状态栏处理*/
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorPrimaryDark), true);

        setContentView(set_Layout());

    }

    /**
     * 返回根控件
     */
    public LinearLayout getRootView() {
        return L;
    }

    /**
     * 返回子根控件
     */
    public View getChildrenRootView() {
        return Childrenl;
    }

    /**
     * 设置状态栏颜色
     */
    public void setStatusColor(int lcolorResource) {
        if (Status == null)
            return;
         /*如果是沉浸式*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Status.setBackgroundResource(lcolorResource);
        }
    }

    /*设置是否显示状态栏*/
    public void setStatusVisable(boolean show) {
         /*如果是沉浸式*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Status == null)
                return;
            if (show) {
                Status.setVisibility(View.VISIBLE);
            } else
                Status.setVisibility(View.GONE);

        }
    }

    /**
     * 设置根控件 颜色
     */
    public void setLcolor(int lcolorResource) {
        L.setBackgroundResource(lcolorResource);
    }

    //设置布局
    private LinearLayout L;
    /*标题栏*/
    private Title_toolbar Title_toolbar;
    private ViewGroup.LayoutParams lpMatchParentWrapContent;
    private ViewGroup.LayoutParams lpMatchParentMatchParent;

    private LinearLayout set_Layout() {
        if (L == (null)) {
            L = new LinearLayout(this);
            L.setOrientation(LinearLayout.VERTICAL);
            lpMatchParentMatchParent = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lpMatchParentWrapContent = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            L.setLayoutParams(lpMatchParentMatchParent);
            Title_toolbar = new Title_toolbar(this, "标题");
            L.addView(Title_toolbar, lpMatchParentWrapContent);
        }
        return L;
    }

    /*需要加载的子布局*/
    private View Childrenl;
    /*状态栏*/
    private LinearLayout Status;

    //重写setContentView
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
         /*如果是沉浸式*/
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            /*状态栏*/
//            Status = new LinearLayout(this);
//            Status.setBackgroundColor(getResources().getColor(R.color.Theme_color));
//            Status.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50));
//            L.addView(Status, 0);
//        }
        Childrenl = getLayoutInflater().inflate(layoutResID, null);
        L.addView(Childrenl, lpMatchParentMatchParent);
    }

    /**
     * 返回标题栏控件
     *
     * @return
     */
    public Title_toolbar setHead_toolbar(boolean showLeft, String title, boolean showRight) {
        if (showLeft) Title_toolbar.back.setVisibility(View.VISIBLE);
        else Title_toolbar.back.setVisibility(View.INVISIBLE);
        if (showRight) Title_toolbar.right.setVisibility(View.VISIBLE);
        else Title_toolbar.right.setVisibility(View.INVISIBLE);
        Title_toolbar.titleName.setText(title);
        return Title_toolbar;
    }


    public Title_toolbar getTitle_toolbar() {
        return Title_toolbar;
    }

}
