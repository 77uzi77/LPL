package com.lzc.lol.dao;

import com.lzc.lol.entity.Auction;

/**
 *  拍卖  数据库相关 接口
 */
public interface AuctionDao {
    /**
     *  增加 拍卖信息
     */
    void addSell(int id, String sellMoney, String startDate, String endDate);

    /**
     *  得到 拍卖信息
     */
    Auction getMessage(int id);

    /**
     *  修改 拍卖信息
     */
    void reviseOne(int id, String money, String user_name);

    /**
     *  删除 拍卖信息
     */
    void delOne(int id);
}
