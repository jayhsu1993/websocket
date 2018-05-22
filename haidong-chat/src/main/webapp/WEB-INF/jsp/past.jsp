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
    <title>签到成功</title>
    <script src="<%=path%>/static/js/mui.min.js"></script>
    <link href="<%=path%>/static/css/mui.min.css" rel="stylesheet"/>
    <link href="<%=path%>/static/css/global.css" rel="stylesheet"/>
    <link href="<%=path%>/static/css/ucenter.css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/css/mui.picker.min.css" />
    <script src="<%=path%>/static/js/mui.picker.min.js"></script>
     <script type="text/javascript" src="http://code.jquery.com/jquery-1.4.2.min.js"></script>
     <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script type="text/javascript" charset="UTF-8">
      	mui.init();
		function myFunction() {
		    var x = document.getElementById("past").value;
		    document.getElementById("account").innerHTML = x;
		}
		
		var chars = ['0','1','2','3','4','5','6','7','8','9'];

		function generateMixed(n) {
		     var res = "";
		     for(var i = 0; i < n ; i ++) {
		         var id = Math.ceil(Math.random()*9);
		         res += chars[id];
		     }
		     return res;
		}
    
		
        function submit(){
            $.ajax({
                type: 'POST',
                url: '<%=path%>/wechatPay/jsOarder.do',
                data: {'detail':'测试','desc':'测试','goodSn':generateMixed(10),'openid':"${openid}",'orderSn':generateMixed(10),'amount':'0.01'},
                success: function(data){
                console.log(data.obj);
                var appId=data.obj.appId;
                var timeStamp=data.obj.timeStamp;
                var nonceStr=data.obj.nonceStr;
                var package=data.obj.package;
                var paySign=data.obj.paySign;
                WeixinJSBridge.invoke(
                        'getBrandWCPayRequest', {
                            "appId":appId,     //公众号名称，由商户传入
                            "timeStamp":timeStamp,         //时间戳，自1970年以来的秒数
                            "nonceStr":nonceStr, //随机串
                            "package":package,
                            "signType":"MD5",         //微信签名方式：
                            "paySign":paySign //微信签名
                        },
                        function(res){
                            WeixinJSBridge.log(res.err_msg);
                            if(res.err_msg == "get_brand_wcpay_request:ok"){
                                <!--支付成功调用-->
                                <!--history.go(0);   -->
                          	  	var wva=$("#music3").val();
                  	     		var wn=$("#name").val();
                      			 mui.openWindow({
                      			url:'<%=path%>/wechatPay/sucpay/${openid}/'+wn+'/' + wva,
       						});
                            }else if(res.err_msg == "get_brand_wcpay_request:cancel"){
                                <!--取消支付调用-->
                               // mui.openWindow({
            						//url:"<%=path%>/wechatPay/${openid}",
            					//})
                                $("#sub").click(function(){
                                	$(this).unbind("click");
                                	 submit();
                                });
                            }else{
                                <!--支付失败-->
                                alert("失败");

                            }
                        }
                );
            } ,
                dataType: "json"});
			
	     	
        }

        $(function(){
        	
                $("#sub").addEventListener('tap',function(){
                	$(this).unbind("click");
                	 submit();
                });
        });

    </script>
</head>
<body>
	<header >
		<!--<a href="live.html" class="mui-icon  mui-pull-left"><span class="iconback"></span></a>
		<h1 class="mui-title color">签到成功</h1>
		<a class="mui-icon mui-pull-right"><span class="icon"></span></a>-->
	</header>
	<div class="mui-content">
		<div class="pstsuc"><img src="<%=path%>/static/images/sucre.png" class="iconsure"/></div>
		<div class="pstsuc" style="padding-top: 10px;"><span >签到成功</span></div>
		<div class="mui-input-row past">
			<input type="text" id="name" placeholder="请输入真实姓名">
		</div>
		<div class="packet">
			<div class="packetcon">
				<span class="col-xs-4">发送份子钱</span>
				<span class="col-xs-7 text-right"><input class="packet1" id="past" type="text" oninput="myFunction()"></input></span>
				<span class="col-xs-1 text-right">元</span>
			</div>
		</div>
		<div class="mui-content-padded">
			<button id='music3' class="mui-btn mui-btn-blocks" type='button'>点击我选择祝福语</button>
		</div>
		<div class="moneyshow"><em ><img src="<%=path%>/static/images/past1.png" style="width: 15px; height: 18px;"/></em>&nbsp;<span id="account">&nbsp;0.00</span></div>
		<div class="packet"><a href="javascript:void(0)" class="sure" id="sub">发送红包</a></div>
		<span class="error">金额不能为空</span>
		<a class="return" href="<%=path%>/wechatPay/${openid}" >已发送，返回首页</a>
	</div>
</body>
<script>

(function($, doc) {
				$.init();
				$.ready(function() {
					//普通示例
					var userPicker = new $.PopPicker();
					userPicker.setData([{
						value: 'ywj',
						text: '早生贵子，百年好合'
					}, {
						value: 'aaa',
						text: '总祝你们百年幸福'
					}, {
						value: 'lj',
						text: '祝爱情甜蜜，婚姻喜洋洋'
					}, {
						value: 'ymt',
						text: '祝永浴爱河，白头偕老'
					},{
						value: 'gezh', 
						text: '白头偕老，幸福美满'
					}]);
					var showUserPickerButton = doc.getElementById('music3');
					var userResult = doc.getElementById('music3');
					showUserPickerButton.addEventListener('tap', function(event) {
						userPicker.show(function(items) {
							/*userResult.innerText = JSON.stringify(items[0]);*/
							//返回 false 可以阻止选择框的关闭
							userResult.innerText = items[0].text;
							userResult.value = items[0].text;
							//return false;
						});
					}, false);
				});
			})(mui, document);			
</script>
</html>