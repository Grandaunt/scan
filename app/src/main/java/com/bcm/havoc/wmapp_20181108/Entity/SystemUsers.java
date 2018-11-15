package com.bcm.havoc.wmapp_20181108.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by win on 2017/8/29.
 */

public class SystemUsers implements Serializable {
    public SystemUsers(String s, String s1, String s2, String aNull, String s3, String s4, String s5, String s6, String s7, String s8, String aNull1, String aNull2) {

    }

    public SystemUsers(String userId, String userName, String roleId, String mobile, String email, String loginPwd, String loginKey, String userHead, String sex, String cangKuId, String token, String cangKuName, String roleName) {
        UserId = userId;
        UserName = userName;
        RoleId = roleId;
        Mobile = mobile;
        Email = email;
        LoginPwd = loginPwd;
        LoginKey = loginKey;
        UserHead = userHead;
        Sex = sex;
        CangKuId = cangKuId;
        Token = token;
        CangKuName = cangKuName;
        RoleName = roleName;
    }
    @SerializedName("UserId")
    public String UserId ;
    @SerializedName("UserName")
    public String UserName  ;
    @SerializedName("RoleId")
    public String RoleId  ;
    @SerializedName("Mobile")
    public String Mobile  ;
    @SerializedName("Email")
    public String Email  ;
    @SerializedName("LoginPwd")
    public String LoginPwd  ;
    @SerializedName("LoginKey")
    public String LoginKey  ;
    @SerializedName("UserHead")
    public String UserHead  ;
    @SerializedName("Sex")
    public String Sex  ;
    /// <summary>
    /// 仓库编号
    /// </summary>
    public String CangKuId  ;
    /// <summary>
    /// 用户唯一标识
    /// </summary>
    public String Token  ;

    //虚拟

    public String CangKuName  ;
    /// <summary>
    /// 角色名称
    /// </summary>
    public String RoleName ;
    //缓存期限
    public float Cachedate ;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getRoleId() {
        return RoleId;
    }

    public void setRoleId(String roleId) {
        RoleId = roleId;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getLoginPwd() {
        return LoginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        LoginPwd = loginPwd;
    }

    public String getLoginKey() {
        return LoginKey;
    }

    public void setLoginKey(String loginKey) {
        LoginKey = loginKey;
    }

    public String getUserHead() {
        return UserHead;
    }

    public void setUserHead(String userHead) {
        UserHead = userHead;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getCangKuId() {
        return CangKuId;
    }

    public void setCangKuId(String cangKuId) {
        CangKuId = cangKuId;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getCangKuName() {
        return CangKuName;
    }

    public void setCangKuName(String cangKuName) {
        CangKuName = cangKuName;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }

    public float getCachedate() {
        return Cachedate;
    }

    public void setCachedate(float cachedate) {
        Cachedate = cachedate;
    }

    @Override
    public String toString() {
        return "SystemUsers{" +
                "UserId=" + UserId +
                ", UserName='" + UserName + '\'' +
                ", RoleId=" + RoleId +
                ", Mobile='" + Mobile + '\'' +
                ", Email='" + Email + '\'' +
                ", LoginPwd='" + LoginPwd + '\'' +
                ", LoginKey='" + LoginKey + '\'' +
                ", UserHead='" + UserHead + '\'' +
                ", Sex=" + Sex +
                ", CangKuId=" + CangKuId +
                ", Token='" + Token + '\'' +
                ", CangKuName='" + CangKuName + '\'' +
                ", RoleName='" + RoleName + '\'' +
                '}';
    }
}
