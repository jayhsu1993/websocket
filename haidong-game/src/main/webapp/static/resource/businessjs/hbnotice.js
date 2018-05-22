var hbNotice = {};
hbNotice.status = false;
hbNotice.queue = [];


observe(hbNotice, function(name, value, old) {
	if(name == 'Array-unshift') {
		if(!hbNotice.status && hbNotice.queue.length > 0) {
			startRed()
		}
	}
})

function startRed(){
	if(hbNotice.queue.length <= 0) {
		hbNotice.status = false;
		return;
	} else {
		hbNotice.status = true;
	}
	var c = hbNotice.queue.pop();
	
	
	$('.red-name').html(c.username);
	$('.red-content').html('抽中'+c.money+'元现金红包')
	
	$('.red-notice').addClass('zoomInDown');
	$('.red-notice').css('opacity','1');
	
	
	setTimeout(function(){
		$('.red-notice').removeClass('zoomInDown');
		$('.red-notice').css('opacity','0');
		setTimeout(startRed,500)
	},9000)
}
