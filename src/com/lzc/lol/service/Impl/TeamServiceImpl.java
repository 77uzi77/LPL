package com.lzc.lol.service.Impl;

import com.lzc.lol.dao.Impl.TeamDaoImpl;
import com.lzc.lol.dao.Impl.UserDaoImpl;
import com.lzc.lol.dao.TeamDao;
import com.lzc.lol.dao.UserDao;
import com.lzc.lol.entity.User;
import com.lzc.lol.service.TeamService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *  战队 业务 实现类
 */
public class TeamServiceImpl implements TeamService {

    private TeamDao teamDao = TeamDaoImpl.getInstance();
    private UserDao userDao = UserDaoImpl.getInstance();

    /**
     *   设置单例  饿汉式
     */
    //类内部实例化
    private static final TeamServiceImpl instance = new TeamServiceImpl();

    //构造器私有化，防止new对象
    private TeamServiceImpl(){

    }

    //对外提供公有方法调用
    public static TeamServiceImpl getInstance(){
        return instance;
    }
    /**
     *  查找战队
     */
    @Override
    public ArrayList<User> findTeam(int id) {
        List<Map<String, Object>> user = teamDao.findUser(id);
        ArrayList<User> team = new ArrayList<>();

        for (int i = 0;i < user.size();i++){
            team.add(userDao.findMember((Integer)user.get(i).get("user_id")));
        }

        return team;
    }


    /**
     *  增加战队成员
     */
    @Override
    public int addPlayer(String userName,String teamId) {
        int userId = userDao.findPlayerId(userName);

        return teamDao.addPlayer(userId,Integer.parseInt(teamId));
    }

}
