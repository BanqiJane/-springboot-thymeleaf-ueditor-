package xyz.acproject.blogs.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import xyz.acproject.blogs.entity.Admin;
import xyz.acproject.blogs.entity.AdminExample;

public interface AdminService {
	Admin login(String name,String pwd,HttpServletRequest req);
	
	List<Admin> selectByExample(AdminExample example);
	
	int insertSelective(Admin record);
	
	 int deleteByPrimaryKey(Integer id);
	 
	 int updateByPrimaryKeySelective(Admin record);
	 
	 Admin selectByPrimaryKey(Integer id);
	 
	 long countByExample(AdminExample example);
}
