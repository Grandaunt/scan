package com.bcm.havoc.mylibrary.Utils;

/**
 * Created by Administrator on 2017/12/8 0008.
 * 全局日志管理
 */

public class SystemPrintln {
    public static void out(String str) {
        if(str==null)
            str="null";
        System.out.println(str);
    }
}

