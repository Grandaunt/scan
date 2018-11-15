package com.bcm.havoc.mylibrary.Utils;

import android.os.Handler;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public class GetLoad {
    /**
     * get请求
     *
     * @param urlString
     * @param params
     * @return
     */
    public static String get(String urlString, Map<String, String> params) {
        int res=-1;
        try {
            StringBuilder urlBuilder = new StringBuilder();
            urlBuilder.append(urlString);

            if (null != params) {
                for (Entry<String, String> entry : params.entrySet()) {

                    System.out.println("-------params    "+"Key = " + entry.getKey() + ", Value = " + entry.getValue());

                }
                urlBuilder.append("?");

                Iterator<Entry<String, String>> iterator = params.entrySet()
                        .iterator();

                while (iterator.hasNext()) {
                    Entry<String, String> param = iterator.next();

                    urlBuilder
                            .append(URLEncoder.encode(param.getKey(), "UTF-8"))
                            .append('=')
                            .append(URLEncoder.encode(param.getValue(), "UTF-8"));
                    if (iterator.hasNext()) {
                        urlBuilder.append('&');
                    }
                }

                // UploadImg.uploadFile(file,urlString);
               }

            // 创建HttpClient对象
            HttpClient client = new DefaultHttpClient();
            // 发送get请求创建HttpGet对象
            HttpGet getMethod = new HttpGet(urlBuilder.toString());
            /*访问token*/
            //getMethod.addHeader("Authorization", "Bearer " + AppConfig.APP_TOKEN);
            /*本次访问的url*/
            System.out.println("-------访问url:" + urlBuilder.toString());
            HttpResponse response = client.execute(getMethod);
            //请求超时
            client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 14000);
            //读取超时
            client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 6000);
            // 获取状态码
            res = response.getStatusLine().getStatusCode();
            if (res == 200) {

                StringBuilder builder = new StringBuilder();
                // 获取响应内容
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

                for (String s = reader.readLine(); s != null; s = reader
                        .readLine()) {
                    builder.append(s);
                }
                return builder.toString();
            }
        } catch (Exception e) {
             e.printStackTrace();
        }
        return "res:"+res;
    }

    /**
     *
     * @param url  访问的url
     * @param p    传入的参数
     * @param postWork 对返回数据的操作
     */
    public static void get(final Handler handler, final String url, final Map<String, String> p, final postWork postWork) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String str = get(url, p);
                System.out.println("-------返回：" + str);
                if (str.startsWith("res:")) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            /*失败-返状态码*/
                            postWork.postWoekFail(Integer.valueOf(str.replace("res:","")),url,null);
                        }
                    });
                    return;
                }

                if (postWork != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                               /*成功*/
                                postWork.postWorkSuccess(url, str);
                            } catch (Exception exception) {
                                /*失败--在执行success 时出错*/
                                postWork.postWoekFail(Integer.valueOf(str.replace("res:","")),url,exception);
                                System.out.print("----------访问成功，发生异常" + url);
                                exception.printStackTrace();
                            }
                        }
                    });
                }
            }
        }).start();
    }
    //接口回调
    public interface postWork {
        /**
         * 获取请求之后需要做的事情
         *
         * @param str 请求后获取到的返回str
         *            请求成功回调
         */
        public void postWorkSuccess(String request, String str);

        /*请求失败回调*/
        public void postWoekFail(int StatusCode, String request, Exception exception);

    }
}