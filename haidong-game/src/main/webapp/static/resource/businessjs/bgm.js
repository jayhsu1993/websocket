//背景音乐
var normalbgm = document.createElement("audio");

// 音效
var soundef = document.createElement("audio");

// 特殊背景音乐
var specialbgm = document.createElement("audio");


var bgmList =['bgm/01- - My Love.mp3'];

var MUSIC_DRAW = pageInfo.path + '/static/resource/soundef/prizenew.mp3';
var MUSIC_SHAKE = pageInfo.path + '/static/resource/soundef/bgm_shake.mp3';
var MUSIC_VOTE = pageInfo.path + '/static/resource/soundef/bgm_vote.mp3';
var MUSIC_GUESS = pageInfo.path + '/static/resource/soundef/guess.mp3';
var MUSIC_MONEY_1 =  pageInfo.path + '/static/resource/soundef/money1.mp3';
var MUSIC_MONEY_2 =  pageInfo.path + '/static/resource/soundef/money3.mp3';
var MUSIC_HUADENG =  pageInfo.path + '/static/resource/soundef/huadeng.mp3';
var MUSIC_FENGCAI = pageInfo.path + '/static/resource/soundef/fengcai.mp3';


for (var i = 0; i < bgmList.length; i++) {
	bgmList[i] = pageInfo.path + "/static/resource/" + bgmList[i];
}

// 获取背景音乐列表

var soundefList = [ "soundef/bgm_draw.mp3", "soundef/bgm_shake.mp3",
		"soundef/bgm_vote.mp3" ];

var testmsList = [ "testms/Button32.wav", "testms/click_01.wav",
		"testms/giftcome.mp3" ]

var bgm_current = 0;

//normalbgm.src = bgmList[bgm_current];
//normalbgm.play();

// setTimeout('normalbgm.pause()',3000);

normalbgm.addEventListener('ended', function() {
	if (bgm_current < bgmList.length - 1) {
		bgm_current++;
	} else {
		bgm_current = 0;
	}
	normalbgm.src = bgmList[bgm_current];
	normalbgm.play();
})


function pauseSpecialbgm(){
	specialbgm.pause();
}

//
function playSpecialbgm(src) {
	if (!src) {
		// changeVolume(bgm,1,'up');
		specialbgm.pause();
		normalbgm.play();
	
	} else {
		normalbgm.pause();
		// changeVolume(bgm,0.1,'down');
		specialbgm.src = src;
		specialbgm.loop = 'loop';
		specialbgm.play();
	}

}

function changeVolume(v, dest, isDown) {
	setTimeout(function() {
		if (isDown == 'up') {
			if (v.volume >= dest) {
				v.volume -= 0.02;
				changeVolume(v, dest, isDown);
			}
		} else if (isDown == 'down') {
			if (v.volume <= dest) {
				v.volume += 0.02;
				changeVolume(v, dest, isDown);
			}
		}
	}, 50)
}

function toogleMusic(obj) {
	if (normalbgm.paused && !specialbgm.paused) {
		specialbgm.pause();
		obj.dataset.current = '1';
		obj.children[0].src = pageInfo.path
				+ '/static/resource/img/index/yinyueclose.png';
	} else if (!normalbgm.paused && specialbgm.paused) {
		normalbgm.pause();
		obj.dataset.current = '0';
		obj.children[0].src = pageInfo.path
				+ '/static/resource/img/index/yinyueclose.png';
	} else if (normalbgm.paused && specialbgm.paused) {
		obj.children[0].src = pageInfo.path
				+ '/static/resource/img/index/yingyue.png';
		if (obj.getAttribute('data-current') == '0') {
			normalbgm.play();
		} else {
			specialbgm.play();
		}
	}
}

/*
 * var soundModule = { audioPlayer: {}, bgmList: [], specialbgmList: [],
 * current: 0,
 * 
 * init: function(obj) { var that = this;
 * 
 * that.current = 0; //背景音乐 var a = document.createElement("audio"); //音效 var b =
 * document.createElement("audio"); //特殊背景音乐 var c =
 * document.createElement("audio");
 * 
 * audioPlayer.bgm = { 'player': a, 'loop': true }; audioPlayer.specialbgm = {
 * 'player': b }; a.src = bgmList; a.addEventListener('ended', function() {
 * if(soundModule.current < soundModule.bgmList.length - 1) {
 * soundModule.current++; this.src = soundModule.bgmList[soundModule.current];
 * this.play(); } else { soundModule.current = 0; this.src =
 * soundModule.bgmList[soundModule.current]; this.play(); } });
 * specialbgmList.push('soundef/bgm_draw.mp3');
 * specialbgmList.push('soundef/bgm_shake.mp3');
 * specialbgmList.push('soundef/bgm_vote.mp3'); } }
 */