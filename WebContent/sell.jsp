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
        <a name="analyse" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-tip" plain="true" >月销售统计</a>
        </div>

    </div>
 
    <%-- 弹窗  Start--%>
        <div id="dlg" class="easyui-dialog" style="width:420px;height:320px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons">
        <div class="ftitle" style="margin-bottom:20px">请输入待查询的年份和月份：</div>
	        <form id="ff" method="post" >
	            <div style="margin-bottom:10px">
	                <input  class="easyui-textbox" name="year" style="width:100%" data-options="label:'年份',prompt:'2020'">
	            </div>
	            <div style="margin-bottom:10px; mrgin-top:10px;">
	                <input class="easyui-textbox" name="month" style="width:100%" data-options="label:'月份',prompt:'1'">
	            </div>
				<hr>
                <div style="margin-bottom:10px; mrgin-top:10px;">
	                <input class="easyui-textbox" name="income" style="width:100%" data-options="label:'总收入(元)',readonly:'true'">
	            </div>

	        </form>
      		 	<a name="dlg-ok" href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" οnclick="saveEqument()" style="width:90px;margin-left:80px;">确认</a>
       		 	<a name="dlg-cancel" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" οnclick="javascript:$('#dlg').dialog('close')" style="width:90px;margin-left:40px;">取消</a>
  		</div>

    <%-- 弹窗  End --%>

</body>

<script>
$(document).ready(function() {

	$("a[name='reload']").click(function(){
		reload();
	});
	$("a[name='analyse']").click(function(){
    	 $('#dlg').dialog('open').dialog('center').dialog('setTitle', '统计');
	});
	$("a[name='dlg-cancel']").click(function(){
		$('#dlg').dialog('close');
	});
	$("a[name='dlg-ok']").click(function(){
		if($("input[name='year']").val() == "" || $("input[name='month']").val() == "" ){
			$.messager.alert('提示','请输入年月','info');
			return;
		}
		var total_money = 0;
		var start_date = $("input[name='year']").val()+"-";
		if(Number($("input[name='month']").val()) < 10){
			start_date += "0";
		}
		start_date += $("input[name='month']").val();
		var end_date = start_date + "-31 23:59:59";
		start_date += "-00 00:00:00";
		console.log(start_date);
		console.log(end_date);
		
		var rows = $("#tb1").datagrid("getRows");
		var rows_length = rows["length"];
		if(rows_length != 0){
			for(i = 0; i < rows_length; i++){
				if(rows[i].saleDate > start_date && rows[i].saleDate < end_date){
					total_money += Number(rows[i].salePrice);				
				}
			}
		}
		console.log(total_money);
        $('#ff').form('load', {
        	"income" : total_money
        });
	});
	
	
});

$(function () {
    $('#tb1').datagrid({
        url: 'SaleServlet?type=findAll',
        width: 1000,
        title: '销售记录',
        method: 'get',
        toolbar: '#tb',
        singleSelect:'true',
        striped: 'true',
        iconCls : 'icon-tip',
        rownumbers: 'true',
        remoteSort: false,
        columns: [[
            { field: 'sId', title: '销售单id', width: 200, align:'center' },
            { field: 'staffName', title: '操作员', width: 200,align:'center'},
            { field: 'salePrice', title: '金额', width: 200,sortable:true,align:'center' },
            { field: 'saleDate', title: '时间', width: 325,sortable:true,align:'center' },

        ]],
        idField: 'sId',
        pagination: true,
        pageSize: 10,
        pageList: [10, 15, 20, 25],
    })
})

function reload() {
    $('#tb1').datagrid('reload');
}



</script>

</html>
