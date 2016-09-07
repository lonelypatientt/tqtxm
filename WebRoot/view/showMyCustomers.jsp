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
	   			url:'loadAllMyCustomers.do?methodName=loadAllMyCustomers&pageNo=1&pageSize=10&uid='+${user.uid}+'',
	   		    pagination:true, //如果为true，则在DataGrid控件底部显示分页工具栏。
	   		    frozenColumns:[[{field:'hhh',checkbox:true}]],   
		    	columns:[[    
			        {field:'interviewnumber',title:'预约号',width:100}, 
			       	{field:'cname',title:'客户姓名',width:100},    
			        {field:'csex',title:'性别',width:100,formatter: function(value,row,index){
						if (value==1){
							return '男';
						} else {
							return '女';
						}
					}},
			        {field:'cphone1',title:'电话1',width:100},
			        {field:'cphone2',title:'电话2',width:100}, 
			        {field:'ciname',title:'客户来源（渠道）',width:100},  
			        {field:'cqq',title:'QQ',width:100},  
			        {field:'pname',title:'咨询产品',width:100},  
			        {field:'uname',title:'咨询师',width:100},  
			        {field:'firstdealmoney',title:'首次成交金额',width:100},  
			        {field:'dealallmoney',title:'成交总金额',width:100},  
			        {field:'address',title:'详细地址',width:100},  
			        {field:'cometostoretime',title:'预计到店时间',width:100},  
			        {field:'cometocustomertime',title:'预计回访时间',width:100},  
			                   
			    ]],
			    toolbar: [{
					text:'新增客户',
					iconCls: 'icon-edit',
					handler: function(){
					$('#win').window("open")
					}
				},'-',{
					text:'删除菜单',
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
							array.push(selection[i].mid);
						}
						//将数组对象转换成字符串
						var str = array.join(',');
						//ajax向后台提交数据
						$.post('deleteMenus.do?methodName=deleteMenus',{mids:str},
								function(){
									alert("删除成功！");
									$('#dg').datagrid('reload');
								},
							'text')
						}
				},'-',{
					text:'修改菜单',
					iconCls: 'icon-modify',
					handler: function(){
						var selection = $('#dg').datagrid('getSelections');
						if(selection.length<=0){
							alert("没有选中行！");
							return;
						}
						if(selection.length>1){
							alert("一次只能修改一个菜单！");
							return;
						}
						//ajax向后台提交数据 去执行修改菜单资料
					$('#modify').window('open');
					var mid = selection[0].mid;
					$.post('toModifyMenu.do?methodName=toModifyMenu',{mid:mid},
						function(data){
							//数据回填 得到这个用户的信息回填
							$('#modifyMenu').form('load',{
								mname:data.mname,
								url:data.url,
								isshow:data.isshow,
								parentName:data.parentid
							});
						},
						
						"json");
					}
				},'-',{
					text:'刷新',
					iconCls: 'icon-refresh',
					handler: function(){
						$('#dg').datagrid('reload');
					}
				}]
			})
       	
       		//分页触发事件---------------------
			$('#dg').datagrid("getPager").pagination({
				onSelectPage:function(pageNumber,pageSize){
					$('#dg').datagrid('loading');
					$.post("loadAllMenus.do",{
						methodName:'loadAllMenus',
						pageNo:pageNumber,
						pageSize:pageSize
					},function(data){
						$("#dg").datagrid("loadData",{
							rows:data.rows,
							total:data.total
						});	
					},"json");
					$('#dg').datagrid('loaded');
				}
			});
			
       		//弹出添加菜单的那个窗口 ---------------------------
			$('#win').window({    
			        width:600,    
			        height:400,    
			        modal:true,
			        title:'新增客户' ,
			        closed:true,
			        top:20, 
			});
			
			 //弹出增加客户的那个窗口 
			$('#modify').window({    
			        width:600,    
			        height:400,    
			        modal:true,
			        title:'添加菜单' ,
			        closed:true,
			        top:20, 
			});		
			
			//加载所有的渠道  用于下拉菜单
			$('#ciname').combobox({    
			    url:'loadAllChannels.do?methodName=loadAllChannels',    
			    valueField:'ciid',    
			    textField:'ciname'   
			});
			//加载所有的咨询产品  用于下拉菜单
			$('#pname').combobox({    
			    url:'loadAllProducts.do?methodName=loadAllProducts',    
			    valueField:'pid',    
			    textField:'pname'   
			});
			
		});//-------------------------------------------------------------------加载
		
		//ajax异步提交数据 添加菜单到数据库去
		function addMenu(){
		$.post("addMenu.do?methodName=addMenu", {
			 mname      :$("#mname").val(),
			 url        :$("#url").val(),
			 isshow     :$("#isshow").combo('getValue'),
			 parentName :$("#parentName").combo('getValue')
		  },
		   function(){
	   			$('#win').window('close');
    	        $('#dg').datagrid('reload');
    	        alert("添加菜单成功！");  
		     	$("#addMenu").form("reset");
		   }, "text");
		}
		
		//ajax异步提交数据 修改菜单后把数据增加到数据库去
		function modifyMenu(){
		$.post("addMenu.do?methodName=addMenu", {
			 mname      :$("#mname2").val(),
			 url        :$("#url2").val(),
			 isshow     :$("#isshow2").combo('getValue'),
			 parentName :$("#parentName2").combo('getValue')
		  },
		   function(){
	   			$('#modify').window('close');
    	        $('#dg').datagrid('reload');
    	        alert("修改菜单成功！");  
		     	$("#modifyMenu").form("clear");
		   }, "text");
		}
	</script>
  </head>
  
  <body>
   	  <table id="dg"></table> 
   	   
   	   <!--新增客户--> 
   	  <div id="win">
   	  	<form  id="addCustomer">
   	  		<table>
	    		<tr>
	    			<td style="width:130px">预约号：</td>
	    			<td><input name="interviewnumber" class="easyui-validatebox" data-options="required:true" id="interviewnumber"/></td>
	    		</tr>
	    		<tr>
	    			<td>客户姓名：</td>
	    			<td><input name="cname" class="easyui-validatebox" data-options="required:true" id="cname"/></td>
	    		</tr>
	    		<tr>
	    			<td>性别：</td>
	    			<td>
	    				<select  class="easyui-combobox" name="csex" style="width:150px;" id="csex">   
							<option value="1">男</option>
							<option value="2">女</option>
						</select> 
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>电话1：</td>
	    			<td>
	    				<input id="cphone1"  name="cphone1" style="width:150px;"  class="easyui-validatebox" />
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>电话2：</td>
	    			<td>
	    				<input id="cphone12"  name="cphone12" style="width:150px;"  class="easyui-validatebox" />
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>客户来源（渠道）：</td>
	    			<td>
	    				<input class="easyui-combobox" name="ciname" style="width:150px;" id="ciname" value="请选择来源渠道"/> 
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>QQ：</td>
	    			<td>
	    				<input id="cqq"  name="cqq" style="width:150px;"  class="easyui-validatebox" />
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>咨询产品：</td>
	    			<td>
	    				<input id="pname"  name="pname" style="width:150px;"  class="easyui-combobox" value="请选择咨询的产品"/>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>咨询师：</td>
	    			<td>
	    				<input id="uname"  name="uname" style="width:150px;"  class="easyui-combobox" value="请选择咨询的美容师"/>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>首次成交金额：</td>
	    			<td>
	    				<input id="firstdealmoney"  name="firstdealmoney" style="width:150px;"  class="easyui-validatebox" />
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>成交总金额：</td>
	    			<td>
	    				<input id="dealallmoney"  name="dealallmoney" style="width:150px;"  class="easyui-validatebox" />
	    			</td>
	    		</tr>
	    			<tr>
	    			<td>详细地址：</td>
	    			<td>
	    				<input id="address"  name="address" style="width:150px;"  class="easyui-validatebox"/>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>预计到店时间：</td>
	    			<td>
	    				<input id="cometostoretime"  name="cometostoretime" style="width:150px;"  class="easyui-datetimebox"/>
	    			</td>
	    		</tr>
	    			<tr>
	    			<td>预计回访时间：</td>
	    			<td>
	    				<input id="cometocustomertime"  name="cometocustomertime" style="width:150px;"  class="easyui-datetimebox" />
	    			</td>
	    		<tr>
	    			<td ><a href="javascript:void(0);" onclick="addMenu();" class="easyui-linkbutton" data-options="iconCls:'icon-undo'" style="margin-left: 40px;">添加</a> </td>
	    		</tr>
	    	</table>
   	  	</form>
   	  </div>
   	  
   	 
   	  <!--修改菜单-->
   	  <div id="modify">
   	  	<form  id="modifyMenu">
   	  		<table>
	    		<tr>
	    			<td style="width:130px">菜单名称：</td>
	    			<td><input name="mname" class="easyui-validatebox" data-options="required:true" id="mname2"/></td>
	    		</tr>
	    		<tr>
	    			<td>菜单地址：</td>
	    			<td><input name="url" class="easyui-validatebox" data-options="required:true" id="url2"/></td>
	    		</tr>
	    		<tr>
	    			<td>是否展示：</td>
	    			<td>
	    				<select  class="easyui-combobox" name="isshow" style="width:150px;" id="isshow2">   
							<option value="1">是</option>
							<option value="0">否</option>
						</select> 
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>父级菜单名字：</td>
	    			<td>
	    				<input id="parentName2"  name="parentName" style="width:150px;"  class="parentName" value="请选择父级菜单"/>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td ><a href="javascript:void(0);" onclick="modifyMenu();" class="easyui-linkbutton" data-options="iconCls:'icon-undo'" style="margin-left: 40px;">修改</a> </td>
	    		</tr>
	    	</table>
   	  	</form>
   	  </div>
   	  <script type="text/javascript">
   	  </script>
  </body>
</html>
