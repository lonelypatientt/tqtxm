<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
	   			url:'loadAllDepartments.do?methodName=loadAllDepartments',
	   		    frozenColumns:[[{field:'hhh',checkbox:true}]],   
		    	columns:[[    
			        {field:'dname',title:'部门名字',width:100}, 
			       	{field:'departmentBossName',title:'部门老大名字',width:100},    
			        {field:'description',title:'部门描述',width:100},
			    ]],
			    toolbar: [{
					text:'新增部门',
					iconCls: 'icon-edit',
					handler: function(){
					$('#win').window("open")
					}
				},'-',{
					text:'删除部门',
					iconCls: 'icon-delete',
					handler: function(){
						var selection = $('#dg').datagrid('getSelections');
						if(selection.length<=0){
							alert("没有选中行");
							return;
						}
						//定义一个数组 存放选中的id
						var array = new Array();
						for(var i in selection){
							array.push(selection[i].did);
						}
						//将数组对象转换成字符串
						var str = array.join(',');
						//ajax向后台提交数据
						$.post('deleteDepartments.do?methodName=deleteDepartments',{dids:str},
								function(){
									alert("删除部门成功！");
									$('#dg').datagrid('reload');
								},
							'text')
						}
				}]
			})
			//弹出新增渠道的那个窗口 ---------------------------
			$('#win').window({    
			        width:600,    
			        height:400,    
			        modal:true,
			        title:'新增部门' ,
			        closed:true,
			        top:20, 
			});
			
				//加载所有部门老大的名字用于增加部门的时候选择老大的名字 用于下拉菜单
				
			$('#departmentBossName').combobox({    
			    url:'loadAllDepartmentBosses.do?methodName=loadAllDepartmentBosses',    
			    valueField:'uid',    
			    textField:'realName' 
			});
			
		});//-------------------------------------------------------------------加载
	//ajax异步提交数据 添加部门到数据库去
		function addDepartment(){
		$.post("addDepartment.do?methodName=addDepartment", {
			 dname      :$("#dname").val(),
			 bossUid  :$("#departmentBossName").combo('getValue'),
			 description :$("#description").val()
		  },
		   function(){
	   			$('#win').window('close');
    	        $('#dg').datagrid('reload');
    	        alert("添加部门成功！");  
		     	$("#addDepartment").form("reset");
		   }, "text");
		}
	</script>
  </head>
  
  <body>
   	  <table id="dg"></table> 
   	  <!--新增部门--> 
   	  <div id="win">
   	  	<form  id="addDepartment">
   	  		<table>
	    		<tr>
	    			<td style="width:130px">部门名称：</td>
	    			<td><input name="dname" class="easyui-validatebox" data-options="required:true" id="dname"/></td>
	    		</tr>
	    		<tr>
	    			<td>部门老大名字：</td>
	    			<td><input name="departmentBossName" class="easyui-validatebox" data-options="required:true" id="departmentBossName" value="请选择部门老大名字"/></td>
	    		</tr>
	    		<tr>
	    			<td>部门描述：</td>
	    			<td>
	    				<input name="description"  type= "text" class="easyui-validatebox" style="width:150px;" id="description"> 
	    			</td>
	    		</tr>
	    		<tr>
	    			<td ><a href="javascript:void(0);" onclick="addDepartment()" class="easyui-linkbutton" data-options="iconCls:'icon-undo'" style="margin-left: 40px;">添加</a> </td>
	    		</tr>
	    	</table>
   	  	</form>
   	  </div>
  </body>
</html>
