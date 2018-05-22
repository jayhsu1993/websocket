  /* INIT VIDEO */
  //This is the block size
  var tam = 300;
  
  //Container
  var videoPreloader =  $('#preloader-canvas');

  function img() {
    var c = document.createElement("canvas");
    c.width = tam;
    c.height = tam;
    var o = c.getContext("2d");
    //Change your pixel size and shape, tam should leave no remainder when divided by either
    var pixelWidth = 1;
    var pixelHeight = 1;

    // Draw rectangles is faster than modify ImageData
    for(var y = 0; y < tam; y+=pixelHeight) {
      for(var x = 0; x < tam; x+=pixelWidth) {
        o.fillStyle = Math.random() < .5 ? "#dfdfdf" : "#000";
        o.fillRect(x, y, pixelWidth, pixelHeight);
      }
    }
    
    var img = c.toDataURL();
    
    videoPreloader.css('background-image', 'url(\'' + img + '\')'); 
    mexer();
    
  }

  function mexer() {
    videoPreloader.css('background-position', Math.floor(Math.random() * tam) + "px " + Math.floor(Math.random() * tam) + "px"); 
    if(typeof webkitRequestAnimationFrame == 'function'){
      webkitRequestAnimationFrame(mexer);
    } else if (typeof mozRequestAnimationFrame == 'function') {
      mozRequestAnimationFrame(mexer);
    } else if (typeof requestAnimationFrame == 'function') {
      requestAnimationFrame(mexer);
    }
  }

  //img();
  



  var  videoModule ={
  	init:function(){
  		$('.wish-video-warp').css({'height':"0px",'padding':'0','top':'324px'});
  		$('.video-line-v').css({'height':'0px'});
  		$('.video-line-h').css({'width':'0px'});
  		$('.video-line-h1').css({'width':'0px'});
  		$('.zuobiao').css({'opacity':'0'});
  		$('.wish-video-name1').css({'padding':'0','width':'0','height':'0','top':'350px'});
  		$('.wish-video-name').css({'padding':'0','width':'0','height':'0','top':'594px'})
  	},
  	
  	startAni:function(videourl,addr,user){
  		$('#vvv').append('<source src="'+videourl+'" type="video/mp4"></source>');
  		$(".wish-video-warp").animate({ height: "360.5px", padding: "20px", top: "144px" }, 300, "linear", function() {
  					//					img();
  					//					setTimeout(function() {
  					//						$('#vvv').show();
  					//						$('#preloader-canvas').show();
  					//						$('#lignt').addClass('lightmove');
  					//						$('#preloader-canvas').addClass('lightmove');
  					//					}, 500)
  					$('.slider-item').addClass('dim')
  					//$('#vvv').show();
  					//$('#preloader-canvas').show();

  					setTimeout(function() {
  						//						$('#lignt').show();
  						//						$('#lignt').addClass('lightmove');
  						$('#preloader-canvas').addClass('lightmove');
  					}, 500)

  					$(".video-line-v").animate({
  						height: "88px"
  					}, 100, "linear", function() {
  						$(".video-line-h").animate({
  							width: "80px"
  						}, 100, "linear", function() {
  							$("#videoname").animate({ width: "350px", borderWidth: "3px" }, 500, "linear", function() {
  								$("#videoname").animate({ height: "48px", top: "576px" }, 100, "linear", function() {
  									Typed.new('#videoname', {
  										strings: [user+"发来了一段祝福视频"],
  										typeSpeed: 2
  									});

  								});
  							});
  						})

  						$(".video-line-h1").animate({
  							width: "115px"
  						}, 500, "linear", function() {
  							$(".wish-video-name1").animate({ width: "200px", borderWidth: "3px" }, 100, "linear", function() {
  								$(".wish-video-name1").animate({ height: "48px", top: "328px" }, 100, "linear", function() {

  									$(".zuobiao").animate({ opacity: "1" }, 300, "swing", function() {})

  									Typed.new('.wish-video-name1', {
  										strings: ["From "+addr],
  										typeSpeed: 2
  									});
  								});
  							});
  						})

  					})
  				});
  			
  	}
  	
  }

 
 $('#flayvideo').on('click',function(){
	$('#vvv').show();
	$('#videosnow').show();
	$('#videosnow').addClass('lightmove');
	$('#lignt').show();
	$('#lignt').addClass('lightmove');
	$('#flayvideo').hide();
	 
 })



