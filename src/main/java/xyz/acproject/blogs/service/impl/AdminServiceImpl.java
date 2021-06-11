package xyz.acproject.blogs.service.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.acproject.blogs.dao.AdminMapper;
import xyz.acproject.blogs.entity.Admin;
import xyz.acproject.blogs.entity.AdminExample;
import xyz.acproject.blogs.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminMapper adminMapper;

	@Override
	public Admin login(String name, String pwd, HttpServletRequest req) {
		Admin admin = adminMapper.selectByNameAndPwd(name, pwd);
		if (admin != null) {
			if (admin.getHeadimg().equals("1")) {
				Calendar calendar = Calendar.getInstance();
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String timeString = dateFormat.format(calendar.getTime());
				Timestamp time = Timestamp.valueOf(timeString.toString());
				Admin record = new Admin();
				record.setId(admin.getId());
				record.setLogintime(time);
				adminMapper.updateByPrimaryKeySelective(record);
				req.getSession().setAttribute("admin", admin);
				return admin;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public List<Admin> selectByExample(AdminExample example) {
		// TODO 自动生成的方法存根
		return adminMapper.selectByExample(example);
	}

	@Override
	public int insertSelective(Admin record) {
		// TODO 自动生成的方法存根
		return adminMapper.insertSelective(record);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO 自动生成的方法存根
		return adminMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Admin record) {
		// TODO 自动生成的方法存根
		return adminMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Admin selectByPrimaryKey(Integer id) {
		// TODO 自动生成的方法存根
		return adminMapper.selectByPrimaryKey(id);
	}

	@Override
	public long countByExample(AdminExample example) {
		// TODO 自动生成的方法存根
		return adminMapper.countByExample(example);
	}
}
