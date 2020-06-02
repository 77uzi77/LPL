package com.lzc.lol.Filter;

import com.lzc.lol.entity.User;
import com.lzc.lol.utils.LoggedUserSessionContext;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *  监听用户 session
 */
@WebListener
public class SessionListener implements HttpSessionListener {

    //HttpServletRequest的getSession()方法，如果当前请求没有对应的session会自动创建session。
    //使用getSession(false)就不会创建session，如果没有当前请求对应的session就返回null.

   
    //新 session 创建 如未知用户浏览
    @Override
    public void sessionCreated(HttpSessionEvent event) {

    }

    //session 销毁 用户下线，退出登录
    @Override
    public void sessionDestroyed(HttpSessionEvent event) throws ClassCastException {
        HttpSession session = event.getSession();
        Object userObj = session.getAttribute("user");
        if(userObj != null){
        	User user = (User)userObj;
        	LoggedUserSessionContext.remove(user.getId());
        }
        
    }

}
