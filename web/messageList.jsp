<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset=utf-8" />
    <title>转会信息</title>
    <script type="text/javascript" src="js/jquery-3.3.1.js" ></script>
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <link rel="stylesheet" type="text/css" href="css/common.css">
    <link rel="stylesheet" href="css/search.css">

    <script src="js/getParameter.js"></script>
</head>
<body>
<script>
    $(function () {
        //查询用户信息
        $.get("userServlet/findOne", {}, function (data) {
            //{uid:1,name:'李四'}
            console.log(data);
            if (data.user_name != undefined){
                var msg = "欢迎回来，" + data.user_name;
                $("#span_username").html(msg);
            }
        });
    });

    $(function(){
    //给搜索按钮绑定单击事件，获取搜索输入框的内容
    $("#search-button").click(function () {
        //用户名称
        var rname = $("#search_input").val();

        var cid = getParameter("cid");
        // 跳转路径 ，拼接上rname=xxx
        location.href="${pageContext.request.contextPath}/messageList.jsp?cid="+cid+"&rname="+rname;
    });
    });



    $(function () {
        /* var search = location.search;
         //alert(search);//?id=5
         // 切割字符
         var cid = search.split("=")[1];串，拿到第二个值*/
        //获取cid的参数值
        var cid = getParameter("cid");
        //获取rname的参数值
         var rname = getParameter("rname");
        //判断rname如果不为null或者""
        // if(rname){
        //     //url解码
        //     rname = window.decodeURIComponent(rname);
        // }
        if (rname == null){
            rname = "";
        }

        //当页码加载完成后，调用load方法，发送ajax请求加载数据
        load(cid,null,rname);
    });

    function load(cid ,currentPage,rname){
        //发送ajax请求，请求route/pageQuery,传递cid
        $.get("transferInfo/pageQueryByClass",{cid:cid,currentPage:currentPage,rname:rname},function (pb) {
            //解析pagebean数据，展示到页面上

            //1.分页工具条数据展示
            //1.1 展示总页码和总记录数x
            $("#totalPage").html(pb.totalPage);
            $("#totalCount").html(pb.totalCount);

            /*
                    <li><a href="">首页</a></li>
                    <li class="threeword"><a href="#">上一页</a></li>
                    <li class="curPage"><a href="#">1</a></li>
                    <li><a href="#">2</a></li>
                    <li><a href="#">3</a></li>
                    <li><a href="#">4</a></li>
                    <li><a href="#">5</a></li>
                    <li><a href="#">6</a></li>
                    <li><a href="#">7</a></li>
                    <li><a href="#">8</a></li>
                    <li><a href="#">9</a></li>
                    <li><a href="#">10</a></li>
                    <li class="threeword"><a href="javascript:;">下一页</a></li>
                    <li class="threeword"><a href="javascript:;">末页</a></li>


             */
            var lis = "";

            var fristPage = '<li onclick="javascipt:load('+cid+',1,\''+rname+'\')"><a href="javascript:void(0)">首页</a></li>';

            //计算上一页的页码
            var beforeNum =  pb.currentPage - 1;
            if(beforeNum <= 0){
                beforeNum = 1;
            }

            var beforePage = '<li  onclick="javascipt:load('+cid+','+beforeNum+',\''+rname+'\')" class="threeword"><a href="javascript:void(0)">上一页</a></li>';

            lis += fristPage;
            lis += beforePage;
            //1.2 展示分页页码
            /*
                1.一共展示10个页码，能够达到前5后4的效果
                2.如果前边不够5个，后边补齐10个
                3.如果后边不足4个，前边补齐10个
            */

            // 定义开始位置begin,结束位置 end
            var begin; // 开始位置
            var end ; //  结束位置


            //1.要显示10个页码
            if(pb.totalPage < 10){
                //总页码不够10页

                begin = 1;
                end = pb.totalPage;
            }else{
                //总页码超过10页

                begin = pb.currentPage - 5 ;
                end = pb.currentPage + 4 ;

                //2.如果前边不够5个，后边补齐10个
                if(begin < 1){
                    begin = 1;
                    end = begin + 9;
                }

                //3.如果后边不足4个，前边补齐10个
                if(end > pb.totalPage){
                    end = pb.totalPage;
                    begin = end - 9 ;
                }
            }


            for (var i = begin; i <= end ; i++) {
                var li;
                //判断当前页码是否等于i
                if(pb.currentPage == i){

                    li = '<li class="curPage" onclick="javascipt:load('+cid+','+i+',\''+rname+'\')"><a href="javascript:void(0)">'+i+'</a></li>';

                }else{
                    //创建页码的li
                    li = '<li onclick="javascipt:load('+cid+','+i+',\''+rname+'\')"><a href="javascript:void(0)">'+i+'</a></li>';
                }
                //拼接字符串
                lis += li;
            }





            /* for (var i = 1; i <= pb.totalPage ; i++) {
                 var li;
                 //判断当前页码是否等于i
                 if(pb.currentPage == i){

                     li = '<li class="curPage" onclick="javascipt:load('+cid+','+i+')"><a href="javascript:void(0)">'+i+'</a></li>';

                 }else{
                     //创建页码的li
                     li = '<li onclick="javascipt:load('+cid+','+i+')"><a href="javascript:void(0)">'+i+'</a></li>';
                 }
                 //拼接字符串
                 lis += li;
             }*/
            var lastPage = '<li class="threeword"><a href="javascript:;">末页</a></li>';
            var nextPage = '<li class="threeword"><a href="javascript:;">下一页</a></li>';

            lis += nextPage;
            lis += lastPage;


            //将lis内容设置到 ul
            $("#pageNum").html(lis);

            /*
                <li>
                    <div class="img"><img src="" alt=""></div>
                    <div class="text1">
                        <p>【减100元 含除夕/春节出发】广州增城三英温泉度假酒店/自由行套票</p>
                        <br/>
                        <p>1-2月出发，网付立享￥1099/2人起！爆款位置有限，抢完即止！</p>
                    </div>
                    <div class="price">
                        <p class="price_num">
                            <span>&yen;</span>
                            <span>299</span>
                            <span>起</span>
                        </p>
                        <p><a href="route_detail.html">查看详情</a></p>
                    </div>
                </li>

             */

            //2.列表数据展示
            var route_lis = "";
            var del_btn = "<button type='button' onclick='del()'>";
            for (var i = 0; i < pb.list.length; i++) {
                //获取{rid:1,rname:"xxx"}
                var route = pb.list[i];
                if ('${user.status}' == '4'){
                    var li = '<li>' +
                        '<div class="img"><img src="' +
                        'images/' + route.picture1 +
                        '"  style="width: 299px;"></div>' +
                        '                        <div class="text1">' +
                        '                            <p>用户名 :'+route.user_name+'</p>' +
                        '                            <br/>' +
                        '                            <p>'+route.introduction+'</p>' +
                        '                        </div>' +
                        '                        <div class="price">' +
                        '                            <p class="price_num">' +
                        '                                <span>&yen;</span>' +
                        '                                <span>'+route.salary+'</span>' +
                        '                                <span>起</span>' +
                        '                            </p>' +
                        '                            <p><a href="selectMessage?gid='+route.id+'">查看详情</a></p>' +
                        '                            <br/><button type="button" onclick="del_transfer('+route.id+')">删除</button>'+
                        '                        </div>' +
                        '                    </li>';
                }else if( '${user.status}' == '3' && route.state !== '是' ){
                    var li = '<li>' +
                        '<div class="img"><img src="' +
                        'images/' + route.picture1 +
                        '"  style="width: 299px;"></div>' +
                        '                        <div class="text1">' +
                        '                            <p>用户名 :'+route.user_name+'</p>' +
                        '                            <br/>' +
                        '                            <p>'+route.introduction+'</p>' +
                        '                        </div>' +
                        '                        <div class="price">' +
                        '                            <p class="price_num">' +
                        '                                <span>&yen;</span>' +
                        '                                <span>'+route.salary+'</span>' +
                        '                                <span>起</span>' +
                        '                            </p>' +
                        '                            <p><a href="selectMessage?gid='+route.id+'">查看详情</a></p>' +
                        '                            <br/><button type="button" onclick="invite_transfer('+route.id+','+route.user_name+')">邀请加入</button>'+
                        '                        </div>' +
                        '                    </li>';
                }else if ('${user.status}' == '3' && route.state === '是'){
                    // alert(route.duringSell);
                    // if(route.duringSell === "1"){
                        window.Function(sellMessage(route.id));
                        var li = '<li>' +
                            '<div class="img"><img src="' +
                            'images/' + route.picture1 +
                            '"  style="width: 299px;"></div>' +
                            '                        <div class="text1">' +
                            '                            <p>用户名 :'+route.user_name+'</p>' +
                            '                            <br/>' +
                            '                            <p>'+route.introduction+'</p>' +
                            '                        </div>' +
                            '                        <div class="price">' +

                            '                            <p>拍卖中！</p>'+
                            '                         <div id="sellMessage"></div>'+
                            '                        </div>' +
                            '                    </li>';
                    // }else{
                    //     if (isBuyer(route.id)){
                    //         var li = '<li>' +
                    //             '<div class="img"><img src="' +
                    //             'images/' + route.picture1 +
                    //             '"  style="width: 299px;"></div>' +
                    //             '                        <div class="text1">' +
                    //             '                            <p>用户名 :'+route.user_name+'</p>' +
                    //             '                            <br/>' +
                    //             '                            <p>'+route.introduction+'</p>' +
                    //             '                        </div>' +
                    //             '                        <div class="price">' +
                    //             '                            <p class="price_num">' +
                    //             '                                <span>&yen;</span>' +
                    //             '                                <span>'+route.salary+'</span>' +
                    //             '                                <span>起</span>' +
                    //             '                            </p>' +
                    //             '                            <p><a href="selectMessage?gid='+route.id+'">查看详情</a></p>' +
                    //             '                            <br/><button type="button" onclick="invite_transfer('+route.id+','+route.user_name+')">邀请加入</button>'+
                    //             '                        </div>' +
                    //             '                    </li>';
                    //     }else{
                    //         var li = '<li>' +
                    //             '<div class="img"><img src="' +
                    //             'images/' + route.picture1 +
                    //             '"  style="width: 299px;"></div>' +
                    //             '                        <div class="text1">' +
                    //             '                            <p>用户名 :'+route.user_name+'</p>' +
                    //             '                            <br/>' +
                    //             '                            <p>'+route.introduction+'</p>' +
                    //             '                        </div>' +
                    //             '                        <div class="price">' +
                    //             '                            <p class="price_num">' +
                    //             '                                <span>&yen;</span>' +
                    //             '                                <span>'+route.salary+'</span>' +
                    //             '                                <span>起</span>' +
                    //             '                            </p>' +
                    //             '                            <p><span>该转会信息已由他人买走！</span></p>' +
                    //             '                        </div>' +
                    //             '                    </li>';
                    //     }
                    // }
                }else{
                    if (route.state === '是'){
                        // if(route.duringSell === "1") {
                            window.Function(sellMessage(route.id));
                            var li = '<li>' +
                                '<div class="img"><img src="' +
                                'images/' + route.picture1 +
                                '"  style="width: 299px;"></div>' +
                                '                        <div class="text1">' +
                                '                            <p>用户名 :' + route.user_name + '</p>' +
                                '                            <br/>' +
                                '                            <p>' + route.introduction + '</p>' +
                                '                        </div>' +
                                '                        <div class="price">' +

                                '                            <p>拍卖中！</p>' +
                                '                         <div id="sellMessage"></div>' +
                                '                        </div>' +
                                '                    </li>';
                        // }else{
                        //     var li = '<li>' +
                        //         '<div class="img"><img src="' +
                        //         'images/' + route.picture1 +
                        //         '"  style="width: 299px;"></div>' +
                        //         '                        <div class="text1">' +
                        //         '                            <p>用户名 :'+route.user_name+'</p>' +
                        //         '                            <br/>' +
                        //         '                            <p>'+route.introduction+'</p>' +
                        //         '                        </div>' +
                        //         '                        <div class="price">' +
                        //         '                            <p class="price_num">' +
                        //         '                                <span>&yen;</span>' +
                        //         '                                <span>'+route.salary+'</span>' +
                        //         '                                <span>起</span>' +
                        //         '                            </p>' +
                        //         '                            <p><span>该转会信息已由他人买走！</span></p>' +
                        //         '                        </div>' +
                        //         '                    </li>';
                        // }
                    }else {
                        var li = '<li>' +
                            '<div class="img"><img src="' +
                            'images/' + route.picture1 +
                            '"  style="width: 299px;"></div>' +
                            '                        <div class="text1">' +
                            '                            <p>用户名 :' + route.user_name + '</p>' +
                            '                            <br/>' +
                            '                            <p>' + route.introduction + '</p>' +
                            '                        </div>' +
                            '                        <div class="price">' +
                            '                            <p class="price_num">' +
                            '                                <span>&yen;</span>' +
                            '                                <span>' + route.salary + '</span>' +
                            '                                <span>起</span>' +
                            '                            </p>' +
                            '                            <p><a href="selectMessage?gid=' + route.id + '">查看详情</a></p>' +
                            '                        </div>' +
                            '                    </li>';
                    }
                }

                route_lis += li;
            }
            $("#route").html(route_lis);

            //定位到页面顶部
            window.scrollTo(0,0);
        });

    }

    function del_transfer(del_num) {
        //alert(del_num);
        if (confirm("确定删除该转会信息吗？")){
            $.ajax({
                url:"${pageContext.request.contextPath}/transferInfo/deleteOne",
                type:"POST",
                data:{
                    del_num : del_num
                },
                success: function (data) {
                    if (data.status === true) {
                        alert(data.message);
                        location.href="messageList.jsp";
                    }else{
                        alert(data.message);
                    }
                }
            })
        }
    }

    function invite_transfer(del_num,userName) {
        // alert(del_num);
        // alert(userName);

        if(${user != null} && ${user.id != undefined}){
            var teamId = "${user.id}";
        }
        if (confirm("确定邀请该选手加入战队吗？")){

            $.ajax({
                url:"${pageContext.request.contextPath}/transferInfo/inviteOne",
                type:"POST",
                data:{
                    del_num : del_num,
                    userName: userName,
                    teamId:teamId
                },
                success: function (data) {
                    if (data.status === true) {
                        alert(data.message);
                        location.href="messageList.jsp";
                    }else{
                        alert(data.message);
                    }
                }
            })
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
        var currentDate = year + seperator1 + month + seperator1 + strDate;
        return currentDate;
    }

    function sellMessage(id) {
        // alert(id);
        $.ajax({
            url:"${pageContext.request.contextPath}/auctionServlet/getMessage",
            type:"POST",
            data:{
                id:id,
            },
            success: function (data) {
                if (data.status === true) {
                    // alert(data.message);
                    // alert(1);
                    if (data.data.endDate < getNowFormatDate() ){
                        // alert(2);
                        $.ajax({
                            url:"${pageContext.request.contextPath}/auctionServlet/delMessage",
                            type:"POST",
                            data:{
                                transfer_id:data.data.transfer_id,
                                id:id,
                            },
                            success: function (message) {
                                location.href = "${pageContext.request.contextPath}/messageList.jsp"
                            }
                        })
                    }else{
                        var message = '                                         <p class="price_num">' +
                            '                                                      <span>&yen;</span>' +
                            '                                                     <span> '+data.data.sellMoney+'</span>' +
                            '                                                     <span>起</span> ' +
                            '                                                  </p> ' +
                            '<span>拍卖结束时间：'+data.data.endDate+
                                '</span>'+
                                '<input type="number" id="money" placeholder="出价">'+
                                '<button id="sellButton"  onclick="markUp('+data.data.sellMoney+','+id+')">加价</button>';
                                $('#sellMessage').html(message)
                    }
                }else{
                    alert(data.message);
                }
            }
        })
    }

    function markUp(last_money,id) {
        // alert(last_money);
        // alert(id);
        if (confirm("确认加价吗？")){
            var money = document.getElementById("money").value;
            var user_name = '${user.user_name}';
            // alert(money);
            // alert(user_name);
            if (last_money >= money){
                alert("您出的价格要高于现价！")
            }else {
                $.ajax({
                    url:"${pageContext.request.contextPath}/auctionServlet/reviseOne",
                    type:"POST",
                    data:{
                        money:money,
                        user_name:user_name,
                        id:id,
                    },
                    success: function (data) {
                        if (data.status === true) {
                            alert(data.message);
                            location.href="messageList.jsp";
                        }else{
                            alert(data.message);
                        }
                    }
                })
            }
        }

    }

    function isBuyer(id) {
        // alert(id);
        var flag = false;
        $.ajax({
            url:"${pageContext.request.contextPath}/auctionServlet/getMessage",
            type:"POST",
            async:false,
            data:{
                id:id
            },
            success: function (data) {
                if (data.status === true) {
                    // alert(data.message);
                    // location.href="messageList.jsp";
                    // alert(data.data.max_user);
                    if (data.data.max_user === "${user.user_name}"){

                        // alert(111);
                        flag =  true;
                        // return flag;
                    }else {
                        // alert(222);
                        // return false;
                    }
                }else{
                    // alert(data.message);
                }
            }
        });
        // alert(flag);
        return flag;
    }

</script>

<div class="shortcut">
    <c:choose>
        <c:when test="${user.status == 2}">
            <!-- 登录状态  -->
            <div class="login">
                <span id="span_username"></span>
                <a href="userMessage.jsp" class="collection">个人主页</a>
                <br/>
                <a href="${pageContext.request.contextPath}/chat.jsp?userId=${user.user_name}" class="collection">私信</a>
                <a href="${pageContext.request.contextPath}/userServlet/exit">退出</a>
            </div>
        </c:when>
        <c:when test="${user.status == 3}">
            <div class="login">
                <a href="${pageContext.request.contextPath}/findMember?team_id=${user.id}" class="collection">战队成员管理</a>
                <a href="${pageContext.request.contextPath}/userServlet/pageQuery?type=2" class="collection">关于选手</a>
                <br/>
                <a href="${pageContext.request.contextPath}/chat.jsp?userId=${user.user_name}" class="collection">私信</a>
                <a href="${pageContext.request.contextPath}/userServlet/exit">退出</a>
            </div>
<%--            <a href="${pageContext.request.contextPath}/userServlet/exit" style="float: right">退出</a>--%>
        </c:when>
        <c:when test="${user.status == 4}">
            <div class="login">
                <a href="${pageContext.request.contextPath}/userServlet/pageQuery?type=1" class="collection">审核注册信息</a>
                <a href="${pageContext.request.contextPath}/transferInfo/pageQuery" class="collection">审核转会信息</a>
                <a href="${pageContext.request.contextPath}/userServlet/pageQuery?type=2" class="collection">用户管理</a>
                <a href="${pageContext.request.contextPath}/reportServlet/pageQuery" class="collection">举报信箱</a>
                <a href="${pageContext.request.contextPath}/userServlet/exit" style="float: right">退出</a>
            </div>
<%--            <a href="${pageContext.request.contextPath}/userServlet/exit" style="float: right">退出</a>--%>
        </c:when>
        <c:otherwise>
            <!-- 未登录状态  -->
            <div class="login_out">
                <a href="login.jsp">登录/注册</a>
            </div>
        </c:otherwise>
    </c:choose>

</div>

<div class="header_wrap">
    <div class="topbar">
        <div class="logo">
            <a href="/"><img src="images/logo.jpg" alt="" style="height: 50px;width: 50px"></a>
        </div>
        <div class="search">
            <input name="" id="search_input"  type="text" placeholder="请输入用户名" class="search_input" autocomplete="off">
            <a href="javascript:;" id="search-button" class="search-button">搜索</a>
        </div>
        <div class="hottel">
            <div class="hot_pic">
                <img src="images/like.jpg" alt="">
            </div>
<%--            <c:if test="${user.status != 4}">--%>
<%--                <div class="hot_tel">--%>
<%--                    <p class="hot_time">客服热线</p>--%>
<%--                    <p class="hot_num">1008611</p>--%>
<%--                </div>--%>
<%--            </c:if>--%>
        </div>
    </div>
</div>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/login.jsp">主页</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="${pageContext.request.contextPath}/messageList.jsp?cid=1">上单 <span class="sr-only"></span></a></li>
                <li><a href="${pageContext.request.contextPath}/messageList.jsp?cid=2">打野</a></li>
                <li><a href="${pageContext.request.contextPath}/messageList.jsp?cid=3">中单</a></li>
                <li><a href="${pageContext.request.contextPath}/messageList.jsp?cid=4">射手</a></li>
                <li><a href="${pageContext.request.contextPath}/messageList.jsp?cid=5">辅助</a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<div class="page_one">
    <div class="contant">
        <div class="xinxi clearfix">
            <div class="left">
                <div class="header">
                    <span>转会信息</span>
                    <span class="jg">年薪</span>
                </div>
                <ul id="route">
                    <li>
                        <div class="img"><img src="" alt=""></div>
                        <div class="text1">
                            <p></p>
                            <br/>
                            <p></p>
                        </div>
                        <div class="price">
                            <p class="price_num">
                                <span></span>
                                <span></span>
                                <span></span>
                            </p>
                            <p><a href=""></a></p>
                        </div>
                    </li>
                    <li>
                        <div class="img"><img src="" alt=""></div>
                        <div class="text1">
                            <p></p>
                            <br/>
                            <p></p>
                        </div>
                        <div class="price">
                            <p class="price_num">
                                <span></span>
                                <span></span>
                                <span></span>
                            </p>
                            <p><a href=""></a></p>
                        </div>
                    </li>
                    <li>
                        <div class="img"><img src="" alt=""></div>
                        <div class="text1">
                            <p></p>
                            <br/>
                            <p></p>
                        </div>
                        <div class="price">
                            <p class="price_num">
                                <span></span>
                                <span></span>
                                <span></span>
                            </p>
                            <p><a href=""></a></p>
                        </div>
                    </li>
                    <li>
                        <div class="img"><img src="" alt=""></div>
                        <div class="text1">
                            <p></p>
                            <br/>
                            <p></p>
                        </div>
                        <div class="price">
                            <p class="price_num">
                                <span></span>
                                <span></span>
                                <span></span>
                            </p>
                            <p><a href=""></a></p>
                        </div>
                    </li>
                    <li>
                        <div class="img"><img src="" alt=""></div>
                        <div class="text1">
                            <p></p>
                            <br/>
                            <p>！</p>
                        </div>
                        <div class="price">
                            <p class="price_num">
                                <span></span>
                                <span></span>
                                <span></span>
                            </p>
                            <p><a href=""></a></p>
                        </div>
                    </li>
                    <li>
                        <div class="img"><img src="" alt=""></div>
                        <div class="text1">
                            <p></p>
                            <br/>
                            <p></p>
                        </div>
                        <div class="price">
                            <p class="price_num">
                                <span></span>
                                <span></span>
                                <span></span>
                            </p>
                            <p><a href=""></a></p>
                        </div>
                    </li>
                    <li>
                        <div class="img"><img src="" alt=""></div>
                        <div class="text1">
                            <p></p>
                            <br/>
                            <p></p>
                        </div>
                        <div class="price">
                            <p class="price_num">
                                <span></span>
                                <span></span>
                                <span></span>
                            </p>
                            <p><a href=""></a></p>
                        </div>
                    </li>
                    <li>
                        <div class="img"><img src="" alt=""></div>
                        <div class="text1">
                            <p></p>
                            <br/>
                            <p></p>
                        </div>
                        <div class="price">
                            <p class="price_num">
                                <span></span>
                                <span></span>
                                <span></span>
                            </p>
                            <p><a href=""></a></p>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>


<div class="page_num_inf">
    <i></i> 共
    <span id="totalPage"></span>页<span id="totalCount"></span>条
</div>
<div class="pageNum">
    <ul id="pageNum">
        <li><a href="">首页</a></li>
        <li class="threeword"><a href="#">上一页</a></li>
        <li class="curPage"><a href="#">1</a></li>
        <li><a href="#">2</a></li>
        <li><a href="#">3</a></li>
        <li><a href="#">4</a></li>
        <li><a href="#">5</a></li>
        <li><a href="#">6</a></li>
        <li><a href="#">7</a></li>
        <li><a href="#">8</a></li>
        <li><a href="#">9</a></li>
        <li><a href="#">10</a></li>
        <li class="threeword"><a href="javascript:;">下一页</a></li>
        <li class="threeword"><a href="javascript:;">末页</a></li>
    </ul>
</div>

</body>
</html>
