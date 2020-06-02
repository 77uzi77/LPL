package com.lzc.lol.servlet;

import com.lzc.lol.entity.User;
import com.lzc.lol.service.Impl.TeamServiceImpl;
import com.lzc.lol.service.TeamService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 *    查找  战队成员 功能
 */
@WebServlet("/findMember")
public class FindMemberServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 根据 队伍id 查找战队
        String str_id = request.getParameter("team_id");
        TeamService teamService = TeamServiceImpl.getInstance();
        ArrayList<User> team = teamService.findTeam(Integer.parseInt(str_id));

        request.setAttribute("team",team);
        request.getRequestDispatcher("memberManage.jsp").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
