/*
 *签到逻辑 
 * */

var imgs = 48;
var imglist = [];

var registerQ = {};
registerQ.status = false;
registerQ.queue = [];
var _templength = 46;

var chatarea = document.getElementById("zhufu-list");
var chatul = document.getElementById("text-content");

var randomArr = [];
for(i = 0; i < 46; i++) {
	randomArr.push(i)
}
randomArr.sort(function() { return 0.5 - Math.random() });


setInterval("scrollUp()", 50);

var photos = document.getElementsByClassName("photo");

chatarea.style.height = window.innerHeight * 0.60 + "px";

observe(registerQ, function(name, value, old) {
	console.log(name + "__" + value + "__" + old);
	console.log(JSON.stringify(value))
	if(name == 'Array-unshift') {
		if(!registerQ.status && registerQ.queue.length > 0) {
			replaceInfo();
		}
	}
})

//设置进度条内 滑动效果
function scrollUp() {
	//console.log(chatarea.scrollTop);
	if(chatarea.scrollTop >= chatul.scrollHeight) {
		chatarea.scrollTop = 0;
	} else {
		chatarea.scrollTop++;
	}
}

//registerQ.queue.unshift({
//	headimg: "http://wx.qlogo.cn/mmopen/ajNVdqHZLLAv5QEExV4M05bfulngA3Q6mR71N2gbnJQ96yN3XoziaricFHhxEVKWhELIumF6kM1Ys1S6pdiaCiaUuQ/0",
//	username: "胡小林",
//	wish: "来吧来吧"
//})

var i = 1;
$('#test').on('click', function() {
	signQ.queue.unshift({
		headimg: "http://wx.qlogo.cn/mmopen/ajNVdqHZLLAv5QEExV4M05bfulngA3Q6mR71N2gbnJQ96yN3XoziaricFHhxEVKWhELIumF6kM1Ys1S6pdiaCiaUuQ/0",
		username: "胡小林" + i,
		wish: "来吧来吧"
	})
	i++
})

function nameList(obj, loginflag) {
	if(loginflag) {
		var li = document.createElement("li");
		li.innerHTML = '<span class="name fleft">' + obj.username + '</span><span class="mcontent fright">' + obj.wish + '</span>'
		document.getElementById("text-content").appendChild(li);

	} else {
		var li = document.createElement("li");
		li.innerHTML = '<span class="name fleft">' + obj.userName + '</span><span class="mcontent fright" style="color:' + colorList[Math.floor(Math.random() * 5)] + '">' + obj.text + '</span>'
		document.getElementById("text-content").appendChild(li);

	}

	//保存99条记录
	if(document.getElementById("text-content").children.length > 30) {
		document.getElementById("text-content").removeChild(document.getElementById("text-content").children[0])
	}
	//				if(document.getElementById("text-content").offsetHeight > (h * 0.6) && flag) {
	//					$('.myscroll').myScroll({
	//						speed: 20, //数值越大，速度越慢
	//						rowHeight: 250 //li的高度
	//					});
	//					flag = false;
	//				}
}

//更换照片
function replaceInfo() {
	//signVoice.play(); //签到音效
	console.log(registerQ.length);
	//添加照片到 心形中   伪随机
	if(registerQ.queue.length <= 0) {
		registerQ.status = false;
		return;
	} else {
		registerQ.status = true;
	}

	var index
	if(randomArr.length > 0) {
		index = randomArr.pop();
	} else {
		index = Math.floor(Math.random() * 46);
	}
	
	
		var content_pop = registerQ.queue.pop();
		var img = document.createElement("img");
		img.className = "rotate";
		img.src = content_pop.headimg;
		if(typeof(content_pop.wish) == undefined) {
			content_pop.wish = '新婚快乐';
		}
		//					imglist.push(content_pop.headimg);

		//					if(imglist.length < 46) {
		//						var j = 0;
		//						for(var i = 0; i < 46; i++) {
		//							if(j == imglist.length) {
		//								j = 0;
		//							}
		//							photos[i].style.backgroundImage = 'url(' + imglist[j] + ')';
		//							j++;
		//						}
		//					}

		photos[index].innerHTML = "";
		photos[index].style.backgroundColor = "transparent";
		photos[index].style.opacity = 1;
		var leftP = photos[index].offsetLeft;

		var topP = photos[index].offsetTop;

		var wrapper = document.getElementsByClassName("wrapper")[0];

		var wrapperL = wrapper.offsetLeft;

		var wrapperT = wrapper.offsetTop;

		var PL = leftP + wrapperL;

		var PT = topP + wrapperT

		var imgW = window.innerWidth * 0.045;

		var imgWch = window.innerWidth * 0.25;
		console.log(window.innerWidth);
		console.log("imgW=" + imgW);
		console.log("imgWch=" + imgWch);
		addShow(PL, PT, imgW, imgWch, content_pop, img, index);

		setTimeout(function() {
			img.classList.remove("rotate");
		}, 5000);

		nameList(content_pop, true);
}

function addShow(PL, PT, imgW, imgWch, content_pop, img, index) {
	console.log(content_pop.username + "," + content_pop.wish);

	//添加出场头像
	$(".addUser").html("<div class='addPosF'></div>");

	$(".addPosF").css({
		"width": imgW,
		"height": "auto",
		"position": "absolute",
		"left": PL,
		"top": PT,

	}).append(img);

	//	javascript: signObj.play(content_pop.username + "," + content_pop.wish);

	$(".addPosF").append('<div class="addTextCont"></div>');

	$(".addPosF").animate({

		"width": imgWch,
		"height": "auto",
		"left": "50%",
		"top": "50%",
		"margin-top": -imgWch * 0.5,
		"margin-left": -imgWch * 0.5

	}, 500, function() {
		console.log("1st ani")
		$(".addTextCont").css("display", "block")
		showText();
		$(".addPosF").animate({
			"left": "50%"
		}, 7000, function() {
			console.log("2st ani")
			$(".addTextCont").css("display", "none")

			$(".addPosF").animate({
				"width": imgW,
				"margin-left": "0",
				"margin-top": "0",
				"left": PL,
				"top": PT,

			}, 2000, function() {
				console.log("3st ani")
				$(".photo").eq(index).append(img);
				$(".addPosF").eq(index).remove();
				replaceInfo();
			})
		})
	})

	function showText() {
		console.log('showText')
		var dataName = "书林哥";
		//					console.log(obj.userName)
		if(content_pop.username != undefined) {
			dataName = content_pop.username
		}

		if(dataName.length >= 5) {
			dataName = dataName.substring(0, 5) + "..."
		}

		//var str = dataName+" 祝新人 "+content_pop.wish;
		var str = '欢迎 ' + dataName + ' 大驾光临';

		tim = 800 / (str.length)

		var i = 0;

		var settimr = setInterval(steTime, tim)

		function steTime() {

			$(".addTextCont").text(str.substring(0, i));

			if(i > str.length) {
				clearInterval(settimr)
			}
			i++;

		}

	}

}