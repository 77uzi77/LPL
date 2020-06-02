package com.lzc.lol.dao.Impl;

import com.lzc.lol.dao.ReportDao;
import com.lzc.lol.entity.Report;
import com.lzc.lol.utils.JDBCUtils;
import com.lzc.lol.utils.SimpleUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 *  举报 数据库相关 实现类
 */
public class ReportDaoImpl implements ReportDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     *   设置单例  饿汉式
     */
    private static final ReportDaoImpl instance = new ReportDaoImpl();

    // 私有化构造方法，防止被 new
    private ReportDaoImpl(){

    }

    //  统一得到单例方法
    public static ReportDaoImpl getInstance(){
        return instance;
    }

    /**
     *  增加  举报
     */
    @Override
    public void addReport(String user_name, String report_name, String content) {
        String sql = "insert into report (user_name,content,reporter,report_time) values (?,?,?,?)";
        template.update(sql,user_name,content,report_name, SimpleUtils.getTimeNow());
    }

    /**
     *  查找 总 举报信息 数
     */
    @Override
    public int findTotalCount(Map<String, String[]> condition) {
        //定义模板初始化sql
        String sql = "select count(*) from report where 1 = 1";
        // 调用工具类拼接sql
        Object[] realSql = SimpleUtils.getRealSql(sql, condition);
        StringBuilder sb = (StringBuilder) realSql[0];
        // 得到参数值
        List<Object> params = (List<Object>) realSql[1];



        return template.queryForObject(sb.toString(),Integer.class,params.toArray());
    }

    /**
     *  分页 查询 举报信息
     */
    @Override
    public List<Report> findByPage(int start, int pageSize, Map<String, String[]> condition) {
        //定义模板初始化sql
        String sql = "select * from report  where 1 =  1 ";
        // 调用工具类拼接sql
        Object[] realSql = SimpleUtils.getRealSql(sql, condition);
        StringBuilder sb = (StringBuilder) realSql[0];
        // 得到参数值
        List<Object> params = (List<Object>) realSql[1];

        //添加分页查询
        sb.append(" order by state desc,report_time asc limit ?,? ");

        //添加分页查询参数值
        params.add(start);
        params.add(pageSize);
        sql = sb.toString();

        System.out.println(sql);
        System.out.println(params);

        return template.query(sql,new BeanPropertyRowMapper<>(Report.class),params.toArray());
    }

    /**
     *  删除举报信息
     */
    @Override
    public void deleteOne(int id) {
        String sql = "delete from report where id = ?";
        template.update(sql,id);
    }

    /**
     *  设为已读信息
     */
    @Override
    public void passOne(int id) {
        String sql = "update report set state = '已读' where id = ?";
        template.update(sql,id);
    }
}
