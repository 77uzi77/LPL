<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <title>$Title$</title>
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
  </head>

  <body>
  <div class="register-right">
    <div class="register-in">
      <a class="book popup-with-zoom-anim button-isi zoomIn animated" data-wow-delay=".5s" href="#small-dialog">注册 »</a>
    </div>
  </div>

  <div class="pop-up">
    <div id="small-dialog" class="mfp-hide book-form">

      <div class="login-form login-form-left">
        <div class="agile-row">
          <h3>登录</h3>
          <div class="login-agileits-top">
            <form id="loginForm"  method="post">
              <input type="text" class="name" name="username" Placeholder="用户名" required=""/>
              <input type="password" class="password" name="password" Placeholder="密码" required=""/>
            </form>
          </div>
        </div>
      </div>

    </div>
  </div>


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
