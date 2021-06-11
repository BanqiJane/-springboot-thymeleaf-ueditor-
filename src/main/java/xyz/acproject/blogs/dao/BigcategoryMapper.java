package xyz.acproject.blogs.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xyz.acproject.blogs.entity.Bigcategory;
import xyz.acproject.blogs.entity.BigcategoryExample;

@Repository
public interface BigcategoryMapper {
    long countByExample(BigcategoryExample example);

    int deleteByExample(BigcategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Bigcategory record);

    int insertSelective(Bigcategory record);

    List<Bigcategory> selectByExample(BigcategoryExample example);

    Bigcategory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Bigcategory record, @Param("example") BigcategoryExample example);

    int updateByExample(@Param("record") Bigcategory record, @Param("example") BigcategoryExample example);

    int updateByPrimaryKeySelective(Bigcategory record);

    int updateByPrimaryKey(Bigcategory record);
    
    List<Bigcategory> selectList();
    
    long count();
    
    Bigcategory selectByValue(@Param("value")String value);
    
}