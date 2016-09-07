package com.web.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.web.dao.UserDao;
import com.web.entity.ChannelInfo;
import com.web.entity.Department;
import com.web.entity.Menu;
import com.web.entity.Product;
import com.web.entity.Role;
import com.web.entity.User;
import com.web.util.DBUtil;
import com.web.util.Page;
import com.web.vo.CustomerVo;
import com.web.vo.DepartmentVo;
import com.web.vo.MenuVo;
import com.web.vo.UserVo;
import com.web.vo.UserVo2;

public class UserDaoImpl implements UserDao {

	/**
	 * 通过用户登录时候用的用户名来数据库查询到是否有这个结果
	 * @return null 或者 user对象
	 */
	public User loadUserByName(String userName) {
		User user = null;
		String sql = "select * from user where username = ?  ";
		Object[]m={userName};
		List<Object[]> list = DBUtil.DQL(sql,m);
		if(list.size()>0){
			 Object[]n=list.get(0);
			 user = new User((Integer)n[0], String.valueOf(n[1]), String.valueOf(n[2]), String.valueOf(n[3]), String.valueOf(n[4]), String.valueOf(n[5]), String.valueOf(n[6]), String.valueOf(n[7]), String.valueOf(n[8]), String.valueOf(n[9]), String.valueOf(n[10]), String.valueOf(n[11]), (Integer)n[12], String.valueOf(n[13]), (Integer)n[14]);
		}
		return user;
	}
	
	/**
	 * 返回当前用户他所拥有的菜单 
	 * @param 
	 * @return
	 */
	public List<Menu> loadUserMenu(String userName){
		String sql = "select m.* from  user u , userrole ur , rolemenu  rm , menu m where u.uid = ur.uid AND ur.rid = rm.rid and rm.mid = m.mid and m.isshow = 1 and u.userName = ? ";
		List<Object[]>list = DBUtil.DQL(sql, new Object[]{userName});
		List<Menu> list1= new ArrayList<Menu>();
		for(Object[] m : list){
			Menu menu = new Menu((Integer)m[0], (String)m[1], (String)m[2], (Integer)m[3], (Integer)m[4], (Integer)m[5]);
			list1.add(menu);
		}
		return list1;
	};

	/**
	 * 查询所有的用户 展示到网页上去 要使用uservo（经理，主管，咨询师）
	 */
	public Page<UserVo> loadAllUsers(int pageNo,int  pageSize,String sql) {
		String sql1 = "SELECT u.uid,u.uNo,u.userName,u.userPassWord,u.realName,u.phone,u.avatar,u.email,u.QQ,u.weChatNo,u.emergencyContactPerson,u.emergencyContactPhone,(SELECT d.dname from department d where d.did=u.did) , u.EntryTime from user u where u.iseffective = 1  ";
		String sql2=" limit ?,?";
		String sql3;
		if(sql !=null&&sql.length()>0){
			 sql3=sql1+sql+sql2;
		}else{
			 sql3=sql1+sql2;
		}
		List<Object[]> list = DBUtil.DQL(sql3, new Object[]{(pageNo-1)*pageSize,pageSize});
		List<UserVo> dateList= new ArrayList<UserVo>();
		for(Object[] n : list){
			UserVo userVo = new UserVo((Integer)n[0], String.valueOf(n[1]), String.valueOf(n[2]), String.valueOf(n[3]), String.valueOf(n[4]), String.valueOf(n[5]), String.valueOf(n[6]), String.valueOf(n[7]), String.valueOf(n[8]), String.valueOf(n[9]), String.valueOf(n[10]), String.valueOf(n[11]), String.valueOf(n[12]), String.valueOf(n[13]));
			dateList.add(userVo);
		 }
		
		sql = "select count(*) from user u where u.iseffective = 1";
		list=DBUtil.DQL(sql,null);
		long total = (Long)list.get(0)[0];
		return new Page<UserVo>(pageNo, pageSize, dateList, total);
	}
	
	/**
	 * 查询所有的部门
	 */
	public List<DepartmentVo> loadAllDepartments(){
		String sql = "select d.did , d.dname, (select u.realName from user u where u.uid = d.directorID),d.description , d.iseffective from Department d where d.iseffective=1";
		List<Object[]>list = DBUtil.DQL(sql, null);
		List<DepartmentVo> list1= new ArrayList<DepartmentVo>();
		for(Object[] m : list){
			DepartmentVo department = new DepartmentVo((Integer)m[0], String.valueOf(m[1]), String.valueOf(m[2]), String.valueOf(m[3]), (Integer)m[4]);
			list1.add(department);
		}
		return list1;
	};
	/**
	 * 查询所有的角色
	 */
	public List<Role> loadAllRoles(){
		String sql ="select *  from role ";
		List<Object[]>list = DBUtil.DQL(sql, null);
		List<Role> list1= new ArrayList<Role>();
		for(Object[] m : list){
			Role role = new Role((Integer)m[0], (String)m[1]);
			list1.add(role);
		}
		return list1;
	}
	
	/**
	 * 添加用户 并且添加此用户的角色 
	 */
	public  void  addUser(String uNo,String userName,String userPassWord,String realName,String phone,String avatar,String email,String  QQ,String weChatNo,String emergencyContactPerson,String emergencyContactPhone,int did,String EntryTime, int iseffective,int rid ){
			String sql = "INSERT into  user (uNo,userName,userPassWord,realName,phone,avatar,email,QQ,weChatNo,emergencyContactPerson" +
						",emergencyContactPhone,did,EntryTime,iseffective)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			if(avatar.length()==0){
				avatar="http://localhost/tqtxm/upload/moren.jpg";
			}
			Object[]m={uNo,userName,userPassWord,realName,phone,avatar,email,QQ,weChatNo,emergencyContactPerson,emergencyContactPhone,did,EntryTime,iseffective};
			//插入用户的数据
			DBUtil.DML(sql, m);
			//查询插入的这个用户的uid
			sql = "select u.uid from user u where userName = ? and userPassWord = ? ";
			List<Object[]>list=DBUtil.DQL(sql, new Object[]{userName,userPassWord});
			int  id = (Integer)list.get(0)[0];
			//添加该用户的角色
			sql = "insert into  userrole(uid,rid) VALUES(?,?) ";
			DBUtil.DML(sql, new Object[]{id,rid});
			
	}
	
	/**
	 * 删除某个用户 或者一些用户  通过传过来的uid 同时要删除他的角色
	 */
	public void deleteUsers(String[] uid){
		String sql = "update user set iseffective = 0  where uid = ?";
		for(String m : uid){
			DBUtil.DML(sql, new Object[]{m});
		}
		sql="delete from userrole  where uid = ? ";
		for(String m : uid){
			DBUtil.DML(sql, new Object[]{m});
		}
	};
	
	/**
	 * 查询用户的全部资料 用于修改用户的时候资料回填 要用uservo2
	 * @param uid
	 */
	public UserVo2 toModifyUser(String uid){
		//先去查询这个用户对应的角色rid
		String sql ="select ur.rid from userrole ur where ur.uid = ?";
		int rid = (Integer)DBUtil.DQL(sql, new Object[]{uid}).get(0)[0];
		//查询这个用户
		sql = "select * from user u where u.uid = ?";
		Object[]m=DBUtil.DQL(sql, new Object[]{uid}).get(0);
		//创建一个uservo2
		UserVo2 userVo2= new UserVo2((Integer)m[0], String.valueOf(m[1]), String.valueOf(m[2]), String.valueOf(m[3]), String.valueOf(m[4]), String.valueOf(m[5]), String.valueOf(m[6]), String.valueOf(m[7]), String.valueOf(m[8]), String.valueOf(m[9]), String.valueOf(m[10]), String.valueOf(m[11]), (Integer)m[12], String.valueOf(m[13]), (Integer)m[14], rid);	
		return userVo2;
	};
	/**
	 * 添加用户 并且添加此用户的角色 
	 */
	public  void  modifyUser(String uid,String uNo,String userName,String userPassWord,String realName,String phone,String avatar,String email,String  QQ,String weChatNo,String emergencyContactPerson,String emergencyContactPhone,int did,String EntryTime, int iseffective,int rid ){
			String sql = "UPDATE user u set u.uNo=? ,u.userName=?,u.userPassWord=?,u.realName=?,u.phone=?,u.avatar=?,u.email=?,u.QQ=?,u.weChatNo=?,u.emergencyContactPerson=?,u.emergencyContactPhone=?,u.did=?,u.EntryTime=?,u.iseffective=?  where u.uid=?";
			Object[]m={uNo,userName,userPassWord,realName,phone,avatar,email,QQ,weChatNo,emergencyContactPerson,emergencyContactPhone,did,EntryTime,iseffective,uid};
			//插入用户的数据
			DBUtil.DML(sql, m);
			//修改该用户的角色
			sql = "UPDATE userrole ur set  ur.rid = ? where ur.uid = ?";
			DBUtil.DML(sql, new Object[]{rid,uid});
			
	}
	/**
	 * 返回一个所有的菜单集合
	 * @param 
	 * @return
	 */
	public Page<MenuVo> loadAllMenus(int pageNo , int pageSize){
		String sql ="select m.mid, m.mname,m.url,m.isshow,m.level,(select m2.mname from menu m2 where m.parentid = m2.mid)  from menu m limit ?,?";
		List<Object[]>list = DBUtil.DQL(sql, new Object[]{(pageNo-1)*pageSize,pageSize});
		List<MenuVo> dateList= new ArrayList<MenuVo>();
		for(Object[] m : list){
			MenuVo menuVo = new MenuVo((Integer)m[0], (String)m[1], (String)m[2], (Integer)m[3], (Integer)m[4], (String)m[5]);
			dateList.add(menuVo);
		}
		
		sql = "select count(*) from menu";
		list=DBUtil.DQL(sql,null);
		long total = (Long)list.get(0)[0];
		return new Page<MenuVo>(pageNo, pageSize, dateList, total);
	}
	
	/**
	 * 返回一个所有一级和二级的菜单集合
	 * @param 
	 * @return
	 */
	public List<Menu> loadAll12Menu(){
		String sql ="select *  from menu m  where m.level in (1,2)";
		List<Object[]>list = DBUtil.DQL(sql, null);
		List<Menu> list1= new ArrayList<Menu>();
		for(Object[] m : list){
			Menu menu = new Menu((Integer)m[0], (String)m[1], (String)m[2], (Integer)m[3], (Integer)m[4], (Integer)m[5]);
			list1.add(menu);
		}
		return list1;
	}

	/**
	 * 添加菜单
	 */
	public void addMenu(String mname,String url,int isshow,int parentid ){
		//先通过父级菜单的id 查到父级菜单的等级 等级 加1 就是子菜单的等级了
		String sql ="select *  from menu m  where m.mid = ?";
		List<Object[]>list = DBUtil.DQL(sql, new Object[]{parentid});
		List<Menu> list1= new ArrayList<Menu>();
		for(Object[] m : list){
			Menu menu = new Menu((Integer)m[0], (String)m[1], (String)m[2], (Integer)m[3], (Integer)m[4], (Integer)m[5]);
			list1.add(menu);
		}
		int i = list1.get(0).getLevel()+1;
		//执行插入菜单动作
	   sql = "insert into menu( mname, url,isshow, level, parentid ) values(?,?,?,?,?) ";
	   DBUtil.DML(sql,new Object[]{mname, url,isshow, i, parentid});
	}
	
	/**
	 * 删除某个菜单 或者一些菜单  通过传过来的mid  
	 */
	public void deleteMenus(String[] mid){
		String sql="delete from menu  where mid = ? ";
		for(String m : mid){
			DBUtil.DML(sql, new Object[]{m});
		}
	};
	/**
	 * 查询菜单的资料 用于修改菜单的时候资料回填
	 */
	public Menu toModifyMenu(String mid){
		String sql = "select * from menu m where m.mid = ?";
		Object[]m=DBUtil.DQL(sql, new Object[]{mid}).get(0);
		//创建一个menu对象
		Menu menu = new Menu((Integer)m[0], String.valueOf(m[1]), String.valueOf(m[2]), (Integer)m[3], (Integer)m[4], (Integer)m[5]);
		//得到这个菜单的资料后就删除这个菜单
		sql = "delete from menu  where mid = ?";
		DBUtil.DML(sql, new Object[]{mid});
		return menu;
	};
	/**
	 * 通过角色的rid去查询他所拥有的菜单（所有菜单 他拥有的菜单第四列 为1）
	 */
	public List<Object[]> loadRoleMenus(int rid) {
		String sql ="select m.mid, m.mname ,(SELECT 1 from rolemenu rm  WHERE rm.mid = m.mid and  rm.rid = ?) as num  from   menu m  where parentid >0 ";
		List<Object[]>menuList = DBUtil.DQL(sql, new Object[]{rid});
		return menuList;
	}
	/**
	 * 执行修改角色所拥有的菜单------------------------------------
	 */
	public void modifyRoleMenus(int roleRid,String [] mid){
		//先执行删除原来角色所对应的菜单选项
		String sql = "delete from rolemenu where rid = ?";
		DBUtil.DML(sql, new Object []{roleRid});
		//然后执行插入数据到原来的角色里面
	
		sql ="insert into rolemenu(rid,mid) values(?,?) ";
		for(int i = 0 ;i <mid.length;i++ ){
			if(""==mid[i]){
				return;
			}
			DBUtil.DML(sql,new Object[]{roleRid,mid[i]});
		}
	}
	/**
	 * 查询所有的渠道
	 */
	public List<ChannelInfo> loadAllChannels(){
		String sql = "select * from channelinfo ";
		List<Object[]>list = DBUtil.DQL(sql, null);
		List<ChannelInfo> list1= new ArrayList<ChannelInfo>();
		for(Object[] m : list){
			ChannelInfo channelInfo = new ChannelInfo((Integer)m[0], String.valueOf(m[1]), (Double)m[2], String.valueOf(m[3]), (Integer)m[4]);
			list1.add(channelInfo);
		}
		return list1;
	};
	/**
	 * 添加渠道
	 */
	public void addChannel(String ciname, String costmoney, String developtime){
		String sql = "insert into channelinfo(ciname, costmoney,developtime) values(?,?,?) ";
		DBUtil.DML(sql,new Object[]{ciname, costmoney,developtime});
	}
	/**
	 * 删除某个渠道 或者一些渠道 
	 */
	public void deleteChannels(String[] ciids){
		String sql="update channelinfo set iseffective = 0  where ciid = ?";
		for(String m : ciids){
			DBUtil.DML(sql, new Object[]{m});
		}
	}
	/**
	 * 查询所有的部门的老大 的名字 用于添加部门的时候选择部门老大
	 * @param 
	 * @returnSELECT 
	 */
	public List<User> loadAllDepartmentBosses(){
		String sql ="SELECT u.* from user u ,userrole ur,role r  WHERE u.uid = ur.uid and ur.rid = r.rid  and r.rname='主管' ";
		List<Object[]>list = DBUtil.DQL(sql, null);
		List<User> list1= new ArrayList<User>();
		for(Object[] n : list){
			User departmentBosses = new User((Integer)n[0], String.valueOf(n[1]), String.valueOf(n[2]), String.valueOf(n[3]), String.valueOf(n[4]), String.valueOf(n[5]), String.valueOf(n[6]), String.valueOf(n[7]), String.valueOf(n[8]), String.valueOf(n[9]), String.valueOf(n[10]), String.valueOf(n[11]), (Integer)n[12], String.valueOf(n[13]), (Integer)n[14]);
			list1.add(departmentBosses);
		}
		return list1;
	};
	/**
	 * 添加部门
	 */
	public void addDepartment(String dname,String directorID,String description){
		   String sql = "insert into department( dname, directorID,description) values(?,?,?) ";
		   DBUtil.DML(sql,new Object[]{dname, directorID, description});
	};
	/**
	 * 删除某个部门或者一些部门
	 */
	public void deleteDepartments(String[] did){
		String sql="update department set iseffective = 0  where did = ?";
		for(String m : did){
			DBUtil.DML(sql, new Object[]{m});
		}
	}
	/**
	 * 查看所有的产品信息
	 */
	public List<Product> loadAllProducts(){
		String sql = "select * from product where iseffective = 1 ";
		List<Object[]>list = DBUtil.DQL(sql, null);
		List<Product> list1= new ArrayList<Product>();
		for(Object[] m : list){
			Product product = new Product((Integer)m[0], String.valueOf(m[1]), String.valueOf(m[2]), String.valueOf(m[3]), String.valueOf(m[4]), (Integer)m[5]);
			list1.add(product);
		}
		return list1;
	};
	/**
	 * 添加产品
	 */
	public void addProduct(String pname,String recommendDoctor,String productDescription,String createTime){
		 String sql = "insert into product( pname, recommendDoctor, productDescription,createTime) values(?,?,?,?) ";
		   DBUtil.DML(sql,new Object[]{pname, recommendDoctor, productDescription,createTime});
	};
	/**
	 * 删除某个产品或者一些产品
	 */
	public void deleteProducts(String[] pid){
		String sql="update product set iseffective = 0  where pid = ?";
		for(String m : pid){
			DBUtil.DML(sql, new Object[]{m});
		}
	};
	/**
	 * 查看美容师个人客户（加载自己美容师的客户 通过传过去自己的uid去查询自己的客户）
	 */
	public Page<CustomerVo> loadAllMyCustomers(int pageNo,int  pageSize ,String sql,String uid){
		String sql1 = "SELECT c.cid, c.interviewnumber,c.cname,c.csex,c.cphone1,c.cphone2,(SELECT ci.ciname from channelinfo ci WHERE c.ciid = ci.ciid ), c.cqq ,(SELECT p.pname from product p WHERE p.pid=c.pid  ),(SELECT u.username from user u where u.uid=?),c.firstdealmoney ,c.dealallmoney,c.address,c.cometostoretime,c.cometocustomertime   from customer c where  c.uid = ? ";
		String sql2=" limit ?,?";
		String sql3;
		if(sql !=null&&sql.length()>0){
			 sql3=sql1+sql+sql2;
		}else{
			 sql3=sql1+sql2;
		}
		List<Object[]> list = DBUtil.DQL(sql3, new Object[]{uid,uid,(pageNo-1)*pageSize,pageSize});
		List<CustomerVo> dateList= new ArrayList<CustomerVo>();
		for(Object[] n : list){
			CustomerVo customerVo = new CustomerVo((Integer)n[0], String.valueOf(n[1]), String.valueOf(n[2]), (Integer)n[3],  String.valueOf(n[4]),  String.valueOf(n[5]),  String.valueOf(n[6]),  String.valueOf(n[7]),  String.valueOf(n[8]),  String.valueOf(n[9]),  String.valueOf(n[10]),  String.valueOf(n[11]),  String.valueOf(n[12]),  String.valueOf(n[13]), String.valueOf(n[14]));
			dateList.add(customerVo);
		 }
		
		sql = "select count(*) from customer c ";
		list=DBUtil.DQL(sql,null);
		long total = (Long)list.get(0)[0];
		return new Page<CustomerVo>(pageNo, pageSize, dateList, total);
	}
}
