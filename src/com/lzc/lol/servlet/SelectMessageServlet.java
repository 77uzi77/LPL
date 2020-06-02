package com.lzc.lol.servlet;

import com.lzc.lol.entity.Comment;
import com.lzc.lol.service.CommentService;
import com.lzc.lol.service.Impl.CommentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 *  查询该信息下所有评论
 */
@WebServlet("/selectMessage")
public class SelectMessageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");

        //接收数据
        int gid = Integer.parseInt(request.getParameter("gid"));
        //查询该信息 所有评论
        CommentService service = CommentServiceImpl.getInstance();
        ArrayList<Comment> message = new ArrayList<>();
        message = service.SelectCommentMessage(gid);

        request.setAttribute("gid", gid);
        request.setAttribute("messageinfo", message);
        // 转发到详细信息页面
        request.getRequestDispatcher("messageDetail.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
