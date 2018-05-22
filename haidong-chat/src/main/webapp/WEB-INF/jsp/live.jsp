<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<title>直播</title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link href="<%=path%>/static/css/mui.min.css" rel="stylesheet" />
		<link href="<%=path%>/static/css/global.css" rel="stylesheet"/>
    	<link href="<%=path%>/static/css/live.css" rel="stylesheet" />
    	<script src="<%=path%>/static/js/jquery-1.11.2.min.js"></script>
    	<script src="<%=path%>/static/js/haidong.js"></script>
	</head>
	<script src="<%=path%>/static/js/mui.min.js"></script>
	<script type="text/javascript">
		mui.init();
		/*消息滚动*/
		/*window.onload=function(){
		    var oUl=oDiv.getElementsByTagName('ul')[0];
		    oUl.innerHTML=oUl.innerHTML+oUl.innerHTML;
		    oUl.style.Height=oUl.children.length*oUl.children[0].offsetHeight+'px';
		    setInterval(function(){
		        oUl.style.top=oUl.offsetTop-1+'px';
		        if(oUl.offsetTop<=  -oUl.offsetHeight/2){
		            oUl.style.top=0;
		        }
		    },40);
		};*/
		$(function($){
			 user = "${user}";
			 pwd = "${pwd}";
			var id; 
			var headimgurl;
			var url=location.search; 
			var Request = new Object(); 
			if(url.indexOf("?")!=-1) 
			{ 
			var str = url.substr(1); //去掉?号 
			 strs = str.split("&"); 
			for(var i=0;i<strs.length;i++) 
			{ 
			Request[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]); 
			} 
			} 
			id=Request["id"];
			headimgurl=Request["headimgurl"];
			$("#header img").attr("src",headimgurl);
			//alert(id);
			//alert(headimgurl);
			
			var videoheight=$(window).height()-180+'px';
			$("#video1").css("height",videoheight);   
			$('.zhezhao').css("height",videoheight);
			$("#infoinput").click(function() {
				$(".live-boticon").css("display","none");
				$(".messageframe").css("display","block");
				$('input').focus();
			})
			/*点击发送按钮*/
			$('#sendgift').click(function(){
				var umoney=parseInt($("span#umoney").html());
				var gvalue=parseInt($("li.select_gift a").attr("data"));
				//alert(gvalue);
	    		if(gvalue>umoney){
			  	//跳转充值页面
			  	mui.openWindow({
				  		url:"<%=path%>/wechatPay/",
				  	})
			  }else if(gvalue<umoney||gvalue==umoney){
			  	topay();
			  }
			  function topay(){
					//$.ajax({
						//url:'http://localhost:8080/haidong-h5/money/p',
						//type:'POST',
						//datatype:'json',
						//data:{gold:gvalue,id:'111111'},
						//success :function (result){  
						   //     alert("111");  
						    //},error:function (error){  
						     //   alert("222");  
							//}
						//});
	   			 }
			  
				/*获取divClassName*/
				var classname = $("li.select_gift").attr("divClassName");
				/*对应动画弹框的显示*/
				$("."+classname).css("display","block");
				$("#present").css("display","none");
				$("."+classname).delay(2000).fadeOut();
				$('.mui-backdrop-action.mui-backdrop.mui-active, .mui-bar-backdrop.mui-backdrop.mui-active').css("opacity","0");

				/* setTimeout(function() {
					$("."+classname).css("display","none");
					$('.mui-backdrop-action.mui-backdrop.mui-active, .mui-bar-backdrop.mui-backdrop.mui-active').css("opacity","0");
				}, 2000);*/
		    });
		    /*点击礼物出现选中样式*/
		  	$('#present a.colrfff').each(function(){
		    	$(this).click(function(){
		    		$('#present ul li').removeClass("select_gift");
		    		$(this).parent().addClass("select_gift");
		    	})
		   })
		
			
		});
		
//		function delpre(){
//			$.ajax({
//					url:'http://localhost:8080/haidong-api/live',
//					type:'POST',
//					datatype:'json',
//					data:{preson:'1',id:id,_method:'PUT'},
//					success :function (result){  
//					        alert("111");  
//					    },error:function (error){  
//					        alert("222");  
//						}
//					});
//			
//		}
		
	</script>
	<body >
		<div class="live-video">
  			<!--<img src="images/test.png" style=" width: 100%;"/>-->
  			<!--视频-->
  			<video width="100%" id="video1" autoplay="autoplay">
				  <source src="<%=path%>/static/mp4/573ecf494a0c63517.mp4"  type="video/mp4">
	  		</video>
	  	</div>
	  	<div class="live-page">
	  		<!--<div style="margin-top:115px; position: absolute;">
  				<button onclick="playPause()" class="mui-btn mui-btn--primary mui-btn--outlined">播放</button> 
  			</div>-->
	  		<div class="zhezhao" style="" style="display: none;"></div>
			<div class="mui-contents">
				<!--消息内容-->
				<div class="live-message">
				    <div id="common">
				        <!--<ul style="top: -77px;">可滚动-->
				        <ul id="msg" style="top: -77px;">
				            
				        </ul>
				    </div>
				</div>
				
				<!--底部图标-->
				<div class="live-boticon">
					<ul>
						<li class="col-xs-3 text-center" id="infoinput">
							<a data="20"><img src="<%=path%>/static/images/iconinfo.png" class="img42" style="-webkit-animation: animBg74 2.5s linear 0ms infinite alternate;animation: animBg74 2.5s linear 0ms infinite alternate;"/></a></li>
						<li class="col-xs-5 text-center"><a href="#present"><img src="<%=path%>/static/images/icongift.png"  class="img42" style="-webkit-animation: animBg74 2.5s linear 0ms infinite alternate;animation: animBg74 2.5s linear 0ms infinite alternate"/></a></li>
						<li class="col-xs-2 text-right">&nbsp;</li>
						<li class="col-xs-2 text-center"><a href="#liveshare"><img src="<%=path%>/static/images/iconshare.png"  class="img42"/></a></li>
					</ul>
				</div>
				
				<!--消息框-->
				<div class="messageframe" style="display: none;">
					<ul>
						<li class="messageinput"><input type="text" id="input-msg" placeholder="和大家说点什么"/></li>
						<li class="messagebtn"><a class="mesbtn" id="btn-send">发送</a></li>
					</ul>	
				</div>
				
				<!--分享弹框-->
				<div id="liveshare" style="padding: 30px; display: none; background: rgb(255, 255, 255);" class="mui-popover mui-popover-action mui-popover-bottom text-center mui-active">
					<a class="col-xs-6" href="#" class="colrfff"><img src="<%=path%>/static/images/weixin.png" class="img60"/><br/>微信</a>
					<a class="col-xs-6" href="#" class="colrfff"><img src="<%=path%>/static/images/pengyouquan.png" class="img60"/><br/>朋友圈</a>
				</div>
				
			<!--礼物弹框-->
				<div id="present" style="display: none; background-color: rgba(87, 87,87, .7)" class="mui-popover mui-popover-action mui-popover-bottom text-center mui-active">
					<ul style="padding: 10px 0px; color: #fff;">
						 <!--id="look-gift" -->
						<li divClassName = "find_datacon" class="col-xs-3 text-center" id="look-gift1" ><a href="#" class="colrfff" data="30"><img src="<%=path%>/static/images/gift1.png" class="img55"/><br/>666嗨币红包<br/>34343<span class="iconhaibi"></span></a></li>
						<li divClassName = "find_datacon1" class="col-xs-3 text-center" id="look-gift2"><a href="#" class="colrfff" data="50"><img src="<%=path%>/static/images/gift2.png" class="img55"/><br/>166嗨币红包<br/>34343<span class="iconhaibi"></span></a></li>
						<li divClassName = "find_datacon2" class="col-xs-3 text-center" id="look-gift3"><a href="#" class="colrfff" data="50"><img src="<%=path%>/static/images/gift3.png" class="img55"/><br/>花好月圆<br/>34343<span class="iconhaibi"></span></a></li>
						<li divClassName = "find_datacon3" class="col-xs-3 text-center" id="look-gift4"><a href="#" class="colrfff" data="50"><img src="<%=path%>/static/images/gift4.png" class="img55"/><br/>天使之心<br/>34343<span class="iconhaibi"></span></a></li>
						<li divClassName = "find_datacon4" class="col-xs-3 text-center" id="look-gift5"><a href="#" class="colrfff" data="50"><img src="<%=path%>/static/images/gift5.png" class="img55"/><br/>烟花<br/>34343<span class="iconhaibi"></span></a></li>
						<li divClassName = "find_datacon5" class="col-xs-3 text-center" id="look-gift6"><a href="#" class="colrfff" data="50"><img src="<%=path%>/static/images/gift6.png" class="img55"/><br/>法拉利<br/>34343<span class="iconhaibi"></span></a></li>
						<li divClassName = "find_datacon6" class="col-xs-3 text-center" id="look-gift7"><a href="#" class="colrfff" data="50"><img src="<%=path%>/static/images/gift7.png" class="img55"/><br/>游轮<br/>34343<span class="iconhaibi"></span></a></li>
						<li divClassName = "find_datacon7" class="col-xs-3 text-center" id="look-gift8"><a href="#" class="colrfff" data="50"><img src="<%=path%>/static/images/gift8.png" class="img55"/><br/>丘比特之心<br/>34343<span class="iconhaibi"></span></a></li>
						<li class="col-xs-6 text-left"  style="padding-left: 10px; line-height: 46px;">
							<a class="giftfont" href="pay.jsp">&nbsp;&nbsp;充值&nbsp;&nbsp;</a><span id="umoney">144444</span>&nbsp;&nbsp;<span class="iconhaibi1"></span><span class="iconjiantou"></span>
						</li>
						<li class="col-xs-6 text-right">
							<div style="width: 100px; text-align: right; display: inline-block;">
								<a class="gift" id="sendgift" href="javascript:void(0)">发送</a>
							</div>
						</li>
					</ul>
				</div>
				
				<!--左上角头像-->
				<div class="live-hea" id="header">
					<img src="<%=path%>/static/images/indexheader.png" class="img42">
				</div>
				
				<!--右上角关闭按钮-->
				<div class="live-close">
					<a href="index.html" onclick="delpre()"><img src="<%=path%>/static/images/close.png" class="img30"></a>
				</div>
				<!--礼物1-->
				<div class="find_datacon" style="display: none;">
					<div class="data">
						<img src="<%=path%>/static/images/car1.png" id="square" id="square" class="rotate animated" />
					</div>
				</div>
				<!--礼物2-->
				<div class="find_datacon1" style="display: none;">
					<div class="data">
						<img src="<%=path%>/static/images/car2.png" id="square" class="bounceInRight animated" />
					</div>
				</div>
				<!--礼物3翻转-->
				<div class="find_datacon2" style="display: none;">
					<div class="data">
						<img src="<%=path%>/static/images/car3.png" id="square" class="flip animated"/>
					</div>
				</div>
				<!--由小变大-->
				<div class="find_datacon3" style="display: none;">
					<div class="data">
						<img src="<%=path%>/static/images/car4.png" id="square" class="flip animated" />
					</div>
				</div>
				<div class="find_datacon4" style="display: none;">
					<div class="data">
						<img src="<%=path%>/static/images/car4.png" id="square" class="flip animated" />
					</div>
				</div>
				<div class="find_datacon5" style="display: none;">
					<div class="data">
						<img src="<%=path%>/static/images/car6.png" id="square" class="bounceInLeft animated"/>
					</div>
				</div>
				<div class="find_datacon6" style="display: none;">
					<div class="data">
						<img src="<%=path%>/static/images/car7.png" id="square" class="bounceInRight animated" />
					</div>
				</div>
				<div class="find_datacon7" style="display: none;">
					<div class="data">
						<img src="<%=path%>/static/images/car8.png" id="square" class="flip animated"/>
					</div>
				</div>
			</div>
		</div>
	</body>								
	<script type="text/javascript">
		var myVideo=document.getElementById("video1");
		function playPause()
		{
		if (myVideo.paused){
		  myVideo.play(); 
		  
		}else{ 
		  myVideo.pause(); 
		  
		} 
		}
		
		
</script>
<script src="<%=path%>/static/js/test2.js"></script>
    	<script src="<%=path%>/static/js/strophe.js"></script>
</html>