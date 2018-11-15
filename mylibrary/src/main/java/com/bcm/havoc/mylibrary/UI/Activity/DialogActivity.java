package com.bcm.havoc.mylibrary.UI.Activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.bcm.havoc.mylibrary.R;
import com.bcm.havoc.mylibrary.UI.DateWidget.DialogDatePicker;
import com.bcm.havoc.mylibrary.Utils.DialogUtils;
import com.lljjcoder.citypickerview.widget.CityPicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//import com.bcm.havoc.mylibrary.UI.CityPicker.CityPicker;

/**
 * 该类提供三类对话框-1.加载中-2.单按钮确认对话框-3.双按钮对话框
 */

public class DialogActivity extends FragmentActivity {

    public DialogUtils InformationDialogue2;
    public DialogUtils InformationDialogue1;
    public DialogUtils GrabInformationDialogue;
    public DialogUtils ContactorDialDialogue;
    public DialogUtils HouseTipsDialogue;
    public DialogUtils ConfirmHouseDialogue;
    public DialogUtils LoadingDialogue;
    public DialogDatePicker customDatePicker;
    public CityPicker cityPicker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        //加载等待动画dialog
        LoadingDialogue = new DialogUtils.Builder(this).view(R.layout.dialog_wait)
                .gravity(Gravity.CENTER)
                .cancelTouchout(false)
                .style(R.style.Dialog_loading)
                .build();
        /*设置加载中弹窗返回键不可消失*/
        setOnKeyListenerloading();
    }

    public void showInformationDialogue2(String information, View.OnClickListener onClickListener) {
        InformationDialogue2 = new DialogUtils.Builder(this)
                .view(R.layout.dialog_layout)
                .settext(information, R.id.dialog_content)
                .setLeftButton("取消", R.id.cancle)
                .setRightButton("确定", R.id.down)
                .gravity(Gravity.CENTER)
                .style(R.style.Dialog_NoAnimation)
                .cancelTouchout(false)
                .addViewOnclick(R.id.cancle, onClickListener)
                .addViewOnclick(R.id.down, onClickListener)
                .build();
        InformationDialogue2.show();
    }

    public void showContactorDialDialogue(String information, View.OnClickListener cancelHandler, View.OnClickListener dialHandler) {
        ContactorDialDialogue = new DialogUtils.Builder(this)
                .view(R.layout.dialog_dial_phone)
                .settext(information, R.id.dialog_content)
                .setLeftButton("取消", R.id.cancle)
                .setRightButton("呼叫", R.id.down)
                .gravity(Gravity.CENTER)
                .style(R.style.Dialog_NoAnimation)
                .cancelTouchout(false)
                .addViewOnclick(R.id.cancle, cancelHandler)
                .addViewOnclick(R.id.down, dialHandler)
                .build();
        ContactorDialDialogue.show();
    }

    public void showHouseTipsDialogue(String information, View.OnClickListener cancelHandler, View.OnClickListener confirmHandler) {
        HouseTipsDialogue = new DialogUtils.Builder(this)
                .view(R.layout.dialog_dial_phone)
                .settext(information, R.id.dialog_content)
                .setLeftButton("关闭", R.id.cancle)
                .setRightButton("查看", R.id.down)
                .gravity(Gravity.CENTER)
                .style(R.style.Dialog_NoAnimation)
                .cancelTouchout(false)
                .addViewOnclick(R.id.cancle, cancelHandler)
                .addViewOnclick(R.id.down, confirmHandler)
                .build();
        HouseTipsDialogue.show();
    }

    public void setOnKeyListener2() {
        InformationDialogue2.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (InformationDialogue2.isShowing())
                    return true;
                else return false;
            }
        });
    }

    public void setOnKeyListener1() {
        InformationDialogue1.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (InformationDialogue1.isShowing())
                    return true;
                else return false;
            }
        });
    }

    public void setOnKeyListenerloading() {
        LoadingDialogue.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                //((Activity)(MyApplication.getInstance())).finish();
                if (LoadingDialogue.isShowing())
                    return true;
                else return false;
            }
        });
    }

    /****
     * 抢房成功
     *
     * @param leftbtn
     * @param rightbtn
     * @param information
     */
    public void showGrabInformationDialogue(String leftbtn, String rightbtn, String title, String information, View.OnClickListener cancelHandler, View.OnClickListener submitHandler) {
        GrabInformationDialogue = new DialogUtils.Builder(this)
                .view(R.layout.dialog_grab_success)
                .setTitle(title, R.id.dialog_title)
                .settext(information, R.id.dialog_content)
                .setLeftButton(leftbtn, R.id.cancle)
                .setRightButton(rightbtn, R.id.down)
                .gravity(Gravity.CENTER)
                .style(R.style.Dialog_NoAnimation)
                .cancelTouchout(false)
                .addViewOnclick(R.id.cancle, cancelHandler)
                .addViewOnclick(R.id.down, submitHandler)
                .build();
        GrabInformationDialogue.show();
    }

    public void showInformationDialogue2(String leftbtn, String rightbtn, String information, View.OnClickListener onClickListener) {
        InformationDialogue2 = new DialogUtils.Builder(this)
                .view(R.layout.dialog_layout)
                .settext(information, R.id.dialog_content)
                .setLeftButton(leftbtn, R.id.cancle)
                .setRightButton(rightbtn, R.id.down)
                .gravity(Gravity.CENTER)
                .style(R.style.Dialog_NoAnimation)
                .cancelTouchout(false)
                .addViewOnclick(R.id.cancle, onClickListener)
                .addViewOnclick(R.id.down, onClickListener)
                .build();
        InformationDialogue2.show();
    }

    public void showInformationDialogue1(String downString, String information, View.OnClickListener onClickListener) {
        if (InformationDialogue1 != null)
            if (InformationDialogue1.isShowing())
                return;
        InformationDialogue1 = new DialogUtils.Builder(this)
                .view(R.layout.dialog_layout_one)
                .settext(information, R.id.dialog_content)
                .setRightButton(downString, R.id.down)
                .gravity(Gravity.CENTER)
                .style(R.style.Dialog_NoAnimation)
                .cancelTouchout(false)
                .addViewOnclick(R.id.down, onClickListener)
                .build();
        //InformationDialogue1.setCancelable(false);

        InformationDialogue1.show();
    }

    public void showConfirmHouseDialogue(String information, String btnText, View.OnClickListener onClickListener) {
        ConfirmHouseDialogue = new DialogUtils.Builder(this)
                .view(R.layout.dialog_layout_confirm_house)
                .settext(information, R.id.dialog_content)
                .setRightButton(btnText, R.id.down)
                .gravity(Gravity.CENTER)
                .style(R.style.Dialog_NoAnimation)
                .cancelTouchout(false)
                .addViewOnclick(R.id.cancle, onClickListener)
                .addViewOnclick(R.id.down, onClickListener)
                .build();
        ConfirmHouseDialogue.setCancelable(false);
        ConfirmHouseDialogue.show();
    }

    /**
     * 今日之前
     *
     * @param currentDate
     */
    public void initDatePicker(final TextView currentDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        //currentDate.setText(now.split(" ")[0]);

        customDatePicker = new DialogDatePicker(this, new DialogDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                currentDate.setText(time.split(" ")[0]);

            }
        }, "1900-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行

        customDatePicker.setIsLoop(false); // 循环滚动
    }

    /**
     * 今日之后
     *
     * @param currentDate
     */
    public void initDatePickerAfterToday(final TextView currentDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        //currentDate.setText(now.split(" ")[0]);

        customDatePicker = new DialogDatePicker(this, new DialogDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                currentDate.setText(time.split(" ")[0]);

            }
        }, now, "2030-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行

        customDatePicker.setIsLoop(true); // 循环滚动
    }
    /*yqq*/
    public DialogUtils showInformationDialogue2(String information, int leftColor, int rightColor, View.OnClickListener onRightClickListener) {
        InformationDialogue2 = new DialogUtils.Builder(this)
                .view(R.layout.dialog_layout)
                .settext(information, R.id.dialog_content)
                .setLeftButton("取消", R.id.cancle, leftColor)
                .setRightButton("确定", R.id.down, rightColor)
                .gravity(Gravity.CENTER)
                .style(R.style.Dialog_NoAnimation)
                .cancelTouchout(false)
                .addViewOnclick(R.id.cancle, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InformationDialogue2.dismiss();
                    }
                })
                .addViewOnclick(R.id.down, onRightClickListener)
                .build();
        InformationDialogue2.show();
        return InformationDialogue2;
    }
    /*呼叫 yqq*/
    public DialogUtils showInformationDialogCall(String information, int leftColor, int rightColor, View.OnClickListener onRightClickListener) {
        InformationDialogue2 = new DialogUtils.Builder(this)
                .view(R.layout.dialog_layout)
                .settext(information, R.id.dialog_content)
                .setLeftButton("取消", R.id.cancle, leftColor)
                .setRightButton("呼叫", R.id.down, rightColor)
                .gravity(Gravity.CENTER)
                .style(R.style.Dialog_NoAnimation)
                .cancelTouchout(false)
                .addViewOnclick(R.id.cancle, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InformationDialogue2.dismiss();
                    }
                })
                .addViewOnclick(R.id.down, onRightClickListener)
                .build();
        InformationDialogue2.show();
        return InformationDialogue2;
    }
    /*强弹消息 yqq*/
    public DialogUtils showInformationDialogueOne(String title, String information, int textColor, int bgColor, View.OnClickListener onClickListener) {
        InformationDialogue1 = new DialogUtils.Builder(this)
                .view(R.layout.dialog_layout_one)
                .setTitle(title,R.id.dialog_title)
                .settext(information, R.id.dialog_content)
                .setRightButtonYel("我知道了", R.id.down,textColor,bgColor)
                .gravity(Gravity.CENTER)
                .style(R.style.Dialog_NoAnimation)
                .cancelTouchout(false)
                .addViewOnclick(R.id.down, onClickListener)
                .build();
        InformationDialogue1.setCancelable(false);
        InformationDialogue1.show();
        return InformationDialogue1;
    }
    /*提醒框 yqq*/
    public DialogUtils showRemindDialogueOne(String title, String information, String btntext, int textColor, int bgColor, View.OnClickListener onClickListener) {
        InformationDialogue1 = new DialogUtils.Builder(this)
                .view(R.layout.dialog_layout_one)
                .setTitle(title,R.id.dialog_title)
                .settext(information, R.id.dialog_content)
                .setRightButtonYel(btntext, R.id.down,textColor,bgColor)
                .gravity(Gravity.CENTER)
                .style(R.style.Dialog_NoAnimation)
                .cancelTouchout(false)
                .addViewOnclick(R.id.down, onClickListener)
                .build();
        InformationDialogue1.setCancelable(false);
        InformationDialogue1.show();
        return InformationDialogue1;
    }

    /*---------------省市区选择----------------*/
    public void select_max_Address(final TextView set_city_text, final onSelectAddressNext next) {

        cityPicker = new CityPicker.Builder(this)
                .textSize(15)
                .titleBackgroundColor("#FFFFFF")
                .titleTextColor("#696969")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .title("城市选择")
                .province("江苏省")
                .city("常州市")
                .district("天宁区")
                .textColor(Color.parseColor("#000000"))
                .backgroundPop(Color.parseColor("#00000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();

        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                //为TextView赋值

                set_city_text.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
                // UserInfo.setCity(set_city_text.getText().toString());
                if (next != null) {
                    next.onNext(set_city_text.getText().toString());
                }
            }
        });
    }
    public interface onSelectAddressNext {
        void onNext(String address);
    }

    /*---------------城市选择-------------------*/
    public void selectAddress(final TextView set_city_text) {

        cityPicker = new CityPicker.Builder(this)
                .textSize(15)
                .titleBackgroundColor("#FFFFFF")
                .titleTextColor("#696969")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .title("城市选择")
                .province("北京市")
                .city("北京市")
                .district("丰台区")
                .textColor(Color.parseColor("#000000"))
                .backgroundPop(Color.parseColor("#00000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();

        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                //为TextView赋值
                //set_city_text.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
                set_city_text.setText(city.trim());
                // UserInfo.setCity(set_city_text.getText().toString());
            }
        });

    }


}
