<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>发送成功</title>
    <script src="<%=path%>/static/js/mui.min.js"></script>
    <link href="<%=path%>/static/css/mui.min.css" rel="stylesheet"/>
    <link href="<%=path%>/static/css/global.css" rel="stylesheet"/>
    <link href="<%=path%>/static/css/ucenter.css" rel="stylesheet"/>
    <script type="text/javascript" charset="UTF-8">
      	mui.init();
    </script>
</head>
<body>
	<header class="mui-bar mui-bar-nav">
		<a href="past.html" class="mui-icon  mui-pull-left"><span class="iconback"></span></a>
		<h1 class="mui-title color">发红包</h1>
		<a class="mui-icon mui-pull-right"><span class="icon"></span></a>
	</header>
	<div class="mui-content sendsuccess wrap">
		<div><img src="<%=path%>/static/images/sure.png" class="iconsure"/></div>
		<p class="past">发送成功，红包金额:</p>
		<p class="ft24"><em ><img src="<%=path%>/static/images/past.png" style="width: 15px; height: 18px;"/></em>88888</p>
		<a class="past" href="<%=path%>/wechatPay/${openid}">观看直播</a>
	</div>
</body>
</html>