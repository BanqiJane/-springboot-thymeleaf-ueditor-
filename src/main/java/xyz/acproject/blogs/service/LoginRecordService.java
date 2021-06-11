package xyz.acproject.blogs.service;

import java.util.List;

import xyz.acproject.blogs.entity.LoginRecord;
import xyz.acproject.blogs.entity.LoginRecordExample;

public interface LoginRecordService {
	int insertSelective(LoginRecord record);
	
	List<LoginRecord> selectByAidDesc(Integer aid);
	
	int deleteByPrimaryKey(Integer id);
	
	List<LoginRecord> selectByExample(LoginRecordExample example);
	
	long countByExample(LoginRecordExample example);
}
