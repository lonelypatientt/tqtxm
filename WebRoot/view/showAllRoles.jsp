<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'showAllUsers.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>easyui/themes/icon.css">
	<script type="text/javascript" src="<%=basePath%>view/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript">
		$(function($) {
			 	$('#dg').datagrid({  
	   			url:'loadAllRoles.do?methodName=loadAllRoles',
	   		    frozenColumns:[[{field:'hhh',checkbox:true}]],   
		    	columns:[[    
			       	{field:'rname',title:'角色名称',width:100}  
			    ]],
			    toolbar: [{
					text:'修改角色菜单',
					iconCls: 'icon-modify',
					handler: function(){
						var selection = $('#dg').datagrid('getSelections');
						if(selection.length<=0){
							alert("没有选中行！");
							return;
						}
						if(selection.length>1){
							alert("一次只能修改一个角色菜单！");
							return;
						}
						var rid=selection[0].rid;
						
						
					//ajax向后台提交数据 去执行查询当前角色的菜单资料   <c:if test="${ml[3]==1}">checked</c:if>  +(data[i].num!=0?'checked':'')+
						$('#modify').window('open');
						$.post("loadRoleMenus.do?methodName=loadRoleMenus", { rid:rid },
							   function(data){
							   		$("input").remove();
							   		$("br").remove();
							   		$(".choice").empty();
							   		for(var i in data){
							   		$('#modify').append('<input type="checkbox"  '+(data[i].num!=0?'checked':'')+ '   name="menuName"  value='+data[i].mid+'>'+'<span class="choice">'+data[i].mname+'</span><br/>');
							   		}
							   	}
							   , "json");
					}
				},'-',{
					text:'刷新',
					iconCls: 'icon-refresh',
					handler: function(){
						$('#dg').datagrid('reload');
					}
				}]
			})
			
			 //弹出修改菜单的那个窗口 
			$('#modify').window({    
			        width:600,    
			        height:400,    
			        modal:true,
			        title:'修改角色菜单' ,
			        closed:true,
			        top:20, 
			});
		
					
			
		});
	
		//执行修改角色菜单
		function executeModify() {
			
			var chenked=$("input[type='checkbox']:checked").val([]);
			var array = new Array();
			for(var i=0;i<chenked.length;i++){    //循环值
				array.push(chenked[i].value);
			 }
			var str = array.join(',');
			$.post("modifyRoleMenus.do?methodName=modifyRoleMenus", {
			 rid      :($('#dg').datagrid('getSelections'))[0].rid,
			 mids :str
		  },
		   function(){
		  		
		   		
		   		
	   			$('#modify').window('close');
    	        $('#dg').datagrid('reload');
    	        alert("修改成功！");  
		     	
		   }, "text");
		}
	</script>
  </head>
  
  <body>
   	  <table id="dg"></table> 
   	  <div id = "modify">
   	  	  <form id = "modifyRoleMenu"></form>
   	  	   <p style="text-align:center">
	    		<a id="btn" href="javascript:executeModify()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">修改</a>
    	  </p>
   	  </div>
  </body>
</html>
