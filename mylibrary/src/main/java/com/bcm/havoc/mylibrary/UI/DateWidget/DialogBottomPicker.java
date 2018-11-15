package com.bcm.havoc.mylibrary.UI.DateWidget;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bcm.havoc.mylibrary.R;
import com.bcm.havoc.mylibrary.Utils.SystemPrintln;

import java.util.ArrayList;
import java.util.List;

/**
 * 最大2级联动
 * <p>
 * new DialogBottomPicker().initData(...).show();
 * 注意数据格式
 * -list00(list0 选择第一项后 第二列所要显示的数据)   -list000(...)
 * -list01(list0 选择第二项后 第二列所要显示的数据)   -list001
 * list0 -list02(...)
 * -list03
 */
public class DialogBottomPicker {

    /**
     * 定义结果回调接口
     */
    public interface ResultString {
        void handle(String stlectStrign);
    }

    private ResultString handler;
    private String Resulyear="";
    private String Resulmonth="";
    private Context context;
    private Dialog datePickerDialog;
    private DatePickerView year_pv, month_pv;

    private List<String> year, month;
    private TextView tv_cancle, tv_select,tv_title;
    private List<String> yearAll;
    private List<List<String>> monthAll = new ArrayList<>();

    public DialogBottomPicker(Context context, ResultString ResultString) {

        this.context = context;
        this.handler = ResultString;
        initDialog();
        initView();

    }

    private void initDialog() {
        if (datePickerDialog == null) {
            datePickerDialog = new Dialog(context, R.style.time_dialog);
            datePickerDialog.setCancelable(false);
            datePickerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            datePickerDialog.setContentView(R.layout.dialog_bottom_picker);
            Window window = datePickerDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.dialog_animation);  //添加动画
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(dm);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = dm.widthPixels;
            window.setAttributes(lp);
        }
    }

    private void initView() {
        year_pv = (DatePickerView) datePickerDialog.findViewById(R.id.year_pv);
        month_pv = (DatePickerView) datePickerDialog.findViewById(R.id.month_pv);

        tv_cancle = (TextView) datePickerDialog.findViewById(R.id.tv_cancle);
        tv_select = (TextView) datePickerDialog.findViewById(R.id.tv_select);
        tv_title = (TextView) datePickerDialog.findViewById(R.id.tv_title);
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.dismiss();
            }
        });
        tv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.handle(Resulyear + " " + Resulmonth);
                datePickerDialog.dismiss();
            }
        });
    }

    public DialogBottomPicker initData(List<String> year, List<List<String>> monthAll) {

        this.yearAll = year;
        this.year = new ArrayList<>();
        this.year.addAll(yearAll);
        if (year != null)
            year_pv.setVisibility(View.VISIBLE);
        this.monthAll = monthAll;
        if (monthAll != null) {
            month_pv.setVisibility(View.VISIBLE);
            if (monthAll.size() > 0) {
                month = new ArrayList<>();
                month.addAll(monthAll.get(0));
            }
        }
        loadComponent();
        return this;
    }
    public DialogBottomPicker SetTitle(String title){
        tv_title.setText(title);
        return this;
    }
    private void loadComponent() {
        if (year.size() > 0) {
            year_pv.setData(year);
            Resulyear = year.get(0);
            year_pv.setSelected(0);
        }
         if(month!=null)
        if (month.size() > 0) {
            Resulmonth = month.get(0);
            month_pv.setData(month);
            month_pv.setSelected(0);
        }

        setIsLoop(true);
    }

    private void addListener() {
        year_pv.setOnSelectListener(new DatePickerView.onSelectListener() {
            @Override
            public void onSelect(String text, int index) {
                Resulyear = text;
                monthChange(text);
            }
        });
        month_pv.setOnSelectListener(new DatePickerView.onSelectListener() {
            @Override
            public void onSelect(String text, int index) {
                Resulmonth = text;
            }
        });

    }

    private void monthChange(String string) {
        /*上一级选中索引为 index*/
        SystemPrintln.out("------string------    " + yearAll.indexOf(string) + string);
        if(month==null)
            return;
        month.clear();
        int index = yearAll.indexOf(string);
        if (index != -1 && monthAll.size() > index) {
            month.addAll(monthAll.get(index));
            if (month.size() > 0) {
                month_pv.setVisibility(View.VISIBLE);
                Resulmonth = month.get(0);
                month_pv.setData(month);
                month_pv.setSelected(0);
                executeAnimator(month_pv);

            }else {
                month_pv.setVisibility(View.INVISIBLE);
            }
        }

    }


    private void executeAnimator(View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f, 0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.1f, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.1f, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(20).start();
    }


    public void show() {

        addListener();
        // setSelectedTime(time);
        datePickerDialog.show();

    }

    public void dismiss(){
        if(null != datePickerDialog){
            datePickerDialog.dismiss();
        }
    }

    /**
     * 设置日期控件是否可以循环滚动
     */
    public void setIsLoop(boolean isLoop) {
        this.year_pv.setIsLoop(isLoop);
        this.month_pv.setIsLoop(isLoop);

    }

}
