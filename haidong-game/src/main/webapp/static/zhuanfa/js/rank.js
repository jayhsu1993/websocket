/*--------------------------------------------------------------------------------------------------

rank js  (For chrom)

version:1.0

create by :no one

email:no-email

website:http://hidongtv.com

date:2017-06-20

----------------------------------------------------------------------------------------------------*/

window.addEventListener('resize', resize);

function resize() {
	document.querySelector(".main-body").style.height = window.innerHeight + 'px';
	document.querySelector(".main-body").style.width = window.innerWidth + 'px';
	
	document.querySelector('.content').style.zoom =window.innerHeight/1080<window.innerWidth/1920?window.innerHeight/1080:window.innerWidth/1920;
	
}

resize();

var timer =null;

var path = 'http://game.hidongtv.com/';

var flag = false;

function getData() {
	$.get(path + 'game/wxShareResult', {roomId:'07d74890afc34a9a95af91cc8325ac92'}, function(data) {
		//获取微信转发次数
		if(data.code == 200) {

			var length = data.data.length;

			for(var i = 0; i < 21; i++) {
				if(i <= length - 1) {
					$('.rank-warp li')[i].children[0].src = data.data[i].headimgurl;
					$('.rank-warp li')[i].children[1].innerHTML = data.data[i].nickname;
					$('.rank-warp li')[i].children[2].innerHTML = data.data[i].score;
				} else {
					$('.rank-warp li')[i].children[0].src = 'img/head2.png';
					$('.rank-warp li')[i].children[1].innerHTML = '虚位以待';
					$('.rank-warp li')[i].children[2].innerHTML = '0';
				}
			}
		}
//		setTimeout(getData, 2000)
	}, 'json').error(function() {
//		setTimeout(getData, 2000)
	});
}

$('.btn').on('click', function(e) {
	if(flag) {
		flag = false;
		this.children[0].src = 'img/index1.png';
		clearInterval(timer);
		timer = null;
	} else {
		flag = true;
		this.children[0].src = 'img/index1-select.png';
		if(timer == null){
			timer=setInterval(getData,3000);
		}
	}
})

$('.btn').click();