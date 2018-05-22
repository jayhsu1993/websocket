var w = 190;
var l = 0;
var len = $("#rankList li").length * 2;

var theDefault={};
theDefault.first =[];
theDefault.second =[];
theDefault.third =[];
var _prizeUser = [];
//_prizeUser.push({reheadimgurl:pageInfo.path+"static/resource/img/headimg/head1.png",renickname:"t1"})
//_prizeUser.push({reheadimgurl:pageInfo.path+"static/resource/img/headimg/head2.png",renickname:"t2"})
//_prizeUser.push({reheadimgurl:pageInfo.path+"static/resource/img/headimg/head3.png",renickname:"t3"})
//_prizeUser.push({reheadimgurl:pageInfo.path+"static/resource/img/headimg/head4.png",renickname:"t4"})
//_prizeUser.push({reheadimgurl:pageInfo.path+"static/resource/img/headimg/head5.png",renickname:"t5"})
//_prizeUser.push({reheadimgurl:pageInfo.path+"static/resource/img/headimg/head6.png",renickname:"t6"})

var _allPrizeUser = [];

$.get(pageInfo.postUrl+'survey/list_prize?liveid='+pageInfo.roomId,function(e){
	//console.log(e);
	if(e.success){
		if(e.data&&e.data.length>0){
			for(var i =0;i<e.data.length;i++){
				if(e.data[i].prize == "1"){
					theDefault.first.push({img:"http://ocar2d7vc.bkt.clouddn.com/"+ e.data[i].head_img,name:e.data[i].username})
				}else if(e.data[i].prize == "2"){
					theDefault.second.push({img:"http://ocar2d7vc.bkt.clouddn.com/"+e.data[i].head_img,name:e.data[i].username})
				}else if(e.data[i].prize == "3"){
					theDefault.third.push({img:"http://ocar2d7vc.bkt.clouddn.com/"+e.data[i].head_img,name:e.data[i].username})
				}
				//载入图片
				var img = new Image();
				img.src = "http://ocar2d7vc.bkt.clouddn.com/"+ e.data[i].head_img;
			}
		}
	}
})

$.get(pageInfo.postUrl+'survey/list_draw?live_id='+pageInfo.roomId,function(e){
	console.log(e);
	if(e.success){
		for(var i =0;i<e.data.length;i++){
			_allPrizeUser.push({
				'renickname':e.data[i],
				'reheadimgurl':pageInfo.path+'static/resource/img/headimg/head'+randomNum(1,7)+'.png'
			})
		}
		$('.canyu').eq(0).html('参加抽奖人数:'+e.data.length);
	}
})



//theDefault.first.push({img:"http://localhost:8088/haidong-game//static/resource/img/headimg/head2.png",name:"陈兴"})
//theDefault.first.push({img:"http://localhost:8088/haidong-game//static/resource/img/headimg/head6.png",name:"大海之"})

function flagt() {
	var reg = /stardraw enddraw/g;
	var className = $(".stardraw").attr("class");
	return reg.test(className);
}

$("#rankList").append($("#rankList").html()).css({
	"width": len * w,
	"left": -len * w / 2
})

$("#passPre").click(function() {
//	var flag = flagt();
//	if(flag) return false;
	if($('.stardraw').hasClass('disabled'))
		return;
	l = parseInt($("#rankList").css("left")) + w; //这里要转成整数，因为后面带了px单位
	showCurrent(l);

});

$("#passNext").click(function() {
//	var flag = flagt();
//	if(flag) return false;
	if($('.stardraw').hasClass('disabled'))
		return;
	
	l = parseInt($("#rankList").css("left")) - w; //这里要转成整数，因为后面带了px单位
	showCurrent(l);
});

function showCurrent(l) {	
	if($("#rankList").is(':animated')) {
		return;
	}
	$("#rankList").animate({
			"left": l
		},
		500,
		function() {
			if(l == 0) {
				$("#rankList").css("left", -len * w / 2);
			} else if(l == (1 - len) * w) {
				$("#rankList").css('left', (1 - len / 2) * w);
			}
		});

}
var dataUser =[];

//for(var i=0; i<10;i++){
//	dataUser.push({headimgurl:'images/main/abc_03.png',nickname:'胡小林'+i})
//}

function getDrwaUser(){
	$.get(pageInfo.postUrl+"reportController/report/"+pageInfo.roomId, function(data) {
		$('#current-people').html(data.data.length + '');
		dataUser.length = 0;
		dataUser = data.data;
		if(data.data.length==0){
			layer.msg('当前还没有抽奖人数哦')
		}
		$('.canyu').eq(0).html('参加抽奖人数:'+data.data.length);
		$('.canyu').eq(1).html('获奖人数:0');
		$('#userList').empty();
	}, 'json').error(function() {
		layer.msg('抽奖初始化失败 请重试');
		dataUser.length = 0;
	})
}


function initLottery(force){
	if(force){
		if(_allPrizeUser.length>0){
			dataUser=simpleCopy(_allPrizeUser)
		}else{
			getDrwaUser();
		}
		$('.canyu').eq(1).html('获奖人数:0');
		$('#userList').empty();
	}else if(dataUser.length == 0){
	if(_allPrizeUser.length>0){
		dataUser=simpleCopy(_allPrizeUser)
		}else{
			getDrwaUser();
		}
	}
}


function toDraw() {
	$.ajax({
		url: 'http://' + chatHost + '/draw?liveid=' + UserInfo.roomId(),
		type: "get",
		dataType: "jsonp",
		jsonp: "callback", //服务端用于接收callback调用的function名的参数   
		jsonpCallback: "success_jsonpCallback", //callback的function名称,服务端会把名称和data一起传递回来  
		success: function(data) {
			//				            	var json =  JSON.stringify(data);
			dataUser = data.data;
			//						dataUser=[{
			//							
			//							headimgurl:"images/main/abc_03.png",
			//							nickname:"我的名字很长很长真的很长很长不骗你"
			//							
			//							
			//						}]			
			$(".luckdraw").css("display", "block");
			$('.jiangpingsan').show();

			$(".canyu").eq(0).html("参加抽奖人数：" + data.num)
			return dataUser;
			//				            	var nikname =[];
			//				            	var headimgurl =json.headimgurl;
		},
		error: function() {
			alert('Error123');
		}
	});
}

var timr;
$(".stardraw").click(function() {
	var flag = flagt(); //true 表示结束
	
	$(this).snabbt({
		scale: [0.9, 0.9],
		duration: 50,
		easing: 'ease'
	}).snabbt({
		scale: [1, 1],
		duration: 50,
		easing: 'ease'
	})
	
	
//	if(dataUser.length == 0){
//		layer.msg("已经抽完啦");
//	}
	
	
	if(flag) {

//		if(voice_draw.paused) {
//			voice_draw.play();
//		}
		clearInterval(timr);
	//	$(this).removeClass("enddraw");
		$(this).removeClass("disabled");
		console.log("抽奖结束");
		var classNam = "firstluck"
		if(l == 0 || l == -570) {
			console.log("我是一等奖")
			classNam = "firstluck"
		} else if(l == -190 || l == -760) {
			classNam = "secondluck"
			console.log("我是二等奖")
		} else if(l == -950 || l == -380) {
			console.log("我是三等奖")
			classNam = "thridluck"
		}
		if(dataUser.length == 0) return false;
		var IdName = document.getElementById("changeImg");
		var scr = IdName.getAttribute("src");
		var title = IdName.getAttribute("title");
		if(title.length > 5) {
			title = title.substr(0, 4) + "..."
		}
		var spliceI = IdName.getAttribute("data-id");
		if(dataUser.length > 0) {
			dataUser.splice(spliceI, 1);
		}
		var li = document.createElement("li");
		var str = '<span class=\"' + classNam + '\"></span><span class="userImg">' +
			'<img src=\"' + scr + '\" /></span>' + title

		li.innerHTML = str;
		$("#userList").prepend(li);
		//						var h = parseFloat($(".postA").css("height"));
		//						var len = parseFloat($("#userList").css("height"));
		//						console.log("h:" + h + ";" + "len:" + len)
		//						if(len > h) {
		//							var topL = h - len;
		//							console.log(topL)
		//							$("#userList").css("top", topL);
		//						};
		var unme = $("#userList li").length;
		$(".canyu").eq(1).html("获奖人数：" + unme)

	} else {

		$(this).addClass("disabled");
		var len = dataUser.length;
		var IdName = document.getElementById("changeImg");
		var Lname =document.getElementById("lotteryname"); 
	
			timr = setInterval(retimer, 50);

			function retimer() {
				
				if(dataUser.length<1){
					var nun = Math.floor(Math.random() * _prizeUser.length);
					IdName.setAttribute("src", _prizeUser[nun].reheadimgurl);
					IdName.setAttribute("title", _prizeUser[nun].renickname);
					IdName.setAttribute("data-id", nun);
				}else{
					var nun = Math.floor(Math.random() * len);
					IdName.setAttribute("src", dataUser[nun].reheadimgurl);
					IdName.setAttribute("title", dataUser[nun].renickname);
					//IdName.setAttribute("title",'halo12');
					IdName.setAttribute("data-id", nun);
					Lname.innerHTML = dataUser[nun].renickname;
				}
			}
	
		
		var that = this;
		setTimeout(function(){
			//$(".stardraw").click();
				clearInterval(timr);
			//	$(this).removeClass("enddraw");
				$(that).removeClass("disabled");
				console.log("抽奖结束");
				var classNam = "firstluck";
				
				var place = 0;
				
				if(l == 0 || l == -570) {
					console.log("我是一等奖")
					classNam = "firstluck";
					place = 1;
				} else if(l == -190 || l == -760) {
					classNam = "secondluck"
					console.log("我是二等奖")
					place = 2;
				} else if(l == -950 || l == -380) {
					console.log("我是三等奖")
					classNam = "thridluck";
					place = 3;
				}
				if(dataUser.length == 0 && place==0) {
					layer.msg("已经抽完啦");
					return false;
				}
					
				//is default
				var defaultImg ="";
				var defaultName = "";
				
				//先抽取内定中奖人
				if(place == 1){
					if(theDefault.first.length>0){
						var temp = theDefault.first.pop();
						defaultImg = temp.img;
						defaultName = temp.name;
					}
				}else if(place == 2){
					if(theDefault.second.length>0){
						var temp = theDefault.second.pop();
						defaultImg = temp.img;
						defaultName = temp.name;
					}
					
				}else if(place == 3){
					if(theDefault.third.length>0){
						var temp = theDefault.third.pop();
						defaultImg = temp.img;
						defaultName = temp.name;
					}
				}
				
				
				if(defaultImg!=""&&defaultName!=""){
					
					//检查相似度
					for(var i =0 ;i<dataUser.length;i++){
						if(Levenshtein_Distance_Percent(defaultName,dataUser[i].renickname)>0.6){
							dataUser.splice(i, 1);
							break;
						}
					}
					
					var IdName = document.getElementById("changeImg");
					IdName.src=defaultImg;
					Lname.innerHTML = defaultName;
					
					
					if(defaultName.length > 5) {
						defaultName = defaultName.substr(0, 4) + "..."
					}
					
					
					

					var li = document.createElement("li");
					var str = '<span class=\"' + classNam + '\"></span><span class="userImg">' +
						'<img src=\"' + defaultImg + '\" /></span>' + defaultName

					li.innerHTML = str;
					$("#userList").prepend(li);
				
					var unme = $("#userList li").length;
					$(".canyu").eq(1).html("获奖人数：" + unme);
					//弹出中奖名单
					showLotteryResult(defaultImg,defaultName);
					
				}else{
					
					if(dataUser.length==0){
						layer.msg("已经抽完啦！");
						return ;
					}
					
					//检查 中奖人在内定中
//					theDefault.first =[];
//					theDefault.second =[];
//					theDefault.third =[];
					
					_checkPrizeUser(document.getElementById("changeImg").getAttribute("title"),classNam);
					
					
					
					
//					var IdName = document.getElementById("changeImg");
//					var scr = IdName.getAttribute("src");
//					var title = IdName.getAttribute("title");
//					if(title.length > 5) {
//						title = title.substr(0, 4) + "..."
//					}
//					var spliceI = IdName.getAttribute("data-id");
//					if(dataUser.length > 0) {
//						dataUser.splice(spliceI, 1);
//					}
//					
//					var li = document.createElement("li");
//					var str = '<span class=\"' + classNam + '\"></span><span class="userImg">' +
//						'<img src=\"' + scr + '\" /></span>' + title
//
//					li.innerHTML = str;
//					$("#userList").prepend(li);
//				
//					var unme = $("#userList li").length;
//					$(".canyu").eq(1).html("获奖人数：" + unme)
//					//弹出中奖名单
//					showLotteryResult(scr,title);
				}
				
				
		},5000)
		

	}

});
$("#reset").click(function() {
	var flag = flagt();
	//				console.log(flag)
	if(flag) {
		return false;
	}
	$("#userList").html("")
})
$("#closebtn").click(function() {
	$(this).parent().css("display", "none");
	$('.jiangpingsan').hide();
});

function gameManExist(userid) {
	for(var i = 0; i < gameManList.length; i++) {
		if(userid == gameManList[i]) {
			return true;
		}
	}
	gameManList.push(userid);
	return false;
}
//theDefault.first =[];
//theDefault.second =[];
//theDefault.third =[];

function _checkPrizeUser(name,classNam){
	console.log('_checkPrizeUser:'+name);
	var IdName = document.getElementById("changeImg");
	var Lname =document.getElementById("lotteryname"); 
	for(var i =0;i<theDefault.first.length;i++){
		if(Levenshtein_Distance_Percent(theDefault.first[i].name,name)>0.6){
			setTimeout(function(){
				var nun = Math.floor(Math.random() * dataUser.length);
				IdName.setAttribute("src", dataUser[nun].reheadimgurl);
				IdName.setAttribute("title", dataUser[nun].renickname);
				IdName.setAttribute("data-id", nun);
				Lname.innerHTML = dataUser[nun].renickname;
				_checkPrizeUser(dataUser[nun].renickname);
			},50)
			
			return;
		}
	}
	
	for(var j =0;j<theDefault.second.length;j++){
		if(Levenshtein_Distance_Percent(theDefault.first[j].name,name)>0.6){
			setTimeout(function(){
				var nun = Math.floor(Math.random() * dataUser.length);
				IdName.setAttribute("src", dataUser[nun].reheadimgurl);
				IdName.setAttribute("title", dataUser[nun].renickname);
				IdName.setAttribute("data-id", nun);
				Lname.innerHTML = dataUser[nun].renickname;
				_checkPrizeUser(dataUser[nun].renickname);
			},50)
		
			return;
		}
	}
	for(var k =0;k<theDefault.third.length;k++){
		if(Levenshtein_Distance_Percent(theDefault.first[k].name,name)>0.6){
			setTimeout(function(){
				var nun = Math.floor(Math.random() * dataUser.length);
				IdName.setAttribute("src", dataUser[nun].reheadimgurl);
				IdName.setAttribute("title", dataUser[nun].renickname);
				IdName.setAttribute("data-id", nun);
				Lname.innerHTML = dataUser[nun].renickname;
				_checkPrizeUser(dataUser[nun].renickname);
			},50)
		
			return;
		}
	}
	
	var scr = IdName.getAttribute("src");
	var title = IdName.getAttribute("title");
	console.log(title);
	if(title.length > 5) {
		title = title.substr(0, 4) + "..."
	}
	var spliceI = IdName.getAttribute("data-id");
	if(dataUser.length > 0) {
		dataUser.splice(spliceI, 1);
	}
	
	var li = document.createElement("li");
	var str = '<span class=\"' + classNam + '\"></span><span class="userImg">' +
		'<img src=\"' + scr + '\" /></span>' + title

	li.innerHTML = str;
	$("#userList").prepend(li);

	var unme = $("#userList li").length;
	$(".canyu").eq(1).html("获奖人数：" + unme)
	//弹出中奖名单
	showLotteryResult(scr,title);
	
	
}



function Levenshtein_Distance(s, t) {
	var n = s.length; // length of s
	var m = t.length; // length of t
	var d = []; // matrix
	var i; // iterates through s
	var j; // iterates through t
	var s_i; // ith character of s
	var t_j; // jth character of t
	var cost; // cost

	if(n == 0) return m;
	if(m == 0) return n;

	for(i = 0; i <= n; i++) {
		d[i] 	= [];
		d[i][0] = i;
	}
	for(j = 0; j <= m; j++) {
		d[0][j] = j;
	}

	for(i = 1; i <= n; i++) {
		s_i = s.charAt(i - 1);
		for(j = 1; j <= m; j++) {
			t_j = t.charAt(j - 1);
			if(s_i == t_j) {
				cost = 0;
			} else {
				cost = 1;
			}

			d[i][j] = Minimum(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + cost);
		}
	}

	return d[n][m];
	
}

//求两个字符串的相似度,返回相似度百分比
function Levenshtein_Distance_Percent(s, t) {
	var l = s.length > t.length ? s.length : t.length;
	var d = Levenshtein_Distance(s, t);
	console.log((1 - d / l).toFixed(3));
	return(1 - d / l).toFixed(3);
}

//求三个数字中的最小值
function Minimum(a, b, c) {
	return a < b ? (a < c ? a : c) : (b < c ? b : c);
}


function showLotteryResult(img,name){
	//alert('show');
	console.log("show");
	$('.lottery-result .lhead').attr('src',img);
	$('.lottery-result .lname').html(name);
	$('.lottery-result').show();
	$('.lottery-result').addClass('fadeIn');
	setTimeout(hideLotteryResult,5000)
}

function hideLotteryResult(){
	console.log("hide");
	$('.lottery-result').addClass('fadeOut');
	setTimeout(function(){
		$('.lottery-result').hide();
		$('.lottery-result').removeClass('fadeOut');
		$('.lottery-result').removeClass('fadeIn');
	},1000);
	
}



