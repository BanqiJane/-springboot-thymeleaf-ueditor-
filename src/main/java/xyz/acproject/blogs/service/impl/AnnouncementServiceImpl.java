package xyz.acproject.blogs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import xyz.acproject.blogs.dao.AnnouncementMapper;
import xyz.acproject.blogs.entity.Announcement;
import xyz.acproject.blogs.entity.AnnouncementExample;
import xyz.acproject.blogs.service.AnnouncementService;
@Service
public class AnnouncementServiceImpl implements AnnouncementService {
	@Autowired
	private AnnouncementMapper announcementMapper;

	@Override
	@Cacheable(cacheNames="indexAnnounce")
	public Announcement selectByPrimaryKey(int id) {
		// TODO 自动生成的方法存根
		return announcementMapper.selectByPrimaryKey(id);
	}

	@Override
	@Cacheable(cacheNames="indexAnnounceByMaxId")
	public int selectByMaxId() {
		// TODO 自动生成的方法存根
		return announcementMapper.selectByMaxId();
	}

	@Override
	public List<Announcement> selectListByPage2(Integer startPos, Integer pageSize) {
		// TODO 自动生成的方法存根
		return announcementMapper.selectListByPage2(startPos, pageSize);
	}

	@Override
	public long countByExample(AnnouncementExample example) {
		// TODO 自动生成的方法存根
		return announcementMapper.countByExample(example);
	}

	@Override
	public int insertSelective(Announcement record) {
		// TODO 自动生成的方法存根
		return announcementMapper.insertSelective(record);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO 自动生成的方法存根
		return announcementMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Announcement record) {
		// TODO 自动生成的方法存根
		return announcementMapper.updateByPrimaryKeySelective(record);
	}

}
