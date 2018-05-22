<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title></title>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<script src="<%=path%>/static/js/jquery-1.8.2.min.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="<%=path%>/static/js/jquery.md5.js"></script>
<script src="<%=path%>/static/js/app.js" type="text/javascript" charset="utf-8"></script>

<style type="text/css">
* {
	margin: 0;
	padding: 0;
}

img {
	border: none;
}

ul {
	list-style: none;
}

.clear-both:after {
	display: block;
	content: "";
	clear: both;
}

body {
	margin: 0;
	padding: 0;
	background-color: transparent;
}

.content {
	height: 100%;
	margin: 0;
	padding: 0;
	position: relative;
	width: 100%;
	background-size: cover;
}

.logo {
	margin: 34px 52px;
}

.loadcontent {
	text-align: center;
	position: absolute;
	width: 30%;
	left: 50%;
	top: 50%;
	border: 2px solid #fff;
	min-width: 300px;
}

.tittle-load {
	margin: 10% 0px;
	text-align: center;
	color: #fff;
	font-size: 26px;
}

input {
	width: 75%;
	height: 12%;
	border-radius: 5px;
	font-size: 20px;
	color: #fff;
	background: none;
	outline: none;
	box-sizing: border-box;
}

[type="submit"] {
	/*margin-top: 6%;*/
	background: #FF5E71;
	font-size: 26px;
}

#userName {
	background-image: url(<%=path%>/static/images/e_03.png);
	background-repeat: no-repeat;
	background-position: 8px center;
	margin-top: 16%;
	padding-left: 40px;
}

#userKey {
	background-image: url(<%=path%>/static/images/c_10.png);
	background-repeat: no-repeat;
	background-position: 8px center;
	padding-left: 40px;
}

.errorKey, .errorUser {
	width: 75%;
	height: 16%;
	margin: 0 auto;
	color: #fff;
	text-align: left;
	padding: 5px;
	box-sizing: border-box;
	font-size: 12px;
}

.errorUser {
	height: 6%;
}

.fixcls {
	position: fixed;
	top: 0;
	left: 0;
	z-index: -1;
	width: 100%;
	height: 100%;
}

.fixcls img {
	width: 100%;
}
</style>
<link rel="stylesheet" type="text/css" href="<%=path%>/static/css/loading.css" />

</head>

<body>

	<div class="fixcls">
		<img src="<%=path%>/static/images/back/1.jpg" />
	</div>

	<div class="loading-container" style="display: none;">
		<div class="load-block"></div>
		<div class="loading"></div>
		<div id="loading-text">登录中...</div>
	</div>

	<div class="content">
		<form action="box/init" id="loginform"></form>
		<div class="logo">
			<img src="<%=path%>/static/resource/img/HIDONG.png" />
		</div>
		<div class="loadcontent">
			<div class="tittle-load">嗨动直播后台登入系统</div>

			<input type="text" name="liveCode" id="liveCode" placeholder="请输入用户名"
				value="" />
			<div class="errorUser"></div>

			<input type="password" name="password" id="password"
				placeholder="请输入密码" value="" />
			<div class="errorKey"></div>

			<input type="button" id="userLoad" name="" value="登录" />

		</div>

	</div>

	<!--	<audio src="audio/load.mp3" id="loadVoice"></audio>-->

	<!--	<script src="js/mui.min.js" type="text/javascript" charset="utf-8"></script>-->

	<script type="text/javascript" src="<%=path%>/static/js/screen.js"></script>

	<script src="<%=path%>/static/js/user.js"></script>
	<script src="<%=path%>/static/resource/js/layer.js"></script>

	<script type="text/javascript">
		//			mui.init();

		$("#userLoad").click(function() {
			submit();
		})

		window.onload = function() {
			reset();
		}
		window.onresize = function() {
			reset();
		}

		function reset() {
			var w = $(".loadcontent").width();
			var wB = $(document).height();
			var t = $("#userLoad").width();
			$(".loadcontent").css({
				"height" : w,
				"top" : (wB - w) * 0.5,
				"margin-left" : -w * 0.5
			})
		}

		function submit() {
			$('.loading-container').show();

			var liveCode = $("#liveCode").val();
			var password = $("#password").val();
			if (liveCode.length == "0") {
				$(".errorUser").text("用户名不能为空")
			}
			;
			if (password.length == "0") {
				$(".errorKey").text("密码不能为空")
			}
			;
			if (liveCode.length > 0) {
				$(".errorUser").text("")
			}
			;
			if (password.length > 0) {
				$(".errorKey").text("")
			}
			;

			$.get("box/login", {
				liveCode : liveCode,
				password : $.md5(password)
			}, function(result) {

				//alert(JSON.stringify(result));
				$('.loading-container').hide();
				if (result.success) {
					$('#loginform').submit();
				} else {
					layer.msg(result.message);
				}

				/* $.get("/haidong-game/box/init", {}, function(result) {
					
				}).error(function() {
					layer.msg('出错了,请重试')
				}); */

			}).error(function() {
				$('.loading-container').hide();
				layer.msg('出错了,请重试')
			});

			/* $.ajax({
				url: '/box/login',
				type: "get",
				dataType: "jsonp",
				data: {
					liveCode: liveCode,
					//password: $.md5(password)
					password:password
				},
				success: function(data) {
					alert("success");

				},
				error: function(xhr, type, errorThrown) {
					$('.loading-container').hide();
					alert(".error");
				}
			}); */

		}
	</script>
</body>

</html>