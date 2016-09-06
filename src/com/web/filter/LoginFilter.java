package com.web.filter;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.dao.UserDao;
import com.web.dao.impl.UserDaoImpl;
import com.web.entity.User;
import com.web.model.UserModel;
import com.web.model.impl.UserModelImpl;

public class LoginFilter implements Filter{
	private UserModel userModel = new UserModelImpl();
	public void destroy() {	
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
//		//强砖一下噻
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
//		request.setCharacterEncoding("utf-8");
//		String methodName = req.getParameter("methodName");
//		System.out.println(methodName);
//		if("login".equals(methodName) || "logout".equals(methodName) || "reg".equals(methodName) ){
			chain.doFilter(req, resp);
//		}else{
//			User user = (User)req.getSession().getAttribute("user");
//			if(null == user ){
//				req.getSession().setAttribute("regError", "对不起，你还没有登陆哦！");
//				PrintWriter out = resp.getWriter();
//				out.write("<script type = 'text/javascript'>window.top.location.href='index.jsp';</script> ");
//				
//			}else{
//				boolean b = userModel.executeUserPower(user.getSid(), methodName);
//				if(b){
//					chain.doFilter(req, resp);
//				}else{
//					//调到无权的一个警告界面
//					req.getRequestDispatcher("view/noPower.jsp").forward(req, resp);
//				}
//				
//				
//				
//				
//				
//			}
//			}	
		
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	
	}



}
