package com.lzc.lol.dao;

import com.lzc.lol.entity.Comment;
import com.lzc.lol.entity.Reply;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


/**
 *   评论 数据库相关 接口
 */
public interface CommentDao {

    /**
     * 查询该信息所有评论记录，并按时间排序
     */
    ArrayList<Comment> SelectUserMessage(int gid);


    /**
     * 查询评论回复信息
     * 返回 Reply回复集合
     */
    ArrayList<Reply> selectUserReplyMessage(String content);


    /**
     * 增加评论
     *
     *  wid-评论者id（写评论的人）
     *  gid-被评论者id（收评论的人）
     *  content-评论内容
     */
    int addmessage(int wid, int gid, String content) ;


    /**
     * 根据msgid查询回复信息
     * msgid -评论编号
     */
    SqlRowSet selectReplyByMsgId(int msgid);

    /**
     * 添加评论回复功能
     * msgid-评论编号
     * userid -回复者id
     * content-回复内容 回复拼接格式：旧的回复内容+id<content<time>
     */
    int updatetmessagereply(int msgid, int userid, String content);
}
