package com.lzc.lol.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lzc.lol.entity.User;
import com.lzc.lol.utils.LoggedUserSessionContext;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *        用于对servlet共用的方法进行抽取
 *
 */
public class BaseServlet extends HttpServlet {

    /**
     *    用于对用户请求的方法进行分发与执行
     *
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 设置编码格式为UTF-8
        request.setCharacterEncoding("utf-8");
        // 获取请求的路径
        String uri = request.getRequestURI();
        // 获取请求的方法名称
        String requestName = uri.substring(uri.lastIndexOf('/')+ 1);

        System.out.println(uri);
        System.out.println(requestName);

        try {
            //  获取方法的对象
            Method method = this.getClass().getMethod(requestName, HttpServletRequest.class, HttpServletResponse.class);
            // 执行这个方法
            Object invokeResponse = method.invoke(this, request, response);

            // 给用户设置唯一的id标识，判断用户是否在线
            if ( !requestName.equals("exit")){
                User user = (User)request.getSession().getAttribute("user");
                if (user != null){
                    HttpSession loginSession = LoggedUserSessionContext.getSession(user.getId());
                    if(loginSession != null){
                        //使用之前登陆过的 id
                        // 设置session过期时间
                        loginSession.setMaxInactiveInterval(60 * 30);
                    }else{
                        HttpSession session = request.getSession();
                        session.setAttribute("user", user);
                        // 设置session过期时间
                        session.setMaxInactiveInterval(60 * 30);
                        LoggedUserSessionContext.putIfAbsent(user.getId(), session);
                    }
                    System.out.println(LoggedUserSessionContext.sessionMap.size());
                    System.out.println(LoggedUserSessionContext.sessionMap);
                }
            }



            if (invokeResponse != null){
                ObjectMapper objectMapper = new ObjectMapper();
                // 设置编码格式
                //if(!StringUtils.equals(requestName, "activeUser")){
                    response.setContentType("application/json;charset=utf-8");
                //}
                // 将数据传回客户端
                objectMapper.writeValue(response.getOutputStream(), invokeResponse);
            }
        }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
