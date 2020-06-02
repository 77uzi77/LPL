package com.lzc.lol.dao;

import com.lzc.lol.entity.User;

import java.util.List;
import java.util.Map;

/**
 *  用户 数据库 实现接口
 */
public interface UserDao {

	/**
	 * 注册用户
	 */
	User findByUsername(String userName);

	/**
	 *  增加用户
	 */
	void addUser(User user);

	/**
	 *  用户 查找验证码
	 */
	User findByCode(String code);

	/**
	 *  更新用户状态
	 */
	void updateStatus(int id,String status);

	/**
	 * 登录验证
	 */
	User login(String username, String password);

	/**
	 *  通过邮箱查找用户
	 */
    Integer findByEmail(String email);

	/**
	 *  用户 修改密码
	 */
	void changePwd(String email, String password);

	/**
	 *  用户 修改信息
	 */
    int revise(String type, String data,String userName);

	/**
	 *  查找用户总数
	 */
    int findTotalCount(Map<String, String[]> condition,String type);

	/**
	 *  用户 分页查找信息
	 */
	List<User> findByPage(int start, int pageSize, Map<String, String[]> condition,String type);

	/**
	 *  通过用户申请
	 */
    void passOne(int parseInt);

	/**
	 *  用户 业务 实现接口
	 */
	Map<String, Object> findIcon(int id);

	/**
	 *  删除用户
	 */
	int deleteOne(int id);

	/**
	 *  通过id查找用户信息
	 */
	User findById(int id);

	/**
	 *  查找成员
	 */
    User findMember(int id);

	/**
	 *  查找选手id
	 */
    int findPlayerId(String userName);

	/**
	 *  封禁用户
	 */
	void banUser(String startTime, String endTime, int parseInt);

	/**
	 *  通过用户名查找用户id
	 */
	int findUserId(String user_name);

}