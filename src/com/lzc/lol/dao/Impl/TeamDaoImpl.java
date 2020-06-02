package com.lzc.lol.dao.Impl;

import com.lzc.lol.dao.TeamDao;
import com.lzc.lol.utils.JDBCUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 *  战队 数据库相关 实现类
 */
public class TeamDaoImpl implements TeamDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     *   设置单例  饿汉式
     */
    private static final  TeamDaoImpl instance = new TeamDaoImpl();

    // 私有化构造方法，防止被 new
    private TeamDaoImpl(){

    }

    //  统一得到单例方法
    public static TeamDaoImpl getInstance(){
        return instance;
    }

    /**
     *  查找用户
     */
    @Override
    public List<Map<String, Object>> findUser(int id) {
        String sql = "select user_id from team where team_id = ?";
        return template.queryForList(sql,id);
    }

    /**
     *  增加战队成员
     */
    @Override
    public int addPlayer(int userId,int teamId) {
        String sql = "insert into team (team_id,user_id) values (?,?)";

        return template.update(sql,teamId,userId);
    }

    /**
     *  删除战队成员
     */
    @Override
    public void deleteOne(int user_id) {
        String sql = "delete from team where user_id = ?";
        try {
            template.update(sql,user_id);
        }catch (Exception e){

        }
    }

}
