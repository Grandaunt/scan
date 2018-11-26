package com.bcm.havoc.wmapp_20181108.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bcm.havoc.mylibrary.Application.ACache;
import com.bcm.havoc.mylibrary.Application.URLConfig;
import com.bcm.havoc.mylibrary.Entity.SectionEntity;
import com.bcm.havoc.mylibrary.Utils.JsonUtils;
import com.bcm.havoc.mylibrary.Utils.logger.Logger;
import com.bcm.havoc.wmapp_20181108.Entity.LoginRequest;
import com.bcm.havoc.wmapp_20181108.Entity.SystemUsers;
import com.bcm.havoc.wmapp_20181108.MyApplication;
import com.bcm.havoc.wmapp_20181108.R;
import com.bcm.havoc.wmapp_20181108.UI.BG.TTSActivity;
import com.google.gson.Gson;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

//import com.bcm.havoc.warehousemanagement.DB.SQLiteHelper;
//账号：18611740426
//密码：566399
@ContentView(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();
    private Context context = LoginActivity.this;
    private String USER, PASSWORD;
    //public static String URL="http://123.57.29.113:8080/MVNFHM/appInterface";
    private ACache aCache;
    private Gson gson = new Gson();
    //"http://172.16.10.242:8080/bcm_rz/appInterface/appLogin";
    private SharedPreferences sharedPrefs;
    private DbManager db;
    private final int SDK_PERMISSION_REQUEST = 127;
    private String permissionInfo;
    EditText etUrl, et_User, et_Password;
    Button btn_Login;
    private MyApplication application;
    private ProgressDialog progressDialog;

    //    private SQLiteHelper sqLiteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        标题栏字体色为白色
//        StatusBarCompat.setLightStatusBar(getWindow(), false);
//        沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar 顶部
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
//        checkUpdateUtils.checkUpdate(LoginActivity.this,"透明云物流管理系统");
        x.view().inject(this);
//        sqLiteHelper = SQLiteHelper.getInstance(this);
        application = (MyApplication) getApplication();
        aCache = ACache.get(this);
        //et_Comm=(EditText)findViewById(R.id.et_comm);
        et_User = (EditText) findViewById(R.id.et_user);
        et_Password = (EditText) findViewById(R.id.et_pass);
        btn_Login = (Button) findViewById(R.id.btn_login);


    }

    /**
     * 单击事件
     * type默认View.OnClickListener.class，故此处可以简化不写，@Event(R.id.bt_main)
     */
    @Event(type = View.OnClickListener.class, value = R.id.btn_login)
    private void testInjectOnClick(View v) {
//        Url =
        USER = et_User.getText().toString();
        PASSWORD = et_Password.getText().toString();
        LoginHttp(USER, PASSWORD);
//            Snackbar.make(v,"OnClickListener",Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 长按事件
     *
     * @Event(type = View.OnLongClickListener.class,value = R.id.bt_main)
     * private boolean testOnLongClickListener(View v){
     * Snackbar.make(v,"testOnLongClickListener",Snackbar.LENGTH_SHORT).show();
     * return true;
     * }
     */
    private void LoginHttp(String acc, String password) {


        RequestParams params = new RequestParams(URLConfig.LoginIn);
//        params.addBodyParameter("Phone",et_User.getText().toString());
//        params.addParameter("PassWord",et_Password.getText().toString());
        progressDialog = new ProgressDialog(this);
        LoginRequest bean = new LoginRequest(et_User.getText().toString(), et_Password.getText().toString());
        Logger.i(et_User.getText().toString() + et_Password.getText().toString());
        String RequestStr = gson.toJson(bean);
        params.setAsJsonContent(true);
        params.setBodyContent(RequestStr);
        params.setCharset("UTF-8");
        Log.i(TAG, "params=" + params + RequestStr);
        x.http().post(params, new Callback.ProgressCallback<String>() {
            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setMessage("正在加载数据，请稍等。。。");
                progressDialog.show();
                progressDialog.setMax((int) total);
                progressDialog.setProgress((int) current);
            }

            @Override
            public void onSuccess(String result) {

                Logger.d(result);
                SectionEntity.ServiceEntity<SystemUsers> Entity = JsonUtils.getServiceEntity(result, SystemUsers.class);
                Logger.i("getMsg:" + Entity.getMsg() + "");
                if (Entity.getStatus().equals("200")) {
                    //写缓存
                    application.setUserLoginMainEntity(Entity.getData());
                    aCache.put(MyApplication.userinfo, Entity.getData());
                    Intent intent = new Intent(LoginActivity.this, TTSActivity.class);
                    Logger.d(Entity.getData());
                    startActivity(intent);
//                        Toast.makeText(LoginActivity.this,"LoginActivity",Toast.LENGTH_SHORT);
                } else {
                    et_User.setText("");
                    et_Password.setText("");
                    Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                Toast.makeText(LoginActivity.this, "下载失败，请检查网络和SD卡", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });

    }


}

