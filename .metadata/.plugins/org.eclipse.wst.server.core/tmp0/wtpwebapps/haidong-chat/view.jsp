<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/static/js/sockjs.js"></script>
<script type="text/javascript" src="static/js/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="static/js/bootstrap.min.js"></script>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="static/css/bootstrap.min.css">
<style type="text/css">
* {
	margin: 0;
	padding: 0;
}

html {
	font-size: 10px;
}

h4 {
	text-align: center;
	font-size: 3rem;
	padding: 30px;
}

.warp {
	width: 1400px;
	overflow: hidden;
	margin: 0 auto;
}

.warp .player1 {
	float: left;
	padding: 20px;
	border: 2px solid #fff;
}

.warp .player2 {
	float: left;
	padding: 20px;
	border: 2px solid #fff;
}

.warp .player1.on {
	border-color: #ff5384;
}

.warp .player2.on {
	border-color: #ff5384;
}



.warp p {
	width:600px;
	font-size: 2rem;
	padding: 15px 0;
	text-align: center;
	word-break: break-all;
}

.warp .btn {
	cursor: pointer;
	height: 50px;
	width: 150px;
	line-height: 50px;
	background-color: #ff5384;
	border-radius: 5px;
	text-align: center;
	color: #fff;
	font-size: 2rem;
	margin-left: 50%;
	transform: translateX(-50%);
	padding:0
}

.noevent {
	pointer-events: none;
}
</style>


</head>
<body>
	
	<h4>当前直播源:<span id="liveId">Live01</span></h4>

	<div class="warp">
		<div class="player1 on">
			<div id="live1"></div>

			<p>信号源:live01</p>

			<div class="btn" onclick="live(1)">切换</div>

		</div>

		<div class="player2">
			<div id="live2"></div>
			<p>信号源:live03</p>
			<div class="btn" onclick="live(3)">切换</div>
		</div>
	</div>



	<script type="text/javascript" src="<%=basePath%>/static/ckplayer/ckplayer/ckplayer.js" charset="utf-8"></script>
	<script>
		var live01='http://live.hidongtv.com/haidongTV/live01.m3u8';
		var live03='http://live.hidongtv.com/haidongTV/live03.m3u8';
		var roomdId = 'cda64caea5624102bc6ff1471cb8fedc';
		
		$('.player1 p').html(live01);
		$('.player2 p').html(live03);
		function getCurrentLive() {
			$.get("getCurrentLive", {
				roomId : roomdId
			}, function(result) {
				$("#liveId").html(result);
				
				if(result==live01){
					$('.player2').removeClass('on');
					$('.player1').addClass('on');
					setTimeout(function(){
						CKobject.getObjectById('ckplayer_a1').changeVolume(100)
						CKobject.getObjectById('ckplayer_a2').changeVolume(0)
					},1000)
					
					
				}else if(result==live03){
					$('.player1').removeClass('on');
					$('.player2').addClass('on');
						setTimeout(function(){
							CKobject.getObjectById('ckplayer_a2').changeVolume(100)
							CKobject.getObjectById('ckplayer_a1').changeVolume(0)
					},1000)
				}
				
				
			});
		}
		getCurrentLive();

		function live(x) {
			var c;
			x == 1 ? c = live01
					: c = live03;
			$.get("changeVideo", {
				roomId : roomdId,
				videoUrl : c
			},function(data){
				console.log(data);
				if(data=="success"){
					//$('#liveId').html('Live0' + x);
					$('#liveId').html(c);
					if(x==3){
						$('.player1').removeClass('on');
						$('.player2').addClass('on');
						CKobject.getObjectById('ckplayer_a2').changeVolume(100)
						CKobject.getObjectById('ckplayer_a1').changeVolume(0)
								
					}else{
						$('.player2').removeClass('on');
						$('.player1').addClass('on');
						CKobject.getObjectById('ckplayer_a1').changeVolume(100)
						CKobject.getObjectById('ckplayer_a2').changeVolume(0)
					}
				}
				
			});
			$.get("changeVideo", {
				roomId : roomdId,
				videoUrl : c
			},function(data){
				console.log(data);
				if(data=="success"){
					//$('#liveId').html('Live0' + x);
					$('#liveId').html(c);
					if(x==3){
						$('.player1').removeClass('on');
						$('.player2').addClass('on');
						CKobject.getObjectById('ckplayer_a2').changeVolume(100)
						CKobject.getObjectById('ckplayer_a1').changeVolume(0)
					}else{
						$('.player2').removeClass('on');
						$('.player1').addClass('on');
						CKobject.getObjectById('ckplayer_a1').changeVolume(100)
						CKobject.getObjectById('ckplayer_a2').changeVolume(0)
					}
				}
			});
			
		}
	</script>

	<script type="text/javascript">
		var flashvars = {
			//f:'http://img.ksbbs.com/asset/Mon_1605/0ec8cc80112a2d6.mp4',
			f : '<%=basePath%>/static/ckplayer/m3u8.swf',
			a : live01,
			c : 0,
			s : 4,
			i : 'http://www.ckplayer.com/static/images/cqdw.jpg',
			lv : '1',
			p : '1'
		};
		var params = {
			bgcolor : '#FFF',
			allowFullScreen : true,
			allowScriptAccess : 'always',
			wmode : 'transparent'
		};
		CKobject.embedSWF("<%=basePath%>/static/ckplayer/ckplayer/ckplayer.swf", 'live1', 'ckplayer_a1',
				'600', '400', flashvars, params);

		var flashvars1 = {
			//f:'http://img.ksbbs.com/asset/Mon_1605/0ec8cc80112a2d6.mp4',
			f : '<%=basePath%>/static/ckplayer/m3u8.swf',
			a : live03,
			c : 0,
			s : 4,
			i : 'http://www.ckplayer.com/static/images/cqdw.jpg',
			lv : '1',
			p : '1'
		};
		var params1 = {
			bgcolor : '#FFF',
			allowFullScreen : true,
			allowScriptAccess : 'always',
			wmode : 'transparent',
			logo : 'null',
			controlBarShow : false
		};
		CKobject.embedSWF("<%=basePath%>/static/ckplayer/ckplayer/ckplayer.swf", 'live2', 'ckplayer_a2',
				'600', '400', flashvars1, params1);
		

		function ckmarqueeadv() {
			return ''
		}
	</script>

</body>
</html>