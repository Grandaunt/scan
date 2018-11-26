package com.bcm.havoc.wmapp_20181108.Entity;

import java.io.Serializable;

/**
 * Created by win on 2017/8/29.
 */

public class LoginRequest implements Serializable {
    public LoginRequest() {

    }

    public LoginRequest(String phone, String passWord) {
        Phone = phone;
        PassWord = passWord;
    }

    public String Phone  ;
    public String PassWord  ;


    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "Phone='" + Phone + '\'' +
                ", PassWord='" + PassWord + '\'' +
                '}';
    }
}
