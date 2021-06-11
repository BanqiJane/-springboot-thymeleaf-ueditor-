package xyz.acproject.blogs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.acproject.blogs.dao.ArticleScaleMapper;
import xyz.acproject.blogs.dao.CommentMapper;
import xyz.acproject.blogs.dao.CommentPraiseMapper;
import xyz.acproject.blogs.entity.Comment;
import xyz.acproject.blogs.entity.CommentExample;
import xyz.acproject.blogs.entity.CommentPraise;
import xyz.acproject.blogs.service.CommentService;
@SuppressWarnings("unused")
@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private CommentPraiseMapper commentPraiseMapper;
	@Autowired
	private ArticleScaleMapper articleScaleMapper;
	@Override
	public long countByExample(CommentExample example) {
		// TODO 自动生成的方法存根
		return commentMapper.countByExample(example);
	}
	@Override
	public List<Comment> selectByExample(CommentExample example) {
		// TODO 自动生成的方法存根
		return commentMapper.selectByExample(example);
	}
	@Override
	public Comment selectByPrimaryKey(Integer id) {
		// TODO 自动生成的方法存根
		return commentMapper.selectByPrimaryKey(id);
	}
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO 自动生成的方法存根
		return commentMapper.deleteByPrimaryKey(id);
	}
	@Override
	public int deleteByCid(Integer cid) {
		// TODO 自动生成的方法存根
		return commentMapper.deleteByCid(cid);
	}
	@Override
	public List<Comment> selectListByCid(Integer cid) {
		// TODO 自动生成的方法存根
		return commentMapper.selectListByCid(cid);
	}
	@Override
	public int deleteByCidPraise(Integer cid) {
		// TODO 自动生成的方法存根
		return commentPraiseMapper.deleteByCid(cid);
	}
	@Override
	public int deleteByCidsPraise(List<Integer> cid) {
		// TODO 自动生成的方法存根
		return commentPraiseMapper.deleteByCids(cid);
	}
	@Override
	public int updateByApidOnCommentNum(int commentnum, int apid) {
		// TODO 自动生成的方法存根
		return articleScaleMapper.updateByApidOnCommentNum(commentnum, apid);
	}
}
