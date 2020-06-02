package com.lzc.lol.dao;

import java.util.List;
import java.util.Map;

/**
 *  战队 数据库相关 接口
 */
public interface TeamDao {

    /**
     *  查找用户
     */
    List<Map<String, Object>> findUser(int id);

    /**
     *  增加战队成员
     */
    int addPlayer(int userId,int teamId);

    /**
     *  删除战队成员
     */
    void deleteOne(int user_id);

}
