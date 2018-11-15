package com.bcm.havoc.wmapp_20181108.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @Author misolamiso.
 * @Date 2018/11/9-16:21
 */
public class pageResults implements Serializable{

    public pageResults() {
    }


    /**
     * msg : 入库成功！
     * code : 200
     * name :  韵达快递
     */


    public pageResults(String status, String msg, String data) {
        this. Status = status;
        this.Msg = msg;
        this. Data = data;
    }
    @SerializedName("Status")
    public String Status ;
    @SerializedName("Msg")
    private String Msg;
    @SerializedName("Data")
    private String Data;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public String getData() {
        return Data;
    }



    public void setData(String data) {
        Data = data;
    }

    @Override
    public String toString() {
        return "ServiceEntity{" +
                "Status=" + Status +
                ", Msg='" + Msg + '\'' +
                ", Data=" + Data +
                '}';
    }
}
