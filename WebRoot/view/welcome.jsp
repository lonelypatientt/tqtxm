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
    <title>welcome</title>
    <style type="text/css">
    </style>
    <link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	 <script type="text/javascript" src="view/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript">
		function addTab(title,url){
			$("#tabs").tabs('add',{
				title:title,
				selected:true,
				closable:true,
				content :"<iframe style='height:100%;width:100%' scrolling='auto' frameborder='0' src='"+ url +"'></iframe>"
				
			});
		} 
	</script>
  </head>
  
<body class="easyui-layout">   
    <div data-options="region:'north',split:true" style="height:100px;" >
    	<div><img src="view/images/tqt_logo.png" style="float: left"/></div>
    	<div id = "head" style="float: right"><span style="font-size: 20px;color: ">热烈欢迎你${user.userName}</span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="logout.do?methodName=logout" style="font-size: 20px;text-decoration: none">安全退出</a> </div>
   		<div><img src="<%=basePath%>files/${user.avatar}"    style="float: right"/></div>
    </div>   
    <div data-options="region:'west',title:'菜单列表',split:true" style="width:200px;">
		<div id="menu" class="easyui-accordion" style="width:300px;height:200px;" data-options="fit:true">   
			<c:forEach items="${menu}" var="m2">
				<c:if test="${m2.level == 2}">
					<div title="${m2.mname }" data-options="iconCls:'icon-save'" style="overflow: auto; padding: 10px;">
						<ul id="tt">
							<c:forEach items="${menu}" var="m3">
								<c:if test="${m3.parentid==m2.mid}">
									<li class="m3">
										<a href="javascript:void(0)" onclick="addTab('${m3.mname}','${basePath}${m3.url}');">${m3.mname}</a>
									</li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</c:if>
			</c:forEach>
		</div>  
    </div>   
    <div data-options="region:'center'" style="padding:5px;background:#eee;">
		<div id="tabs" class="easyui-tabs" style="width:500px;height:250px;" data-options="fit:true">   
		    <div title="欢迎" style="padding:20px;">   
		       	 热烈欢迎你！！唯一！！   
		    </div> 
		</div>  
    </div>   
</body>  


</html>
