package com.ssm.OaManager.web.system;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.SocketUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.ssm.OaManager.entity.RuMiddle;
import com.ssm.OaManager.entity.User;
import com.ssm.OaManager.entity.hrm.Department;
import com.ssm.OaManager.entity.hrm.Employee;
import com.ssm.OaManager.entity.privilege.Role;
import com.ssm.OaManager.service.hrm.EmployeeService;
import com.ssm.OaManager.service.system.RoleService;
import com.ssm.OaManager.service.system.UserService;
import com.ssm.OaManager.utils.MailUitls;
import com.ssm.OaManager.utils.PageBean;
import com.ssm.OaManager.utils.PrivilegeFilter;
import com.ssm.OaManager.utils.ValidateCode;
import com.ssm.OaManager.web.BaseController;
import org.apache.commons.beanutils.BeanUtils;

@Controller
public class UserController extends BaseController {

	@Resource
	private UserService userService;// 用户业务层
	@Resource
	private EmployeeService employeeService;// 员工业务层

	@Resource
	private RoleService roleService;

	@Resource
	private PrivilegeFilter privilegeFilter;

	public void setPrivilegeFilterUser(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		this.privilegeFilter.setUser(user);
	}

	// 查询登录
	@RequestMapping("/view/user/findByNp.action")
	public String findByNp(String str,User user, String inpcode, Model model,HttpServletRequest request) {
		
		
		//*****免登录代码****************************
		//List<User> lists = userService.findAll();
//		for (User user2 : lists) {
//			System.out.println(user2);
//		}
//		User MianUser = new User();
//		MianUser.setUserName("jingli");
//		MianUser.setUserPassword("123");
//		request.getSession().setAttribute("user", MianUser);
		//******************************************
		//System.out.println("这是用户输入的对象"+user);
		String viewName;
		try {
			User sessionUser = (User) request.getSession().getAttribute("user");
			viewName = "redirect:/index.jsp";

			// 判断session里面是否有user
			if (sessionUser != null) {
				user = sessionUser;
			} 
			//*********************面验证码验证****************
//			else {
//				if (!inpcode.equalsIgnoreCase(ValidateCode.code)) {
//					request.setAttribute("result5", "验证码错误");
//					return "/success.jsp";
//				}
//				
//				
//			}
			
			User resultUser = userService.findByNp(user);
			//******************************************
			//System.out.println("这到底是谁干的呀！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
			
			if (resultUser != null) {
				if (resultUser.getState() != 1) {
					request.setAttribute("result6", "账号未激活");
					return "/success.jsp";
				}
				request.getSession().setAttribute("user", resultUser);
				request.getSession().setAttribute("inpcode", inpcode);
			} else {
				request.setAttribute("message", "用户名或密码错误");
				return "/success.jsp";
			}
			if(str!=null&&!str.equals("")){
				if(str.equals("sq")){
					viewName="/page/user/fillByPage.action";
				}
				
				if(str.equals("js")){
					viewName="/sys_privilege/findByFunPage.action";
				}
				if(str.equals("ur")){
					viewName="/sys_privilege/findByRolePage.action";
				}
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return "redirect:/page/404.jsp";
		}
		
		return viewName;
		
	}

	// 异步验证用户名
	// 查询用户名
	@RequestMapping("/view/user/findByName.action")
	public void findByName(User user, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			User resultUser = userService.findByEmail(user);

			if (resultUser != null) {
				request.getSession().setAttribute("user", resultUser);
				// 发送给客户端
				response.getWriter().write("1");
			} else {
				// 发送给客户端
				response.getWriter().write("2");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用户注册的方法:
	 */
	@RequestMapping("/view/user/regist.action")
	public String regist(User user, HttpServletRequest request,
		HttpServletResponse response) {
		
		//System.out.println(user);
		
		//先获取输入的员工的名字
		String empName = request.getParameter("emp_name");
		Employee e = new Employee();
		e.setEmpName(empName);
		Employee emp = employeeService.findEmpIdByEmpName(e);  //获取到名字相关的员工对象
		emp.setUser(user); //员工设置用户，
		user.setEmp(emp); //用户设置员工
		Department dep = emp.getDep();
		user.setDep(dep); //设置用户的部门
		//System.out.println("id是："+emp.getEmpId()+"名字是："+emp.getEmpName()+"部门号是:"+emp.getDep().getDeptId());
		
		
		//去数据库中查找看看
		// 注册
		userService.save(user);
		//Employee emp2 = employeeService.findById(user.getEmp().getEmpId());  //根据这个id从数据库中查找出来
		//emp2.setEmpId(user.getEmp().getEmpId());
		//emp2.setUser(user);  //给他设置用户
		
		employeeService.updateEmp(emp);  //然后修改数据库中的那个对象
		request.setAttribute("result0", "成功！");
		return "/success.jsp";
	}

	/**
	 * 用户激活的方法
	 * 
	 * @return
	 */
	@RequestMapping("/view/user/active.action")
	public String active(String code, HttpServletRequest request, //User user, 
			HttpServletResponse response) {
		//System.out.println("来了老弟激活码。。。。。。。。。。。");
		// 根据激活码查询用户:
		User existUser = userService.findByCode(code);
		// 判断
		if (existUser == null) {
			// 激活码错误的
			request.setAttribute("result1", "激活失败:激活码错误!");
			return "/success.jsp";
		} else {
			// 激活成功
			request.setAttribute("result2", "激活成功:请去登录!");
			// 修改用户的状态
			existUser.setState(1); //1代表激活
			// 清空code防止二次激活
			existUser.setCode(""); //激活码消失了哈哈哈
			userService.updateUser(existUser);
			return "/success.jsp";
		}

	}

	/**
	 * 根据邮箱重置用户密码
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/view/user/findUser.action")
	public String findUser(User user, HttpServletRequest request,
			HttpServletResponse response) {
		User finduser = userService.findByEmail(user);
		// 判断用户是否存在
		if (finduser == null) {
			request.setAttribute("findUser", "该邮箱不存在!");
			return "/success.jsp";
		} else {
			// 放入Application作用域
			ServletContext application = request.getSession()
					.getServletContext();
			application.setAttribute("finduser", finduser);
			// 发送邮件重置密码;
			MailUitls mailUitls = new MailUitls();
			mailUitls.sendMailFindUser(user.getUserEmail());
			request.setAttribute("result3", "邮箱发送成功!");
		}
		return "/success.jsp";

	}

	// 异步验证邮箱
	@RequestMapping("/view/user/findEmail.action")
	public void findEmail(User user, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			User finduser = userService.findByEmail(user);
			// 判断邮箱是否存在
			if (finduser != null) {
				response.getWriter().write("3");
			} else {
				// 发送给客户端
				response.getWriter().write("4");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用户重置密码
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/view/user/updateUser.action")
	public String updateUser(User user, HttpServletRequest request,
			HttpServletResponse response) {
		// 修改用户
		userService.updateUser(user);
		ServletContext application = request.getSession().getServletContext();
		// 删除整个web的作用域finduser
		// 销毁Application作用域
		application.removeAttribute("finduser");
		// 删除session里的验证码
		request.getSession().removeAttribute("inpcode");

		request.setAttribute("result4", "密码重置成功");
		return "/success.jsp";
	}

	/**
	 * 解锁用户 异步请求
	 * 
	 * @param userName
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/view/user/lockuser.action")
	public void lockUser(String pwd, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			User user = new User();
			user = (User) request.getSession().getAttribute("user");
			user.setUserName(user.getUserName());
			user.setUserPassword(pwd);
			User resultUser = userService.findByNp(user);
			if (resultUser != null) {
				// 发送给客户端
				response.getWriter().write("1");
			} else {
				// 发送给客户端
				response.getWriter().write("2");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 退出登录
	@RequestMapping("/view/user/quitUser.action")
	public String quitUser(HttpServletRequest request) {
		try {
			request.getSession().removeAttribute("user");
			request.getSession().removeAttribute("inpcode");
		} catch (Exception e) {
			return "redirect:/page/404.jsp";
		}
		return "redirect:/login.jsp";
	}

	/**
	 * login.jsp页面的验证码生成
	 */
	@RequestMapping("/view/user/checkCode.action")
	public void ajaxValidateCode(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
		response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expire", 0);
		ValidateCode randomValidateCode = new ValidateCode();
		try {
			ValidateCode.code = randomValidateCode.getRandcode(request,
					response);// 输出图片方法
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 图片上传
	 */
	public Employee upload(HttpServletRequest request) {
		Employee emp = new Employee();// 保存对象
		try {
			// 图片上传
			// 1.创建工厂对象
			FileItemFactory factory = new DiskFileItemFactory();

			// 2.文件上传核心工具类
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 3.设置上传 大小限制参数
			upload.setFileSizeMax(10 * 1024 * 1024); // 单个文件大小限制
			upload.setSizeMax(50 * 1024 * 1024); // 总文件大小限制
			upload.setHeaderEncoding("UTF-8"); // 对中文文件编码处理

			// 判断是否是上传的表单
			// <form 是否添加了此 enctype="multipart/form-data"
			if (upload.isMultipartContent(request)) {
				// 把请求数据转换为list集合
				List<FileItem> list = upload.parseRequest(request);

				// FileItem 代表请求的 内容（数据） 员工姓名、员工性别....
				for (FileItem item : list) {
					// jsp name属性值
					String name = item.getFieldName();
					// jsp 属性对应的value值
					String value = new String(item.getString().getBytes(
							"iso8859-1"), "utf-8");
					// 判断 是否上传 tiem
					if (!item.isFormField()) {

						if (item.getName() != null
								&& !"".equals(item.getName())) {
							// 获取tomcat，所在工程的，真实绝对路径
							String realPath = request.getSession()
									.getServletContext().getRealPath("/");
							// 调用上传 把item的文件内容 写入 另外一个文件
							// 创建文件
							File newFile = new File(realPath + "//",
									item.getName());
							item.write(newFile);
							item.delete();// 删除临时文件

							String eimg = "/images/" + item.getName();// 数据库保存字段

							emp.setEmpPhotoUrl(eimg);// 给emp对象 eimg赋值
						}
					} else { // 普通的item
						if (name.equals("userId")) {
							User user = new User();
							user.setUserId(Integer.valueOf(value));
							emp.setUser(user);
						}
						BeanUtils.setProperty(emp, name, value);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return emp;
	}

	// 修改个人头像
	@RequestMapping("/view/user/updateUserImg.action")
	public String updateUserImg(HttpServletRequest request,
			HttpServletResponse response) {
		// 给权限设置属性
		Employee emp = upload(request);
		this.employeeService.updateImg(emp);
		return "/view/user/findByNp.action";
	}

	// 异步查询密码
	@RequestMapping("/view/user/finduserPwd.action")
	public void findUserPwd(String userPassword, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			User user = new User();
			user = (User) request.getSession().getAttribute("user");
			user.setUserName(user.getUserName());
			user.setUserPassword(userPassword);
			User resultUser = userService.findByNp(user);
			if (resultUser != null) {
				// 发送给客户端
				response.getWriter().write("1");
			} else {
				// 发送给客户端
				response.getWriter().write(2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * 修改密码
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/view/user/updateUserPwd.action")
	public String updateUserPwd(String userPassword,
			HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		user.setUserPassword(userPassword);
		// 修改用户密码
		userService.updateUser(user);
		return "redirect:/index.jsp";
	}

	// 修改员工的个人信息
	@RequestMapping("/view/emp/updateEmpInforById.action")
	public String updateEmpInforById(Employee emp, HttpServletRequest request,
			HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		emp.setUser(user);
		// 执行修改
		this.employeeService.updateImg(emp);
		return "/view/user/findByNp.action";
	}

	// 异步验证员工
	// 查询员工
	@RequestMapping("/view/user/findEmpIdByEmpName.action")
	public void findEmpIdByEmpName(String empName, HttpServletRequest request,
			HttpServletResponse response) {
		
		System.out.println("这是文本输入框的值:"+empName); 
		
		try {
			//这里不太理解为什么要转化成byte??????
			//String value = new String(empName.getBytes("iso8859-1"), "utf-8");
			Employee emp = new Employee();
			emp.setEmpName(empName);
			Employee resultEmp = employeeService.findEmpIdByEmpName(emp);
			System.out.println("这是从数据库查询出来的员工:"+resultEmp);
			
			
			String result = JSON.toJSONString(resultEmp);
			if (resultEmp != null) {
				// 发送给客户端
				response.getWriter().write(result);
			} else {
				// 发送给客户端
				response.getWriter().write("6");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 分页查询 //查询员工（智慧显示已经注册了的员工）
	@RequestMapping("/page/user/fillByPage.action")
	public String fillByPage(String usName, String currentPage, Model model,
			HttpServletRequest request) {
		setPrivilegeFilterUser(request);
		try {
			User user = (User) request.getSession().getAttribute("user");
			// 给权限设置属性
			this.privilegeFilter.setUser(user);
			Map<String, Object> map = new HashMap<String, Object>();
			// 创建分页对象
			PageBean<User> pageBean = new PageBean<User>();
			map.put("userId", user.getUserId());
			pageBean.setParaMap(map);
			pageBean.setPageSize(1000);
			// 设置分页对象其他属性
			userService.findPageBean(pageBean);

			if (pageBean.getDatas() == null) {
				request.setAttribute("result", "权限不足");
			}
			model.addAttribute("pageBean", pageBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/sys_privilege/oa_user_list.jsp";
	}

	// 查询
	@RequestMapping("/view/user/findByUserId.action")
	public String findByUserId(Integer userId, String str,
			HttpServletRequest request, HttpServletResponse response) {
		setPrivilegeFilterUser(request);
		List<Object> obj = new ArrayList<Object>();
		User user = this.userService.findById(userId);
		obj.add(user);
		if (str != null) {
			List<Role> roles = this.roleService.findAll();
			for (Role role : user.getRoles()) {
				for (int i = 0; i < roles.size(); i++) {
					Role role2 = roles.get(i);
					if (role.getRoleId() == role2.getRoleId()) {
						roles.remove(i);
					}
				}
			}
			obj.add(roles);
		}
		try {
			if (user == null) {
				response.getWriter().write("1");
			} else {
				String result = JSON.toJSONString(obj);
				response.getWriter().write(result);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	// 授权
	@RequestMapping("/view/emp/insertRuMiddle.action")
	public String insertRuMiddle(RuMiddle ruMiddle, HttpServletRequest request) {
		System.out.println("来了授权妈卖批*************************************1");
		setPrivilegeFilterUser(request);
		
	
		
		String[] roleIds = request.getParameterValues("roleId"); //获取两个name属性，万一两个都有呢（）
		
		
		
		//经过这个方法得到了Function属性
		int num = this.userService.inserRuMiddle(ruMiddle, roleIds);    
		
		
		
		if (num == -1) {
			request.setAttribute("result", "权限不足！");
			return "/page/user/fillByPage.action";
		}
		
		
		
		return "/view/user/findByNp.action?str=sq";
	}
}
