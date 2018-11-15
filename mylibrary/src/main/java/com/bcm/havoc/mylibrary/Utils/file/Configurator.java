package com.bcm.havoc.mylibrary.Utils.file;

import java.util.HashMap;

/**
 * <p>文件描述：<p>
 * <p>作者：yqq<p>
 * <p>创建时间：2018/8/7<p>
 * <p>更改时间：2018/8/7<p>
 * <p>版本号：1<p>
 */
public class Configurator {
    private static final HashMap<Object,Object> CONFIGS=new HashMap<>();
    //private static final ArrayList<Interceptor> INTERCEPTORS=new ArrayList<>();
    private static String head="";
    //单例
    private static class Holder{
        private static final Configurator INSTANCE=new Configurator();
    }
    public static Configurator getInstance(){
        return Holder.INSTANCE;
    }
    private Configurator(){
        CONFIGS.put(ConfigKeys.CONFIG_READY.name(),false);
    }
    //获取配置信息
    final HashMap<Object,Object> getConfigs(){
        return CONFIGS;
    }
    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        final Object value = CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " IS NULL");
        }
        return (T) CONFIGS.get(key);
    }

    //配置APIHOST
    public final Configurator withApiHost(String host){
        CONFIGS.put(ConfigKeys.API_HOST,host);
        return this;
    }
    /*head*/
    public final Configurator setHead(String head){
        this.head=head;
        return this;
    }
    public static final String getHead( ){

        return head;
    }
    //检查配置是否完成
    private void checkConfiguration(){
        final boolean isReady=(boolean)CONFIGS.get(ConfigKeys.CONFIG_READY.name());
        if(!isReady){
            //throw new RuntimeException("Configuration is not ready,call configure()");
            System.out.println("Configuration is not ready,call configure()");
        }
    }

    //配置完成
    public final void configure(){
        CONFIGS.put(ConfigKeys.CONFIG_READY.name(),true);
    }


}
