package com.lzc.lol.service;

import com.lzc.lol.entity.Auction;

/**
 *  拍卖 业务实现接口
 */
public interface AuctionService {

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
