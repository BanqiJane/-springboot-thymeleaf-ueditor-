package xyz.acproject.blogs.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xyz.acproject.blogs.entity.ArticleProperty;
import xyz.acproject.blogs.entity.ArticlePropertyExample;

@Repository
public interface ArticlePropertyMapper {
    long countByExample(ArticlePropertyExample example);

    int deleteByExample(ArticlePropertyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ArticleProperty record);

    int insertSelective(ArticleProperty record);

    List<ArticleProperty> selectByExample(ArticlePropertyExample example);

    ArticleProperty selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ArticleProperty record, @Param("example") ArticlePropertyExample example);

    int updateByExample(@Param("record") ArticleProperty record, @Param("example") ArticlePropertyExample example);

    int updateByPrimaryKeySelective(ArticleProperty record);

    int updateByPrimaryKey(ArticleProperty record);
    
    List<ArticleProperty> selectListByPageNum(@Param("num")int num);
    
    long count();
    
    List<ArticleProperty> selectListOrderBycommentNumDesc();
    
    List<ArticleProperty> selectListOrderByarticlePvDesc();
    
    List<ArticleProperty> selectListOrderByarticlePraiseDesc();
    
    ArticleProperty selectByid(Integer id);
    
    List<ArticleProperty> selectListBybcidOrscid(@Param("bcid")Integer bcid,@Param("scid")Integer scid,@Param("num")int num);
    
    List<ArticleProperty> selectListByscid(Integer scid);
    
    List<ArticleProperty> selectListByTitle(@Param("title")String title);
    
    List<ArticleProperty> selectListByTitles(@Param("title")String title,@Param("num")int num);
    
    long selectListByTitlesCount(@Param("title")String title);
    
    long selectListBybcidOrscidCount(@Param("bcid")Integer bcid,@Param("scid")Integer scid);
    
    List<ArticleProperty> selectListByScidDescOnTime(@Param("scid")Integer scid);
    
    List<ArticleProperty> selectListByBcidDescOnTime(@Param("bcid")Integer bcid);
    
    List<ArticleProperty> selectListByPage2(@Param("startPos")Integer startPos,@Param("pageSize")Integer pageSize);
    
    int selectByMaxApid();
    
    int countBycreateMan(@Param("createman")String createman);
}