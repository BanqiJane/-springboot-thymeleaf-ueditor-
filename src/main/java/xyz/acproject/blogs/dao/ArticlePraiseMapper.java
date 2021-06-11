package xyz.acproject.blogs.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xyz.acproject.blogs.entity.ArticlePraise;
import xyz.acproject.blogs.entity.ArticlePraiseExample;

@Repository
public interface ArticlePraiseMapper {
    long countByExample(ArticlePraiseExample example);

    int deleteByExample(ArticlePraiseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ArticlePraise record);

    int insertSelective(ArticlePraise record);

    List<ArticlePraise> selectByExample(ArticlePraiseExample example);

    ArticlePraise selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ArticlePraise record, @Param("example") ArticlePraiseExample example);

    int updateByExample(@Param("record") ArticlePraise record, @Param("example") ArticlePraiseExample example);

    int updateByPrimaryKeySelective(ArticlePraise record);

    int updateByPrimaryKey(ArticlePraise record);
    
    ArticlePraise selectByNameAndApid(@Param("name")String ip,@Param("apid")Integer apid);
    
    long countByApid(@Param("apid")Integer apid);
    
    int deleteByApid(@Param("apid")Integer apid);
    
    int insertIsExists(ArticlePraise record);
}