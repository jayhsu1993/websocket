//简易的图片轮播  JS
	var hslider = {
		background_timer: null,
		current: 0,
		next: 0,
		time:8000,
		init: function() {
			var that = this;
			//$('.slider-item').css('height', window.innerHeight + 'px');
			$('.slider-item')[0].style.display = 'block';
			
			if($('.slider-item').length>1){
				hslider.background_timer = setInterval(hslider.mySlider, hslider.time);
			}
		},
		mySlider: function() {
			//console.log(hslider.current);
			for(var i = 0; i < $('.slider-item').length; i++) {
				if(hslider.current == i) {
					if(hslider.current == $('.slider-item').length - 1) {
						hslider.next = 0;
						hslider.silder(hslider.current, hslider.next);
						hslider.current = 0;
					} else {
						hslider.next += 1;
						hslider.silder(hslider.current, hslider.next);
						hslider.current++;
					}
					break;
				}
			}

		},
		silder: function(a,b) {
			$('.slider-item')[a].classList.remove('fadeIn');
			$('.slider-item')[a].classList.add('fadeOut');
			$('.slider-item')[b].classList.remove('fadeOut');
			$('.slider-item')[b].classList.add('fadeIn')
		},
		stop: function() {
			clearInterval(hslider.background_timer);
		},
		start: function() {
			hslider.background_timer = setInterval(hslider.mySlider, hslider.time);
		}
	}

	//hslider.init();
	
	$.get(pageInfo.postUrl + "backgroundController/listBackgroundImg/"+pageInfo.roomId, {}, function(data) {
		if(data.success){
			//alert('123');
			//$('#sliderWarp').
			//alert(typeof(data.data)!=undefined)
			if(typeof(data.data)!='undefined'){
				if(data.data.length>0){
					$('#sliderWarp').empty();
					$('#sliderWarp')[0].style.back
					for(var i = 0;i<data.data.length;i++){
						$('#sliderWarp').append('<div class="slider-item fadeOut" style="background-image: url('+data.data[i]+');"></div>')
					}
					$('#sliderWarp')[0].children[0].classList.remove('fadeOut');
					$('#sliderWarp')[0].children[0].classList.add('fadeIn');
					document.getElementById("sliderWarp").style.background="";
					hslider.init();
				}
			}
			
		}else{};
		
	}).error(function(){
		
	});


function resizeHtml() {
			$('body').css('margin-top','0');
			setTimeout(function(){
				console.log('time resize');
				/*缩放系数   1280*720 为基础*/
				pageInfo.zoom = window.innerWidth/1280 >window.innerHeight/720?window.innerHeight/720:window.innerWidth/1280
				/*缩放系数   1920*1080 为基础*/
				pageInfo.zooma = window.innerWidth/1920 >window.innerHeight/1080?window.innerHeight/1080:window.innerWidth/1920
				pageInfo.needResize = false;
				if((window.innerHeight/window.innerWidth).toFixed(2)>(9/16).toFixed(2)){
					pageInfo.needResize = true;
				}	
				
				$('body').css('zoom','1');
				
				document.body.style.height = window.innerHeight + 'px';
				document.body.style.width = window.innerWidth+'px';
				$('.slider-item').css('height', window.innerHeight + 'px');
				$('.module').css('height', window.innerHeight + 'px')
				
				
				/*游戏  resize*/
				$('.leda').css('margin-top',(window.innerHeight - 140 - $('.leda').height())/ 2 + 'px');
				
				//$('#erweima').css('margin-top',(window.innerHeight - 140 - $('.leda').height()) / 2+ $('.game-wait-people').height() + 'px');
				$('#erweima').css('margin-top',(window.innerHeight - 140 - $('#erweima').height()) / 2);
				
				/*游戏  resize END*/

				/*摇一摇  resize*/
				$('.shakegame .content').css({
					"height" : window.innerHeight + 'px',
					"width" : window.innerWidth + 'px',
					"background-color" : "rgb(248,217,156)"
				})

				$('.husband-warp').css({
					"left" : '1110px'
				})
				var _w = window.innerWidth - $('.rank-warp li').width() * 10
				if (_w > 0) {
					$('.rank-warp li:first-child')
							.css('margin-left', _w / 2 + 'px')
				}
				/*摇一摇  resize*/

				/*抽奖 resize*/
				$('.luckdraw').css('zoom', pageInfo.zoom);
				$('.lottery-result').css('zoom',pageInfo.zooma);
				/*抽奖 resize*/
				
				/*guess resize*/
				$('.guess-warp').css('zoom', pageInfo.zooma);
				/*guess resize*/
				
				$('#moneygame').css('zoom',pageInfo.zooma);
				
				$('.tip-shake').css('zoom',pageInfo.zooma);
				
				/*3D签到*/
				$('#bigCard').css('margin-left', (window.innerWidth - $('#bigCard').width()) / 2 + 'px');
				$('#bigCard').css('margin-top', (window.innerHeight - $('#bigCard').height()) / 2 + 'px');
			
				/*ship resize*/
				$('#shipGift')[0].style.zoom =  pageInfo.zooma;
				
				$('.gift-huajia')[0].style.zoom =  pageInfo.zooma;
				$('.gift-sansheng')[0].style.zoom =  pageInfo.zooma;
				
				/*chat gx*/
				document.getElementById("cw").style.zoom = pageInfo.zooma;
				
				/*bahe resize*/
				$('#mk-bahe').css('zoom',pageInfo.zooma);
				$('#mk-bahemain').css('zoom',pageInfo.zooma);
				
				/*countdown resize*/
//				$('.wrappercount').css('zoom',pageInfo.zooma);
				$('#mgamecountdown').css('zoom',pageInfo.zooma);
//				$('#mgamecountdown').css('left',685*pageInfo.zooma+'px');
				
				$('.red-warp').css('zoom',pageInfo.zooma);
				
				if(pageInfo.zoom<1||pageInfo.needResize){
					$('body').css('width','1280px');
					$('body').css('height','720px');
					$('body').css('zoom',pageInfo.zoom);
					$('.slider-item').css('height', '720px');
					
					$('body').css('margin-top',((window.innerHeight-720*pageInfo.zoom)/pageInfo.zoom)/2+'px');
				
					$('.signin3D').css('zoom','1');
					$('.module').css('height', '720px');
					
					/*游戏  resize*/
					$('.leda').css('margin-top','0px');
					//$('#erweima').css('margin-top','0px');
					
					$('#erweima').css('margin-top',(window.innerHeight - 140 - $('#erweima').height()) / 2);
					
					
					/*游戏  resize END*/
					
					/*摇一摇  resize*/
					$('.shakegame .content').css({
						"height" : '720px',
						"width" :'1280px',
						"background-color" : "rgb(248,217,156)"
					})

					$('.husband-warp').css({
						"left" : '1110px'
					})
					/*var _w = 1280 - $('.rank-warp li').width() * 10
					if (_w > 0) {
						$('.rank-warp li:first-child').css('margin-left', _w / 2 + 'px')
					}*/
					$('.rank-warp li:first-child').css('margin-left', '0px');
					/*摇一摇  resize*/
					
					/*抽奖 resize*/
					$('.luckdraw').css('zoom', '1');
					$('.lottery-result').css('zoom',1280/1920);
					/*抽奖 resize END*/
					
					/*guess resize*/
					$('.guess-warp').css('zoom', 1280/1920);
					/*guess resize*/
					$('#moneygame').css('zoom', 1280/1920);
					
					$('.tip-shake').css('zoom',1280/1920);
					
					document.getElementById("cw").style.zoom = '0.63';
					
					$('#cw').css({
						'zoom':'0.63',
						//'top':'100px'
					})
					
					/*ship resize*/
					$('#shipGift')[0].style.zoom =  1280/1920;
					
					$('.gift-huajia')[0].style.zoom =  1280/1920;
					$('.gift-sansheng')[0].style.zoom = 1280/1920;
					
					
					/*bahe resize*/
					$('#mk-bahe').css('zoom',1280/1920);
					$('#mk-bahemain').css('zoom',1280/1920);
					
					/*countdown resize*/
					$('#mgamecountdown').css('zoom',1280/1920);
//					$('#mgamecountdown').css('left',685*(1280/1920)+'px');
					
					/*red-warp resize*/
					$('.red-warp').css('zoom',1280/1920);
				}
			},50)	
		}
		
		//window.addEventListener('resize', resizeHtml);
		
		
		var erweimaLayer;
		
		function erweima() {
			if(erweimaLayer){
				layer.close(erweimaLayer);
				erweimaLayer = null;
			}else{
				//页面层
				var _offset = 'r';
//				if(pageInfo.needResize){
//					
//					_offset = ["300px",1280+"px"]
//				}else{
//					_offset="r"
//				}
				
				erweimaLayer=layer.open({
					type : 1,
					title : '',
					
					area: [400 * pageInfo.scale + 'px', 400 * pageInfo.scale + 'px'], //宽高
					content : '<img src="'+pageInfo.qrcodeUrl+'" style="height:100%;width:100%"/>',
					shade : 0,
					move : '.layui-layer-content',
					offset: _offset,
					end:function(){erweimaLayer=null}
				});
				
//				if(pageInfo.zoom<1||pageInfo.needResize){
//					layer.style(erweimaLayer,{
//						zoom:pageInfo.zoom
//					});
//				}
			}
		}

		layer.ready(function() {
			layer.msg('欢迎来到嗨动平台');
		});
		
		
		
		var gift_layer =null;
		$('#choujiangliwu').on('click',function(){
		
			 gift_layer=layer.open({
				type: 1,
				title: '',
//		   		skin: 'layui-layer-rim', //加上边框
				area: ['350px', '350px'], //宽高
				content:"<img src="+pageInfo.path+"static/resource/img/prize.png />",
				resize: 'false',
				shadeClose: 'true',
			});
			
			layer.style(gift_layer, {
				background: 'rgba(0,0,0,0.5)',
				border: 'none'
			});
			
		});	
		
		var networkInterval = setInterval(function(){
			if(!navigator.onLine){
				layer.alert('网络已经断开,请刷新页面', {
					  title:'警告',
					  icon: 2,
					  skin: 'layer-ext-moon' //该皮肤由layer.seaning.com友情扩展。关于皮肤的扩展规则，去这里查阅
				})
			}
		},20000);
		
	    
		function _resize() {

			  setTimeout(function () {
			    var windowH = window.innerHeight;
			    var windowW = window.innerWidth;
			    document.querySelector('body').style.height = windowH + 'px';
			    document.querySelector('body').style.width = windowW + 'px';
			 

			    //base 1920 1080

			    var needScale1 = windowH / 1080;
			    var needScale2 = windowW / 1920;
			   
			    if (needScale1 < needScale2) {
			      document.getElementById("hdApp").style.transform = "scale(" + needScale1 + ") translateX(" + ((windowW - 1920 * needScale1) / 2) / needScale1 + "px)";
			      pageInfo.scale = needScale1;
			      pageInfo.x = ((windowW - 1920 * needScale1) / 2) / needScale1 ;
			    } else {
			      document.getElementById("hdApp").style.transform = "scale(" + needScale2 + ") translateY(" + ((windowH - 1080 * needScale2) / 2) / needScale2 + "px)";
			      pageInfo.scale = needScale2;
			      pageInfo.y = ((windowH - 1080 * needScale2) / 2) / needScale2 ;
			    }
			    
				$('.shakegame .content').css({
					"height" : '1080px',
					"width" : '1920pxpx',
					"background-color" : "rgb(248,217,156)"
				})

				$('.husband-warp').css({
					"left" : '1110px'
				})
				
				
			
				var _w = 1920 - $('.rank-warp li').width() * 10
				if (_w > 0) {
					$('.rank-warp li:first-child')
					.css('margin-left', _w / 2 + 'px')
				}
			    
//			    $('.leda').css('margin-top',(window.innerHeight - 140 - $('.leda').height())/ 2 + 'px');
//								//$('#erweima').css('margin-top',(window.innerHeight - 140 - $('.leda').height()) / 2+ $('.game-wait-people').height() + 'px');
//				$('#erweima').css('margin-top',(window.innerHeight - 140 - $('#erweima').height()) / 2);

			  });
			}

			window.onresize = _resize;
		
			_resize();