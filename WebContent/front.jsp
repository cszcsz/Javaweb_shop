<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	pageContext.setAttribute("APP_PATH", request.getContextPath());
%>
<%@ include file = "./easyuisupport.jsp" %>  

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="${base}">

<title>前台</title>

<link href="${APP_PATH}/static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<style>

</style>

</head>

<body>
<div class="easyui-layout" style="width:100%;height:100%;">
     <div id="tb" style="padding:5px">
        <div>
                查询商品：<input id="keyword" type="text" name="name" style="width:150px; margin-right:140px;"/>
            <a name="query"  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
            <a name="delete" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" οnclick="obj.search()">移除</a>
             <a name="check"  href="javascript:void(0)"class="easyui-linkbutton" iconCls="icon-ok" οnclick="obj.search()">结算</a>
        </div>
    </div>
        
        
        <div data-options="region:'center',title:'收银台',iconCls:'icon-ok'">
            <table id="tb1" class="easyui-datagrid"
                    data-options="toolbar:'#tb',striped:'true',rownumbers:'true',border:false,singleSelect:true,fit:true,fitColumns:true">
                <thead>
                    <tr>
                        <th data-options="field:'id', title:'商品id', align:'center'" width="80"></th>
                        <th data-options="field:'name', title:'商品名称',align:'center'" width="120"></th>
                        <th data-options="field:'price', title:'单价', align:'center'" width="100"></th>
                        <th data-options="field:'nums', title:'购买数量', align:'center'" width="100"></th>
                    </tr>
                </thead>
            </table>

        </div>
        <div data-options="region:'east',split:true,iconCls:'icon-tip'" title="商品详情" style="width:350px; padding:25px">
        	<img id="goodsImg" alt="" src="source/shopcar.jpg" style="width:80%; height:30%;margin-bottom:20px;margin-left:30px;">
        	 <form id="ff" >
	        	 	<div style="margin-bottom:10px">
			            <input name="id" class="easyui-textbox" label="商品id" labelPosition="before" style="width:100%;" data-options="readonly:'true'">
			        </div>
			        <div style="margin-bottom:10px">
			            <input name="name" class="easyui-textbox" label="商品名" labelPosition="before" style="width:100%;" data-options="readonly:'true'">
			        </div>
			        <div style="margin-bottom:10px">
			            <input name="goodsType" class="easyui-textbox" label="类型" labelPosition="before" style="width:100%;" data-options="readonly:'true'">
			        </div>
			        <div style="margin-bottom:10px">
			            <input name="price" class="easyui-textbox" label="单价" labelPosition="before" style="width:100%;" data-options="readonly:'true'">
			        </div>
		      	    <div style="margin-bottom:10px">
			            <input name="cNum" class="easyui-textbox" label="库存量" labelPosition="before" style="width:100%;" data-options="readonly:'true'">
			        </div>
			        <hr>	
			        <div style="margin-bottom:15px;margin-top:20px;">
			            <input name="pNum" class="easyui-textbox" label="购买数量" labelPosition="before" style="width:100%;" data-options="prompt:'请输入数量...'">
			        </div>

        	 </form> 
	        <div>
	            <a name="addGoods" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" style="width:100%;height:32px;">加入购物车</a>
	        </div>
 	   </div>
        
       
    </div>
    
        <%-- 弹窗  Start--%>
        <div id="dlg" class="easyui-dialog" style="width:420px;height:390px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons">
        <div class="ftitle" style="margin-bottom:10px">交易清单</div>
	        <form id="ff2" method="post" >
	            <div style="margin-bottom:10px">
	                <input id="saleId" class="easyui-textbox" name="id" style="width:100%" data-options="label:'交易id',readonly:'true'">
	            </div>
	            <div style="margin-bottom:10px; mrgin-top:10px;">
	                <input class="easyui-textbox" name="staffName" style="width:100%" data-options="label:'操作员',readonly:'true'">
	            </div>
        	    <div style="margin-bottom:10px; mrgin-top:10px;">
	                <input class="easyui-textbox" name="date" style="width:100%" data-options="label:'交易时间',readonly:'true'">
	            </div>
                <div style="margin-bottom:10px; mrgin-top:10px;">
	                <input class="easyui-textbox" name="cost" style="width:100%" data-options="label:'总金额',readonly:'true'">
	            </div>
             	<img src="source/QRcode.png" style="width:20%; height:20%;position: relative; top:50%;left: 40%;"></img>
	           
	           
	        </form>
       		 	<a name="dlg-ok" href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" οnclick="saveEqument()" style="width:90px;margin-left:80px;">确认</a>
       		 	<a name="dlg-cancel" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" οnclick="javascript:$('#dlg').dialog('close')" style="width:90px;margin-left:40px;">取消</a>
  		</div>

    <%-- 弹窗  End --%>

</body>

<script>
$(document).ready(function() {

	$("a[name='query']").click(function(){
		query();
	});
	$("a[name='check']").click(function(){
		var total_cost = 0;
		var rows = $("#tb1").datagrid("getRows");
		var rows_length = rows["length"];
		if(rows_length != 0){
			var now = getFormatDate();
			for(i = 0; i < rows_length; i++){
				total_cost += Number(rows[i].price) * Number(rows[i].nums);
			}
			var saleId = random_No(5);
	        $('#dlg').dialog('open').dialog('center').dialog('setTitle', '交易清单');
	        $('#ff2').form('load', {
	            id: saleId,
	            staffName : localStorage.getItem("userName"),
	            date : now,
	            cost : total_cost.toString(),
	        });
		}
		else{
			$.messager.alert('提示','请选择至少一件商品~','infomation');
		}
	});
	$("a[name='delete']").click(function(){
		 var row = $('#tb1').datagrid('getSelected');
		    if (row) {
		    	var rowIndex = $('#tb1').datagrid('getRowIndex',row);
		    	$('#tb1').datagrid('deleteRow',rowIndex);
		    }
	});
	$("a[name='addGoods']").click(function(){
		if($("input[name='id']").val()!="" && $("input[name='pNum']").val()!=""){
			if(Number($("input[name='pNum']").val()) > Number($("input[name='cNum']").val())){
				$.messager.alert('提示','超过最大购买数量，库存不足~','infomation');
			}
			else if(Number($("input[name='pNum']").val() <= 0)){
				$.messager.alert('提示','请输入大于0的整数','infomation');
			}
			else{				
				$('#tb1').datagrid('appendRow',{
					id: $("input[name='id']").val(),
					name: $("input[name='name']").val(),
					price: $("input[name='price']").val(),
					nums : $("input[name='pNum']").val()
				});
			}
		}
		else{
			$.messager.alert('提示','请选择商品并输入购买数量~','infomation');
		}
	});
	
	$("a[name='dlg-ok']").click(function(){
	    var params = $('#ff2').serialize(); //序列化表单的值
	    console.log(params);
	      // ajax请求插入sale表
		  $.ajax({
		        type: "post",
		        url: "SaleServlet?type=insert",
		 	    dataType: "json",
		            data: params,
		        success: function(data){
		        	if(data['message'] == "交易成功~"){
		        		$.messager.alert('提示','交易成功！','info',function(){
		        			$('#dlg').dialog('close');  
		        		});		        		
		        		}
		        	},
		        error: function(data){
		        	console.log(data);
		        	$.messager.alert('提示','交易失败，后端错误','error');
		        	$('#dlg').dialog('close');  
		        }
		    });
	      // ajax请求更新goods表
	        var params = "goods="
			var rows = $("#tb1").datagrid("getRows");
			console.log(rows);
			params = params + JSON.stringify(rows);
			console.log(params);
			  $.ajax({
			        type: "post",
			        url: "SaleServlet?type=updateGoods",
			 	    dataType: "json",
			            data: params,
			        success: function(data){

			        	},
			        error: function(data){
			        	console.log(data);

			        }
			    });
			 // ajax请求记录销售详情
			 params = params + "&saleId=" + $("#saleId").val();
			 console.log(params);
			  $.ajax({
			        type: "post",
			        url: "SaleServlet?type=insertSaleDetail",
			 	    dataType: "json",
			            data: params,
			        success: function(data){

			        	},
			        error: function(data){
			        	console.log(data);

			        }
			    });
			  
	});
	$("a[name='dlg-cancel']").click(function(){
		$('#dlg').dialog('close');
	});
	
})

function query(){
	 var name = $('#keyword').val();
	 console.log(name);
	 console.log("GoodsServlet?type=query?name=" + name);
	    if (name != "") {
			  $.ajax({
			        type: "post",
			        url: "GoodsServlet?type=query&name=" + encodeURI(encodeURI(name)),
			        success: function(data){
			        	var data = JSON.parse(data);
			        	console.log(data);
			        	if(data["name"] != null){
				                $('#ff').form('load', {
				    	        	id : data["id"],
				    	            name: data["name"],
				    	            goodsType : data["goodsType"],
				    	            price : data["price"],
				    	            cNum : data["cNum"]
				    	        });
		                	  console.log(data["imgUrl"]);
				                if(data["imgUrl"]!=null){
			              			  $("#goodsImg").attr('src',data["imgUrl"]);
				                }
			       			 }
			        	else{
			        		$.messager.alert('提示','不存在该商品~','warning');
			        	}
			        	},
			        error: function(data){
			        	console.log(data);
			        	$.messager.alert('提示','添加失败，后端错误','error');
			        }
			    });
		    }
}


function getFormatDate(){
    var nowDate = new Date();
    var year = nowDate.getFullYear();
    var month = nowDate.getMonth() + 1 < 10 ? "0" + (nowDate.getMonth() + 1) : nowDate.getMonth() + 1;
    var date = nowDate.getDate() < 10 ? "0" + nowDate.getDate() : nowDate.getDate();
    var hour = nowDate.getHours()< 10 ? "0" + nowDate.getHours() : nowDate.getHours();
    var minute = nowDate.getMinutes()< 10 ? "0" + nowDate.getMinutes() : nowDate.getMinutes();
    var second = nowDate.getSeconds()< 10 ? "0" + nowDate.getSeconds() : nowDate.getSeconds();
    return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
}

function random_No(j) {
    var random_no = "";
    for (var i = 0; i < j; i++) //j位随机数，用以加在时间戳后面。
    {
        random_no += Math.floor(Math.random() * 10);
    }
    return random_no;
};

</script>

</html>
