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
	   			url:'loadAllChannels.do?methodName=loadAllChannels',
	   		    frozenColumns:[[{field:'hhh',checkbox:true}]],   
		    	columns:[[    
			        {field:'ciname',title:'渠道名字',width:100}, 
			       	{field:'costmoney',title:'渠道投入成本金额',width:100},    
			        {field:'developtime',title:'渠道开发时间',width:100},
			    ]],
			    toolbar: [{
					text:'新增渠道',
					iconCls: 'icon-edit',
					handler: function(){
					$('#win').window("open")
					}
				},'-',{
					text:'删除渠道',
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
							array.push(selection[i].ciid);
						}
						//将数组对象转换成字符串
						var str = array.join(',');
						//ajax向后台提交数据
						$.post('deleteChannels.do?methodName=deleteChannels',{ciid:str},
								function(){
									alert("删除成功！");
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
			        title:'新增渠道' ,
			        closed:true,
			        top:20, 
			});
			
		
			
		});//-------------------------------------------------------------------加载
	//ajax异步提交数据 添加渠道到数据库去
		function addChannel(){
		$.post("addChannel.do?methodName=addChannel", {
			 ciname      :$("#ciname").val(),
			 costmoney   :$("#costmoney").val(),
			 developtime :$("#developtime").combo('getValue')
		  },
		   function(){
	   			$('#win').window('close');
    	        $('#dg').datagrid('reload');
    	        alert("添加渠道成功！");  
		     	$("#addChannel").form("reset");
		   }, "text");
		}
	</script>
  </head>
  
  <body>
   	  <table id="dg"></table> 
   	  <!--添加菜单--> 
   	  <div id="win">
   	  	<form  id="addChannel">
   	  		<table>
	    		<tr>
	    			<td style="width:130px">渠道名称：</td>
	    			<td><input name="ciname" class="easyui-validatebox" data-options="required:true" id="ciname"/></td>
	    		</tr>
	    		<tr>
	    			<td>渠道投入成本：</td>
	    			<td><input name="costmoney" class="easyui-validatebox" data-options="required:true" id="costmoney"/></td>
	    		</tr>
	    		<tr>
	    			<td>渠道开发时间：</td>
	    			<td>
	    				<input name="developtime"  type= "text" class= "easyui-datetimebox" style="width:150px;" id="developtime"> 
	    			</td>
	    		</tr>
	    		<tr>
	    			<td ><a href="javascript:void(0);" onclick="addChannel()" class="easyui-linkbutton" data-options="iconCls:'icon-undo'" style="margin-left: 40px;">新增</a> </td>
	    		</tr>
	    	</table>
   	  	</form>
   	  </div>
  </body>
</html>
