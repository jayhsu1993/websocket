<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sockjs.js"></script>
<script type="text/javascript" src="static/js/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="static/js/bootstrap.min.js"></script>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="static/css/bootstrap.min.css">
<script type="text/javascript">
	
	
	var websocket =null;
	if (window['WebSocket']) {
		//alert( window.location.host);
		//WebSocket连接
		websocket = new WebSocket('ws://' + window.location.host + '/haidong-chat/marco?roomId=5ac20f85e70c4a0eb19802ea28ae84e0&type=1');
	}
	else
		//SockeJS连接
		websocket = new SockJS('/haidong-h5/js/marco');
		

	websocket.onopen = function() {
		//alert('Opening');
		//websocket.send("");
		//sayMarco();
	};
	
	websocket.onmessage = function(e) {
		//alert('Received message: '+e.data);
		$('#msg').append("<li>"+e.data+"</li>");
		var data = $.parseJSON(e.data);
		$('#userName').html(data.data.userName);
	};
	
	websocket.onclose = function() {
		alert('Closing');
	};
	
	function sayMarco() {
		alert('Sending Marco!');
		websocket.send("Marco!");
	}  
	
	function checkType(){
		
	}
	
	
	function sendText(){
		//websocket.send($('input').val());
		//if($('text'))S
		$.get("chat",{msg:$('input').val(),userId:"68065618dccf41eb8f3c502a5d1ed509",roomId:"5ac20f85e70c4a0eb19802ea28ae84e0",money:1000});
		$('input').val('');
	}
	
	function sendGift(){
		//websocket.send($('input').val());
		$.get("gift",{giftId:$('#gift').val(),openId:"oz_1CwX7c-Nft60uNSo1kRfJjnNU",roomId:1,text:"百年好合"});
		console.log($('#gift').val());
	}
	
	
	
	function getAllUsers() {
		 $.ajax({  
           url:'http://192.168.1.107:8080/haidong-chat/draw?liveid=5ac20f85e70c4a0eb19802ea28ae84e0',  
           type: "get",   
           dataType: "jsonp",
           jsonp:"callback",//服务端用于接收callback调用的function名的参数   
           jsonpCallback: "success_jsonpCallback", //callback的function名称,服务端会把名称和data一起传递回来  
           success: function (data) { 
           	var json =  JSON.stringify(data);
           	alert(json);
           },  
           error: function(){alert('Error123');}  
   });  
	}	
</script>
	
</head>
<body>
	<div id="userInfo">
		
	</div>
	<button type="button" class="btn btn-primary" onclick="getAllUsers();" id="draw">发送</button><br>
	
	<input class="form-controll" type="text">
	<button type="button" class="btn btn-primary" onclick="sendText()" id="text">发送</button><br>
	<select	id="gift" class="form-controll">
		<option value="flower">鲜花</option>
		<option value="car">跑车</option>
		<option value="love">爱心</option>
	</select>
	<button type="button" class="btn btn-primary"  onclick="sendGift()">发送礼物</button><br>
	<ul id="msg">
	</ul>
</body>
</html>