package xyz.acproject.blogs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import xyz.acproject.blogs.dao.BigcategoryMapper;
import xyz.acproject.blogs.dao.SmallcategoryMapper;
import xyz.acproject.blogs.entity.Bigcategory;
import xyz.acproject.blogs.entity.Smallcategory;
import xyz.acproject.blogs.service.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private BigcategoryMapper bigcategoryMapper;
	@Autowired
	private SmallcategoryMapper smallcategoryMapper;
	@Override
	@Cacheable(cacheNames="bcList")
	public List<Bigcategory> selectbcList() {
		// TODO 自动生成的方法存根
		List<Bigcategory> list = bigcategoryMapper.selectList();
		return list;
	}
	@Override
	@Cacheable(cacheNames="scList")
	public List<Smallcategory> selectscList() {
		// TODO 自动生成的方法存根
		List<Smallcategory> list = smallcategoryMapper.selectList();
		return list;
	}
	@Override
	public List<Smallcategory> selectListByBcid(Integer bcid) {
		// TODO 自动生成的方法存根
		List<Smallcategory> list = smallcategoryMapper.selectListByBcid(bcid);
		return list;
	}
	@Override
	public Smallcategory selectByPrimaryKeysc(Integer id) {
		// TODO 自动生成的方法存根
		return smallcategoryMapper.selectByPrimaryKey(id);
	}
	@Override
	public Bigcategory selectByPrimaryKeybc(Integer id) {
		// TODO 自动生成的方法存根
		return bigcategoryMapper.selectByPrimaryKey(id);
	}
	@Override
	public long bigCategoryCount() {
		// TODO 自动生成的方法存根
		return bigcategoryMapper.count();
	}
	@Override
	public int deleteByPrimaryKeysc(Integer id) {
		// TODO 自动生成的方法存根
		return smallcategoryMapper.deleteByPrimaryKey(id);
	}
	@Override
	public Bigcategory selectByValuebc(String value) {
		// TODO 自动生成的方法存根
		return bigcategoryMapper.selectByValue(value);
	}
	@Override
	public int insertSelectivesc(Smallcategory record) {
		// TODO 自动生成的方法存根
		return smallcategoryMapper.insertSelective(record);
	}
}
