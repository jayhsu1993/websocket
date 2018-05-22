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
		<style type="text/css">
body,html{margin:0;padding:0;height:100%;font-family:'微软雅黑',Arial,Helvetica,sans-serif}
p{margin:0}
.login-background{background:url(<%=path%>/static/resource/img/business/qiye.png) 0 0 no-repeat;background-size:cover;width:100%;height:100%}
.login-background>div:first-of-type{position:absolute;left:50%;margin-left:-577.5px;top:200px}
.login-background>div:first-of-type{width:1155px}
.title{float:left;padding-top:27px}
.login{float:right;background-color:rgba(0,0,0,.2)}
.login-background>div:first-of-type:after{content:'';display:block;clear:both}
.login>img{padding:30px 83px}
.login>div:after{content:'';display:block;clear:both}
.login>div{border-top:1px solid #2db5ff;padding:30px 36px 32px 36px}
.login form>div{margin:7px 0}
.login form>div:after{content:'';display:block;clear:both}
.login form>div>div{float:left;width:49px;padding:7px 0 6px 0;text-align:center;background-color:#aaabab}
.login form>div>input{float:left;padding:14px 0 14px 9px;width:218px;outline:0;border:0}
.login a{display:block;text-align:center;margin:34px 0 21px 0;padding:12px 0;background-color:#0079ce;color:#fff;border-radius:16px}
.login p{color:#fff}
.left-bottom{position:absolute;right:94px;bottom:70px;display:none}
		</style>
	</head>

	<body>
		<div id="root" class="login-background">
			<div>
				<div class="title">
					<div><img src="<%=path%>/static/resource/img/business/background@2x.png" alt="嗨动大屏幕"></div>
				</div>
				<div class="login">
					<img src="<%=path%>/static/resource/img/business/welcomelogoin@2x.png" alt="欢迎登录企业版">
					<div>
						<form method="post" action="">
							<div>
								<div><img src="<%=path%>/static/resource/img/business/user@2x.png" alt="用户名"></div>
								<input type="text" placeholder="请输入账号" id="liveCode">
							</div>
							<div>
								<div><img src="<%=path%>/static/resource/img/business/password@2x.png" alt="密码"></div>
								<input type="password" placeholder="请输入密码" id="password">
							</div>
							<a href="#" javascript=":;" onclick="submit()">登录</a>
							<p>如需使用企业版请联系 400-108-1516</p>
						</form>
					</div>
				</div>
			</div>
			<div class="left-bottom"><img src="<%=path%>/static/resource/img/business/right_bottom_corner.png" alt="嗨动大屏幕"></div>
		</div>
		<form action="init" id="loginform"></form>

		<script src="<%=path%>/static/resource/js/layer.js"></script>

		<script type="text/javascript">
			function submit() {
				var liveCode = $("#liveCode").val();
				var password = $("#password").val();
				if(liveCode.length == "0") {
					layer.msg("请输入用户名");
					return;
				};
				if(password.length == "0") {
					layer.msg("请输入密码");
					return;
				};
				
				var loading = layer.msg('登陆中', {
					icon: 16,
					shade: 0.01,
					time: 0
				});

				$.get("login", {
					liveCode: liveCode,
					password: $.md5(password)
				}, function(result) {
					layer.close(loading);
					if(result.success) {
						$('#loginform').submit();
					} else {
						layer.msg(result.message);
					}
				}).error(function() {
					layer.close(loading);
					layer.msg('出错了,请重试')
				});
			}
		</script>
	</body>

</html>