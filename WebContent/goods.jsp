<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	pageContext.setAttribute("APP_PATH", request.getContextPath());
%>
<%@ include file = "./easyuisupport.jsp" %>  

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="${base}">

<title>货物管理</title>

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
        <a name="add" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true">进货</a>
        <a name="update" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" >更新库存</a>
        <a name="delete" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除货物</a>
        </div>
        <div>
            查询货物：<input id="keyword" type="text" name="name" style="width:150px"/>
<!--             <select id="order" class="easyui-combobox" panelHeight="auto" style="width:100px"> -->
<!--                 <option value="asc">升序</option> -->
<!--                 <option value="desc">降序</option> -->
<!--             </select> -->
            <a  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" οnclick="obj.search()">查询</a>
        </div>
    </div>
 
    <%-- 弹窗1  Start--%>
        <div id="dlg" class="easyui-dialog" style="width:420px;height:595px;padding:10px 10px"
            closed="true" buttons="#dlg-buttons">
        <div class="ftitle" style="margin-bottom:5px">货物信息</div>
	        <form id="ff" method="post" >
        		<div style="margin-bottom:10px;">
	                <input id="id_input" class="easyui-textbox" name="id" style="width:100%" data-options="label:'货物id'">
	            </div>
	            <div style="margin-bottom:10px; mrgin-top:10px;">
	                <input id="name_input" class="easyui-textbox" name="name" style="width:100%" data-options="label:'货物名'">
	            </div>
	            <div style="margin-bottom:10px; mrgin-top:10px;">
	                <input class="easyui-textbox" name="goodsType" style="width:100%" data-options="label:'类型'">
	            </div>
        	    <div style="margin-bottom:10px; mrgin-top:10px;">
	                <input class="easyui-textbox" name="price" style="width:100%" data-options="label:'单价'">
	            </div>
	            <div style="margin-bottom:10px; mrgin-top:10px;">
	                <input class="easyui-datetimebox" name="sDate" style="width:100%" data-options="label:'促销开始',showSeconds:false">
	            </div>
	            <div style="margin-bottom:10px; mrgin-top:10px;">
	                <input class="easyui-datetimebox" name="eDate" style="width:100%" data-options="label:'促销结束',showSeconds:false">
	            </div>
                <div style="margin-bottom:10px; mrgin-top:10px;">
	                <input class="easyui-textbox" name="offPrice" style="width:100%" data-options="label:'促销单价'">
	            </div>
                <div style="margin-bottom:10px; mrgin-top:10px;">
	                <input class="easyui-textbox" name="cNum" style="width:100%" data-options="label:'当前数量',">
	            </div>
                <div style="margin-bottom:10px; mrgin-top:10px;">
	                <input class="easyui-textbox" name="wNum" style="width:100%" data-options="label:'预警数量'">
	            </div>
	            
                <div style="margin-bottom:10px; mrgin-top:10px;">
	                <input class="easyui-textbox" name="pId" style="width:100%" data-options="label:'供货商id'">
	            </div>
				<div style="margin-bottom:10px; mrgin-top:10px;">
	                <input class="easyui-textbox" name="fId" style="width:100%" data-options="label:'生产厂商id'">
	            </div>
	        </form>
       		 	<a name="dlg-ok" href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" οnclick="saveEqument()" style="width:90px;margin-left:80px;">确认</a>
       		 	<a name="dlg-cancel" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" οnclick="javascript:$('#dlg').dialog('close')" style="width:90px;margin-left:40px;">取消</a>
  		</div>

    <%-- 弹窗 1 End --%>

    <%-- 弹窗2  Start--%>
        <div id="dlg2" class="easyui-dialog" style="width:420px;height:430px;padding:10px 10px"
            closed="true" buttons="#dlg-buttons">
        <div class="ftitle" style="margin-bottom:5px">货物信息</div>
	        <form id="ff2" method="post" >
	            <div style="margin-bottom:10px;">
	                <input class="easyui-textbox" name="name" style="width:100%" data-options="label:'货物名',required:true">
	            </div>
	            <div style="margin-bottom:10px; mrgin-top:10px;">
	                <input class="easyui-textbox" name="goodsType" style="width:100%" data-options="label:'类型',required:true">
	            </div>
        	    <div style="margin-bottom:10px; mrgin-top:10px;">
	                <input class="easyui-textbox" name="price" style="width:100%" data-options="label:'单价',required:true">
	            </div>
                <div style="margin-bottom:10px; mrgin-top:10px;">
	                <input class="easyui-textbox" name="cNum" style="width:100%" data-options="label:'进货数量',required:true">
	            </div>
                <div style="margin-bottom:10px; mrgin-top:10px;">
	                <input class="easyui-textbox" name="wNum" style="width:100%" data-options="label:'预警数量',required:true">
	            </div>
	            
                <div style="margin-bottom:10px; mrgin-top:10px;">
	                <input class="easyui-textbox" name="pId" style="width:100%" data-options="label:'供货商id',required:true">
	            </div>
				<div style="margin-bottom:10px; mrgin-top:10px;">
	                <input class="easyui-textbox" name="fId" style="width:100%" data-options="label:'生产厂商id',required:true">
	            </div>
	        </form>
       		 	<a name="dlg-ok2" href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" οnclick="saveEqument()" style="width:90px;margin-left:80px;">确认</a>
       		 	<a name="dlg-cancel2" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" οnclick="javascript:$('#dlg').dialog('close')" style="width:90px;margin-left:40px;">取消</a>
  		</div>

    <%-- 弹窗 2 End --%>

</body>

<script>
$("#id_input").attr("readonly",true);
$(document).ready(function() {

	$("a[name='reload']").click(function(){
		reload();
	});
	$("a[name='update']").click(function(){
		update();
	});
	$("a[name='delete']").click(function(){
		deleteGoods();
	});
	$("a[name='add']").click(function(){
		addGoods();
	});
	$("a[name='dlg-ok']").click(function(){
	    var params = $('#ff').serialize(); //序列化表单的值
	    console.log(params);
		  $.ajax({
		        type: "post",
		        url: "GoodsServlet?type=update",
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
	
	$("a[name='dlg-ok2']").click(function(){
	    var params = $('#ff2').serialize(); //序列化表单的值
		  $.ajax({
		        type: "post",
		        url: "GoodsServlet?type=add",
		 	    dataType: "json",
		            data: params,
		        success: function(data){
		        	console.log(data);
		        	if(data["message"] == "添加成功~"){
		        		$.messager.alert('提示','添加成功！','info',function(){
		        			$('#dlg2').dialog('close');  
		        			reload();
		        		});
		       		 }
		        	else if(data["message"] == "该货物已存在，请更新~"){
		        		$.messager.alert('提示','该货物已存在，请更新~','warning',function(){
		        			$('#dlg2').dialog('close');
		        			reload();
		        		});
		        	}
		        	},
		        error: function(data){
		        	console.log(data);
		        	$.messager.alert('提示','添加失败，请填写完整的表单','error');
		        }
		    });
	});
	$("a[name='dlg-cancel2']").click(function(){
 		$('#dlg2').dialog('close');
	});
});

$(function () {
    $('#tb1').datagrid({
    	url: 'GoodsServlet?type=findAll',
        width: 1000,
        title: '货物管理',
        method: 'get',
        toolbar: '#tb',
        striped: 'true',
        singleSelect:'true',
        fit: 'true',
        iconCls : 'icon-search',
        remoteSort: false,
        columns: [[
            { field: 'id', title: '货物id', width: 100,sortable:true,align:'center'},
            { field: 'name', title: '货物名', width: 100,align:'center'},
            { field: 'type', title: '类型', width: 100,align:'center'},
            { field: 'price', title: '单价(元)', width: 100,sortable:true,align:'center' },
            { field: 'cNum', title: '当前数量', width: 100, styler:cellStyler,sortable:true,align:'center' },
            { field: 'wNum', title: '预警数量', width: 100,sortable:true,align:'center' },
            { field: 'offPrice', title: '促销单价(元)', width: 100,align:'center' },
            { field: 'sDate', title: '促销开始日期', width: 100,align:'center' },
            { field: 'eDate', title: '促销结束日期', width: 100,align:'center' },
            { field: 'providerId', title: '供货商id', width: 100,align:'center' },
            { field: 'factoryId', title: '生产厂商id', width: 100,align:'center' },
        ]],
        idField : 'id',
        pagination: true,
        pageSize: 15,
        pageList: [10, 15, 20, 25],
    })
})

function cellStyler(value,row,index){
            if (value < row.wNum && value != 0){
                return 'background-color:#ffee00;color:red;';
            }
            else if(value == 0){
            	return 'background-color:red';
            }
}

function reload() {
    document.getElementById("keyword").value = "";
    $('#tb1').datagrid('reload');
}
function update(){
	 var row = $('#tb1').datagrid('getSelected');
	    if (row) {
	        $('#dlg').dialog('open').dialog('center').dialog('setTitle', 'tip');
	        $('#ff').form('load', {
	        	id : row.id,
	            name: row.name,
	            goodsType : row.type,
	            price : row.price,
	            offPrice : row.offPrice,
	            cNum : row.cNum,
	            wNum : row.wNum,
	            pId : row.providerId,
	            fId : row.factoryId,
	            sDate: row.sDate,
	            eDate: row.eDate
	        });
	    }
}

function addGoods(){
	 $('#dlg2').dialog('open').dialog('center').dialog('setTitle', '进货');
}

function deleteGoods(){
	 var row = $('#tb1').datagrid('getSelected');
	    if (row) {
	        $.messager.confirm('Confirm', '确定彻底删除该货物吗?', function (r) {
	            if (r) {
	                $.post('GoodsServlet?type=delete', { id: row.id }, function (result) {
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




</script>

</html>
