package com.lzc.lol.service;

import com.lzc.lol.entity.PageBean;
import com.lzc.lol.entity.TransferInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 *  转会信息 业务 实现接口
 */
public interface TransferInfoService {
    /**
     *  分类 分页查询转会信息
     */
    PageBean<TransferInfo> pageQuery(int cid,HttpServletRequest request, String rname );

    /**
     *  查询单个转会信息
     */
    TransferInfo findOne(String rid);

    /**
     *  增加转会信息
     */
    void addOne(TransferInfo transferInfo);

    /**
     *  查找转会信息 相关图片
     */
    String[] findImages(int del_num);

    /**
     *  删除转会信息
     */
    int deleteOne(int del_num);

    /**
     *  通过分页查询
     */
    PageBean<TransferInfo> findByPage(HttpServletRequest request, Map<String, String[]> condition);

    /**
     *  通过转会信息申请
     */
    void passOne(String id);

    /**
     *  删除选中转会信息
     */
    void delSelectedUser(String[] ids);

    /**
     *  通过 用户名 查找转会信息
     */
    void findUserName(String id);

    /**
     *  将 转会信息 设为拍卖
     */
    void addSell(int id);

//    /**
//     *  更改 转会 拍卖信息
//     */
//    void isSell(int id);

    /**
     *  找到相同位置 推荐选手
     */
    List<Map<String, Object>> findRecommend(String cid,String localId);

    /**
     *  查找用户名
     */
    String selectUser(int id);
}
