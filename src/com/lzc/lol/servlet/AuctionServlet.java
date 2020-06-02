package com.lzc.lol.servlet;

import com.lzc.lol.entity.Auction;
import com.lzc.lol.entity.ResultInfo;
import com.lzc.lol.service.AuctionService;
import com.lzc.lol.service.Impl.AuctionServiceImpl;
import com.lzc.lol.service.Impl.TeamServiceImpl;
import com.lzc.lol.service.Impl.TransferInfoServiceImpl;
import com.lzc.lol.service.Impl.UserServiceImpl;
import com.lzc.lol.service.TeamService;
import com.lzc.lol.service.TransferInfoService;
import com.lzc.lol.service.UserService;
import org.omg.PortableInterceptor.INACTIVE;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/auctionServlet/*")
public class AuctionServlet extends BaseServlet{

    private TransferInfoService transferInfoService = TransferInfoServiceImpl.getInstance();
    private AuctionService auctionService = AuctionServiceImpl.getInstance();
    private TeamService teamService = TeamServiceImpl.getInstance();
    private UserService userService = UserServiceImpl.getInstance();

    /**
     *   增加拍卖功能
     */
    public Object addSell(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ResultInfo resultInfo = new ResultInfo();

        // 获得参数
        String str_id = request.getParameter("id");
        String sellMoney = request.getParameter("sellMoney");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        int id = Integer.parseInt(str_id);


        transferInfoService.addSell(id);
        auctionService.addSell(id,sellMoney,startDate,endDate);
        resultInfo.setStatus(true);
        resultInfo.setMessage("设置拍卖成功！");

        return resultInfo;
    }

    /**
     *   得到拍卖信息
     */
    public Object getMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo resultInfo = new ResultInfo();
        String id = request.getParameter("id");

        Auction auction = auctionService.getMessage(Integer.parseInt(id));
        resultInfo.setData(auction);
        resultInfo.setStatus(true);
        return resultInfo;
    }

    /**
     *   战队管理员加价，修改拍卖信息
     */
    public Object reviseOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ResultInfo resultInfo = new ResultInfo();
        String money = request.getParameter("money");
        String user_name = request.getParameter("user_name");
        String id = request.getParameter("id");

        auctionService.reviseOne(Integer.parseInt(id),money,user_name);
        resultInfo.setStatus(true);
        resultInfo.setMessage("加价成功！");

        return resultInfo;
    }

    /**
     *   拍卖结束  删除拍卖信息
     */
    public Object delMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String str_id = request.getParameter("id");
        int id = Integer.parseInt(str_id);
        // 得到拍卖信息
        Auction auction = auctionService.getMessage(id);
        String team_name = auction.getMax_user();
        // 判断是否被卖出
        if (! "-1".equals(team_name)){
            String user_name = transferInfoService.selectUser(id);

            int teamId = userService.findId(team_name);
            teamService.addPlayer(user_name,String.valueOf(teamId));
        }
        // 删除拍卖信息
        transferInfoService.deleteOne(id);
        auctionService.delOne(auction.getId());

        return null;
    }

}
