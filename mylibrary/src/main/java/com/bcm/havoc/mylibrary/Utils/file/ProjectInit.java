package com.bcm.havoc.mylibrary.Utils.file;

import android.content.Context;

/**
 * <p>文件描述：<p>
 * <p>作者：yqq<p>
 * <p>创建时间：2018/8/7<p>
 * <p>更改时间：2018/8/7<p>
 * <p>版本号：1<p>
 */
public class ProjectInit {
    public static Configurator init(Context context) {
        Configurator.getInstance()
                .getConfigs()
                .put(ConfigKeys.APPLICATION_CONTEXT.name(), context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }

    public static Context getApplicationContext() {
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT.name());
    }

}
