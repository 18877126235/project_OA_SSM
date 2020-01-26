package com.ssm.OaManager.web.hrm;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ssm.OaManager.entity.User;
import com.ssm.OaManager.entity.hrm.Department;
import com.ssm.OaManager.entity.hrm.Employee;
import com.ssm.OaManager.entity.hrm.Position;
import com.ssm.OaManager.service.hrm.DeptService;
import com.ssm.OaManager.service.hrm.EmployeeService;
import com.ssm.OaManager.service.hrm.PositionService;
import com.ssm.OaManager.service.system.UserService;
import com.ssm.OaManager.utils.PageBean;
import com.ssm.OaManager.utils.PrivilegeFilter;
import org.apache.commons.beanutils.BeanUtils;


@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Resource
	private PrivilegeFilter privilegeFilter;  //这是什么对象？？？？，这他妈是切面对象
	
	@Resource
	private DeptService deptService;
	
	@Resource
	private PositionService positionService;

	@Resource
	private UserService userService; 
	
	/** 分页查询 显示所有员工的列表*/
	@RequestMapping("/hrm/getEmployeeByPage.action")
	public String getEmployByPage(HttpServletRequest request) {
		//System.out.println(".................来了老弟.............");
		
		setPrivilegeFilterUser(request);
		PageBean<Employee> pageBean = new PageBean<Employee>();
		List<Employee> emps = employeeService.findByPage(pageBean);
		User user = new User();
		//遍历匹配用户id
		for (Employee employee : emps) {
			
			user = userService.findById(employee.getEmp_user_id());
			
			employee.setUser(user);
			
		}
		request.setAttribute("emps", emps);
		return "/pages_hr/public_hr_employee.jsp";
	}

	/**
	 * 插入数据
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value="/hrm/insertEmployee.action",method=RequestMethod.POST)
	public String inse0rtEmp(HttpServletRequest request) throws Exception {

//		this.setPrivilegeFilterUser(request);  //获取当前用户????
		
		Employee emp = this.editEmp(request);  //获取封装的员工对象
		
//		System.out.println(emp.getEmpAddress());
//		System.out.println(emp.getEmpName());
//		System.out.println(emp.getEmpNumber());
//		System.out.println(emp.getEmpSex());
//		System.out.println(emp.getEmpTelephone());
//		System.out.println(emp.getEmpAge());
//		System.out.println("这是部门id"+emp.getDep().getDeptId());
//		System.out.println("这是职位id"+emp.getPos().getPosId());
		
		//System.out.println(emp.getEmpPhotoUrl());
		
		
//		System.out.println("这是该员工的部门"+emp.getDep().getDeptId());  //部门为空？？
//		System.out.println("这是该员工的职位"+emp.getPos().getPosId());  //职位
//		System.out.println("获取员工号？？？"+emp.getEmpNumber());
//		System.out.println("该员工的用户id"+emp.getUser().getUserId());
//		System.out.println("该员工的部门id"+emp.getDep().getDeptId());
//		System.out.println("该员工的职位id"+emp.getPos().getPosId());
		
		
		
		
		
		//deptService.findAll(); //部门查找？？？

		int num = 0;
		
		try { //捕获到异常说明权限不足哈哈哈
			num = employeeService.insert(emp);  //把该员工插入数据库
		} catch (Exception e) {
			// TODO: handle exception
			num = -1;
		}
		
		if (num == -1) {
			request.setAttribute("result", "权限不足");
		}
		
		//再次查询然后转发到页面??
		return "/hrm/getEmployeeByPage.action";
	}

	@RequestMapping("go.action")
	public String go(HttpServletRequest request){
		
		List<Department> depts = deptService.findAll();
		List<Position> positions = positionService .findAll();
		
		request.setAttribute("depts", depts);
		request.setAttribute("positions", positions);
		
		return "/pages_hr/public_hr_employee_add.jsp";
		
	}
	
	/**
	 * 封装员工数据
	 * 
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	public Employee editEmp(HttpServletRequest request) throws Exception {
		/*Employee emp = new Employee();
		// 将当前上下文初始化给多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有 enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将rquest 变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

			// 获得所有页面参数
			
			 * private Integer empId; private String empNumber; private String
			 * empName;
			 * 
			 * private String empSex; private Integer empAge; private Date
			 * empBrithday;
			 * 
			 * private Double empSalary; private String empTelephone; private
			 * String empAddress; private Date empEntryDate; private Integer
			 * MaritalStatus; private String empPhotoUrl;
			 * 
			 * private User user;
			 * 
			 * private Integer posId; private Integer depId; private Department
			 * dep; private Position pos;
			 

			String empNumber = multiRequest.getParameter("empNumber");
			String empName = multiRequest.getParameter("empName");
			String empSex = multiRequest.getParameter("empSex");
			String empAge = multiRequest.getParameter("empAge");
			String empBrithday = multiRequest.getParameter("empBrithday");
			String empSalary = multiRequest.getParameter("empSalary");
			String empTelephone = multiRequest.getParameter("empTelephone");
			String empAddress = multiRequest.getParameter("empAddress");
			String empEntryDate = multiRequest.getParameter("empEntryDate");
			String MaritalStatus = multiRequest.getParameter("maritalStatus");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			emp.setEmpNumber(empNumber);
			emp.setEmpName(empName);
			emp.setEmpSex(empSex);
			emp.setEmpAge(Integer.parseInt(empAge));
			emp.setEmpBrithday(sdf.parse(empBrithday));
			emp.setEmpSalary(new Double(empSalary));
			emp.setEmpTelephone(empTelephone);
			emp.setEmpAddress(empAddress);
			emp.setEmpEntryDate(sdf.parse(empEntryDate));
			emp.setMaritalStatus(Integer.valueOf(MaritalStatus));
			// 根据value值 获得 文件对象
			MultipartFile file = multiRequest.getFile("empPhotoUrl");

			if (file != null && !file.isEmpty()) {
				// 上传路径
				String path = multiRequest.getSession().getServletContext()
						.getRealPath("/")
						+ "upload/" + file.getOriginalFilename();
				// 上传文件
				file.transferTo(new File(path));
				// 赋值路径信息
				emp.setEmpPhotoUrl(file.getOriginalFilename());
			}
		}
		return emp;*/
		//获取已登录用户 
				//User user = (User) request.getSession().getAttribute("user");
				Employee emp = new Employee();
				
				Department dept = new Department(); //定义部门
				Position position = new Position();	//定义职位
				
				//拖过字节码文件后去official和flow的属性
				Field[] empFields = emp.getClass().getDeclaredFields();

				// 图片上传
				// 1.创建工厂对象
				FileItemFactory factory = new DiskFileItemFactory();
				// 2.文件上传核心工具类
				ServletFileUpload upload = new ServletFileUpload(factory);
				// 3.设置上传 大小限制参数
				upload.setFileSizeMax(100000 * 1024 * 1024); // 单个文件大小限制
				upload.setSizeMax(50000000 * 1024 * 1024); // 总文件大小限制
				upload.setHeaderEncoding("UTF-8"); // 对中文文件编码处理

				// 判断是否是上传的表单
				// <form 是否添加了此 enctype="multipart/form-data"
				if (upload.isMultipartContent(request)) {
					// 把请求数据转换为list集合
					List<FileItem> list = upload.parseRequest(request);

					// FileItem 代表请求的 内容（数据） 员工姓名、员工性别....
					for (FileItem item : list) {
						//System.out.println(item.getFieldName()+"...");  //打印试试看
						// jsp name属性值
						String name = item.getFieldName();
						// jsp 属性对应的value值
						String value = new String(item.getString().getBytes(
								"iso8859-1"), "utf-8");
//						System.out.println("这是name属性的名称："+name);
//						System.out.println("获取表单的输入看看："+value);
//						System.out.println("这是getname的值："+item.getName());
						// 判断 是否上传 tiem
						if (!item.isFormField()) {

							if (item.getName() != null
									&& !"".equals(item.getName())) {
								// 获取tomcat，所在工程的，真实绝对路径
								String realPath = request.getSession()
										.getServletContext().getRealPath("/");
								// 调用上传 把item的文件内容 写入 另外一个文件
								// 创建文件
								File newFile = new File(realPath + "/images",
										item.getName());
								item.write(newFile);
								item.delete();// 删除临时文件
								String eimg = "/images/" + item.getName();// 数据库保存字段
								emp.setEmpPhotoUrl(eimg);// 给emp对象 eimg赋值
							}
						} else { // 普通的item
							
							if(item.getFieldName().equals("empBrithday")||item.getFieldName().equals("empEntryDate")){
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								Date parse = sdf.parse(value);
								BeanUtils.setProperty(emp, name, parse);
							}else{
								if(item.getFieldName().equals("deptId")){
									dept.setDeptId(Integer.parseInt(value));
									emp.setDep(dept);
									//System.out.println("你大爷的但那哦没复制成功？？？"+emp.getDep().getDeptId());
								}else
								if(item.getFieldName().equals("postId")){
									position.setPosId(Integer.parseInt(value));
									emp.setPos(position);
									//System.out.println("你大爷的但那哦没复制成功？？？"+emp.getPos().getPosId());
								}else{
									System.out.println(value);
									BeanUtils.setProperty(emp, name, value);  //调用自定义的工具类来赋值
								}
							}
							
							
							/*for(Field field : empFields){
								
								System.out.println("这是去她蛮的getName:"+field.getName());
								
								if(field.getName().equals(name)){
									if(field.getName().equals("empBrithday")||field.getName().equals("empEntryDate")){
										SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
										Date parse = sdf.parse(value);
										
										BeanUtils.setProperty(emp, name, parse);
									}else{
										if(field.getName().equals("deptId")){
											dept.setDeptId(Integer.parseInt(value));
											emp.setDep(dept);
											System.out.println("你大爷的但那哦没复制成功？？？"+emp.getDep().getDeptId());
										}else
										if(field.getName().equals("posId")){
											position.setPosId(Integer.parseInt(value));
											emp.setPos(position);
											System.out.println("你大爷的但那哦没复制成功？？？"+emp.getPos().getClass());
										}else{
											BeanUtils.setProperty(emp, name, value);
										}
									}
								}
							}*/
						}
					}
				}
		
		return emp;
	}

	@RequestMapping("/hrm/deleteEmp.action")
	public String deleteEmp(Integer empId, HttpServletRequest request) {
		setPrivilegeFilterUser(request);
		int num = 0;
		
		try {
			num = employeeService.deleteById(empId);
			
		} catch (Exception e) {
			// TODO: handle exception
			num = -1;
		}
		
		if (num == -1) {
			request.setAttribute("result", "权限不足");
		}
		return "/hrm/getEmployeeByPage.action";
	}

	//查找而已
	@RequestMapping("/hrm/findEmpById.action")
	public String findEmp(Integer empId, HttpServletRequest request) {
		setPrivilegeFilterUser(request);
		Employee emp = employeeService.findById(empId);
		
		List<Department> depts = deptService.findAll();
		List<Position> positions = positionService .findAll();
		
		request.setAttribute("emp", emp);
		request.setAttribute("depts", depts);
		request.setAttribute("positions", positions);
		
		return "/pages_hr/public_hr_employee_edit.jsp";
	}

	//修改部门
	@RequestMapping("/hrm/updateEmployee.action")
	public String updateEmp(HttpServletRequest request) throws Exception {
		setPrivilegeFilterUser(request);
		Employee emp = editEmp(request);
		int num = 0;
		
		try {
			num = employeeService.updateEmp(emp);
		} catch (Exception e) {
			// TODO: handle exception
			num = -1;
		}
		
		if (num == -1) {
			request.setAttribute("result", "权限不足");
		}
		return "/hrm/getEmployeeByPage.action";
	}

	//切面妈卖批坑死我了
	public void setPrivilegeFilterUser(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user"); //先获取当前登录的用户
		this.privilegeFilter.setUser(user);
	}
	

}
