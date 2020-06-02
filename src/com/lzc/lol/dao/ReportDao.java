package com.lzc.lol.dao;

import com.lzc.lol.entity.Report;

import java.util.List;
import java.util.Map;

/**
 *  举报 数据库相关 接口
 */
public interface ReportDao {

    /**
     *  增加  举报
     */
    void addReport(String user_name, String report_name, String content);

    /**
     *  查找 总 举报信息 数
     */
    int findTotalCount(Map<String, String[]> condition);

    /**
     *  分页 查询 举报信息
     */
    List<Report> findByPage(int start, int pageSize, Map<String, String[]> condition);

    /**
     *  删除举报信息
     */
    void deleteOne(int id);


    /**
     *  设为已读信息
     */
    void passOne(int id);
}
