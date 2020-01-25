package com.ssm.OaManager.utils;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.ssm.OaManager.entity.User;
import com.ssm.OaManager.entity.privilege.Function;
import com.ssm.OaManager.entity.privilege.Role;

/**
 * 切面类
 * 
 * @author Administrator
 * 
 */
@Component
@Aspect
public class PrivilegeFilter {

	private User user; //有用户

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	//
	@Pointcut("execution(* com.ssm.OaManager.service..*(..))")  //配置该包下的所有方法都代理
	public void privilegePoint() {
		//System.out.println("切面方法，卧槽怎么跑到这来执行了呀***********");
	}

	/**
	 * 环绕通知的切面
	 * 
	 * @param point
	 * @throws Throwable
	 */
	@SuppressWarnings("rawtypes")
	@Around(value = "privilegePoint()")
	public Object aopAround(ProceedingJoinPoint point) throws Throwable,Exception {
		/** 获取目标方法的 返回类型字节码 */
		Class returnTypeClazz = null;
		// 获得字节码
		Class clazz = point.getTarget().getClass();
		// 获得当前连接点方法名称
		String methodName = point.getSignature().getName();
		// 获得所有方法
		Method[] methods = clazz.getDeclaredMethods();
		if (!methodName.equals("getEmpIdByEmpName") //如果不是以下的方法之一
				&& !methodName.equals("updateUser")
				&& !methodName.equals("save")
				&& !methodName.equals("updateImg")
				&& !methodName.startsWith("find")
				&& !methodName.equals("updateEmp")
				&& !methodName.equals("updateRole")) {
			
			// 遍历方法
			for (Method method : methods) {
				// 当方法等于当前方法时
				if (method.getName().equals(methodName)) {
					/** 获取目标方法的 返回类型字节码 */
					returnTypeClazz = method.getReturnType();
					// 判断是否存在自定义注解
					if (method.isAnnotationPresent(MyAnnotation.class)) {
						
						// 获得自定义注解对象
						MyAnnotation myAnnotation = (MyAnnotation) method
								.getAnnotation(MyAnnotation.class);
						
						// 遍历注解属性
						for (Role role : this.user.getRoles()) {  //问题处在这里面
							System.out.println(user);
							if(role.getFunctions()!=null){
								
								for (Function function : role.getFunctions()) { 
									
									//System.out.println("去你妈的空指针异常"+function.getFunDescripti());
									if (function.getFunForbidden() == 0) {/*************bbbbb*************/
										// 判断是否跟当前连接点方法名称一致
										if (myAnnotation.methodNames()[0]
												.equals(function.getFunUrl())) {
											return point.proceed();
										}
									}
								}
							}

						}
					}
				}
			}
		} else {
			return point.proceed();
		}
		return RecordMappingToMap.recordMappingToMap(returnTypeClazz);
	}
}
