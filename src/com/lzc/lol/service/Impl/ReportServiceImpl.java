package com.lzc.lol.service.Impl;

import com.lzc.lol.dao.Impl.ReportDaoImpl;
import com.lzc.lol.dao.ReportDao;
import com.lzc.lol.entity.PageBean;
import com.lzc.lol.entity.Report;
import com.lzc.lol.service.ReportService;
import com.lzc.lol.utils.SimpleUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 *  举报 业务 实现类
 */
public class ReportServiceImpl implements ReportService {

    private ReportDao reportDao = ReportDaoImpl.getInstance();

    /**
     *   设置单例  饿汉式
     */
    //类内部实例化
    private static final ReportServiceImpl instance = new ReportServiceImpl();

    //构造器私有化，防止new对象
    private ReportServiceImpl(){

    }

    //对外提供公有方法调用
    public static ReportServiceImpl getInstance(){
        return instance;
    }

    /**
     *  增加 举报
     */
    @Override
    public void addReport(String user_name, String report_name, String content) {
        reportDao.addReport(user_name,report_name,content);
    }

    /**
     *  分页 查询 举报信息
     */
    @Override
    public PageBean<Report> findByPage(HttpServletRequest request, Map<String, String[]> condition) {

       //调用dao查询总记录数
        int totalCount = reportDao.findTotalCount(condition);

        // 得到分页条件参数
        Object[] pageParams = SimpleUtils.getPageParams(request, totalCount);
        PageBean pb = (PageBean) pageParams[0];
        int start = (int) pageParams[1];
        // 调用dao查找数据
        List<Report> list = reportDao.findByPage(start,pb.getPageSize(),condition);
        pb.setList(list);


        return pb;
    }

    /**
     *  删除举报信息
     */
    @Override
    public void deleteOne(int id) {
        reportDao.deleteOne(id);
    }

    /**
     *  设为已读信息
     */
    @Override
    public void passOne(String id) {
        reportDao.passOne(Integer.parseInt(id));
    }

    /**
     *  删除选中
     */
    @Override
    public void delSelected(String[] ids) {
        if(ids != null && ids.length > 0){
            //1.遍历数组
            for (String id : ids) {
                //2.调用dao删除
                reportDao.deleteOne(Integer.parseInt(id));
            }
        }
    }
}
