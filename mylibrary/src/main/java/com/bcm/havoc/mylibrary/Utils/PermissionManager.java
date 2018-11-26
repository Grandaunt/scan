package com.bcm.havoc.mylibrary.Utils;

import android.Manifest;
import android.app.Activity;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by ${xinGen} on 2017/11/1.
 * blog: http://blog.csdn.net/hexingen
 * <p>
 * 权限工具类
 */

public class PermissionManager {
    /**
     * 写入权限的请求code,提示语，和权限码
     */
    public final static int WRITE_PERMISSION_CODE = 110;
    public final static String WRITE_PERMISSION_TIP = "为了正常使用，请允许权限!";
    public final static String[] PERMS_WRITE = {
            //开发权限汇总
            Manifest.permission.WRITE_EXTERNAL_STORAGE, //访问照片、媒体内容和文件,读取手机存储
            Manifest.permission.ACCESS_FINE_LOCATION,    // 定位精确位置
            Manifest.permission.ACCESS_COARSE_LOCATION, // 定位粗略位置
            Manifest.permission.CAMERA,//照相机
//            Manifest.permission.WRITE_SETTINGS,//读取或写入系统设置
            Manifest.permission.READ_PHONE_STATE,// 读取电话状态权限
            Manifest.permission.INTERNET,//允许程序访问网络连接，可能产生GPRS流量
            Manifest.permission.ACCESS_NETWORK_STATE,//允许程序获取网络信息状态，如当前的网络连接是否有效
            Manifest.permission.MODIFY_AUDIO_SETTINGS,//允许程序修改声音设置信息
            Manifest.permission.ACCESS_WIFI_STATE,//允许程序获取当前WiFi接入的状态以及WLAN热点的信息
            Manifest.permission.CHANGE_WIFI_STATE,//允许程序改变WiFi状态

};
/**
 * @param context
 * return true:已经获取权限
 * return false: 未获取权限，主动请求权限
 */
// @AfterPermissionGranted 是可选的
public static boolean checkPermission(Activity context,String[]perms){
        return EasyPermissions.hasPermissions(context,perms);
        }

/**
 * 检查添加权限
 */
public void checkBasePermission(Activity context,String[]perms){
        boolean result=PermissionManager.checkPermission(context,perms);
        if(!result){
        PermissionManager.requestPermission(context,PermissionManager.WRITE_PERMISSION_TIP,PermissionManager.WRITE_PERMISSION_CODE,perms);
        }
        }
/**
 * 检查基础权限
 */
public void checkBasePermission(Activity context){
        boolean result=PermissionManager.checkPermission(context,PERMS_WRITE);
        if(!result){
        PermissionManager.requestPermission(context,PermissionManager.WRITE_PERMISSION_TIP,PermissionManager.WRITE_PERMISSION_CODE,PermissionManager.PERMS_WRITE);
        }
        }
/**
 * 请求权限
 * @param context
 */
public static void requestPermission(Activity context,String tip,int requestCode,String[]perms){
        EasyPermissions.requestPermissions(context,tip,requestCode,perms);

        }

        }
