var voteModule={
	currentVote:[],
	
	init:function(){
		//console.log(this);
		this.currentVote.length = 0;
		$('#vote-yes-people').empty();
		$('#vote-no-people').empty();
		$('#vote-yes-num').html('0');
		$('#vote-no-num').html('0');
		$('.vote-bar-mid.pink').css('height','30px');
		$('.vote-bar-mid.yellow').css('height','30px');
		
	},
	
	isEiset:function(userid){
		for(var i=0;i<this.currentVote.length;i++){
			if(userid ==this.currentVote[i] ){
				return true;
			}
		}
		this.currentVote.push(userid)
		return false;
	},
	
	
	resizeBar:function(isYes,img,name,userid){
		if(this.isEiset(userid)){
			return ;				
		}
		if(isYes =='yes'){
			$('.vote-bar-mid.pink').css('height',$('.vote-bar-mid.pink').height()+2 +'px');		
			$('#vote-yes-num').html( parseInt($('#vote-yes-num').html())+1);
			$('#vote-yes-people').append('<div class="yes-people"><img src="'+img+'" /><div>'+name+'</div></div>')
			
		}else if(isYes =='no'){
			$('.vote-bar-mid.yellow').css('height',$('.vote-bar-mid.yellow').height()+2 +'px');	
			$('#vote-no-num').html(parseInt($('#vote-no-num').html())+1);
			$('#vote-no-people').append('<div class="yes-people"><img src="'+img+'" /><div>'+name+'</div></div>')
		}
	}
};

$('#vote-rest').on('click',function(){
	voteModule.init();
})






