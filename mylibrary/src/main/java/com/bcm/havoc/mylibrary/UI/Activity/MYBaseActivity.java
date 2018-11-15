package com.bcm.havoc.mylibrary.UI.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

import com.bcm.havoc.mylibrary.Application.AppManager;
import com.bcm.havoc.mylibrary.Application.MyApplication;


/**
 * 提供屏幕尺寸-以及Activity栈管理
 */

class MYBaseActivity extends TitleActivity {
    private static int win_width;
    private static int win_height;
    //activity 的统一管理者
    public AppManager appManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //屏幕的尺寸
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();

        win_width = display.getWidth();
        win_height = display.getHeight();

        // 将当前Activity加入栈管理
        appManager = AppManager.getAppManager();
        appManager.addActivity(this);
        /*----------------------------------------------------------------------------------*/
        /*这段代码作用 见    http://blog.csdn.net/liangde123/article/details/70255520     */
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                appManager.finishActivity(this);
                return;
            }
        }
        /*强制竖屏*/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        /*----------------------------------------------------------------------------------*/
        MyApplication.setContext(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        MyApplication.setContext(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.setContext(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(LoadingDialogue.isShowing())
            LoadingDialogue.dismiss();
    }

    /**
     * 返回屏幕宽度
     *
     * @return
     */
    public static int getWin_width() {
        return win_width;
    }

    /**
     * 返回屏幕高度
     *
     * @return
     */
    public static int getWin_height() {
        return win_height;
    }

}
