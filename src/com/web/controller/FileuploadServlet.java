package com.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileuploadServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
				this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		//创建文件工厂对象
		DiskFileItemFactory dff = new DiskFileItemFactory();
		//创建一个文件上传处理工具
		ServletFileUpload sfu = new ServletFileUpload(dff);
		try {
			//读取请求中的二进制数据 
			List<FileItem> files = sfu.parseRequest(req);
			for(FileItem fi : files){
				//判断当前数据是否为普通表单字段(除文件上传之外的其他字段)
				if(fi.isFormField()){
					
				}else{
				//文件上传的二进制数据字段
					String fileName = fi.getName();
					String suffix = fileName.substring(fileName.lastIndexOf("."));
					
					if(".jpg".equalsIgnoreCase(suffix)||".png".equalsIgnoreCase(suffix)||".bmp".equalsIgnoreCase(suffix)||".jpeg".equalsIgnoreCase(suffix)||".ico".equalsIgnoreCase(suffix)){
						if(fi.getSize()<=1024*1024*10){//1造
							Random random = new Random();//随机数
							int i = random.nextInt(1000);//产生0到1000的随机整数
							long l = new Date().getTime();//当前系统时间的毫秒数
							fileName = i +""+l+suffix;//生成一个新的文件名称（今后用的比较多的方式）
							
							String path = this.getServletContext().getRealPath("files")+"/"+fileName;
							File f = new File(path);
							//将二进制数据写入文件保存在硬盘上
							fi.write(f);
							//插入数据库 修改数据
							String savaPath = "files/"+fileName;
							//String sql = "insert into user (avatar) values ()";
							
							
							System.out.println("laile ------------------");
							
							req.setAttribute("fileuploadmsg", "文件上传成功");
						}else{
							req.setAttribute("fileuploadmsg", "文件格式不能超过1zhao");
						}
					}else{
						req.setAttribute("fileuploadmsg", "文件格式错误");
					}
					
				}
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		req.getRequestDispatcher("view/fileupload.jsp").forward(req, resp);
	}
		
}
