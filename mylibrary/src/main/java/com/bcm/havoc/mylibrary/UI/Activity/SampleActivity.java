package com.bcm.havoc.mylibrary.UI.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bcm.havoc.mylibrary.R;
import com.bcm.havoc.mylibrary.UI.BaseActivity;
import com.bcm.havoc.mylibrary.UI.Widget.MainView;
import com.bcm.havoc.mylibrary.Utils.DoubleClickExit;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/10/20 0020.
 */

public class SampleActivity extends BaseActivity {

    private TextView textView1;

    private TextView textView2;

    private LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sample);

        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        M();
        inItTitle();
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_Date();
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               set_city();
            }
        });

    }

    /*底部导航示例*/
    private void M() {
        root = (LinearLayout) findViewById(R.id.root);

      /*底部控制按钮对应容器列表*/
        List<View> list_container= new ArrayList<>();
        list_container.add(new Button(this));
        list_container.add(new Button(this));
        /*底部控制按钮列表*/
        List<Button> list_button = new ArrayList<>();
        list_button.add(new Button(this));
        list_button.add(new Button(this));
        /*底部控制按钮非选中图片*/
        List<Bitmap> blist = new ArrayList<>();

        blist.add(BitmapFactory.decodeResource(getResources(), R.mipmap.wo_de));
        blist.add(BitmapFactory.decodeResource(getResources(),R.mipmap.shou_yie));
       /*底部控制按钮选中图片*/
        List<Bitmap> blist_select = new ArrayList<>();
        blist_select.add(BitmapFactory.decodeResource(getResources(), R.mipmap.wo_de_));
        blist_select.add(BitmapFactory.decodeResource(getResources(), R.mipmap.shou_yie_));

        root.addView(new MainView(this,  list_button,list_container, blist, blist_select, R.color.divide_color));

    }

    /*标题示例*/
    private void inItTitle() {
        setHead_toolbar(true, "标题设置", true);
        //showInformationDialogue1("ssss");
        showInformationDialogue2("aaaa", null);
        LoadingDialogue.show();

    }
    /*设置城市实例*/
    public void set_city( ) {
        selectAddress(textView2);//调用CityPicker选取区域
    }
    /*设置生日示例*/
    public void set_Date( ) {
        initDatePicker(textView1);
       // customDatePicker.showSpecificTime(false);
        customDatePicker.show(textView1.getText().toString());
    }
    //双击返回
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return DoubleClickExit.onKeyDown(this, keyCode, event);
    }

}
