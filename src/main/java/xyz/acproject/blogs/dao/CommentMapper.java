package xyz.acproject.blogs.dao;

import java.sql.Timestamp;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xyz.acproject.blogs.entity.Comment;
import xyz.acproject.blogs.entity.CommentExample;

@Repository
public interface CommentMapper {
    long countByExample(CommentExample example);

    int deleteByExample(CommentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    List<Comment> selectByExample(CommentExample example);

    Comment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByExample(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);
    
    List<Comment> selectByApid(@Param("apid")Integer apid,@Param("startPos")Integer startPos,@Param("pageSize")Integer pageSize);
    
    List<Comment> selectByReApid(@Param("apid")Integer apid);
    
    long countByApid(@Param("apid")Integer apid);
    
    long countByReApid(@Param("apid")Integer apid);
    
    int updateByIdOnPraiseNum(@Param("id")Integer id);
    
    long selectByIdOnPraiseNum(@Param("id")Integer id);
    
    Comment selectByTime(@Param("time")Timestamp time);
    
    int deleteByApid(@Param("apid")Integer apid);
    
    int deleteByCid(@Param("cid")Integer cid);
    
    List<Comment> selectListByCid(@Param("cid")Integer cid);
}