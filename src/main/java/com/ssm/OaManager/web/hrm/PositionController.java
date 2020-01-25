package com.ssm.OaManager.web.hrm;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.runner.Request;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.ssm.OaManager.entity.User;
import com.ssm.OaManager.entity.hrm.Position;
import com.ssm.OaManager.service.hrm.PositionService;
import com.ssm.OaManager.utils.PrivilegeFilter;
import com.ssm.OaManager.web.BaseController;

/**
 * 岗位控制层
 * 
 * @author HUX
 * 
 */
@Controller
public class PositionController extends BaseController {

	@Resource
	private PositionService positionService;

	@Resource
	private PrivilegeFilter privilegeFilter;

	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
	}

	public void setPrivilegeFilterUser(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user"); //获取当前登录用户？？
		System.out.println("当前的用户是。。。。。"+user);
		this.privilegeFilter.setUser(user);
	}

	/**
	 * 分页查询全部
	 * 
	 * @param positionId
	 * @return
	 */
	@RequestMapping("/pages_hr/findPositionPages.action")
	public String findByPage(Model model, HttpServletRequest request) {
		setPrivilegeFilterUser(request);
		List<Position> pageBean = this.positionService.findAll();

		// model.addAllAttributes(pageBean);
		request.setAttribute("pageBean", pageBean);

		return "/pages_hr/public_hr_position_add.jsp";

	}

	/**
	 * 添加
	 * 
	 * @param function
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/pages_hr/insertPages.action",method = RequestMethod.POST)
	public String insert(String posName, String posDescriti, HttpServletRequest request) {
		
		//System.out.println(posName+posDescriti+"hhhhhh");
		
		//System.out.println("来了老弟。。。。。。。。。。。。。");
		setPrivilegeFilterUser(request);
		//System.out.println("来了你大爷。。。。。。。。。。。。");
		
		Position position = new Position();
		position.setPosName(posName);
		position.setPosDescriti(posDescriti);
		System.out.println("打印对象试试看"+position);
		int num = 0;
		//System.out.println(position.getPosName()+"试试看");
		try {
			num = this.positionService.insert(position);//新增职位
			//如果权限不够的话这里会报异常（老子收了你）
		}catch (Exception e) {
			// TODO: handle exception
			num = -1;
		}
		if (num == -1) {
			request.setAttribute("result", "你干啥呢，你没权限");
		}
		return "/pages_hr/findPositionPages.action";
		
	}

	/**
	 * 删除
	 * 
	 * @param positionId
	 * @return
	 */
	@RequestMapping("/pages_hr/deletePosition.action")
	public String delete(Integer positionId, HttpServletRequest request) {
		setPrivilegeFilterUser(request);
		int num = 0;
		try {
			
			System.out.println("什么鬼");
			num = this.positionService.deleteById(positionId);
			
		} catch (Exception e) {
			// TODO: handle exception
			num = -1;
		}
		
		if (num == -1) {
			request.setAttribute("result", "你大爷的你没资格");
		} else if(num == -2) {
			request.setAttribute("result", "删除失败");
		}
		return "/pages_hr/findPositionPages.action";

	}

	/**
	 * 修改
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/pages_hr/updatePonsition.action")
	public String update(Position position, HttpServletRequest request) {
		setPrivilegeFilterUser(request);
		
		int num = 0;
		
		try {
			num = this.positionService.updateById(position);
		} catch (Exception e) { //有异常就代表删除失败
			// TODO: handle exception
			num = -1;
		}
		
		
		if (num == -1) {
			request.setAttribute("result", "权限不足");
		}
		return "/pages_hr/findPositionPages.action";
	}

	@RequestMapping("/pages_hr/selectOnePonsition.action")
	public void findByFunId(Integer positionId, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Position position = this.positionService.findById(positionId);

			if (position == null) {
				response.getWriter().write("1");
			} else {
				String functions = JSON.toJSONString(position);
				// 发送给客户端
				response.getWriter().write(functions);
			}

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}