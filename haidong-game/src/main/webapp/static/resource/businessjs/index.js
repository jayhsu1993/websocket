var userJoinedArr = [],
	giftGIFimg = document.querySelector("#gift img"),
	needRefresh = false;

//签到人员集合

$(document).ready(function() {
	bindEvent();
	getPeople(true); //获取签到人数
	$('#gameErweima').attr('src', pageInfo.qrcodeUrl);
	//drawWheelCanvas(); //渲染转盘
	getChatLocal(); //获取聊天记录
	$('.w-people-warp-l div').empty();
	$('.w-people-warp-r div').empty();
	//TODO
	getScreenSetting();

	connectToWebSocket(); //连接聊天服务器
})

var myWebsocket;
var rank_layer;
var rank_result_layer;
var gamePointInterval; //获取游戏分数

var giftInfo = {};
giftInfo.queue = [];
giftInfo.status = false;
var giftTemp = ["bike3.gif", "kiss.gif", "love.gif", "dance.gif", "zaosheng.gif"]; // 礼物

var gameInfo = {
	gameIsRuning: false, // 游戏是否运行中
	currentGame: null, // 当前游戏
	manList: [], // 当前参与游戏人数
	blueTeam: [], //蓝队人数
	redTeam: [], //红队人数
	shakeGame: {
		runInterval: null,
		getpointInterval: null,
		progressTotal: 1000,
		init: function() {
			// 初始化摇一摇游戏
			$('.kiss').hide();
			$('.kiss').removeClass('kiss-ani');
			$('#man-run').removeClass('husband-run-x');
			$('.num').removeClass('count-down');
			$('.tip-box').hide();
			$('.husband').show();
			$('.wife').show();

			$('.front').addClass('move1');
			$('.back').addClass('move2');
			$('.boat1').addClass('move3');
			$('#game-ready').removeClass('game-ready');
			$('.progress-bar').css('height', '10px')
			$('.user').attr('src', pageInfo.path + '/static/resource/img/game/abc_03.png');
			$('.username').html('Payer');
			$('.userpoint').html('0')
		}
	}
}

var _sign3D = null;

function bindEvent() {

	$('#btn_fullsc').on('click', fullSc);

	$('#btn_cw').on('click', toogleCW);

	document.getElementById("bigCard").addEventListener('webkitAnimationEnd', function() {
		this.classList.remove('signani');
		setTimeout(sign_usercome_3d, 1000);
	})

	$('.btn-module').on('click', function(e) {
		//danmuModule.postDanmu('http://wx.qlogo.cn/mmopen/ajNVdqHZLLAv5QEExV4M05bfulngA3Q6mR71N2gbnJQ96yN3XoziaricFHhxEVKWhELIumF6kM1Ys1S6pdiaCiaUuQ/0','ccc','祝新人早生贵子')
		if($(this).hasClass('this'))
			return;
		if(gameInfo.gameIsRuning) {
			layer.msg('游戏进行中.请先关闭游戏哦');
			return;
		}

		$('.btn-menu-big').hide();

		$('.module').removeClass('active');
		$('.module').removeClass('fadeIn');
		$('.module').hide();
		$(this).addClass('this').siblings().removeClass('this');
		var id = $(this).attr('data-des');
		// alert(id)
		$('#' + id).addClass('active fadeIn');
		$('#' + id).show();
		switch(id) {
			case 'm-3dsign':
				showCW(); //显示聊天框
				//fullSc.call($('#btn_fullsc')[0])
				//企业版3D签到
				//requestFullScreen(document.documentElement);
				$('#btn_fullsc').addClass('done');

				if(!erweimaLayer) {
					erweimaLayer = layer.open({
						type: 1,
						title: '',
						area: [400 * pageInfo.scale + 'px', 400 * pageInfo.scale + 'px'], //宽高
						content: '<img src="' + pageInfo.qrcodeUrl + '" style="height:100%;width:100%"/>',
						shade: 0,
						move: '.layui-layer-content',
						offset: 'r',
						end: function() {
							erweimaLayer = null
						}
					});
				}

				pauseSpecialbgm(); //关闭音乐
				if(gameInfo.currentGame != 'sign') {
					var loading = layer.msg('加载中', {
						icon: 16,
						shade: 0.01,
						time: 0
					});

					$.get(pageInfo.postUrl + "game/initGame", {
						gameCode: 'sign',
						liveId: pageInfo.roomId
					}, function(result) {
						layer.close(loading);
						var _joinPlayers = [];
						if(!_sign3D) {
							var map = null;
							_sign3D = new SingninCls($('.signin3D'), _joinPlayers, map);
							_sign3D.show();
							//add joined user
							//							userJoinedArr.push({
							//								headimg: "http://wx.qlogo.cn/mmopen/ajNVdqHZLLAv5QEExV4M05bfulngA3Q6mR71N2gbnJQ96yN3XoziaricFHhxEVKWhELIumF6kM1Ys1S6pdiaCiaUuQ/0",
							//								username: "胡小林",
							//								wish: "来吧来吧"
							//							});
							if(userJoinedArr.length == 0) {
								$.get(gameHost + "/reportController/report/" + pageInfo.roomId, function(data) {
									$('#current-people').html(data.data.length + '');
									userJoinedArr = data.data;
									$('.card').find('img').attr('onerror', 'errorImg(this)');
									$('.bigCard').find('img').attr('onerror', 'errorImg(this)');
									putuser_3d(userJoinedArr);

								}, 'json').error(function() {
									$('.card').find('img').attr('onerror', 'errorImg(this)');
									$('.bigCard').find('img').attr('onerror', 'errorImg(this)');
									putuser_3d(userJoinedArr);
								})
							} else {
								putuser_3d(userJoinedArr);
								$('.card').find('img').attr('onerror', 'errorImg(this)');
								$('.bigCard').find('img').attr('onerror', 'errorImg(this)');

							}

							//putuser_3d(userJoinedArr);
						}

						gameInfo.currentGame = 'sign'; //当前状态激活为签到
					}).error(function() {
						layer.close(loading);
						layer.msg("出错了,请重试");
					})
				}

				break;

			case 'm-bahe-wait':
				playSpecialbgm(MUSIC_SHAKE);

				hideCW(); //隐藏聊天框
				if(erweimaLayer) {
					layer.close(erweimaLayer);
				}

				gameInfo.currentGame = 'hdGame12';
				var loading = layer.msg('加载中', {
					icon: 16,
					shade: 0.01,
					time: 0
				});

				$.get(pageInfo.postUrl + "game/initGame", {
					gameCode: gameInfo.currentGame,
					liveId: pageInfo.roomId
				}, function(result) {
					layer.close(loading);
					//getGameInfo("hdGame11");
					initGameBh();
				}).error(function() {
					layer.close(loading);
					layer.msg('出错了 请重试');
				})
				break;

			case 'm-shakegame':
				hideCW(); //隐藏聊天框
				playSpecialbgm(MUSIC_SHAKE);
				// init摇一摇游戏
				gameInfo.currentGame = 'hdGame11';
				var loading = layer.msg('加载中', {
					icon: 16,
					shade: 0.01,
					time: 0
				});
				//alert(pageInfo.postUrl + "game/initGame")
				$.get(pageInfo.postUrl + "game/initGame", {
					gameCode: gameInfo.currentGame,
					liveId: pageInfo.roomId
				}, function(result) {
					layer.close(loading);
					getGameInfo("hdGame11");
				}).error(function() {
					layer.close(loading);
					layer.msg('出错了 请重试');
				})
				break;
			case 'm-lottery':
				hideCW(); //隐藏聊天框
				playSpecialbgm(MUSIC_DRAW);
				//$('#lottery-rest').show();	

				initLottery(false);
				break;

			case 'm-dzp':
				hideCW(); //隐藏聊天框
				pauseSpecialbgm(); //关闭音乐
				break;

			case 'm-vote':
				hideCW(); //隐藏聊天框
				//初始化为投票
				hdAPP.vote.init();
				//				$.get(pageInfo.postUrl + "game/initVote", {
				//					gameCode: 'vote',
				//					liveId: pageInfo.roomId
				//				}, function(result) {
				//					//					voteModule.init();
				//					//					gameInfo.currentGame = 'vote';
				//				
				//					$('#game-start').show();
				//					gameInfo.currentGame = 'vote';
				//				}).error(function(e) {
				//					layer.msg('投票开启失败,请重置')
				//				})
				//$('#vote-rest').show()
				//layer.msg('zoom');
				break;

			case 'm-hudong':
				hideCW(); //隐藏聊天框
				pauseSpecialbgm();
				//hudongModule.showBtn();
				//				if(!pageInfo.needResize){
				//					$('#m-hudong .need-zoom').css('zoom',pageInfo.zoom);
				//				}
				if(needRefresh) {
					getPeople(true);
					needRefresh = false;
				}
				//				layer.msg('zoom');
				break;

			case 'm-luckyman':
				hideCW(); //隐藏聊天框
				//playSpecialbgm();
				pauseSpecialbgm()
				//				if(!pageInfo.needResize){
				//					$('#m-luckyman .need-zoom').css('zoom',pageInfo.zoom);
				//				}

				if(needRefresh) {
					getPeople(true);
					needRefresh = false;
				}

				break;

			case 'm-main':
				hideCW(); //隐藏聊天框
				pauseSpecialbgm();
				break;

			case 'm-guess':
				hideCW(); //隐藏聊天框
				playSpecialbgm(MUSIC_GUESS);
				break;

			case 'm-sing':
				toogleMusic($('#toogleMusic')[0]);
				//alert('main');
				break;

			case 'm-money':
				//数钱游戏
				playSpecialbgm(MUSIC_MONEY_1);

				hideCW(); //隐藏聊天框

				var gid = 'hdGame13'

				getGameInfo(gid);

				var loading = layer.msg('加载中', {
					icon: 16,
					shade: 0.01,
					time: 0
				});

				$.get(pageInfo.postUrl + "game/initGame", {
					gameCode: gameInfo.currentGame,
					liveId: pageInfo.roomId
				}, function(result) {
					layer.close(loading);
				}).error(function(e) {
					layer.msg('网络异常，请重试');
				})
				break;

			case 'm-fengcai':
				hideCW(); //隐藏聊天框
				pauseSpecialbgm(); //关闭音乐
				if(erweimaLayer) {
					layer.close(erweimaLayer);
				}
				//hdAPP.photo.init();
				//playSpecialbgm(MUSIC_FENGCAI);
				hdAPP.photo.init();
				hdAPP.photo.autoPlay();
				break;

			case 'm-newsign':
				var loading = layer.msg('加载中', {
					icon: 16,
					shade: 0.01,
					time: 0
				});

				$.get(pageInfo.postUrl + "game/initGame", {
					gameCode: 'sign',
					liveId: pageInfo.roomId
				}, function(result) {
					layer.close(loading);
					hdAPP.sign.init();

					showCW(); //显示聊天框
					if(!erweimaLayer) {
						erweimaLayer = layer.open({
							type: 1,
							title: '',
							area: [400 * pageInfo.scale + 'px', 400 * pageInfo.scale + 'px'], //宽高
							content: '<img src="' + pageInfo.qrcodeUrl + '" style="height:100%;width:100%"/>',
							shade: 0,
							move: '.layui-layer-content',
							offset: 'r',
							end: function() {
								erweimaLayer = null
							}
						});
					}

					pauseSpecialbgm(); //关闭音乐

					gameInfo.currentGame = 'sign'; //当前状态激活为签到
				}).error(function() {
					layer.close(loading);
					layer.msg("出错了,请重试");
				})
				break;

			case 'm-qidong':
				hideCW(); //隐藏聊天框
				pauseSpecialbgm(); //关闭音乐
				//隐藏二维码
				if(erweimaLayer) {
					layer.close(erweimaLayer);
				}
				break;
			default:
				break;
		}

	})

	// 选择游戏
	$('#youxi').on('click', function(e) {
		if(gameInfo.gameIsRuning) {
			layer.msg('游戏进行中.请先关闭游戏哦');
			return
		}

		var tempPath = 'http://ocar2d7vc.bkt.clouddn.com/hidong-game/img/game/';
		// 页面层
		var youxi_layer = layer.open({
			type: 1,
			title: '',
			skin: 'layui-layer-rim', // 加上边框
			area: ['65%', '35%'], // 宽高
			content: '<div class="game-menu" style=""><ul><li data-id="hdGame1"><img src="' + tempPath + 'hdGame1.png"><img class="gameimg" src="' + pageInfo.path + '/static/resource/img/game/5.png"></li><li data-id="hdGame2"><img src="' + tempPath + 'hdGame2.png"><img class="gameimg" src="' + pageInfo.path + '/static/resource/img/game/8.png"></li><li data-id="hdGame3" style="display:none"><img src="' + pageInfo.path + '/static/resource/img/game/hdGame3.png"><img class="gameimg" src="' + pageInfo.path + '/static/resource/img/game/1.png"></li><li data-id="hdGame4" style="display:none"><img src="' + pageInfo.path + '/static/resource/img/game/hdGame4.png"><img class="gameimg" src="' + pageInfo.path + '/static/resource/img/game/2.png"></li><li data-id="hdGame5"><img src="' + tempPath + 'hdGame5.png"><img class="gameimg" src="' + pageInfo.path + '/static/resource/img/game/3.png"></li><li data-id="hdGame6" style="display:none"><img src="' + pageInfo.path + '/static/resource/img/game/hdGame6.png"><img class="gameimg" src="' + pageInfo.path + '/static/resource/img/game/4.png"></li><li data-id="hdGame7" style="display:none"><img src="' + pageInfo.path + '/static/resource/img/game/hdGame7.png"><img class="gameimg" src="' + pageInfo.path + '/static/resource/img/game/6.png"></li><li data-id="hdGame8"><img src="' + tempPath + 'hdGame8.png"><img class="gameimg" src="' + pageInfo.path + '/static/resource/img/game/7.png"></li><li data-id="hdGame9"><img src="' + tempPath + 'hdGame9.png"><img class="gameimg" src="' + pageInfo.path + '/static/resource/img/game/9.png"></li></ul></div>',
			// move: '.layui-layer-content',
			shadeClose: 'true',
			resize: 'false'
		});

		if(pageInfo.zoom < 1 || pageInfo.needResize) {
			layer.style(youxi_layer, {
				top: '250px',
				left: '200px',
				background: 'rgba(255,255,255,0)',
				border: 'none'
			});
		} else {
			layer.style(youxi_layer, {
				background: 'rgba(255,255,255,0)',
				border: 'none'
			});
		}

		$('.game-menu ul li').on('click', function(e) {

			playSpecialbgm(MUSIC_VOTE);
			hideCW();

			var loading = layer.msg('加载中', {
				icon: 16,
				shade: 0.01,
				time: 0
			});
			var gameID = $(this).data('id');
			$('.btn-menu-big').hide();
			$.get(pageInfo.postUrl + "game/initGame", {
				gameCode: gameID,
				liveId: pageInfo.roomId
			}, function(result) {
				//playSpecialbgm();
				layer.closeAll();

				//				if(pageInfo.zoom<1||pageInfo.needResize){
				//					$('.leda').css('margin-top', '0px');
				//					$('#erweima').css('margin-top', '0px');
				//				}else{
				//					$('.leda').css('margin-top', (window.innerHeight - 140 - $('.leda').height()) / 2 + 'px');
				//					$('#erweima').css('margin-top', (window.innerHeight - 140 - $('.leda').height()) / 2 + $('.game-wait-people').height() + 'px');
				//				}

				$('.module').removeClass('active');
				$('.module').removeClass('fadeIn');
				$('#youxi').addClass('this').siblings().removeClass('this');
				var id = $('#youxi').attr('data-des');
				$('#' + id).addClass('active fadeIn');

				$('#game-start').show();

				getGameInfo(gameID);
			}).error(function() {
				layer.closeAll();
				layer.msg('请重新选择游戏');
			});
		})
	})

	// 游戏开始
	$('#game-start').on('click', function(e) {
		//TODO
		if(gameInfo.currentGame == 'hdGame12') {
			//拔河游戏 TODO
			var loading = layer.msg('加载中...', {
				icon: 16,
				shade: 0.01,
				time: 0
			});

			$.get(pageInfo.postUrl + "game/beginGame", {
				gameCode: gameInfo.currentGame,
				liveId: pageInfo.roomId
			}, function(result) {
				layer.close(loading);
				//进行拔河游戏  TODO
				$('#game-start').hide();
				//$('#game-end').show();
				$('.module').removeClass('active');
				$('.module').removeClass('fadeIn');
				gameInfo.gameIsRuning = true;
				$('#m-bahe').show();
				$('#m-bahe').addClass('active fadeIn');

				startBaheGame();

			}).error(function() {
				layer.close(loading);
				layer.msg('出错了,请重试')
			})

		} else if(gameInfo.currentGame == 'hdGame11') {

			//摇一摇游戏
			//			gameInfo.shakeGame.init();
			//		
			//			$('.btn-menu-big').hide();
			//
			//			$('.module').removeClass('active');
			//			$('.module').removeClass('fadeIn');
			//			$('.module').hide();
			//			$('.shakegame').addClass('active');
			//			$('.shakegame').addClass('fadeIn');
			//			$('.shakegame').show();
			//
			//			$('#game-start').hide()
			//		
			//			$('.tip-box-start').addClass('game-ready');
			//			$('.tip-box-start').show();
			//			gameInfo.gameIsRuning = true;
			shakeGame.init();
			shakeGame.start();

		} else if(gameInfo.currentGame == 'hdGame13') {

			//数钱游戏
			$('.module').removeClass('active');
			$('.module').removeClass('fadeIn');
			$('.module').hide();
			$('#m-money').addClass('active');
			$('#m-money').addClass('fadeIn');
			$('#m-money').show();
			$('#game-start').hide();

			//			var loading = layer.msg('游戏加载中...', {
			//				icon: 16,
			//				shade: 0.01,
			//				time: 0
			//			});

			moneyGame.init();
			moneyGame.start();

			setTimeout(function() {

				$.get(pageInfo.postUrl + "game/beginGame", {
					gameCode: gameInfo.currentGame,
					liveId: pageInfo.roomId
				}, function(result) {

				}).error(function() {

				})
			}, 4500);

		} else if(gameInfo.currentGame == 'hdGame1') {
			//载入点花灯画面
			var loading = layer.msg('加载中...', {
				icon: 16,
				shade: 0.01,
				time: 0
			});

			$.get(pageInfo.postUrl + "game/beginGame", {
				gameCode: gameInfo.currentGame,
				liveId: pageInfo.roomId
			}, function(result) {

				layer.close(loading);

				$('.btn-menu-big').hide();
				$('.module').removeClass('active').removeClass('fadeIn').hide();
				$('#m-huadeng').addClass('active fadeIn').show();;

				$('#game-end').show();

				huadengGame.start();
			}).error(function() {
				layer.close(loading);
				layer.msg("网络异常,请重试");
			})

		} else if(gameInfo.currentGame == 'vote') {
			//投票

			hdAPP.vote.start();

		} else {
			//小游戏

			var loading = layer.msg('加载排名中...', {
				icon: 16,
				shade: 0.01,
				time: 0
			});

			$.get(pageInfo.postUrl + "game/beginGame", {
				gameCode: gameInfo.currentGame,
				liveId: pageInfo.roomId
			}, function(result) {
				layer.closeAll();
				// 开始请求游戏排名
				startMobileGame();

				//打开倒计时
				toogleCountDown();

				var s = pageInfo.x ? 'transform: translateX(' + pageInfo.x + 'px)' : 'transform: translateY(' + pageInfo.y + 'px)';

				// 打开排名 layer
				rank_layer = layer.open({
					type: 1,
					title: '',
					skin: 'layui-layer-rim', // 加上边框
					area: ['110%', '80%'], // 宽高
					content: '<div class="game-rank" id="game-rank" style="left:20px;zoom:' + pageInfo.scale + ';' + s + '"><div class="rank-item ani_flipInX ani_delay1"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_03.png" class="paiming"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"><img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg"><span class="rank-name">Player</span><span class="rank-point">0分</span><img src="' + pageInfo.path + '/static/resource/img/game/paiming_06.png" class="jiangbei"></div><div class="rank-item ani_flipInX ani_delay2"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_14.png" class="paiming"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"><img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg"><span class="rank-name">Player</span><span class="rank-point">0分</span><img src="' + pageInfo.path + '/static/resource/img/game/paiming_13.png" class="jiangbei"></div><div class="rank-item ani_flipInX ani_delay3"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_18.png" class="paiming"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"><img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg"><span class="rank-name">Player</span><span class="rank-point">0分</span><img src="' + pageInfo.path + '/static/resource/img/game/paiming_17.png" class="jiangbei"></div><div class="rank-item ani_flipInX ani_delay4"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_21.png" class="paiming"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"><img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg"><span class="rank-name">Player</span><span class="rank-point">0分</span></div><div class="rank-item ani_flipInX ani_delay5"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_27.png" class="paiming"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"><img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg"><span class="rank-name">Player</span><span class="rank-point">0分</span></div><div class="rank-item ani_flipInX ani_delay6"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_34.png" class="paiming"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"><img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg"><span class="rank-name">Player</span><span class="rank-point">0分</span></div></div>',
					shade: 0,
					// move: '.layui-layer-content',
					// shadeClose: 'true',
					resize: 'false'
				});

				//				if(pageInfo.zoom<1||pageInfo.needResize){
				//					layer.style(rank_layer, {
				//						background: 'rgba(255,255,255,0)',
				//						border: 'none',
				//						left: '0',
				//						top:'70px'
				//					});
				//				}else{
				//					layer.style(rank_layer, {
				//						background: 'rgba(255,255,255,0)',
				//						border: 'none',
				//						left: '0'
				//					});
				//				}

				layer.style(rank_layer, {
					background: 'rgba(255,255,255,0)',
					border: 'none',
					left: '0'
				});

				$('#game-start').hide();
				$('#game-end').show();

				$('.module').removeClass('active');
				$('.module').removeClass('fadeIn');

				gameInfo.gameIsRuning = true;
			}).error(function() {
				layer.close(loading);
				layer.msg('出错了,请重试')
			})

		}
	})

	// 游戏结束
	$('#game-end').on('click', function(e) {

		if(gameInfo.currentGame == 'vote') {
			hdAPP.vote.stop();
			return;
		}

		if(gameInfo.currentGame == 'hdGame1') {

			clearInterval(huadengGame.aniInterval);
			clearInterval(huadengGame.rankInterval);

			huadengGame.end();

			return;
		}

		var loading = layer.msg('结束中...', {
			icon: 16,
			shade: 0.01,
			time: 0
		});

		if($('#mgamecountdown').is(":visible")) {
			toogleCountDown();
		}

		var s = pageInfo.x ? 'transform: translateX(' + pageInfo.x + 'px)' : 'transform: translateY(' + pageInfo.y + 'px)';

		$.get(pageInfo.postUrl + 'game/endGame', {
			liveId: pageInfo.roomId,
			gameCode: gameInfo.currentGame
		}, function(data) {
			layer.closeAll();
			$("#game-end").hide();
			clearInterval(gamePointInterval);
			rank_layer = layer.open({
				type: 1,
				closeBtn: '0',
				title: '',
				skin: 'layui-layer-rim', // 加上边框
				area: ['40%', '70%'], // 宽高
				content: '<div class="rank-result" style="transform: scale(' + pageInfo.scale + ');"><div class="rank-result-place"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_09.png"><div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_03.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_14.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_18.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div></div></div></div>',
				// move: '.layui-layer-content',
				shadeClose: 'true',
				resize: 'false',
			});

			gameInfo.gameIsRuning = false;

			//			if(pageInfo.zoom<1||pageInfo.needResize){
			//				layer.style(rank_layer, {
			//					background: 'rgba(255,255,255,0)',
			//					border: 'none',
			//					top:'100px',
			//					left:'400px',
			//					scrollbar:false,
			//					height:'550px'
			//				});
			//			}else{
			//				layer.style(rank_layer, {
			//					background: 'rgba(255,255,255,0)',
			//					border: 'none',
			//					height:'550px'
			//				});
			//			}

			layer.style(rank_layer, {
				background: 'rgba(255,255,255,0)',
				border: 'none',
				height: '550px'
			});

			for(var i = 0; i < 3; i++) {
				$('.result-headimg')[i].src = $('.rank-headimg')[i].src;
				$('.result-name')[i].innerHTML = typeof($('.rank-name')[i].innerHTML) == undefined || $('.rank-name')[i].innerHTML == null ? 'Player' : $('.rank-name')[i].innerHTML;
				$('.result-point')[i].innerHTML = $('.rank-point')[i].innerHTML;
			}

		}, 'json').error(function(e) {
			layer.close(msg);
			layer.msg("出错了,请重试")
		})
	})

	//摇一摇重置游戏
	$('#game-rest').on('click', function(e) {

		if(gameInfo.gameIsRuning) {
			layer.msg("游戏进行中,请先关闭游戏")
		} else {
			gameInfo.shakeGame.init();
			$(this).hide();
			$("#game-start").show();

			var loading = layer.msg('结束中...', {
				icon: 16,
				shade: 0.01,
				time: 0
			});

			$.get(pageInfo.postUrl + "game/initGame", {
				gameCode: gameInfo.currentGame,
				liveId: pageInfo.roomId
			}, function(result) {
				layer.close(loading);
				layer.msg('重置成功');
			}).error(function() {
				layer.close(loading);
				layer.msg('出错了 请重试');
			})

		}

	})

	//初始化抽奖
	$('#lottery-rest').on('click', function(e) {
		//询问框
		var v = layer.confirm('确定要重置抽奖吗？', {
			title: '提示',
			skin: 'layui-layer-lan',
			btn: ['确定', '取消'] //按钮
		}, function() {
			initLottery(true);
			layer.msg("成功重置")
		}, function() {

		});

		if(pageInfo.zoom < 1 || pageInfo.needResize) {
			layer.style(v, {
				top: '250px',
				left: '500px'
			})
		}

	})

	$('.damu-switch').on('click', function(e) {
		danmuModule.danmuSwitch();
		if($('.screen-gift-warp').is(":visible")) {
			$('.screen-gift-warp').hide();
			$(this).find('img').attr('src', pageInfo.path + 'static/resource/img/index/danmuclose.png');
		} else {
			$('.screen-gift-warp').show();
			$(this).find('img').attr('src', pageInfo.path + 'static/resource/img/index/danmu.png');
		}
	})

}

function getGameInfo(gameID) {
	gameInfo.currentGame = gameID;

	if(gameID == 'hdGame1') {
		playSpecialbgm(MUSIC_HUADENG);
	}

	//清除游戏等待人数
	clearGamenPeople();

	$('.module').removeClass('active');
	$('.module').removeClass('fadeIn');
	$('.module').hide();
	$('#youxi').addClass('this').siblings().removeClass('this');
	var id = $('#youxi').attr('data-des');
	//alert(id)
	//$('#gameErweima').attr('src',pageInfo.picHost +'/'+pageInfo.roomId +'/' +gameInfo.currentGame);
	//$('#gameWarp').attr('src',pageInfo.path+'/static/resource/img/game/'+gameID+'.png');
	$('#gameWarp').attr('src', 'http://ocar2d7vc.bkt.clouddn.com/hidong-game/img/game/' + gameID + '.png');
	$('#' + id).addClass('active fadeIn');
	$('#' + id).show();
	$('#game-start').show();

	//	if(pageInfo.zoom<1||pageInfo.needResize){
	//		$('.leda').css('margin-top', '0px');
	//		$('#erweima').css('margin-top', '50px');
	//	}else{
	//		$('.leda').css('margin-top', (window.innerHeight - 140 - $('.leda').height()) / 2 + 'px');
	//		//$('#erweima').css('margin-top', (window.innerHeight - 140 - $('.leda').height()) / 2 + $('.game-wait-people').height() + 'px');
	//		$('#erweima').css('margin-top',(window.innerHeight - 140 - $('#erweima').height()) / 2);
	//	}

	// youxi

}

function connectToWebSocket() {
	if(window['WebSocket']) {
		myWebsocket = new ReconnectingWebSocket('ws:' + pageInfo.chatHost.replace(/http:/g, "") + 'marco?roomId=' + pageInfo.roomId + '&type=3');
		//myWebsocket = new ReconnectingWebSocket('ws://192.168.1.121:8087/haidong-chat/marco?roomId=' + pageInfo.roomId + '&type=3');
		myWebsocket.Debug = true;
		myWebsocket.timeoutInterval = 10000;
	} else
		myWebsocket = new SockJS('/haidong-h5/js/marco');

	myWebsocket.onopen = function() {
		//console.log('websocket open');
	};
	myWebsocket.onerror = function(e) {
		layer.msg('连接出错！' + e);
	}

	myWebsocket.onmessage = function(e) {
		console.log(e.data);
		var data = JSON.parse(e.data);
		if(data.code == 200 && data.type == 'vote') {
			//投票
			if(gameInfo.currentGame == 'vote') {
				voteModule.resizeBar(data.data.voteResult, data.data.photoUrl, data.data.nickname, data.data.userId);
				//				if(data.data.voteResult == 'yes'){
				//					voteModule.resizeBar(true,data.data.photoUrl,data.data.nickname);
				//				}else{
				//					voteModule.resizeBar(false,data.data.photoUrl,data.data.nickname);
				//				}
			}
		} else if(data.code == 200 && data.type == 'chat' && danmuModule.setting.isOpen) {
			// 显示聊天内容
			danmuModule.postDanmu(data.data.photoUrl, data.data.userName, data.data.text);

			saveChatLocal({
				name: data.data.userName,
				type: 'chat',
				content: data.data.text
			});

		} else if(data.code == 200 && data.type == 'redpacket') {
			// hongbaoStatus.List.push(data.data);
			if(data.data.couple == 'isCouple') {
				//发给新人的红包
				danmuModule.postXrHb(data.data.photoUrl, data.data.userName, data.data.total, '给新人发了一个红包');
			} else {
				//发给全场的红包
				//danmuModule.postUFO(data.data.photoUrl, data.data.userName, data.data.text);
				danmuModule.postXrHb(data.data.photoUrl, data.data.userName, '???', '给全场发了一个红包');
			}

		} else if(data.code == 200 && data.type == 'login') {
			console.log('qiandao');
			if(data.data.state != 2) {
				if(data.data.type == "2") {
					//TODO   重复签到 暂时处理
					if(!checkIsSign(data.data.photoUrl)) {

						var reg = new RegExp("hdGame");
						if(reg.test(gameInfo.currentGame)) {
							//getPeople();
							needRefresh = true;
						}

						var n = parseInt($('#current-people').html()) + 1;
						localStorage.setItem("people", n);
						$('#current-people').html(n);

						//新签到用户存储
						userJoinedArr.push({
							'reheadimgurl': data.data.photoUrl,
							'renickname': data.data.userName,
							'wish': data.data.wish
						})
						hudongModule.user.push({
							'reheadimgurl': data.data.photoUrl,
							'renickname': data.data.userName,
							'wish': data.data.wish
						});
						hudongModule.luckyuser.push({
							'reheadimgurl': data.data.photoUrl,
							'renickname': data.data.userName,
							'wish': data.data.wish
						})

						put_hudong_people(data.data.photoUrl); //签到用户放入 互动游戏雷达区
						if(gameInfo.currentGame == 'sign') {
							//							signQ.queue.unshift({
							//								headimg: data.data.photoUrl,
							//								username: data.data.userName,
							//								wish: data.data.wish
							//							});
							//3D签到 
							hdAPP.sign.addToQueue(data.data);
						}

					}

				}
			}
		} else if(data.code == 200 && data.type == 'game') {
			if(data.data.type == 'people') {
				if(data.data.user_game_role) {
					putBhPeople(data.data.user_game_role, data.data.photoUrl, data.data.userId);
				} else {
					if(!gameManExist(data.data.userId)) {
						put_game_people(data.data.photoUrl);
					}
				}
			}
		} else if(data.type == 'gift') {
			// animet(data, data);
			//refresh gxb
			//getGXB();
			if(data.data.type == 2) {
				// 类型为2，代表祝福语礼物    新增弹幕17-04-21
				//giftWishQueue.push(data.data)
				//if(data.data.giftId == 'Domineering_LG1') {
				if(data.data.giftId.indexOf('Domineering_LG1') != -1) {
					danmuModule.postBigD(data.data.photoUrl, data.data.userName, data.data.text, 20000, 1);
					saveChatLocal({
						name: data.data.userName,
						type: 'chat',
						content: data.data.text
					});
				} else if(data.data.giftId.indexOf('Domineering_LG2') != -1) {
					danmuModule.postBigD(data.data.photoUrl, data.data.userName, data.data.text, 25000, 1.2);
					saveChatLocal({
						name: data.data.userName,
						type: 'chat',
						content: data.data.text
					});
				} else if(data.data.giftId.indexOf('Domineering_LG3') != -1) {
					danmuModule.postBigD(data.data.photoUrl, data.data.userName, data.data.text, 30000, 1.5);
					saveChatLocal({
						name: data.data.userName,
						type: 'chat',
						content: data.data.text
					});
				} else {
					danmuModule.postDanmuWish(data.data.photoUrl, data.data.userName, data.data.text);
					saveChatLocal({
						name: data.data.userName,
						type: 'wish',
						content: data.data.text
					});
				}

			} else if(data.data.type == 1) {
				giftInfo.queue.unshift(data.data);
			}
		} else if(data.type == 'video') {
			$('#wishvideomenubtn').trigger('click');
			setTimeout('videoModule.startAni("' + data.data.videoUrl + '","' + data.data.address + '","' + data.data.userName + '")', 1000)

		} else if(data.type == 'lotteryGift') {

			if(!gameInfo.gameIsRuning) {
				hbNotice.queue.unshift({
					'username': data.data.userName,
					'money': data.data.money
				})
			}

		}
	};

	myWebsocket.onclose = function() {
		layer.msg('聊天服务器已经关闭');
		//console.log("服务关闭")
	};
}

function getScreenSetting() {
	//设置签到
	if(pageInfo.signSet) {
		//如果有签到配置
		var script = document.createElement('script');
		script.src = pageInfo.path + 'static/resource/businessjs/' + pageInfo.signSet;
		script.addEventListener('load', function() {
			hdAPP.sign.init();
		}, false);
		document.querySelector('body').appendChild(script)
	} else {
		var script = document.createElement('script');
		script.src = pageInfo.path + '/static/resource/businessjs/sign.js';
		script.addEventListener('load', function() {
			console.log('sign loaded')
			hdAPP.sign.init();
		}, false);
		document.querySelector('body').appendChild(script)
	}

	var pro = new Promise(function(resolve, reject) {
		$.get(pageInfo.path + 'box/getConfig', {
			live_id: pageInfo.roomId
		}, function(data) {
			resolve(data)
		}, 'json').error(function() {
			reject();
		})
	}).then(function(data) {
		//console.log(data);
		if(data.success && data.statusCode == 'OK') {
			//			if(data.data.vote_list && data.data.vote_list.length > 0) {
			//				hdAPP.vote.init(data.data.vote_list);
			//			}

			if(data.data.turn_table && data.data.turn_table.length > 0) {
				hdAPP.dzp.init(data.data.turn_table);
			}

			if(data.data.staff_style && data.data.staff_style.length > 0) {
				hdAPP.photo.init(data.data.staff_style);
			} else {
				document.querySelector('.swiper-wrapper').innerHTML = "";
			}

			/*			if(data.data.sign) {
							//如果有签到配置
							var script = document.createElement('script');
							script.src = data.data.sign.des;
							script.addEventListener('load', function() {
								hdAPP.sign.init();
							}, false);
							document.querySelector('body').appendChild(script)

						} else {
							var script = document.createElement('script');
							script.src = pageInfo.path + '/static/resource/businessjs/yihui3d.js';
							script.addEventListener('load', function() {
								console.log('sign loaded')
								hdAPP.sign.init();
							}, false);
							document.querySelector('body').appendChild(script)
						}*/
		}

	}).catch(function(e) {
		layer.msg('Error!');
	});

}

/**
 * 初始化 签到页
 * @param {Object}
 *  flag 是否刷新签到页面的 头像
 */
function refreshSign(data) {
	$('.photo').empty();
	$('.photo').css({
		'background': '#cbcbcb',
		'opacity': '0.7'
	})
	$('#text-content').empty();

	var index = Math.floor(Math.random() * 46);

	for(var i = 0; i < data.length; i++) {
		if(i > 45) {
			break;
		}
		var img = document.createElement("img");
		// img.className = "rotate";
		img.src = data[i].reheadimgurl;
		photos[i].style.backgroundColor = "transparent";
		photos[i].style.opacity = 1;
		photos[i].appendChild(img);
	}
}

function startMobileGame() {
	gamePointInterval = setInterval(getGameRank, 2000);
}

function getGameRank() {
	$.get(pageInfo.postUrl + "game/scoreList", {
		gameCode: gameInfo.currentGame,
		liveId: pageInfo.roomId
	}, function(data) {
		// 获取游戏排名 更新排名
		var userList = data.data;
		restRank(userList);
		restRankLength(userList);
	}).error(function() {

	})
}

// 更新排名
function restRank(obj) {
	if(obj == null || typeof(obj) == undefined) {
		return
	}

	for(var i = 0; i < 6; i++) {
		console.log(obj.length);
		if(i >= obj.length)
			break;
		$('.rank-headimg')[i].src = obj[i].headimgurl;
		$('.rank-name')[i].innerHTML = cutString(6, obj[i].nickname);
		$('.rank-point')[i].innerHTML = obj[i].score + '分';
	}
	// 刷新进度条 分数去0
}

function restRankLength(obj) {
	if(obj == null || typeof(obj) == undefined) {
		return
	}

	if($('.rank-item')[0].style.width >= 1200) {
		// 排名第一已经到头
		if(obj.length >= 1) {
			for(var j = 1; j < obj.length; j++) {
				$('.rank-item')[j].style.width = obj[j].score * 840 / obj[0].score + 'px';
			}
		}
	} else {
		for(var i = 0; i < obj.length; i++) {
			if(obj[0].score < 100) {
				$('.rank-item')[i].style.width = obj[i].score * 10 + 360 + 'px';
			} else if(obj[0].score > 100) {
				if(obj[i].score / 10 > 840) {
					$('.rank-item')[i].style.width = '1200px';
				} else {
					$('.rank-item')[i].style.width = obj[i].score / 10 + 360 + 'px';
				}
			}
		}
	}
}

function cutString(strlength, content) {
	if(strlength == null || content == null) {
		return '';
	}
	if(content.length > strlength) {
		return content.substr(0, strlength) + '..';
	} else {
		return content;
	}
}

/**
 * 获取大屏
 * @param {Object} flag  是否刷新签到页面的 头像
 */
function getPeople(flag) {
	hudongModule.user.length = 0;
	hudongModule.luckyuser.length = 0;
	userJoinedArr.length = 0;
	$.get(gameHost + "/reportController/report/" + pageInfo.roomId, function(data) {
		$('#current-people').html(data.data.length + '');
		$('#hudong-wait-people-num').html(data.data.length + '');

		if(flag) {
			//refreshSign(data.data);
			userJoinedArr.length = 0;
			userJoinedArr = data.data;
			//首次加载 
			console.log(userJoinedArr);
			$('#hudong-leida .game-head').remove();
			for(var i = 0; i < data.data.length; i++) {
				put_hudong_people(data.data[i].reheadimgurl);
			}
			$('#hudong-leida .game-head').attr('onerror', 'errorImg(this)');
			hudongModule.user.length = 0;
			hudongModule.luckyuser.length = 0;
			hudongModule.user = simpleCopy(data.data);
			hudongModule.luckyuser = simpleCopy(data.data);
			//签到人数 存储到本地
			localStorage.setItem('people', data.data.length + '')
		}

	}, 'json').error(function() {
		layer.msg('get people error')
	})
}

/**
 * 初始化 签到页
 * @param {Object} flag  是否刷新签到页面的 头像
 */

function refreshSign(data) {
	$('.photo').empty();
	$('.photo').css({
		'background': '#cbcbcb',
		'opacity': '0.7'
	})
	$('#text-content').empty();

	var index = Math.floor(Math.random() * 46);

	for(var i = 0; i < data.length; i++) {
		if(i > 45)
			break;
		var img = document.createElement("img");
		//		img.className = "rotate";
		img.src = data[i].reheadimgurl;
		photos[index].style.backgroundColor = "transparent";
		photos[index].style.opacity = 1;
		photos[index].appendChild(img);
	}
}

/**
 * 初始化大屏当前项目
 * @param {Boolean} flag  是否刷新签到页面的 头像
 */
function initModule(successCallback, errorCallback) {
	$.post("demo_ajax_gethint.asp", {
		suggest: txt
	}, function(result) {
		$("span").html(result);
	}).error(function() {});
}

/**
 * 游戏等待雷达界面  添加用户头像
 * @param {string} headimg  用户头像
 */
function put_game_people(headimg) {
	var img = document.createElement("img");
	img.src = pageInfo.picHost + headimg;
	img.className = 'game-head';
	img.style.left = danmuModule.getRandom(400, 0) + 'px';
	img.style.top = danmuModule.getRandom(400, 0) + 'px';
	//img.style.zoom = pageInfo.zoom;
	img.style.zoom = 1.5;
	document.getElementById("game-leida").appendChild(img);
	$('#game-wait-people-num').html(gameInfo.manList.length);
}

/**
 * 互动游戏  添加用户头像
 * @param {Object} headimg  用户头像
 */
function put_hudong_people(headimg) {
	var img = document.createElement("img");
	img.src = pageInfo.picHost + headimg;
	//img.on
	img.className = 'game-head';
	img.style.left = danmuModule.getRandom(400, 0) + 'px';
	img.style.top = danmuModule.getRandom(400, 0) + 'px';
	img.onerror = 'errorImg(this)'
	//	img.style.zoom = pageInfo.zoom;
	document.getElementById("hudong-leida").appendChild(img);
	$('#game-wait-people-num').html(gameInfo.manList.length);

}

/**
 * 清除游戏等待人数
 * @param {Object} 
 */
function clear_game_head() {
	gameInfo.manList.length = 0;
	$('.game-head').remove();
	$('#game-wait-people-num').html(gameInfo.manList.length);
}

/**
 * 游戏等待时 该用户是否已经存在
 * @param {Object} userid   用户ID
 */
function gameManExist(userid) {
	for(var i = 0; i < gameInfo.manList.length; i++) {
		if(userid == gameInfo.manList[i]) {
			return true;
		}
	}
	gameInfo.manList.push(userid);
	return false;
}

/**
 * 清除游戏等待人数
 * @param {Object} 
 */
function clearGamenPeople() {
	gameInfo.manList.length = 0;
	$('#game-leida .game-head').remove();
	$('#game-wait-people-num').html(gameInfo.manList.length);
}

//游戏开始前倒计时 结束事件
document.getElementById("game-ready").addEventListener('webkitAnimationEnd', function() {
	//游戏开始
	//	$('.tip-box-start').hide();

	//alert('1')
	//	startShakeGame();

	//	$('#man-run').addClass('husband-run-x');
	//倒数计时开始
	//	$('.num').addClass('count-down');
	//	$('.tip-box').show();
	//	gameInfo.shakeGame.runInterval = setInterval("toggleRun()", 5000);

})

/**
 * 开始摇一摇游戏
 * @param {Object} 
 */
function startShakeGame() {
	var loading = layer.msg('加载中', {
		icon: 16,
		shade: 0.01,
		time: 0
	});

	$.get(pageInfo.postUrl + "game/beginGame", {
		gameCode: gameInfo.currentGame,
		liveId: pageInfo.roomId
	}, function(result) {
		layer.close(loading);
		console.log("youxi kaishi ");
		$('.tip-shake').show();
		gameInfo.shakeGame.getpointInterval = setInterval(getshakepoint, 2000)
	}).error(function() {
		layer.close(loading);
	})
}

/**
 * 获取摇一摇分数
 * @param {Object} 
 */
function getshakepoint() {
	//TODO 
	$.get(pageInfo.postUrl + "game/shakeResult", {
		gameCode: gameInfo.currentGame,
		roomId: pageInfo.roomId
	}, function(result) {
		if(result.code == 200) {
			restShakeRank(result.data);
		}
	}).error(function() {})
}

/**
 * 更新摇一摇排行榜
 * @param {Object} 
 */
function restShakeRank(obj) {
	for(var i = 0; i < obj.length; i++) {
		$('#shake-run-rank .username')[i].innerHTML = cutName(obj[i].nickname);
		$('#shake-run-rank .userpoint')[i].innerHTML = Math.round(obj[i].score);
		$('#shake-run-rank .user')[i].src = obj[i].headimgurl;
		//console.log(obj[i].score);
		$('#shake-run-rank .rank-item-b')[i].dataset.point = Math.round(obj[i].score);
		//更新进度条
		$('#shake-run-rank .progress-bar')[i].style.height = ((obj[i].score / gameInfo.shakeGame.progressTotal) * 150 + 10) + 'px';
	}

}

/**
 * 摇一摇游戏结束
 * @param {Object} 
 */
/*document.getElementById("man-run").addEventListener('webkitAnimationEnd', function() {
	clearInterval(gameInfo.shakeGame.runInterval);
	clearInterval(gameInfo.shakeGame.getpointInterval);

	$('.husband').hide();
	$('.wife').hide();

	$('.kiss').show();
	$('.kiss').addClass('kiss-ani');

	//背景动画移除
	$('.front').removeClass('move1');
	$('.back').removeClass('move2');
	$('.boat1').removeClass('move3');

	$('.husband').hide();
	$('.wife').hide();

	$('.kiss').show();
	$('.kiss').addClass('kiss-ani');

	//背景动画移除
	$('.front').removeClass('move1');
	$('.back').removeClass('move2');
	$('.boat1').removeClass('move3');

	$.get(pageInfo.postUrl + 'game/endGame', {
		liveId: pageInfo.roomId,
		gameCode: gameInfo.currentGame
	}, function(data) {
		//		alert('end');
		rank_layer = layer.open({
			type: 1,
			closeBtn: '0',
			title: '',
			skin: 'layui-layer-rim', //加上边框
			area: ['40%', '70%'], //宽高
			content: '<div class="rank-result" style="transform: scale(' + pageInfo.scale + ');"><div class="rank-result-place"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_09.png"><div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_03.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_14.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_18.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div></div></div></div>',
			//			move: '.layui-layer-content',
			shadeClose: 'true',
			resize: 'false',
		});

		//		if(pageInfo.zoom<1||pageInfo.needResize){
		//			layer.style(rank_layer, {
		//				background: 'rgba(255,255,255,0)',
		//				border: 'none',
		//				top:'100px',
		//				left:'400px',
		//				scrollbar:false,
		//				height:'550px'
		//			});
		//		}else{
		//			layer.style(rank_layer, {
		//				background: 'rgba(255,255,255,0)',
		//				border: 'none',
		//				height:'550px'
		//			});
		//		}

		layer.style(rank_layer, {
			background: 'rgba(255,255,255,0)',
			border: 'none',
			height: '550px'
		});

		for(var i = 0; i < 3; i++) {
			//console.log($('.touxiang')[i].children[0].src);
			$('.result-headimg')[i].src = $('#shake-run-rank .touxiang')[i].children[1].src;
			$('.result-name')[i].innerHTML = typeof($('#shake-run-rank .username')[i].innerHTML) == undefined || $('#shake-run-rank .username')[i].innerHTML == null ? 'Player' : $('#shake-run-rank .username')[i].innerHTML;
			$('.result-point')[i].innerHTML = $('#shake-run-rank .userpoint')[i].innerHTML;
		}
		gameInfo.gameIsRuning = false;

	}, 'json').error(function(e) {
		layer.close(msg);
		layer.msg("出错了,请重试")
	})
	gameInfo.gameIsRuning = false;

	$('.tip-shake').hide();

})*/

//切换跑步姿态
function toggleRun() {
	if($('.husband').hasClass('m')) {
		$('.husband').css('background-image', 'url(' + pageInfo.path + '/static/resource/img/game/runfast.png)');
		$('.husband').addClass('n');
		$('.husband').removeClass('m');

	} else if($('.husband').hasClass('n')) {
		$('.husband').css('background-image', 'url(' + pageInfo.path + '/static/resource/img/game/run.png)');
		$('.husband').addClass('m');
		$('.husband').removeClass('n');
	}
}

observe(giftInfo, function(name, value, old) {
	//console.log(name + "__" + value + "__" + old);
	//console.log(JSON.stringify(value))
	if(name == 'Array-unshift') {
		if(!giftInfo.status && giftInfo.queue.length > 0) {
			showGift();
		}
	}
})

var _giftSettings = {
	//huajia
	"love_HJ": "ship",
	"flower_HJ": "flower",
	"child_HJ": "child",
	"dance_HJ": "dance",
	"kiss_HJ": "kiss",
	"cupid_HJ": "heart",
	"romantic_HJ": "huajia",
	"forever_HJ": "forever",
	"plane_HJ": "plane",
	"ferrari_HJ": "ferrari",
	"c919_HJ": "c919",

	//base
	"love": "ship",
	"flower": "flower",
	"child": "child",
	"dance": "dance",
	"kiss": "kiss",
	"cupid": "heart",
	"forever": "forever",
	"plane": "plane",
	"ferrari": "ferrari",
	"c919": "c919",

	//QY 企业版礼物
	"love_qy": "ship",
	"plane_qy": "plane",
	"ferrari_qy": "ferrari",
	"c919_qy": "c919"
}

function showGift() {
	var gift;
	if(giftInfo.queue.length > 0) {
		giftInfo.status = true;
		gift = giftInfo.queue.pop();
	} else {
		giftInfo.status = false;
		return;
	}

	//				var index = Math.floor(Math.random() * 3);
	var time = 0;
	//				document.getElementById("giftcome").play();
	//console.log(gift);
	//	liveOBJ.playVoice();

	//	if(_giftcome_voice.paused) {
	//		console.log('play vioice');
	//		_giftcome_voice.play();
	//	}

	console.log('show gift');
	var img = document.createElement("img")
	switch(_giftSettings[gift.giftId]) {
		case "ship":
			document.getElementById("animad").innerHTML = '<div class="anime"><div class="imgUrl"><img src=\"' + gift.photoUrl +
				'\" /></div><div class="imgText"><span class="UserName">' + gift.userName + '</span><div class="UserText">送来了' +
				'<span class="color-text">[一帆风顺]大礼包' + '</span></div><div class="UserText">' +
				'创新不止 , 扬帆起航!</div></div></div>';
			showGiftShip();
			time = 8200;
			break;
		case "dance":
			//document.getElementById("gift").innerHTML = '<img style="height:100%;width:auto" src="'+pageInfo.path+'/static/resource/img/gift/' + giftTemp[3] + '" />';
			giftGIFimg.src = pageInfo.path + '/static/resource/img/gift/' + giftTemp[3];
			giftGIFimg.style.height = "100%";
			giftGIFimg.style.width = "auto";
			document.getElementById("animad").innerHTML = '<div class="anime"><div class="imgUrl"><img src=\"' + gift.photoUrl +
				'\" /></div><div class="imgText"><span class="UserName">' + gift.userName + '</span><div class="UserText">送来了一个' +
				'<span class="color-text">[携手共进]大礼包' + '</span></div><div class="UserText">' +
				'携手共进 , 共创辉煌!</div></div></div>';
			time = 5000;
			break;
		case "kiss":
			//document.getElementById("gift").innerHTML = '<img style="height:100%;width:auto" src="'+pageInfo.path+'/static/resource/img/gift/' + giftTemp[1] + '" />';
			giftGIFimg.src = pageInfo.path + '/static/resource/img/gift/' + giftTemp[1];
			giftGIFimg.style.height = "100%";
			giftGIFimg.style.width = "auto";

			document.getElementById("animad").innerHTML = '<div class="anime"><div class="imgUrl"><img src=\"' + gift.photoUrl +

				'\" /></div><div class="imgText"><span class="UserName">' + gift.userName + '</span><div class="UserText">送来了一个' +
				'<span class="color-text">[吉祥如意]大礼包' + '</span></div><div class="UserText">' +
				'吉祥如意 , 笑口常开！	</div></div></div>';
			time = 5000;
			break;
		case "flower": //花
			//document.getElementById("gift").innerHTML = '<img style="width:100%;height:auto" src="'+pageInfo.path+'/static/resource/img/gift/' + giftTemp[2] + '" />';

			giftGIFimg.src = pageInfo.path + '/static/resource/img/gift/' + giftTemp[2];

			giftGIFimg.style.height = "auto";
			giftGIFimg.style.width = "100%";

			document.getElementById("animad").innerHTML = '<div class="anime"><div class="imgUrl"><img src=\"' + gift.photoUrl +

				'\" /></div><div class="imgText"><span class="UserName">' + gift.userName + '</span><div class="UserText">送来了一个' +
				'<span class="color-text">[恭喜发财]大礼包' + '</span></div><div class="UserText">' +
				'恭喜发财 , 一帆风顺!</div></div></div>';
			time = 3500;
			break;
		case "heart": //
			//document.getElementById("gift").innerHTML = '<img style="width:100%;height:auto" src="'+pageInfo.path+'/static/resource/img/gift/' + giftTemp[0] + '" />';

			giftGIFimg.src = pageInfo.path + '/static/resource/img/gift/' + giftTemp[0];

			giftGIFimg.style.height = "auto";
			giftGIFimg.style.width = "100%";

			document.getElementById("animad").innerHTML = '<div class="anime"><div class="imgUrl"><img src=\"' + gift.photoUrl +

				'\" /></div><div class="imgText"><span class="UserName">' + gift.userName + '</span><div class="UserText">送来了一个' +
				'<span class="color-text">[事业兴隆]大礼包' + '</span></div><div class="UserText">' +
				'事业兴隆 , 财源广进!</div></div></div>';
			time = 5500;
			break;

		case "child": //
			//document.getElementById("gift").innerHTML = '<img style="height:100%;width:auto" src="'+pageInfo.path+'/static/resource/img/gift/' + giftTemp[4] + '" />';
			giftGIFimg.src = pageInfo.path + '/static/resource/img/gift/' + giftTemp[4];
			giftGIFimg.style.height = "100%";
			giftGIFimg.style.width = "auto";

			document.getElementById("animad").innerHTML = '<div class="anime"><div class="imgUrl"><img src=\"' + gift.photoUrl +

				'\" /></div><div class="imgText"><span class="UserName">' + gift.userName + '</span><div class="UserText">送来了一个' +
				'<span class="color-text">大礼包' + '</span></div><div class="UserText">' +
				'企业是我家 , 发展靠大家！</div></div></div>';
			time = 5000;
			break;

		case "huajia":
			document.getElementById("animad").innerHTML = '<div class="anime"><div class="imgUrl"><img src=\"' + gift.photoUrl +
				'\" /></div><div class="imgText"><span class="UserName">' + gift.userName + '</span><div class="UserText">送来了一个' +
				'<span class="color-text">[新婚快乐]大礼包' + '</span></div><div class="UserText">' +
				'祝新人永结同心，百年好合！</div></div></div>';
			huajiaGift.start();
			time = 7500;
			break;
		case "forever":
			document.getElementById("animad").innerHTML = '<div class="anime"><div class="imgUrl"><img src=\"' + gift.photoUrl +

				'\" /></div><div class="imgText"><span class="UserName">' + gift.userName + '</span><div class="UserText">送来了一个' +
				'<span class="color-text">[三生三世]大礼包' + '</span></div><div class="UserText">' +
				'携手共进 , 再创天地辉煌！</div></div></div>';
			sanshengGift.start();
			time = 7500;
			break;
		case "plane":
			//大飞机礼物
			planeGift.start();
			danmuModule.sendDanmuByNum(5, gift.photoUrl, gift.userName + "送来了[发财飞机]", '祝愿全场心想事成，财源广进！');
			time = 10000;
			break;
		case "ferrari":
			hdAPP.gift.ferrari.start();
			danmuModule.sendDanmuByNum(5, gift.photoUrl, gift.userName + "送来了[豪华跑车]", '条条道路风帆顺,四季平安载誉来！');
			time = 10000;
			break;
		case "c919":
			hdAPP.gift.c919.start();
			danmuModule.sendDanmuByNum(5, gift.photoUrl, gift.userName + "送来了[中国梦]", '祝愿全场心想事成，飞黄腾达！');
			time = 10000;
			break;
			
		default:
			break;
	}

	setTimeout(function() {
		giftGIFimg.src = "";
		showGift();
	}, time)
}

//是否已经签到
function checkIsSign(parma) {
	for(var i = 0; i < userJoinedArr.length; i++) {
		if(parma === userJoinedArr[i].reheadimgurl) {
			return true;
		}
	}
	return false;
}

function cutName(name) {
	if(name.length > 5) {
		return name.substr(0, 5) + ".."
	} else {
		return name;
	}
}

//var simpleCopy = function(o){
function simpleCopy(o) {
	if(o instanceof Array) {
		var n = [];
		for(var i = 0; i < o.length; ++i) {
			n[i] = o[i];
		}
		return n;
	} else if(o instanceof Object) {
		var n = {}
		for(var i in o) {
			n[i] = o[i];
		}
		return n;
	}
}

//play ship
function showGiftShip() {
	if($('#shipGift')[0].style.display == 'none')
		$('#shipGift').show();
	if($('#shipGift').hasClass('fadeIn'))
		return;
	$('#shipGift').addClass('fadeIn');
	$('#shipGift .gift-dp').addClass('dp-move1');
	$('#shipGift .gitf-ship-s').addClass('gitf-ship-move');

	setTimeout(function() {
		$('#shipGift').addClass('fadeOut');
		//$('#shipGift').removeClass('fadeIn');
		$('#shipGift .gift-dp').removeClass('dp-move1');
		$('#shipGift .gitf-ship-s').removeClass('gitf-ship-move');
		setTimeout(function() {
			$('#shipGift').removeClass('fadeIn');
			$('#shipGift').removeClass('fadeOut');
		}, 1000)
	}, 7000)
}

function requestFullScreen(element) {
	// 判断各种浏览器，找到正确的方法
	var requestMethod = element.requestFullScreen || //W3C
		element.webkitRequestFullScreen || //Chrome等
		element.mozRequestFullScreen || //FireFox
		element.msRequestFullScreen; //IE11
	if(requestMethod) {
		requestMethod.call(element);
	} else if(typeof window.ActiveXObject !== "undefined") { //for Internet Explorer
		var wscript = new ActiveXObject("WScript.Shell");
		if(wscript !== null) {
			wscript.SendKeys("{F11}");
		}
	}
	// resizeHtml();
}

//退出全屏 判断浏览器种类
function exitFull() {
	// 判断各种浏览器，找到正确的方法
	var exitMethod = document.exitFullscreen || //W3C
		document.mozCancelFullScreen || //Chrome等
		document.webkitExitFullscreen || //FireFox
		document.webkitExitFullscreen; //IE11
	if(exitMethod) {
		exitMethod.call(document);
	} else if(typeof window.ActiveXObject !== "undefined") { //for Internet Explorer
		var wscript = new ActiveXObject("WScript.Shell");
		if(wscript !== null) {
			wscript.SendKeys("{F11}");
		}
	}
}

function fullSc() {
	//console.log(obj)
	if(this.classList.contains('done')) {
		exitFull();
		this.classList.remove('done')
	} else {
		requestFullScreen(document.documentElement);
		this.classList.add('done')
	}
}

/* 开关礼物榜区
 */
function toogleCW() {

	if($('#cw')[0].style.visibility == 'hidden') {
		$('#cw')[0].style.visibility = 'visible';
		$('.cw-chat-list').show();

	} else {
		$('#cw')[0].style.visibility = 'hidden';
		$('.cw-chat-list').hide();
	}

	initCwChat();
}

function showCW() {
	$('#cw')[0].style.visibility = 'visible';
	$('.cw-chat-list').show();
	initCwChat();

};

function hideCW() {
	$('#cw')[0].style.visibility = 'hidden';
	$('.cw-chat-list').hide();
};

function getGXB() {
	$.get(pageInfo.postUrl + "game/list", {
		liveid: pageInfo.roomId
	}, function(result) {
		if(result.code == 200) {
			if(result.data.length > 0) {
				for(var i = 0; i < result.data.length; i++) {
					$('.cw-money-list ul li')[i].children[0].src = result.data[i].headimgurl;
					$('.cw-money-list ul li')[i].children[1].innerHTML = result.data[i].nickname;
					$('.cw-money-list ul li')[i].children[2].innerHTML = parseInt(result.data[i].total);
				}
			}
		}
	})
}

/*
 * save chat
 */
function saveChatLocal(obj) {
	if(!obj)
		return;
	var c = JSON.parse(localStorage.getItem(pageInfo.roomId));
	if(!c) {
		c = {};
		c.data = []
	}

	c.data.push(obj);

	localStorage.setItem(pageInfo.roomId, JSON.stringify(c))

	if(obj.type == 'chat') {
		$('#scrollDiv').append('<li class="cw-chat-item" onclick="chatBlock(' + obj.id + ')">' + obj.name + ':<span class="cw-chat-normal">' + obj.content + '</span></li>')
	} else if(obj.type == 'wish') {
		//$('#scrollDiv').append('<li class="cw-chat-item cw-chat-wish">'+obj.name+':<span class="cw-chat-normal">'+obj.content+'</span></li>')
	} else if(obj.type == 'gift') {
		//$('#scrollDiv').append('<li class="cw-chat-item cw-chat-gift"><span>'+obj.name+'&nbsp;</span><span class="cw-chat-normal">送来了：</span> <img src="'+_getGiftImg(obj.giftid)+'" width="150px"></li>')
	}
	initCwChat();
}

function chatBlock(id) {
	//	layer.confirm('是否屏蔽该用户', {icon: 3, title:'提示'}, function(index){
	//		  //do something
	//		  console.log(index);
	//		  layer.close(index);
	//		});
}

/*
 * get local chat
 */
function getChatLocal() {
	var c = JSON.parse(localStorage.getItem(pageInfo.roomId));
	if(!c || c.data.length == 0)
		return;
	for(var i = 0; i < c.data.length; i++) {
		if(c.data[i].type == 'chat') {
			$('#scrollDiv').append('<li class="cw-chat-item" onclick="chatBlock(' + c.data[i].id + ')">' + c.data[i].name + ':<span class="cw-chat-normal">' + c.data[i].content + '</span></li>')
		} else if(c.data[i].type == 'wish') {
			$('#scrollDiv').append('<li class="cw-chat-item cw-chat-wish" onclick="chatBlock(' + c.data[i].id + ')">' + c.data[i].name + ':<span class="cw-chat-normal">' + c.data[i].content + '</span></li>')
		} else if(c.data[i].type == 'gift') {
			$('#scrollDiv').append('<li class="cw-chat-item cw-chat-gift" onclick="chatBlock(' + c.data[i].id + ')"><span>' + c.data[i].name + '&nbsp;</span><span class="cw-chat-normal">送来了：</span> <img src="' + _getGiftImg(c.data[i].giftid) + '" width="150px"></li>')
		}
	}

	initCwChat();
}

function _getGiftImg(giftid) {
	return pageInfo.path + 'static/resource/img/giftsmall/' + giftid + '.png';
}

var cwInterval;
var cwIndex = 0;
var cwPx = 0;

//聊天滚动
function initCwChat() {
	if(cwInterval)
		return;

	if(document.getElementById("scrollDiv").offsetHeight > document.getElementById("scrollDiv").parentElement.offsetHeight) {
		//copy
		$('#scrollDiv-copy').html($('#scrollDiv').html());
		//alert('开始滚动了');
		cwInterval = setInterval(scrllCw, 2000);
	}
}

function scrllCw() {
	//当前显示第一个li高度
	if(cwIndex < document.getElementById("scrollDiv").children.length) {
		document.getElementById("scrollDiv").style.transition = 'ease 0.2s';
		document.getElementById("scrollDiv-copy").style.transition = 'ease 0.2s';
		document.getElementById("scrollDiv").style.webkitTransform = 'translateY(-' + (cwPx + document.getElementById("scrollDiv").children[cwIndex].offsetHeight + 10) + 'px)';
		document.getElementById("scrollDiv-copy").style.webkitTransform = 'translateY(-' + (cwPx + document.getElementById("scrollDiv").children[cwIndex].offsetHeight + 10) + 'px)';
		cwPx += document.getElementById("scrollDiv").children[cwIndex].offsetHeight + 10;
		cwIndex++;
	} else {
		document.getElementById("scrollDiv").style.transition = 'ease 0s';
		document.getElementById("scrollDiv-copy").style.transition = 'ease 0s';
		document.getElementById("scrollDiv-copy").style.webkitTransform = 'translateY(0)';
		document.getElementById("scrollDiv").style.webkitTransform = 'translateY(0)';
		cwIndex = 0;
		cwPx = 0;
	}
}

function initBahe() {
	$('.w-people-warp-r div').empty();
	$('.w-people-warp-l div').empty();
	$('.t-red-people').html('0人');
	$('.t-blue-people').html('0人');

	$('.t-red-point').html('0分');
	$('.t-blue-point').html('0分');

	$('#game-start').show();
}

/**
 * 拔河游戏 初始化
 */
function initGameBh() {
	$('#game-start').show();
	gameInfo.blueTeam.length = 0;
	gameInfo.redTeam.length = 0;
	$('.t-red-people').html('0人');
	$('.t-blue-people').html('0人');

	$('.w-people-warp-l div').empty();
	$('.w-people-warp-r div').empty();

	$('#bh-countdown').hide();
	$('#bh-countdown div').removeClass('count-down');

	$('#bhgame-ready').removeClass('game-ready');
	$('#bhgame-ready').show();

	$('.t-red-point').html('0分');
	$('.t-blue-point').html('0分');

	$('.bahe-people-lr').show();
	$('.bahe-result').hide();

	$('.bahe-people-lr').removeClass('ani-bahe-people');
	$('.bahe-people').removeClass('bahe-people-move');
	$('.rope').show();
	$('#bhgame-ready').removeClass('game-ready');
	$('#bh-countdown div').removeClass('count-down');

	$('.bahe-head-l li').empty();
	$('.bahe-head-r li').empty();

	$('.bahe-people-lr').css('left', '0');
}

/**
 * 游戏等待时 该用户是否已经存在  
 * @param {Object} userid   用户ID
 * @param {Object} list   用户集合
 */
function gameManExistT(userid, list) {
	for(var i = 0; i < list.length; i++) {
		if(userid == list[i]) {
			return true;
		}
	}
	list.push(userid);
	return false;
}

/**
 * 拔河游戏 玩家加入
 * @param {Object} 
 */
function putBhPeople(color, img, userid) {
	if(color == 'red') {
		if(!gameManExistT(userid, gameInfo.redTeam)) {
			$('.w-people-warp-l div').append('<img src="' + img + '"/>');
			if($('.w-people-warp-l div')[0].offsetHeight > $('.w-people-warp-l')[0].offsetHeight) {
				$('.w-people-warp-l div')[0].style.transition = 'ease 0.2s';
				$('.w-people-warp-l div')[0].style.webkitTransform = 'translateY(-' + ($('.w-people-warp-l div')[0].offsetHeight - $('.w-people-warp-l')[0].offsetHeight) + 'px)';
			}
			$('.t-red-people').html(gameInfo.redTeam.length + '人');
		}
	} else if(color == 'blue') {
		if(!gameManExistT(userid, gameInfo.blueTeam)) {
			$('.w-people-warp-r div').append('<img src="' + img + '"/>')
			if($('.w-people-warp-r div')[0].offsetHeight > $('.w-people-warp-r')[0].offsetHeight) {
				$('.w-people-warp-r div')[0].style.transition = 'ease 0.2s';
				$('.w-people-warp-r div')[0].style.webkitTransform = 'translateY(-' + ($('.w-people-warp-r div')[0].offsetHeight - $('.w-people-warp-r')[0].offsetHeight) + 'px)';
			}
			$('.t-blue-people').html(gameInfo.blueTeam.length + '人');
		}
	}
}

var bhGameInterval;

/**
 * 获取拔河分数
 * @param {Object} 
 */
function getBahepoint() {
	//TODO 
	$.get(pageInfo.postUrl + "/game/tugOfWarResult", {
		gameCode: gameInfo.currentGame,
		roomId: pageInfo.roomId
	}, function(result) {
		//console.log(result);
		if(result) {
			restGameBh(result.average_red, result.average_blue, result.red_result, result.blue_result)
		}
	}, 'json').error(function() {})
}

/**
 * 拔河游戏 开始
 */
function startBaheGame() {
	$('#btn-bahe').removeClass('this');
	$('#bhgame-ready').show();
	$('#bhgame-ready').addClass('game-ready');

	setTimeout(function() {
		$('.bahe-people-lr').addClass('ani-bahe-people');
		$('.bahe-people').addClass('bahe-people-move');
		$('#bhgame-ready').hide();
		$('#bh-countdown').show();
		$('#bh-countdown div').addClass('count-down');

		bhGameInterval = setInterval(getBahepoint, 2000);
		//		$('.tip-shake1').show();
		setTimeout(function() {
			endBaheGame();
		}, 30000)
	}, 5000)
}

/**
 * 拔河游戏 结束
 */
function endBaheGame() {
	$('.bahe-people-lr').hide();
	$('.rope').hide();
	gameInfo.gameIsRuning = false;
	clearInterval(bhGameInterval);

	var resultkuang;
	var rwin;

	if(parseFloat($('.t-red-point').attr('data-point')) > parseFloat($('.t-blue-point').attr('data-point'))) {
		$('.bahe-result img').attr('src', pageInfo.path + '/static/resource/img/bahe/rwin.png')
		resultkuang = pageInfo.path + '/static/resource/img/bahe/rwinkuang.png';
		rwin = true;
	} else {
		$('.bahe-result img').attr('src', pageInfo.path + '/static/resource/img/bahe/rwin.png')
		resultkuang = pageInfo.path + '/static/resource/img/bahe/bwinkuang.png';
		rwin = false;
	}

	rank_layer = layer.open({
		type: 1,
		closeBtn: '0',
		title: '',
		skin: 'layui-layer-rim', // 加上边框
		area: ['40%', '70%'], // 宽高
		content: '<div class="rank-result" style="transform: scale(' + pageInfo.scale + ');"><div class="rank-result-place"><img src="' + resultkuang + '"><div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_03.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_14.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_18.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div></div></div></div>',
		// move: '.layui-layer-content',
		shadeClose: 'true',
		resize: 'false',
	});

	//	if(pageInfo.zoom<1||pageInfo.needResize){
	//		layer.style(rank_layer, {
	//			background: 'rgba(255,255,255,0)',
	//			border: 'none',
	//			top:'100px',
	//			left:'400px',
	//			scrollbar:false,
	//			height:'550px'
	//		});
	//	}else{
	//		layer.style(rank_layer, {
	//			background: 'rgba(255,255,255,0)',
	//			border: 'none',
	//			height:'550px'
	//		});
	//	}

	layer.style(rank_layer, {
		background: 'rgba(255,255,255,0)',
		border: 'none',
		height: '550px'
	});

	if(rwin) {
		$('.bahe-result img').attr('src', pageInfo.path + '/static/resource/img/bahe/rwin.png')
		for(var i = 0; i < 3; i++) {
			if(!$('.bahe-head-l li')[i].children[1]) {
				break;
			} //console.log($('.touxiang')[i].children[0].src);
			$('.result-headimg')[i].src = $('.bahe-head-l li')[i].children[1].src;
			$('.result-name')[i].innerHTML = typeof($('.bahe-head-l li')[i].children[1].getAttribute('data-name')) == undefined || $('.bahe-head-l li')[i].children[1].getAttribute('data-name') == null ? 'Player' : cutName($('.bahe-head-l li')[i].children[1].getAttribute('data-name'));
			$('.result-point')[i].innerHTML = parseInt($('.bahe-head-l li')[i].children[1].getAttribute('data-point')) + '分';
		}

	} else {
		$('.bahe-result img').attr('src', pageInfo.path + '/static/resource/img/bahe/bwin.png')
		for(var i = 0; i < 3; i++) {
			//console.log($('.touxiang')[i].children[0].src);
			if(!$('.bahe-head-r li')[i].children[1]) {
				break;
			}
			$('.result-headimg')[i].src = $('.bahe-head-r li')[i].children[1].src;
			$('.result-name')[i].innerHTML = typeof($('.bahe-head-r li')[i].children[1].getAttribute('data-name')) == undefined || $('.bahe-head-r li')[i].children[1].getAttribute('data-name') == null ? 'Player' : cutName($('.bahe-head-r li')[i].children[1].getAttribute('data-name'));
			$('.result-point')[i].innerHTML = parseInt($('.bahe-head-r li')[i].children[1].getAttribute('data-point')) + '分';
		}

	}

	$('.bahe-result').show();

	$.get(pageInfo.postUrl + 'game/endGame', {
		liveId: pageInfo.roomId,
		gameCode: gameInfo.currentGame
	}, function(data) {
		//拔河游戏结束
	})
	//	$('.tip-shake1').hide();
}
/**
 * 拔河游戏 刷新分数
 * @param {Object} 
 */
function restGameBh(redscroe, bluescroe, redHead, blueHead) {
	var c = redscroe - bluescroe;

	$('.t-red-point').html(Math.round(redscroe) + '分');
	$('.t-blue-point').html(Math.round(bluescroe) + '分');
	$('.t-red-point').attr('data-point', redscroe);
	$('.t-blue-point').attr('data-point', bluescroe);

	$('.bahe-head-l li').empty();
	$('.bahe-head-r li').empty();

	for(var i = 0; i < redHead.length; i++) {
		$('.bahe-head-l li')[i].innerHTML = '<span>' + redHead[i].nickname + '</span><img data-point="' + redHead[i].score + '" data-name="' + redHead[i].nickname + '" src="' + redHead[i].headimgurl + '" />';
	}

	for(var j = 0; j < blueHead.length; j++) {
		$('.bahe-head-r li')[j].innerHTML = '<span>' + blueHead[j].nickname + '</span><img data-point="' + blueHead[j].score + '" data-name="' + blueHead[j].nickname + '" src="' + blueHead[j].headimgurl + '" />';
	}

	if(Math.abs(c) < 100) {
		$('.bahe-people-lr').css('left', -c + 'px');
	} else {
		if(c > 0) {
			$('.bahe-people-lr').css('left', '-100px');
		} else {
			$('.bahe-people-lr').css('left', '100px');
		}
	}
}

document.getElementById("dp-move").addEventListener('webkitAnimationEnd', function() {
	//	alert('111');
	this.classList.remove('dp-move');
	setTimeout(function() {
		//		this.classList.remove('dp-move');
		document.getElementById("dp-move").classList.add('dp-move');
	}, 3000)
})

var gameCountTime = null;
var vargameCountTimeOut = null;
var countDownflag = false;

//小游戏倒计时  3MIN
function toogleCountDown() {
	if(countDownflag) {
		countDownflag = false;
		$('#mgamecountdown').hide();
		$('.time-part.minutes')[0].classList.remove('tens');
		$('.time-part.minutes')[1].classList.remove('ones');

		$('.time-part.seconds')[0].classList.remove('tens');
		$('.time-part.seconds')[1].classList.remove('ones');

		$('.time-part.hundredths')[0].classList.remove('tens');
		$('.time-part.hundredths')[1].classList.remove('ones');
		clearTimeout(gameCountTimeOut);
		clearTimeout(gameCountTime);
	} else {
		$('#mgamecountdown').show();
		countDownflag = true;
		$('.time-part.minutes')[1].children[0].style.webkitTransform = 'translateY(-1440px)';
		//延迟20S 后开始倒计时		
		gameCountTimeOut = setTimeout(function() {
			$('.time-part.minutes')[1].children[0].style.webkitTransform = 'translateY(-0px)';
			$('.time-part.minutes')[0].classList.add('tens');
			$('.time-part.minutes')[1].classList.add('ones');
			$('.time-part.seconds')[0].classList.add('tens');
			$('.time-part.seconds')[1].classList.add('ones');
			$('.time-part.hundredths')[0].classList.add('tens');
			$('.time-part.hundredths')[1].classList.add('ones');
			//2分钟后游戏结束
			gameCountTime = setTimeout(function() {
				$('#game-end').click();
			}, 119000)
		}, 5000)
	}
}

//2017-11-23
(function(a) {
	console.log(a);
	var time = 30;
	var gInterval = null;
	var rankInterval = null;

	//重置
	a.init = function() {
		time = 30;
		$('.money-stage .ds').html(time);
		$('.money-stage .sky').removeClass('move');
		$('.money-stage .ship').removeClass('move');
		$('.money-stage .ground').removeClass('move');
		$('.money-stage .man').removeClass('move');
		$('.money-stage .man .p img').removeClass('move');
		$('.money-stage .man .l img').removeClass('move');
		$('.money-stage .man .s img').hide();

		$('.money-stage .goldbox').removeClass('move');
		$('.money-stage .facai').removeClass('move');

		$($('.money-stage .man .p')[0]).hide();
		$($('.money-stage .man .p')[1]).show();
		$('.money-stage .ds').hide();

		$('#money-rank .progress-bar').css('height', '10px');
		$('#money-rank .user').attr('src', pageInfo.path + '/static/resource/img/game/abc_03.png');
		$('#money-rank .username').html('Payer');
		$('#money-rank .userpoint').html('0');

	};
	//开始
	a.start = function() {
		playSpecialbgm(MUSIC_MONEY_2);
		gameInfo.gameIsRuning = true;

		$('.money-stage .tip-box-start').show();
		$('.money-stage .tip-box-start').addClass('game-ready');
		setTimeout(function() {
			$('.money-stage .tip-box-start').hide();
			$('.money-stage .tip-box-start').removeClass('game-ready');
			$('.money-stage .ds').show();
			$('.money-stage .ship').addClass('move');
			$('.money-stage .sky').addClass('move');
			$('.money-stage .ground').addClass('move');
			$('.money-stage .man').addClass('move');
			$('.money-stage .man .p img').addClass('move');
			$('.money-stage .man .l img').addClass('move');
			$('.money-stage .man .s img').show();
			$('.money-stage .goldbox').addClass('move');
			$('.money-stage .facai').addClass('move');

			$('.money-stage .ds').html(time);

			rankInterval = setInterval(a.getPoint, 1500);
			gInterval = setInterval(function() {
				time--;
				$('.money-stage .ds').html(time);

				if(time == 1) {
					$($('.money-stage .man .p')[0]).show();
					$($('.money-stage .man .p')[1]).hide();
				}

				if(time == 0) {
					clearInterval(gInterval);
					clearInterval(rankInterval);
					a.end();
				}
			}, 1000)

		}, 5000)

	};

	//游戏结束
	a.end = function() {
		playSpecialbgm(MUSIC_MONEY_1);

		$('.money-stage .sky').removeClass('move');
		$('.money-stage .ship').removeClass('move');
		$('.money-stage .ground').removeClass('move');
		$('.money-stage .man .l img').removeClass('move');
		$('.money-stage .man .s img').hide();

		$.get(pageInfo.postUrl + 'game/endGameBefore', {
			liveId: pageInfo.roomId,
			gameCode: gameInfo.currentGame
		}, function(data) {
			if(data.code == 200) {
				$.get(pageInfo.postUrl + 'game/endGameAfter', {
					liveId: pageInfo.roomId,
					gameCode: gameInfo.currentGame
				}, function(data) {
					if(data.code == 200) {
						a.refreshRank(data.data);
						rank_layer = layer.open({
							type: 1,
							closeBtn: '0',
							title: '',
							skin: 'layui-layer-rim', // 加上边框
							area: ['40%', '70%'], // 宽高
							content: '<div class="rank-result" style="transform: scale(' + pageInfo.scale + ');"><div class="rank-result-place"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_09.png"><div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_03.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_14.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_18.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div></div></div></div>',
							// move: '.layui-layer-content',
							shadeClose: 'true',
							resize: 'false',
						});

						layer.style(rank_layer, {
							background: 'rgba(255,255,255,0)',
							border: 'none',
							height: '550px'
						});

						for(var i = 0; i < 3; i++) {
							$('.result-headimg')[i].src = $('#money-rank .touxiang')[i].children[1].src;
							$('.result-name')[i].innerHTML = typeof($('#money-rank .username')[i].innerHTML) == undefined || $('#money-rank .username')[i].innerHTML == null ? 'Player' : $('#money-rank .username')[i].innerHTML;
							$('.result-point')[i].innerHTML = $('#money-rank .userpoint')[i].innerHTML;
						}

					}

				})

			}
		});

		gameInfo.gameIsRuning = false;

	};

	a.refreshRank = function(obj) {
		for(var i = 0; i < obj.length; i++) {
			$('#money-rank .username')[i].innerHTML = cutName(obj[i].nickname);
			$('#money-rank .userpoint')[i].innerHTML = Math.round(obj[i].score);
			$('#money-rank .user')[i].src = obj[i].headimgurl;
			//console.log(obj[i].score);
			$('#money-rank .rank-item-b')[i].dataset.point = Math.round(obj[i].score);
			//更新进度条
			$('#money-rank .progress-bar')[i].style.height = ((obj[i].score / 20000) * 350 + 10) + 'px';
		}
	};

	a.getPoint = function(callback) {
		$.get(pageInfo.postUrl + "game/countCashResult", {
			gameCode: gameInfo.currentGame,
			roomId: pageInfo.roomId
		}, function(result) {
			if(result.code == 200) {
				a.refreshRank(result.data);
				if(callback) {
					callback();
				}
			}
		}).error(function() {})
	}

})(window.moneyGame = {});

//2017-12-28
(function(game) {

	var time = 120,
		gInterval = null,
		rankInterval = null,
		aniInterval = null;

	game.init = function() {
		$('.dl-warp').empty();
		$('#huadeng-rank .progress-bar').css('height', '10px');
		$('#huadeng-rank .user').attr('src', pageInfo.path + '/static/resource/img/game/abc_03.png');
		$('#huadeng-rank .username').html('Payer');
		$('#huadeng-rank .userpoint').html('0');
		$('.huadeng-stage .moon').removeClass('move');
		time = 120;
		$('.huadeng-stage .ds').html(game.formatTime(time));

	};

	game.start = function() {

		game.init();
		gameInfo.gameIsRuning = true;
		$('.huadeng-stage .moon').addClass('move');
		game.ani(); //开始动画

		rankInterval = setInterval(game.getPoint, 1500);

	};

	game.getPoint = function() {

		$.get(pageInfo.postUrl + "game/scoreList", {
			gameCode: gameInfo.currentGame,
			liveId: pageInfo.roomId
		}, function(result) {
			if(result.code == 200) {
				game.refresh(result.data);

			}
		}).error(function() {

		})

	};

	game.refresh = function(obj) {
		for(var i = 0; i < obj.length; i++) {
			$('#huadeng-rank .username')[i].innerHTML = cutName(obj[i].nickname);
			$('#huadeng-rank .userpoint')[i].innerHTML = Math.round(obj[i].score);
			$('#huadeng-rank .user')[i].src = obj[i].headimgurl;
			//huadeng.log(obj[i].score);
			$('#huadeng-rank .rank-item-b')[i].dataset.point = Math.round(obj[i].score);
			//更新进度条
			//$('#huadeng-rank .progress-bar')[i].style.height = ((obj[i].score /320) * 350 + 10) + 'px';

			$($('#huadeng-rank .progress-bar')[i]).animate({
				height: ((obj[i].score / 310) * 185 + 10) + 'px'
			}, 500);

		}

	};

	game.end = function() {
		$('#game-end').hide();
		clearInterval(aniInterval);
		clearInterval(rankInterval);
		gameInfo.gameIsRuning = false;
		$.get(pageInfo.postUrl + 'game/endGame', {
			liveId: pageInfo.roomId,
			gameCode: gameInfo.currentGame
		}, function(data) {
			rank_layer = layer.open({
				type: 1,
				closeBtn: '0',
				title: '',
				skin: 'layui-layer-rim', // 加上边框
				area: ['40%', '70%'], // 宽高
				content: '<div class="rank-result" style="transform: scale(' + pageInfo.scale + ');"><div class="rank-result-place"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_09.png"><div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_03.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_14.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_18.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div></div></div></div>',
				// move: '.layui-layer-content',
				shadeClose: 'true',
				resize: 'false',
			});

			layer.style(rank_layer, {
				background: 'rgba(255,255,255,0)',
				border: 'none',
				height: '550px'
			});

			for(var i = 0; i < 3; i++) {
				$('.result-headimg')[i].src = $('#huadeng-rank .touxiang')[i].children[1].src;
				$('.result-name')[i].innerHTML = typeof($('#huadeng-rank .username')[i].innerHTML) == undefined || $('#huadeng-rank .username')[i].innerHTML == null ? 'Player' : $('#huadeng-rank .username')[i].innerHTML;
				$('.result-point')[i].innerHTML = $('#huadeng-rank .userpoint')[i].innerHTML;
			}

		})
	};

	game.random = function(n, m) {
		var random = Math.floor(Math.random() * (m - n)) + n;
		return random;
	};

	game.formatTime = function(result) {
		var h = Math.floor(result / 3600);
		var m = Math.floor((result / 60 % 60));
		var s = Math.floor((result % 60));

		m = m < 10 ? "0" + m : m;
		s = s < 10 ? "0" + s : s;
		return result = m + ":" + s + "";
	};

	game.ani = function() {
		//每隔一秒 产生5个灯笼

		function _removeImg(img, id) {
			console.log(id);
			setTimeout(function() {
				$(img).remove();
			}, 23000);

		}

		aniInterval = setInterval(function() {
			$('.huadeng-stage .ds').html(game.formatTime(time));
			if(time == 0) {
				game.end();
				return;
			} else {
				var imgs = [];
				for(var i = 0; i < 3; i++) {
					var id = 'dl-' + time + '-' + i;
					var img = document.createElement('img');
					img.id = 'dl-' + time + '-' + i;
					img.src = pageInfo.path + 'static/resource/img/huadeng/dl.png';
					img.style.position = 'absolute';
					img.style.bottom = '-121px';
					img.style.left = game.random(700, 1180) + 'px';
					imgs.push(img);
					document.querySelector('.dl-warp').appendChild(img);
					_removeImg(img);
				}

				setTimeout(function() {
					for(var i = 0; i < 3; i++) {
						imgs[i].style.transition = 'ease-out,' + game.random(10, 20) + 's,' + 'infinite';
						imgs[i].style.transform = 'translate(' + game.random(-280, 280) + 'px,-879px) scale(0.6)';
						imgs[i].style.transitionDelay = game.random(0, 3) + 's';

					}
				}, 50);
				time--
			}

		}, 1000);

	}

})(window.huadengGame = {});

//2018-01-05
(function(g) {

	var time = 30,
		gInterval = null,
		rankInterval = null,
		aniInterval = null;

	g.init = function() {
		time = 30;
		$('.shake-stage .buildings').removeClass('move');
		$('.shake-stage .ship').removeClass('move');
		$('.shake-stage .sea').removeClass('move');
		$('.shake-stage .car1').css({
			'webkitTransform': 'translateX(0px)',
			'transition': 'none'
		});
		$('.shake-stage .car2').css({
			'webkitTransform': 'translateX(0px)',
			'transition': 'none'
		});
		$('.shake-stage .car3').css({
			'webkitTransform': 'translateX(0px)',
			'transition': 'none'
		});

		$('.shake-rank .progress-bar').css('height', '10px');
		$('.shake-rank .username').html('Player');
		$('.shake-rank .userpoint').html('0');
		$('#m-shakegame .tip-box-start').removeClass('.game-ready').show();
		$('#m-shakegame .user').attr('src', pageInfo.path + '/static/resource/img/game/abc_03.png');
		$('#m-shakegame .userp img').attr('src', pageInfo.path + '/static/resource/img/game/abc_03.png');
		$('#m-shakegame .userp .namep').html('Player');
	};

	g.refresh = function(obj) {
		for(var i = 0; i < obj.length; i++) {

			if(i < 3) {
				$('#m-shakegame .car' + (i + 1) + ' .userp img')[0].src = obj[i].headimgurl;
				$('#m-shakegame .car' + (i + 1) + ' .userp .namep')[0].innerHTML = obj[i].nickname;
			}

			$('#shake-run-rank .username')[i].innerHTML = cutName(obj[i].nickname);
			$('#shake-run-rank .userpoint')[i].innerHTML = Math.round(obj[i].score);
			$('#shake-run-rank .user')[i].src = obj[i].headimgurl;
			//console.log(obj[i].score);
			$('#shake-run-rank .rank-item-b')[i].dataset.point = Math.round(obj[i].score);
			//更新进度条
			$('#shake-run-rank .progress-bar')[i].style.height = ((obj[i].score / gameInfo.shakeGame.progressTotal) * 150 + 10) + 'px';
			$($('#shake-run-rank .progress-bar')[i])
				.animate({
					height: ((obj[i].score / gameInfo.shakeGame.progressTotal) * 150 + 10) + 'px'
				}, 500);

		}

	};

	g.getPoint = function() {
		$.get(pageInfo.postUrl + "game/shakeResult", {
			gameCode: gameInfo.currentGame,
			roomId: pageInfo.roomId
		}, function(result) {
			if(result.code == 200) {
				g.refresh(result.data);
			}
		}).error(function() {})

	};

	g.start = function() {
		var _this = this;
		$('#m-shakegame .tip-box-start').show().addClass('game-ready');
		$('.btn-menu-big').hide();
		$('.module').removeClass('active');
		$('.module').removeClass('fadeIn');
		$('.module').hide();
		$('.shakegame').addClass('active');
		$('.shakegame').addClass('fadeIn');
		$('.shakegame').show();
		gameInfo.gameIsRuning = true;

		setTimeout(function() {
			var loading = layer.msg('加载中', {
				icon: 16,
				shade: 0.01,
				time: 0
			});
			$('#m-shakegame .tip-box-start').hide().removeClass('game-ready');
			$.get(pageInfo.postUrl + "game/beginGame", {
				gameCode: gameInfo.currentGame,
				liveId: pageInfo.roomId
			}, function(result) {
				layer.close(loading);
				$('#m-shakegame .buildings').addClass('move');
				$('#m-shakegame .ship').addClass('move');
				$('#m-shakegame .sea').addClass('move');

				g.ani();
				$('#m-shakegame .ds').html(time).show();
				rankInterval = setInterval(_this.getPoint, 1500);
				gInterval = setInterval(function() {
					console.log(_this.time);
					time--;
					$('#m-shakegame .ds').html(time)
					if(time == 0) {
						clearInterval(gInterval);
						clearInterval(rankInterval);
						_this.end();
					}

				}, 1000)

				$('.tip-shake').show();
				//gameInfo.shakeGame.getpointInterval = setInterval(getshakepoint, 2000)
			}).error(function() {
				layer.close(loading);
				layer.msg('网络异常,请重试');
			})

		}, 4500)

	};

	g.end = function() {
		gameInfo.gameIsRuning = false;
		$('.tip-shake').hide();

		setTimeout(function() {
			$('#m-shakegame .buildings').removeClass('move');
			$('#m-shakegame .ship').removeClass('move');
			$('#m-shakegame .sea').removeClass('move');

		}, 1500);

		$.get(pageInfo.postUrl + 'game/endGame', {
			liveId: pageInfo.roomId,
			gameCode: gameInfo.currentGame
		}, function(data) {
			//		alert('end');
			rank_layer = layer.open({
				type: 1,
				closeBtn: '0',
				title: '',
				skin: 'layui-layer-rim', //加上边框
				area: ['40%', '70%'], //宽高
				content: '<div class="rank-result" style="transform: scale(' + pageInfo.scale + ');"><div class="rank-result-place"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_09.png"><div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_03.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_14.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div><div class="rank-result-item"><img src="' + pageInfo.path + '/static/resource/img/game/paiming_18.png" class="paiming"> <img src="' + pageInfo.path + '/static/resource/img/game/paiming_32.png" class="rank-head"> <img src="' + pageInfo.path + '/static/resource/img/game/indexheader.png" class="rank-headimg result-headimg"> <span class="rank-name result-name">Player</span> <span class="rank-point result-point">0分</span></div></div></div></div>',
				//			move: '.layui-layer-content',
				shadeClose: 'true',
				resize: 'false',
			});

			layer.style(rank_layer, {
				background: 'rgba(255,255,255,0)',
				border: 'none',
				height: '550px'
			});

			for(var i = 0; i < 3; i++) {
				//console.log($('.touxiang')[i].children[0].src);
				$('.result-headimg')[i].src = $('#shake-run-rank .touxiang')[i].children[1].src;
				$('.result-name')[i].innerHTML = typeof($('#shake-run-rank .username')[i].innerHTML) == undefined || $('#shake-run-rank .username')[i].innerHTML == null ? 'Player' : $('#shake-run-rank .username')[i].innerHTML;
				$('.result-point')[i].innerHTML = $('#shake-run-rank .userpoint')[i].innerHTML;
			}
		})
	};

	function _ani(dom, ani, timefuc, time, done) {
		timefuc = timefuc || 'ease';
		$(dom).css({
			'transition': time + 's' + ' ' + timefuc,
			'webkitTransform': ani
		});
		if(done) {
			setTimeout(function() {
				done();
			}, time * 1000)
		}
	};

	g.carMove = function(dom, ani, timefuc, time, done) {
		timefuc = timefuc || 'ease';
		$(dom).css({
			'transition': time + 's' + ' ' + timefuc,
			'webkitTransform': ani
		});
		if(done) {
			setTimeout(function() {
				done();
			}, time * 1000)
		}
	};

	g.ani = function() {
		var _car1 = document.querySelector('.shake-stage .car1'),
			_car2 = document.querySelector('.shake-stage .car2'),
			_car3 = document.querySelector('.shake-stage .car3');

		_ani(_car1, 'translateX(-600px)', 'ease', 1, function() {
			_ani(_car1, 'translateX(-500px)', 'ease', 2, function() {
				_ani(_car1, 'translateX(-800px)', 'ease', 2, function() {
					_ani(_car1, 'translateX(-700px)', 'ease', 2, function() {
						_ani(_car1, 'translateX(-900px)', 'ease', 2, function() {
							_ani(_car1, 'translateX(-950px)', 'ease', 2, function() {
								_ani(_car1, 'translateX(-800px)', 'ease', 1, function() {
									_ani(_car1, 'translateX(-1200px)', 'ease', 2, function() {
										_ani(_car1, 'translateX(-1100px)', 'ease', 2, function() {
											_ani(_car1, 'translateX(-900px)', 'ease', 2, function() {
												_ani(_car1, 'translateX(-1000px)', 'ease', 2, function() {
													_ani(_car1, 'translateX(-1200px)', 'ease', 2, function() {
														_ani(_car1, 'translateX(-1250px)', 'ease', 2, function() {
															_ani(_car1, 'translateX(-1300px)', 'ease', 2, function() {
																_ani(_car1, 'translateX(-1600px)', 'ease', 2, function() {
																	_ani(_car1, 'translateX(-1250px)', 'ease', 2, function() {
																		_ani(_car1, 'translateX(-3000px)', 'ease', 1, function() {

																		})
																	})
																})
															})
														})
													})
												})
											})
										})
									})
								})
							})
						})
					})
				})
			})
		})

		setTimeout(function() {
			_ani(_car2, 'translateX(-600px)', 'ease', 1, function() {
				_ani(_car2, 'translateX(-500px)', 'ease', 2, function() {
					_ani(_car2, 'translateX(-800px)', 'ease', 2, function() {
						_ani(_car2, 'translateX(-700px)', 'ease', 2, function() {
							_ani(_car2, 'translateX(-900px)', 'ease', 2, function() {
								_ani(_car2, 'translateX(-950px)', 'ease', 2, function() {
									_ani(_car2, 'translateX(-800px)', 'ease', 2, function() {
										_ani(_car2, 'translateX(-1200px)', 'ease', 1, function() {
											_ani(_car2, 'translateX(-1100px)', 'ease', 2, function() {
												_ani(_car2, 'translateX(-900px)', 'ease', 2, function() {
													_ani(_car2, 'translateX(-1000px)', 'ease', 2, function() {
														_ani(_car2, 'translateX(-1200px)', 'ease', 2, function() {
															_ani(_car2, 'translateX(-1250px)', 'ease', 2, function() {
																_ani(_car2, 'translateX(-1300px)', 'ease', 2, function() {
																	_ani(_car2, 'translateX(-1600px)', 'ease', 2, function() {
																		_ani(_car2, 'translateX(-1250px)', 'ease', 2, function() {
																			_ani(_car2, 'translateX(-3000px)', 'ease', 1, function() {

																			})
																		})
																	})
																})
															})
														})
													})
												})
											})
										})
									})
								})
							})
						})
					})
				})
			})
		}, 600);

		setTimeout(function() {
			_ani(_car3, 'translateX(-600px)', 'ease', 1, function() {
				_ani(_car3, 'translateX(-500px)', 'ease', 2, function() {
					_ani(_car3, 'translateX(-800px)', 'ease', 2, function() {
						_ani(_car3, 'translateX(-700px)', 'ease', 2, function() {
							_ani(_car3, 'translateX(-900px)', 'ease', 2, function() {
								_ani(_car3, 'translateX(-950px)', 'ease', 2, function() {
									_ani(_car3, 'translateX(-800px)', 'ease', 1, function() {
										_ani(_car3, 'translateX(-1200px)', 'ease', 2, function() {
											_ani(_car3, 'translateX(-1100px)', 'ease', 2, function() {
												_ani(_car3, 'translateX(-900px)', 'ease', 2, function() {
													_ani(_car3, 'translateX(-1000px)', 'ease', 2, function() {
														_ani(_car3, 'translateX(-1200px)', 'ease', 2, function() {
															_ani(_car3, 'translateX(-1250px)', 'ease', 2, function() {
																_ani(_car3, 'translateX(-1300px)', 'ease', 2, function() {
																	_ani(_car3, 'translateX(-1600px)', 'ease', 2, function() {
																		_ani(_car3, 'translateX(-1250px)', 'ease', 2, function() {
																			_ani(_car3, 'translateX(-3000px)', 'ease', 1, function() {

																			})
																		})
																	})
																})
															})
														})
													})
												})
											})
										})
									})
								})
							})
						})
					})
				})
			})

		}, 1000);

	}
})(window.shakeGame = {});

//投票
(function(app) {

	var color = ['#ff8228', '#ffd24d'];

	var voteInterval = null;

	var tpl = '<li class="vote-item">' +
		'<div class="vote-bar-warp">' +
		'<div class="b">' +
		'<div class="pnum">{{votenum}}</div>' +
		'<div class="bar" data-id={{id}} data-count={{count}} style="background-color:{{color}};"></div>' +
		'</div>' +
		'</div>' +
		'<div class="vote-name">{{title}}</div>' +
		'<div class="vote-desc">{{desc}}</div>' +
		'</li>';

	var vote = app.vote = {
		init: function(obj) {
			//alert('init');
			$.get(pageInfo.postUrl + "game/initVote", {
				gameCode: 'vote',
				liveId: pageInfo.roomId
			}, function(result) {

				if(result.code == 200) {

					var str = '';
					var obj = result.vote_list;
					for(var i = 0; i < obj.length; i++) {
						str += tpl.replace('{{votenum}}', obj[i].count + '票').replace('{{title}}', obj[i].title).replace('{{desc}}', obj[i].description ? obj[i].description : '').replace('{{id}}', obj[i].id).replace('{{color}}', color[i % 2]).replace('{{count}}', obj[i].count);
					}
					document.querySelector('.vote-warp ul').innerHTML = str;
				}

				vote.resizeBar();

				$('#game-start').show();
				gameInfo.currentGame = 'vote';
			}).error(function(e) {
				layer.msg('投票开启失败,请重置')
			})

		},

		refresh: function(data) {
			//TODO
			for(var i = 0; i < data.length; i++) {
				$('.vote-bar-warp .bar').each(function(index, item) {
					if(item.getAttribute('data-id') == data[i].vote_id) {
						item.dataset.count = data[i].score;
						item.parentNode.children[0].innerHTML = data[i].score + '票';
						return false
					}
				})
			}
			vote.resizeBar();
		},

		sortBar: function() {
			var list = document.querySelector(".vote-warp ul");
			var listArr = document.getElementsByClassName("vote-item");
			for(var i = 0; i < listArr.length; i++) {
				for(var j = 0; j < listArr.length - i - 1; j++) {

					if(parseInt(listArr[j].children[0].children[0].children[1].getAttribute('data-count')) < parseInt(listArr[j + 1].children[0].children[0].children[1].getAttribute('data-count'))) {
						list.insertBefore(listArr[j + 1], listArr[j]);
					}
				}
				listArr[i].children[0].children[0].children[1].style.backgroundColor = color[i % 2];
			}
		},

		resizeBar: function() {
			var _highest = 0;
			$('.vote-warp .bar').each(function(index, item) {
				var t = parseInt(item.getAttribute('data-count'))
				if(t > _highest) {
					_highest = t;
				}
			})
			//MAX 500   计算倍率
			if(_highest == 0) {
				//				$('.vote-warp .bar').each(function(index, item) {
				//					item.parentElement.children[0].innerHTML = '0票';
				//					snabbt(item, {
				//						fromHeight: item.offsetHeight,
				//						height:0,
				//						duration: 500,
				//						easing: 'ease',
				//					})
				//
				//				})

				return;
			}
			var resize = _highest < 500 ? 500 / ((Math.floor(_highest / 100) + 1) * 100) : 1;
			//计算出最高的高度
			var _highestPX = _highest % 500 == 0 ? 500 : _highest % 500;

			$('.vote-warp .bar').each(function(index, item) {
				var t = parseInt(item.getAttribute('data-count'));
				item.parentElement.children[0].innerHTML = t + '票';

				t = (t / _highest) * _highestPX;

				snabbt(item, {
					fromHeight: item.offsetHeight,
					height: _highest < 500 ? resize * t : t,
					duration: 500,
					easing: 'ease',
				})
			});
			vote.sortBar();
		},

		getVote: function() {
			voteInterval = setInterval(function() {
				$.get(pageInfo.postUrl + "game/VoteResult", {
					gameCode: gameInfo.currentGame,
					roomId: pageInfo.roomId
				}, function(result) {
					if(result.code == 200) {
						vote.refresh(result.data);
					}
				}).error(function() {})

			}, 1500);
		},

		start: function() {
			$('.btn-menu-big').hide();
			$('#game-end').show();
			this.getVote();
			gameInfo.gameIsRuning = true;

		},

		stop: function() {
			var loading = layer.msg('加载中', {
				icon: 16,
				shade: 0.01,
				time: 0
			});

			$.get(pageInfo.postUrl + "game/endVote", {
				gameCode: gameInfo.currentGame,
				liveId: pageInfo.roomId
			}, function(result) {
				layer.close(loading);
				clearInterval(voteInterval);
				$('.btn-menu-big').hide();
				//$('#game-start').show();
				gameInfo.gameIsRuning = false;

			}).error(function() {
				layer.close(loading);
				layer.msg('投票结束失败，请重试')
			})

		},

		clear: function() {
			alert('clear!');

		}
	}
})(window.hdAPP || (window.hdAPP = {}));

//员工风采
(function(app) {

	var swiper = null,
		fcInterval = null;

	var tpl = '<div class="swiper-slide">' +
		//'<div class="fc-img"><img src="{{img}}" style="height:100%;width:auto" /></div>' +
		'<div class="fc-img" style="background-image:url({{img}})"></div>' +
		'<div class="fc-info">' +
		'<p>{{name}}&nbsp;{{job}}</p>' +
		'<p>{{desc}}</p>' +
		'</div>' +
		'</div>';

	function render(obj) {
		var str = '';
		for(var i = 0; i < obj.length; i++) {
			str += tpl.replace('{{name}}', obj[i].staff_name).replace('{{job}}', obj[i].staff_positional).replace('{{desc}}', obj[i].staff_desc).replace('{{img}}', pageInfo.cdnPath + obj[i].img_url);
		}
		document.querySelector('.swiper-wrapper').innerHTML = str;
	}

	var photo = app.photo = {
		init: function(obj) {

			if(!obj) {
				return;
			}

			if(swiper) {
				return;
			}

			render(obj);

			swiper = new Swiper('.swiper-container', {
				on: {
					slideChange: function() {
						_ani();
						console.log((this.activeIndex));
						console.log((this));
						if(this.activeIndex + 1 == $('.swiper-slide').length) {
							$('#fc-pause').click();
							if($('#btn-fengcai').hasClass('this')) {
								setTimeout(function(e) {
									$('#homepage').click();
								}, 5000)
							}
						}
					},
				},
				slidesPerView: 5,
				centeredSlides: true,
				spaceBetween: 140,
				grabCursor: true,
				pagination: {
					el: '.swiper-pagination',
					clickable: true,
				},
			});

			document.getElementById("fc-pre").addEventListener('click', function(e) {
				swiper.slidePrev();

			})

			document.getElementById("fc-next").addEventListener('click', function(e) {
				swiper.slideNext();
			})

			document.getElementById("fc-play").addEventListener('click', function(e) {
				document.getElementById("fc-pause").style.display = 'inline-block';
				this.style.display = 'none';
				document.getElementById("fc-pre").classList.add('disabled');
				document.getElementById("fc-next").classList.add('disabled');
				fcInterval = setInterval(function() {
					swiper.slideNext()
				}, 4500);
			})

			document.getElementById("fc-pause").addEventListener('click', function(e) {
				document.getElementById("fc-play").style.display = 'inline-block';
				this.style.display = 'none';
				document.getElementById("fc-pre").classList.remove('disabled');
				document.getElementById("fc-next").classList.remove('disabled');
				clearInterval(fcInterval);
				fcInterval = null;
			})

		},

		autoPlay: function() {
			clearInterval(fcInterval);
			fcInterval = null;
			swiper.slideTo(0, 1000, false);
			document.getElementById("fc-pause").style.display = 'inline-block';
			document.getElementById("fc-play").style.display = 'none';
			document.getElementById("fc-pre").classList.add('disabled');
			document.getElementById("fc-next").classList.add('disabled')
			fcInterval = setInterval(function() {
				swiper.slideNext()
			}, 4500);
		},

		clear: function() {
			alert('clear!');
		}
	}

	function _ani() {
		snabbt(document.querySelector(".fc-line"), {
			fromOpacity: 1,
			opacity: 1,
			fromPosition: [0, 0, 0],
			position: [0, 520, 0],
			duration: 1000,
			easing: 'ease',
			delay: 500
		}).snabbt({
			fromOpacity: 1,
			opacity: 0,
			duration: 500
		});

		snabbt(document.querySelector(".fc-line2"), {
			fromScale: [1, 0.3],
			scale: [1, 0.3],
			fromPosition: [0, 0, 0],
			position: [-1000, 0, 0],
			duration: 500,
			easing: 'linear',
		}).snabbt({
			fromScale: [1, 0.3],
			scale: [1.5, 1.5],
			fromPosition: [-1000, 0, 0],
			position: [-2500, 0, 0],
			duration: 500,
			easing: 'linear',
		}).snabbt({
			fromScale: [1.5, 1.5],
			scale: [0.3, 0.3],
			fromPosition: [-2500, 0, 0],
			position: [-3300, 0, 0],
			duration: 500,
			easing: 'linear',
		});

		snabbt(document.querySelector(".fc-kuang"), {
			fromScale: [1, 1],
			scale: [0.9, 0.9],
			duration: 200,
			easing: 'ease',
			delay: 1000
		}).snabbt({
			fromScale: [0.9, 0.9],
			scale: [1, 1],
			duration: 200,
			easing: 'ease',
		}).snabbt({
			fromScale: [1, 1],
			scale: [0.9, 0.9],
			duration: 200,
			easing: 'ease',
		}).snabbt({
			fromScale: [0.9, 0.9],
			scale: [1, 1],
			duration: 200,
			easing: 'ease',
		})

	}

})(window.hdAPP || (window.hdAPP = {}));

(function(app) {
	//礼物模块 统一	

	//思域超跑
	var ferrari = {
		time: 10000, //礼物动画效果时间
		ui: {
			warp: document.querySelector(".gift-ferrari"),
			car: document.querySelector(".ferrari"),
			light: document.querySelector(".ferrari-light"),
			halo: document.querySelector(".ferrari-halo"),
			stage: document.querySelector(".gift-ferrari"),
		},

		start: function() {
			var that = this;
			that.ui.warp.style.display = 'block';
			console.log(that.ui.car);
			snabbt(that.ui.car, {
				fromOpacity: 1,
				opacity: 1,
				fromScale: [0.8, 0.8],
				scale: [1.5, 1.5],
				fromPosition: [0, 0, 0],
				position: [1200, 600, 0],
				duration: 1000,
				easing: 'ease',
				allDone: function() {
					console.log('car move');
					that.ui.car.classList.add('move');
				}
			});

			snabbt(that.ui.light, {
				fromPosition: [0, 0, 0],
				position: [1200, 530, 0],
				duration: 500,
				easing: 'ease',
				delay: 1000
			}).snabbt({
				position: [1150, 500, 0],
				duration: 200,
			}).snabbt({
				position: [1200, 530, 0],
				duration: 200,
			}).snabbt({
				position: [1150, 500, 0],
				duration: 200,
			}).snabbt({
				position: [1200, 530, 0],
				duration: 200,
			}).snabbt({
				position: [1150, 500, 0],
				duration: 200,
			}).snabbt({
				position: [1200, 530, 0],
				duration: 200,
			}).snabbt({
				position: [2850, 830, 0],
				duration: 200,
			});

			snabbt(that.ui.halo, {
				fromOpacity: 0,
				opacity: 1,
				fromScale: [0.8, 0.8],
				scale: [1.5, 1.5],
				delay: 2900,
				duration: 500,
			}).snabbt({
				fromScale: [1.5, 1.5],
				scale: [0.8, 0.8],
				duration: 500,
			}).snabbt({
				fromScale: [0.8, 0.8],
				scale: [1.5, 1.5],
				duration: 500,
			}).snabbt({
				fromScale: [1.5, 1.5],
				scale: [0.8, 0.8],
				duration: 500,
			}).snabbt({
				fromScale: [0.8, 0.8],
				scale: [1.5, 1.5],
				duration: 500,
			}).snabbt({
				fromScale: [1.5, 1.5],
				scale: [0.8, 0.8],
				duration: 500,
			}).snabbt({
				fromOpacity: 0,
				opacity: 0,
				fromScale: [0.8, 0.8],
				scale: [1.5, 1.5],
				duration: 500,
				complete: function() {
					console.log('car stop');
					that.ui.car.classList.remove('move');
					snabbt(that.ui.car, {
						fromOpacity: 1,
						opacity: 0,
						fromScale: [1.5, 1.5],
						scale: [0.8, 0.8],
						fromPosition: [1200, 600, 0],
						position: [2400, 600, 0],
						duration: 1000,
						easing: 'ease',
						complete: function() {
							that.ui.warp.style.display = 'none';
						}
					});
				}
			})
		}
	}

	var c919 = {
		ui: {
			warp: document.querySelector(".gift-cplane"),
			yanhua1: document.querySelector(".yanhua1"),
			yanhua2: document.querySelector(".yanhua2"),
			yanhua3: document.querySelector(".yanhua3"),
			yanhua4: document.querySelector(".yanhua4"),
			plane: document.querySelector(".cplane"),
			cloud: document.querySelector(".ccloud")
		},

		start: function() {
			var that = this;
			that.ui.warp.style.display = 'block';
			snabbt(that.ui.plane, {
				fromScale: [0.6, 0.6],
				scale: [1, 1],
				fromPosition: [0, 0, 0],
				position: [-900, 0, 0],
				duration: 1000,
			}).snabbt({
				fromPosition: [-900, 0, 0],
				position: [-1400, 0, 0],
				duration: 5000,
				easing: 'ease',
			}).snabbt({
				fromPosition: [-1400, 0, 0],
				position: [-2800, 0, 0],
				duration: 1500,
				easing: 'ease',
				complete: function() {
					that.ui.warp.style.display = 'none';
				}
			});

			snabbt(that.ui.cloud, {
				fromPosition: [0, 0, 0],
				position: [1400, 0, 0],
				duration: 1000,
			}).snabbt({
				fromPosition: [1400, 0, 0],
				position: [1800, 0, 0],
				duration: 5000,
				easing: 'ease',
			}).snabbt({
				fromPosition: [1800, 0, 0],
				position: [3500, 0, 0],
				duration: 1500,
				easing: 'ease',
			});

			snabbt(that.ui.yanhua1, {
				fromOpacity: 0,
				opacity: 1,
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
				delay: '2000'
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromOpacity: 1,
				opacity: 0,
				duration: 700,
				easing: 'easeOut',
			});

			snabbt(that.ui.yanhua2, {
				fromOpacity: 0,
				opacity: 1,
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
				delay: '2500'
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromOpacity: 1,
				opacity: 0,
				duration: 700,
				easing: 'easeOut',
			})

			snabbt(that.ui.yanhua3, {
				fromOpacity: 0,
				opacity: 1,
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
				delay: '1800'
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromOpacity: 1,
				opacity: 0,
				duration: 700,
				easing: 'easeOut',
			})

			snabbt(that.ui.yanhua4, {
				fromOpacity: 0,
				opacity: 1,
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
				delay: '2800'
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromScale: [0, 0],
				scale: [1.2, 1.2],
				duration: 700,
				easing: 'easeOut',
			}).snabbt({
				fromOpacity: 1,
				opacity: 0,
				duration: 700,
				easing: 'easeOut',
			})

		}

	};

	var gift = app.gift = {
		ferrari: ferrari,
		c919: c919
	};

})(window.hdAPP || (window.hdAPP = {}));