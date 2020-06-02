package com.lzc.lol.entity;

/**
 * 转会信息实体类
 */
public class TransferInfo {

    private int id;
    // 用户名
    private String user_name;
    // 位置
    private String cid;
    // 年薪
    private String salary;
    // 介绍
    private String introduction;
    // 图片
    private String picture1;
    // 图片
    private String picture2;
    // 图片
    private String picture3;
    // 类型
    private String status;
    // 状态
    private String state;
    // 拍卖期间
    private String duringSell;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPicture1() {
        return picture1;
    }

    public void setPicture1(String picture1) {
        this.picture1 = picture1;
    }

    public String getPicture2() {
        return picture2;
    }

    public void setPicture2(String picture2) {
        this.picture2 = picture2;
    }

    public String getPicture3() {
        return picture3;
    }

    public void setPicture3(String picture3) {
        this.picture3 = picture3;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDuringSell() {
        return duringSell;
    }

    public void setDuringSell(String duringSell) {
        this.duringSell = duringSell;
    }
}
