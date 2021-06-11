package xyz.acproject.blogs.service;

import java.util.List;


import xyz.acproject.blogs.entity.Comment;
import xyz.acproject.blogs.entity.CommentExample;

public interface CommentService {
	
	long countByExample(CommentExample example);
	
	 List<Comment> selectByExample(CommentExample example);
	 
	 Comment selectByPrimaryKey(Integer id);
	 
	 int deleteByPrimaryKey(Integer id);
	 
	 int deleteByCid(Integer cid);
	 
	 List<Comment> selectListByCid(Integer cid);
	 
	 int deleteByCidPraise(Integer cid);
	 
	 int deleteByCidsPraise(List<Integer> cid);
	 
	 int updateByApidOnCommentNum(int commentnum,int apid);
}
