package com.lzc.lol.utils;

import com.lzc.lol.entity.PageBean;
import com.lzc.lol.entity.ResultInfo;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 简单工具类
 */
public class SimpleUtils {

    /**
     * 产生4位随机字符串
     */
    public static String getCheckCode() {
        String base = "0123456789ABCDEFGabcdefg";
        int size = base.length();
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i=1;i<=4;i++){
            //产生0到size-1的随机值
            int index = r.nextInt(size);
            //在base字符串中获取下标为index的字符
            char c = base.charAt(index);
            //将c放入到StringBuffer中去
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     *   将项目中含图片表单的提交 抽取为 工具类
     */
    public static Object[] getFileItem(HttpServletRequest request) throws UnsupportedEncodingException {
        // 设置编码格式为UTF-8
        request.setCharacterEncoding("utf-8");

        Object[] data = new Object[2];
        ResultInfo resultInfo = new ResultInfo();
        data[0] = resultInfo;
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        // 如果不满足要求就立即结束对该请求的处理 （post 请求 / enctype 是否以multipart打头）
        if (!isMultipart) {
            resultInfo.setStatus(false);
            resultInfo.setMessage("提交失败！");
            return data;
        }

        Map<String,Object> map = new HashMap<>();
        data[1] = map;

        try {
            // FileItem 是表单中的每一个元素的封装
            // 创建一个 FileItem 的工厂类
            FileItemFactory factory = new DiskFileItemFactory();
            // 创建一个文件上传处理器（装饰设计模式）
            ServletFileUpload upload = new ServletFileUpload(factory);
            // 解析请求
            List<FileItem> items = upload.parseRequest(request);
            for (FileItem fileItem :
                    items) {
                // 判断空间是否是普通控件
                if (fileItem.isFormField()) {
                    // 普通控件
                    map.put(fileItem.getFieldName(),fileItem.getString("utf-8"));
                } else {
                    // 上传控件
                    String contentType = fileItem.getContentType();
                    if (!contentType.startsWith("image/")) {
                        // 实现简单的错误提示
//                        request.setAttribute("errorMsg", "您上传的文件格式不正确，请重新上传！");
//                        request.getRequestDispatcher("upload.jsp").forward(request, response);
                        resultInfo.setStatus(false);
                        resultInfo.setMessage("请选择正确的图片！");
                        return data;  // 如果不是图片类型则不再对请求进行处理
                    }
                    // 随机命名文件名
                    String fileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(fileItem.getName());
                    // 将上传的文件保存到服务器
                    fileItem.write(new File("C:/Users/啊柒哟/IdeaProjects/CAT2/web/images/", fileName));
                    map.put(fileItem.getFieldName(),fileName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        resultInfo.setStatus(true);
        return data;
    }


    /**
     * 获取当前系统时间
     */
    public static String getTimeNow(){
        Date a=new Date();
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return formatter.format(a);
    }

    /**
     *  处理分页参数,封装pageBean
     */
    public static Object[] getPageParams(HttpServletRequest request,int totalCount){
        //接受参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");

        //当前页码，如果不传递，则默认为第一页
        int currentPage = 1;
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        }
        if(currentPage <= 0 ) {
            currentPage = 1;
        }

        //每页显示条数，如果不传递，默认每页显示5条记录
        int pageSize = 5;
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        }

        // 总页数，如果 当前页数大于总页数，则 将当前页设置为 最后页
        int totalPage = (totalCount % pageSize)  == 0 ? totalCount/pageSize : (totalCount/pageSize) + 1;

        if (totalPage != 0 && currentPage >= totalPage){
            currentPage = totalPage;
        }

        //计算开始的记录索引
        int start = (currentPage - 1) * pageSize;

        // 将参数封装为 pageBean 对象
        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalCount(totalCount);
        pageBean.setTotalPage(totalPage);

        // 返回pageB对象 和 开始页
        return new Object[]{pageBean,start};
    }


    /**
     *     将基础sql语句进行字符串拼接
     *     用于 多条件 查询
     */
    public static Object[] getRealSql(String sql,Map<String, String[]> condition){

        StringBuilder sb = new StringBuilder(sql);

            List<Object> params = null;
            if (condition != null) {
                //2.遍历map
                Set<String> keySet = condition.keySet();
                //定义参数的集合
                params = new ArrayList<Object>();
                for (String key : keySet) {
                    //排除分页条件参数
                    if ("type".equals(key) || "currentPage".equals(key) || "pageSize".equals(key)) {
                        continue;
                    }
                    if("cid".equals(key)){
                        switch (condition.get(key)[0]){
                            case  "上单":
                            case  "上":
                            case  "单":
                                condition.get(key)[0] = "1";break;
                            case "打野":
                            case "打":
                            case "野":
                                condition.get(key)[0] = "2";break;
                            case "中单":
                            case "中":
                                //case "单":
                                condition.get(key)[0] = "3";break;
                            case "射手":
                            case "射":
                            case "手":
                                condition.get(key)[0] = "4";break;
                            case "辅助":
                            case "辅":
                            case "助":
                                condition.get(key)[0] = "5";break;
                        }
                    }

                    //获取value
                    String value = condition.get(key)[0];
                    //判断value是否有值
                    if (value != null && !"".equals(value)) {
                        //有值
                        sb.append(" and " + key + " like ? ");
                        //？条件的值
                        params.add("%" + value + "%");
                    }
                }
            }


        // 返回 拼接后的字符串 和 所需参数
        return new Object[]{sb,params};
    }

}
