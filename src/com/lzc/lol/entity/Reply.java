package com.lzc.lol.entity;

/**
 * 回复实体类
 */
public class Reply {

    private int id;
    // 回复者
    private String name;
    // 回复内容
    private String content;
    // 回复者头像
    private String img;
    // 回复时间
    private String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
