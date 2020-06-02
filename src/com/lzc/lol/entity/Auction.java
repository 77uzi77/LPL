package com.lzc.lol.entity;

/**
 *  拍卖实体类
 */
public class Auction {

    private int id;
    // 转会id
    private int transfer_id;
    // 开始日期
    private String startDate;
    // 结束日期
    private String endDate;
    // 拍卖金额
    private String sellMoney;
    // 出价最高者
    private String max_user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTransfer_id() {
        return transfer_id;
    }

    public void setTransfer_id(int transfer_id) {
        this.transfer_id = transfer_id;
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

    public String getSellMoney() {
        return sellMoney;
    }

    public void setSellMoney(String sellMoney) {
        this.sellMoney = sellMoney;
    }

    public String getMax_user() {
        return max_user;
    }

    public void setMax_user(String max_user) {
        this.max_user = max_user;
    }
}
