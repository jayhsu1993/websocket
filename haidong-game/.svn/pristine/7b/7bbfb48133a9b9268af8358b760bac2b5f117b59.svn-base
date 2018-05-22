var UserInfo = {};

//清除登录信息
UserInfo.clear = function() {
	localStorage.clear();
}

//liveCode
UserInfo.liveCode = function() {
	return localStorage.getItem("liveCode");
}

//host
UserInfo.host = function() {
	return localStorage.getItem("host");
}

//roomId
UserInfo.roomId = function() {
	return localStorage.getItem("roomId");
}

//px
UserInfo.px = function() {
	return localStorage.getItem("px");
}



//roomId
UserInfo.saveInfo = function(livecode, host, roomid) {
	localStorage.setItem("liveCode", livecode);
	localStorage.setItem("host", host);
	localStorage.setItem("roomId", roomid);
}