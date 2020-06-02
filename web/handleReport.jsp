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
    <title>举报信箱</title>

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
        function deleteUser(id){
            //用户安全提示
            if(confirm("您确定要删除吗？")){
                //访问路径
                location.href="${pageContext.request.contextPath}/reportServlet/refuseOne?id="+id;
            }
        }

        function passUser(id){
            //用户安全提示
            //访问路径
            location.href="${pageContext.request.contextPath}/reportServlet/passOne?id="+id;

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
    <h3 style="text-align: center">举报信息列表</h3>

    <div style="float: left;">

        <form class="form-inline" action="${pageContext.request.contextPath}/reportServlet/pageQuery" method="post">
            <div class="form-group">
                <label for="exampleInputName2">被举报用户</label>
                <input type="text" name="user_name" value="${condition.name[0]}" class="form-control" id="exampleInputName2" >
            </div>
            <div class="form-group">
                <label for="exampleInputName3">举报者</label>
                <input type="text" name="reporter" value="${condition.address[0]}" class="form-control" id="exampleInputName3" >
            </div>

            <div class="form-group">
                <label for="exampleInputEmail2">举报时间</label>
                <input type="text" name="report_time" value="${condition.email[0]}" class="form-control" id="exampleInputEmail2"  >
            </div>
            <button type="submit" class="btn btn-default">查询</button>
        </form>

    </div>

    <div style="float: right;margin: 5px;">

        <%--        <a class="btn btn-primary" href="${pageContext.request.contextPath}/add.jsp">添加联系人</a>--%>
        <a class="btn btn-primary" href="javascript:void(0);" id="delSelected">删除选中</a>

    </div>
    <form id="form" action="${pageContext.request.contextPath}/reportServlet/delSelected" method="post">
        <table border="1" class="table table-bordered table-hover">
            <tr class="success">
                <th><input type="checkbox" id="firstCb"></th>
                <th>编号</th>
                <th>被举报用户</th>
                <th>举报内容</th>
                <th>举报者</th>
                <th>举报时间</th>
                <th>状态</th>
                <th>操作</th>
            </tr>

            <c:forEach items="${pb.list}" var="report" varStatus="s">
                <tr>
                    <td><input type="checkbox" name="uid" value="${report.id}"></td>
                    <td>${s.count}</td>
                    <td>${report.user_name}</td>

                        <%--                    <td id="picture1"></td>--%>
                        <%--                    <td id="picture2"></td>--%>
                        <%--                    <td id="picture3"></td>--%>
                        <%--                    <c:if test="${report.picture1!=null}">--%>
                        <%--                        <script>--%>
                        <%--                            var picture1 = "<img src='../images/" +--%>
                        <%--                                "${report.picture1}' style='width: 50px;height: 50px;'>";--%>
                        <%--                            $("#picture1").html(picture1)--%>
                        <%--                        </script>--%>
                        <%--                    </c:if>--%>
                        <%--                    <c:if test="${report.picture2!=null}">--%>
                        <%--                        <script>--%>
                        <%--                            var picture2 = "<img src='../images/" +--%>
                        <%--                                "${report.picture2}' style='width: 50px;height: 50px;'>";--%>
                        <%--                            $("#picture2").html(picture2)--%>
                        <%--                        </script>--%>
                        <%--                    </c:if>--%>
                        <%--                    <c:if test="${report.picture3!=null}">--%>
                        <%--                        <script>--%>
                        <%--                            var picture3 = "<img src='../images/" +--%>
                        <%--                                "${report.picture3}' style='width: 50px;height: 50px;'>";--%>
                        <%--                            $("#picture3").html(picture3)--%>
                        <%--                        </scrip t>--%>
                        <%--                    </c:if>--%>

                    <td>${report.content}</td>
                    <td>${report.reporter}</td>
                    <td>${report.report_time}</td>
                    <td>${report.state}</td>
                        <%--                    <td>${user.last_team}</td>--%>
                        <%--                    <td>${user.join_time}</td>--%>
                        <%--                    <td>${user.email}</td>--%>
                    <td><a class="btn btn-default btn-sm" href="javascript:passUser(${report.id});">已读</a>&nbsp;
                        <a class="btn btn-default btn-sm" href="javascript:deleteUser(${report.id});">删除</a></td>
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


                    <a href="${pageContext.request.contextPath}/reportServlet/pageQuery?currentPage=${pb.currentPage - 1}&pageSize=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>


                <c:forEach begin="1" end="${pb.totalPage}" var="i" >


                    <c:if test="${pb.currentPage == i}">
                        <li class="active"><a href="${pageContext.request.contextPath}/reportServlet/pageQuery?currentPage=${i}&pageSize=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}">${i}</a></li>
                    </c:if>
                    <c:if test="${pb.currentPage != i}">
                        <li><a href="${pageContext.request.contextPath}/reportServlet/pageQuery?currentPage=${i}&pageSize=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}">${i}</a></li>
                    </c:if>

                </c:forEach>


                <c:if test="${pb.currentPage == pb.totalPage}">
                <li class="disabled">
                    </c:if>
                    <c:if test="${pb.currentPage != pb.totalPage}">
                <li>
                    </c:if>

                    <a href="${pageContext.request.contextPath}/reportServlet/pageQuery?currentPage=${pb.currentPage + 1}&pageSize=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}" aria-label="Next">
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
<%--                url:"${pageContext.request.contextPath}/reportServlet/pageQuery"--%>
<%--            })--%>
<%--        })--%>
<%--    </script>--%>


</body>
</html>

