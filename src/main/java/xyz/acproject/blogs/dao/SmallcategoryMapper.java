package xyz.acproject.blogs.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import xyz.acproject.blogs.entity.Smallcategory;
import xyz.acproject.blogs.entity.SmallcategoryExample;

@Repository
public interface SmallcategoryMapper {
    long countByExample(SmallcategoryExample example);

    int deleteByExample(SmallcategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Smallcategory record);

    int insertSelective(Smallcategory record);

    List<Smallcategory> selectByExample(SmallcategoryExample example);

    Smallcategory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Smallcategory record, @Param("example") SmallcategoryExample example);

    int updateByExample(@Param("record") Smallcategory record, @Param("example") SmallcategoryExample example);

    int updateByPrimaryKeySelective(Smallcategory record);

    int updateByPrimaryKey(Smallcategory record);
    
    List<Smallcategory> selectList();
    
    List<Smallcategory> selectListByBcid(Integer bcid);
    
    Smallcategory selectByValue(@Param("value")String value);
    
    int selectByMaxScid();
}