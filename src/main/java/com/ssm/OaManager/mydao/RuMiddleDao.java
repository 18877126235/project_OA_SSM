package com.ssm.OaManager.mydao;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository("ruMiddleDao")
public interface RuMiddleDao {

	public List<RuMiddle> findAll();

	public RuMiddle getByUserId(int userId); //根据用户id查找
	
}
