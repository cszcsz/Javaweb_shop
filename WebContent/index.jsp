<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<!-- 每个页面引用下面这个文件 -->
<%@ include file = "./easyuisupport.jsp" %>  
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	pageContext.setAttribute("APP_PATH", request.getContextPath());
%>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires", "-1");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<base href="<%=basePath%>">

<title>登录页面</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<style>
#header {
	background-color: #B0B0B0;
	height: 10%;
	width: 100%;
	position: absolute;
	left: 0px;
	top: 0px;
	opacity: 0.5;
	text-align: center;
}

#frame {
	height: 200px;
	width: 360px;
	position: absolute;
	left: 10%;
	top: 55%;
	opacity: 0.8
}

.black_overlay {
	display: none;
	position: absolute;
	top: 0%;
	left: 0%;
	width: 100%;
	height: 100%;
	background-color: #000000;
	z-index: 1001;
	-moz-opacity: 0.8;
	opacity: 0.8;
	filter: alpha(opacity = 88);
}

.white_content {
	display: none;
	position: absolute;
	top: 40%;
	left: 40%;
	width: 20%;
	height: 20%;
	padding: 20px;
	border-radius: 10px;
	border: 2px solid #FFFFFF;
	background-color: #FFFFFF;
	z-index: 1002;
	overflow: auto;
}

div#bg {
	opacity: 0.8;
	position: fixed;
	top: 0;
	left: 0;
	height: 100%;
	width: 100%;
	z-index: -10;
	background-position: center 0;
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	-webkit-background-size: cover;
	-o-background-size: cover;
	opacity: 1;
	transition: opacity 1s linear;
	-moz-transition: opacity 1s linear;
	-webkit-transition: opacity 1s linear;
	-o-transition: opacity 1s linear;
}
</style>


</head>
<body>

	<div id="bg"></div>

	<div id="frame">
		<div class="easyui-panel" title="欢迎使用超市管理系统~" style="width:100%;max-width:400px;padding:30px 60px;border-radius: 5px;">
	        <form id="ff" method="post">
	            <div style="margin-bottom:10px">
	                <input class="easyui-textbox" name="name" style="width:100%" data-options="label:'用户名:',required:true">
	            </div>
	            <div style="margin-bottom:10px; mrgin-top:10px;">
	                <input class="easyui-textbox" name="password" style="width:100%" data-options="label:'密码:',required:true" type="password">
	            </div>
	            <div style="margin-bottom:10px; mrgin-top:10px;">
	                <select class="easyui-combobox" name="type" label="用户类型" style="width:100%" data-options="panelHeight:'auto'">
	                <option value="销售人员">销售人员</option>
	                <option value="库存人员">库存人员</option>
	                <option value="管理员">管理员</option>
	                </select>
	            </div>
	        </form>
	        <div style="text-align:center;padding:5px 10px">
	            <a class="easyui-linkbutton" onclick="login()" style="width:80px">登录</a>
	            <a href="UserServlet?type=reg" class="easyui-linkbutton"  style="width:80px">注册</a>
	        </div>
	    </div>
	</div>

</body>

<script>
	var bgDiv = document.getElementById("bg");
	bg_image = "source/login_bg.jpg";
	bgDiv.style.backgroundImage = "URL(" + bg_image + ")";

	function login(){
	    var params = $('#ff').serialize(); //序列化表单的值
	    console.log(params);
		  $.ajax({
		        type: "post",
		        url: "UserServlet?type=userLogin",
		 	    dataType: "json",
		            data: params,
		        success: function(data){
		        	console.log(data);
		        	if(data["message"] == "登录成功~"){
		        		$.messager.alert('提示','登录成功！','info',function(){
		        			localStorage.setItem("userName", $("input[name='name']").val());
 		        			window.location.href="main.jsp";	  
		        		});

		        	}
		        	else if(data["message"] == "不存在该用户~"){
		        		$.messager.alert('提示','该用户不存在,请先注册~','warning');
		        	}
		        	else{
		        		$.messager.alert('提示','密码错误~','error');
		        	}
		        },
		        error: function(data){
		        	console.log(data);
		        	$.messager.alert('提示','登录失败，服务器错误','error');
		        }
		    });
		}


</script>

</html>