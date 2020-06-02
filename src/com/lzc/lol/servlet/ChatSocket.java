package com.lzc.lol.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import net.sf.json.JSONObject;


/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/chatSocket")

public class ChatSocket {
    // 日期格式化
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private  static  List<ChatSocket>  sockets=new Vector<ChatSocket>();

    private  static  List<String>   nameList=new ArrayList<String>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private  Session  session;

    private String userId;

    /**
     * 连接建立成功调用的方法，只要有人连接这个服务，就会打开，执行下面的方法。
     * session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void open(Session session) throws UnsupportedEncodingException{
        //一个session就代表一个通信会话
        System.out.println("sessionid:"+session.getId()+"通道开启了。。。。");

        //把session添加到容器中
        this.session=session;
        sockets.add(this);
        //getQueryString把url中？后面的所有的串儿都取出来
        String queryString = session.getQueryString();
        //获取用户名
        this.userId = URLDecoder.decode(queryString.substring(queryString.indexOf("=")+1), "UTF-8");
//       logger.info("userId:"+userId);
        nameList.add(userId);
        JSONObject message = new JSONObject();
      /*  message.put("alert", userId+"进入聊天室！！");
        message.put("names", nameList);
        broadcast(sockets, message.toString());*/
        System.out.println(userId+"进入聊天室！！");

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void close(Session session){
        //1.清理退出的session
        sockets.remove(this);
        //2.清理列表用户名
        nameList.remove(this.userId);
        //3.更新消息信息
        JSONObject message = new JSONObject();
        message.put("alert", this.userId+"退出聊天室！！");
        message.put("names", nameList);
        //4.广播消息信息
        // broadcast(sockets, message.toString());
        System.out.println(this.userId+"退出聊天室！！");

    }


    /**
     * 发生错误时调用
     */
    @OnError
    public void error(Throwable t) {

    }




    /**
     * 收到客户端消息后调用的方法
     *  msg 客户端发送过来的消息
     *  session 可选的参数
     */
    @OnMessage
    public void message(Session session,String msg){
        //接收消息
        JSONObject message = new JSONObject();
        try {
            String[] split = msg.split("#");
            message.put("sendMsg", split[1]);
            message.put("from", this.userId);
            message.put("date", DATE_FORMAT.format(new Date()));
            broadcast(sockets, message.toString(),split[0]);
        }catch (Exception e){

        }

    }

    /**
     * 广播消息
     *  ss 用户session
     *  msg 广播消息
     */
    public void broadcast(List<ChatSocket>  ss ,String msg,String toId){
        System.out.println("toId:"+toId);
        int index = nameList.indexOf(toId);
        ChatSocket chatSocket = ss.get(index);
        try {
            chatSocket.session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
