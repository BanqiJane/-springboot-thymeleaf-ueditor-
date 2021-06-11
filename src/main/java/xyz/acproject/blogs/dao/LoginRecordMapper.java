package xyz.acproject.blogs.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xyz.acproject.blogs.entity.LoginRecord;
import xyz.acproject.blogs.entity.LoginRecordExample;

@Repository
public interface LoginRecordMapper {
    long countByExample(LoginRecordExample example);

    int deleteByExample(LoginRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LoginRecord record);

    int insertSelective(LoginRecord record);

    List<LoginRecord> selectByExample(LoginRecordExample example);

    LoginRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LoginRecord record, @Param("example") LoginRecordExample example);

    int updateByExample(@Param("record") LoginRecord record, @Param("example") LoginRecordExample example);

    int updateByPrimaryKeySelective(LoginRecord record);

    int updateByPrimaryKey(LoginRecord record);
    
    List<LoginRecord> selectByAidDesc(@Param("aid")Integer aid);
}