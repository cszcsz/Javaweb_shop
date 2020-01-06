<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	pageContext.setAttribute("APP_PATH", request.getContextPath());
%>
<%@ include file="./easyuisupport.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="${base}">

<title>零售商店系统</title>

<link href="${APP_PATH}/static/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<style>
#welcome{
	background:url("source/welcome_bg.jpg");
	background-size:100% 100%;
}
</style>

</head>

<body class="easyui-layout" style="overflow-y: hidden" scroll="no" data-options="fit:true">

	<div id="LeftMenu" data-options="region:'west',split:true"
		style="width: 150px; overflow: auto; margin: 0px; padding: 0px">
	</div>


	<div data-options="region:'center',title:'零售超市管理系统'" style="padding: 5px; background: #eee;">
		<div id="mainTab" class="easyui-tabs"
			data-options="fit:true,onSelect:onTabSelect"
			style="width: 100%; height: 100%;">
		 	<div id="welcome" title="Welcome" style="padding:10px; ">
				<h2>欢迎使用零售超市管理系统~</h2>
				<p>by 蔡松志</p>
       		</div>
		</div>
	</div>
</body>

<script type="text/javascript">
	var data = [ {
		text : '功能导航',
		iconCls : 'icon-more',
		state : 'open',
		children : [ {
			text : '前台销售',
			url : 'front.jsp',
			iconCls : 'icon-edit'
		}, {
			text : '货物管理',
			url : 'goods.jsp',
			iconCls : 'icon-search'
		}, {
			text : '人员管理',
			url : 'staff.jsp',
			iconCls: 'icon-man'
		}, {
			text : '销售管理',
			url : 'sell.jsp',
			iconCls : 'icon-tip'
		} ]
	} ];

	$(function() {
		$('#LeftMenu').sidemenu({
			data : data,
			onSelect : onSideMenuSelect,
			border : false
		});

	});

	function toggle() {
		var opts = $('#sm').sidemenu('options');
		$('#sm').sidemenu(opts.collapsed ? 'expand' : 'collapse');
		opts = $('#sm').sidemenu('options');
		$('#sm').sidemenu('resize', {
			width : opts.collapsed ? 60 : 200
		})
	}

	function onSideMenuSelect(item) {
		console.log("url:" + item.url);
		if (!$('#mainTab').tabs('exists', item.text)) {
			$('#mainTab')
					.tabs(
							'add',
							{
								title : item.text,
								content : '<iframe scrolling="auto" frameborder="0"  src="'
										+ item.url
										+ '" style="width:100%;height:100%;"></iframe>',
								closable : true,
								icon : item.iconCls,
								id : item.id
							});
		} else {
			$('#mainTab').tabs('select', item.text);
		}
		addTabMenu();
	}

	function onTabSelect(title, index) {
		var tabs = $('#mainTab');
		var tab = tabs.tabs('getTab', index);
		var menus = $('#LeftMenu');
		if (menus.hasClass('sidemenu')) {
			var opts = menus.sidemenu("options");
			console.log(tab[0].id);
			changeMenuSelect(menus, opts, tab[0].id);
		}
	}

	function addTabMenu() {
		/* 双击关闭TAB选项卡 */
		$(".tabs-inner").dblclick(function() {
			var subtitle = $(this).children(".tabs-closable").text();
			$('#mainTab').tabs('close', subtitle);
		});
		/* 为选项卡绑定右键 */
		$(".tabs-inner").bind('contextmenu', function(e) {
			$('#tab_menu').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
			var subtitle = $(this).children(".tabs-closable").text();
			$('#tab_menu').data("currtab", subtitle);
			$('#mainTab').tabs('select', subtitle);
			return false;
		});
	}

	function changeMenuSelect(menus, opts, selectId) {
		var menutrees = menus.find(".sidemenu-tree");
		menutrees.each(function() {
			var menuItem = $(this);
			changeMenuStyle(menuItem, opts, selectId);
		});
		var tooltips = menus.find(".tooltip-f");
		tooltips.each(function() {
			var menuItem = $(this);
			var tip = menuItem.tooltip("tip");
			if (tip) {
				tip.find(".sidemenu-tree").each(function() {
					changeMenuStyle($(this), opts, selectId);
				});
				menuItem.tooltip("reposition");
			}
		});
	}

	function changeMenuStyle(menuItem, opts, selectId) {
		menuItem.find("div.tree-node-selected").removeClass(
				"tree-node-selected");
		var node = menuItem.tree("find", selectId);
		if (node) {
			$(node.target).addClass("tree-node-selected");
			opts.selectedItemId = node.id;
			menuItem.trigger("mouseleave.sidemenu");
		}
		changeMenuSelect(menuItem);
	}
</script>

</html>