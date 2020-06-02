package com.lzc.lol.servlet;

import com.lzc.lol.entity.PageBean;
import com.lzc.lol.entity.Report;
import com.lzc.lol.entity.ResultInfo;
import com.lzc.lol.entity.TransferInfo;
import com.lzc.lol.service.Impl.ReportServiceImpl;
import com.lzc.lol.service.ReportService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 *   集合 战队管理员 举报用户相关 功能
 */
@WebServlet("/reportServlet/*")
public class ReportServlet extends BaseServlet {

    private ReportService reportService = ReportServiceImpl.getInstance();

    /**
     *   战队管理员 举报 功能
     */
    public Object addReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo resultInfo = new ResultInfo();
        String user_name = request.getParameter("user_name");
        String report_name = request.getParameter("report_name");
        String content = request.getParameter("content");

        reportService.addReport(user_name,report_name,content);
        resultInfo.setStatus(true);
        resultInfo.setMessage("举报成功！");

        return resultInfo;
    }

    /**
     *   分页 查询 举报信息
     */
    public Object pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取条件查询参数
        Map<String, String[]> condition = request.getParameterMap();

        //调用service查询
        PageBean<Report> pb = reportService.findByPage(request,condition);



        //将PageBean存入request
        request.setAttribute("pb",pb);
        request.setAttribute("condition",condition);//将查询条件存入request

        //4.转发
        request.getRequestDispatcher("/handleReport.jsp").forward(request,response);

        return null;
    }

    /**
     *   管理 删除举报 信息
     */
    public Object refuseOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String str_id = request.getParameter("id");


       reportService.deleteOne(Integer.parseInt(str_id));

        //3.跳转到查询所有Servlet
        response.sendRedirect(request.getContextPath()+"/reportServlet/pageQuery");

        return null;
    }

    /**
     *   管理员 将举报信息 设为已读
     */
    public Object passOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        reportService.passOne(id);
//        if (teamService.findTeam(id))
//        reportService.findUserName(id);
        //3.跳转到查询所有Servlet
        response.sendRedirect(request.getContextPath()+"/reportServlet/pageQuery");

        return null;
    }

    /**
     *   管理员 批量 删除举报信息
     */
    public Object delSelected(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.获取所有id
        String[] ids = request.getParameterValues("id");
        //2.调用service删除

        reportService.delSelected(ids);

        //3.跳转查询所有Servlet
        response.sendRedirect(request.getContextPath()+"/reportServlet/pageQuery");

        return null;
    }
}
