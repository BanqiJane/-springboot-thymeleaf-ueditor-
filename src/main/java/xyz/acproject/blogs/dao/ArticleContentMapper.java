package xyz.acproject.blogs.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xyz.acproject.blogs.entity.ArticleContent;
import xyz.acproject.blogs.entity.ArticleContentExample;

@Repository
public interface ArticleContentMapper {
    long countByExample(ArticleContentExample example);

    int deleteByExample(ArticleContentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ArticleContent record);

    int insertSelective(ArticleContent record);

    List<ArticleContent> selectByExample(ArticleContentExample example);

    ArticleContent selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ArticleContent record, @Param("example") ArticleContentExample example);

    int updateByExample(@Param("record") ArticleContent record, @Param("example") ArticleContentExample example);

    int updateByPrimaryKeySelective(ArticleContent record);

    int updateByPrimaryKey(ArticleContent record);
    
    int deleteByApid(@Param("apid")Integer apid);
}