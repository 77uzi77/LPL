package com.lzc.lol.service;

import com.lzc.lol.entity.User;

import java.util.ArrayList;

/**
 *  战队 业务 实现接口
 */
public interface TeamService {

    /**
     *  查找战队
     */
    ArrayList<User> findTeam(int id);

    /**
     *  增加战队成员
     */
    int addPlayer(String userName,String teamId);

//    void delPlayer(int id);
}
