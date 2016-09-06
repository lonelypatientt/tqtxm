package com.web.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public abstract class DBUtil {
	private static String url; 
	private static String root; 
	private static String password; 
	private static String driverclass;
	
	/**
	 * 初始化
	 */
	static{
		Properties p = new Properties();
		InputStream input = null;
		try {
			input = DBUtil.class.getResourceAsStream("/com/web/util/configuration.properties");
			p.load(input);
			url = p.getProperty("url");
			root = p.getProperty("root");
			password = p.getProperty("password");
			driverclass = p.getProperty("driverclass");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	/**
	 * 获得连接
	a */
	public static Connection getConnection(){
		Connection conn =null;
		try {
			//获得驱动
			Class.forName(driverclass);
			//获得连接
			conn = DriverManager.getConnection(url, root, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   return conn;
	}
	
	
	/**
	 * DML  支持预编译处理
	 */
	
	public static void DML(String sql , Object [] obj){
		Connection conn =null;
		PreparedStatement ps =null;
		try {
			conn = DBUtil.getConnection();
			ps = conn.prepareStatement(sql);
			if(null != obj && obj.length > 0 ){
				for(int i = 0 ;i < obj.length;i ++){
					ps.setObject((i+1), obj[i]);
				}
			}
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
				try {
					if(ps != null) ps.close();
					if(conn != null)conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		} 
		
		
	}
	

	/**
	 * DQL  支持预编译处理
	 */
	
	public static List<Object []> DQL(String sql , Object [] obj){
		List<Object []> list = new ArrayList(); 
		Connection conn =null;
		PreparedStatement ps =null;
		ResultSet res = null;
		try {
			conn = DBUtil.getConnection();
			ps = conn.prepareStatement(sql);
			if(null != obj && obj.length > 0 ){
				for(int i = 0 ;i < obj.length;i ++){
					ps.setObject((i+1), obj[i]);
				}
			}
			res = ps.executeQuery();
			//处理结果集
			int count = res.getMetaData().getColumnCount();
			Object []m = null;
			while(res.next()){
				m = new Object[count];
				for(int i = 0 ;i < count;i++){
					m[i] = res.getObject(i+1);
				}
				list.add(m);
			}
			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
				try {
					if(res != null) res.close();
					if(ps != null) ps.close();
					if(conn != null)conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		} 
		return list;
		
		
	}
	
	
	
	
	
	
}
