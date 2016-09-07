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
	   			url:'loadAllProducts.do?methodName=loadAllProducts',
	   		    pagination:true, //如果为true，则在DataGrid控件底部显示分页工具栏。
	   		    frozenColumns:[[{field:'hhh',checkbox:true}]],   
		    	columns:[[    
			        {field:'pname',title:'产品名字',width:100}, 
			       	{field:'recommendDoctor',title:'产品推荐医师',width:100},    
			        {field:'productDescription',title:'产品描述',width:100},
			        {field:'createTime',title:'产品出厂时间',width:100},
			    ]],
			    toolbar: [{
					text:'新增产品',
					iconCls: 'icon-edit',
					handler: function(){
					$('#win').window("open")
					}
				},'-',{
					text:'删除产品',
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
							array.push(selection[i].pid);
						}
						//将数组对象转换成字符串
						var str = array.join(',');
						//ajax向后台提交数据
						$.post('deleteProducts.do?methodName=deleteProducts',{pids:str},
								function(){
									alert("删除产品成功！");
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
			        title:'新增产品' ,
			        closed:true,
			        top:20, 
			});
			
			
		});//-------------------------------------------------------------------加载
	//ajax异步提交数据 添加产品到数据库去
		function addProduct(){
		$.post("addProduct.do?methodName=addProduct", {
			 pname      		  :$("#pname").val(),
			 recommendDoctor      :$("#recommendDoctor").val(),
			 productDescription   :$("#productDescription").val(),
			 createTime  		  :$("#createTime").combo('getValue'),
		  },
		   function(){
	   			$('#win').window('close');
    	        $('#dg').datagrid('reload');
    	        alert("添加产品成功！");  
		     	$("#addDepartment").form("reset");
		   }, "text");
		}
	</script>
  </head>
  
  <body>
   	  <table id="dg"></table> 
   	  <!--新增产品--> 
   	  <div id="win">
   	  	<form  id="addProduct">
   	  		<table>
	    		<tr>
	    			<td style="width:130px">产品名称：</td>
	    			<td><input name="pname" class="easyui-validatebox" data-options="required:true" id="pname"/></td>
	    		</tr>
	    		<tr>
	    			<td>推荐医师：</td>
	    			<td><input name="recommendDoctor" class="easyui-validatebox" data-options="required:true" id="recommendDoctor"/></td>
	    		</tr>
	    		<tr>
	    			<td>产品描述：</td>
	    			<td>
	    				<input name="productDescription"  type= "text" class="easyui-validatebox" style="width:150px;" id="productDescription"> 
	    			</td>
	    		</tr>
	    			<tr>
	    			<td>产品创建时间：</td>
	    			<td>
	    				<input name="createTime"  type= "text" class="easyui-datetimebox" style="width:150px;" id="createTime"> 
	    			</td>
	    		</tr>
	    		<tr>
	    			<td ><a href="javascript:void(0);" onclick="addProduct()" class="easyui-linkbutton" data-options="iconCls:'icon-undo'" style="margin-left: 40px;">添加</a> </td>
	    		</tr>
	    	</table>
   	  	</form>
   	  </div>
  </body>
</html>
