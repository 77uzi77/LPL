package com.lzc.lol.servlet;


import com.lzc.lol.entity.ResultInfo;
import com.lzc.lol.entity.User;
import com.lzc.lol.service.Impl.UserServiceImpl;
import com.lzc.lol.service.UserService;
import com.lzc.lol.utils.LoggedUserSessionContext;
import com.lzc.lol.utils.MD5Util;
import com.lzc.lol.utils.SimpleUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 游客的servlet，集合了游客的功能
 */
@WebServlet("/customerServlet/*")
public class CustomerServlet extends BaseServlet {

    UserService userService = UserServiceImpl.getInstance();

    /**
     * 游客的登陆功能
     */
    public Object login(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
        ResultInfo resultInfo = new ResultInfo();
        //2.获取数据
        //2.1获取用户填写验证码
        String verifycode = request.getParameter("verifycode");
        //3.验证码校验
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//确保验证码一次性
        if(!checkcode_server.equalsIgnoreCase(verifycode)){
            //验证码不正确
            //提示信息
            resultInfo.setStatus(false);
            resultInfo.setMessage("验证码错误");
            //跳转登录页面
            //request.getRequestDispatcher("/login.jsp").forward(request,response);
            //response.sendRedirect(request.getContextPath()+"/login.jsp");
            return resultInfo;
        }

        String username = request.getParameter("username");
        String pre_password = request.getParameter("password");
        String password = MD5Util.md5Jdk(pre_password);

        User user = userService.login(username, password);

        // 用户不存在
        if (user == null){
            resultInfo.setStatus(false);
            resultInfo.setMessage("用户名或密码错误");
        //  用户未激活
        }else if ("0".equals(user.getStatus())){
            resultInfo.setStatus(false);
            resultInfo.setMessage("您尚未激活，请激活！");
        // 管理员未审核
        }else if ("1".equals(user.getStatus())){
            resultInfo.setStatus(false);
            resultInfo.setMessage("请等待管理员审核！");
        // 用户被管理员封禁
        }else if ("-1".equals(user.getStatus())){
            Date date = new Date();
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = dateFormat.format(date);
            // 判断封禁时间是否已过
            if (user.getStartDate().compareTo(currentDate) <= 0){
                if (user.getEndDate().compareTo(currentDate) > 0 ){
                    resultInfo.setStatus(false);
                    resultInfo.setMessage("您已被管理员封禁，请于"+user.getEndDate()+"后登陆");
                }else{
                    // 消除密码，防止可以在页面上得到密码
                    user.setPassword("******");
                    session.setAttribute("user", user);
                    resultInfo.setStatus(true);
                    resultInfo.setMessage("登录成功！");
                }
            }else{
                // 消除密码，防止可以在页面上得到密码
                user.setPassword("******");
                session.setAttribute("user", user);
                resultInfo.setStatus(true);
                resultInfo.setMessage("登录成功！");
            }

            if(resultInfo.isStatus()){
                userService.updateStatus(user.getId());
            }

        }else{
            // 消除密码，防止可以在页面上得到密码
            user.setPassword("******");
            session.setAttribute("user", user);
            resultInfo.setStatus(true);
            resultInfo.setMessage("登录成功！");
        }

        return resultInfo;

    }

    /**
     *    游客的注册功能
     */
    public Object register(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        // 得到表单数据
        Object[] fileItem = SimpleUtils.getFileItem(request);
        ResultInfo resultInfo = (ResultInfo) fileItem[0];
        // 得到数据成功
        if (resultInfo.isStatus()) {
            // 封装数据为map集合
            Map<String,Object> map = (Map<String, Object>)fileItem[1];
            String password = (String) map.get("password");
            map.replace("password",MD5Util.md5Jdk(password));

            User user = new User();
            try {
                // 封装为user对象
                BeanUtils.populate(user, map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            // UserService userService = new UserServiceImpl();
            if (userService.register(user)) {
                resultInfo.setStatus(true);
                resultInfo.setMessage("注册成功！请等待管理员审核！");
            } else {
                resultInfo.setStatus(false);
                resultInfo.setMessage("注册失败！请检查信息");
            }
        }


        return resultInfo;
    }


    /**
     *    游客的激活功能
     */
    public Object activeUser (HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

        //1.获取激活码
        String code = request.getParameter("code");
        if(code != null){
            //2.调用service完成激活
            boolean flag = userService.active(code);

            //3.判断标记
            String msg = null;
            if(flag){
                //激活成功
                msg = "激活成功，请等待管理员审核！";
            }else{
                //激活失败
                msg = "激活失败，请联系管理员!";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);

        }
        return null;
    }

    /**
     *   游客找回密码功能
     */
    public Object sendFindCode(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
        ResultInfo resultInfo = new ResultInfo();
        String email = request.getParameter("userEmail");
        String checkCode = userService.findCode(email);
        if (  checkCode != null ){
            resultInfo.setStatus(true);
            resultInfo.setMessage("发送成功！请前往邮箱查看验证码");
        }else{
            resultInfo.setStatus(false);
            resultInfo.setMessage("发送失败！请检查邮箱！");
        }

        return resultInfo;
    }

    /**
     *   游客修改密码功能
     */
    public Object changePwd(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
        ResultInfo resultInfo = new ResultInfo();
        String email = request.getParameter("email");
        String code = request.getParameter("code");
        if ( !userService.checkCode(email,code) ){
            resultInfo.setStatus(false);
            resultInfo.setMessage("修改密码失败！请检查信息！");
        }else{
            String password = request.getParameter("newPassword");
            userService.changePwd(email,password);
            resultInfo.setStatus(true);
            resultInfo.setMessage("修改密码成功！");
        }

        return resultInfo;
    }


}

