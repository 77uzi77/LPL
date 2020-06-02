package com.lzc.lol.dao;

import com.lzc.lol.entity.TransferInfo;

import java.util.List;
import java.util.Map;

/**
 *  转会信息 数据库相关 实现接口
 */
public interface TransferInfoDao {

    /**
     *  分类 查找 信息总数
     */
    int findTotalCountByClass(int cid,String rname);


    /**
     *  分类 分页查询转会信息
     */
    List<TransferInfo> findByPageByClass(int cid, int start, int pageSize,String rname);

    /**
     *  查询单个转会信息
     */
    TransferInfo findOne(int id);

    /**
     *  增加转会信息
     */
    void addOne(TransferInfo transferInfo);

    /**
     *  通过 用户名 查找转会图片
     */
    Map<String, Object> findByUserName(String user_name);

    /**
     *  修改转会信息
     */
    void reviseOne(TransferInfo transferInfo);

    /**
     *  查找转会信息 相关图片
     */
    Map<String, Object> findImages(int del_num);

    /**
     *  删除转会信息
     */
    int deleteOne(int del_num);

    /**
     *  查询 信息总数
     */
    int findTotalCount(Map<String, String[]> condition);

    /**
     *  通过分页查询
     */
    List<TransferInfo> findByPage(int start, int pageSize, Map<String, String[]> condition);

    /**
     *  通过转会信息申请
     */
    void passOne(int parseInt);

    /**
     *  通过 id 查找 用户名
     */
    String findUserName(int id);

    /**
     *  增加拍卖信息
     */
    void addSell(int id);

//    /**
//     *  修改拍卖信息
//     */
//    void isSell(int id);

    List<Map<String, Object>> findRecommend(String cid,int localId);

//
//    String findById(int id);
}
