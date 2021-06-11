package xyz.acproject.blogs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.acproject.blogs.dao.LoginRecordMapper;
import xyz.acproject.blogs.entity.LoginRecord;
import xyz.acproject.blogs.entity.LoginRecordExample;
import xyz.acproject.blogs.service.LoginRecordService;
@Service
public class LoginRecordServiceImpl implements LoginRecordService {
	@Autowired
	private LoginRecordMapper loginRecordMapper;

	@Override
	public int insertSelective(LoginRecord record) {
		// TODO 自动生成的方法存根
		return loginRecordMapper.insertSelective(record);
	}

	@Override
	public List<LoginRecord> selectByAidDesc(Integer aid) {
		// TODO 自动生成的方法存根
		return loginRecordMapper.selectByAidDesc(aid);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO 自动生成的方法存根
		return loginRecordMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<LoginRecord> selectByExample(LoginRecordExample example) {
		// TODO 自动生成的方法存根
		return loginRecordMapper.selectByExample(example);
	}

	@Override
	public long countByExample(LoginRecordExample example) {
		// TODO 自动生成的方法存根
		return loginRecordMapper.countByExample(example);
	}
}
