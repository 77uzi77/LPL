package com.lzc.lol.dao.Impl;

import com.lzc.lol.dao.TransferInfoDao;
import com.lzc.lol.entity.TransferInfo;
import com.lzc.lol.utils.JDBCUtils;
import com.lzc.lol.utils.SimpleUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.lang.model.element.VariableElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *  转会信息 数据库相关 实现类
 */
public class TransferInfoDaoImpl implements TransferInfoDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     *   设置单例  饿汉式
     */
    private final static  TransferInfoDaoImpl instance = new TransferInfoDaoImpl();

    // 私有化构造方法，防止被 new
    private TransferInfoDaoImpl(){

    }

    //  统一得到单例方法
    public static TransferInfoDaoImpl getInstance(){
        return instance;
    }

    /**
     *  分类 查找 信息总数
     */
    @Override
    public int findTotalCountByClass(int cid,String user_name) {
        //1.定义sql模板
        String sql = "select count(*) from transfer_info where status = '1' ";
        // 调用工具类拼接sql
        Object[] realSqlByClass = getRealSqlByClass(sql, cid, user_name);
        StringBuilder sb = (StringBuilder) realSqlByClass[0];
        // 得到参数值
        List params = (List) realSqlByClass[1];

        sql = sb.toString();


        return template.queryForObject(sql,Integer.class,params.toArray());
    }

    /**
     *  分类 分页查询转会信息
     */
    @Override
    public List<TransferInfo> findByPageByClass(int cid, int start, int pageSize,String user_name) {
        //定义sql模板
        String sql = " select * from transfer_info where status = '1' ";
        // 调用工具类拼接sql
        Object[] realSqlByClass = getRealSqlByClass(sql, cid, user_name);
        StringBuilder sb = (StringBuilder) realSqlByClass[0];
        // 得到参数值
        List params = (List) realSqlByClass[1];

        //添加分页条件
        sb.append(" limit ? , ? ");
        sql = sb.toString();
        params.add(start);
        params.add(pageSize);


        return template.query(sql, new BeanPropertyRowMapper<>(TransferInfo.class),params.toArray());
    }

    /**
     *  查询单个转会信息
     */
    @Override
    public TransferInfo findOne(int id) {

        String sql = "select * from transfer_info where id = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(TransferInfo.class),id);
    }

    /**
     *  增加转会信息
     */
    @Override
    public void addOne(TransferInfo transferInfo) {
        String sql = "insert into transfer_info (user_name,cid,salary,picture1,picture2,picture3,introduction,status,state) " +
                "VALUES (?,?,?,?,?,?,?,'0',?) ";

        template.update(sql,transferInfo.getUser_name(),transferInfo.getCid(),transferInfo.getSalary(),
                        transferInfo.getPicture1(),transferInfo.getPicture2(),transferInfo.getPicture3(),
                        transferInfo.getIntroduction(),transferInfo.getState());
    }

    /**
     *  通过 用户名 查找转会图片
     */
    @Override
    public Map<String, Object> findByUserName(String user_name) {
        try {
            String sql = "select picture1,picture2,picture3 from transfer_info where user_name = ?";
            return template.queryForMap(sql,user_name);
        }catch (Exception e){

        }
        return null;
    }

    /**
     *  修改转会信息
     */
    @Override
    public void reviseOne(TransferInfo transferInfo) {
        String sql = "update transfer_info set cid = ?,salary = ?,picture1 = ?,picture2 = ?,picture3 = ?," +
                "introduction = ?,status = '0',state = ? where user_name = ?";
        template.update(sql,transferInfo.getCid(),transferInfo.getSalary(),
                transferInfo.getPicture1(), transferInfo.getPicture2(), transferInfo.getPicture3(),
                transferInfo.getIntroduction(),transferInfo.getState(),transferInfo.getUser_name());
    }

    /**
     *  查找转会信息 相关图片
     */
    @Override
    public Map<String, Object> findImages(int del_num) {
        String sql = "select picture1,picture2,picture3 from transfer_info where id = ?";

        return template.queryForMap(sql, del_num);
    }

    /**
     *  删除转会信息
     */
    @Override
    public int deleteOne(int del_num) {
        String sql = "delete from transfer_info where id = ?";

        return template.update(sql,del_num);
    }

    /**
     *  查询 信息总数
     */
    @Override
    public int findTotalCount(Map<String, String[]> condition) {
        //定义模板初始化sql
        String sql = "select count(*) from transfer_info where status = 0 ";
        // 调用工具类拼接sql
        Object[] realSql = SimpleUtils.getRealSql(sql, condition);
        StringBuilder sb = (StringBuilder) realSql[0];
        // 得到参数值
        List<Object> params = (List<Object>) realSql[1];


        return template.queryForObject(sb.toString(),Integer.class,params.toArray());
    }

    /**
     *  通过分页查询
     */
    @Override
    public List<TransferInfo> findByPage(int start, int pageSize, Map<String, String[]> condition) {
        //定义模板初始化sql
        String sql = "select * from transfer_info  where status = 0 ";
        // 调用工具类拼接sql
        Object[] realSql = SimpleUtils.getRealSql(sql, condition);
        StringBuilder sb = (StringBuilder) realSql[0];
        // 得到参数值
        List<Object> params = (List<Object>) realSql[1];
        //添加分页查询
        sb.append(" limit ?,? ");
        //添加分页查询参数值
        params.add(start);
        params.add(pageSize);
        sql = sb.toString();


        return template.query(sql,new BeanPropertyRowMapper<TransferInfo>(TransferInfo.class),params.toArray());
    }

    /**
     *  通过转会信息申请
     */
    @Override
    public void passOne(int parseInt) {
        String sql = "update transfer_info set status = 1 where id = ?";
        template.update(sql,parseInt);
    }

    /**
     *  通过 id 查找 用户名
     */
    @Override
    public String findUserName(int id) {
        String sql = "select user_name from transfer_info where id = ?";
        try {
            return (String) template.queryForMap(sql,id).get("user_name");
        }catch (Exception e){

        }

        return null;
    }

    /**
     *  增加拍卖信息
     */
    @Override
    public void addSell(int id) {
        String sql = "update transfer_info set status = 1 , state = '是'  where id = ?";
        template.update(sql,id);
    }

    /**
     *  查找推荐选手
     */
    @Override
    public List<Map<String, Object>> findRecommend(String cid,int localId) {
        String sql = "select id,user_name,picture1 from transfer_info where cid = ? and status = 1 and state = '否' and id != ? order by salary desc limit 4";
        return template.queryForList(sql,cid,localId);
    }


    /**
     *   抽取得到 sql语句的通用方法
     */
    private Object[] getRealSqlByClass(String sql,int cid,String user_name){
        StringBuilder sb = new StringBuilder(sql);

        List params = new ArrayList();//条件们
        //2.判断参数是否有值
        if(cid != 0){
            sb.append( " and cid = ? ");

            params.add(cid);//添加？对应的值
        }

        if(user_name != null && user_name.length() > 0){
            sb.append(" and user_name like ? ");

            params.add("%"+user_name+"%");
        }

        return new Object[]{sb,params};
    }
}
