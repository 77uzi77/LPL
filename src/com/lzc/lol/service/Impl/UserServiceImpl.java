package com.lzc.lol.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzc.lol.dao.Impl.UserDaoImpl;
import com.lzc.lol.dao.UserDao;
import com.lzc.lol.entity.PageBean;
import com.lzc.lol.entity.User;
import com.lzc.lol.service.UserService;
import com.lzc.lol.utils.MD5Util;
import com.lzc.lol.utils.MailUtils;
import com.lzc.lol.utils.SimpleUtils;
import com.lzc.lol.utils.UuidUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 *  用户 业务 实现类
 */
public class UserServiceImpl implements UserService {

    private UserDaoImpl userDao = UserDaoImpl.getInstance();

    private String correct_code = null;


    /**
     *   设置单例  饿汉式
     */
    //类内部实例化
    private static final UserService instance = new UserServiceImpl();

    //构造器私有化，防止new对象
    private UserServiceImpl(){

    }

    //对外提供公有方法调用
    public static UserService getInstance(){
        return instance;
    }

    /**
     *  用户 注册
     */
    @Override
    public boolean register(User user) {

        //根据用户名查询用户对象
        User u = userDao.findByUsername(user.getUser_name());
        int emailExist = userDao.findByEmail(user.getEmail());
        //判断u是否为null
        if(u != null || emailExist != -1){
            //用户名或邮箱存在，注册失败
            return false;
        }

        //2.保存用户信息
        //2.1设置激活码，唯一字符串
        user.setCode(UuidUtil.getUuid());
        //2.2设置激活状态
        user.setStatus("0");
        userDao.addUser(user);

        //3.激活邮件发送，邮件正文？

        String content="<a href='http://127.0.0.1:8080/CAT2_war_exploded/customerServlet/activeUser?code="+user.getCode()+"'>【点击激活账号】</a>";

        MailUtils.sendMail(user.getEmail(),content,"激活邮件");

        return true;
    }

    /**
     *  用户 激活
     */
    @Override
    public boolean active(String code) {
        //1.根据激活码查询用户对象
        User user = userDao.findByCode(code);
        if(user != null){
            //2.调用dao的修改激活状态的方法
            userDao.updateStatus(user.getId(),"1");
            return true;
        }else{
            return false;
        }
    }


    /**
     *  用户 登录
     */
    @Override
    public User login(String userName, String passWod) {
        return userDao.login(userName, passWod);
    }

    /**
     *  用户 查找验证码
     */
    @Override
    public String findCode(String email) {

        if ( userDao.findByEmail(email) == -1 ){
            return null;
        }

        String checkCode = SimpleUtils.getCheckCode();
        System.out.println(checkCode);
        String content="您的验证码为：" + checkCode;

        MailUtils.sendMail(email,content,"验证邮件");
        correct_code = checkCode;

        return checkCode;
    }

    /**
     *  用户 确认验证码
     */
    @Override
    public boolean checkCode(String email, String code) {

        if (userDao.findByEmail(email) == -1){
            return false;
        }else{
            if ( correct_code != null && correct_code.equals(code) ){
                correct_code = null;
                return true;
            }else{
                return false;
            }
        }
    }

    /**
     *  用户 修改密码
     */
    @Override
    public void changePwd(String email,String password) {
        userDao.changePwd(email,MD5Util.md5Jdk(password));
    }

    /**
     *  用户 修改信息
     */
    @Override
    public int revise(String type, String data,String userName) {
        return userDao.revise(type,data,userName);
    }

    /**
     *  用户 分页查找信息
     */
    @Override
    public PageBean<User> findUserByPage(HttpServletRequest request, Map<String, String[]> condition, String type) {

       //调用dao查询总记录数
        int totalCount = userDao.findTotalCount(condition,type);
        // 得到分页条件参数
        Object[] pageParams = SimpleUtils.getPageParams(request, totalCount);
        PageBean pb = (PageBean) pageParams[0];
        int start = (int) pageParams[1];
        // 调用dao查找数据
        List<User> list = userDao.findByPage(start,pb.getPageSize(),condition,type);
        pb.setList(list);


        return pb;
    }

    /**
     *  通过用户申请
     */
    @Override
    public void passOne(String id) {
        userDao.passOne(Integer.parseInt(id));
    }

    /**
     *  查找头像
     */
    @Override
    public String findIcon(int id) {
        Map<String, Object> icon = userDao.findIcon(id);

        return (String)icon.get("icon");
    }

    /**
     *  删除用户
     */
    @Override
    public int deleteOne(int id) {
        return userDao.deleteOne(id);
    }

    /**
     *  批量删除用户
     */
    @Override
    public void delSelectedUser(String[] ids) {
        if(ids != null && ids.length > 0){
            //1.遍历数组
            for (String id : ids) {
                //2.调用dao删除
                userDao.deleteOne(Integer.parseInt(id));
            }
        }
    }

    /**
     *  封禁用户
     */
    @Override
    public void banUser(String startTime, String endTime, int parseInt) {
        userDao.banUser(startTime,endTime,parseInt);
    }

    /**
     *  更新用户状态
     */
    @Override
    public void updateStatus(int id) {
        userDao.updateStatus(id,"2");
    }

    /**
     *
     *  查找用户id
     */
    @Override
    public int findId(String user_name) {
        return userDao.findUserId(user_name);
    }


    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
    }
}
