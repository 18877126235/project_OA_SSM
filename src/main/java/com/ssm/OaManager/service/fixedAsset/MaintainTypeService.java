package com.ssm.OaManager.service.fixedAsset;

import java.io.Serializable;
import java.util.List;

import com.ssm.OaManager.entity.fixedAsset.MaintainType;
import com.ssm.OaManager.utils.PageBean;
/**
 * 维护类型业务层实接口
 * @author Administrator
 *
 */
public interface MaintainTypeService {

	/**只查询一个，常用于修改*/
	public MaintainType findById(Serializable id);	

	/**
	 * 插入，用实体作为参数
	 * @param entity
	 */
	public int insert(MaintainType entity);

	/**
	 * 修改，用实体作为参数
	 * @param entity
	 */
	public int update(MaintainType entity);	

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
	public List<MaintainType> findAll();

	/**
	 * 设置分页对象属性
	 * @param pageBeapageBeann 分页对象
	 */
	public void findPageBean(PageBean<MaintainType> pageBean);
	
}
