package com.bcm.havoc.wmapp_20181108.UI.BG;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bcm.havoc.mylibrary.UI.BaseActivity;
import com.bcm.havoc.mylibrary.Utils.PermissionManager;
import com.bcm.havoc.wmapp_20181108.MyApplication;
import com.bcm.havoc.wmapp_20181108.R;

public class TTSActivity extends BaseActivity implements View.OnClickListener{
    Intent intent;
    ImageView button1,button2;
    TextView username;
    private MyApplication application;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts);
        PermissionManager permissionManager = new PermissionManager();
        permissionManager.checkBasePermission(TTSActivity.this);
        application = (MyApplication) getApplication();
        setHead_toolbar(false, "透明云物流", false).setDarkTheme();
        button1 = (ImageView)findViewById(R.id.bg_rk);
        button2 = (ImageView)findViewById(R.id.bg_ck);
//        btn_update_v = (Button)findViewById(R.id.btn_update_v);

        username = (TextView)findViewById(R.id.tv_courier_user_name) ;
        username.setText(application.getUserName());
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
//        btn_update_v.setOnClickListener(this);
//        checkUpdateUtils.checkUpdate(TTSActivity.this,"透明云物流管理系统");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //版本更新
//            case R.id.btn_update_v:
//                checkUpdateUtils.checkUpdate(TTSActivity.this,"courier");
//                break;
            //快递入库
            case R.id.bg_rk:
                intent = new Intent(TTSActivity.this,BGInActivity.class);
                startActivity(intent);
                break;
            //快递出库
            case R.id.bg_ck:
                intent = new Intent(TTSActivity.this,BGOutActivity.class);
                startActivity(intent);
                break;

        }
    }
}
