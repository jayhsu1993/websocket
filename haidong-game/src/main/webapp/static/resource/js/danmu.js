/**
 * 弹幕模块
 */
var danmuModule = {
	setting: {
		time: 15000, //弹幕飘动时间
		isOpen: true
	},

	newyearTpl: function(headimg, username, content) {
		_content = danmuModule.cutDanmu(content);
		return '<div style="display: inline-block;vertical-align: middle;position: relative;">' +
			'<img src="http://ocar2d7vc.bkt.clouddn.com/hidong-game/img/newyearhead.png"/>' +
			'<img src="' + headimg + '" onerror="errorImg(this)" style="position: absolute;top: 8px;left: 8px;border-radius: 100px;height: 50px;width:50px">' +
			'</div>' +
			'<div style="border-radius: 26px;background-color: rgba(0,0,0,0.5); padding: 5px 15px;color: rgb(255,255,255);display: inline-block;vertical-align: middle;border:2px solid #ffe400;background-color:rgba(211,16,16,0.85)">' +
			'<span style="display: block;word-break: keep-all">' + username + '</span>' +
			'<span style="display: block;word-break: keep-all;">' + content + '</span>' +
			'</div>' +
			'<img src ="http://ocar2d7vc.bkt.clouddn.com/hidong-game/img/newyear.png" style="position:absolute;top: -15px;left: 70px;height: 25px;"/>';

	},

	normalTpl: function(headimg, username, content) {

		_content = danmuModule.cutDanmu(content);

		return '<div style="display: inline-block;vertical-align: middle;position: relative;">' +
			'<img src="' + pageInfo.path + '/static/resource/img/gn_r4_c1.png">' +
			'<img src="' + headimg + '" style="position: absolute;top: 5px;left: 10px;border-radius: 100px;height: 50px;width:50px">' +
			'</div>' +
			'<div style="border-radius: 20px;background-color: rgba(0,0,0,0.5); padding: 5px 15px;color: rgb(255,255,255);display: inline-block;vertical-align: middle;">' +
			'<span style="display: block;word-break: keep-all">' + username + '</span>' +
			'<span style="display: block;word-break: keep-all;">' + content + '</span>' +
			'</div>';
	},

	planeTpl: function(headimg, username, content) {
		return '<div style="display: inline-block;vertical-align: middle;position: relative;">' +
			'<img src="http://ocar2d7vc.bkt.clouddn.com/hidong-game/img/newyearhead.png"/>' +
			'<img src="' + headimg + '" onerror="errorImg(this)" style="position: absolute;top: 8px;left: 8px;border-radius: 100px;height: 50px;width:50px">' +
			'</div>' +
			'<div style="border-radius: 26px;background-color: rgba(0,0,0,0.5); padding: 5px 15px;color: rgb(255,255,255);display: inline-block;vertical-align: middle;border:2px solid #ffe400;background-color:rgba(211,16,16,0.85)">' +
			'<span style="display: block;word-break: keep-all">' + username + '</span>' +
			'<span style="display: block;word-break: keep-all;">' + content + '</span>' +
			'</div>' +
			'<img src ="' + pageInfo.path + 'static/resource/img/gift/plane/gifttitle.png' + '" style="position:absolute;top: -27px;left: 82px;height: 35px;"/>';
	},

	danmuSwitch: function() {
		var that = this;
		if(that.setting.isOpen) {
			that.setting.isOpen = false;
//			layer.msg('弹幕已经关闭');
		} else {
			that.setting.isOpen = true;
//			layer.msg('弹幕已经开启');
		}
	},

	//发送 普通弹幕
	postDanmu: function(headimg, username, content) {
		var that = this;
		var div = document.createElement("div");
		div.className = 'danmu-item';
		div.style.position = "absolute";
		var h = that.randomNum(70, 350);
		//		if(pageInfo.zoom<1||pageInfo.needResize){
		//			div.style.left =1280/1.5 +'px';
		//			div.style.zoom = 1.5;
		//			div.style.top = h + 'px';
		//		}else{
		//			div.style.zoom = pageInfo.zoom*1.5;
		//			div.style.left = window.innerWidth/(pageInfo.zoom*1.5)+ 'px';
		//			div.style.top = h + 'px';
		//		}

		div.style.zoom = 1.5 * 1.5;
		div.style.left = 1920 / (1.5 * 1.5) + 'px';
		div.style.top = h + 'px';

		//div.style.top = h + 'px';
		//		div.innerHTML = '<div style="display: inline-block;vertical-align: middle;position: relative;"><img src="'+pageInfo.path+'/static/resource/img/gn_r4_c1.png" style="" />' +
		//			'<img src="' + headimg + '" style="position: absolute;top: 5px;left: 10px;border-radius: 100px;height: 50px;width:50px" />' +
		//			'</div>' +
		//			'<div style="border-radius: 20px;background-color: rgba(0,0,0,0.5); padding: 5px 15px;color: rgb(255,255,255);display: inline-block;vertical-align: middle;">' +
		//			'<span style="display: block;word-break: keep-all">' + username + '</span>' +
		//			'<span style="display: block;word-break: keep-all;">' + that.cutDanmu(content) + '</span>' +
		//			'</div>';

		div.innerHTML = that.newyearTpl(headimg, username, content);

		//document.getElementsByTagName('body')[0].appendChild(div);
		document.getElementById('hdApp').appendChild(div);
		var w = pageInfo.zoom < 1 || pageInfo.needResize ? div.offsetWidth + 1280 : div.offsetWidth + window.innerWidth;
		w = 1920;
		div.style.transition = "transform " + that.setting.time + "ms linear";
		div.style.transform = 'translateX(-' + (w / 1.5) + 'px)';

		console.log(w);
		console.log(div.offsetWidth);
		console.log($(div).width());
		setTimeout(function() {
			$(div).remove();
		}, that.setting.time)
	},

	postPlaneDanmu: function(headimg, username, content) {
		var that = this;
		var div = document.createElement("div");
		div.className = 'danmu-item';
		div.style.position = "absolute";
		var h = that.randomNum(70, 350);

		div.style.zoom = 1.5 * 1.5;
		div.style.left = 1920 / (1.5 * 1.5) + 'px';
		div.style.top = h + 'px';
		div.style.zIndex = 800;

		div.innerHTML = that.planeTpl(headimg, username, content);
		document.getElementById('hdApp').appendChild(div);
		//document.getElementsByTagName('body')[0].appendChild(div);
		//document.querySelector('.screen-gift-warp').appendChild(div);
		//var w = pageInfo.zoom < 1 || pageInfo.needResize ? div.offsetWidth + 1280 : div.offsetWidth + window.innerWidth;
		var w = 1920 / div.style.zoom + div.offsetWidth * div.style.zoom;
		div.style.transition = "transform " + that.setting.time + "ms linear";
		//div.style.transform = 'translateX(-' + (w / 1.5) + 'px)';
		div.style.transform = 'translateX(-' + w + 'px)';

		setTimeout(function() {
			$(div).remove();
		}, that.setting.time)
	},

	postNewYearDanmu: function(headimg, username, content) {
		var that = this;
		var div = document.createElement("div");
		div.className = 'danmu-item';
		div.style.position = "absolute";
		var h = that.randomNum(70, 350);

		div.style.zoom = 1.5 * 1.5;
		div.style.left = 1920 / (1.5 * 1.5) + 'px';
		div.style.top = h + 'px';
		div.innerHTML = ''
	},

	//APP端 发红包  大屏显示  wuyong
	postUFO: function(headimg, username, content) {
		var that = this;
		var div = document.createElement("div");
		div.className = 'danmu-item';
		div.style.zIndex = '900';
		div.style.position = "fixed";
		div.style.left = window.innerWidth + 'px';
		var h = that.randomNum(0, 400);
		//var h = that.getRandom(400, 0)
		div.style.top = h + 'px';
		div.style.width = '300px';
		div.innerHTML = '<div style="display: inline-block;vertical-align: middle;position: relative;" class="ufo"><img src="' + pageInfo.path + '/static/resource/img/hb-ufo.png" style="width: 100%;height: 100%;" />' +
			'<img src="' + headimg + '" style="position: absolute;top: 10px;left: 80px;border-radius: 100px;height: 50px;width:50px" />' +
			'</div>' +
			'<div style="width:200px;border-radius: 20px;background-color: rgba(0,0,0,0.5); padding: 5px 15px;color: rgb(255,255,255);">' +
			'<span style="display: block;word-break: keep-all">' + username + '</span>' +
			'<span style="display: block;word-break: keep-all;">' + content + '</span>' +
			'</div>';
		document.getElementsByTagName('body')[0].appendChild(div);

		var w = div.offsetWidth + window.innerWidth;
		console.log(w);
		div.style.transition = "transform " + that.setting.time + "ms linear";
		div.style.transform = 'translateX(-' + w + 'px)'

		setTimeout(function() {
			$(div).remove();
		}, that.setting.time)
	},

	//A小红包 wuyong
	postDanmuHongbao: function(headimg, username, content) {
		var that = this;
		var div = document.createElement("div");
		div.className = 'danmu-item';
		div.style.position = "fixed";
		div.style.zoom = pageInfo.zoom;
		div.style.left = window.innerWidth / pageInfo.zoom + 'px';
		//div.style.top = that.getRandom(580, 70) + 'px';
		div.style.top = that.randomNum(70, 450) + 'px';
		div.style.width = '240px';
		div.innerHTML = '	<div style="display: inline-block;vertical-align: middle;position: relative;"><img src="' + pageInfo.path + '/static/resource/img/gn_r4_c1.png" style="" />' +
			'<img src="' + headimg + '" style="position: absolute;top: 5px;left: 10px;border-radius: 100px;height: 50px;width:50px" />' +
			'</div>' +
			'<div style="border-radius: 20px;background-color: rgba(0,0,0,0.5); padding: 5px 15px;color: rgb(255,255,255);display: inline-block;vertical-align: middle;position: relative; width: 250px;">' +
			'<span style="display: block;word-break: keep-all">' + username + '</span>' +
			'<span style="display: block;word-break: keep-all;color: yellow;">' + content + '</span>' +
			'<div class="danmu-hongbao danmu-hongbao-fly"></div>' +
			'</div>';
		document.getElementsByTagName('body')[0].appendChild(div);
		//		div.addEventListener('webkitAnimationEnd', function() {
		//			$(this).remove();
		//		})
		//var w = div.offsetWidth + window.innerWidth;
		var w = pageInfo.zoom < 1 || pageInfo.needResize ? div.offsetWidth + 1280 : div.offsetWidth + window.innerWidth;
		div.style.transition = "transform " + that.setting.time + "ms linear";
		div.style.transform = 'translateX(-' + w + 'px)'
		setTimeout(function() {
			$(div).remove();
		}, that.setting.time)
	},

	//祝福语
	postDanmuWish: function(headimg, username, content) {
		var that = this;
		var div = document.createElement("div");
		div.className = 'danmu-item danmumove';
		div.style.position = "fixed";

		if(pageInfo.zoom < 1 || pageInfo.needResize) {
			div.style.left = '1280px';
			div.style.zoom = 1.5;
		} else {
			div.style.zoom = pageInfo.zoom * 1.5; //祝福语1.5倍
			div.style.left = window.innerWidth / (pageInfo.zoom * 1.5) + 'px';
		}

		//div.style.top = that.getRandom(580, 70) + 'px';
		div.style.top = that.randomNum(70, 450) / 1.5 + 'px';

		div.style.width = '700px';
		div.innerHTML = '	<div style="display: inline-block;vertical-align: middle;position: relative;"><img src="' + pageInfo.path + '/static/resource/img/gn_r4_c1.png" style="" />' +
			'<img src="' + headimg + '" style="position: absolute;top: 5px;left: 10px;border-radius: 100px;height: 50px;width:50px" />' +
			'</div>' +
			'<div class="jianbian" style="border-radius: 20px; padding: 5px 15px;color: rgb(255,255,255);display: inline-block;vertical-align: middle;position: relative;">' +
			'<span style="display: block;word-break: keep-all">' + username + '</span>' +
			'<span style="display: block;word-break: keep-all;color: yellow;">' + content + '</span>' +
			'</div>';
		document.getElementsByTagName('body')[0].appendChild(div);
		//		div.addEventListener('webkitAnimationEnd', function() {
		//			$(this).remove();
		//		})
		//var w = div.offsetWidth + window.innerWidth;
		var w = pageInfo.zoom < 1 || pageInfo.needResize ? div.offsetWidth + 1280 : div.offsetWidth + window.innerWidth;

		div.style.transition = "transform " + that.setting.time + "ms linear";
		div.style.transform = 'translateX(-' + w + 'px)';
		setTimeout(function() {
			$(div).remove();
		}, that.setting.time)
	},

	//霸屏弹幕
	postBigD: function(headimg, username, content, second, zoom) {
		zoom = zoom * 1.3;

		var that = this;
		var div = document.createElement("div");
		div.style.position = "absolute";

		//		if(pageInfo.zoom<1||pageInfo.needResize){
		//			div.style.left =1280/zoom +'px';
		//			div.style.zoom =zoom;
		//			div.style.top=that.randomNum(70, 300)/zoom+'px';
		//		}else{
		//			div.style.zoom = pageInfo.zoom*zoom;
		//			div.style.left = window.innerWidth/(pageInfo.zoom*zoom) + 'px';
		//			div.style.top = that.randomNum(70, 300)/zoom + 'px';
		//		}

		div.style.zoom = 1.5 * zoom;
		div.style.left = 1920 / (1.5 * zoom) + 'px';
		div.style.top = that.randomNum(70, 300) / zoom + 'px';

		//div.style.top = that.getRandom(580, 70) + 'px';

		div.style.width = '750px';
		div.innerHTML = '<div style="position: relative;display: inline-block;vertical-align: middle;">' +
			'<img src="' + pageInfo.path + '/static/resource/img/HUOJIAN.png" width="300"/>' +
			'<img src="' + headimg + '" style="height: 50px;width: 50px;border: 2px solid #FFF700;border-radius: 50%;position: absolute;left: 60px;top: 53px;"/>' +
			'</div>' +
			'<div style="border: 3px solid #F8CA2B; border-radius: 40px;background-color: rgba(0,0,0,0.5); padding: 5px 15px;color: rgb(255,255,255);display: inline-block;vertical-align: middle;position: relative;">' +
			'<span style="display: block;word-break: keep-all">' + username + '</span>' +
			'<span style="display: block;word-break: keep-all;color: yellow;">' + content + '</span>' +
			'</div>';

		//document.getElementsByTagName('body')[0].appendChild(div);
		document.getElementById('hdApp').appendChild(div);
		//var w = (div.offsetWidth + window.innerWidth)/zoom;

		var w = pageInfo.zoom < 1 || pageInfo.needResize ? (div.offsetWidth + 1280) / zoom : (div.offsetWidth + window.innerWidth) / zoom;
		w = 1920;
		//		div.style.transition = 'transform '+second+'ms linear';
		div.style.transition = 'transform 15000ms linear';
		div.style.transform = 'translateX(-' + w + 'px)';
		//		div.style.transform = 'translateX(-500px)';

		setTimeout(function() {
			$(div).remove();
			//			div.style.transform = 'translateX(-' + w + 'px)';
			//					setTimeout(function() {
			//			$(div).remove();
			//		}, 5000)
		}, 15000)
	},

	postXrHb: function(headimg, username, money, content) {
		var that = this;
		var div = document.createElement("div");
		div.className = 'xrhb ani-hb-down';

		if(pageInfo.zoom < 1 || pageInfo.needResize) {
			div.style.left = (randomNum(20, window.innerWidth - 20)) + 'px';
		} else {
			div.style.zoom = pageInfo.zoom;
			div.style.left = (randomNum(20, window.innerWidth - 20)) / pageInfo.zoom + 'px';
		}

		div.innerHTML = '<div style="position: relative;width: 100%;height: 100%;">' +
			'<img src="' + pageInfo.path + '/static/resource/img/GUANGM.png" style="position: absolute;top: -90px;width: 180px;left: 10px;" class="ani-guang"/>' +
			'<img class="ani-smallhb" src="' + pageInfo.path + '/static/resource/img/XIAOHONGB.png" style="position: absolute;top: 50px;left: -70px;width: 350px;z-index: 100;transform: scale(0);"/>' +
			'<img src="' + headimg + '" style="height: 50px;width: 50px;position: absolute;top: -20px;left: 75px;border: 3px solid #F8CA2B;border-radius: 40px;"/>' +
			'<img src="' + pageInfo.path + '/static/resource/img/HONGB.png" width="200" style="z-index: 110;"/>' +
			'<div style="color: #fff;text-align: center;padding: 5px 10px;margin-top: -290px;">' + username + '</div>' +
			'<div style="color: #F8CA2B;text-align: center;margin-top: 50px;font-size: 23px;padding: 5px 10px;">' + money + '元</div>' +
			'<div style="text-align: center;color: #fff;padding: 5px;">' + content + '</div>' +
			'<div style="text-align: center;color: #F8CA2B;padding: 5px;">祝新人新婚愉快</div></div>';

		document.getElementsByTagName('body')[0].appendChild(div);

		setTimeout(function() {
			$(div).remove();
		}, 8000)
	},

	//getRandom(1140,70); 随机数
	getRandom: function(n, m) {
		return Math.floor(Math.random() * n + 1) + m
	},

	//工具 限制弹幕字数
	cutDanmu: function(content) {
		if(content.length > 40) {
			return content.substr(0, 40) + ".."
		} else {
			return content;
		}
	},

	randomNum: function(n, m) {
		var random = Math.floor(Math.random() * (m - n)) + n;
		//		console.log("rewardNames[" + random + "]");
		return random;
	},
	sendDanmuByNum: function(num, headimg, username, content) {
		var time = 0;
		for(var i = 0; i < num; i++) {
			(function() {
				setTimeout(function() {
					danmuModule.postPlaneDanmu(headimg, username, content)
				}, time);
			})(time);
			time += 2000;
		}
	}
};