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
    <title>转会信息管理系统</title>

    <!-- 1. 导入CSS的全局样式 -->
    <%--    <link href="css/bootstrap.min.css" rel="stylesheet">--%>
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <!-- 3. 导入bootstrap的js文件 -->
    <%--    <script src="js/bootstrap.min.js"></script>--%>
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<%--    <script type="text/javascript" src="C:/Users/啊柒哟/IdeaProjects/CAT2/web/js/jquery-3.3.1.js" ></script>--%>

    <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
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


        function deleteUser(id){
            //用户安全提示
            if(confirm("您确定要删除吗？")){
                //访问路径
                location.href="${pageContext.request.contextPath}/transferInfo/refuseOne?id="+id;
            }
        }

        function sellMessage(id){
            //用户安全提示
            if(confirm("您确定要拍卖该用户的信息吗？")){
                //访问路径
                <%--location.href="${pageContext.request.contextPath}/transferInfo/passOne?id="+id;--%>
                var endDate = document.getElementById("endDate").value;
                var sellMoney = document.getElementById("sellMoney").value;
                var startDate = getNowFormatDate();
                if (endDate < startDate){
                    alert("请选择正确的拍卖结束时间");
                }else{
                    $.ajax({
                        url:"${pageContext.request.contextPath}/auctionServlet/addSell",
                        type: "POST",
                        data:{
                            id : id,
                            endDate : endDate,
                            sellMoney : sellMoney,
                            startDate : startDate,
                        },
                        success: function (data) {
                            if (data.status === true) {
                                alert(data.message);
                                // location.href="messageList.jsp";
                                location.href="${pageContext.request.contextPath}/transferInfo/pageQuery";
                            }else{
                                alert(data.message);
                            }
                        }
                    })
                }

            }
        }

        function passUser(id){
            //用户安全提示
            if(confirm("您确定要通过吗？")){
                //访问路径
                location.href="${pageContext.request.contextPath}/transferInfo/passOne?id="+id;
            }
        }

        window.onload = function(){
            //给删除选中按钮添加单击事件
            document.getElementById("delSelected").onclick = function(){
                if(confirm("您确定要删除选中条目吗？")){

                    var flag = false;
                    //判断是否有选中条目
                    var cbs = document.getElementsByName("uid");
                    for (var i = 0; i < cbs.length; i++) {
                        if(cbs[i].checked){
                            //有一个条目选中了
                            flag = true;
                            break;
                        }
                    }

                    if(flag){//有条目被选中
                        //表单提交
                        document.getElementById("form").submit();
                    }

                }

            };
            //1.获取第一个cb
            document.getElementById("firstCb").onclick = function(){
                //2.获取下边列表中所有的cb
                var cbs = document.getElementsByName("uid");
                //3.遍历
                for (var i = 0; i < cbs.length; i++) {
                    //4.设置这些cbs[i]的checked状态 = firstCb.checked
                    cbs[i].checked = this.checked;

                }

            }


        }


    </script>
</head>
<body>
<div class="container">
    <h3 style="text-align: center">用户信息列表</h3>

    <div style="float: left;">

        <form class="form-inline" action="${pageContext.request.contextPath}/transferInfo/pageQuery" method="post">
            <div class="form-group">
                <label for="exampleInputName2">用户名</label>
                <input type="text" name="user_name" value="${condition.name[0]}" class="form-control" id="exampleInputName2" >
            </div>
            <div class="form-group">
                <label for="exampleInputName3">位置</label>
                <input type="text" name="cid" value="${condition.address[0]}" class="form-control" id="exampleInputName3" >
            </div>

            <div class="form-group">
                <label for="exampleInputEmail2">年薪</label>
                <input type="text" name="salary" value="${condition.email[0]}" class="form-control" id="exampleInputEmail2"  >
            </div>
            <button type="submit" class="btn btn-default">查询</button>
        </form>

    </div>

    <div style="float: right;margin: 5px;">

        <%--        <a class="btn btn-primary" href="${pageContext.request.contextPath}/add.jsp">添加联系人</a>--%>
        <a class="btn btn-primary" href="javascript:void(0);" id="delSelected">删除选中</a>

    </div>
    <form id="form" action="${pageContext.request.contextPath}/transferInfo/delSelected" method="post">
        <table border="1" class="table table-bordered table-hover">
            <tr class="success">
                <th><input type="checkbox" id="firstCb"></th>
                <th>编号</th>
                <th>用户名</th>
                <th>位置</th>
                <th>高光时刻</th>
                <th>高光时刻</th>
                <th>高光时刻</th>
                <th>年薪</th>
                <th>介绍</th>
                <th>是否申请拍卖</th>
                <th>操作</th>
                <th>拍卖</th>
            </tr>

            <c:forEach items="${pb.list}" var="transferInfo" varStatus="s">
                <tr>
                    <td><input type="checkbox" name="uid" value="${transferInfo.id}"></td>
                    <td>${s.count}</td>
                    <td>${transferInfo.user_name}</td>
                    <td>
                        <c:choose>
                            <c:when test="${transferInfo.cid == '1'}">
                                <span>上单</span>
                            </c:when>
                            <c:when test="${transferInfo.cid == '2'}">
                                <span>打野</span>
                            </c:when>
                            <c:when test="${transferInfo.cid == '3'}">
                                <span>中单</span>
                            </c:when>
                            <c:when test="${transferInfo.cid == '4'}">
                                <span>射手</span>
                            </c:when>
                            <c:when test="${transferInfo.cid == '5'}">
                                <span>辅助</span>
                            </c:when>
                        </c:choose>
                    </td>

<%--                    <td id="picture1"></td>--%>
<%--                    <td id="picture2"></td>--%>
<%--                    <td id="picture3"></td>--%>
<%--                    <c:if test="${transferInfo.picture1!=null}">--%>
<%--                        <script>--%>
<%--                            var picture1 = "<img src='../images/" +--%>
<%--                                "${transferInfo.picture1}' style='width: 50px;height: 50px;'>";--%>
<%--                            $("#picture1").html(picture1)--%>
<%--                        </script>--%>
<%--                    </c:if>--%>
<%--                    <c:if test="${transferInfo.picture2!=null}">--%>
<%--                        <script>--%>
<%--                            var picture2 = "<img src='../images/" +--%>
<%--                                "${transferInfo.picture2}' style='width: 50px;height: 50px;'>";--%>
<%--                            $("#picture2").html(picture2)--%>
<%--                        </script>--%>
<%--                    </c:if>--%>
<%--                    <c:if test="${transferInfo.picture3!=null}">--%>
<%--                        <script>--%>
<%--                            var picture3 = "<img src='../images/" +--%>
<%--                                "${transferInfo.picture3}' style='width: 50px;height: 50px;'>";--%>
<%--                            $("#picture3").html(picture3)--%>
<%--                        </script>--%>
<%--                    </c:if>--%>
                    <td>
                        <c:if test="${transferInfo.picture1!=null}">
                            <img src="../images/${transferInfo.picture1}" style='width: 50px;height: 50px;'>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${transferInfo.picture2!=null}">
                            <img src="../images/${transferInfo.picture2}" style='width: 50px;height: 50px;'>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${transferInfo.picture3!=null}">
                           <img src="../images/${transferInfo.picture3}" style='width: 50px;height: 50px;'>
                        </c:if>
                    </td>

                    <td>${transferInfo.salary}</td>
                    <td>${transferInfo.introduction}</td>
                    <td>${transferInfo.state}</td>
<%--                    <td>${user.last_team}</td>--%>
<%--                    <td>${user.join_time}</td>--%>
<%--                    <td>${user.email}</td>--%>
                    <td><a class="btn btn-default btn-sm" href="javascript:passUser(${transferInfo.id});">通过</a>&nbsp;
                        <a class="btn btn-default btn-sm" href="javascript:deleteUser(${transferInfo.id});">删除</a></td>
                    <td>
                        <input type="date" placeholder="结束时间" id="endDate">
                        <input type="number" placeholder="起拍金额" id="sellMoney">
                        <a class="btn btn-default btn-sm" href="javascript:sellMessage(${transferInfo.id});">拍卖</a>
                    </td>
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


                    <a href="${pageContext.request.contextPath}/transferInfo/pageQuery?currentPage=${pb.currentPage - 1}&pageSize=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>


                <c:forEach begin="1" end="${pb.totalPage}" var="i" >


                    <c:if test="${pb.currentPage == i}">
                        <li class="active"><a href="${pageContext.request.contextPath}/transferInfo/pageQuery?currentPage=${i}&pageSize=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}">${i}</a></li>
                    </c:if>
                    <c:if test="${pb.currentPage != i}">
                        <li><a href="${pageContext.request.contextPath}/transferInfo/pageQuery?currentPage=${i}&pageSize=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}">${i}</a></li>
                    </c:if>

                </c:forEach>


                <c:if test="${pb.currentPage == pb.totalPage}">
                <li class="disabled">
                    </c:if>
                    <c:if test="${pb.currentPage != pb.totalPage}">
                <li>
                    </c:if>

                    <a href="${pageContext.request.contextPath}/transferInfo/pageQuery?currentPage=${pb.currentPage + 1}&pageSize=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}" aria-label="Next">
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
<%--                url:"${pageContext.request.contextPath}/transferInfo/pageQuery"--%>
<%--            })--%>
<%--        })--%>
<%--    </script>--%>


</body>
</html>
