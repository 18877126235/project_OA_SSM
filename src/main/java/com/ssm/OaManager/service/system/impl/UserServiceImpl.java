package com.ssm.OaManager.service.system.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.OaManager.dao.system.UserDao;
import com.ssm.OaManager.entity.RuMiddle;
import com.ssm.OaManager.entity.User;
import com.ssm.OaManager.entity.privilege.Role;
import com.ssm.OaManager.service.system.UserService;
import com.ssm.OaManager.utils.MailUitls;
import com.ssm.OaManager.utils.MyAnnotation;
import com.ssm.OaManager.utils.PageBean;
import com.ssm.OaManager.utils.UUIDUtils;

/**
 * 用户业务层实现类
 * 
 * @author Administrator
 * 
 */
@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public User findById(Serializable id) {
		User user = this.userDao.findById(id);
		if (user == null) {
			user = new User();
		}
		return user;
	}

	@MyAnnotation(methodNames = "redact")
	public int insert(User entity) {
		return this.userDao.insert(entity);
	}

	@MyAnnotation(methodNames = "redact")
	public int updateUser(User entity) {
		return this.userDao.update(entity);
	}

	@MyAnnotation(methodNames = "redact")
	public int deleteById(Serializable id) {
		return this.userDao.deleteById(id);
	}

	@MyAnnotation(methodNames = "redact")
	public int delete(Serializable[] ids) {
		return this.userDao.delete(ids);
	}

	// 业务层完成用户注册代码:
	@MyAnnotation(methodNames = "redact")
	public int save(User user) {
		// 将数据存入到数据库
		user.setState(0); // 0:代表用户未激活. 1:代表用户已经激活.
		String code = UUIDUtils.getUUID() + UUIDUtils.getUUID();
		user.setCode(code); //设置激活码
		this.userDao.insertUser(user);
		// 默认设置普通用户？？？
		RuMiddle ruMiddle = new RuMiddle(); //这是什么对象？？（这是用户与角色的中间对象）
		ruMiddle.setUser(user);  //设置这个用户
		Role role = new Role(); //设置角色等级？？？
		role.setRoleId(2); //1号代表超级管理员，2号代表普通管理员
		ruMiddle.setRole(role);
		this.userDao.inserRuMiddle(ruMiddle);  //然后就代表了你是个普通管理员

		// 发送激活邮件;
		MailUitls mailUitls = new MailUitls();
		mailUitls.sendMail(user.getUserEmail(), code); //用户的邮箱号和激活码发送过去？？
		return 1;
	}

	// 根据激活码查询用户

	public User findByCode(String code) {
		return this.userDao.findByCode(code);

	}


	public List<User> findAll() {
		List<User> user = this.userDao.findAll();
		if (user == null) {
			user = new ArrayList<User>();
		}
		return user;
	}


	public User findByEmail(User user) {
		return this.userDao.findByEmail(user);
	}


	public User findByNp(User user) {
		return this.userDao.findByNp(user);
	}


	public void findPageBean(PageBean<User> pageBean) {
		// 获得总记录数
		Integer totalCount = this.userDao.findCount(pageBean);
		// 设置入分页对象
		if (totalCount != null) {
			pageBean.setTotalCount(totalCount);
		}
		if (pageBean.getCurrentPage() < 1) {
			pageBean.setCurrentPage(1);
		}

		if (pageBean.getCurrentPage() > pageBean.getTotalPage()) {
			pageBean.setCurrentPage(pageBean.getTotalPage());
		}
		pageBean.getParaMap().put("firstPage",
				(pageBean.getCurrentPage() - 1) * pageBean.getPageSize());
		pageBean.getParaMap().put("lastPage",
				pageBean.getCurrentPage() * pageBean.getPageSize());

		// 获得分页数据
		List<User> datas = this.userDao.findByPage(pageBean);

		pageBean.setDatas(datas);

	}

	@MyAnnotation(methodNames = "redact")
	public void insertUser(User user) {
		this.userDao.insertUser(user);
	}

	@MyAnnotation(methodNames = "impower")
	public int inserRuMiddle(RuMiddle ruMiddle, String[] roleIds) {
		
		userDao.deleteByUserId(ruMiddle.getUser().getUserId());  //删除掉？？？？(这么他妈的是删除tb_rumiddle的数据他妈的卧槽)
		
		
		
		for (String roleId : roleIds) {
			
			Role role = new Role();
			role.setRoleId(Integer.valueOf(roleId));  //设置角色id
			
			//System.out.println("看看这里能获取了没有"+role.getFunctions());
			
			ruMiddle.setRole(role);
			
			//System.out.println(ruMiddle.getRole().getFunctions());
			System.out.println("这个是什么呀哈"+roleId);
			
			this.userDao.inserRuMiddle(ruMiddle);  //经过该方法获得了Function的值
			
			
		}

		try{
			User user1 = userDao.findById(26); //得到先
			System.out.println("这是service之后的"+"   <<  "+user1.getRoles().get(0).getFunctions().get(1).getFunDescripti()+"   >>  "+"看看是不是获取了");}catch(Exception e) {
			System.out.println("这他妈service这是执行授权之后的￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥，这里报异常了");
		}
		
		return 1;

	}

	public List<User> findByDeptId(User user) {
		return this.userDao.findByDeptId(user);
	}

}
