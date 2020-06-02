package com.lzc.lol.entity;

/**
 * 状态信息的实体类对象
 */
public class ResultInfo {

    // 结果的状态
    private boolean status;
    // 返回结果的信息
    private String message;
    // 返回结果的数据
    private Object data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}