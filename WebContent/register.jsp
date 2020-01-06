<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	pageContext.setAttribute("APP_PATH", request.getContextPath());
%>
<%@ include file = "./easyuisupport.jsp" %>  

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="${base}">

<title>用户注册</title>

<link href="${APP_PATH}/static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<style>
#signinBlock {
	background-color: #B0B0B0;
	height: 550px;
	width: 400px;
	border-radius: 10px;
	border: 5px solid #B0B0B0;
	position: absolute;
	left: 8%;
	top: 10%;
	opacity: 0.9;
}

body {
/* 	background: url("source/signinBG.jpg") no-repeat; */
	height: 100%;
	width: 100%;
	overflow: hidden;
	background-size: cover;
}
</style>

</head>

<body>

	<div style="width: 50%; margin: 0 auto">
		<h2>用户注册</h2>
		<form id="ff" method="post">
			<div class="form-group">
				<label for="exampleInputEmail1">姓名</label> <input name="name" type="text"
					class="form-control" id="inputName" placeholder="请输入姓名">
			</div>
			<div class="form-group">
				<label for="exampleInputEmail2">年龄</label> <input name="age" type="text"
					class="form-control" id="inputAge" placeholder="请输入年龄" >
			</div>
			<div class="form-group">
				<label>性别</label><br>
				<label class="radio-inline"> <input type="radio"
					name="sex" id="sexRadio1" value="男">
					男
				</label> <label class="radio-inline"> <input type="radio"
					name="sex" id="sexRadio2" value="女">
					女
				</label>
			</div>
			
			<div class="form-group">
				<label for="exampleInputPassword1">密码</label> <input name="password" type="password"
					class="form-control" id="inputPassword1" placeholder="Password">
			</div>

			<div class="form-group">
				<label for="exampleInputPassword2">确认密码</label> <input name="password2"
					type="password" class="form-control" id="inputPassword2"
					placeholder="确保两次密码输入一致">
			</div>
			<div class="form-group">
				<label>用户类型</label><br>
				<label class="radio-inline"> <input type="radio"
					name="staffType" id="typeRadio1" value="销售人员">
					销售人员
				</label> <label class="radio-inline"> <input type="radio"
					name="staffType" id="typeRadio2" value="库存人员">
					库存人员
				</label> <label class="radio-inline"> <input type="radio"
					name="staffType" id="typeRadio3" value="管理员">
					管理员
				</label>
			</div>

			<button id="submit" type="button" onclick="signUp()" class="btn btn-primary btn-lg" style="margin-top:10px">确认</button>
		</form>
	</div>
</body>

<script>
		function signUp(){
			if($("input[name='name']").val() == ""){
				 $.messager.alert('提示','姓名不能为空','info');
				 return;
			}
			if($("input[name='age']").val() == ""){
				 $.messager.alert('提示','年龄不能为空','info');
				 return;
			}
			var sex= $('input:radio[name="sex"]:checked').val();
			if(sex == null){
				 $.messager.alert('提示','性别不能为空','info');
				 return;
			}
			if($("input[name='password']").val() == ""){
				 $.messager.alert('提示','密码不能为空','info');
				 return;
			}
			if($("input[name='password2']").val() != $("input[name='password']").val()){
				 $.messager.alert('提示','两次密码不一致','info');
				 return;
			}
			var radioVal = $('input:radio[name="staffType"]:checked').val();
			if(radioVal==null){
				 $.messager.alert('提示','请选择用户类型','info');
				 return;
			}
			console.log(radioVal);
			
		    var params = $('#ff').serialize(); //序列化表单的值
		    console.log(params);
		    $.ajax({
		        type: "post",
		        url: "UserServlet?type=signup",
		 	    dataType: "json",
		            data: params,
		        success: function(data){
		        	console.log(data)
		            console.log(data["message"]);
		        	if(data["message"] == "注册成功~"){
		        		$.messager.alert('提示','注册成功！','info',function(){
		        			console.log("index.jsp");
		        			window.location.href="index.jsp";	  
		        		});
		        	}
		        	else if(data["message"] == "已存在该用户~"){
		        		$.messager.alert('提示','已存在该用户~','warning');
		        	}
		        	else{
		        		$.messager.alert('提示','注册失败~请检查年龄是否为数字类型','error');
		        	}
		        },
		        error: function(data){
		        	$.messager.alert('提示','注册失败~请检查年龄是否为数字类型','error');
		        }
		    });
		}

	</script>

</html>
