package com.bcm.havoc.mylibrary.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class CacheUtils 
{ 
public  static String SP_NAME ="user";
private static SharedPreferences sp;

public static SharedPreferences getSp(Context context)
{
    if (sp == null)
    {
        //sp = context.getSharedPreferences(SP_NAME, Context.MODE_WORLD_READABLE );
        sp=  PreferenceManager.getDefaultSharedPreferences(context);
    }
    return sp;
}

/**
 * 获取boolean 数据
 * 
 * @param context
 * @param key
 * @return 如果没有值，返回false
 */
public static boolean getBoolean(Context context, String key)
{
    SharedPreferences sp = getSp(context);
    return sp.getBoolean(key, false);
}

/**
 * 获取boolean 数据
 * 
 * @param context
 * @param key
 * @param defValue
 * @return
 */
public static boolean getBoolean(Context context, String key, boolean defValue)
{
    SharedPreferences sp = getSp(context);
    return sp.getBoolean(key, defValue);
}

/**
 * 存boolean缓存
 * 
 * @param context
 * @param key
 * @param value
 */
public static void setBoolean(Context context, String key, boolean value)
{
    SharedPreferences sp = getSp(context);
    Editor editor = sp.edit();
    editor.putBoolean(key, value);
    editor.commit();
}

/**
 * 获取String 数据
 * 
 * @param context
 * @param key
 * @return 如果没有值，返回null
 */
public static String getString(Context context, String key)
{
    SharedPreferences sp = getSp(context);
    return sp.getString(key, null);
}

/**
 * 获取String 数据
 * 
 * @param context
 * @param key
 * @param defValue
 * @return
 */
public static String getString(Context context, String key, String defValue)
{
    SharedPreferences sp = getSp(context);
    return sp.getString(key, defValue);
}

/**
 * 存String缓存
 * 
 * @param context
 * @param key
 * @param value
 */
public static void setString(Context context, String key, String value)
{
    SharedPreferences sp = getSp(context);
    Editor editor = sp.edit();
    editor.putString(key, value);
    editor.commit();
}


/**
 * 获取int 数据
 * 
 * @param context
 * @param key
 * @return 如果没有值，返回-1
 */
public static int getInt(Context context, String key)
{
    SharedPreferences sp = getSp(context);
    return sp.getInt(key, -1);
}

/**
 * 获取int 数据
 * 
 * @param context
 * @param key
 * @param defValue
 * @return
 */
public static int getInt(Context context, String key, int defValue)
{
    SharedPreferences sp = getSp(context);
    return sp.getInt(key, defValue);
}

/**
 * 存int缓存
 * 
 * @param context
 * @param key
 * @param value
 */
public static void setInt(Context context, String key, int value)
{
    SharedPreferences sp = getSp(context);
    Editor editor = sp.edit();
    editor.putInt(key, value);
    editor.commit();
}
}