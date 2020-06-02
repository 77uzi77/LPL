package com.lzc.lol.service;

import com.lzc.lol.entity.PageBean;
import com.lzc.lol.entity.Report;
import com.lzc.lol.entity.TransferInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *  举报 业务 实现接口
 */
public interface ReportService {

    /**
     *  增加 举报
     */
    void addReport(String user_name, String report_name, String content);

    /**
     *  分页 查询 举报信息
     */
    PageBean<Report> findByPage(HttpServletRequest request, Map<String, String[]> condition);

    /**
     *  删除举报信息
     */
    void deleteOne(int id);

    /**
     *  设为已读信息
     */
    void passOne(String id);

    /**
     *  删除选中
     */
    void delSelected(String[] ids);
}
