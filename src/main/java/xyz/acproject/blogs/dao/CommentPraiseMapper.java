package xyz.acproject.blogs.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xyz.acproject.blogs.entity.CommentPraise;
import xyz.acproject.blogs.entity.CommentPraiseExample;

@Repository
public interface CommentPraiseMapper {
    long countByExample(CommentPraiseExample example);

    int deleteByExample(CommentPraiseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CommentPraise record);

    int insertSelective(CommentPraise record);

    List<CommentPraise> selectByExample(CommentPraiseExample example);

    CommentPraise selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CommentPraise record, @Param("example") CommentPraiseExample example);

    int updateByExample(@Param("record") CommentPraise record, @Param("example") CommentPraiseExample example);

    int updateByPrimaryKeySelective(CommentPraise record);

    int updateByPrimaryKey(CommentPraise record);
    
    CommentPraise selectByIpAndCommentId(@Param("name")String ip,@Param("commentid")Integer cid,@Param("apid")Integer apid);
    
    int deleteByApid(@Param("apid")Integer apid);
    
    int deleteByCid(@Param("cid")Integer cid);
    
    int deleteByCids(List<Integer> cid);
    
    int insertIsExists(CommentPraise record);
}