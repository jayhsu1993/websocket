<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String liveId = session.getAttribute("roomId").toString();
	String picHost = session.getAttribute("host").toString();
	String qrcodeUrl = session.getAttribute("QRCodeUrl").toString();
	String chatUrl = session.getAttribute("chatUrl").toString();
	String logo = session.getAttribute("game_logo_pic").toString();
	String cdnPath ="http://ocar2d7vc.bkt.clouddn.com/";
	String sign = session.getAttribute("game_sign_content").toString();
	request.setAttribute("liveId",liveId); 
%>

<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8" />
		<link href='https://fonts.googleapis.com/css?family=Aldrich' rel='stylesheet' type='text/css'>
		<link rel="stylesheet" type="text/css" href="<%=path%>/static/resource/businesscss/swiper.min.css" />
		<link rel="stylesheet" type="text/css" href="<%=path%>/static/resource/businesscss/main.css?20180209" />
		<%-- <link rel="stylesheet" type="text/css" href="<%=path%>/static/resource/css/index20180103.css" /> --%>
		<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
		<script type="text/javascript">
			var gameHost = "<%= basePath%>";
			var pageInfo = {};
			pageInfo.path = "<%=basePath%>";
			pageInfo.roomId = "<%=liveId%>";
			pageInfo.picHost = "<%=picHost%>";
			pageInfo.postUrl = gameHost;
			pageInfo.qrcodeUrl = "<%=qrcodeUrl%>";
			pageInfo.picHost = "";
			pageInfo.signSet = "<%=sign%>";
			pageInfo.needResize = false;
			pageInfo.cdnPath = "http://ocar2d7vc.bkt.clouddn.com/";
			/*聊天服务器*/
			pageInfo.chatHost = "<%=chatUrl%>";
			/*缩放系数   1280*720 为基础*/
			pageInfo.zoom = window.innerWidth / 1280 > window.innerHeight / 720 ? window.innerHeight / 720 : window.innerWidth / 1280
			/*缩放系数   1920*1080 为基础*/
			pageInfo.zooma = window.innerWidth / 1920 > window.innerHeight / 1080 ? window.innerHeight / 1080 : window.innerWidth / 1920

			console.log(pageInfo.qrcodeUrl);

			function errorImg(obj) {
				var index = Math.round(Math.random() * 5 + 1);
				obj.src = pageInfo.path + '/static/resource/img/headimg/head' + index + '.png';
			}
		</script>
		<style type="text/css">
			.cw-chat-item {
				color: #20e8ff
			}
		</style>

	</head>

	<body style="overflow: hidden;background-color:#000">

		<div id="hdApp" style="height:1080px;width:1920px;transform-origin: 0% 0%;position: relative;overflow:hidden">
			<!--头部 800-->
			<div class="header">
				<div class="fright people">
					签到人数:<span id="current-people">0</span>
				</div>

				<div class="logo fright" style="padding-right: 35px;">
					<img src="<%=cdnPath%><%=logo%>" alt="" />
				</div>
			</div>

			<div class="screen-gift-warp">
				<!--抽奖通知-->
				<div class="red-warp">
					<div class="red-notice" style="opacity: 0;">
						<img src="<%=path%>/static/resource/img/zhongjiang.png" class="red-png" />
						<div class="red-name"></div>
						<div class="red-content"></div>
					</div>
				</div>

				<div style="position: absolute;width: 100%;top: 80px;pointer-events:none;">
					<div id="animad" style="z-index: 900;">
					</div>
				</div>
				<!--礼物-->
				<div class="gift" id="gift">
					<img src="" alt="" />
					<%-- <img src="<%=path%>/static/resource/img/gift/dance.gif" style="height: 100%;" /> --%>
				</div>

				<div class="fullscreen-gift gift-ship" style="opacity: 0;display:none;" id="shipGift">
					<div style="position: relative;width: 100%;height: 100%;">
						<!--sea-->
						<div style="" class="gitf-ship-seamove"></div>
						<!--Dolphin-->
						<div class="gift-dp" style=""></div>

						<!--ship gitf-ship-move -->
						<div class="gitf-ship-s">
							<div class="ani-ship-t"><img class="ani-ship-m" src="<%=path%>/static/resource/img/gift/ship1.png" /></div>
						</div>
					</div>
				</div>

				<div class="fullscreen-gift gift-huajia">
					<div class="huajia-heart"><img src="<%=path%>/static/resource/img/huajia/huajiaheart.png" /></div>
					<div class="huajia-ground"><img src="<%=path%>/static/resource/img/huajia/huajiaground1.png" /></div>
					<div class="huajia-man"><img src="<%=path%>/static/resource/img/huajia/huajiaman1.png" /></div>
				</div>

				<div class="fullscreen-gift gift-sansheng">
					<div class="gift-sansheng-warp">
						<div class="tree"><img src="<%=path%>/static/resource/img/san/tree.png" /></div>
						<div class="san-people"><img src="<%=path%>/static/resource/img/san/people.png" /></div>
						<div class="text"><img src="<%=path%>/static/resource/img/san/text.png" /></div>
						<div class="flower"></div>
					</div>
				</div>

				<div class="fullscreen-gift gift-plane">
					<div class="ground"><img src="<%=path%>/static/resource/img/gift/plane/planeground.png" /></div>
					<div class="plane">
						<div class="wings"><img src="<%=path%>/static/resource/img/gift/plane/wings.png" class="move" /></div>
						<div class="plane-body"><img src="<%=path%>/static/resource/img/gift/plane/plane.png" /></div>
					</div>
				</div>

				<div class="fullscreen-gift gift-ferrari">
					<div class="ferrari-halo"></div>
					<div class="ferrari-light"></div>
					<div class="ferrari"></div>
				</div>

				<div class="fullscreen-gift gift-cplane">
					<div class="yanhua1"></div>
					<div class="yanhua2"></div>
					<div class="yanhua3"></div>
					<div class="yanhua4"></div>
					<div class="cplane"></div>
					<div class="ccloud"></div>
				</div>

			</div>

			<!--头部 背景图片 轮播-->
			<div class="slider" id='sliderWarp' style="background-image: url(<%=path%>/static/resource/img/back/qybg.png);background-size:100%;">
			</div>
			<!--倒计时<-->
			<div style="position: absolute;top: 100px;left: 685px;z-index: 20000000; display:none" id="mgamecountdown">
				<div class='countdowntxt'>倒计时</div>
				<div class='wrappercount'>
					<div class='time-part-wrapper'>
						<div class='time-part minutes'>
							<!--tens-->
							<div class='digit-wrapper'>
								<span class='digit'>0</span>
								<span class='digit'>5</span>
								<span class='digit'>4</span>
								<span class='digit'>3</span>
								<span class='digit'>2</span>
								<span class='digit'>1</span>
								<span class='digit'>0</span>
							</div>
						</div>
						<div class='time-part minutes'>
							<!--ones-->
							<div class='digit-wrapper'>
								<span class='digit'>0</span>
								<span class='digit'>9</span>
								<span class='digit'>8</span>
								<span class='digit'>7</span>
								<span class='digit'>6</span>
								<span class='digit'>5</span>
								<span class='digit'>4</span>
								<span class='digit'>3</span>
								<span class='digit'>2</span>
								<span class='digit'>1</span>
								<span class='digit'>0</span>
							</div>
						</div>
					</div>
					<div class='time-part-wrapper'>
						<div class='time-part seconds'>
							<!--tens-->
							<div class='digit-wrapper'>
								<span class='digit'>0</span>
								<span class='digit'>5</span>
								<span class='digit'>4</span>
								<span class='digit'>3</span>
								<span class='digit'>2</span>
								<span class='digit'>1</span>
								<span class='digit'>0</span>
							</div>
						</div>
						<div class='time-part seconds'>
							<!--ones-->
							<div class='digit-wrapper'>
								<span class='digit'>0</span>
								<span class='digit'>9</span>
								<span class='digit'>8</span>
								<span class='digit'>7</span>
								<span class='digit'>6</span>
								<span class='digit'>5</span>
								<span class='digit'>4</span>
								<span class='digit'>3</span>
								<span class='digit'>2</span>
								<span class='digit'>1</span>
								<span class='digit'>0</span>
							</div>
						</div>
					</div>
					<div class='time-part-wrapper'>
						<div class='time-part hundredths '>
							<!--tens-->
							<div class='digit-wrapper'>
								<span class='digit'>0</span>
								<span class='digit'>9</span>
								<span class='digit'>8</span>
								<span class='digit'>7</span>
								<span class='digit'>6</span>
								<span class='digit'>5</span>
								<span class='digit'>4</span>
								<span class='digit'>3</span>
								<span class='digit'>2</span>
								<span class='digit'>1</span>
								<span class='digit'>0</span>
							</div>
						</div>
						<div class='time-part hundredths '>
							<!--ones-->
							<div class='digit-wrapper'>
								<span class='digit'>0</span>
								<span class='digit'>9</span>
								<span class='digit'>8</span>
								<span class='digit'>7</span>
								<span class='digit'>6</span>
								<span class='digit'>5</span>
								<span class='digit'>4</span>
								<span class='digit'>3</span>
								<span class='digit'>2</span>
								<span class='digit'>1</span>
								<span class='digit'>0</span>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="module 3dsign" id="m-3dsign" style="background-color: rgba(0,0,0,0.35);overflow: hidden">
				<div class="signin3D">
					<div id="surface" class="root">
					</div>
					<div class="bigCard" id="bigCard" style="width: 467px; height: 588px;" style="background-image: url(" <%=path%>/static/resource/img/sign/big_box_bg.png")">
						<img src="">
						<span></br></span>
					</div>
				</div>
			</div>

			<!--摇一摇游戏 800-->
			<div class="module shakegame" id="m-shakegame" style="display: none;background-color:rgb(248,217,156);">
				<div class="content" style="background-color:rgb(248,217,156);">
					<!--Loading层-->
					<div class="tip-box-loading" id="game-load" style="display: none;">Loading...</div>
					<div class="tip-shake"><img src="<%=path%>/static/resource/img/tip-shake2.png" /></div>

					<!--游戏开始倒计时-->
					<div class="tip-box-start" style="display: none;" id="game-ready"></div>

					<!--背景台-->
					<div class="stage shake-stage">
						<img class="sun sunswing" src="<%=path%>/static/resource/img/run1/SUN.png" />

						<!--<img class="back move2" src="http://ocar2d7vc.bkt.clouddn.com/hidong-game/img/game/buildings.png" />
						<img class="front move1" src="http://ocar2d7vc.bkt.clouddn.com/hidong-game/img/game/sea.png" id="img1" />-->

						<img src="<%=path%>/static/resource/img/run1/buildings.png" class="buildings" alt="">
						<img src="<%=path%>/static/resource/img/run1/SHIP.png" alt="" class="ship">
						<img src="<%=path%>/static/resource/img/run1/sea.png" alt="" class="sea">

						<div class="ds" style="z-index: 160;">30"</div>

						<div class="car3">
							<div class="userp">
								<img src="<%=path%>/static/resource/img/headimg/head1.png" class="headp" alt="">
								<span class="namep">Player</span>
							</div>
							<div class="item">
								<img src="<%=path%>/static/resource/img/run1/YECAR.png" alt="" class="move">
							</div>
						</div>

						<div class="car1">
							<div class="userp">
								<img src="<%=path%>/static/resource/img/headimg/head1.png" class="headp" alt="">
								<span class="namep">Player</span>
							</div>
							<div class="item">
								<img src="<%=path%>/static/resource/img/run1/blueCAR.png" alt="" class="move">
							</div>
						</div>
						<div class="car2">
							<div class="userp">
								<img src="<%=path%>/static/resource/img/headimg/head1.png" class="headp" alt="">
								<span class="namep">Player</span>
							</div>

							<div class="item">
								<img src="<%=path%>/static/resource/img/run1/REDCAR.png" alt="" class="move">
							</div>
						</div>
					</div>

					<!--	<div src="<%=path%>/static/resource/img/man/WIFE 01.png" class="wife hinge" />-->

					<!--<div class="kiss" style="display: none;"></div>

					<div class="wife hinge"></div>

					<div class="husband-warp" id="man-run">
						<div class="husband husband-run  m"></div>
					</div>-->

					<!--排名-->

					<div style="position: absolute; bottom: 0; left: 0; right: 0; margin: 0 auto;">
						<div class="rank shake-rank" style="position: relative; display: block;">
							<ul class="rank-warp" style="position: relative; bottom: 80px; z-index: 800;" id="shake-run-rank">
								<li>
									<div class="rank-item-b">
										<!--名字-->
										<div class="username">Player</div>
										<!--分数-->
										<div class="userpoint">0</div>
										<!--头像-->
										<div class="touxiang ani_flipInX">
											<img src="<%=path%>/static/resource/img/game/qwed_10.png" /><img class="user" src="<%=path%>/static/resource/img/game/abc_03.png" />
										</div>
										<!--progress-->
										<div class="progress-bar pro"></div>
										<!--rank排名-->
										<div class="paiming-b">
											<img src="<%=path%>/static/resource/img/game/qwed_15.png" /><span class="mingci">1</span>
										</div>
									</div>
								</li>
								<li>
									<div class="rank-item-b">
										<!--名字<-->
										<div class="username">Player</div>
										<!--分数-->
										<div class="userpoint">0</div>
										<!--头像-->
										<div class="touxiang ani_flipInX ani_delay1">
											<img src="<%=path%>/static/resource/img/game/qwed_10.png" /><img class="user" src="<%=path%>/static/resource/img/game/abc_03.png" />
										</div>
										<!--progress-->
										<div class="progress-bar pro"></div>
										<!--rank排名-->
										<div class="paiming-b">
											<img src="<%=path%>/static/resource/img/game/qwed_15.png" /><span class="mingci">2</span>
										</div>
									</div>
								</li>
								<li>
									<div class="rank-item-b">
										<!--名字<-->
										<div class="username">Player</div>
										<!--分数-->
										<div class="userpoint">0</div>
										<!--头像-->
										<div class="touxiang ani_flipInX ani_delay2">
											<img src="<%=path%>/static/resource/img/game/qwed_10.png" /><img class="user" src="<%=path%>/static/resource/img/game/abc_03.png" />
										</div>
										<!--progress-->
										<div class="progress-bar pro"></div>
										<!--rank排名-->
										<div class="paiming-b">
											<img src="<%=path%>/static/resource/img/game/qwed_15.png" /><span class="mingci">3</span>
										</div>
									</div>
								</li>
								<li>
									<div class="rank-item-b">
										<!--名字<-->
										<div class="username">Player</div>
										<!--分数-->
										<div class="userpoint">0</div>
										<!--头像-->
										<div class="touxiang ani_flipInX ani_delay3">
											<img src="<%=path%>/static/resource/img/game/qwed_10.png" /><img class="user" src="<%=path%>/static/resource/img/game/abc_03.png" />
										</div>
										<!--progress-->
										<div class="progress-bar pro"></div>
										<!--rank排名-->
										<div class="paiming-b">
											<img src="<%=path%>/static/resource/img/game/qwed_15.png" /><span class="mingci">4</span>
										</div>
									</div>
								</li>
								<li>
									<div class="rank-item-b">
										<!--名字<-->
										<div class="username">player</div>
										<!--分数-->
										<div class="userpoint">0</div>
										<!--头像-->
										<div class="touxiang ani_flipInX ani_delay4">
											<img src="<%=path%>/static/resource/img/game/qwed_10.png" /><img class="user" src="<%=path%>/static/resource/img/game/abc_03.png" />
										</div>
										<!--progress-->
										<div class="progress-bar pro"></div>
										<!--rank排名-->
										<div class="paiming-b">
											<img src="<%=path%>/static/resource/img/game/qwed_15.png" /><span class="mingci">5</span>
										</div>
									</div>
								</li>
								<li>
									<div class="rank-item-b">
										<!--名字<-->
										<div class="username">player</div>
										<!--分数-->
										<div class="userpoint">0</div>
										<!--头像-->
										<div class="touxiang ani_flipInX ani_delay5">
											<img src="<%=path%>/static/resource/img/game/qwed_10.png" /><img class="user" src="<%=path%>/static/resource/img/game/abc_03.png" />
										</div>
										<!--progress-->
										<div class="progress-bar pro"></div>
										<!--rank排名-->
										<div class="paiming-b">
											<img src="<%=path%>/static/resource/img/game/qwed_15.png" /><span class="mingci">6</span>
										</div>
									</div>
								</li>
								<li>
									<div class="rank-item-b">
										<!--名字<-->
										<div class="username">Player</div>
										<!--分数-->
										<div class="userpoint">0</div>
										<!--头像-->
										<div class="touxiang ani_flipInX ani_delay6">
											<img src="<%=path%>/static/resource/img/game/qwed_10.png" /><img class="user" src="<%=path%>/static/resource/img/game/abc_03.png" />
										</div>
										<!--progress-->
										<div class="progress-bar pro"></div>
										<!--rank排名-->
										<div class="paiming-b">
											<img src="<%=path%>/static/resource/img/game/qwed_15.png" /><span class="mingci">7</span>
										</div>
									</div>
								</li>
								<li>
									<div class="rank-item-b">
										<!--名字<-->
										<div class="username">Player</div>
										<!--分数-->
										<div class="userpoint">0</div>
										<!--头像-->
										<div class="touxiang ani_flipInX ani_delay7">
											<img src="<%=path%>/static/resource/img/game/qwed_10.png" /><img class="user" src="<%=path%>/static/resource/img/game/abc_03.png" />
										</div>
										<!--progress-->
										<div class="progress-bar pro"></div>
										<!--rank排名-->
										<div class="paiming-b">
											<img src="<%=path%>/static/resource/img/game/qwed_15.png" /><span class="mingci">8</span>
										</div>
									</div>
								</li>
								<li>
									<div class="rank-item-b">
										<!--名字<-->
										<div class="username">Player</div>
										<!--分数-->
										<div class="userpoint">0</div>
										<!--头像-->
										<div class="touxiang ani_flipInX ani_delay8">
											<img src="<%=path%>/static/resource/img/game/qwed_10.png" /><img class="user" src="<%=path%>/static/resource/img/game/abc_03.png" />
										</div>
										<!--progress-->
										<div class="progress-bar pro"></div>
										<!--rank排名-->
										<div class="paiming-b">
											<img src="<%=path%>/static/resource/img/game/qwed_15.png" /><span class="mingci">9</span>
										</div>
									</div>
								</li>
								<li>
									<div class="rank-item-b">
										<!--名字<-->
										<div class="username">Player</div>
										<!--分数-->
										<div class="userpoint">0</div>
										<!--头像-->
										<div class="touxiang">
											<img src="<%=path%>/static/resource/img/game/qwed_10.png" /><img class="user" src="<%=path%>/static/resource/img/game/abc_03.png" />
										</div>
										<!--progress-->
										<div class="progress-bar pro"></div>
										<!--rank排名-->
										<div class="paiming-b">
											<img src="<%=path%>/static/resource/img/game/qwed_15.png" /><span class="mingci" style="left: 53px;">10</span>
										</div>
									</div>
								</li>
							</ul>
						</div>

					</div>

				</div>
			</div>

			<!--签到模块 手机游戏-->
			<div class="module mobilegame" id="m-mobilegame">
				<div class="content">
					<div class="game-menu" style="display: none;">
						<ul>
							<li data-id="hdGame1"><img src="<%=path%>/static/resource/img/game/hdGame1.png" /><img class="gameimg" src="<%=path%>/static/resource/img/game/5.png" /></li>
							<li data-id="hdGame2"><img src="<%=path%>/static/resource/img/game/hdGame2.png" /><img class="gameimg" src="<%=path%>/static/resource/img/game/8.png" /></li>
							<li data-id="hdGame3"><img src="<%=path%>/static/resource/img/game/hdGame3.png" /><img class="gameimg" src="<%=path%>/static/resource/img/game/1.png" /></li>
							<li data-id="hdGame4"><img src="<%=path%>/static/resource/img/game/hdGame4.png" /><img class="gameimg" src="<%=path%>/static/resource/img/game/2.png" /></li>
							<li data-id="hdGame5"><img src="<%=path%>/static/resource/img/game/hdGame5.png" /><img class="gameimg" src="<%=path%>/static/resource/img/game/3.png" /></li>
							<li data-id="hdGame6"><img src="<%=path%>/static/resource/img/game/hdGame6.png" /><img class="gameimg" src="<%=path%>/static/resource/img/game/4.png" /></li>
							<li data-id="hdGame7"><img src="<%=path%>/static/resource/img/game/hdGame7.png" /><img class="gameimg" src="<%=path%>/static/resource/img/game/6.png" /></li>
							<li data-id="hdGame8"><img src="<%=path%>/static/resource/img/game/hdGame8.png" /><img class="gameimg" src="<%=path%>/static/resource/img/game/7.png" /></li>
							<li data-id="hdGame9"><img src="<%=path%>/static/resource/img/game/hdGame9.png" /><img class="gameimg" src="<%=path%>/static/resource/img/game/9.png" /></li>
						</ul>
					</div>

					<div class="game-content">
						<!--游戏等待雷达图-->
						<div class="game-wait" id="game-wait">
							<div class="leda">
								<span class="game-wait-people">游戏等待人数：<span
							class="game-wait-people-num" id="game-wait-people-num">0</span></span>
								<div class="game-wait-warp" id="game-leida">
									<img src="<%=path%>/static/resource/img/game/leida.png" class="leidaimg leida-roate-slow" /> <img src="<%=path%>/static/resource/img/game/leidasao.png" class="leidaimg leida-sao leida-roate" />
									<!--<img src="images/indexheader.png" class="game-head" />-->
								</div>
							</div>

							<div style="float: right; width: 38%; position: relative;margin-right: 5%;margin-top:143px;" id="erweima">
								<img src="<%=path%>/static/resource/img/game/hdGame1.png" style="width: 90%;" id="gameWarp" /><img src="<%=path%>/static/resource/img/game/erwei_03.jpg" style="position: absolute; top: 15%; left: 10%; width: 70%; border-radius: 10%;" id="gameErweima" />
							</div>

						</div>

						<!--<div class="game-wait" style="display: none;">
						<span class="game-wait-people">游戏等待人数：<span class="game-wait-people-num" id="game-wait-people-num">0</span></span>
						<div class="game-wait-warp" id="game-leida">
							<img src="<%=path%>/static/resource/img/game/leida.png" class="leida-roate-slow" />
							<img src="<%=path%>/static/resource/img/game/leidasao.png" class="leida-sao leida-roate" />
							<img src="images/indexheader.png" class="game-head"/>
						</div>
					</div>-->
					</div>

					<div class="game-rank-content"></div>
				</div>

			</div>
			<!--抽奖 -->
			<div class="module mobilegame" id="m-lottery">
				<div class="content">
					<!--抽奖-->
					<div class="luckdraw">
						<div class="currentdraw">
							<img src="<%=path%>/static/resource/img/lottery/preset.png" alt="抽奖" style="position: absolute; left: 0; height: 599px; top: 0; width: 550px;" />
							<div class="canyu" id="canyudiv">参加抽奖人数:0</div>
							<div class="sprizename" style="display: none;"></div>
							<div id="peopleImg" style="display: block;">
								<img src="" alt="" title="哈哈哈" id="changeImg" onerror="errorImg(this)" />
							</div>

							<div id="luckyprizeImg" style="display: none;">
								<img src="" alt="" onerror="errorImg(this)" />
								<img src="" alt="" onerror="errorImg(this)" />
								<img src="" alt="" onerror="errorImg(this)" />
								<img src="" alt="" onerror="errorImg(this)" />
								<img src="" alt="" onerror="errorImg(this)" />
							</div>

							<div class="prizepeople fivewarp">
								<img src="" alt="" onerror="errorImg(this)" />
								<img src="" alt="" onerror="errorImg(this)" />
								<img src="" alt="" onerror="errorImg(this)" />
								<img src="" alt="" onerror="errorImg(this)" />
								<img src="" alt="" onerror="errorImg(this)" />
							</div>

							<div class="prizepeople tenwarp">
								<img src="" alt="" onerror="errorImg(this)" />
								<img src="" alt="" onerror="errorImg(this)" />
								<img src="" alt="" onerror="errorImg(this)" />
								<img src="" alt="" onerror="errorImg(this)" />
								<img src="" alt="" onerror="errorImg(this)" />
								<img src="" alt="" onerror="errorImg(this)" />
								<img src="" alt="" onerror="errorImg(this)" />
								<img src="" alt="" onerror="errorImg(this)" />
								<img src="" alt="" onerror="errorImg(this)" />
								<img src="" alt="" onerror="errorImg(this)" />
							</div>
							<div class="prizepeople fifteenwarp">
								<img src="" alt="" onerror="errorImg(this)" />
								<img src="" alt="" onerror="errorImg(this)" />
								<img src="" alt="" onerror="errorImg(this)" />
								<img src="" alt="" onerror="errorImg(this)" />
								<img src="" alt="" onerror="errorImg(this)" />
								<img src="" alt="" onerror="errorImg(this)" />
								<img src="" alt="" onerror="errorImg(this)" />
								<img src="" alt="" onerror="errorImg(this)" />
								<img src="" alt="" onerror="errorImg(this)" />
								<img src="" alt="" onerror="errorImg(this)" />
								<img src="" alt="" onerror="errorImg(this)" />
								<img src="" alt="" onerror="errorImg(this)" />
								<img src="" alt="" onerror="errorImg(this)" />
								<img src="" alt="" onerror="errorImg(this)" />
								<img src="" alt="" onerror="errorImg(this)" />
							</div>

							<div id="lotteryname" style="position: absolute;text-align: center;top:390px;width:547px">???</div>

							<div class="butlist" style="    bottom: 110px;">
								<div id="passPre">
									<img src="<%=path%>/static/resource/img/lottery/toleft.png" alt="向前按钮" />
								</div>
								<div class="rank-line">
									<ul id="rankList">
										<li>特等奖</li>
										<li>一等奖</li>
										<li>二等奖</li>
										<li>三等奖</li>
										<li>幸运奖</li>
									</ul>
								</div>

								<div id="passNext">
									<img src="<%=path%>/static/resource/img/lottery/toright.png" alt="向前按钮" />
								</div>
							</div>
							<div class="stardraw" style="bottom: 15px;"></div>
						</div>
						<div class="drawlist">
							<img src="<%=path%>/static/resource/img/lottery/drawlist.png" alt="获奖列表" style="position: absolute; left: 0; height: 599px; top: 0; width: 550px;" />
							<div class="canyu" onclick='showPrizeUser()'>获奖人数:0</div>
							<!--	<div class="postA">-->
							<ul id="userList">
								<!--<li>
						<span class="firstluck">
					   </span>
						<span class="userImg">
					   		<img src="images/1.png" alt="" />
					   </span> 傻哈维订单
					</li>-->
							</ul>
							<!--<div id="swipt">
						<div id="huakuai"></div>
					</div>-->
							<!--	</div>-->
						</div>

						<div id="reset">
						</div>
						<div id="closebtn">

						</div>
					</div>

					<div class="lottery-result">
						<div class="l">
							<img src="<%=path%>/static/resource/img/lottery/result.png" alt="" class="kuang" />
							<img src="<%=path%>/static/resource/img/lottery/resultlight.png" class="light" />
							<img src="<%=path%>/static/resource/img/headimg/head1.png" alt="" class="lhead" />
							<div class="lname"></div>
						</div>
					</div>

				</div>
			</div>

			<!--转盘抽奖模块 转盘-->
			<div class="module" id="m-dzp">
				<div class="content">
					<div class="dzp-warp need-zoom">
						<div class="wheel done" style="background-image: url(<%=path%>/static/resource/img/dzp/bg.png)">
							<canvas class="item" id="wheelCanvas" width="422px" height="422px"></canvas>
							<img class="pointer" src="<%=path%>/static/resource/img/dzp/wheel-pointer.png" />
							<div class="dzp-btn"><img src="<%=path%>/static/resource/img/dzp/start.png" /></div>
							<img src="<%=path%>/static/resource/img/dzp/gift1.png" class="dzp-gift1" />
							<img src="<%=path%>/static/resource/img/dzp/gift2.png" class="dzp-gift2" />
						</div>
					</div>
				</div>
			</div>

			<!--现场互动-->
			<div class="module" id="m-hudong">
				<div class="content hudong-warp need-zoom">

					<div class="leda1" style="margin-top: 75px;">
						<span class="hudong-wait-people">游戏等待人数：<span class="hudong-wait-people-num" id="hudong-wait-people-num">0</span></span>
						<div class="hudong-wait-warp" style="float: left;" id="hudong-leida">
							<img src="<%=path%>/static/resource/img/game/leida.png" class="leida-roate-slow" />
							<img src="<%=path%>/static/resource/img/game/leidasao.png" class="leida-sao leida-roate" />
							<!--<img src="images/indexheader.png" class="game-head" />-->
						</div>

						<div id="" class="hudong-content-warp" style="float: right;display: block;  margin-top: -50px;">
							<div class="lc fleft">
								<div class="peidui">
									<img src="<%=path%>/static/resource/img/gn_r4_c1.png" height="86" width="94" />
									<img src="<%=path%>/static/resource/img/hudng/WOSHOU.png" />
									<img src="<%=path%>/static/resource/img/gn_r4_c1.png" height="86" width="94" />
									<img src="<%=path%>/static/resource/img/abc_03.png" class="hudonguser1-img" id="hudonguser1-img" />
									<img src="<%=path%>/static/resource/img/abc_03.png" class="hudonguser2-img" id="hudonguser2-img" />
									<span class="hudong-user" id="hudonguser1"></span>
									<span class="hudong-user" id="hudonguser2"></span>
								</div>
								<img src="<%=path%>/static/resource/img/hudng/ZUDUI.png" id="btn-peidui" /></br>
								<img src="<%=path%>/static/resource/img/hudng/RESET.png" id="btn-peidui-rest">
							</div>

							<div class="rc fleft">

								<div class="peiduizuhe">
									<img src="<%=path%>/static/resource/img/game/firstluck.png" />
									<img src="<%=path%>/static/resource/img/abc_03.png" style="width: 60px;height: 60px;border-radius: 50%;" />
									<img src="<%=path%>/static/resource/img/hudng/WOSHOU.png" style="width: 50px;" />
									<img src="<%=path%>/static/resource/img/abc_03.png" style="width: 60px;height: 60px;border-radius: 50%;" />
									<span class="zuhe-hudong-user1"></span>
									<span class="zuhe-hudong-user2"></span>
								</div>

								<div class="peiduizuhe">
									<img src="<%=path%>/static/resource/img/game/secondluck.png" />
									<img src="<%=path%>/static/resource/img/abc_03.png" style="width: 60px;height: 60px;border-radius: 50%;" />
									<img src="<%=path%>/static/resource/img/hudng/WOSHOU.png" style="width: 50px;" />
									<img src="<%=path%>/static/resource/img/abc_03.png" style="width: 60px;height: 60px;border-radius: 50%;" />
									<span class="zuhe-hudong-user1"></span>
									<span class="zuhe-hudong-user2"></span>
								</div>
								<div class="peiduizuhe">
									<img src="<%=path%>/static/resource/img/game/thrithluck.png" />
									<img src="<%=path%>/static/resource/img/abc_03.png" style="width: 60px;height: 60px;border-radius: 50%;" />
									<img src="<%=path%>/static/resource/img/hudng/WOSHOU.png" style="width: 50px;" />
									<img src="<%=path%>/static/resource/img/abc_03.png" style="width: 60px;height: 60px;border-radius: 50%;" />
									<span class="zuhe-hudong-user1"></span>
									<span class="zuhe-hudong-user2"></span>
								</div>
							</div>
						</div>
					</div>

					<ul class="card-warp" style="display: none;">
						<li>
							<div class="card-item"><img src="<%=path%>/static/resource/img/hudng/BAINIAN.png" class="card-item-front" /><img width="200" src="<%=path%>/static/resource/img/hudng/APPLE.png" class="card-item-back" /></div>
						</li>
						<li>
							<div class="card-item"><img src="<%=path%>/static/resource/img/hudng/TIANC.png" class="card-item-front" /><img width="200" src="<%=path%>/static/resource/img/hudng/PIG.png" class="card-item-back" /></div>
						</li>
						<li>
							<div class="card-item"><img src="<%=path%>/static/resource/img/hudng/GONGX.png" class="card-item-front" /><img width="200" src="<%=path%>/static/resource/img/hudng/TAITAN.png" class="card-item-back" /></div>
						</li>
						<li>
							<div class="card-item"><img src="<%=path%>/static/resource/img/hudng/XINH.png" class="card-item-front" /><img width="200" src="<%=path%>/static/resource/img/hudng/WEDDING.png" class="card-item-back" /></div>
						</li>
						<li>
							<div class="card-item"><img src="<%=path%>/static/resource/img/hudng/HUA.png" class="card-item-front" /><img width="200" src="<%=path%>/static/resource/img/hudng/MENGNA.png" class="card-item-back" /></div>
						</li>
					</ul>
				</div>
			</div>

			<!--投票 800-->
			<div class="module" id="m-vote" style="background-color: rgba(0,0,0,0.6);">
				<div class="content vote-stage">
					<div class="vote-warp">

						<ul>
							<li class="vote-item">
								<div class="vote-bar-warp">
									<div class="b">
										<div class="pnum"></div>
										<div class="bar"></div>
									</div>
								</div>
								<div class="vote-name"></div>
								<div class="vote-desc"></div>

							</li>
							<li class="vote-item">

								<div class="vote-bar-warp">
									<div class="b">
										<div class="pnum">200票</div>
										<div class="bar"></div>
									</div>
								</div>
								<div class="vote-name"> </div>
								<div class="vote-desc"></div>

							</li>

						</ul>
						<div class="line"></div>
						<div class="title"></div>
					</div>

				</div>
			</div>

			<!--抽取用户 800-->
			<div class="module" id="m-luckyman">
				<div class="content">
					<div id="" class="hudong-content-warp need-zoom" style="float: none; display: block;margin: 90px auto;background-image: url(<%=path%>/static/resource/img/GOODMAN.png);">
						<div class="lc fleft">
							<div class="peidui" style="margin-left: 85px;">
								<img src="<%=path%>/static/resource/img/gn_r4_c1.png" height="86" width="94" />
								<!--<img src="img/hudng/WOSHOU.png" />
								<img src="img/gn_r4_c1.png" height="86" width="94" />-->
								<img src="<%=path%>/static/resource/img/abc_03.png" class="hudonguser1-img" id="luckyuser-img" />
								<!--	<img src="img/abc_03.png" class="hudonguser2-img" id="hudonguser2-img" />-->
								<span class="hudong-user" id="luckyuser-name" style="margin-left:-15px"></span>
								<!--	<span class="hudong-user" id="hudonguser2">User2</span>-->
							</div>
							<img src="<%=path%>/static/resource/img/GO.png" id="btn-lucky-start" style="margin-left: 80px;cursor: pointer;height:115px;margin-top: -10px;" /></br>
							<img src="<%=path%>/static/resource/img/hudng/RESET.png" id="btn-lucky-rest" style="margin-left: 90px;cursor: pointer;height:100px;" />
						</div>

						<div class="rc fleft">
							<div class="peiduizuhe mlucky" style="height: 320px;width:420px;overflow-y: auto;" id="luckyuser-warp">

							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="cw" id="cw" style="visibility: hidden;display: block;z-index:999">
				<!--祝福榜-->
				<div class="cw-money-list" style="display:none">
					<!--<img src="img/zhufubang.png"/>-->
					<ul>
						<li>
							<img src="<%=path%>/static/resource/img/headimg/head2.png" style="" />
							<span class="cw-money-list-username">虚位以待</span>
							<span class="cw-money-list-money">0</span>
							<img src="<%=path%>/static/resource/img/1@2x.png" />
						</li>

						<li>
							<img src="<%=path%>/static/resource/img/headimg/head2.png" style="" />
							<span class="cw-money-list-username">虚位以待</span>
							<span class="cw-money-list-money">0</span>
							<img src="<%=path%>/static/resource/img/2@2x.png" />
						</li>
						<li>
							<img src="<%=path%>/static/resource/img/headimg/head2.png" style="" />
							<span class="cw-money-list-username">虚位以待</span>
							<span class="cw-money-list-money">0</span>
							<img src="<%=path%>/static/resource/img/3@2x.png" />
						</li>
						<li>
							<img src="<%=path%>/static/resource/img/headimg/head2.png" style="" />
							<span class="cw-money-list-username">虚位以待</span>
							<span class="cw-money-list-money">0</span>
						</li>
						<li>
							<img src="<%=path%>/static/resource/img/headimg/head2.png" style="" />
							<span class="cw-money-list-username">虚位以待</span>
							<span class="cw-money-list-money">0</span>
						</li>

					</ul>

				</div>
				<!--聊天信息-->
				<div class="cw-chat-list" style="height: 950px;">
					<div class="cw-chat-warp">
						<ul id="scrollDiv">
						</ul>

						<ul id="scrollDiv-copy">
						</ul>
					</div>
				</div>
			</div>

			<!--拔河等待模块-->
			<div class="module" id="m-bahe-wait" style="overflow: hidden;opacity: 0;background-position: 100%;">
				<div id="mk-bahe">
					<div class="fleft bahe-wait-l">
						<div class="t-red" style="margin-left:150px">
							<span class="t-red-people">45人</span>
						</div>

						</br>
						<div class="w-people-warp-l">
							<div id="ppp">

							</div>
						</div>

						<img src="<%=path%>/static/resource/img/bahe/rwait.png" style="margin-top: 40px;" />

					</div>
					<div class="fright bahe-wait-r">
						<div class="t-blue" style="margin-right:120px">
							<span class="t-blue-people">0人</span>
						</div>
						</br>
						<div class="w-people-warp-r">
							<div></div>
						</div>
						<img src="<%=path%>/static/resource/img/bahe/bwait.png" style="margin-top: 40px;" />
					</div>
					<div class="bherweima"><img src="<%=qrcodeUrl%>" /></div>
				</div>
			</div>
			<!--拔河画面-->
			<div class="module" id="m-bahe" style="background-image: url(<%=path%>/static/resource/img/bahe/bg.png);overflow: hidden;background-size: 100%;">
				<div id="mk-bahemain">
					<!--游戏倒计时-->
					<div class="tip-box" style="display: none;" id="bh-countdown">
						<div class="num" id="num">30 29 28 27 26 25 24 23 22 21 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1 0</div>
					</div>

					<div class="tip-shake1"><img src="<%=path%>/static/resource/img/tip-shake2.png" /></div>

					<!--游戏开始倒计时-->
					<div class="tip-box-start" style="display:none;" id="bhgame-ready"></div>

					<div class="rope"></div>

					<div class="bahe-team">
						<div class="t-red">
							<span class="t-red-people">0人</span>
							<span class="t-red-point">0分</span>
						</div>
						<div class="t-blue">
							<span class="t-blue-people">0人</span>
							<span class="t-blue-point">0分</span>
						</div>
					</div>

					<!--boat-->
					<div style="width:85px; height: 113px; background-image: url(<%=path%>/static/resource/img/bahe/boat.png);top: 450px;left: 1500px;position: absolute;z-index:-1">

					</div>

					<!--Dolphin-->
					<div class="dp-move" id="dp-move" style="width: 479px;height: 321px;position: absolute;background-image: url(<%=path%>/static/resource/img/bahe/dp.png);top: 350px;left: 800px;	background-position-x: 479px">

					</div>

					<!--people-->
					<div class="bahe-people-lr">
						<div class="bahe-people">
							<ul class="bahe-head-l">
								<li></li>
								<li></li>
								<li></li>
								<li></li>
								<li></li>
								<li></li>
								<li></li>
								<li></li>
								<li></li>
								<li></li>
							</ul>

							<ul class="bahe-head-r">
								<li></li>
								<li></li>
								<li></li>
								<li></li>
								<li></li>
								<li></li>
								<li></li>
								<li></li>
								<li></li>
								<li></li>
							</ul>

						</div>
					</div>
					<!--	<img src="img/bahe/people.png"/>-->
					<!--<img src="img/back/2.jpg"/>-->
					<div class="bahe-result" style="margin-top: 100px;display: none;">
						<img src="<%=path%>/static/resource/img/bahe/rwin.png" />
					</div>
				</div>
			</div>
			<!--主页空 800-->
			<div class="module" id="m-main" style="background-color: rgba(0,0,0,0);"></div>

			<div class="module" id="m-guess" style="background-color: rgba(0,0,0,.3);">
				<div class="content">
					<div class="guess-warp">
						<div class="star-warp">
							<img src="" alt="" />
						</div>
						<div class="start-control-warp">
							<img class="guess-continue" src="<%=path%>/static/resource/img/guess/continue.png" />
							<img class="guess-answer" src="<%=path%>/static/resource/img/guess/answer.png" />
							<img class="guess-start" src="<%=path%>/static/resource/img/guess/start.png" />
							<img class="guess-rechoose" src="<%=path%>/static/resource/img/guess/choose.png" />
							<div class="star-btn">
								<div style="width: 100%;text-align: center;">
									<img src="<%=path%>/static/resource/img/guess/toleft.png" alt="" id="preStar" />
									<!--	<img src="<%=path%>/static/resource/img/guess/1.png" alt="" id="starIndex" />-->
									<span id="starIndex">1</span>
									<img src="<%=path%>/static/resource/img/guess/toright.png" alt="" id="nextStar" /></div>
							</div>
						</div>

						<div class="guess-deng">
							<div class="guess-deng-move">
								<img src="<%=path%>/static/resource/img/guess/deng11.png" />
								<img src="<%=path%>/static/resource/img/guess/deng22.png" />
							</div>
						</div>
						<img src="<%=path%>/static/resource/img/guess/title1.png" class="guess-title" alt="" />
					</div>
				</div>
			</div>

			<div class="module" id="m-money" style="background-color: rgba(0,0,0,.3);display:none">
				<div class="content game-money" id="moneygame">
					<!-- 主画面 -->
					<div class="money-stage">
						<img src="<%=path%>/static/resource/img/money/SKY.png" class="sky " />
						<img src="<%=path%>/static/resource/img/money/SUN.png" class="sun move" />
						<img src="<%=path%>/static/resource/img/money/SHIP.png" class="ship " />
						<img src="<%=path%>/static/resource/img/money/ground.png" class="ground " />

						<div class="man">
							<div class="p" style="display:none"><img src="<%=path%>/static/resource//img/money/runend.png" alt="" class="end"></div>
							<div class="p"><img src="<%=path%>/static/resource/img/money/PAOBU.png" alt="" class=""></div>
							<div class="s"><img src="<%=path%>/static/resource/img/money/shadows.png" alt="" class="" style="display:none"></div>
							<div class="l"><img src="<%=path%>/static/resource/img/money/FHLUN.png" alt="" class=""></div>
						</div>

						<img class="goldbox" src="<%=path%>/static/resource/img/money/GOLD.png" alt="">
						<img class="facai" src="<%=path%>/static/resource/img/money/facai.png" alt="">

						<div class="ds">30"</div>
						<div class="tip-box-start" style="display: none;"></div>
					</div>
					<!-- 排名 -->
					<div class="money-rank" id="money-rank">
						<ul class="warp">
							<li class="rank-itema">
								<div class="rank-item-b">
									<!--名字-->
									<div class="username">Player</div>
									<!--分数-->
									<div class="userpoint">0</div>
									<!--头像-->
									<div class="touxiang ani_flipInX">
										<img src="<%=path%>/static/resource/img/game/qwed_10.png" class="kuang">
										<img class="user" src="<%=path%>/static/resource/img/game/abc_03.png">
									</div>
									<!--progress-->
									<div class="progress-bar pro" style="height: 10px;"></div>
									<!--rank排名-->
									<div class="paiming-b">
										<img src="<%=path%>/static/resource/img/game/qwed_15.png"><span class="mingci">1</span>
									</div>
								</div>

							</li>

							<li class="rank-itema">
								<div class="rank-item-b">
									<!--名字-->
									<div class="username">Player</div>
									<!--分数-->
									<div class="userpoint">0</div>
									<!--头像-->
									<div class="touxiang ani_flipInX">
										<img src="<%=path%>/static/resource/img/game/qwed_10.png" class="kuang">
										<img class="user" src="<%=path%>/static/resource/img/game/abc_03.png">
									</div>
									<!--progress-->
									<div class="progress-bar pro" style="height: 10px;"></div>
									<!--rank排名-->
									<div class="paiming-b">
										<img src="<%=path%>/static/resource/img/game/qwed_15.png"><span class="mingci">2</span>
									</div>
								</div>

							</li>

							<li class="rank-itema">
								<div class="rank-item-b">
									<!--名字-->
									<div class="username">Player</div>
									<!--分数-->
									<div class="userpoint">0</div>
									<!--头像-->
									<div class="touxiang ani_flipInX">
										<img src="<%=path%>/static/resource/img/game/qwed_10.png" class="kuang">
										<img class="user" src="<%=path%>/static/resource/img/game/abc_03.png">
									</div>
									<!--progress-->
									<div class="progress-bar pro" style="height: 10px;"></div>
									<!--rank排名-->
									<div class="paiming-b">
										<img src="<%=path%>/static/resource/img/game/qwed_15.png"><span class="mingci">3</span>
									</div>
								</div>

							</li>

							<li class="rank-itema">
								<div class="rank-item-b">
									<!--名字-->
									<div class="username">Player</div>
									<!--分数-->
									<div class="userpoint">0</div>
									<!--头像-->
									<div class="touxiang ani_flipInX">
										<img src="<%=path%>/static/resource/img/game/qwed_10.png" class="kuang">
										<img class="user" src="<%=path%>/static/resource/img/game/abc_03.png">
									</div>
									<!--progress-->
									<div class="progress-bar pro" style="height: 10px;"></div>
									<!--rank排名-->
									<div class="paiming-b">
										<img src="<%=path%>/static/resource/img/game/qwed_15.png"><span class="mingci">4</span>
									</div>
								</div>

							</li>

							<li class="rank-itema">
								<div class="rank-item-b">
									<!--名字-->
									<div class="username">Player</div>
									<!--分数-->
									<div class="userpoint">0</div>
									<!--头像-->
									<div class="touxiang ani_flipInX">
										<img src="<%=path%>/static/resource/img/game/qwed_10.png" class="kuang">
										<img class="user" src="<%=path%>/static/resource/img/game/abc_03.png">
									</div>
									<!--progress-->
									<div class="progress-bar pro" style="height: 10px;"></div>
									<!--rank排名-->
									<div class="paiming-b">
										<img src="<%=path%>/static/resource/img/game/qwed_15.png"><span class="mingci">5</span>
									</div>
								</div>

							</li>

							<li class="rank-itema">
								<div class="rank-item-b">
									<!--名字-->
									<div class="username">Player</div>
									<!--分数-->
									<div class="userpoint">0</div>
									<!--头像-->
									<div class="touxiang ani_flipInX">
										<img src="<%=path%>/static/resource/img/game/qwed_10.png" class="kuang">
										<img class="user" src="<%=path%>/static/resource/img/game/abc_03.png">
									</div>
									<!--progress-->
									<div class="progress-bar pro" style="height: 10px;"></div>
									<!--rank排名-->
									<div class="paiming-b">
										<img src="<%=path%>/static/resource/img/game/qwed_15.png"><span class="mingci">6</span>
									</div>
								</div>

							</li>

							<li class="rank-itema">
								<div class="rank-item-b">
									<!--名字-->
									<div class="username">Player</div>
									<!--分数-->
									<div class="userpoint">0</div>
									<!--头像-->
									<div class="touxiang ani_flipInX">
										<img src="<%=path%>/static/resource/img/game/qwed_10.png" class="kuang">
										<img class="user" src="<%=path%>/static/resource/img/game/abc_03.png">
									</div>
									<!--progress-->
									<div class="progress-bar pro" style="height: 10px;"></div>
									<!--rank排名-->
									<div class="paiming-b">
										<img src="<%=path%>/static/resource/img/game/qwed_15.png"><span class="mingci">7</span>
									</div>
								</div>

							</li>

							<li class="rank-itema">
								<div class="rank-item-b">
									<!--名字-->
									<div class="username">Player</div>
									<!--分数-->
									<div class="userpoint">0</div>
									<!--头像-->
									<div class="touxiang ani_flipInX">
										<img src="<%=path%>/static/resource/img/game/qwed_10.png" class="kuang">
										<img class="user" src="<%=path%>/static/resource/img/game/abc_03.png">
									</div>
									<!--progress-->
									<div class="progress-bar pro" style="height: 10px;"></div>
									<!--rank排名-->
									<div class="paiming-b">
										<img src="<%=path%>/static/resource/img/game/qwed_15.png"><span class="mingci">8</span>
									</div>
								</div>

							</li>

							<li class="rank-itema">
								<div class="rank-item-b">
									<!--名字-->
									<div class="username">Player</div>
									<!--分数-->
									<div class="userpoint">0</div>
									<!--头像-->
									<div class="touxiang ani_flipInX">
										<img src="<%=path%>/static/resource/img/game/qwed_10.png" class="kuang">
										<img class="user" src="<%=path%>/static/resource/img/game/abc_03.png">
									</div>
									<!--progress-->
									<div class="progress-bar pro" style="height: 10px;"></div>
									<!--rank排名-->
									<div class="paiming-b">
										<img src="<%=path%>/static/resource/img/game/qwed_15.png"><span class="mingci">9</span>
									</div>
								</div>

							</li>

							<li class="rank-itema">
								<div class="rank-item-b">
									<!--名字-->
									<div class="username">Player</div>
									<!--分数-->
									<div class="userpoint">0</div>
									<!--头像-->
									<div class="touxiang ani_flipInX">
										<img src="<%=path%>/static/resource/img/game/qwed_10.png" class="kuang">
										<img class="user" src="<%=path%>/static/resource/img/game/abc_03.png">
									</div>
									<!--progress-->
									<div class="progress-bar pro" style="height: 10px;"></div>
									<!--rank排名-->
									<div class="paiming-b">
										<img src="<%=path%>/static/resource/img/game/qwed_15.png"><span class="mingci t">10</span>
									</div>
								</div>

							</li>

						</ul>

					</div>

				</div>
			</div>

			<div class="module" style="background-color: #f6ceb5;" id="m-huadeng">
				<div class="content">
					<div class="huadeng-stage little-game-stage">

						<img src="<%=path%>/static/resource/img/huadeng/sky.png" alt="" class="sky">
						<img src="<%=path%>/static/resource/img/huadeng/moon.png" alt="" class="moon move">
						<img src="<%=path%>/static/resource/img/huadeng/cloud.png" alt="" class="cloud">

						<div class="dl-warp">
						</div>
						<img src="<%=path%>/static/resource/img/huadeng/house.png" alt="" class="house">
						<div class="deng">
							<img src="<%=path%>/static/resource/img/huadeng/deng.png" alt="">
						</div>

						<img src="<%=path%>/static/resource/img/huadeng/renwu.png" alt="" class="renwu">

						<div class="ds" style="display: block;">0</div>

					</div>
					<div class="money-rank" id="huadeng-rank">
						<ul class="warp">
							<li class="rank-itema">
								<div class="rank-item-b">
									<!--名字-->
									<div class="username">Player</div>
									<!--分数-->
									<div class="userpoint">0</div>
									<!--头像-->
									<div class="touxiang ani_flipInX">
										<img src="<%=path%>/static/resource/img/game/qwed_10.png" class="kuang">
										<img class="user" src="<%=path%>/static/resource/img/game/abc_03.png">
									</div>
									<!--progress-->
									<div class="progress-bar pro" style="height: 10px;"></div>
									<!--rank排名-->
									<div class="paiming-b">
										<img src="<%=path%>/static/resource/img/game/qwed_15.png"><span class="mingci">1</span>
									</div>
								</div>

							</li>

							<li class="rank-itema">
								<div class="rank-item-b">
									<!--名字-->
									<div class="username">Player</div>
									<!--分数-->
									<div class="userpoint">0</div>
									<!--头像-->
									<div class="touxiang ani_flipInX">
										<img src="<%=path%>/static/resource/img/game/qwed_10.png" class="kuang">
										<img class="user" src="<%=path%>/static/resource/img/game/abc_03.png">
									</div>
									<!--progress-->
									<div class="progress-bar pro" style="height: 10px;"></div>
									<!--rank排名-->
									<div class="paiming-b">
										<img src="<%=path%>/static/resource/img/game/qwed_15.png"><span class="mingci">2</span>
									</div>
								</div>

							</li>

							<li class="rank-itema">
								<div class="rank-item-b">
									<!--名字-->
									<div class="username">Player</div>
									<!--分数-->
									<div class="userpoint">0</div>
									<!--头像-->
									<div class="touxiang ani_flipInX">
										<img src="<%=path%>/static/resource/img/game/qwed_10.png" class="kuang">
										<img class="user" src="<%=path%>/static/resource/img/game/abc_03.png">
									</div>
									<!--progress-->
									<div class="progress-bar pro" style="height: 10px;"></div>
									<!--rank排名-->
									<div class="paiming-b">
										<img src="<%=path%>/static/resource/img/game/qwed_15.png"><span class="mingci">3</span>
									</div>
								</div>

							</li>

							<li class="rank-itema">
								<div class="rank-item-b">
									<!--名字-->
									<div class="username">Player</div>
									<!--分数-->
									<div class="userpoint">0</div>
									<!--头像-->
									<div class="touxiang ani_flipInX">
										<img src="<%=path%>/static/resource/img/game/qwed_10.png" class="kuang">
										<img class="user" src="<%=path%>/static/resource/img/game/abc_03.png">
									</div>
									<!--progress-->
									<div class="progress-bar pro" style="height: 10px;"></div>
									<!--rank排名-->
									<div class="paiming-b">
										<img src="<%=path%>/static/resource/img/game/qwed_15.png"><span class="mingci">4</span>
									</div>
								</div>

							</li>

							<li class="rank-itema">
								<div class="rank-item-b">
									<!--名字-->
									<div class="username">Player</div>
									<!--分数-->
									<div class="userpoint">0</div>
									<!--头像-->
									<div class="touxiang ani_flipInX">
										<img src="<%=path%>/static/resource/img/game/qwed_10.png" class="kuang">
										<img class="user" src="<%=path%>/static/resource/img/game/abc_03.png">
									</div>
									<!--progress-->
									<div class="progress-bar pro" style="height: 10px;"></div>
									<!--rank排名-->
									<div class="paiming-b">
										<img src="<%=path%>/static/resource/img/game/qwed_15.png"><span class="mingci">5</span>
									</div>
								</div>

							</li>

							<li class="rank-itema">
								<div class="rank-item-b">
									<!--名字-->
									<div class="username">Player</div>
									<!--分数-->
									<div class="userpoint">0</div>
									<!--头像-->
									<div class="touxiang ani_flipInX">
										<img src="<%=path%>/static/resource/img/game/qwed_10.png" class="kuang">
										<img class="user" src="<%=path%>/static/resource/img/game/abc_03.png">
									</div>
									<!--progress-->
									<div class="progress-bar pro" style="height: 10px;"></div>
									<!--rank排名-->
									<div class="paiming-b">
										<img src="<%=path%>/static/resource/img/game/qwed_15.png"><span class="mingci">6</span>
									</div>
								</div>

							</li>

							<li class="rank-itema">
								<div class="rank-item-b">
									<!--名字-->
									<div class="username">Player</div>
									<!--分数-->
									<div class="userpoint">0</div>
									<!--头像-->
									<div class="touxiang ani_flipInX">
										<img src="<%=path%>/static/resource/img/game/qwed_10.png" class="kuang">
										<img class="user" src="<%=path%>/static/resource/img/game/abc_03.png">
									</div>
									<!--progress-->
									<div class="progress-bar pro" style="height: 10px;"></div>
									<!--rank排名-->
									<div class="paiming-b">
										<img src="<%=path%>/static/resource/img/game/qwed_15.png"><span class="mingci">7</span>
									</div>
								</div>

							</li>

							<li class="rank-itema">
								<div class="rank-item-b">
									<!--名字-->
									<div class="username">Player</div>
									<!--分数-->
									<div class="userpoint">0</div>
									<!--头像-->
									<div class="touxiang ani_flipInX">
										<img src="<%=path%>/static/resource/img/game/qwed_10.png" class="kuang">
										<img class="user" src="<%=path%>/static/resource/img/game/abc_03.png">
									</div>
									<!--progress-->
									<div class="progress-bar pro" style="height: 10px;"></div>
									<!--rank排名-->
									<div class="paiming-b">
										<img src="<%=path%>/static/resource/img/game/qwed_15.png"><span class="mingci">8</span>
									</div>
								</div>

							</li>

							<li class="rank-itema">
								<div class="rank-item-b">
									<!--名字-->
									<div class="username">Player</div>
									<!--分数-->
									<div class="userpoint">0</div>
									<!--头像-->
									<div class="touxiang ani_flipInX">
										<img src="<%=path%>/static/resource/img/game/qwed_10.png" class="kuang">
										<img class="user" src="<%=path%>/static/resource/img/game/abc_03.png">
									</div>
									<!--progress-->
									<div class="progress-bar pro" style="height: 10px;"></div>
									<!--rank排名-->
									<div class="paiming-b">
										<img src="<%=path%>/static/resource/img/game/qwed_15.png"><span class="mingci">9</span>
									</div>
								</div>

							</li>

							<li class="rank-itema">
								<div class="rank-item-b">
									<!--名字-->
									<div class="username">Player</div>
									<!--分数-->
									<div class="userpoint">0</div>
									<!--头像-->
									<div class="touxiang ani_flipInX">
										<img src="<%=path%>/static/resource/img/game/qwed_10.png" class="kuang">
										<img class="user" src="<%=path%>/static/resource/img/game/abc_03.png">
									</div>
									<!--progress-->
									<div class="progress-bar pro" style="height: 10px;"></div>
									<!--rank排名-->
									<div class="paiming-b">
										<img src="<%=path%>/static/resource/img/game/qwed_15.png"><span class="mingci t">10</span>
									</div>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</div>

			<div class="module" id="m-newsign">
				<div id="3dcontainer"></div>
				<div class="bigCard" id="newbigcard" style="width: 467px; height: 588px;" style="background-image: url(" <%=path%>/static/resource/img/sign/big_box_bg.png")">
					<img src="">
					<span></br></span>
				</div>
			</div>

			<div class="module" id="m-fengcai">
				<div class="fengcai-stage" style="transform: scale(1);">
					<!-- Swiper -->
					<div class="swiper-container">
						<div class="swiper-wrapper">
							<!--<div class="swiper-slide">
						<div class="fc-img"></div>
						<div class="fc-info">
							<p>王强 销售总监</p>
							<p>17年至今，销售了一百多套房子。销售了一百多套房子。销售了一百多套房子。销售了一百多套房子。销售了一百多套房子。</p>
						</div>
					</div>
				-->
						</div>
					</div>

					<div class="fc-kuang"></div>
					<div class="fc-line"></div>
					<div class="fc-line2"></div>

					<div class="fc-rtop">
						<img src="<%=path%>/static/resource/img/fengcai/star.png" class="star" />
						<img src="<%=path%>/static/resource/img/fengcai/fengcai.png" alt="" class="yuangong" />
						<img src="<%=path%>/static/resource/img/fengcai/xieshou.png" alt="" class="xieshou" />
					</div>

					<div class="fc-control">
						<img src="<%=path%>/static/resource/img/fengcai/pre.png" alt="" id="fc-pre" />
						<img src="<%=path%>/static/resource/img/fengcai/play.png" alt="" id="fc-play" />
						<img src="<%=path%>/static/resource/img/fengcai/pause.png" alt="" id="fc-pause" style="display: none;" />
						<img src="<%=path%>/static/resource/img/fengcai/next.png" alt="" id="fc-next" />
					</div>

				</div>

			</div>

			<!--底部 800-->
			<div class="footer">
				<div class="fleft btn-menu btn-module" data-des="m-3dsign" style="display:none">
					<img src="<%=path%>/static/resource/img/index/qiandao.png" />
				</div>
				<div class="fleft btn-menu btn-module" data-des="m-newsign">
					<img src="<%=path%>/static/resource/img/index/qiandao.png" />
				</div>

				<div class="fleft btn-menu btn-module" data-des="m-sign" style="display:none">
					<img src="<%=path%>/static/resource/img/index/qiandao.png" />
				</div>

				<div class="fleft btn-menu btn-module" data-des="m-money" id="btn-money">
					<img src="<%=path%>/static/resource/img/index/btn_money1.png" />
				</div>

				<div class="fleft btn-menu btn-module" data-des="m-shakegame">
					<img src="<%=path%>/static/resource/img/index/yaoyiyao.png" />
				</div>

				<div class="fleft btn-menu btn-module" data-des="m-bahe-wait" id="btn-bahe">
					<img src="<%=path%>/static/resource/img/index/btn_bahe.png" />
				</div>

				<div class="fleft btn-menu " data-des="m-mobilegame" id="youxi">
					<img src="http://ocar2d7vc.bkt.clouddn.com/hidong-game/img/btn-menu-game.png" />
				</div>
				<div class="fleft btn-menu  btn-module" data-des="m-guess"><img src="<%=path%>/static/resource/img/index/btn_guess1.png" /></div>

				<dl class="line fleft"></dl>
				<div class="fleft btn-menu btn-module small" data-des="m-lottery">
					<img src="<%=path%>/static/resource/img/index/choujiang.png" />
				</div>
				<div class="fleft btn-menu damu-switch small" style="display:block">
					<img src="<%=path%>/static/resource/img/index/danmu.png" />
				</div>

				<div class="fleft btn-menu btn-module" data-des="m-sing" style="display:none"><img src="<%=path%>/static/resource/img/index/btn_sing.png" /></div>
				<div class="fleft btn-menu btn-module small" data-des="m-dzp"><img src="<%=path%>/static/resource/img/index/btn_dzp.png" /></div>
				<div class="fleft btn-menu  btn-module" data-des="m-vote" style=""><img src="<%=path%>/static/resource/img/index/btn_vote.png" /></div>
				<div class="fleft btn-menu  btn-module small" data-des="m-hudong"><img src="<%=path%>/static/resource/img/index/btn_hudong.png" /></div>
				<div class="fleft btn-menu  btn-module" data-des="m-luckyman" id="luckyMan"><img src="<%=path%>/static/resource/img/index/btn_luckyman.png" /></div>

				<div class="fleft btn-menu" id="btn_cw" style="display:block">
					<img src="<%=path%>/static/resource/img/index/btn_gxb.png" />
				</div>

				<div class="fleft btn-menu btn-module" data-des="m-fengcai" style="display:block" id="btn-fengcai">
					<img src="<%=path%>/static/resource/img/index/btn_fengcai.png" />
				</div>

				<div class="fleft btn-menu" id="btn_fullsc">
					<img src="<%=path%>/static/resource/img/index/btn_full.png" />
				</div>

				<div class="fleft btn-menu small" onclick="erweima()">
					<img src="<%=path%>/static/resource/img/index/btn_erweima.png" />
				</div>

				<div class="fleft btn-menu" onclick="toogleMusic(this)" data-current='0' id="toogleMusic" style="display:none"><img src="<%=path%>/static/resource/img/index/yingyue.png" /></div>

				<div class="fleft btn-menu" onclick="toVideo()" style="display:none" id="siyiBtn">
					<img src="<%=path%>/static/resource/img/index/btn_vote.png" />
				</div>

				<div class="fleft btn-menu btn-module" data-des="m-main" id="homepage">
					<img src="<%=path%>/static/resource/img/index/shouye.png" />
				</div>

				<div class="fleft btn-menu btn-module" data-des="m-video" style="display:none" id="wishvideomenubtn">
					<img src="<%=path%>/static/resource/img/index/shouye.png" />
				</div>

				<div id="game-start" class="btn-menu-big fright" style="display: none;">
					<img src="<%=path%>/static/resource/img/game/btn-start .png" />
				</div>
				<div id="game-end" class="btn-menu-big fright" style="display: none;">
					<img src="<%=path%>/static/resource/img/game/btn-gameend.png" />
				</div>

				<div id="game-rest" class="btn-menu-big fright" style="display: none;"><img src="<%=path%>/static/resource/img/game/btn-rest.png" /></div>
				<div id="lottery-rest" class="btn-menu-big fright" style="display: none;"><img src="<%=path%>/static/resource/img/game/btn-rest.png" /></div>

				<div id="hudong-start" class="btn-menu-big fright" style="display: none;"><img src="<%=path%>/static/resource/img/game/btn-start .png" /></div>
				<div id="hudong-back" class="btn-menu-big fright" style="display: none;"><img src="<%=path%>/static/resource/img/index/btn-back.png" /></div>

				<div id="vote-rest" class="btn-menu-big fright" style="display: none;"><img src="<%=path%>/static/resource/img/game/btn-rest.png" /></div>

			</div>
		</div>

		<script src="<%=path%>/static/resource/js/layer.js"></script>
		<script src="<%=path%>/static/resource/businessjs/main.min.js?20180209"></script>
		
		<!--<script src="<%=path%>/static/resource/businessjs/swiper.min.js"></script>
		<script src="<%=path%>/static/resource/businessjs/observe.js"></script>
		<script src="<%=path%>/static/resource/businessjs/three.min.js"></script>
		<script src="<%=path%>/static/resource/businessjs/CSS3DRenderer.js"></script>
		<script src="<%=path%>/static/resource/businessjs/TrackballControls.js"></script>
		<script src="<%=path%>/static/resource/businessjs/tween.min.js"></script>-->
		<!--<script src="<%=path%>/static/resource/businessjs/main.min.js?20180207010"></script>-->

		<!--<script src="<%=path%>/static/resource/businessjs/huajia.js"></script>
		<script src="<%=path%>/static/resource/businessjs/jQueryRotate.js"></script>
		<script src="<%=path%>/static/resource/businessjs/reconnecting-websocket.js"></script>
		<script src="<%=path%>/static/resource/businessjs/danmu.js"></script>
		<script src="<%=path%>/static/resource/businessjs/lottery.js"></script>
		<script src="<%=path%>/static/resource/businessjs/snabbt.min.js"></script>
		<script src="<%=path%>/static/resource/businessjs/usercome.js"></script>
		<script src="<%=path%>/static/resource/businessjs/3dsign.js"></script>
		<script src="<%=path%>/static/resource/businessjs/hbnotice.js"></script>
		<script src="<%=path%>/static/resource/businessjs/dzp.js"></script>
		<script src="<%=path%>/static/resource/businessjs/hudong.js"></script>
		<script src="<%=path%>/static/resource/businessjs/bgm.js"></script>
		<script src="<%=path%>/static/resource/businessjs/index.js"></script>
		<script src="<%=path%>/static/resource/businessjs/slide.js"></script>-->

	</body>

</html>