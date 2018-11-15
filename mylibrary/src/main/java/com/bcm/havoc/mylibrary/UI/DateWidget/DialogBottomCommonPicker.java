package com.bcm.havoc.mylibrary.UI.DateWidget;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bcm.havoc.mylibrary.R;
import com.bcm.havoc.mylibrary.Utils.StringUtil;
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
public class DialogBottomCommonPicker {

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
    private EditText minEt, maxEt;
    private TextView tiTxt;

    private List<String> year, month;
    private TextView tv_cancle, tv_select,tv_title;
    private List<String> yearAll;
    private List<List<String>> monthAll = new ArrayList<>();
    private String unitStr;

    public DialogBottomCommonPicker(Context context, ResultString ResultString) {
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
            datePickerDialog.setContentView(R.layout.dialog_bottom_picker_common);
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
        year_pv = (DatePickerView) datePickerDialog.findViewById(R.id.year_pv);year_pv.setIsLoop(false);
        month_pv = (DatePickerView) datePickerDialog.findViewById(R.id.month_pv);month_pv.setIsLoop(false);

        tiTxt = (TextView) datePickerDialog.findViewById(R.id.tiTxt);
        minEt = (EditText) datePickerDialog.findViewById(R.id.minEt);
        maxEt = (EditText) datePickerDialog.findViewById(R.id.maxEt);
        minEt.setInputType(InputType.TYPE_CLASS_NUMBER| InputType.TYPE_NUMBER_FLAG_DECIMAL);
        minEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                String string = s.toString();
                if(!StringUtil.isEmpty(string)){
                    if(string.startsWith("."))
                    {
                        String afterStr = string.length()>1?string.substring(1):"";
                        minEt.setText(afterStr);
                        minEt.setSelection(StringUtil.isEmpty(afterStr)?0:afterStr.length());
                        return;
                    }
                    if(string.indexOf(".")>0)
                    {
                        int idex = string.indexOf(".");
                        if(idex+3 < string.length())
                        {
                            String finalStr = string.substring(0, idex+3);
                            minEt.setText(finalStr);
                            minEt.setSelection(finalStr.length());
                            return;
                        }

                    }
                    float num = Float.parseFloat(string);
                    if(num == 0.0f&&string.length()>1&&!string.contains("0."))
                    {
                        minEt.setText("0");
                        minEt.setSelection(1);
                        return;
                    }
                }

            }
        });
        maxEt.setInputType(InputType.TYPE_CLASS_NUMBER| InputType.TYPE_NUMBER_FLAG_DECIMAL);
        maxEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                String string = s.toString();
                if(!StringUtil.isEmpty(string)){
                    if(string.startsWith("."))
                    {
                        String afterStr = string.length()>1?string.substring(1):"";
                        maxEt.setText(afterStr);
                        maxEt.setSelection(StringUtil.isEmpty(afterStr)?0:afterStr.length());
                        return;
                    }
                    if(string.indexOf(".")>0)
                    {
                        int idex = string.indexOf(".");
                        if(idex+3 < string.length())
                        {
                            String finalStr = string.substring(0, idex+3);
                            maxEt.setText(finalStr);
                            maxEt.setSelection(finalStr.length());
                            return;
                        }

                    }
                    float num = Float.parseFloat(string);
                    if(num == 0.0f&&string.length()>1&&!string.contains("0."))
                    {
                        maxEt.setText("0");
                        maxEt.setSelection(1);
                        return;
                    }
                }

            }
        });

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
                String minStr = minEt.getText().toString().trim();
                String maxStr = maxEt.getText().toString().trim();
                if(StringUtil.isEmpty(minStr)&&StringUtil.isEmpty(maxStr))
                {
                    handler.handle("0|"+Resulyear + " " + Resulmonth);
                }else
                {
                    if(StringUtil.isEmpty(minStr))
                    {
                        handler.handle("1|" + maxStr);
                    }else if(StringUtil.isEmpty(maxStr))
                    {
                        handler.handle("2|" + minStr);
                    }else
                        handler.handle("3|" + minStr + "-" + maxStr);
                }

                datePickerDialog.dismiss();
            }
        });
    }
    public DialogBottomCommonPicker initData(List<String> year, List<List<String>> monthAll) {

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
    public DialogBottomCommonPicker SetTitle(String title, String label, String minHint, String maxHint){
        tv_title.setText(title);
        tiTxt.setText(label);
        minEt.setHint(minHint);
        maxEt.setHint(maxHint);
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

    /**
     * 设置日期控件是否可以循环滚动
     */
    public DialogBottomCommonPicker setIsLoop(boolean isLoop) {
        this.year_pv.setIsLoop(isLoop);
        this.month_pv.setIsLoop(isLoop);
        return this;
    }

}
