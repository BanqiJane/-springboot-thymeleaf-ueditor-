package xyz.acproject.blogs.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xyz.acproject.blogs.entity.ArticleScale;
import xyz.acproject.blogs.entity.ArticleScaleExample;

@Repository
public interface ArticleScaleMapper {
    long countByExample(ArticleScaleExample example);

    int deleteByExample(ArticleScaleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ArticleScale record);

    int insertSelective(ArticleScale record);

    List<ArticleScale> selectByExample(ArticleScaleExample example);

    ArticleScale selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ArticleScale record, @Param("example") ArticleScaleExample example);

    int updateByExample(@Param("record") ArticleScale record, @Param("example") ArticleScaleExample example);

    int updateByPrimaryKeySelective(ArticleScale record);

    int updateByPrimaryKey(ArticleScale record);
    
    int updateByApidOnPv(@Param("apid")int apid);
    
    int updateByApidOnPraise(@Param("articlepraise")int articlepraise,@Param("apid")int apid);
    
    int updateByApidOnCommentNum(@Param("commentnum")int commentnum,@Param("apid")int apid);
    
    long CountByTotalArticlePv();
    
    int deleteByApid(@Param("apid")Integer apid);
}