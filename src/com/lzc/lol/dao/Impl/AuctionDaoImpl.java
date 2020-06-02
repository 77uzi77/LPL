package com.lzc.lol.dao.Impl;

import com.lzc.lol.dao.AuctionDao;
import com.lzc.lol.entity.Auction;
import com.lzc.lol.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *  拍卖  数据库相关 实现类
 */
public class AuctionDaoImpl implements AuctionDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     *   设置单例  饿汉式
     */
    private final static AuctionDaoImpl instance = new AuctionDaoImpl();

    // 私有化构造方法，防止被 new
    private AuctionDaoImpl(){

    }

    //  统一得到单例方法
    public static AuctionDaoImpl getInstance(){
        return instance;
    }

    /**
     *  增加 拍卖信息
     */
    @Override
    public void addSell(int id, String sellMoney, String startDate, String endDate) {
        String sql = "insert into auction (transfer_id,startDate,endDate,sellMoney) values (?,?,?,?) ";
        template.update(sql,id,startDate,endDate,sellMoney);
    }

    /**
     *  得到 拍卖信息
     */
    @Override
    public Auction getMessage(int id) {
        String sql = "select *  from auction where transfer_id = ?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<>(Auction.class),id);
    }

    /**
     *  修改 拍卖信息
     */
    @Override
    public void reviseOne(int id, String money, String user_name) {
        String sql = "update auction set sellMoney = ?,max_user = ? where transfer_id = ?";
        template.update(sql,money,user_name,id);
    }

    /**
     *  删除 拍卖信息
     */
    @Override
    public void delOne(int id) {
        String sql = "delete from auction where id = ?";
        template.update(sql,id);
    }
}
