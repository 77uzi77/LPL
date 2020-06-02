package com.lzc.lol.servlet;

import com.lzc.lol.entity.PageBean;
import com.lzc.lol.entity.ResultInfo;
import com.lzc.lol.entity.TransferInfo;
import com.lzc.lol.entity.User;
import com.lzc.lol.service.Impl.TeamServiceImpl;
import com.lzc.lol.service.Impl.TransferInfoServiceImpl;
import com.lzc.lol.service.Impl.UserServiceImpl;
import com.lzc.lol.service.TeamService;
import com.lzc.lol.service.TransferInfoService;
import com.lzc.lol.service.UserService;
import com.lzc.lol.utils.LoggedUserSessionContext;
import com.lzc.lol.utils.SimpleUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 *   集合用户的功能
 */
@WebServlet("/userServlet/*")
public class UserServlet extends BaseServlet {

    private TransferInfoService transferInfoService = TransferInfoServiceImpl.getInstance();
    private TeamService teamService = TeamServiceImpl.getInstance();
    private UserService userService =  UserServiceImpl.getInstance();

    /**
     *    查找单个用户
     */
    public Object findOne(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException{
        Object user = request.getSession().getAttribute("user");

        return user;
    }

    /**
     *   用户退出功能
     */
    public Object exit(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException{
        // 将用户的sessionId 从 map中移除
        HttpSession session =  request.getSession();
        User user = (User)session.getAttribute("user");
        System.out.println(user.getId());
        LoggedUserSessionContext.remove(user.getId());
        session.invalidate();
        System.out.println(LoggedUserSessionContext.sessionMap);


        // 退出后转发到登录页面
        response.sendRedirect(request.getContextPath()+"/login.jsp");


        return null;
    }

    /**
     *   用户提交/修改转会信息功能
     */
    public Object transfer(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException{
        // 得到 表单数据
        Object[] fileItem = SimpleUtils.getFileItem(request);
        ResultInfo resultInfo = (ResultInfo) fileItem[0];
        // 得到 表单数据成功
        if (resultInfo.isStatus()){
            Map<String,Object> map = (Map<String, Object>)fileItem[1];

            String name = request.getParameter("user_name");

            if (name == null || name.equals("")){
                User user = (User) request.getSession().getAttribute("user");
                name = user.getUser_name();
            }

            map.put("user_name",name);
            TransferInfo transferInfo = new TransferInfo();
            try {
                // 封装为 转会信息表 对象
                BeanUtils.populate(transferInfo,map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            // 增加转会信息
            transferInfoService.addOne(transferInfo);

            resultInfo.setStatus(true);
            resultInfo.setMessage("提交成功！请等待管理员审核！");
        }


        return resultInfo;
    }

    /**
     *   修改用户信息功能
     */
    public Object reviseMessage(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException{

        User user = (User) request.getSession().getAttribute("user");
        String userName = user.getUser_name();
        // 得到 表单数据
        Object[] fileItem = SimpleUtils.getFileItem(request);
        ResultInfo resultInfo = (ResultInfo) fileItem[0];
        String type = "";
        String data = "";
        // 得到 表单数据成功
        if (resultInfo.isStatus()){
            Map<String,Object> map = (Map<String, Object>)fileItem[1];
            for ( String s : map.keySet()){
                type = s;
                data = (String) map.get(s);
            }
            if (userService.revise(type,data,userName) != 0){

                resultInfo.setMessage("修改成功！请等待管理员审核！");
            }else{
                resultInfo.setStatus(false);
                resultInfo.setMessage("修改失败！请检查信息");
            }
        }

        return resultInfo;
    }


    /**
     *   分页查询用户信息
     */
    public Object pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接受参数
        String type = request.getParameter("type");
        System.out.println(type);;

        //获取条件查询参数
        Map<String, String[]> condition = request.getParameterMap();


        //调用service查询
        PageBean<User> pb = userService.findUserByPage(request,condition,type);

        //将PageBean存入request
        request.setAttribute("pb",pb);
        request.setAttribute("condition",condition);//将查询条件存入request
        //根据用户的身份转发到不同页面
        if ("1".equals(type)){
            // 用户未审核，转发到审核页面
            request.getRequestDispatcher("/examineRegister.jsp").forward(request,response);
        }else{
            // 用户已审核，转发到封禁页面
            request.getRequestDispatcher("/banUser.jsp").forward(request,response);
        }


        return null;
    }

    /**
     *   管理员 通过用户注册 的功能
     */
    public Object passOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        userService.passOne(id);

        //3.跳转到查询所有Servlet
        response.sendRedirect(request.getContextPath()+"/userServlet/pageQuery?type=1");

        return null;
    }

    /**
     *   管理员 拒绝用户注册 的功能
     */
    public Object deleteOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String str_id = request.getParameter("id");
        int id = Integer.parseInt(str_id);
        String icon = userService.findIcon(id);
        if (userService.deleteOne(id) != 0){
            File file = new File("C:/Users/啊柒哟/IdeaProjects/CAT2/web/images/"+icon);
            if (file.isFile() && file.exists()){
                file.delete();
            }
        }

        //3.跳转到查询所有Servlet
        response.sendRedirect(request.getContextPath()+"/userServlet/pageQuery?type=1");

        return null;
    }


    /**
     *   管理员 批量删除 用户功能
     */
    public Object delSelected(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.获取所有id
        String[] ids = request.getParameterValues("uid");
        //2.调用service删除
//        UserService service = new UserServiceImpl();
        userService.delSelectedUser(ids);

        //3.跳转查询所有Servlet
        response.sendRedirect(request.getContextPath()+"/userServlet/pageQuery?type=1");

        return null;
    }

    /**
     *   管理员  封禁用户 功能
     */
    public Object banUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ResultInfo resultInfo = new ResultInfo();
        // 得到相关参数
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String id = request.getParameter("id");

        userService.banUser(startTime,endTime,Integer.parseInt(id));
        resultInfo.setStatus(true);
        resultInfo.setMessage("封禁成功！");
        return resultInfo;
    }
}
