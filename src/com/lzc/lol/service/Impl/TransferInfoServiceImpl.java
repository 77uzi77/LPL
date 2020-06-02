package com.lzc.lol.service.Impl;

import com.lzc.lol.dao.Impl.TeamDaoImpl;
import com.lzc.lol.dao.Impl.TransferInfoDaoImpl;
import com.lzc.lol.dao.Impl.UserDaoImpl;
import com.lzc.lol.dao.TeamDao;
import com.lzc.lol.dao.TransferInfoDao;
import com.lzc.lol.dao.UserDao;
import com.lzc.lol.entity.PageBean;
import com.lzc.lol.entity.TransferInfo;
import com.lzc.lol.service.TransferInfoService;
import com.lzc.lol.utils.SimpleUtils;


import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Map;


/**
 *  转会信息 业务 实现类
 */
public class TransferInfoServiceImpl implements TransferInfoService {


    private TransferInfoDao transferInfoDao = TransferInfoDaoImpl.getInstance();
    private UserDao userDao = UserDaoImpl.getInstance();
    private TeamDao teamDao = TeamDaoImpl.getInstance();

    /**
     *   设置单例  饿汉式
     */
    //类内部实例化
    private static final TransferInfoService instance = new TransferInfoServiceImpl();

    //构造器私有化，防止new对象
    private TransferInfoServiceImpl(){

    }

    //对外提供公有方法调用
    public static TransferInfoService getInstance(){
        return instance;
    }


    /**
     *  分类 分页查询转会信息
     */
    @Override
    public PageBean<TransferInfo> pageQuery(int cid, HttpServletRequest request, String rname ) {
        //设置总记录数
        int totalCount = transferInfoDao.findTotalCountByClass(cid,rname);
        // 得到分页条件参数
        Object[] pageParams = SimpleUtils.getPageParams(request, totalCount);
        PageBean pb = (PageBean) pageParams[0];
        int start = (int) pageParams[1];
        // 调用dao查找数据
        List<TransferInfo> list = transferInfoDao.findByPageByClass(cid,start,pb.getPageSize(),rname);
        pb.setList(list);

        return pb;
    }

    /**
     *  查询单个转会信息
     */
    @Override
    public TransferInfo findOne(String rid) {

        TransferInfo transferInfo = transferInfoDao.findOne(Integer.parseInt(rid));

        return transferInfo;
    }

    /**
     *  增加转会信息
     */
    @Override
    public void addOne(TransferInfo transferInfo) {
        Map<String, Object> userImages = transferInfoDao.findByUserName(transferInfo.getUser_name());
        // 如果用户转会信息存在，则删除旧的图片
        if ( userImages != null){
            for (String s : userImages.keySet()){
                File file = new File("C:/Users/啊柒哟/IdeaProjects/CAT2/web/images/"+userImages.get(s));
                if (file.isFile() && file.exists()){
                    file.delete();
                }
            }
            transferInfoDao.reviseOne(transferInfo);
        }else{
            transferInfoDao.addOne(transferInfo);
        }

    }

    /**
     *  查找转会信息 相关图片
     */
    @Override
    public String[] findImages(int del_num) {
        Map<String, Object> imgMap =  transferInfoDao.findImages(del_num);
        String[] data = new String[imgMap.size()];
        int i = 0;
        for (String s : imgMap.keySet()){
            data[i++] = (String) imgMap.get(s);
        }

        return data;
    }

    /**
     *  删除转会信息
     */
    @Override
    public int deleteOne(int del_num) {
        return transferInfoDao.deleteOne(del_num);
    }


    /**
     *  通过分页查询
     */
    @Override
    public PageBean<TransferInfo> findByPage(HttpServletRequest request, Map<String, String[]> condition) {
        //调用dao查询总记录数
        int totalCount = transferInfoDao.findTotalCount(condition);
        // 得到分页条件参数
        Object[] pageParams = SimpleUtils.getPageParams(request, totalCount);
        PageBean pb = (PageBean) pageParams[0];
        int start = (int) pageParams[1];
        // 调用dao查找数据
        List<TransferInfo> list = transferInfoDao.findByPage(start,pb.getPageSize(),condition);
        pb.setList(list);


        return pb;
    }

    /**
     *  通过转会信息申请
     */
    @Override
    public void passOne(String id) {
        transferInfoDao.passOne(Integer.parseInt(id));
    }

    /**
     *  删除选中转会信息
     */
    @Override
    public void delSelectedUser(String[] ids) {
        if(ids != null && ids.length > 0){
            //1.遍历数组
            for (String id : ids) {
                //2.调用dao删除
                transferInfoDao.deleteOne(Integer.parseInt(id));
            }
        }
    }

    /**
     *  查找转会信息
     */
    @Override
    public void findUserName(String id) {
        String user_name = transferInfoDao.findUserName(Integer.parseInt(id));
        if (user_name != null){
            int user_id = userDao.findUserId(user_name);
            teamDao.deleteOne(user_id);
        }
    }

    /**
     *  将 转会信息 设为拍卖
     */
    @Override
    public void addSell(int id) {
        transferInfoDao.addSell(id);
    }

    /**
     *  查找推荐选手
     */
    @Override
    public List<Map<String, Object>> findRecommend(String cid,String localId) {
        return transferInfoDao.findRecommend(cid,Integer.parseInt(localId));
    }

    /**
     *  查找用户
     */
    @Override
    public String selectUser(int id) {
        return transferInfoDao.findUserName(id);
    }


}
