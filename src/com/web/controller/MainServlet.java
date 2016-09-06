package com.web.controller;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.web.entity.ChannelInfo;
import com.web.entity.Department;
import com.web.entity.Menu;
import com.web.entity.Product;
import com.web.entity.Role;
import com.web.entity.User;
import com.web.model.UserModel;
import com.web.model.impl.UserModelImpl;
import com.web.util.Page;
import com.web.vo.DepartmentVo;
import com.web.vo.MenuVo;
import com.web.vo.MenuVo2;
import com.web.vo.UserVo;
import com.web.vo.UserVo2;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class MainServlet extends HttpServlet{
	//控制层拥有模型层对象
	private UserModel userModel = new UserModelImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	/**
	 * 通过传过来的methodName去调用下面的方法
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		String methodName = req.getParameter("methodName");
		Class c = MainServlet.class;
		try {
			Method m = c.getMethod(methodName , HttpServletRequest.class,HttpServletResponse.class);
			m.invoke(this, req,resp);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 登录界面  假如登录成功还要返回一个这个用户他所拥有的 菜单 并且把这个用户保存在session范围里面
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void login(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException{
		String userName = req.getParameter("userName");
		String passWord = req.getParameter("userPassword");
		User user = userModel.loadUserByName(userName);
		String result = "0";
		if(null == user){
			result = "3";
		}else if(passWord.equals(user.getUserPassWord())){
			 HttpSession session = req.getSession();
		     session.setAttribute("user", user);
		     //返回一个菜单的集合出来 通过用户的username去查询他所拥有的菜单
		     List<Menu> menu =  userModel.loadUserMenu(userName);
		     session.setAttribute("menu", menu);	
		     result = "1";
		}else{
			 result = "2";
		}
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().write(result);
	}
	
	/**
	 * 查询所有的用户（经理，主管，咨询师）这里已经查询到了所有的用户 还有一共多少行记录
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void loadAllUsers(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException{
		int pageNo =Integer.valueOf(req.getParameter("pageNo"));
		int pageSize =Integer.valueOf(req.getParameter("pageSize"));
		String sql=req.getParameter("sql");
		Page<UserVo> page =userModel.loadAllUsers(pageNo, pageSize,sql);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("rows", page.getDateList());
		map.put("total", page.getTotal());
		String josn=JSONObject.fromObject(map).toString();
		
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().write(josn);
		resp.getWriter().flush();
	}
	/**
	 * 查询所有的部门
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void loadAllDepartments(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException{
		List<DepartmentVo> departments = userModel.loadAllDepartments();
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().write(JSONArray.fromObject(departments).toString());
		resp.getWriter().flush();
	}
	/**
	 * 查询所有角色
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void loadAllRoles(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException{
		List<Role> roles = userModel.loadAllRoles();
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().write(JSONArray.fromObject(roles).toString());
		resp.getWriter().flush();	
	}
	
	/**
	 * 添加用户到数据库里面去
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void addUser(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException{
		String uNo = req.getParameter("uNo");
		String userName = req.getParameter("userName");
		String userPassWord = req.getParameter("userPassWord");
		String realName = req.getParameter("realName");
		String phone = req.getParameter("phone");
		String email = req.getParameter("email");
		String QQ = req.getParameter("QQ");
		String weChatNo = req.getParameter("weChatNo");
		String emergencyContactPerson = req.getParameter("emergencyContactPerson");
		String emergencyContactPhone = req.getParameter("emergencyContactPhone");
		int did = Integer.parseInt(req.getParameter("did"));
		String EntryTime = req.getParameter("EntryTime");
		int iseffective = Integer.parseInt(req.getParameter("iseffective"));
		int rid = Integer.parseInt(req.getParameter("rid"));
		userModel.addUser(uNo, userName, userPassWord, realName, phone, email, QQ, weChatNo, emergencyContactPerson, emergencyContactPhone, did, EntryTime, iseffective, rid);
		resp.setCharacterEncoding("utf-8");
//		resp.getWriter().write("添加成功");
//		resp.getWriter().flush();	
	}

	/**
	 * 删除用户 可以删除一个用户 或者很多个用户 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void deleteUsers(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException{
		String uids = req.getParameter("uids");
		String[] uid = uids.split(",");
		userModel.deleteUsers(uid);
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().write("删除成功");
		resp.getWriter().flush();
	}
	
	/**
	 * 用于修改某一个用户的资料  根据传来的用户id去数据库查询此用户的信息 包括他的角色信息 并且重新定义一个包含他角色的uservo2
	 */
	public void toModifyUser(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException{
		String uid = req.getParameter("uid");
		UserVo2 userVo2 =userModel.toModifyUser(uid);
		resp.setCharacterEncoding("utf-8");
		//记住这里返回的是一个json对象就用fromobject
		resp.getWriter().write(JSONObject.fromObject(userVo2).toString());
		resp.getWriter().flush();
	}
	
	/**
	 * 查看所有的菜单
	 */
	public void loadAllMenus(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException{
		int pageNo =Integer.valueOf(req.getParameter("pageNo"));
		int pageSize =Integer.valueOf(req.getParameter("pageSize"));
		Page<MenuVo> page =userModel.loadAllMenus(pageNo, pageSize);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("rows", page.getDateList());
		map.put("total", page.getTotal());
		String josn=JSONObject.fromObject(map).toString();
		
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().write(josn);
	}
	
	/**
	 * 查看所有的一级和二级菜单 用于添加菜单的时候选择的下拉列表
	 */
	public void toAddMenu(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException{
		List<Menu> menus =userModel.loadAll12Menu();
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().write(JSONArray.fromObject(menus).toString());
		resp.getWriter().flush();	
	}
	
	/**
	 * 添加菜单
	 */
	public void addMenu(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException{
		String mname = req.getParameter("mname");
		String url = req.getParameter("url");
		String isshow = req.getParameter("isshow");
		String parentid = req.getParameter("parentName");//父级菜单的编号
		userModel.addMenu(mname, url, Integer.parseInt(isshow), Integer.parseInt(parentid));
		resp.setCharacterEncoding("utf-8");
	}
	/**
	 * 删除菜单 删除一个或者许多个
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void deleteMenus(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException{
		String mids = req.getParameter("mids");
		String[] mid = mids.split(",");
		userModel.deleteMenus(mid);
		resp.setCharacterEncoding("utf-8");
	}
	
	/**
	 * 修改菜单  根据传来的菜单的mid去数据库查询此菜单的信息 并把此用户的信息删了 修改然后执行一个添加即可
	 */
	public void toModifyMenu(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException{
		String mid = req.getParameter("mid");
		Menu menu =userModel.toModifyMenu(mid);
		resp.setCharacterEncoding("utf-8");
		//记住这里返回的是一个json对象就用fromobject
		resp.getWriter().write(JSONObject.fromObject(menu).toString());
		resp.getWriter().flush();
	}
	
	/**
	 * 通过角色的rid 去找到他对应的所拥有的菜单
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void loadRoleMenus(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException{
		int rid = Integer.valueOf(req.getParameter("rid"));
		List<Object[]>  menuList = userModel.loadRoleMenus(rid);
		List<MenuVo2> menuVo2 = new ArrayList<MenuVo2>();
		for(Object[] m :menuList){
			if((m[2])== null){
				 m[2]=0;
			}
			String m1 = m[0].toString();
			String m2 = m[1].toString();
			String m3 = m[2].toString();
			MenuVo2 n = new MenuVo2(Integer.valueOf(m1), m2,Integer.valueOf(m3));
//			MenuVo2 n = new MenuVo2((Integer)m[0], String.valueOf(m[1]),(Integer)m[2]);
//			menuVo2.add(n);
			menuVo2.add(n);
		}
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().write(JSONArray.fromObject(menuVo2).toString());
		resp.getWriter().flush();
	}
	
	/**
	 * 执行修改角色拥有的菜单
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void modifyRoleMenus(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException{
		String  mids= req.getParameter("mids");
		
		System.out.println(mids);
//		String m =mids.substring(1,mids.length()); //去掉第一个   ， 号 
//		System.out.println(m);
		int roleRid =Integer.valueOf(req.getParameter("rid"));
		String [] mid = mids.trim().split(",");
		userModel.modifyRoleMenus(roleRid, mid);
	}
	
	/**
	 * 查看所有的渠道消息
	 */
	public void loadAllChannels(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException{
		List<ChannelInfo> channels =userModel.loadAllChannels();
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().write(JSONArray.fromObject(channels).toString());
		resp.getWriter().flush();
	}
	
	/**
	 * 添加渠道
	 */
	public void addChannel(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException{
		String ciname = req.getParameter("ciname");
		String costmoney = req.getParameter("costmoney");
		String developtime = req.getParameter("developtime");
		userModel.addChannel(ciname, costmoney, developtime );
		resp.setCharacterEncoding("utf-8");
	}
	/**
	 * 删除渠道 删除一个或者许多个
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void deleteChannels(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException{
		String ciid = req.getParameter("ciid");
		String[] ciids = ciid.split(",");
		userModel.deleteChannels(ciids);
		resp.setCharacterEncoding("utf-8");
	}
	/**
	 * 查询所有的部门的老大 的名字 用于添加部门的时候选择部门老大
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void loadAllDepartmentBosses(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException{
		List<User> departmentBosses = userModel.loadAllDepartmentBosses();
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().write(JSONArray.fromObject(departmentBosses).toString());
		resp.getWriter().flush();
	}

	/**
	 * 添加部门
	 */
	public void addDepartment(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException{
		String dname = req.getParameter("dname");
		String directorID = req.getParameter("bossUid");
		String description = req.getParameter("description");
		userModel.addDepartment(dname, directorID, description);
		resp.setCharacterEncoding("utf-8");
	}
	/**
	 * 删除部门  删除一个或者许多个
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void deleteDepartments(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException{
		String dids = req.getParameter("dids");
		String[] did = dids.split(",");
		userModel.deleteDepartments(did);
		resp.setCharacterEncoding("utf-8");
	}
	/**
	 * 查看所有的产品信息
	 */
	public void loadAllProducts(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException{
		List<Product> products =userModel.loadAllProducts();
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().write(JSONArray.fromObject(products).toString());
		resp.getWriter().flush();
	}
	/**
	 * 添加产品
	 */
	public void addProduct(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException{
		String pname = req.getParameter("pname");
		String recommendDoctor = req.getParameter("recommendDoctor");
		String productDescription = req.getParameter("productDescription");
		String createTime = req.getParameter("createTime");
		userModel.addProduct(pname, recommendDoctor, productDescription,createTime);
		resp.setCharacterEncoding("utf-8");
	}
	/**
	 * 删除产品  删除一个或者许多个
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void deleteProducts(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException{
		String pids = req.getParameter("pids");
		String[] pid = pids.split(",");
		userModel.deleteProducts(pid);
		resp.setCharacterEncoding("utf-8");
	}
}


