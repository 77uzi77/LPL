package com.lzc.lol.service;

import com.lzc.lol.entity.PageBean;
import com.lzc.lol.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 *  用户 业务 实现接口
 */
public interface UserService {

    /**
     *  用户 注册
     */
    boolean register(User user);

    /**
     *  用户 激活
     */
    boolean active(String code);

    /**
     *  用户 登录
     */
    User login(String userName, String passWod);

    /**
     *  用户 查找验证码
     */
    String findCode(String email);

    /**
     *  用户 确认验证码
     */
    boolean checkCode(String email, String code);

    /**
     *  用户 修改密码
     */
    void changePwd(String email,String password);

    /**
     *  用户 修改信息
     */
    int revise(String type, String data,String userName);

    /**
     *  用户 分页查找信息
     */
    PageBean<User> findUserByPage(HttpServletRequest request, Map<String, String[]> condition, String type);

    /**
     *  通过用户申请
     */
    void passOne(String id);

    /**
     *  用户 业务 实现接口
     */
    String findIcon(int id);

    /**
     *  删除用户
     */
    int deleteOne(int id);

    /**
     *  批量删除用户
     */
    void delSelectedUser(String[] ids);

    /**
     *  封禁用户
     */
    void banUser(String startTime, String endTime, int parseInt);

    /**
     *  更新用户状态
     */
    void updateStatus(int id);


    int findId(String user_name);
}
