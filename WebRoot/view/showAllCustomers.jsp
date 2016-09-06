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
	   			url:'loadAllUsers.do?methodName=loadAllUsers&pageNo=1&pageSize=10',
	   		    pagination:true, //如果为true，则在DataGrid控件底部显示分页工具栏。
	   		    frozenColumns:[[{field:'hhh',checkbox:true}]],   
		    	columns:[[    
			        {field:'uid',title:'编号',width:100}, 
			       	{field:'uNo',title:'员工编号',width:100},    
			        {field:'userName',title:'员工账户',width:100},
			        {field:'userPassWord',title:'员工账户密码',width:100}, 
			        {field:'realName',title:'员工真实名字',width:100}, 
			        {field:'phone',title:'员工手机号码',width:100}, 
			        {field:'avatar',title:'员工头像地址',width:100}, 
			        {field:'emali',title:'员工邮箱',width:100},
			        {field:'qq',title:'员工qq号码',width:100}, 
			        {field:'weChatNo',title:'员工微信号码',width:100}, 
			        {field:'emergencyContactPerson',title:'员工的紧急联系人姓名',width:100}, 
			        {field:'emergencyContactPhone',title:'员工的紧急联系人姓名的手机号码',width:100},      
			        {field:'departmentName',title:'员工的所属部门',width:100,align:'center'},  
			        {field:'entryTime',title:'员工的入职时间',width:100}
			    ]],
				toolbar: [{
					text:'添加用户',
					iconCls: 'icon-edit',
					handler: function(){
					$('#win').window("open")
					}
				},'-',{
					text:'删除用户',
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
							array.push(selection[i].uid);
						}
						//将数组对象转换成字符串
						var str = array.join(',');
						//ajax向后台提交数据
						$.post('deleteUsers.do?methodName=deleteUsers',{uids:str},
								function(data){
									alert(data);
									$('#dg').datagrid('reload');
								},
							'text')
						}
				},'-',{
					text:'修改用户',
					iconCls: 'icon-modify',
					handler: function(){
						var selection = $('#dg').datagrid('getSelections');
						if(selection.length<=0){
							alert("没有选中行！");
							return;
						}
						if(selection.length>1){
							alert("一次只能修改一个用户！");
							return;
						}
						//ajax向后台提交数据 去执行修改用户资料
					$('#modify').window('open');
					var uid = selection[0].uid;
					$.post('toModifyUser.do?methodName=toModifyUser',{uid:uid},
						function(data){
							//数据回填 得到这个用户的信息回填
							$('#modifyUser').form('load',{
								uNo:data.uNo,
								userName:data.userName,
								userPassWord:data.userPassWord,
								realName:data.realName,
								phone:data.phone,
								email:data.emali,
								QQ:data.qq,
								weChatNo:data.weChatNo,
								emergencyContactPerson:data.emergencyContactPerson,
								emergencyContactPhone:data.emergencyContactPhone,
								dname:data.did,
								EntryTime:data.entryTime,
								iseffective:data.iseffective,
								role:data.rid
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
			});
			//弹出修改用户的那个窗口
			$('#modify').window({    
				width:600,    
		        height:400,    
		        modal:true,
		        title:'修改用户' ,
		        closed:true,
		        top:20, 
  			});
  			//弹出添加用户的那个窗口 
			$('#win').window({    
			        width:600,    
			        height:400,    
			        modal:true,
			        title:'添加用户' ,
			        closed:true,
			        top:20, 
			});
			//分页触发事件
			$('#dg').datagrid("getPager").pagination({
				onSelectPage:function(pageNumber,pageSize){
					$('#dg').datagrid('loading');
					$.post("loadAllUsers.do",{
						methodName:'loadAllUsers',
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
		
		//加载部门 用于下拉菜单
			$('.department').combobox({    
			    url:'loadAllDepartments.do?methodName=loadAllDepartments',    
			    valueField:'did',    
			    textField:'dname'   
			});
		//加载角色 用于下拉菜单
			$('.role').combobox({    
			    url:'loadAllRoles.do?methodName=loadAllRoles',    
			    valueField:'rid',    
			    textField:'rname'   
			});
			  
		});//--------------------------------------------------加载的结尾
		//-------------------------------
		//ajax异步提交数据 添加用户到数据库去
		function addUser(){
		$.post("addUser.do?methodName=addUser", {
			 uNo          :$("#uNo").val(),
			 userName     :$("#userName").val(),
			 userPassWord :$("#userPassWord").val(),
			 realName     :$("#realName2").val(),
			 phone        :$("#phone2").val(),
			 email        :$("#email").val(),
			 QQ           :$("#QQ").val(),
			 weChatNo     :$("#weChatNo").val(),
			 emergencyContactPerson:$("#emergencyContactPerson").val(),
			 emergencyContactPhone:$("#emergencyContactPhone").val(),
			 did		  :$("#department2").combo('getValue'),
			 EntryTime    :$("#EntryTime").combo('getValue'),
			 iseffective  :$("#iseffective").combo('getValue'),
			 rid          :$("#role").combo('getValue')
		  },
		   function(){
	   			$('#win').window('close');
    	        $('#dg').datagrid('reload');
    	        alert("添加用户成功！");  
		     	$("#addUser").form("reset");
		   }, "text");
		}
		//-------------------------------
		//ajax异步提交执行将修改后的资料重新放回数据库
		function modifyUser(){
			$.post("addUser.do?methodName=addUser", { 
			 uNo          :$("#uNo3").val(),
			 userName     :$("#userName3").val(),
			 userPassWord :$("#userPassWord3").val(),
			 realName     :$("#realName3").val(),
			 phone        :$("#phone3").val(),
			 email        :$("#email3").val(),
			 QQ           :$("#QQ3").val(),
			 weChatNo     :$("#weChatNo3").val(),
			 emergencyContactPerson:$("#emergencyContactPerson3").val(),
			 emergencyContactPhone:$("#emergencyContactPhone3").val(),
			 did		  :$("#department3").combo('getValue'),
			 EntryTime    :$("#EntryTime3").combo('getValue'),
			 iseffective  :$("#iseffective3").combo('getValue'),
			 rid          :$("#role3").combo('getValue')
			 },
			  function(){
				  $('#modify').window('close');
    	          $('#dg').datagrid('reload');
    	          alert("修改用户成功！");  
		     	  $("#modifyUser").form("reset");
		   }, "text");
		}	
		function fileupload() {
			$.post("Fileupload", { "func": "getNameAndTime" },
			   function(data){
			     alert(data.name); // John
			     console.log(data.time); //  2pm
			   }, "json");
		}
		
		
		
			
	</script>
  </head>
  
  <body>
      <label>姓名：</label><input class="easyui-textbox" data-options="iconCls:'icon-search'" style="width:100px" id="realName"> 
      <label>电话：</label><input class="easyui-textbox" data-options="iconCls:'icon-search'" style="width:120px" id="phone"> 
      <label>部门：</label><input class="department" name="dept" value="请选择部门" id="department">  
   	  <a id="btn" href="javascript:query()"  class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
   	   
   	  <table id="dg"></table>  
   	  
   	   <!--添加用户-->
   	  <div id="win">
  	  	<form  id="addUser" method="post">
	    	<table>
	    		<tr>
	    			<td style="width:130px">员工工号：</td>
	    			<td><input name="job" class="easyui-validatebox" data-options="required:true" id="uNo"/></td>
	    		</tr>
	    		<tr>
	    			<td>用户登录名：</td>
	    			<td><input name="userName" class="easyui-validatebox" data-options="required:true" id="userName"/></td>
	    		</tr>
	    		<tr>
	    			<td>用户密码：</td>
	    			<td><input  name="userPassWord" type="password" class="easyui-validatebox" data-options="required:true" id="userPassWord" /></td>
	    		</tr>
	    		<tr>
	    			<td>真实姓名：</td>
	    			<td><input name="realName" class="easyui-validatebox" data-options="required:false" id="realName2"/></td>
	    		</tr>
	    		<tr>
	    			<td>手机号：</td>
	    			<td><input name="phone" class="easyui-validatebox" data-options="required:false" id="phone2"/></td>
	    		</tr>
	
	    		<tr>
	    			<td>邮箱：</td>
	    			<td><input name="email" class="easyui-textbox" data-options="required:false"  id="email"></td>
	    		</tr>
	    		<tr>
	    			<td>QQ：</td>
	    			<td><input type="text" name="QQ" id="QQ"/></td>
	    		</tr>
	    		<tr>
	    			<td>微信：</td>
	    			<td><input type="text" name="weChatNo" id="weChatNo"/></td>
	    		</tr>
	    		<tr>
	    			<td>紧急联系人：</td>
	    			<td><input type="text" name="emergencyContactPerson" id="emergencyContactPerson"/></td>
	    		</tr>
	    		<tr>
	    			<td>紧急联系人电话：</td>
	    			<td><input type="text" name="emergencyContactPhone" id="emergencyContactPhone"/></td>
	    		</tr>
	    		<tr>
	    			<td>所属部门：</td>
	    			<td>
	    				<input class="department"  name="dname" style="width:150px;" id="department2" />  
	    				 
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>入职时间：</td>
	    			<td><input name="EntryTime"  type= "text" class= "easyui-datetimebox" style="width:150px;" id="EntryTime"></td>
	    		</tr>
	    		<tr>
	    			<td>是否有效：</td>
	    			<td>
	    				<select  class="easyui-combobox" name="iseffective" style="width:150px;" id="iseffective">   
							<option value="1">是</option>
							<option value="0">否</option>
						</select> 
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>用户角色：</td>
	    			<td>
	    				<input id="role"  name="role" style="width:150px;"  class="role"/> 
	    			</td>
	    		</tr>
	    		<tr>
	    			<td ><a href="javascript:void(0);" onclick="addUser();" class="easyui-linkbutton" data-options="iconCls:'icon-adduser'" style="margin-left: 40px;">添加</a> </td>
	    		</tr>
	    	</table>
    	</form>
    	
		<form action="javascript:fileupload()" method="post" enctype="multipart/form-data" >
		   	<input type="text" name="submit-name" ><br />
		    <input type="file" name="files"><br />
		    <input type="submit" value="上传">
		</form>
  	  </div>
   	  
   	  <!--修改用户资料-->
   	  <div id="modify">
  	  	<form  id="modifyUser" method="post">
	    	<table>
	    		<tr>
	    			<td style="width:130px" >员工工号：</td>
	    			<td><input name="uNo" class="easyui-validatebox" data-options="required:true" id="uNo3" /></td>
	    		</tr>
	    		<tr>
	    			<td>用户登录名：</td>
	    			<td><input name="userName" class="easyui-validatebox" data-options="required:true" id="userName3"/></td>
	    		</tr>
	    		<tr>
	    			<td>用户密码：</td>
	    			<td><input  name="userPassWord" type="password" class="easyui-validatebox" data-options="required:true" id="userPassWord3" /></td>
	    		</tr>
	    		<tr>
	    			<td>真实姓名：</td>
	    			<td><input name="realName" class="easyui-validatebox" data-options="required:false" id="realName3" /></td>
	    		</tr>
	    		<tr>
	    			<td>手机号：</td>
	    			<td><input name="phone" class="easyui-validatebox" data-options="required:false" id="phone3"/></td>
	    		</tr>
	    		<tr>
	    			<td>邮箱：</td>
	    			<td><input name="email" class="easyui-textbox" data-options="validType:'email'" id="email3" ></td>
	    		</tr>
	    		<tr>
	    			<td>QQ：</td>
	    			<td><input type="text" name="QQ" id="QQ3"/></td>
	    		</tr>
	    		<tr>
	    			<td>微信：</td>
	    			<td><input type="text" name="weChatNo" id="weChatNo3"/></td>
	    		</tr>
	    		<tr>
	    			<td>紧急联系人：</td>
	    			<td><input type="text" name="emergencyContactPerson"  id="emergencyContactPerson3"/></td>
	    		</tr>
	    		<tr>
	    			<td>紧急联系人电话：</td>
	    			<td><input type="text" name="emergencyContactPhone" id="emergencyContactPhone3"/></td>
	    		</tr>
	    		<tr>
	    			<td>所属部门：</td>
	    			<td>
	    				<input class="department"  name="dname" style="width:150px;" id="department3"/>  
	    				 
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>入职时间：</td>
	    			<td><input name="EntryTime"  type= "text" class= "easyui-datetimebox" style="width:150px;" id="EntryTime3" ></td>
	    		</tr>
	    		<tr>
	    			<td>是否有效：</td>
	    			<td>
	    				<select  class="easyui-combobox" name="iseffective" style="width:150px;" id="iseffective3" >   
							<option value="1">是</option>
							<option value="0">否</option>
						</select> 
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>用户角色：</td>
	    			<td>
	    				<input name="role" style="width:150px;" id="role3" class="role"/> 
	    			</td>
	    		</tr>
	    		<tr>
	    			<td ><a href="javascript:void(0);" onclick="modifyUser();" class="easyui-linkbutton" data-options="iconCls:'icon-adduser'" style="margin-left: 40px;">修改</a> </td>
	    		</tr>
	    	</table>
    	</form>
  	  </div>
   	  
   	  
   	  
   	  <script type="text/javascript">
   	  		//查询用户 
	   	  	function query(){
				realName=$('#realName').val();  
				phone= $('#phone').val();
				department= $('#department').combo('getValue'); 
				var sql = "";
				if(realName!=''){
					sql+=" and realName LIKE '%"+realName+"%'" ;
				}
				 if(phone!=''){
					 sql+=" and phone  LIKE '%"+phone+"%'" ;
				}
				 if(department!=''&& department > 0){
					 sql+=" and did  LIKE '%"+department+"%'" ;
				};
				 $("#dg").datagrid("load",{
						sql:sql,
					}
					);
			}
   	  </script>
  </body>
</html>
