package com.ssm.OaManager.mydao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ruMiddleService")
public class RuMiddleService {

	@Autowired
	private RuMiddleDao ruMiddleDao;
	
	
	public List<RuMiddle> findAll(){
		return ruMiddleDao.findAll();
	}


	public RuMiddle getByUserId(int userId) {
		// TODO Auto-generated method stub
		return ruMiddleDao.getByUserId(userId);
	}
}
