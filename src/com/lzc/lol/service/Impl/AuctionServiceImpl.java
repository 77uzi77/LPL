package com.lzc.lol.service.Impl;


import com.alibaba.druid.sql.dialect.postgresql.ast.stmt.PGUpdateStatement;
import com.lzc.lol.dao.AuctionDao;
import com.lzc.lol.dao.Impl.AuctionDaoImpl;
import com.lzc.lol.entity.Auction;
import com.lzc.lol.service.AuctionService;

/**
 *  拍卖 业务实现类
 */
public class AuctionServiceImpl implements AuctionService {

    private AuctionDao auctionDao = AuctionDaoImpl.getInstance();

    /**
     *   设置单例  饿汉式
     */
    //类内部实例化
    private static final AuctionServiceImpl instance = new AuctionServiceImpl();

    //构造器私有化，防止new对象
    private AuctionServiceImpl(){

    }

    //对外提供公有方法调用
    public static AuctionServiceImpl getInstance(){
        return instance;
    }

    /**
     *  增加 拍卖信息
     */
    @Override
    public void addSell(int id, String sellMoney, String startDate, String endDate) {
        auctionDao.addSell(id,sellMoney,startDate,endDate);
    }

    /**
     *  得到 拍卖信息
     */
    @Override
    public Auction getMessage(int id) {
        return auctionDao.getMessage(id);
    }

    /**
     *  修改 拍卖信息
     */
    @Override
    public void reviseOne(int id, String money, String user_name) {
        auctionDao.reviseOne(id,money,user_name);
    }

    /**
     *  删除 拍卖信息
     */
    @Override
    public void delOne(int id) {
        auctionDao.delOne(id);
    }
}
