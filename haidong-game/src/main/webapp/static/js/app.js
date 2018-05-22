//登陆验证API服务器IP
//var apiUrl = "http://4ac90be8.ngrok.natapp.cn/haidong-api/";  
//var apiUrl = "http://192.168.1.92:8080/haidong-api/";
//var apiUrl = "http://192.168.1.121:8080/haidong-api/"; 
//var chatHost = "192.168.1.92:8080/haidong-chat";

var apiUrl = "http://api.hidongtv.com/";
var chatHost = "121.40.194.154:8081/haidong-chat";



function GetQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r != null) return unescape(r[2]);
	return null;
}

function AJAXbyJsonP(url, postdata, callback, errorcallback) {
	$.ajax({
		url: apiUrl + url,
		type: "get",
		dataType: "jsonp",
		jsonp: "callback",
		jsonpCallback: "success_jsonpCallback",
		data: postdata,
		success: function(data){
			callback(data);
		},
		error: function(xhr, type, errorThrown) {
//			var data = JSON.parse(xhr.response);
//			javascript: playgameObj.toast(data.message);
//			if(errorcallback!=null){
//				errorcallback(xhr, type, errorThrown);
//			}
			
		}
	});
}

function cutString(strlength,content){
	if(strlength == null||content == null){
		return '';
	}
	
	if(content.length>strlength){
		return content.substr(0,strlength)+'..';
	}else{
		return content;
	}
}
