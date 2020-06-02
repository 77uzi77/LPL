package com.lzc.lol.service.Impl;

import com.lzc.lol.dao.CommentDao;
import com.lzc.lol.dao.Impl.CommentDaoImpl;
import com.lzc.lol.entity.Comment;
import com.lzc.lol.service.CommentService;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 *   评论 业务实现类
 */
public class CommentServiceImpl implements CommentService {

    private CommentDao dao = CommentDaoImpl.getInstance();

    /**
     *   设置单例  饿汉式
     */
    //类内部实例化
    private static final CommentServiceImpl instance = new CommentServiceImpl();

    //构造器私有化，防止new对象
    private CommentServiceImpl(){

    }

    //对外提供公有方法调用
    public static CommentServiceImpl getInstance(){
        return instance;
    }



    /**
     *  查询评论信息
     */
    @Override
    public ArrayList<Comment> SelectCommentMessage(int gid) {
        return dao.SelectUserMessage(gid);
    }

    /**
     * 增加评论
     * wid-评论者id（写评论的人）
     * gid-被评论者id（收评论的人）
     * content-评论内容
     */
    
    @Override
    public int addmessage(int wid,int gid,String content) {

        return dao.addmessage(wid, gid, content);

    }
    
    /**
     * 根据msgid查询回复信息
     * msgid -评论编号
     */

    @Override
    public SqlRowSet selectReplyByMsgId(int msgid) {
        return dao.selectReplyByMsgId(msgid);
    }
    
    /**
     * 添加评论回复功能
     * msgid-评论编号
     * userid 回复者id
     * content-回复内容
     * 回复拼接格式：旧的回复内容+id<content<time>
     */
    @Override
    public int updatetmessagereply(int msgid,int userid,String content) {
        return dao.updatetmessagereply(msgid, userid, content);
    }
}
