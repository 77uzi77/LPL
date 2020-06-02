package com.lzc.lol.servlet;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lzc.lol.entity.Comment;
import com.lzc.lol.entity.User;
import com.lzc.lol.service.CommentService;
import com.lzc.lol.service.Impl.CommentServiceImpl;

/**
 * 评论回复功能
 */
@WebServlet("/messageUserReply")
public class MessageUserReply extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session=request.getSession();
		//获取当前用户信息
		User user = new User();
		if(session.getAttribute("user")==null) {
			response.sendRedirect("login.jsp");
			return;
		}
		user = (User)session.getAttribute("user");
		int userid = user.getId();
		//接收表单数据
		String content = request.getParameter("replycontent");
		int msgid = Integer.parseInt(request.getParameter("thismessageid"));
		int gid = Integer.parseInt(request.getParameter("gid"));
		//DAO
		CommentService service = CommentServiceImpl.getInstance();
		service.updatetmessagereply(msgid, userid, content);
		//查询该用户所有留言信息
		ArrayList<Comment> message = new ArrayList<>();
		message = service.SelectCommentMessage(gid);
		request.setAttribute("messageinfo", message);
		request.setAttribute("gid", gid);
		request.getRequestDispatcher("messageDetail.jsp").forward(request, response);
	}

}
