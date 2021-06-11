package xyz.acproject.blogs.service;

import java.util.List;


import xyz.acproject.blogs.entity.Announcement;
import xyz.acproject.blogs.entity.AnnouncementExample;

public interface AnnouncementService {
	
	 Announcement selectByPrimaryKey(int id);
	 
	 int selectByMaxId();
	 
	 List<Announcement> selectListByPage2(Integer startPos,Integer pageSize);
	 
	 long countByExample(AnnouncementExample example);
	 
	 int insertSelective(Announcement record);
	 
	 int deleteByPrimaryKey(Integer id);
	 
	 int updateByPrimaryKeySelective(Announcement record);
}
