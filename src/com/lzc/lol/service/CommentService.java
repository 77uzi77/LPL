package com.lzc.lol.service;

import com.lzc.lol.entity.Comment;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;

/**
 *   评论 业务实现接口
 */
public interface CommentService {

    /**
     *  查询评论信息
     */
    ArrayList<Comment> SelectCommentMessage(int gid);

    /**
     *  增加评论
     */
    public int addmessage(int wid,int gid,String content);

    /**
     *  查询回复信息
     */
    public SqlRowSet selectReplyByMsgId(int msgid);

    /**
     *  更新评论回复
     */
    public int updatetmessagereply(int msgid,int userid,String content);
}
