<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	pageContext.setAttribute("APP_PATH", request.getContextPath());
%>
<%@ include file = "./easyuisupport.jsp" %>  

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="${base}">

<title>员工管理</title>

<link href="${APP_PATH}/static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<style>

</style>

</head>

<body>
  <table id="tb1""></table>
    <div id="tb" style="padding:5px">
        <div style="margin-bottom:5px">
        <a name="reload" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" >刷新</a>
<!--         <a name="add" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" οnclick="AddEqument()">添加</a> -->
        <a name="update" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" οnclick="editEqument()">修改</a>
        <a name="delete" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" οnclick="destroyEqument()">删除</a>
        </div>
        <div>
            查询员工：<input id="keyword" type="text" name="name" style="width:150px"/>
<!--             <select id="order" class="easyui-combobox" panelHeight="auto" style="width:100px"> -->
<!--                 <option value="asc">升序</option> -->
<!--                 <option value="desc">降序</option> -->
<!--             </select> -->
            <a name="keywordSearch" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
        </div>
    </div>
 
    <%-- 弹窗  Start--%>
        <div id="dlg" class="easyui-dialog" style="width:420px;height:360px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons">
        <div class="ftitle" style="margin-bottom:10px">修改员工信息</div>
	        <form id="ff" method="post" >
	            <div style="margin-bottom:10px">
	                <input id="name_input" class="easyui-textbox" name="name" style="width:100%" data-options="label:'姓名'">
	            </div>
	            <div style="margin-bottom:10px; mrgin-top:10px;">
	                <input class="easyui-textbox" name="sex" style="width:100%" data-options="label:'性别'">
	            </div>
        	    <div style="margin-bottom:10px; mrgin-top:10px;">
	                <input class="easyui-textbox" name="age" style="width:100%" data-options="label:'年龄'">
	            </div>
                <div style="margin-bottom:10px; mrgin-top:10px;">
	                <input class="easyui-textbox" name="level" style="width:100%" data-options="label:'级别'">
	            </div>
	            <div style="margin-bottom:0px; mrgin-top:10px;">
	                <select class="easyui-combobox" name="staffType" label="员工类型" style="width:100%" data-options="panelHeight:'auto'">
		                <option value="销售人员">销售人员</option>
		                <option value="库存人员">库存人员</option>
		                <option value="管理员">管理员</option>
	                </select>
	            </div>
	        </form>
       		 	<a name="dlg-ok" href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" οnclick="saveEqument()" style="width:90px;margin-left:80px;">确认</a>
       		 	<a name="dlg-cancel" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" οnclick="javascript:$('#dlg').dialog('close')" style="width:90px;margin-left:40px;">取消</a>
  		</div>

    <%-- 弹窗  End --%>


</body>

<script>
$("#name_input").attr("readonly",true);
$(document).ready(function() {
	
	$("a[name='reload']").click(function(){
		reload();
	});
	$("a[name='update']").click(function(){
		update();
	});
	$("a[name='delete']").click(function(){
		deleteUser();
	});
	$("a[name='keywordSearch']").click(function(){
		console.log($("#keyword").val());
		if($("#keyword").val()!=""){
			$('#tb1').datagrid('load',{
				name: $("#keyword").val()
			});			
		}
		else{
			$('#tb1').datagrid('load',{
				name: "all"
			});		
		}
	});
	$("a[name='dlg-ok']").click(function(){
	    var params = $('#ff').serialize(); //序列化表单的值
	    console.log(params);
		  $.ajax({
		        type: "post",
		        url: "UserServlet?type=update",
		 	    dataType: "json",
		            data: params,
		        success: function(data){
		        	console.log(data);
		        	if(data["message"] == "更新成功~"){
		        		$.messager.alert('提示','更新成功！','info',function(){
		        			$('#dlg').dialog('close');  
		        			reload();
		        		});
		       		 }
		        	else{
		        		$.messager.alert('提示','更新失败！','error',function(){
		        			$('#dlg').dialog('close');
		        			reload();
		        		});
		        	}
		        	},
		        error: function(data){
		        	console.log(data);
		        	$.messager.alert('提示','更新失败，后端错误','error');
		        }
		    });
	});
	$("a[name='dlg-cancel']").click(function(){
		$('#dlg').dialog('close');
	});
});

$(function () {
    $('#tb1').datagrid({
        url: 'UserServlet?type=findAll',
        width: 1000,
        title: '员工管理',
        method: 'get',
        toolbar: '#tb',
        singleSelect:'true',
        striped: 'true',
        iconCls : 'icon-man',
        rownumbers: 'true',
        remoteSort: false,
        columns: [[
            { field: 'id', title: '员工id', width: 100,sortable:true,align:'center' },
            { field: 'name', title: '姓名', width: 180,align:'center'},
            { field: 'sex', title: '性别', width: 150,align:'center' },
            { field: 'age', title: '年龄', width: 150,sortable:true,align:'center' },
            { field: 'workLevel', title: '级别', width: 150,sortable:true,align:'center' },
            { field: 'type', title: '员工类型', width: 200,sortable:true,align:'center' }
        ]],
        idField: 'id',
        pagination: true,
        pageSize: 15,
        pageList: [10, 15, 20, 25],
    })
})

function reload() {
    $('#tb1').datagrid('reload');
}
function update(){
	 var row = $('#tb1').datagrid('getSelected');
	    if (row) {
	        $('#dlg').dialog('open').dialog('center').dialog('setTitle', '对话框');
	        $('#ff').form('load', {
	            name: row.name,
	            sex : row.sex,
	            age : row.age,
	            level : row.workLevel,
	            type : row.type
	        });
	    }
}

function deleteUser(){
	 var row = $('#tb1').datagrid('getSelected');
	    if (row) {
	        $.messager.confirm('Confirm', '确定删除该人员吗?', function (r) {
	            if (r) {
	                $.post('UserServlet?type=delete', { id: row.id }, function (result) {
	                    if (result) {
	                    	console.log(result);
	                        $('#tb1').datagrid('reload');
	                    } else {
	                        $.messager.alert("提示", "删除失败", "error");
	                    }
	                });
	            }
	        });
	    }
}


 
$(function () {
    obj = {
        search: function () {
            $('#tb1').datagrid('load', {
                Equement: $('input[name="Eq"]').val(),
                order: $("#order").combobox('getValue')
            });
        }
    }
})


</script>

</html>
