
<%@ page language="java" contentType="text/html; charset=utf-8"

         pageEncoding="utf-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>

<head>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

    <title>聊天室</title>

    <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>

    <script>

        function getQueryVariable(variable) {

            var query = window.location.search.substring(1);

            var vars = query.split("&");

            for (var i=0;i<vars.length;i++) {
                var pair = vars[i].split("=");
                if(pair[0] == variable){return pair[1];}
            }
            return(false);

        }

        var ws; //一个ws对象就是一个通信管道！！，只要不关浏览器，不关闭服务器就一直开着 ${sessionScope.userId}

        var target="ws://127.0.0.1:8080/CAT2_war_exploded/chatSocket?userId="+getQueryVariable("userId");

        /*alert(target);*/

        $().ready(function () {

            //alert("开始");
            //页面加载判断是否已经开启了target这个通道，如果没有开启，就开启
            if ('WebSocket' in window) {
                ws = new WebSocket(target);
                // alert(ws);
            } else if ('MozWebSocket' in window) {
                ws = new MozWebSocket(target);
                // alert(ws);
            } else {
                alert('WebSocket is not supported by this browser.');
                return;
            }

            //接收消息
            ws.onmessage = function (event) {

                eval("var result="+event.data);

                if(result.alert!=undefined){

                    $("#content").append(result.alert+"<br/>");

                }

                if(result.names!=undefined){

                    $("#userList").html("");

                    $(result.names).each(function(){

                        $("#userList").append(this+"<br/>");
                    });
                }

                if(result.from!=undefined){

                    $("#content").append(result.from+" "+result.date+
                        "   说：<br/>"+result.sendMsg+"<br/>");
                }
            };

        });

        //点击发送消息触发事件

        function send(){
            var msg = $("#msg").val();
            ws.send(msg);
            $("#msg").val("");
        }

    </script>

</head>

<body>

<h3>欢迎 ${sessionScope.userId} 来到私信界面！！
    私信格式为： 用户名#内容
</h3>

<div id="content"

     style="

        border: 1px solid black; width: 400px; height: 300px;

        float: left;

    "></div>

<div id="userList"

     style="

        border: 1px solid black; width: 100px; height: 300px;

        float:left;

    "></div>

<div style="clear: both;">

    <input id="msg" />

    <button onclick="send();">send</button>

</div>

</body>

</html>
