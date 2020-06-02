<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset=utf-8" />
    <title>转会详情</title>
    <script type="text/javascript" src="js/jquery-3.3.1.js" ></script>
    <link rel="stylesheet" type="text/css" href="css/common.css">
    <link rel="stylesheet" href="css/message.css">
    <link rel="stylesheet" type="text/css" href="css/route-detail.css">
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <script src="js/getParameter.js"></script>
</head>
<body>
    <script>
        $(function () {
            //1.获取rid  <img src="'images/'+route.picture1+">
            var id = ${requestScope.gid};
            //alert(id);
            //2.发送请求请求 route/findOne
            $.get("transferInfo/findOne", {id: id}, function (route) {
                //3.解析数据填充html
                $("#rname").html("用户名："+route.user_name);
                var position;
                switch (route.cid) {
                    case "1":
                        position = "上单";break;
                    case "2":
                        position = "打野";break;
                    case "3":
                        position = "中单";break;
                    case "4":
                        position = "射手";break;
                    case "5" :
                        position = "辅助";break;
                }

                $("#poi").html("位置：" + position);

                $("#routeIntroduce").html("个人介绍："+route.introduction);
                $("#price").html("年薪：¥" + route.salary + "起");
                var images = "历史高光:<br/>";
                if (route.picture1 != null) {
                    images += '<img src="\images/'+route.picture1+'" style="width: 100px;height: 100px">'
                }
                if (route.picture2 != null) {
                    images += '<img src="\images/'+route.picture2+'" style="width: 100px;height: 100px">'
                }
                if (route.picture3 != null) {
                    images += '<img src="\images/'+route.picture3+'" style="width: 100px;height: 100px">'
                }
                $("#picture").html(images);
                recommend(route.cid);
                // $("#sname").html(route.seller.sname);
                // $("#consphone").html(route.seller.consphone);
                // $("#address").html(route.seller.address);
                // //设置收藏次数
                // $("#favoriteCount").html("已收藏"+route.count+"次");
            })
        });

        function recommend(cid) {
            // var localId = getParameter("gid");
            var localId = "${requestScope.gid}";
            $.ajax({
                url:"${pageContext.request.contextPath}/transferInfo/recommend",
                type: "POST",
                data:{
                    cid : cid,
                    localId:localId,
                },
                success: function (data) {
                if (data.status === true) {
                    var message = "";
                    var list = data.data;
                    // alert(list.length);
                    for (var i = 0;i < list.length;i++){
                        // alert(111);
                        message += '<span style="float: left">';
                        message += '<img src="\images/'+list[i].picture1 +'" style="width: 100px;height: 100px;">';
                        message += '<div ">'+list[i].user_name +'</div>';
                        message += '<br/>';
                        message += '<div><a href="selectMessage?gid='+list[i].id+'" style="color: red;">查看详情</a></div>'
                        message += '</span>'
                    }
                    // alert(222);
                    $("#recommend").html(message);
                }else{
                    alert(data.message);
                }
            }
            })
        }
    </script>
    <div class="pros_title" id="rname"></div>
    <div class="pros_title" id="poi"></div>
    <div class="hot" id="routeIntroduce"></div>
    <div class="pros_price">
        <p class="price"><strong id="price"></strong><span></span></p>


<%--        <p class="collect">--%
<%--            <a class="btn" id="favorite" onclick="addFavorite();"><i class="glyphicon glyphicon-heart-empty"></i>点击收藏</a>--%>

<%--            <!-- <a  class="btn already" disabled="disabled"><i class="glyphicon glyphicon-heart-empty"></i>点击收藏</a>-->--%>
<%--            <span id="favoriteCount">已收藏100次</span>--%>
<%--        </p>--%>
    </div>
    <c:if test="${user.status == 3 || user.status == 4}">
    <div><a href="${pageContext.request.contextPath}/transferInfo/download?id=${requestScope.gid}" style="color: red">下载转会信息</a></div>
    </c:if>
    <span id="picture"></span>


    <br/>
    <br/>
    <br/>
    <div>相关推荐</div>
    <hr/>
    <div id="recommend"></div>

    <c:if test="${user.status == 2 || user.status == 3 || user.status == 4}">

<%--        <script>--%>
<%--            var gid = getParameter("rid");--%>
<%--            $(function x() {--%>
<%--                location.href = "selectMessage?gid="+gid;--%>
<%--            })--%>
<%--        </script>--%>
    <br/>
    <br/>
    <br/>
    <div id="content">
        <div><!-- 评论展示内容 -->
            <div class="MessagecontentBigBox"><!-- 内容外层div -->
                <div class="MessagecontentBigBox-top"> <font>评论区</font> </div>
                <div class="messageinfodiv">
                    <div class="publishmessage">
                        <form action="addMessageBy" method="post" onsubmit="return checkdata()">
                            <div class="publishmessagein">
                                <textarea maxlength="60" id="messagecontent" name="messagecontent"></textarea>
                            </div>
                            <input type="hidden" name="gid" value="<c:out  value="${requestScope.gid }"/>" />
                            <div class="publishmessagebutton">
                                <input type="submit" value="发送">&nbsp;&nbsp;
                                <input type="reset" value="取消">
                            </div>
                        </form>
                    </div>
                    <ul>
                        <c:forEach items="${requestScope.messageinfo }" var="msg">
                            <li class="thismessageinfodiv">
                                <img src="<c:out value="images/${msg.getWimg()}"/>" class="messageuserimg"/>
                                <font class="messageusername"><c:out value="${msg.getWname() }"/></font><br/>
                                <p class="messageusercontent"> <c:out value="${msg.getMessageText()}" escapeXml="true" default="加载失败"/> </p>
                                <p class="messageusertime"><c:out value="${msg.getWritetime() }"/></p>
                                <font class="messageuserreply" id="<c:out value="${msg.getId() }"/>">回复</font>
                                <c:forEach items="${msg.getReplyMessage() }" var="rel">
                                    <div class="messagereplydiv">
                                        <img class="messagereplyimg" src="<c:out value="images/${rel.getImg()}"/>"/>
                                        <font class="messagereplyname"><c:out value="${rel.getName()}"/></font>
                                        <font class="messagereplycontent"><c:out value="${rel.getContent()}"/></font>
                                        <p class="messagereplytime"><c:out value="${rel.getTime()}"/></p>
                                    </div>
                                </c:forEach>
                                <div class="messageuserreplydiv" id="replybigdiv<c:out value="${msg.getId() }"/>" style="display: none">
                                    <form action="messageUserReply" method="post" id="replymessageform">
                                        <input type="hidden" name="thismessageid" value="<c:out value="${msg.getId() }"/>" />
                                        <input type="hidden" name="gid" value="<c:out value="${requestScope.gid }"/>" />
                                        <textarea required id="replycontent" name="replycontent"></textarea><br/>
                                        <input type="submit" value="确定">&nbsp;&nbsp;
                                        <input type="reset" value="取消" class="cancelinput">
                                    </form>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>


    <script>
        $(document).ready(function (){
            var ti;
            $("#userinfo").mouseover(function(){$("#usernameshow").fadeIn(200); });
            $("#usernameshow").mouseover(function(){
                clearTimeout(ti);
                $("#usernameshow").show();
            });
            $("#usernameshow").mouseout(function(){$("#usernameshow").hide(); });
            $("#userinfo").mouseout(function(){
                $("#usernameshow").stop(false,true);
                ti=setTimeout(function(){
                    $("#usernameshow").hide();
                },100);
            });
            $(".messageuserreply").click(function(){
                var a=this.id;
                $("[id$='div"+a+"']").toggle();
            });
            $(".cancelinput").click(function(){$(this).parents(".messageuserreplydiv").hide(); });

        });
        function checkdata(){var a=document.getElementById("messagecontent").value; if(a==null||a==""){alert("输入不能为空！发布失败。"); return false; } }
    </script>

    </c:if>

</body>
</html>
