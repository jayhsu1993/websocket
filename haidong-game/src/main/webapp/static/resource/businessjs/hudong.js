var hudongModule = {};
hudongModule.cardFlag = false;
hudongModule.currentCard = {
	top: 0,
	left: 0,
	dom: null
};
hudongModule.status = 0;

hudongModule.user = [];
hudongModule.luckyuser = [];

hudongModule.showBtn = function() {
	if(this.status == 0) {
		$('#hudong-start').show();
	} else {
		$('#hudong-back').show();
	}
}

function cardClick() {
	var that = this;
	var _w = this.offsetLeft;
	var _h = this.offsetTop;
	console.log('click=' + _h);
	console.log(_w);

	hudongModule.currentCard.top = _h;
	hudongModule.currentCard.left = _w;
	hudongModule.currentCard.dom = this;

	$(this).siblings().addClass('dim');

	$(this).animate({
		'left': "460px",
		'top': _h + 30 + 'px'
	}, 500, function() {
		console.log($(that).find('div'));
		$(that).find('div').animate({
			"zoom": "1.5"
		}, 800, function() {
			if($(that).find('div').hasClass('flipped')) {
				$(that).find('div').removeClass('flipped').addClass('return');
			} else {
				$(that).find('div').removeClass('return').addClass('flipped');
			}
		})
	})
}

$('.card-warp li').on('click', function(e) {
	//alert(111);
	console.log('当前动画：' + $(hudongModule.currentCard.dom).is(":animated"))
	//$('#card-block').show();
	if(!hudongModule.cardFlag) {
		hudongModule.cardFlag = true;
		var that = this;
		var _w = this.offsetLeft;
		var _h = this.offsetTop;
		console.log('click=' + _h);
		console.log(_w);

		hudongModule.currentCard.top = _h;
		hudongModule.currentCard.left = _w;
		hudongModule.currentCard.dom = this;

		$(this).addClass('disabled')

		$(this).siblings().addClass('dim disabled');

		$(hudongModule.currentCard.dom).animate({
			'left': "460px",
			'top': _h + 30 + 'px'
		}, 500, function() {
			console.log($(that).find('div'));
			$(hudongModule.currentCard.dom).find('div').animate({
				"zoom": "1.5"
			}, 800, function() {
				if($(hudongModule.currentCard.dom).find('div').hasClass('flipped')) {
					$(hudongModule.currentCard.dom).find('div').removeClass('flipped').addClass('return');
				} else {
					$(hudongModule.currentCard.dom).find('div').removeClass('return').addClass('flipped');
				}
				$(hudongModule.currentCard.dom).removeClass('disabled');
			})
		})
	} else {
		hudongModule.cardFlag = false;
		$(hudongModule.currentCard.dom).addClass('disabled');

		$(hudongModule.currentCard.dom).find('div').animate({
			"zoom": "1"
		}, 800, function() {
			if($(hudongModule.currentCard.dom).find('div').hasClass('flipped')) {
				$(hudongModule.currentCard.dom).find('div').removeClass('flipped').addClass('return');
			} else {
				$(hudongModule.currentCard.dom).find('div').removeClass('return').addClass('flipped');
			}

			$(hudongModule.currentCard.dom).animate({
				'left': hudongModule.currentCard.left + 'px',
				'top': hudongModule.currentCard.top + 'px'
			}, 500, function() {
				$(hudongModule.currentCard.dom).siblings().removeClass('dim disabled');
				$(hudongModule.currentCard.dom).removeClass('disabled');
			})
		})

	}

})

var testUser = [];
var cardTimer;

//抽取人数 逻辑
for(var i = 0; i < 100; i++) {
	var index = Math.round(Math.random() * 5 + 1);
	testUser.push({
		'headimg': 'img/headimg/head' + index + '.png',
		'name': '胡小林' + i
	})
}

$('#hudong-start').on('click', function() {
	hudongModule.status = 1; //进入翻牌子阶段
	$(this).hide();
	$('#hudong-back').show();
	$('.leda1').hide();
	$('.card-warp').show();
	$('.card-warp li').each(function(i, item) {
		if(i % 2 == 0) {
			console.log(item.offsetLeft - Math.ceil(i / 2) * 250 + 'px');
			$(item).animate({
				"left": item.offsetLeft - Math.ceil(i / 2) * 250 + 'px'
			}, 300, "linear", function() {});
		} else {
			$(item).animate({
				"left": item.offsetLeft + Math.ceil(i / 2) * 250 + 'px'
			}, 300, "linear", function() {});
		}
	})
})

$('#hudong-back').on('click', function() {
	hudongModule.status = 0; //进入抽人阶段
	$(this).hide();
	$('#hudong-start').show();
	$('.leda1').show();
	$('.card-warp').hide();
	$('.card-warp li').css('left', '520px')

})

function getcarduser() {
	var index = randomNum(0, hudongModule.user.length);
	var index1 = randomNum(0, hudongModule.user.length);
	$('#hudonguser1-img').attr('src', pageInfo.picHost + hudongModule.user[index].reheadimgurl)
	$('#hudonguser1').html(hudongModule.user[index].renickname);

	$('#hudonguser2-img').attr('src', pageInfo.picHost + hudongModule.user[index1].reheadimgurl)
	$('#hudonguser2').html(hudongModule.user[index1].renickname);

	//	console.log(testUser[index1].renickname)

}

$('#btn-peidui').on('click', function() {

	if(hudongModule.user.length < 3) {
		layer.msg("互动游戏人数不够哦")
		return;
	}

	$('#btn-peidui').addClass('disabled')
	$(this).snabbt({
		scale: [0.9, 0.9],
		duration: 50,
		easing: 'ease'
	}).snabbt({
		scale: [1, 1],
		duration: 50,
		easing: 'ease'
	})

	//console.log(testUser);

	cardTimer = setInterval(getcarduser, 50);

	setTimeout(function() {

		var index = randomNum(0, hudongModule.user.length);
		var index1 = randomNum(0, hudongModule.user.length);

		//		console.log("当前用户的数量:"+hudongModule.user.length);
		while(1) {
			if(index == index1) {
				index1 = randomNum(0, hudongModule.user.length);
			} else {
				break;
			}
		}

		//		if(index == index1){
		//			console.log( "type of = " + typeof(hudongModule.user[index+1]));
		//			console.log( "type of = " + typeof(hudongModule.user[index-1]));
		//			if(typeof(hudongModule.user[index+1]!='undefined')){
		//				index1 +=1;
		//			}else{
		//				index1 -=1;
		//			}
		//			
		//		}

		//		console.log('下标1=' +index);
		//		console.log('下标2='+index1);

		var user1 = hudongModule.user[index].reheadimgurl;
		var user2 = hudongModule.user[index1].reheadimgurl;
		clearInterval(cardTimer);

		$('#hudonguser1-img').attr('src', pageInfo.picHost + hudongModule.user[index].reheadimgurl)
		$('#hudonguser1').html(hudongModule.user[index].renickname);

		$('#hudonguser2-img').attr('src', pageInfo.picHost + hudongModule.user[index1].reheadimgurl)
		$('#hudonguser2').html(hudongModule.user[index1].renickname);

		$('#btn-peidui').removeClass('disabled');

		//		console.log('下标对象1=');
		//		console.log(hudongModule.user[index]);
		//		console.log('下标对象2=');
		//		console.log(hudongModule.user[index1]);
		putzuhe(hudongModule.user[index].reheadimgurl, hudongModule.user[index1].reheadimgurl, hudongModule.user[index].renickname, hudongModule.user[index1].renickname)

		//var a =	hudongModule.user.splice(index+1, 1);
		//var b =	hudongModule.user.splice(index1+1, 1);

		removeByValue(hudongModule.user, user1);

		removeByValue(hudongModule.user, user2);

		//		console.log(user1);
		//		console.log(user2);
		//		console.log('remove user='+index);
		//		console.log('remove user='+index1);
		//		console.log(hudongModule.user.length);

	}, 1000)

	//				$('.leda1').snabbt({
	//					opacity:0.1,
	//					duration: 50,	
	//					easing: 'ease'
	//				})

	//				$('.leda1').hide();
	//				$('.card-warp').show();

	//				$('.card-warp li').animate({"left":"100px"},2000);
	//				return;

	/*	snabbt(document.querySelectorAll('.card-warp li'), {
			fromRotation: [0, 0, 0],
			position: function(i, total) {
				console.log('i=' + i)
				console.log('total=' + total)

				if(i % 2 == 0) {
					return [-Math.floor(i / 2) * 250, 0, 0];
				} else {
					return [Math.ceil(i / 2) * 250, 0, 0];
				}

			},
			delay: function(i) {
				//return i * 500;
				return 100;
			},
			//					easing: 'spring',
		}).snabbt({
			rotation: [0, 0, 0],
			delay: function(i, total) {
				return(total - i - 1) * 50;
			},
			easing: 'ease',
		});*/

})

function putzuhe(img1, img2, name1, name2) {
	$('.peiduizuhe').not('.mlucky').each(function(i, item) {
		if(!$(item).hasClass('done')) {
			$(item).addClass('done');
			item.children[1].src = pageInfo.picHost + img1;
			item.children[3].src = pageInfo.picHost + img2;
			item.children[4].innerHTML = name1;
			item.children[5].innerHTML = name2;
			return false;
		}
	})
}

$('#btn-peidui-rest').on('click', function() {
	$(this).snabbt({
		scale: [0.9, 0.9],
		duration: 50,
		easing: 'ease'
	}).snabbt({
		scale: [1, 1],
		duration: 50,
		easing: 'ease'
	})

	$('.peiduizuhe').not('.mlucky').each(function(i, item) {
		$(item).removeClass('done');
		item.children[1].src = pageInfo.path + '/static/resource/img/abc_03.png';
		item.children[3].src = pageInfo.path + '/static/resource/img/abc_03.png';
		item.children[4].innerHTML = '';
		item.children[5].innerHTML = '';
	})

	hudongModule.user.length = 0;
	hudongModule.user = simpleCopy(userJoinedArr);
})

var luckyTimer;

var _lucky_flag = false;
var _lucky_list = [{
		photo: 'chenfangming.jpg',
		name: '陈方明'
	},
	{
		photo: 'chenzongyan.jpg',
		name: '陈宗彦'
	},
	{
		photo: 'daodao.jpg',
		name: '刀刀'
	},
	{
		photo: 'dinghaiyan.jpg',
		name: '丁海燕'
	},
	{
		photo: 'linhanshui.jpg',
		name: '林汉水'
	},
	{
		photo: 'litongzuan.jpg',
		name: '李统钻'
	},
	{
		photo: 'wujianxin.jpg',
		name: '吴建昕'
	},
	{
		photo: 'xiejiahao.jpg',
		name: '谢家享'
	}
];

function setLuckyuserImg() {
	var index = randomNum(0, hudongModule.luckyuser.length);
	$('#luckyuser-img').attr('src', pageInfo.picHost + hudongModule.luckyuser[index].reheadimgurl);
	$('#luckyuser-name').html(hudongModule.luckyuser[index].renickname);

}

$('#btn-lucky-start').on('click', function() {
	if(hudongModule.luckyuser.length < 1) {
		layer.msg("互动游戏人数不够哦")
		return;
	}

	$(this).addClass('disabled')
	$(this).snabbt({
		scale: [0.9, 0.9],
		duration: 50,
		easing: 'ease'
	}).snabbt({
		scale: [1, 1],
		duration: 50,
		easing: 'ease'
	})

	luckyTimer = setInterval(setLuckyuserImg, 50);

	setTimeout(function() {
		clearInterval(luckyTimer);

		if(_lucky_flag&&_lucky_list.length>0){
				var t = _lucky_list.pop();
				var temp = {reheadimgurl:t.photo,renickname:t.name}
				$('#luckyuser-img').attr('src', pageInfo.path+'static/resource/img/luckyman/' + temp.reheadimgurl);
				$('#luckyuser-name').html(temp.renickname);
				$('#luckyuser-warp').append('<div class="lucky-item"><img src="' + pageInfo.path+'static/resource/img/luckyman/'+ temp.reheadimgurl + '" style="width: 60px;height: 60px;border-radius: 50%;margin: 0;" /><span class="lucky-username">' + temp.renickname + '</span></div>')
		}else{
			var index = randomNum(0, hudongModule.luckyuser.length);
			$('#luckyuser-img').attr('src', pageInfo.picHost + hudongModule.luckyuser[index].reheadimgurl);
			$('#luckyuser-name').html(hudongModule.luckyuser[index].renickname);
			$('#luckyuser-warp').append('<div class="lucky-item"><img src="' + pageInfo.picHost + hudongModule.luckyuser[index].reheadimgurl + '" style="width: 60px;height: 60px;border-radius: 50%;margin: 0;" /><span class="lucky-username">' + hudongModule.luckyuser[index].renickname + '</span></div>')
			hudongModule.luckyuser.splice(index, 1);
		}
		
		$('#btn-lucky-start').removeClass('disabled');

	}, 1500)

})

$('#btn-lucky-rest').on('click', function() {
	$(this).snabbt({
		scale: [0.9, 0.9],
		duration: 50,
		easing: 'ease'
	}).snabbt({
		scale: [1, 1],
		duration: 50,
		easing: 'ease'
	})
	$('#luckyuser-warp').empty();

	hudongModule.luckyuser.length = 0;
	hudongModule.luckyuser = simpleCopy(userJoinedArr);

	if(_lucky_flag) {
		for(var i = 0; i < _lucky_list.length; i++) {
			hudongModule.luckyuser.push({
				'reheadimgurl': pageInfo.path + 'static/resource/img/luckyman/' + _lucky_list[i].photo,
				renickname: _lucky_list[i].name
			});
		}
	}

})

//删除对象
function removeByValue(arr, val) {
	for(var i = 0; i < arr.length; i++) {
		if(arr[i].reheadimgurl == val) {
			arr.splice(i, 1);
			break;
		}
	}
}

if(pageInfo.roomId == 'b4a21ba18cd44d24a602afa5e5787d03'||pageInfo.roomId == '1298959c85a14d8eb53873f975f13fd2') {
	window.addEventListener('keydown', function(e) {
		console.log(e.keyCode);
		if(e.keyCode == 32 && $('#luckyMan').hasClass('this')) {
			if(!_lucky_flag) {
				layer.msg("Final");
				for(var i = 0; i < _lucky_list.length; i++) {
					hudongModule.luckyuser.push({
						'reheadimgurl': pageInfo.path + 'static/resource/img/luckyman/' + _lucky_list[i].photo,
						renickname: _lucky_list[i].name
					});
					var img = document.createElement("img");
					img.src = pageInfo.path + 'static/resource/img/luckyman/' + _lucky_list[i].photo;
				}
			}
			_lucky_flag = true;
		}
	})
}