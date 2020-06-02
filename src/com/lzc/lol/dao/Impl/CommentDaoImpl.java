package com.lzc.lol.dao.Impl;

import com.lzc.lol.dao.CommentDao;
import com.lzc.lol.dao.UserDao;
import com.lzc.lol.entity.Comment;
import com.lzc.lol.entity.Reply;
import com.lzc.lol.entity.User;
import com.lzc.lol.utils.JDBCUtils;
import com.lzc.lol.utils.SimpleUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.rowset.CachedRowSet;
import java.util.ArrayList;

/**
 *   评论 数据库相关 实现类
 */
public class CommentDaoImpl implements CommentDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    private UserDao userDao = UserDaoImpl.getInstance();

    /**
     *   设置单例  饿汉式
     */
    private static final CommentDaoImpl instance = new CommentDaoImpl();
    // 私有化构造方法，防止被 new
    private CommentDaoImpl(){

    }

    //  统一得到单例方法
    public static CommentDaoImpl getInstance(){
        return instance;
    }
    
    /**
     * 查询该信息所有评论记录，并按时间排序
     */
    @Override
    public ArrayList<Comment> SelectUserMessage(int gid) {
        ArrayList<Comment> message = new ArrayList<>();
        String sql = "SELECT * FROM comment WHERE guid = ? ORDER BY writeTime DESC";
        SqlRowSet sqlRowSet = template.queryForRowSet(sql, gid);
        while (sqlRowSet.next()){
            message.add(getMessage(sqlRowSet));
        }
        return message;
    }
    
    

    /**
     * 将查询结果转换为message对象
     */
    private Comment getMessage(SqlRowSet res) {
        
        Comment comment = new Comment();
        User user = null;
        try {
            comment.setId(res.getInt("id"));
            comment.setWuid(res.getInt("wuid"));
            user = userDao.findById(comment.getWuid());
            comment.setWname(user.getUser_name());
            comment.setWimg(user.getIcon());
            comment.setGuid(res.getInt("guid"));
            comment.setMessageText(res.getString("messageText"));
            if(res.getString("replyText")!=null&&res.getString("replyText").length()>0)
                comment.setReplyMessage(selectUserReplyMessage(res.getString("replyText")));
            comment.setWritetime(res.getString("writetime"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comment;
    }



    /**
     * 查询评论回复信息
     * 返回 Reply回复集合
     */
    @Override
    public ArrayList<Reply> selectUserReplyMessage(String content){
        content = content.trim();
        String[] one_msg = content.split(">");
        String[] one_msg_info = null;
        ArrayList<Reply> replys = new ArrayList<>();
        User user = new User();

        for (String value : one_msg) {
            one_msg_info = value.split("<");
            Reply re = new Reply();
            for (int i = 0; i < one_msg_info.length; i++) {
                if (i == 0) {
                    re.setId(Integer.parseInt(one_msg_info[0]));
                    user = userDao.findById(re.getId());// 查询用户name和头像路径
                    re.setImg(user.getIcon());
                    re.setName(user.getUser_name());
                }
                if (i == 1)
                    re.setContent(one_msg_info[1]);
                if (i == 2)
                    re.setTime(one_msg_info[2]);
            }
            replys.add(re);
        }
        return replys;
    }

    /**
     * 增加评论
	 *
     *  wid-评论者id（写评论的人）
     *  gid-被评论者id（收评论的人）
     *  content-评论内容
     */
    @Override
    public int addmessage(int wid, int gid, String content) {

        String sql = "insert into comment (wuid,guid,messageText,writetime) VALUES (?,?,?,?)";
        return template.update(sql,wid,gid,content, SimpleUtils.getTimeNow());

    }

    /**
     * 根据msgid查询回复信息
     *msgid -评论编号
     */
    @Override
    public SqlRowSet selectReplyByMsgId(int msgid) {
        CachedRowSet rowset = null;
        String sql = "SELECT * FROM comment WHERE id = ?";

        return template.queryForRowSet(sql,msgid);

        //return (ResultSet) template.query
    }


    /**
     * 添加评论回复功能
     * msgid-评论编号
     * userid -回复者id
     * content-回复内容 回复拼接格式：旧的回复内容+id<content<time>
     */
    public int updatetmessagereply(int msgid, int userid, String content) {
        int end = 0;
        String sql = "UPDATE comment SET replyText = ? WHERE id = ?";
        content = content.replaceAll("<", "&lt;");
        content = content.replaceAll(">", "&gt;");
        String time = SimpleUtils.getTimeNow();
        SqlRowSet oldmessageinfo = selectReplyByMsgId(msgid);// 获取原数据库的评论回复内容，然后开始字符串的拼接

        StringBuilder oldMessageReply = new StringBuilder();
        try {
            if (oldmessageinfo.next()) {
                if (oldmessageinfo.getString("replyText") != null && oldmessageinfo.getString("replyText").length() > 0) {
                    oldMessageReply.append(oldmessageinfo.getString("replyText"));
                }
                oldMessageReply.append(userid + "<" + content + "<" + time + ">");
                end = template.update(sql,oldMessageReply,msgid);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return end;
    }
}
