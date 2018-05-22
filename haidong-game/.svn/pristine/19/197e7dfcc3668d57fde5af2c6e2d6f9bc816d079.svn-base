//礼物效果

var huajiaGift = (function() {
	var c = {};

	var isEnd = true;

	c.start = function() {
		if(isEnd) {
			isEnd = false;
			startAni();
			// setFlowerDown();
			setTimeout(removeAni, 7000);
		}

	}

	function startAni() {
		document.querySelector(".huajia-ground").classList
			.add('huajia-ground-move');
		document.querySelector(".huajia-man").classList.add('huajia-man-move');
		document.querySelector(".huajia-heart").classList
			.add('huajia-heart-move');
	}

	function removeAni() {
		document.querySelector(".huajia-ground").classList
			.remove('huajia-ground-move');
		document.querySelector(".huajia-man").classList
			.remove('huajia-man-move');
		document.querySelector(".huajia-heart").classList
			.remove('huajia-heart-move');
		isEnd = true;
	}

	function randomNum(n, m) {
		var random = Math.floor(Math.random() * (m - n)) + n;
		// console.log("rewardNames[" + random + "]");
		return random;
	}
	return c;
})();

var sanshengGift = (function() {
	var c = {};

	var isEnd = true;

	c.start = function() {

		if(isEnd) {
			isEnd = false;
			startAni();
			setFlowerDown();
			setTimeout(removeAni, 7000);
		}

	}

	function startAni() {
		document.querySelector(".tree").classList.add('tree-move');
		document.querySelector(".san-people").classList.add('people-move');
		document.querySelector(".gift-sansheng .text").classList.add('text-move');
	}

	function removeAni() {
		document.querySelector(".tree").classList.remove('tree-move');
		document.querySelector(".san-people").classList.remove('people-move');
		document.querySelector(".gift-sansheng .text").classList.remove('text-move');
		isEnd = true;
	}

	function setFlowerDown() {
		document.querySelector(".flower").innerHTML = '';
		var arr = [];
		for(var i = 0; i < 30; i++) {
			var div = document.createElement("div");
			div.className = 'f';
			div.innerHTML = '<img src="' + pageInfo.path + '/static/resource/img/san/flower.png" class="r"/>';
			div.style.left = randomNum(0, 800) + 700 + 'px';
			div.style.top = '-100px';
			document.querySelector(".flower").appendChild(div);
			arr.push(div);
		}

		setTimeout(function() {
			for(var j = 0; j < arr.length; j++) {
				arr[j].children[0].style.zoom = randomNum(60, 100) / 100;
				arr[j].children[0].style.animationDelay = (randomNum(1, 10) / 10) + 's';
				arr[j].style.transition = 'ease-out,' + randomNum(3, 6) + 's';
				arr[j].style.transform = 'translate(-' + randomNum(0, 600) + 'px,1200px)';
				arr[j].style.transitionDelay = Math.round(Math.random() * 3) + 's';
			}

		}, 50)

	}

	function randomNum(n, m) {
		var random = Math.floor(Math.random() * (m - n)) + n;
		//	console.log("rewardNames[" + random + "]");
		return random;
	}
	return c;
})();

//猜明星
var guessStar = (function() {

	var flag = false; //定制开关
	var starList = [
		'qiwei',
		'wangdongcheng',
		'xiena2',
		'sunhao',
		'xujiao',
		'zhangliang',
		'guotao',

		'liangjiahui',
		'zhouhaitao',
		'dazhangwei',
		'jialing',
		'quying',
		'wang',
		'qianfeng',
		'liuqingyun',
		'sunjian',
		'liuxiang',
		'zhoudongyu',

		'handou',
		'geyou',
		'jinzhengen',
		'huge',
		'wangzulan',
		'liudehua',
		'xuzhen',
		'luoyufeng',
		'huangxiaoming',
		'xiena',
		'sunhonglei',
		'shixiaolong',
		'halibote',
		'zhaobenshan',
		'chenguanxi'
	];

	var chooseList = {
		'1': 'handou',
		'2': 'geyou',
		'3': 'jinzhengen',
		'4': 'huge',
		'5': 'wangzulan',
		'6': 'liudehua',
		'7': 'xuzhen',
		'8': 'luoyufeng',
		'9': 'huangxiaoming',
		'10': 'xiena',
		'11': 'sunhonglei',
		'12': 'shixiaolong',
		'13': 'halibote',
		'14': 'zhaobenshan',
		'15': 'chenguanxi',
	};

	if(pageInfo.roomId == 'b4a21ba18cd44d24a602afa5e5787d03' || pageInfo.roomId == '1298959c85a14d8eb53873f975f13fd2') {
		starList.length = 0;
		for(var i = 0; i < 24; i++) {
			starList.push(i)
		}
		flag = true;
	};
	//打乱顺序
	starList.sort(function() {
		return 0.5 - Math.random()
	})

	var starStatus = false;
	//var startSize = 15;
	var startSize = starList.length;
	var next = false;

	var currentStarIndex = 1;
	var photoIndex = 1;

	$('.guess-continue').on('click', function(e) {
		//		layer.msg('continue');
		if(photoIndex == 7) {
			$('.guess-answer').click();
			return;
		}

		photoIndex++;

		//$('.star-warp img').attr('src', pageInfo.path + '/static/resource/img/guess/star/' + chooseList[currentStarIndex] + '/' + photoIndex + '.png');

		$('.star-warp img').attr('src', pageInfo.path + '/static/resource/img/guess/star/' + starList[parseInt(currentStarIndex) - 1] + '/' + photoIndex + '.png');
	})

	$('.guess-answer').on('click', function(e) {
		//layer.msg('answer');
		photoIndex = 8;
		$('.star-warp img').attr('src', pageInfo.path + '/static/resource/img/guess/star/' + starList[parseInt(currentStarIndex) - 1] + '/' + photoIndex + '.png');

		$(this).hide();
		$('.guess-continue').hide();
		$('.guess-rechoose').show();
		next = true;
		starStatus = false;
	})

	$('#preStar').on('click', function(e) {
		//console.log("123");
		if(starStatus) {
			layer.msg('请先揭晓答案');
			return;
		}

		if(next) {
			$('.guess-rechoose').click();
		}

		if(currentStarIndex == 1) {
			layer.msg('没有更多了');
			return;
		}

		currentStarIndex--;
		//		$('#starIndex').attr('src', pageInfo.path + '/static/resource/img/guess/' + currentStarIndex + '.png')
		$('#starIndex').html(currentStarIndex);

	})

	$('#nextStar').on('click', function(e) {
		if(starStatus) {
			layer.msg('请先揭晓答案');
			return;
		}

		if(next) {
			$('.guess-rechoose').click();
		}

		if(currentStarIndex == startSize) {
			layer.msg('没有更多了');
			return;
		}
		currentStarIndex++;
		//$('#starIndex').attr('src', pageInfo.path + '/static/resource/img/guess/' + currentStarIndex + '.png')
		$('#starIndex').html(currentStarIndex);
	})

	$('.guess-start').on('click', function(e) {
		$(this).hide();
		$('.guess-answer').show();
		$('.guess-continue').show();
		starStatus = true;
		//		$('#nextStar').addClass('disabled');
		//		$('#preStar').addClass('disabled');
		//$('.star-warp img').attr('src', pageInfo.path + '/static/resource/img/guess/star/' + chooseList[currentStarIndex] + '/' + photoIndex + '.png');
		$('.star-warp img').attr('src', pageInfo.path + '/static/resource/img/guess/star/' + starList[parseInt(currentStarIndex) - 1] + '/' + photoIndex + '.png');

		if(flag) {
			$('.guess-answer').click()
		}
	})

	$('.guess-rechoose').on('click', function() {
		photoIndex = 1;
		$('.star-warp img').attr('src', '');
		$(this).hide();
		starStatus = false;
		next = false;
		$('.guess-start').show();
	})

	setTimeout(function() {
		var audio = new Audio();
		audio.src = pageInfo.path + '/static/resource/soundef/guess.mp3';
		//载入图片
		for(var i = 1; i < starList.length; i++) {
			//			var img1 = new Image();
			//			img1.src = pageInfo.path + '/static/resource/img/guess/' + i + '.png';
			for(var j = 1; j < 9; j++) {
				var img = new Image();
				//	console.log(pageInfo.path+'/static/resource/img/guess/star/'+ chooseList[i]+'/'+j+'.png');
				img.src = pageInfo.path + '/static/resource/img/guess/star/' + starList[i] + '/' + j + '.png';
			}
		}
	}, 5000)
})();

var planeGift = (function() {
	var c = {};

	c.start = function() {
		
		if(!document.querySelector(".gift-plane .plane")){
			this.render();
		}
		
		//plane 动画
		snabbt(document.querySelector(".gift-plane .plane"), {
			fromOpacity: 1,
			opacity: 1,
			fromPosition: [0, 0, 0],
			position: [-1400, 250, 0],
			duration: 5000,
			//					easing: 'ease',
		}).snabbt({
			fromScale: [1, 1],
			scale: [1.2, 1.2],
			fromPosition: [-1400, 250, 0],
			position: [-1650, 250, 0],
			duration: 3000,
			//					easing: 'ease',
		}).snabbt({
			duration: 1000,
		}).snabbt({
			fromScale: [1.2, 1.2],
			scale: [1, 1],
			fromPosition: [-1650, 250, 0],
			position: [-3000, 350, 0],
			duration: 1000,
		});
		
		snabbt(document.querySelector(".gift-plane .ground"), {
			fromOpacity: 0,
			opacity: 1,
			duration: 1000
		}).snabbt({
			fromOpacity: 1,
			opacity: 0,
			duration: 1000,
			delay:8000
		});
	};

	c.render = function() {

	};

	return c;
})();