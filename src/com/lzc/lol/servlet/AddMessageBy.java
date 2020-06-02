package com.lzc.lol.servlet;


import com.lzc.lol.entity.Comment;
import com.lzc.lol.entity.User;
import com.lzc.lol.service.CommentService;
import com.lzc.lol.service.Impl.CommentServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * 评论功能
 */
@WebServlet("/addMessageBy")
public class AddMessageBy extends HttpServlet {

    private static final long serialVersionUID = 1L;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        HttpSession session=request.getSession();
        //获取当前用户信息
        User user = new User();
        user = (User)session.getAttribute("user");
        if(session.getAttribute("user")==null) {
            response.sendRedirect("login.html");
            return;
        }
        int wid = 0,gid = 0;
        wid = user.getId();//当前用户id
        //接收数据
        gid = Integer.parseInt(request.getParameter("gid"));//接收评论者的id
        String content=request.getParameter("messagecontent");//评论内容
        //业务
        CommentService service = CommentServiceImpl.getInstance();
        int end = service.addmessage(wid, gid, content);
        //查询该用户所有评论信息
        ArrayList<Comment> message = new ArrayList<>();
        message = service.SelectCommentMessage(wid);
        request.setAttribute("messageinfo", message);
        if(end==1){
            out.print("<script>");
            out.print("alert('评论发布成功！');");
            out.print("window.location.href='message.jsp';");
            out.print("</script>");
        }else{
            out.print("<script>");
            out.print("alert('Error！评论失败！');");
            out.print("window.location.href='message.jsp';");
            out.print("</script>");
        }
        request.getRequestDispatcher("selectMessage?gid="+gid).forward(request, response);
    }

}
