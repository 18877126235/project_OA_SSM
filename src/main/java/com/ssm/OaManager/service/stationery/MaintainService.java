package com.ssm.OaManager.service.stationery;

import java.io.Serializable;
import java.util.List;

import com.ssm.OaManager.entity.stationery.Maintain;
import com.ssm.OaManager.utils.PageBean;



public interface MaintainService{
	
	/**带条件分页查询，条件可以为null，既没有条件；返回list对象集合*/
	public List<Maintain> findByPage(PageBean<Maintain> pageBean);	
	
	/**只查询一个，常用于修改*/
	public Maintain findById(Serializable id);	
	
	/**
	 * 插入，用实体作为参数
	 * @param entity
	 */
	public int insert(Maintain entity);
	
	/**
	 * 修改，用实体作为参数
	 * @param entity
	 */
	public int update(Maintain entity);	
	
	/**
	 * 按id删除，删除一条；支持整数型和字符串类型ID
	 * @param entity
	 */
	public int deleteById(Serializable id);	
	
	/**
	 * 批量删除；支持整数型和字符串类型ID
	 * @param entity
	 */
	public int delete(Serializable[] ids);			
	
	/**
	 * 查询全部
	 * @param entity
	 */
	public List<Maintain> findAll();
	
	/**
	 * 查询记录数
	 * @param entity
	 */
	public int findCount(PageBean<Maintain> pageBean);
}
