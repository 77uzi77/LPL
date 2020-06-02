package com.lzc.lol.dao.Impl;


import com.lzc.lol.dao.UserDao;
import com.lzc.lol.entity.User;
import com.lzc.lol.utils.JDBCUtils;
import com.lzc.lol.utils.SimpleUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 *  用户 数据库 实现类
 */
public class UserDaoImpl implements UserDao {

	private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

	/**
	 *   设置单例  饿汉式
	 */
	//类内部实例化
	private final static UserDaoImpl instance = new UserDaoImpl();


	//构造器私有化，防止new对象
	private UserDaoImpl(){

	}

	//对外提供公有方法调用
	public static UserDaoImpl getInstance(){
		return instance;
	}


	/**
	 * 注册用户
	 */
	@Override
	public User findByUsername(String userName) {
		User user = null;
		try {
			//1.定义sql
			String sql = "select * from user where user_name = ?";
			//2.执行sql
			user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), userName);
		} catch (Exception e) {
			// 如果捕捉到异常，证明用户不存在，返回null
		}

		return user;
	}

	/**
	 *  增加用户
	 */
	@Override
	public void addUser(User user) {
		String sql = "insert into user (user_name,email,password,icon,age,message,last_team,join_time,status,code)" +
				"values (?,?,?,?,?,?,?,?,?,?)";

		//2.执行sql
		template.update(sql,
				user.getUser_name(),
				user.getEmail(),
				user.getPassword(),
				user.getIcon(),
				user.getAge(),
				user.getMessage(),
				user.getLast_team(),
				user.getJoin_time(),
				user.getStatus(),
				user.getCode()
		);
	}

	/**
	 *  用户 查找验证码
	 */
	@Override
	public User findByCode(String code) {
		User user = null;
		try {
			String sql = "select id,email from user where code = ?";

			user = template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),code);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		return user;
	}

	/**
	 *  更新用户状态
	 */
	@Override
	public void updateStatus(int id,String status) {
		String sql = " update user set status = ? where id = ?";
		template.update(sql,status,id);
	}

	/**
	 * 登录验证
	 */
	@Override
	public User login(String username, String password) {

		User user = findByUsername(username);

		if ( user != null && !password.equals(user.getPassword()) ){
			user = null;
		}

		return user;
	}

	/**
	 *  通过邮箱查找用户
	 */
	@Override
	public Integer findByEmail(String email) {

		String sql = "select id from user where email = ?";
		int result = -1;
		try {
			result = (Integer) template.queryForMap(sql,email).get("id");
		}catch (Exception e){

		}

		return result;
	}

	/**
	 *  用户 修改密码
	 */
	@Override
	public void changePwd(String email, String password) {
		String sql = "update user set password = ? where email = ?";
		template.update(sql,password,email);
	}

	/**
	 *  用户 修改信息
	 */
	@Override
	public int revise(String type, String data,String userName) {
		String sql = "update user set "+type+" = ?,status = '1' where user_name = ?";
		return template.update(sql,data,userName);
	}

	/**
	 *  查找用户总数
	 */
	@Override
	public int findTotalCount(Map<String, String[]> condition,String type) {
		//定义模板初始化sql
		String sql = "select count(*) from user where status = " + type;
		// 调用工具类拼接sql
		Object[] realSql = SimpleUtils.getRealSql(sql, condition);
		StringBuilder sb = (StringBuilder) realSql[0];
		// 得到参数值
		List<Object> params = (List<Object>) realSql[1];



		return template.queryForObject(sb.toString(),Integer.class,params.toArray());
	}

	/**
	 *  用户 分页查找信息
	 */
	@Override
	public List<User> findByPage(int start, int pageSize, Map<String, String[]> condition,String type) {
		//定义模板初始化sql
		String sql = "select * from user  where status =  " + type;
		// 调用工具类拼接sql
		Object[] realSql = SimpleUtils.getRealSql(sql, condition);
		StringBuilder sb = (StringBuilder) realSql[0];
		// 得到参数值
		List<Object> params = (List<Object>) realSql[1];

		System.out.println(sb.toString());
		System.out.println(params);

		//添加分页查询
		sb.append(" limit ?,? ");
		//添加分页查询参数值
		params.add(start);
		params.add(pageSize);
		sql = sb.toString();


		return template.query(sql,new BeanPropertyRowMapper<User>(User.class),params.toArray());
	}

	/**
	 *  通过用户申请
	 */
	@Override
	public void passOne(int parseInt) {
		String sql = "update user set status = 2 where id = ?";
		template.update(sql,parseInt);
	}

	/**
	 *  用户 业务 实现接口
	 */
	@Override
	public Map<String, Object> findIcon(int id) {
		String sql = "select icon from user where id = ?";
		return template.queryForMap(sql,id);
	}

	/**
	 *  删除用户
	 */
	@Override
	public int deleteOne(int id) {
		String sql = "delete from user where id = ?";

		return template.update(sql,id);
	}

	/**
	 *  通过id查找用户信息
	 */
	@Override
	public User findById(int id) {
		String sql = "select user_name,icon from user where id = ?";

		return template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),id);
	}

	/**
	 *  查找成员
	 */
	@Override
	public User findMember(int id) {
		String sql = "select user_name,email,icon,age,message,last_team,join_time from user where id = ?";
		return template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),id);
	}

	/**
	 *  查找选手id
	 */
	@Override
	public int findPlayerId(String userName) {
		String sql = "select id from user where user_name = ?";
		return (Integer) template.queryForMap(sql,userName).get("id");
	}


	/**
	 *  封禁用户
	 */
	@Override
	public void banUser(String startTime, String endTime, int id) {
		String sql = "update user set status = -1,startDate = ?,endDate = ? where id = ? ";
		template.update(sql,startTime,endTime,id);
	}

	/**
	 *  通过用户名查找用户id
	 */
	@Override
	public int findUserId(String user_name) {
		String sql = "select id from user where user_name = ?";
		return (Integer) template.queryForMap(sql,user_name).get("id");
	}

}
