package com.lzc.lol.servlet;

import com.lzc.lol.entity.PageBean;
import com.lzc.lol.entity.ResultInfo;
import com.lzc.lol.entity.TransferInfo;
import com.lzc.lol.service.Impl.TeamServiceImpl;
import com.lzc.lol.service.Impl.TransferInfoServiceImpl;
import com.lzc.lol.service.TeamService;
import com.lzc.lol.service.TransferInfoService;
import com.lzc.lol.utils.SimpleUtils;
import com.lzc.lol.utils.WordUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *   集合转会相关的功能
 */
@WebServlet("/transferInfo/*")
public class TransferInfoServlet extends  BaseServlet{

    private TransferInfoService service = TransferInfoServiceImpl.getInstance();
    private TeamService teamService = TeamServiceImpl.getInstance();

    /**
     *  分类 分页 查询转会信息
     */
    public Object pageQueryByClass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接受参数
        String cidStr = request.getParameter("cid");
        //接受rname名称
       String rname = request.getParameter("rname");
        int cid = 0;//类别id
        //2.处理参数
        if (cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)) {
            cid = Integer.parseInt(cidStr);
        }

        // 调用service查询PageBean对象
        PageBean<TransferInfo> pb = service.pageQuery(cid, request, rname);

        //4. 将pageBean对象序列化为json，返回
       return pb;
    }


    /**
     * 根据id查询一个转会详细信息
     */

    public Object findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.接收id
        String id = request.getParameter("id");
        //2.调用service查询route对象
        TransferInfo transferInfo = service.findOne(id);
        //3.转为json写回客户端
        return transferInfo;
    }

    /**
     *   管理员  删除用户转会信息 功能
     */
    public Object deleteOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ResultInfo resultInfo = new ResultInfo();
        String del_num_str = request.getParameter("del_num");
        int del_num = Integer.parseInt(del_num_str);
        String images[] = service.findImages(del_num);

        // 将用户保存在服务器的图片删除
        if (service.deleteOne(del_num) != 0){
            for (int i = 0;i < images.length;i++){
                File file = new File("C:/Users/啊柒哟/IdeaProjects/CAT2/web/images/"+images[i]);
                if (file.isFile() && file.exists()){
                    file.delete();
                }
            }
            resultInfo.setStatus(true);
            resultInfo.setMessage("删除该转会信息成功！");
        }else{
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除该转会信息失败！");
        }


        return resultInfo;
    }

    /**
     *   管理员 分页查看 用户 转会 申请
     */
    public Object pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取条件查询参数
        Map<String, String[]> condition = request.getParameterMap();
        //调用service查询
        PageBean<TransferInfo> pb = service.findByPage(request,condition);
        //将PageBean存入request
        request.setAttribute("pb",pb);
        request.setAttribute("condition",condition);//将查询条件存入request
        //转发到审核页面

        request.getRequestDispatcher("/examineTransfer.jsp").forward(request,response);

        return null;
    }


    /**
     *   管理员 通过 用户转会申请 功能
     */
    public Object passOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
         // 通过用户申请
        service.passOne(id);
        // 判断该转会信息是否为 战队管理员 提供
        service.findUserName(id);
        //跳转到查询所有Servlet
        response.sendRedirect(request.getContextPath()+"/transferInfo/pageQuery");

        return null;
    }


    /**
     *  管理员 拒绝用户申请 功能
     */
    public Object refuseOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String str_id = request.getParameter("id");
        int id = Integer.parseInt(str_id);

        // 将用户保存在服务器的图片删除
        String images[] = service.findImages(id);
        if (service.deleteOne(id) != 0){
            for (int i = 0;i < images.length;i++){
                File file = new File("C:/Users/啊柒哟/IdeaProjects/CAT2/web/images/"+images[i]);
                if (file.isFile() && file.exists()){
                    file.delete();
                }
            }
        }

        //3.跳转到查询所有Servlet
        response.sendRedirect(request.getContextPath()+"/transferInfo/pageQuery");

        return null;
    }

    /**
     *   管理员 批量 拒绝 用户 转会申请 功能
     */
    public Object delSelected(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.获取所有id
        String[] ids = request.getParameterValues("id");
        //2.调用service删除

        service.delSelectedUser(ids);

        //3.跳转查询所有Servlet
        response.sendRedirect(request.getContextPath()+"/transferInfo/pageQuery");

        return null;
    }

    /**
     *   战队管理员 邀请 选手 加入战队 功能
     */
    public Object inviteOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userName = request.getParameter("userName");
        String teamId = request.getParameter("teamId");

        ResultInfo resultInfo = (ResultInfo) deleteOne(request,response);
        if ( resultInfo.isStatus() ){
            if (teamService.addPlayer(userName,teamId) != 0){
                resultInfo.setMessage("邀请成功！");
            }
        }else{
            resultInfo.setMessage("邀请失败！");
        }

        return resultInfo;

    }

    /**
     *   下载 选手转会信息
     */
    public Object download(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TransferInfo transferInfo = (TransferInfo) findOne(request,response);
        Map<String, Object> map = new HashMap<>();
        map.put("userName",transferInfo.getUser_name());
        String position = "";
        switch (transferInfo.getCid()){
            case "1":
                position = "上单";break;
            case "2":
                position = "打野";break;
            case "3":
                position = "中单";break;
            case "4":
                position = "射手";break;
            case "5" :
                position = "辅助";break;
        }
        map.put("cid",position);
        map.put("salary",transferInfo.getSalary());
        map.put("introduction",transferInfo.getIntroduction());
        List<String> images = new ArrayList<>();
        images.add(WordUtils.getImageBase(transferInfo.getPicture1(),request));
        images.add(WordUtils.getImageBase(transferInfo.getPicture2(),request));
        images.add(WordUtils.getImageBase(transferInfo.getPicture3(),request));
        map.put("images",images);

        // 将转会信息 封装为 map集合 根据模板下载
        WordUtils.exportMillCertificateWord(request,response,map);

        return null;
    }

    /**
     *  推荐选手
     */
    public Object recommend(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ResultInfo resultInfo = new ResultInfo();
        String cid = request.getParameter("cid");
        // 排除当前 信息的id
        String localId = request.getParameter("localId");
        List<Map<String, Object>> recommend = service.findRecommend(cid,localId);

        resultInfo.setStatus(true);
        resultInfo.setData(recommend);


        return  resultInfo;
    }

}
