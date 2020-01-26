package com.ssm.OaManager.web.system;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssm.OaManager.entity.Schedule;
import com.ssm.OaManager.entity.User;
import com.ssm.OaManager.entity.hrm.Employee;
import com.ssm.OaManager.service.hrm.EmployeeService;
import com.ssm.OaManager.service.system.ScheduleService;
import com.ssm.OaManager.service.system.UserService;
import com.ssm.OaManager.utils.PageBean;
import com.ssm.OaManager.utils.PrivilegeFilter;
import com.ssm.OaManager.web.BaseController;

@Controller
public class ScheduleController extends BaseController {
	
	@Resource
	private ScheduleService scheduleService;

	@Resource
	private EmployeeService employeeService; //注入
	
	@Resource
	private UserService userService; 
	
	public void setScheduleService(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	@Resource
	private PrivilegeFilter privilegeFilter;

	public void setPrivilegeFilterUser(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		this.privilegeFilter.setUser(user);
	}

	/**
	 * 分页查询全部
	 * 
	 * @param currentPage
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/sys_schedule/findBySchedulePage.action")
	public String findByPage(String scheduleDate, Model model,HttpServletRequest request) {
		
		setPrivilegeFilterUser(request);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Date date = null;
		if (scheduleDate != null && !"".equals(scheduleDate)) {
			map.put("scheduleDate", scheduleDate);

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				date = (Date) format.parse(scheduleDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		User user = (User) request.getSession().getAttribute("user");
		map.put("userId", user.getUserId());
		List<Schedule> schedule = this.scheduleService.findByUserAll(map);

		model.addAttribute("scheduleDate", date);
		model.addAttribute("pageBean", schedule);
		return "/pages_personal/personal_calendars.jsp";

	}

	/**
	 * 查询个人的全部日程
	 * 
	 * @param currentPage
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/sys_schedule/findByScheduleAll.action")
	public String findAll(String scheduleDate, Model model,HttpServletRequest request) {
		setPrivilegeFilterUser(request);  //每次都特么的经过切面类，烦死了
		
		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User) request.getSession().getAttribute("user");
		map.put("userId", user.getUserId());
		List<Schedule> schedule = this.scheduleService.findByUserAll(map); //查找所有日程???
		model.addAttribute("pageBean", schedule);
		return "/pages_personal/public_schedule.jsp";

	}

	/**
	 * 添加
	 * 
	 * @param Schedule
	 * @param request
	 * @return
	 */
	@RequestMapping("/sys_schedule/saveSchedule.action")
	public String insert(Schedule schedule, HttpServletRequest request) {
		setPrivilegeFilterUser(request);
		User user = (User) request.getSession().getAttribute("user");
		schedule.setUser(user);
		int num = 0;
		
		try {
			num = this.scheduleService.insert(schedule);
		}catch (Exception e) {
			// TODO: handl/e exception
			num = -1;
		}
		
		if (num == -1) {
			request.setAttribute("result", "权限不足！");
		}
		return "/sys_schedulefindByScheduleAll.action";
	}

	/**
	 * 根据ID删除
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/sys_schedule/delete.action")
	public String deleteByIds(Integer id, HttpServletRequest request) {
		setPrivilegeFilterUser(request);
		int num = 0;
		
		try {
			 num = this.scheduleService.deleteById(id);
		} catch (Exception e) {
			// TODO: handle exception
			num = -1;
		}
		
		if (num == -1) {
			request.setAttribute("result", "权限不足！");
		}
		return "/sys_schedule/findByScheduleAll.action";

	}

	/**
	 * ajax 根据ID删除
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/sys_schedule/deleteSchedule.action")
	public void deleteById(Integer id, HttpServletRequest request,
			HttpServletResponse response) {
		setPrivilegeFilterUser(request);
		try {
			int num = this.scheduleService.deleteById(id);
			if (num > 0) {
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
	 * 查询
	 * 
	 * @param function
	 * @param model
	 * @return
	 */
	@RequestMapping("/sys_schedule/findByScheduleId.action")
	public String findById(Integer id, HttpServletResponse response,
			HttpServletRequest request) {

		Schedule schedule = this.scheduleService.findById(id);
		request.setAttribute("data", schedule);
		return "/pages_personal/public_schedule_edit.jsp";
	}

	/**
	 * 修改
	 * 
	 * @param Schedule
	 * @param request
	 * @return
	 */
	@RequestMapping("/sys_schedule/updateSchedule.action")
	public String update(Schedule schedule, HttpServletRequest request) {
		setPrivilegeFilterUser(request);
		User user = (User) request.getSession().getAttribute("user");
		schedule.setUser(user);
		
		int num = 0;
		try {
			num = this.scheduleService.update(schedule);
		} catch (Exception e) {
			// TODO: handle exception
			num = -1;
		}
		if (num == -1) {
			request.setAttribute("result", "权限不足！");
		}
		return "/sys_schedule/findByScheduleAll.action";
	}
	
	
	/*
	 * 点击员工日程表显示所有员工信息
	 */
	@RequestMapping("/sys_schedule/emp_Schedule.action")
	public String emp_Schedule(HttpServletRequest request) {
		
		//System.out.println("来了老弟哈哈哈哈。。。。。。。。。。。");
		
			//System.out.println(".................来了老弟.............");
			setPrivilegeFilterUser(request);
			PageBean<Employee> pageBean = new PageBean<Employee>();
			
			List<Employee> emps = employeeService.findByPage(pageBean); //获取员工集合
			List<Employee> emps2 = new ArrayList<Employee>(); //返回到前端的集合
		
			
//			User user = new User();
//			Employee employee = new Employee();
			//遍历匹配用户id
			for(int i = 0;i < emps.size(); i++) { //遇到问题，for循环无法遍历完集合内的数据
				
				//System.out.println("我是谁："+emps.get(i).getEmpName()+" ");
				
				Integer userId = emps.get(i).getEmp_user_id(); //获取关联的用户id
				User user = userService.findById(userId);
				Employee emp = emps.get(i); 
				//System.out.println("获取id试试看:"+userId);
				if(userId == null) {
					System.out.println(emps.get(i).getEmpName());
					
					//emps.remove(i); //把当前的移除不显示
					
				}else {
					//打印试试看
					//System.out.println(emps.get(i).getEmpName());
					emp.setUser(user);
					emps2.add(emp); //加入集合
					
				}
				
				//user.setUserId(userId); //设置用户id
//				User user2 = userService.findById(userId); //根据id查找用户
//				System.out.println(user2);
				
				
				
				/*if(emps.get(i).getEmp_user_id() == null) { //如果该员工没有注册
					System.out.println("这是空的员工名称是："+emps.get(i).getEmpName()+"  "+"用户名称是："+emps.get(i).getUser().getUserName());
					emps.remove(i);	
				}else {
					emps.get(i).setUser(userService.findById(emps.get(i).getEmp_user_id())); //设置用户
					
					System.out.println("员工名称是："+emps.get(i).getEmpName()+"  "+"用户名称是："+emps.get(i).getUser().getUserName());
					
				}*/

				//System.out.println(employee.getEmp_user_id()+"     "+"这是用户id");
				
//				user = userService.findById(employee.getEmp_user_id());
//				System.out.println("哈哈哈哈哈"+user);
//				employee.setUser( userService.findById(employee.getEmp_user_id()) );
//				System.out.println("卧槽我不信"+employee.getEmpName()+employee.getUser());
//				String username = employee.getUser().getUserName();
//				
//			//	System.out.println("这是用户名"+);
//				if(username == null ||username.equals("")) {
//					//System.out.println("这是空的删除了   "+username);
//					emps.remove(i); //不显示
//				}
				
			
			
			}
			
			
			
			
//			for (Employee employee : emps) {
//				.
//				user = userService.findById(employee.getEmp_user_id());
//				employee.setUser(user);
//			}
		request.setAttribute("emps", emps2);
		return "/pages_personal/public_schedule_employee.jsp";
		
	}

}
