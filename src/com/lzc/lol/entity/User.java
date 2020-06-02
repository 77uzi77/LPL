package com.lzc.lol.entity;

/**
 * 用户实体类
 */
public class User {

    private int id;
    // 用户名
    private String user_name;
    // 邮箱
    private String email;
    // 密码
    private String password;
    // 年龄
    private int age;
    // 简介
    private String message;
    // 头像
    private String icon;
    // 上个战队
    private String last_team;
    // 加入LPL时间
    private String join_time;
    // 身份
    private String status;
    // 识别码
    private String code;
    // 开始日期
    private String startDate;
    // 结束日期
    private String endDate;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLast_team() {
        return last_team;
    }

    public void setLast_team(String last_team) {
        this.last_team = last_team;
    }

    public String getJoin_time() {
        return join_time;
    }

    public void setJoin_time(String join_time) {
        this.join_time = join_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
