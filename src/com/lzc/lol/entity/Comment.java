package com.lzc.lol.entity;

import java.util.ArrayList;

/**
 *  评论实体类
 */
public class Comment {

    private int id;
    // 评论者 的id
    private int wuid;
    // 评论者的 用户名
    private String wname;
    // 评论者 的 头像
    private String wimg;
    // 评论的对象
    private int guid;
    // 评论内容
    private String messageText;
    // 回复信息
    private ArrayList<Reply> replyMessage;
    // 评论时间
    private String writetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getWuid() {
        return wuid;
    }

    public void setWuid(int wuid) {
        this.wuid = wuid;
    }

    public String getWname() {
        return wname;
    }

    public void setWname(String wname) {
        this.wname = wname;
    }

    public String getWimg() {
        return wimg;
    }

    public void setWimg(String wimg) {
        this.wimg = wimg;
    }

    public int getGuid() {
        return guid;
    }

    public void setGuid(int guid) {
        this.guid = guid;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public ArrayList<Reply> getReplyMessage() {
        return replyMessage;
    }

    public void setReplyMessage(ArrayList<Reply> replyMessage) {
        this.replyMessage = replyMessage;
    }

    public String getWritetime() {
        return writetime;
    }
    public void setWritetime(String writetime) {
        this.writetime = writetime;
    }


    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", wuid=" + wuid +
                ", wname='" + wname + '\'' +
                ", wimg='" + wimg + '\'' +
                ", guid=" + guid +
                ", messageText='" + messageText + '\'' +
                ", replyMessage =" + replyMessage +
                ", writetime='" + writetime + '\'' +
                '}';
    }
}
