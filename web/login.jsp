<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>登录</title>
<!-- custom-theme -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="" />

<!-- css files -->
<link href="css/style2.css" type="text/css" rel="stylesheet" media="all">   
<link rel="stylesheet" href="css/font-awesome.css"> <!-- Font-Awesome-Icons-CSS -->
<link href="css/popup-box.css" rel="stylesheet" type="text/css" media="all" /> <!-- popup box css -->
<link rel="stylesheet" href="css/music.css">


	<script type="text/javascript" src="js/jquery-2.1.4.min.js" ></script>

	<script type="text/javascript">
		//切换验证码
		function refreshCode(){
			//1.获取验证码图片对象
			var vcode = document.getElementById("vcode");

			//2.设置其src属性，加时间戳
			vcode.src = "${pageContext.request.contextPath}/checkCodeServlet?time="+new Date().getTime();
		}
	</script>


	<style>

		#preview, .img, img {

			width: 100px;

			height: 100px;

		}



		#preview {

			border: 1px solid #000;

		}

	</style>

	<script>
		/*
            表单校验：
                1.用户名：单词字符，长度8到20位
                2.密码：单词字符，长度8到20位
                3.email：邮件格式
                4.姓名：非空
                5.手机号：手机号格式
                6.出生日期：非空
                7.验证码：非空
         */

		//校验用户名
		//单词字符，长度8到20位
		function checkUsername() {
			//1.获取用户名值
			var username = $("#user_name").val();
			//2.定义正则
			var reg_username = /^\w{1,20}$/;

			//3.判断，给出提示信息
			var flag = reg_username.test(username);
			if(flag){
				//用户名合法
				$("#user_name").css("border","");
			}else{
				//用户名非法,加一个红色边框
				$("#user_name").css("border","1px solid red");
			}

			return flag;
		}

		//校验密码
		function checkPassword() {
			//1.获取密码值
			var password = $("#password").val();
			//2.定义正则
			var reg_password = /^\w{4,20}$/;

			//3.判断，给出提示信息
			var flag = reg_password.test(password);
			if(flag){
				//密码合法
				$("#password").css("border","");
			}else{
				//密码非法,加一个红色边框
				$("#password").css("border","1px solid red");
			}

			return flag;
		}

		//校验邮箱
		function checkEmail(){
			//1.获取邮箱
			var email = $("#email").val();
			//2.定义正则		itcast@163.com
			var reg_email = /^\w+@\w+\.\w+$/;

			//3.判断
			var flag = reg_email.test(email);
			if(flag){
				$("#email").css("border","");
			}else{
				$("#email").css("border","1px solid red");
			}

			return flag;
		}

		//校验邮箱
		function checkFindEmail(){
			//1.获取邮箱
			var email = $("#userEmail").val();
			//2.定义正则		itcast@163.com
			var reg_email = /^\w+@\w+\.\w+$/;

			//3.判断
			var flag = reg_email.test(email);
			if(flag){
				$("#email").css("border","");
			}else{
				$("#email").css("border","1px solid red");
			}

			return flag;
		}

		$("#user_name").blur(checkUsername());
		$("#password").blur(checkPassword());
		$("#email").blur(checkEmail());
		$("#userEmail").blur(checkFindEmail());


	</script>

</head>

<body>

<!-- section -->
<section class="register">
	<div class="header">
		<div class="logo">
			<a href="#">LPL转会系统</a>
		</div>
	<div class="social">
			<li><a href="#"><span class="fa fa-facebook"></span></a></li>
			<li><a href="#"><span class="fa fa-twitter"></span></a></li>
			<li><a href="#"><span class="fa fa-linkedin"></span></a></li>
			<li><a href="#"><span class="fa fa-google-plus"></span></a></li>
	</div>
		<div class="clear"></div>
	</div>
	
	<div class="register-full">
		<div class="register-left">
			<div class="register">
				<p>WelCome to you!</p>
				
				<p>著名养猪厂厂长说过：下路一直让我去，我怎么去啊.</p>
				<div class="content3">
					<ul>
						<li><a class="" href="${pageContext.request.contextPath}/messageList.jsp"> 访客进入</a></li>
						<li><a class="read" href=""> 关于我</a></li>
					</ul>
				</div>
			</div>
		</div>
		

		<div class="register-right">
			<div class="register-in">
				<a class="book popup-with-zoom-anim button-isi zoomIn animated" data-wow-delay=".5s" href="#small-dialog2">注册 »</a>
			</div>
			<div class="register-in middle">
				<a class="book popup-with-zoom-anim button-isi zoomIn animated" data-wow-delay=".5s" href="#small-dialog">登录 »</a>
			</div>
			<div class="register-in">
				<a class="book popup-with-zoom-anim button-isi zoomIn animated" data-wow-delay=".5s">Subscribe here »</a>
			</div>
			
		</div>
	
	</div>
	
	
	
	<div class="clear"> </div>
	<!-- copyright -->

	<!-- //copyright -->
</section>
<!-- //section -->



<!--删除此框后报错 -->
<%--<div class="pop-up"> --%>
<%--	<div id="small-dialog1" class="mfp-hide book-form">--%>

<%--		<div class="login-form login-form-left"> --%>
<%--			<div class="agile-row">--%>
<%--				<h3>Login to your site</h3>--%>
<%--				<span class="fa fa-lock"></span>--%>
<%--				<div class="clear"></div>--%>
<%--				<div class="login-agileits-top"> 	--%>
<%--					<form action="#" method="post"> --%>
<%--						<input type="text" class="name" name="user name" Placeholder="Username" required=""/>--%>
<%--						<input type="password" class="password" name="Password" Placeholder="Password" required=""/>--%>
<%--						<input type="submit" value="Login"> --%>
<%--					</form> 	--%>
<%--				</div> --%>
<%--				<div class="login-agileits-bottom"> --%>
<%--					<h6><a href="#">Forgot password?</a></h6>--%>
<%--				</div> --%>
<%--			</div>  --%>
<%--		</div> --%>
<%--		--%>
<%--	</div>  --%>
<%--</div>--%>
<!-- //login form popup-->

<!-- 登录-->
<div class="pop-up"> 
	<div id="small-dialog" class="mfp-hide book-form">
	
		<div class="login-form login-form-left"> 
			<div class="agile-row">
				<h3>登录</h3>
				<div class="login-agileits-top"> 	
					<form id="loginForm"  method="post">
						<input type="text" class="name" name="username" Placeholder="用户名" required=""/>
						<input type="password" class="password" name="password" Placeholder="密码" required=""/>
						<input type="text" name="verifycode" class="form-control" id="verifycode" placeholder="验证码" required=""/>
						<a href="javascript:refreshCode();"><img src="${pageContext.request.contextPath}/checkCodeServlet" title="看不清点击刷新" id="vcode"/></a>
						<button type="button" id="btn_login">登陆</button>
					</form> 		
				</div>
				<div class="register-in middle">
					<a class="book popup-with-zoom-anim button-isi zoomIn animated" data-wow-delay=".5s" href="#small-dialog3">忘记密码？</a>
				</div>
			</div>
		</div>
		
	</div>
</div>

<!-- 注册-->
<div class="pop-up"> 
	<div id="small-dialog2" class="mfp-hide book-form">
	
		<div class="login-form login-form-left"> 
			<div class="agile-row">
				<h3>注册</h3>
				<div class="login-agileits-top"> 	
					<form enctype="multipart/form-data" id="tf" onsubmit="return false" method="post" >
						<input type="text" class="name" name="user_name" Placeholder="用户名" id=user_name required=""/>
						<input type="email" class="email" name="email" Placeholder="邮箱" id=email required=""/>
						<input type="number" class="name" name="age" Placeholder="年龄" id=age required=""/>
						<input type="text" class="message" name="message" Placeholder="简介" id="message" required=""/>
						<input type="password" class="password" name="password" Placeholder="密码" id=password required=""/>
						<input type="text" class="name" name="last_team" Placeholder="上个队伍" id=last_team required=""/>
				加入LPL时间:<input type="date" class="name" name="join_time" Placeholder="加入时间" id="join_time" required=""/>
						<input type="file" name="icon"  onchange="preview(this)"><br/>
						<div id="preview"></div>
						<div class="login-check">
							<label class="checkbox"><input type="checkbox" name="checkbox" checked=""><i> </i> I Accept to the <a href="#">Terms &amp; Conditions</a></label>
						</div>
						<input type="submit" value="注册" onclick="test()">
					</form> 	
				</div>
			</div>
		</div>
		
	</div>
</div>

<!-- 找回密码 -->
<div class="pop-up">
	<div id="small-dialog3" class="mfp-hide book-form">

		<div class="login-form login-form-left">
			<div class="agile-row">
				<h3>找回密码</h3>
				<div class="login-agileits-top">
					<form id="forgetForm"  method="post">
						<input type="email" class="email" name="userEmail" id="userEmail"  Placeholder="邮箱" required=""/>
						<input type="text" name="findCode" id="findCode" placeholder="验证码" required=""/>
						<input type="password"  name="newPassword" id="newPassword" placeholder="新密码" required=""/>
						<button type="button" id="sendCode">发送验证码</button>
						<button type="button" id="btn_find">找回密码</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- //register form popup-->


<!-- js -->

<script>
	$(function(){
		$("#btn_login").click(function () {
				//2.发送ajax请求，提交表单数据
				$.post("${pageContext.request.contextPath}/customerServlet/login",$("#loginForm").serialize(),function (data) {
					// console.log(data)
					if(data.status === true){
						alert(data.message);
						window.location.href = "messageList.jsp";
					}else{
						alert(data.message);
						window.location.href = "login.jsp";
					}
				});
			}
		)
				/*console.log(verifycode)
				$.ajax({
					url : "/customerServlet/login",
					type: "POST",
					data: {
						verifycode:verifycode
					},
					dataType: 'json',
					success: function (data) {
						console.log(data)
						if (data.status === true) {
							alert(data.message);
							window.location.href = "index.jsp";
						}else{
							alert(data.message);
						}
					}
				})*/

			});



	var waitTime = 60;
	$(function () {
		$("#sendCode").click(function () {
			time(this);
			if (checkFindEmail() ){
				var userEmail = document.getElementById("userEmail").value;
				$.ajax({
					url : "${pageContext.request.contextPath}/customerServlet/sendFindCode",
					type: "POST",
					dataType: 'json',
					data: {
						userEmail:userEmail
					},
					success: function (data) {
						if (data.status === true) {
							alert(data.message);
						}else{
							alert(data.message);
						}
					}
				});
			}else{
				alert("请输入正确的邮箱号！");
			}
		})
	});

	function time(ele) {
		if (waitTime == 0) {
			// ele.disabled=false;
			ele.removeAttribute("disabled");
			ele.innerHTML = "发送验证码";
			waitTime = 60;// 恢复计时
		} else {
			ele.setAttribute("disabled",true);
			ele.innerHTML = waitTime + "秒后可以重新发送";
			waitTime--;
			setTimeout(function() {
				time(ele)// 循环调用
			}, 1000);
		}
	}

	$(function(){
		$("#btn_find").click(function () {
			if (checkFindEmail() ){
				var email = document.getElementById("userEmail").value;
				var code = document.getElementById("findCode").value;
				var newPassword = document.getElementById("newPassword").value;
				$.ajax({
					url : "${pageContext.request.contextPath}/customerServlet/changePwd",
					type: "POST",
					dataType: 'json',
					data: {
						email:email,
						code:code,
						newPassword:newPassword
					},
					success: function (data) {
						if (data.status === true) {
							alert(data.message);
						}else{
							alert(data.message);
						}
					}
				})
			}
		})
	})

</script>


<script type="text/javascript">



	function test(){

		if(checkUsername() && checkPassword() && checkEmail()){

			var form = new FormData(document.getElementById("tf"));

			//             var req = new XMLHttpRequest();

			//             req.open("post", "\\${pageContext.request.contextPath}/public/testupload", false);

			//             req.send(form);

			$.ajax({

				url:"${pageContext.request.contextPath}/customerServlet/register",

				type:"post",

				data:form,

				processData:false,

				contentType:false,

				success:function(data){
					
					// window.clearInterval(timer);
					//
					// console.log("over..");
					if (data.status){
						location.href="register_ok.html";
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

	}



	function preview(file) {

		var prevDiv = document.getElementById('preview');

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

</script>
<!-- //js -->

<!--popup-js-->
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
<!--//popup-js-->


</body>	
<!-- //body ends -->
</html>