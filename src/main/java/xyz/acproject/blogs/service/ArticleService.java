package xyz.acproject.blogs.service;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import xyz.acproject.blogs.entity.ArticleProperty;
import xyz.acproject.blogs.entity.ArticlePropertyExample;
import xyz.acproject.blogs.entity.Comment;

public interface ArticleService {
	 
	 List<ArticleProperty> selectListByPageNum(int num);
	 
	 long articlePropertyCount();
	 
	 List<ArticleProperty> selectListOrderBycommentNumDesc();
	    
	 List<ArticleProperty> selectListOrderByarticlePvDesc();
	    
	 List<ArticleProperty> selectListOrderByarticlePraiseDesc();
	 
	 ArticleProperty selectListByid(Integer id);
	 
	 List<ArticleProperty> selectListBybcidOrscid(Integer bcid,Integer scid,int num);
	    
	 List<ArticleProperty> selectListByscid(Integer scid);
	 
	 void selectListByTitle(String title,HttpServletRequest req,HttpServletResponse resp, PrintWriter writer);
	 
	 List<ArticleProperty> selectListByTitles(String title,HttpServletRequest req,HttpServletResponse resp,int num);
	 
	 List<ArticleProperty> selectListByScidDescOnTime(Integer scid);
	    
	 List<ArticleProperty> selectListByBcidDescOnTime(Integer bcid);
	 
	 int updateByApidOnPv(int apid);
	 
	 long selectListByTitlesCount(String title);
	 
	 long selectListBybcidOrscidCount(Integer bcid,Integer scid);
	 
	 void insertArticlePraise(int apid,HttpServletRequest req,HttpServletResponse resp,PrintWriter writer);
	 
	 void selectArticlePraiseByNameAndApid(String ip,Integer apid,HttpServletRequest req,HttpServletResponse resp,PrintWriter writer);
	 
	 void selectCommentByApid(Integer apid,HttpServletRequest req,HttpServletResponse resp,PrintWriter writer,Model model);
	 
	 void insertPraiseByApid(Integer apid,Integer cid, HttpServletRequest req,HttpServletResponse resp, PrintWriter writer);
	 
	 void insertCommentSelective(Integer apid,String value,Comment record, HttpServletRequest req,HttpServletResponse resp, PrintWriter writer);
	 
	 void insertReCommentSelective(Integer apid,String value,Integer cid,Comment record, HttpServletRequest req,HttpServletResponse resp, PrintWriter writer);
	 
	 long CountByTotalArticlePv();
	 
	 List<ArticleProperty> selectListByPage2(Integer startPos,Integer pageSize);
	 
	 int deleteArticle(Integer apid);
	 
	 int insertArticle(Integer apid,String title,String content,String describe,String imgUrl,Integer bcid,String smallCategory,Model model,HttpServletRequest req,HttpServletResponse resp);
	 
	 long countByApid(Integer apid);
	 
	 int updateArticle(Integer apid,String title,String content,String describe,String imgUrl,Integer bcid,String smallCategory,Model model,HttpServletRequest req,HttpServletResponse resp);
     
	 int countBycreateMan(String createman);
	 
	 int updateByPrimaryKeySelectiveP(ArticleProperty record);
	 
	 List<ArticleProperty> selectByExampleP(ArticlePropertyExample example);
}
