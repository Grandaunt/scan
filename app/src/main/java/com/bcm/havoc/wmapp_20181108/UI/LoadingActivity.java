package com.bcm.havoc.wmapp_20181108.UI;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import com.bcm.havoc.mylibrary.Utils.PermissionManager;
import com.bcm.havoc.wmapp_20181108.MyApplication;
import com.bcm.havoc.wmapp_20181108.R;
import com.bcm.havoc.wmapp_20181108.UI.BG.TTSActivity;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static com.bcm.havoc.mylibrary.Utils.PermissionManager.PERMS_WRITE;

public class LoadingActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private static final int REQUEST_CODE = 1;
    private String TAG = this.getClass().getSimpleName();
    private static final int TIME = 3000;
    private static final int GO_HOME = 15000;
    private static final int GO_MAIN = 15001;
    //    private boolean isFirstIn = false;
    private final int SDK_PERMISSION_REQUEST = 127;
    private String permissionInfo;
    private MyApplication application;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GO_HOME:
                    goHome();
//                    goMain();
                    finish();
                    break;
                case GO_MAIN:
                    goMain();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
//        initWindow();
        application = (MyApplication) getApplication();
        PermissionManager permissionManager = new PermissionManager();
        permissionManager.checkBasePermission(LoadingActivity.this);

        if(permissionManager.checkPermission(this,PERMS_WRITE)) {
            if (application.getUserLoginMainEntity() == null) {
                handler.sendEmptyMessageDelayed(GO_HOME, TIME);
            } else {
                handler.sendEmptyMessageDelayed(GO_MAIN, TIME);
            }
        }

    }

    @TargetApi(19)
    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    private void goHome() {
        Log.i("LoadingActivity", "LoadingActivity欢迎。。。Intent");
        Intent intent = new Intent(LoadingActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void goMain() {
        Log.i("LoadingActivity", "LoadingActivity欢迎。。。Intent");
        Intent intent = new Intent(LoadingActivity.this, TTSActivity.class);
//         Intent intent = new Intent(LoadingActivity.this,ScanActivity.class);
        startActivity(intent);
        finish();
    }


    /**
     * 重写onRequestPermissionsResult，用于接受请求结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //将请求结果传递EasyPermission库处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    /**
     * 请求权限成功
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
//        ToastUtils.showToast(getApplicationContext(), "用户授权成功");
        if (application.getUserLoginMainEntity() == null) {
            handler.sendEmptyMessageDelayed(GO_HOME, TIME);
        } else {
            handler.sendEmptyMessageDelayed(GO_MAIN, TIME);
        }
    }
    /**
     * 请求权限失败
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
//        ToastUtils.showToast(getApplicationContext(), "用户授权失败");
        /**
         * 若是在权限弹窗中，用户勾选了'NEVER ASK AGAIN.'或者'不在提示'，且拒绝权限。
         * 这时候，需要跳转到设置界面去，让用户手动开启。
         */
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();

        }
        if (application.getUserLoginMainEntity() == null) {
            handler.sendEmptyMessageDelayed(GO_HOME, TIME);
        } else {
            handler.sendEmptyMessageDelayed(GO_MAIN, TIME);
        }
    }


}
