<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>个人主页</title>
    <!-- custom-theme -->
<%--    <meta name="viewport" content="width=device-width, initial-scale=1">--%>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="" />

    <!-- css files -->
<%--    <link href="css/style2.css" type="text/css" rel="stylesheet" media="all">--%>
    <link rel="stylesheet" href="css/font-awesome.css"> <!-- Font-Awesome-Icons-CSS -->
    <link href="css/popup-box.css" rel="stylesheet" type="text/css" media="all" /> <!-- popup box css -->
    <link rel="stylesheet" href="css/music.css">


    <script type="text/javascript" src="js/jquery-2.1.4.min.js" ></script>
</head>
<body>


<div class="personal">
        <div class="personal-item">
            <span>用户名</span>
            <span>${user.user_name}</span>
        </div>
        <div class="personal-item">
            <span>邮箱</span>
            <span>${user.email}</span>
        </div>
        <div class="personal-item">
            <span>年龄</span>
            <span>${user.age}</span>
        </div>
        <div class="personal-item">
            <span>头像</span>
            <span><img src="images/${user.icon}" style="width: 50px;height: 50px;"/></span>
        </div>
        <div class="personal-item">
            <span>个人简介</span>
            <span>${user.message}</span>
        </div>
        <div class="personal-item">上个战队
                <span>${user.last_team}</span>
        </div>
        <div class="personal-item">
            <span>加入LPL时间</span>
            <span>${user.join_time}</span>
        </div>
    </div>


    <div class="register-right">
        <div class="register-in">
            <a class="book popup-with-zoom-anim button-isi zoomIn animated" data-wow-delay=".5s" href="#small-dialog">提交/修改转会信息 »</a>
        </div>
    </div>

    <div class="register-right">
        <div class="register-in">
            <a class="book popup-with-zoom-anim button-isi zoomIn animated" data-wow-delay=".5s" href="#small-dialog2">修改个人信息 »</a>
        </div>
    </div>

    <div class="pop-up">
        <div id="small-dialog" class="mfp-hide book-form">
            <div class="login-form login-form-left">
                <div class="agile-row">
            <div class="login-agileits-top">
                <form id="submitTransfer" enctype="multipart/form-data" method="post">
                    <textarea class="name" name="introduction" Placeholder="个人介绍" required=""></textarea>
                    位置选择:<select  name="cid">
                        <option value="1">上单</option>
                        <option value="2">打野</option>
                        <option value="3">中单</option>
                        <option value="4">射手</option>
                        <option value="5">辅助</option>
                    </select>
                    <br/>
                    是否参与拍卖：
                    <input type="radio" name="state" value="是" /> 是
                    <input type="radio" name="state" value="否" checked/> 否
                    <br/>
                    <input type="number" name="salary" placeholder="年薪" required=""/>
                    <div>高光时刻：</div>
                    <input type="file" name="picture1"  onchange="preview(this,1)"><br/>
                    <div id="preview1"></div>
                    <input type="file" name="picture2"  onchange="preview(this,2)"><br/>
                    <div id="preview2"></div>
                    <input type="file" name="picture3"  onchange="preview(this,3)"><br/>
                    <div id="preview3"></div>
                    <button type="button" id="submit">提交</button>
                </form>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <div class="pop-up">
        <div id="small-dialog2" class="mfp-hide book-form">
            <div class="login-form login-form-left">
                <div class="agile-row">
            <div class="login-agileits-top">
                <form id="reviseMessage" enctype="multipart/form-data" method="post">
                    <select name="type" id="revise_select" onchange="revise_type()">
                        <option selected>用户名</option>
                        <option>头像</option>
                        <option>年龄</option>
                        <option>简介</option>
                        <option>上个队伍</option>
                        <option>加入LPL时间</option>
                    </select>
                    <span id="revise_input"><input type='text' name='user_name' id='data' required='' /></span>
                    <button type="button" id="revise_btn">修改</button>
                </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

<script>
    function preview(file,number) {

        var prevDiv = document.getElementById('preview'+number);

        if (file.files && file.files[0]) {

            var reader = new FileReader();

            reader.onload = function(evt) {

                prevDiv.innerHTML = '<img src="' + evt.target.result + '" />';

            }

            reader.readAsDataURL(file.files[0]);

        } else {

            prevDiv.innerHTML = '<div class="img" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\'"></div>';

        }

    }

    $(function () {
        $("#submit").click(function () {
            var form = new FormData(document.getElementById("submitTransfer"));
            $.ajax({

                url:"${pageContext.request.contextPath}/userServlet/transfer",

                type:"post",

                data:form,

                processData:false,

                contentType:false,

                success:function(data){

                    // window.clearInterval(timer);
                    //
                    // console.log("over..");
                    if (data.status){
                        alert(data.message);
                    }else{
                        alert(data.message);
                    }

                },

                error:function(e){

                    alert("错误！！");

                    // window.clearInterval(timer);
                }

            });
        })
    });

    function revise_type() {
        var type  = $("#revise_select").val();
        //alert(type);
        //alert(1111);
        var input_type;
        switch (type) {
            case "用户名":
                input_type = "<input  type='text' name='user_name' id='data' required='' />";
                $("#revise_input").html(input_type);
                break;
            case "头像":
                input_type = "<input type='file' name='icon' id='data' required='' />";
                $("#revise_input").html(input_type);
                break;
            case "年龄":
                input_type = "<input type='number' name='age' id='data' required='' />";
                $("#revise_input").html(input_type);
                break;
            case "简介":
                input_type = "<input type='text' name='message' id='data' required='' />";
                $("#revise_input").html(input_type);
                break;
            case "上个队伍":
                input_type = "<input type='text' name='last_team' id='data' required='' />";
                $("#revise_input").html(input_type);
                break;
            case "加入LPL时间":
                input_type = "<input type='date' name='join_time' id='data' required='' />";
                $("#revise_input").html(input_type);
                break;

        }
    }

    $(function () {
        $("#revise_btn").click(function () {
            // var type  = $("#revise_select").val().value;
            // var data = document.getElementById("data").value;
            var data = new FormData(document.getElementById("reviseMessage"));
            $.ajax({
                url:"${pageContext.request.contextPath}/userServlet/reviseMessage",
                type: "POST",
                data:data,
                processData:false,
                contentType:false,

                success: function (data) {
                    if (data.status === true) {
                        alert(data.message);
                    }else{
                        alert(data.message);
                    }
                }
            })
        })
    })


</script>


    <script src="js/jquery.magnific-popup.js" type="text/javascript"></script>
    <script>
        $(document).ready(function() {
            $('.popup-with-zoom-anim').magnificPopup({
                type: 'inline',
                fixedContentPos: false,
                fixedBgPos: true,
                overflowY: 'auto',
                closeBtnInside: true,
                preloader: false,
                midClick: true,
                removalDelay: 300,
                mainClass: 'my-mfp-zoom-in'
            });

        });
    </script>



</body>
</html>

<style>
    .personal{
        margin: 20px 100px;
        border: 2px solid;
    }
    .personal-item{
        height: 50px;
        border-bottom: 2px solid;
        line-height: 50px;
        text-align: center;
    }


    #preview1,#preview2,#preview3, .img,img{
        width: 100px;

        height: 100px;
    }

    #preview1,#preview2,#preview3 {

        border: 1px solid #000;

    }

</style>
