package xyz.acproject.blogs.service;

import java.util.List;

import xyz.acproject.blogs.entity.Bigcategory;
import xyz.acproject.blogs.entity.Smallcategory;

public interface CategoryService {
	
	List<Bigcategory> selectbcList();
	
	List<Smallcategory> selectscList();
	
	List<Smallcategory> selectListByBcid(Integer bcid);
	
	Smallcategory selectByPrimaryKeysc(Integer id);
	
	Bigcategory selectByPrimaryKeybc(Integer id);
	
	long bigCategoryCount();
	
	int deleteByPrimaryKeysc(Integer id);
	
	Bigcategory selectByValuebc(String value);
	
	int insertSelectivesc(Smallcategory record);
}
