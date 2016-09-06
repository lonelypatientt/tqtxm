package com.web.dao;

import java.util.List;

import com.web.entity.ChannelInfo;
import com.web.entity.Department;
import com.web.entity.Menu;
import com.web.entity.Product;
import com.web.entity.Role;
import com.web.entity.User;
import com.web.util.Page;
import com.web.vo.DepartmentVo;
import com.web.vo.MenuVo;
import com.web.vo.UserVo;
import com.web.vo.UserVo2;

public interface UserDao {
	/**
	 * 通过用户名去查询此用户 登录界面那个用户名调到数据来查询
	 * @param userName
	 * @return null 或者 user对象
	 */
	public User loadUserByName(String userName);
	
	/**
	 * 返回当前用户他所拥有的菜单 他可以看什么
	 * @param 
	 * @return
	 */
	public List<Menu> loadUserMenu(String userName);
	
	/**
	 * 查询所有的用户（经理，主管，咨询师）展现到网页上面去 要用uservo
	 */
	public Page<UserVo> loadAllUsers(int pageNo, int pageSize,String sql);
	
	/**
	 * 查询所有的部门
	 */
	public List<DepartmentVo> loadAllDepartments();
	
	/**
	 * 查询所有的角色 返回所有的角色的集合
	 */
	public List<Role> loadAllRoles();
	
	/**
	 * 添加用户 并且添加此用户的角色
	 */
	public  void  addUser(String uNO,String userName,String userPassWord,String realName,String phone,String email,String  QQ,String weChatNo,String emergencyContactPerson,String emergencyContactPhone,int did,String EntryTime, int iseffective,int rid );
	
	/**
	 * 删除某个用户 或者一些用户 
	 */
	public void deleteUsers(String[] uid);
	
	/**
	 * 查询用户的全部资料 用于修改用户的时候资料回填 要用uservo2
	 * @param uid
	 */
	public UserVo2 toModifyUser(String uid);
	
	/**
	 * 返回一个所有的菜单集合
	 * @param 
	 * @return
	 */
	public Page<MenuVo> loadAllMenus(int pageNo , int pageSize);

	/**
	 * 返回一个所有一级和二级的菜单集合
	 * @param 
	 * @return
	 */
	public List<Menu> loadAll12Menu();
	
	/**
	 * 添加菜单
	 */
	public void addMenu(String mname,String url,int isshow,int parentid  );
	
	/**
	 * 删除某个菜单 或者一些菜单 
	 */
	public void deleteMenus(String[] mid);
	
	/**
	 * 查询菜单的资料 用于修改菜单的时候资料回填
	 * @param mid
	 */
	public Menu toModifyMenu(String mid);
	
	/**
	 * 查询不同角色拥有的不同的菜单，就是不同角色能操作的菜单（所有菜单 他拥有的菜单后面第四列是1）
	 */
	public List<Object[]> loadRoleMenus(int rid);
	
	/**
	 * 执行修改角色所拥有的菜单
	 */
	public void modifyRoleMenus(int roleRid,String [] mid);
	
	/**
	 * 查询所有的部门
	 */
	public List<ChannelInfo> loadAllChannels();
	/**
	 * 添加渠道
	 */
	public void addChannel(String ciname, String costmoney, String developtime);
	/**
	 * 删除某个渠道 或者一些渠道 
	 */
	public void deleteChannels(String[] ciids);
	/**
	 * 查询所有的部门的老大 的名字 用于添加部门的时候选择部门老大
	 * @param 
	 * @return
	 */
	public List<User> loadAllDepartmentBosses();
	/**
	 * 添加部门
	 */
	public void addDepartment(String dname,String directorID,String description);
	/**
	 * 删除某个部门或者一些部门
	 */
	public void deleteDepartments(String[] did);
	/**
	 * 查看所有的产品信息
	 */
	public List<Product> loadAllProducts();
	/**
	 * 添加产品
	 */
	public void addProduct(String pname,String recommendDoctor,String productDescription,String createTime);
	/**
	 * 删除某个产品或者一些产品
	 */
	public void deleteProducts(String[] pid);
}

	
