<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- HTML5文档-->
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
    <title>添加用户</title>

    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <script type="text/javascript" src="js/jquery-3.3.1.js" ></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <script src="js/getParameter.js"></script>

</head>
<body>
<div class="container">
    <center><h3>提交转会信息</h3></center>
    <form  id="submitTransfer" enctype="multipart/form-data"  method="post">

        <div class="form-group">
        <textarea class="name" name="introduction" Placeholder="个人介绍" required=""></textarea>
        </div>

<%--        <div class="form-group">--%>
<%--            <label for="name">姓名：</label>--%>
<%--            <input type="text" class="form-control" id="name" name="name" placeholder="请输入姓名">--%>
<%--        </div>--%>

<%--        <div class="form-group">--%>
<%--            <label>性别：</label>--%>
<%--            <input type="radio" name="gender" value="男" checked="checked"/>男--%>
<%--            <input type="radio" name="gender" value="女"/>女--%>
<%--        </div>--%>

        <div class="form-group">
            <label for="age">年龄：</label>
            <input type="number" class="form-control" id="age" name="age" placeholder="请输入年龄">
        </div>

        <div class="form-group">
            <label for="address">位置：</label>
            <select name="cid" class="form-control" id="address">
                <option value="1">上单</option>
                <option value="2">打野</option>
                <option value="3">中单</option>
                <option value="4">射手</option>
                <option value="5">辅助</option>
            </select>
        </div>

        <div class="form-group">
            <label for="age">年薪：</label>
            <input type="number" class="form-control" id="salary" name="salary" placeholder="请输入年薪">
        </div>
        是否参与拍卖：
        <input type="radio"  name="state" value="是" /> 是
        <input type="radio"  name="state" value="否" checked/> 否
<%--        <div class="form-group">--%>
<%--            <label for="qq">QQ：</label>--%>
<%--            <input type="text" class="form-control" id="qq" name="qq" placeholder="请输入QQ号码"/>--%>
<%--        </div>--%>

<%--        <div class="form-group">--%>
<%--            <label for="email">Email：</label>--%>
<%--            <input type="text" class="form-control" id="email" name="email" placeholder="请输入邮箱地址"/>--%>
<%--        </div>--%>
        <div class="form-group">
            <label for="address">高光时刻：</label>
        <input type="file" name="picture1"  onchange="preview(this,1)"><br/>
        <div id="preview1"></div>
        <input type="file" name="picture2"  onchange="preview(this,2)"><br/>
        <div id="preview2"></div>
        <input type="file" name="picture3"  onchange="preview(this,3)"><br/>
        <div id="preview3"></div>
        </div>
        <button type="button" id="submit">提交</button>
<%--        <div class="form-group" style="text-align: center">--%>
<%--            <input class="btn btn-primary" type="submit" id="submit" value="提交" />--%>
<%--            <input class="btn btn-default" type="reset" value="重置" />--%>
<%--&lt;%&ndash;            <input class="btn btn-default" type="button" value="返回" />&ndash;%&gt;--%>
<%--        </div>--%>
    </form>
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
            if(confirm("您确定要转会该选手吗？")){
                var user_name = getParameter("user_name");
                var form = new FormData(document.getElementById("submitTransfer"));
                // alert(222);
                $.ajax({

                    url:"${pageContext.request.contextPath}/userServlet/transfer?user_name="+user_name,

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
                            // alert(111);
                            location.href = "${pageContext.request.contextPath}/findMember?team_id=${user.id}"
                        }else{
                            alert(data.message);
                        }

                    },

                    error:function(e){

                        alert("错误！！");

                        // window.clearInterval(timer);
                    }

                });
            }
        })
    });
</script>



</body>
    <style>

        #preview1,#preview2,#preview3, .img,img{
            width: 100px;

            height: 100px;
        }

        #preview1,#preview2,#preview3 {

            border: 1px solid #000;

        }

    </style>
</html>