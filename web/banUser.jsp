<%@ page import="com.lzc.lol.utils.LoggedUserSessionContext" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<!-- 网页使用的语言 -->
<html lang="zh-CN">
<head>
    <!-- 指定字符集 -->
    <meta charset="utf-8">
    <!-- 使用Edge最新的浏览器的渲染方式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- viewport视口：网页可以根据设置的宽度自动进行适配，在浏览器的内部虚拟一个容器，容器的宽度与设备的宽度相同。
    width: 默认宽度与设备的宽度相同
    initial-scale: 初始的缩放比，为1:1 -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>用户信息管理系统</title>

    <!-- 1. 导入CSS的全局样式 -->
    <%--    <link href="css/bootstrap.min.css" rel="stylesheet">--%>
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <!-- 3. 导入bootstrap的js文件 -->
    <%--    <script src="js/bootstrap.min.js"></script>--%>
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

    <style type="text/css">
        td, th {
            text-align: center;
        }
    </style>

    <script>
             <%--function deleteUser(id){--%>
        <%--    //用户安全提示--%>
        <%--    if(confirm("您确定要删除吗？")){--%>
        <%--        //访问路径--%>
        <%--        location.href="${pageContext.request.contextPath}/userServlet/deleteOne?id="+id;--%>
        <%--    }--%>
        <%--}--%>

        function banUser(id){
            //用户安全提示
            var startTime = document.getElementById("startTime"+id).value;
            var endTime = document.getElementById("endTime"+id).value;
            // startTime.toLocaleString();
            var  time = getNowFormatDate();
            // alert(time);
            // alert(startTime);
            // var time = startTime.toDateString();
            // alert(time);
            // alert(startTime);
            // alert(endTime);
            if (endTime > time && startTime >= time && endTime > startTime){
                if(confirm("您确定要封禁该用户吗？")){
                    //访问路径
                    $.ajax({
                        url:"${pageContext.request.contextPath}/userServlet/banUser",
                        type: "POST",
                        data:{
                            startTime:startTime,
                            endTime:endTime,
                            id:id,
                        },
                        success: function (data) {
                            if (data.status === true) {
                                alert(data.message);
                                location.href = "${pageContext.request.contextPath}/userServlet/pageQuery?type=2"
                            }else{
                                alert(data.message);
                            }
                        }
                    })
                }
            }else{
                alert("请选择正确的时间！");
            }

        }

             //获取当前时间，格式YYYY-MM-DD
             function getNowFormatDate() {
                 var date = new Date();
                 var seperator1 = "-";
                 var year = date.getFullYear();
                 var month = date.getMonth() + 1;
                 var strDate = date.getDate();
                 if (month >= 1 && month <= 9) {
                     month = "0" + month;
                 }
                 if (strDate >= 0 && strDate <= 9) {
                     strDate = "0" + strDate;
                 }
                 var currentdate = year + seperator1 + month + seperator1 + strDate;
                 return currentdate;
             }


             function reportUser(user_name) {
                // alert(user_name);
                var report_name = '${sessionScope.get('user').user_name}';
                var content = document.getElementById("content"+user_name).value;
                // alert(report_name);
                alert(content);
                 if (confirm("确定举报该用户吗？") && content !== ""){
                     $.ajax({
                         url:"${pageContext.request.contextPath}/reportServlet/addReport",
                         type:"post",
                         data:{
                           user_name:user_name,
                           report_name:report_name,
                           content:content
                         },
                         success:function(data){
                             // window.clearInterval(timer);
                             // console.log("over..");
                             if (data.status){
                                 alert(data.message);
                                 location.href = "${pageContext.request.contextPath}/userServlet/pageQuery?type=2"
                             }else{
                                 alert(data.message);
                             }
                         }
                     })
                 }
             }

             // window.onload = function(){
        //     //给删除选中按钮添加单击事件
        //     document.getElementById("delSelected").onclick = function(){
        //         if(confirm("您确定要删除选中条目吗？")){
        //
        //             var flag = false;
        //             //判断是否有选中条目
        //             var cbs = document.getElementsByName("uid");
        //             for (var i = 0; i < cbs.length; i++) {
        //                 if(cbs[i].checked){
        //                     //有一个条目选中了
        //                     flag = true;
        //                     break;
        //                 }
        //             }
        //
        //             if(flag){//有条目被选中
        //                 //表单提交
        //                 document.getElementById("form").submit();
        //             }
        //
        //         }
        //
        //     };
        //     //1.获取第一个cb
        //     document.getElementById("firstCb").onclick = function(){
        //         //2.获取下边列表中所有的cb
        //         var cbs = document.getElementsByName("uid");
        //         //3.遍历
        //         for (var i = 0; i < cbs.length; i++) {
        //             //4.设置这些cbs[i]的checked状态 = firstCb.checked
        //             cbs[i].checked = this.checked;
        //
        //         }
        //
        //     }
        //
        //
        // }


    </script>
</head>
<body>
<div class="container">
    <h3 style="text-align: center">用户信息列表</h3>

    <div style="float: left;">

        <form class="form-inline" action="${pageContext.request.contextPath}/userServlet/pageQuery?type=2" method="post">
            <div class="form-group">
                <label for="exampleInputName2">用户名</label>
                <input type="text" name="user_name" value="${condition.name[0]}" class="form-control" id="exampleInputName2" >
            </div>
            <div class="form-group">
                <label for="exampleInputName3">上个战队</label>
                <input type="text" name="last_team" value="${condition.address[0]}" class="form-control" id="exampleInputName3" >
            </div>

            <div class="form-group">
                <label for="exampleInputEmail2">邮箱</label>
                <input type="text" name="email" value="${condition.email[0]}" class="form-control" id="exampleInputEmail2"  >
            </div>
            <button type="submit" class="btn btn-default">查询</button>
        </form>

    </div>

<%--    <div style="float: right;margin: 5px;">--%>

<%--        &lt;%&ndash;        <a class="btn btn-primary" href="${pageContext.request.contextPath}/add.jsp">添加联系人</a>&ndash;%&gt;--%>
<%--        <a class="btn btn-primary" href="javascript:void(0);" id="delSelected">删除选中</a>--%>

<%--    </div>--%>
    <form id="form" action="" method="post">
        <table border="1" class="table table-bordered table-hover">
            <tr class="success">
                <th><input type="checkbox" id="firstCb"></th>
                <th>编号</th>
                <th>用户名</th>
                <th>头像</th>
                <th>年龄</th>
                <th>简介</th>
                <th>上个战队</th>
                <th>加入LPL时间</th>
                <th>邮箱</th>
                <th>状态</th>
                <th>操作</th>
            </tr>

            <c:forEach items="${pb.list}" var="user" varStatus="s">
                <tr>
                    <td><input type="checkbox" name="uid" value="${user.id}"></td>
                    <td>${s.count}</td>
                    <td>${user.user_name}</td>
                    <td><img src="../images/${user.icon}" style="width: 50px;height: 50px;"></td>
                    <td>${user.age}</td>
                    <td>${user.message}</td>
                    <td>${user.last_team}</td>
                    <td>${user.join_time}</td>
                    <td>${user.email}</td>
                    <td>
                    <c:set var="uid" value="${user.id}" scope="request"/>
                    <c:choose>
                        <c:when test='<%=LoggedUserSessionContext.sessionMap.containsKey(request.getAttribute("uid"))%>'>
<%--                            request.getAttribute("uid")--%>
                            <span>在线</span>
                        </c:when>
                        <c:otherwise>
                            <span>离线</span>
                        </c:otherwise>
                    </c:choose>
                    </td>
                    <td>
                        <c:if test="${sessionScope.get('user').status == 4}">
                        <input type="date" name="startTime" required="" placeholder="开始时间" id="startTime${user.id}" />
                        <span>到</span>
                        <input type="date" name="endTime" required="" placeholder="结束时间" id="endTime${user.id}" />
                        <a class="btn btn-default btn-sm" href="javascript:banUser(${user.id});">封禁</a>&nbsp;
                        </c:if>
                        <c:if test="${sessionScope.get('user').status == 3}">
                            <textarea placeholder="举报内容" name="content" required="" id="content${user.user_name}"></textarea>
                        <a class="btn btn-default btn-sm" href="javascript:reportUser('${user.user_name}');">举报</a>&nbsp;
                        </c:if>
                    </td>
<%--                        <a class="btn btn-default btn-sm" href="javascript:deleteUser(${user.id});">删除</a></td>--%>
                </tr>

            </c:forEach>


        </table>
    </form>
    <div>
        <nav aria-label="Page navigation">
            <ul class="pagination">

                <c:if test="${pb.currentPage == 1}">
                    <%--                    ||${pb.currentPage == pb.totalPage}--%>
                <li class="disabled">
                    </c:if>

                    <c:if test="${pb.currentPage != 1}">
                        <%--                    &&${pb.currentPage != pb.totalPage}--%>
                <li>
                    </c:if>


                    <a href="${pageContext.request.contextPath}/userServlet/pageQuery?currentPage=${pb.currentPage - 1}&pageSize=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}&type=2" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>


                <c:forEach begin="1" end="${pb.totalPage}" var="i" >


                    <c:if test="${pb.currentPage == i}">
                        <li class="active"><a href="${pageContext.request.contextPath}/userServlet/pageQuery?currentPage=${i}&pageSize=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}&type=2">${i}</a></li>
                    </c:if>
                    <c:if test="${pb.currentPage != i}">
                        <li><a href="${pageContext.request.contextPath}/userServlet/pageQuery?currentPage=${i}&pageSize=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}&type=2">${i}</a></li>
                    </c:if>

                </c:forEach>


                <c:if test="${pb.currentPage == pb.totalPage}">
                <li class="disabled">
                    </c:if>
                    <c:if test="${pb.currentPage != pb.totalPage}">
                <li>
                    </c:if>

                    <a href="${pageContext.request.contextPath}/userServlet/pageQuery?currentPage=${pb.currentPage + 1}&pageSize=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}&type=2" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
                <span style="font-size: 25px;margin-left: 5px;">
                    共${pb.totalCount}条记录，共${pb.totalPage}页
                </span>

            </ul>
        </nav>


    </div>


</div>

<%--    <script>--%>
<%--        $(function () {--%>
<%--            $.ajax({--%>
<%--                url:"${pageContext.request.contextPath}/userServlet/pageQuery"--%>
<%--            })--%>
<%--        })--%>
<%--    </script>--%>


</body>
</html>
