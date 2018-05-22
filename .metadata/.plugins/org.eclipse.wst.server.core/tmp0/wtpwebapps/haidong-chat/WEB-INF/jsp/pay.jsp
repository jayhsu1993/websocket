<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>充钱</title>
        <script src="js/mui.min.js"></script>
    	<script src="js/jquery-1.11.2.min.js"></script>
        <script type="text/javascript">
        	mui.init();
        	$(function($){
		        $('#sendpay').click(function(){
		        	var paym=$("#howM").val();
		        	alert(paym);
		        	function topay(){
						$.ajax({
							url:'http://localhost:8080/haidong-h5/money/g',
							type:'POST',
							datatype:'json',
							data:{money:umoney,openid:'111111'},
							success :function (result){  
							        alert(result);  
							    },error:function (){  
							        alert("failed!");  
								}
						});
				   	}
				});
			});
		</script>
    </head>
    <body>
    	<div id="paygold">
    		<input type="text" id="howM" name="howM" />
    		<input type="button"  id="sendpay" value="submit"/>
    		
    	</div>
 	</body>
</html>