var signQ = {};
signQ.status = false;
signQ.queue = [];

var random3dArr = [];

//(function(arr) {
//	for(i = 0; i < 98; i++) {
//		arr.push(i)
//	}
//	arr.sort(function() { return 0.5 - Math.random() });
//})(random3dArr);

observe(signQ, function(name, value, old) {
	//	console.log(name + "__" + value + "__" + old);
	//	console.log(JSON.stringify(value))
	if(name == 'Array-unshift') {
		if(!signQ.status && signQ.queue.length > 0) {
			sign_usercome_3d();
		}
	}
})

function putuser_3d(data) {
	for(var i = 0; i < 98; i++) {
		random3dArr.push(i)
	}
	random3dArr.sort(function() { return 0.5 - Math.random() });

	for(var j = 0; j < data.length; j++) {
		if(random3dArr.length > 0) {
			var index = random3dArr.pop();
			$('.card').eq(index).find('img').attr('src',pageInfo.picHost+ data[j].reheadimgurl);
			if($('.card').hasClass('imgOut')) {
				$('.card').removeClass('imgOut').addClass('imgIn');
			}
		}
	}
}

function sign_usercome_3d() {
	if(signQ.queue.length <= 0) {
		signQ.status = false;
		return;
	} else {
		signQ.status = true;
	}
	console.log("start ani")
	var user = signQ.queue.pop();
	if(random3dArr.length <= 0) {
		
		for(var i = 0; i < 98; i++) {
			random3dArr.push(i)
		}
		
		random3dArr.sort(function() { return 0.5 - Math.random() });
	}
	var index = random3dArr.pop();
//	alert(index)
	console.log(index)
	$('.card').eq(index).find('img').attr('src',pageInfo.picHost+user.headimg);
	$('.card').removeClass('imgOut').addClass('imgIn');
	$('#bigCard').find('img').attr('src', user.headimg)
	$('#bigCard').find('span').html(user.username + '</br>' + user.wish);
	$('#bigCard').addClass('signani');
}

//document.getElementById("bigCard").addEventListener('webkitAnimationEnd', function() {
//	this.classList.remove('signani');
//	setTimeout(sign_usercome_3d, 1000);
//})

