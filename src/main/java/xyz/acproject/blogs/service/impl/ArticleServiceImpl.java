package xyz.acproject.blogs.service.impl;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import xyz.acproject.blogs.dao.ArticleContentMapper;
import xyz.acproject.blogs.dao.ArticlePraiseMapper;
import xyz.acproject.blogs.dao.ArticlePropertyMapper;
import xyz.acproject.blogs.dao.ArticleScaleMapper;
import xyz.acproject.blogs.dao.CommentMapper;
import xyz.acproject.blogs.dao.CommentPraiseMapper;
import xyz.acproject.blogs.dao.SmallcategoryMapper;
import xyz.acproject.blogs.entity.Admin;
import xyz.acproject.blogs.entity.ArticleContent;
import xyz.acproject.blogs.entity.ArticlePraise;
import xyz.acproject.blogs.entity.ArticleProperty;
import xyz.acproject.blogs.entity.ArticlePropertyExample;
import xyz.acproject.blogs.entity.ArticleScale;
import xyz.acproject.blogs.entity.Comment;
import xyz.acproject.blogs.entity.CommentPraise;
import xyz.acproject.blogs.entity.Smallcategory;
import xyz.acproject.blogs.service.ArticleService;
import xyz.acproject.blogs.tools.cleanXss;
import xyz.acproject.blogs.tools.page.page3.Page;
import xyz.acproject.blogs.tools.returnJson.FastjsonConfig.FastJsonUtil;
import xyz.acproject.blogs.tools.returnJson.FastjsonConfig.Response;
import xyz.acproject.blogs.tools.returnJson.PubilcClass.ArticlePraiseJson;
import xyz.acproject.blogs.tools.returnJson.PubilcClass.CommentPraiseNum;
import xyz.acproject.blogs.tools.returnJson.PubilcClass.CommentsFlag;
import xyz.acproject.blogs.tools.returnJson.PubilcClass.CommentsJson;
import xyz.acproject.blogs.tools.returnJson.PubilcClass.SearchJson;

@Service
public class ArticleServiceImpl implements ArticleService {
//	@Autowired
//	private ArticleContentMapper articleContentMapper;//备用
	@Autowired
	private ArticlePraiseMapper articlePraiseMapper;
	@Autowired
	private ArticlePropertyMapper articlePropertyMapper;
	@Autowired
	private ArticleScaleMapper articleScaleMapper;
	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private CommentPraiseMapper commentPraiseMapper;
	@Autowired
	private ArticleContentMapper articleContentMapper;
	@Autowired
	private SmallcategoryMapper smallcategoryMapper;
	
	
	@Override
	@Cacheable(cacheNames="newArticles")
	public List<ArticleProperty> selectListByPageNum(int num) {
		// TODO 自动生成的方法存根
		// 页码乘以5从第几个开始查几个
		List<ArticleProperty> list = articlePropertyMapper.selectListByPageNum(num * 5);
		return list;
	}

	@Override
	public long articlePropertyCount() {
		// TODO 自动生成的方法存根
		return articlePropertyMapper.count();
	}
	@Cacheable(cacheNames="indexCommentNum")
	@Override
	public List<ArticleProperty> selectListOrderBycommentNumDesc() {
		// TODO 自动生成的方法存根
		return articlePropertyMapper.selectListOrderBycommentNumDesc();
	}
	@Cacheable(cacheNames="indexArticlePv")
	@Override
	public List<ArticleProperty> selectListOrderByarticlePvDesc() {
		// TODO 自动生成的方法存根
		return articlePropertyMapper.selectListOrderByarticlePvDesc();
	}
	@Cacheable(cacheNames="indexArticlePraise")
	@Override
	public List<ArticleProperty> selectListOrderByarticlePraiseDesc() {
		// TODO 自动生成的方法存根
		return articlePropertyMapper.selectListOrderByarticlePraiseDesc();
	}

	@Override
	public ArticleProperty selectListByid(Integer id) {
		// TODO 自动生成的方法存根
		return articlePropertyMapper.selectByid(id);
	}

	@Override
	public List<ArticleProperty> selectListBybcidOrscid(Integer bcid, Integer scid, int num) {
		// TODO 自动生成的方法存根
		// 页码乘以5从第几个开始查几个
		return articlePropertyMapper.selectListBybcidOrscid(bcid, scid, num * 5);
	}

	@Override
	public List<ArticleProperty> selectListByscid(Integer scid) {
		// TODO 自动生成的方法存根
		return articlePropertyMapper.selectListByscid(scid);
	}

	/**
	 * @effect {模糊查询的文章标题列表json串}
	 */
	@Override
	public void selectListByTitle(String title, HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) {
		// TODO 自动生成的方法存根
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		// 根据文章标题查询文章属性
		List<ArticleProperty> articleProperties = articlePropertyMapper.selectListByTitle(title);
		// 获取文章属性集合中的所有文章标题组成集合
		List<String> titleList = articleProperties.stream().map(ArticleProperty::getTitle).collect(Collectors.toList());
		// 获取文章属性集合中的所有文章id组成集合
		List<Integer> idList = articleProperties.stream().map(ArticleProperty::getId).collect(Collectors.toList());
		// 以"。"符号和成字符串
		String afterString = String.join("。", titleList);
		// 替换关键字
		String beforeString = afterString.replace(title,
				"<span style='color:red;font-weight:bold;'>" + title + "</span>");
		// 替换后重新组成数组
		String[] reArray = beforeString.split("。");
		// 新建数组
		List<String> retitleList = new ArrayList<String>();
		// 赋值给数组
		for (String string : reArray) {
			retitleList.add(string);
		}
		// 封装格式
		SearchJson<List<?>> searchJson = new SearchJson<List<?>>(titleList, retitleList, idList);
		// 利用fastjson封装成最终json串
		String jsonString = FastJsonUtil.toJson(Response.success(searchJson, req));
		// 向前端页面输送封装好的json串
		resp.setContentType("application/json;charset=UTF-8");
		writer.append(jsonString);
		writer.flush();
		writer.close();
	}

	@Override
	public List<ArticleProperty> selectListByTitles(String title, HttpServletRequest req, HttpServletResponse resp,
			int num) {
		// TODO 自动生成的方法存根
		// 页码乘以5从第几个开始查几个
		return articlePropertyMapper.selectListByTitles(title, num * 5);
	}

	@Override
	public int updateByApidOnPv(int apid) {
		// TODO 自动生成的方法存根
		return articleScaleMapper.updateByApidOnPv(apid);
	}

	@Override
	public long selectListByTitlesCount(String title) {
		// TODO 自动生成的方法存根
		return articlePropertyMapper.selectListByTitlesCount(title);
	}

	@Override
	public long selectListBybcidOrscidCount(Integer bcid, Integer scid) {
		// TODO 自动生成的方法存根
		return articlePropertyMapper.selectListBybcidOrscidCount(bcid, scid);
	}

	/**
	 * @effect {插入文章点赞}
	 */
	@Override
	public void insertArticlePraise(int apid, HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) {
		// TODO 自动生成的方法存根
		// 获取ip
		String ip = req.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
		}
		// 获取时间
		Calendar calendar = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeString = dateFormat.format(calendar.getTime());
		Timestamp time = Timestamp.valueOf(timeString.toString());
		// 新建一个文章点赞对象
		ArticlePraise record = new ArticlePraise();
		record.setApid(apid);
		record.setName(ip);
		record.setTime(time);
		// TODO 自动生成的方法存根
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		// 查询数据库是否含有该记录，含有就返回false，没有就插入新值
		if (articlePraiseMapper.selectByNameAndApid(ip, apid) != null) {
			// 获取点赞数
			long count = articlePraiseMapper.countByApid(apid);
			// 封装格式
			ArticlePraiseJson<String> articlePraiseJson = new ArticlePraiseJson<String>("false", count + "");
			// fastjson封装json格式
			String jsonString = FastJsonUtil.toJson(Response.success(articlePraiseJson, req));
			// 发送json给前端
			resp.setContentType("application/json;charset=UTF-8");
			writer.append(jsonString);
			writer.flush();
			writer.close();
		} else {
			// 插入新的点赞值
			articlePraiseMapper.insertIsExists(record);
			// 获取点赞数
			long count = articlePraiseMapper.countByApid(apid);
			// 更新点赞数
			articleScaleMapper.updateByApidOnPraise((int) count, apid);
			// 封装格式
			ArticlePraiseJson<String> articlePraiseJson = new ArticlePraiseJson<String>("true", count + "");
			// fastjson封装json格式
			String jsonString = FastJsonUtil.toJson(Response.success(articlePraiseJson, req));
			// 发送json给前端
			resp.setContentType("application/json;charset=UTF-8");
			writer.append(jsonString);
			writer.flush();
			writer.close();
		}
	}

	/**
	 * @effect {根据ip(名字)和文章属性id查询文章点赞库}
	 */
	@Override
	public void selectArticlePraiseByNameAndApid(String ip, Integer apid, HttpServletRequest req,
			HttpServletResponse resp, PrintWriter writer) {
		// TODO 自动生成的方法存根
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		if (articlePraiseMapper.selectByNameAndApid(ip, apid) != null) {
			// 获取点赞数
			long count = articlePraiseMapper.countByApid(apid);
			// 封装格式
			ArticlePraiseJson<String> articlePraiseJson = new ArticlePraiseJson<String>("true", count + "");
			// fastjson封装json格式
			String jsonString = FastJsonUtil.toJson(Response.success(articlePraiseJson, req));
			// 发送json给前端
			resp.setContentType("application/json;charset=UTF-8");
			writer.append(jsonString);
			writer.flush();
			writer.close();
		} else {
			// 获取点赞数
			long count = articlePraiseMapper.countByApid(apid);
			// 封装格式
			ArticlePraiseJson<String> articlePraiseJson = new ArticlePraiseJson<String>("false", count + "");
			// fastjson封装json格式
			String jsonString = FastJsonUtil.toJson(Response.success(articlePraiseJson, req));
			// 发送json给前端
			resp.setContentType("application/json;charset=UTF-8");
			writer.append(jsonString);
			writer.flush();
			writer.close();
		}
	}

	/**
	 * @effect {获取文章评论json}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void selectCommentByApid(Integer apid, HttpServletRequest req, HttpServletResponse resp, PrintWriter writer,
			Model model) {
		// TODO 自动生成的方法存根
		long counts = commentMapper.countByApid(apid);
		articleScaleMapper.updateByApidOnCommentNum((int) counts, apid);
		// 获取ip
		String ip = req.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
		}
		// 总评论数
		long total = commentMapper.countByApid(apid);
		model.addAttribute("total", total);
		String pageNow = req.getParameter("pageNow");
		if (pageNow == null)
			pageNow = "" + 1;
		long count = commentMapper.countByReApid(apid);
		Page page = new Page((int) count, Integer.parseInt(pageNow));
		page.setPageSize(10);
		// 主评论
		List<Comment> commentList = commentMapper.selectByApid(apid, page.getStartPos(), page.getPageSize());
		List<CommentsFlag> commentsFlagList = new ArrayList<CommentsFlag>();
		for (Comment comment : commentList) {
			if (commentPraiseMapper.selectByIpAndCommentId(ip, comment.getId(), apid) != null) {
				commentsFlagList.add(new CommentsFlag(comment, "true"));
			} else {
				commentsFlagList.add(new CommentsFlag(comment, "false"));
			}
		}
		/*
		 * for (CommentsFlag commentsFlag : commentsFlagList) {
		 * System.out.println(commentsFlag); }
		 */
		// 回复评论
		List<Comment> recommentList = commentMapper.selectByReApid(apid);
		List<CommentsFlag> recommentsFlagList = new ArrayList<CommentsFlag>();
		for (Comment comment : recommentList) {
			if (commentPraiseMapper.selectByIpAndCommentId(ip, comment.getId(), apid) != null) {
				recommentsFlagList.add(new CommentsFlag(comment, "true"));
			} else {
				recommentsFlagList.add(new CommentsFlag(comment, "false"));
			}
		}
		// 处理包装json串
		CommentsJson commentsJson = new CommentsJson(commentsFlagList, recommentsFlagList, page);
		String jsonString = FastJsonUtil.toJson(Response.success(commentsJson, req));
		resp.setContentType("application/json;charset=UTF-8");
		writer.write(jsonString);
		writer.flush();
		writer.close();
	}

	/**
	 * @effect {插入文章评论点赞}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void insertPraiseByApid(Integer apid, Integer cid, HttpServletRequest req, HttpServletResponse resp,
			PrintWriter writer) {
		// TODO 自动生成的方法存根
		// 获取ip
		String ip = req.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
		}
		// 获取时间
		Calendar calendar = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeString = dateFormat.format(calendar.getTime());
		Timestamp time = Timestamp.valueOf(timeString.toString());
		// 新建评论点赞对象
		CommentPraise record = new CommentPraise();
		record.setName(ip);
		record.setTime(time);
		record.setCommentid(cid);
		record.setApid(apid);
		long praiseNum = 0;
		if (commentPraiseMapper.selectByIpAndCommentId(ip, cid, apid) != null) {
			praiseNum = commentMapper.selectByIdOnPraiseNum(cid);
			CommentPraiseNum commentPraiseNum = new CommentPraiseNum(praiseNum, "true");
			String jsonString = FastJsonUtil.toJson(Response.success(commentPraiseNum, req));
			resp.setContentType("application/json;charset=UTF-8");
			writer.write(jsonString);
			writer.flush();
			writer.close();
		} else {
			int a = commentPraiseMapper.insertIsExists(record);
			if (a != 0) {
				commentMapper.updateByIdOnPraiseNum(cid);
				praiseNum = commentMapper.selectByIdOnPraiseNum(cid);
				CommentPraiseNum commentPraiseNum = new CommentPraiseNum(praiseNum, "true");
				String jsonString = FastJsonUtil.toJson(Response.success(commentPraiseNum, req));
				resp.setContentType("application/json;charset=UTF-8");
				writer.write(jsonString);
				writer.flush();
				writer.close();
			} else {
			//	commentMapper.updateByIdOnPraiseNum(cid);
				praiseNum = commentMapper.selectByIdOnPraiseNum(cid);
				CommentPraiseNum commentPraiseNum = new CommentPraiseNum(praiseNum, "false");
				String jsonString = FastJsonUtil.toJson(Response.success(commentPraiseNum, req));
				resp.setContentType("application/json;charset=UTF-8");
				writer.write(jsonString);
				writer.flush();
				writer.close();
			}
		}
	}

	/**
	 * @effect {插入文章主评论}
	 */
	@Override
	public void insertCommentSelective(Integer apid, String value, Comment record, HttpServletRequest req,
			HttpServletResponse resp, PrintWriter writer) {
		// TODO 自动生成的方法存根
		// 获取ip
		String ip = req.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
		}
		// 获取时间
		Calendar calendar = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeString = dateFormat.format(calendar.getTime());
		Timestamp time = Timestamp.valueOf(timeString.toString());
		// 获取名字
		Admin admin = (Admin) (req.getSession().getAttribute("admin"));
		if (admin != null) {
			record.setName(admin.getName());
		} else {
			record.setName("匿名");
		}
		record.setValue(cleanXss.cleanXSS(value));
		record.setIp(ip);
		record.setTime(time);
		record.setApid(apid);
		// 向数据库插入评论信息，成功就返回本条新插入的评论值，失败就返回error
		int a = commentMapper.insertSelective(record);
		if (a != 0) {
			long count = commentMapper.countByApid(apid);
			articleScaleMapper.updateByApidOnCommentNum((int) count, apid);
			Comment comment = commentMapper.selectByTime(time);
			String jsonString = FastJsonUtil.toJson(Response.success(comment, req));
			resp.setContentType("application/json;charset=UTF-8");
			writer.write(jsonString);
			writer.flush();
			writer.close();
		} else {
			String jsonString = FastJsonUtil.toJson(Response.error(req));
			resp.setContentType("application/json;charset=UTF-8");
			writer.write(jsonString);
			writer.flush();
			writer.close();
		}
	}

	/**
	 * @effect {插入文章回复评论}
	 */
	@Override
	public void insertReCommentSelective(Integer apid, String value, Integer cid, Comment record,
			HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) {
		// TODO 自动生成的方法存根
		// 获取ip
		String ip = req.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
		}
		// 获取时间
		Calendar calendar = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeString = dateFormat.format(calendar.getTime());
		Timestamp time = Timestamp.valueOf(timeString.toString());
		// 获取名字
		Admin admin = (Admin) (req.getSession().getAttribute("admin"));
		if (admin != null) {
			record.setName(admin.getName());
		} else {
			record.setName("匿名");
		}
		record.setValue(cleanXss.cleanXSS(value));
		record.setIp(ip);
		record.setTime(time);
		record.setCid(cid);
		record.setApid(apid);
		// 向数据库插入评论信息，成功就返回本条新插入的评论值，失败就返回error
		int a = commentMapper.insertSelective(record);
		if (a != 0) {
			long count = commentMapper.countByApid(apid);
			articleScaleMapper.updateByApidOnCommentNum((int) count, apid);
			Comment recomment = commentMapper.selectByTime(time);
			String jsonString = FastJsonUtil.toJson(Response.success(recomment, req));
			resp.setContentType("application/json;charset=UTF-8");
			writer.write(jsonString);
			writer.flush();
			writer.close();
		} else {
			String jsonString = FastJsonUtil.toJson(Response.error(req));
			resp.setContentType("application/json;charset=UTF-8");
			writer.write(jsonString);
			writer.flush();
			writer.close();
		}
	}

	@Override
	public List<ArticleProperty> selectListByScidDescOnTime(Integer scid) {
		// TODO 自动生成的方法存根
		return articlePropertyMapper.selectListByScidDescOnTime(scid);
	}

	@Override
	public List<ArticleProperty> selectListByBcidDescOnTime(Integer bcid) {
		// TODO 自动生成的方法存根
		return articlePropertyMapper.selectListByBcidDescOnTime(bcid);
	}

	@Override
	public long CountByTotalArticlePv() {
		// TODO 自动生成的方法存根
		return articleScaleMapper.CountByTotalArticlePv();
	}

	@Override
	public List<ArticleProperty> selectListByPage2(Integer startPos, Integer pageSize) {
		// TODO 自动生成的方法存根
		return articlePropertyMapper.selectListByPage2(startPos, pageSize);
	}

	/**
	 * @effect {删除文章}
	 */
	@Transactional
	@Override
	public int deleteArticle(Integer apid) {
		// TODO 自动生成的方法存根
		int as = articleScaleMapper.deleteByApid(apid);
		int ac = articleContentMapper.deleteByApid(apid);
		int ap = articlePropertyMapper.deleteByPrimaryKey(apid);
		articlePraiseMapper.deleteByApid(apid);
		commentMapper.deleteByApid(apid);
		commentPraiseMapper.deleteByApid(apid);
		if (as != 0 && ac != 0 && ap != 0) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * @effect {插入新文章}
	 */
	@Transactional
	@Override
	public int insertArticle(Integer apid, String title, String content, String describe, String imgUrl, Integer bcid,
			String smallCategory, Model model, HttpServletRequest req, HttpServletResponse resp) {
		// TODO 自动生成的方法存根
		// 插入文章属性---------
		ArticleProperty articleProperty = new ArticleProperty();
		apid = articlePropertyMapper.selectByMaxApid() + 1;// 生成apid
		articleProperty.setId(apid);
		articleProperty.setTitle(title);
		articleProperty.setDescribe(describe);
		articleProperty.setBcid(bcid);
		articleProperty.setImgurl("../" + imgUrl);
		Admin admin = (Admin) req.getSession().getAttribute("admin");
		articleProperty.setCreateman(admin.getName());
		Calendar calendar = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeString = dateFormat.format(calendar.getTime());
		Timestamp time = Timestamp.valueOf(timeString.toString());
		articleProperty.setCreatetime(time);
		articleProperty.setContentid(apid);
		// 插入小类别
		Smallcategory smallcategorys = smallcategoryMapper.selectByValue(smallCategory);
		if (smallcategorys != null) {
			articleProperty.setScid(smallcategorys.getId());
		} else {
			int scid = smallcategoryMapper.selectByMaxScid() + 1;
			Smallcategory newSmallcategory = new Smallcategory();
			newSmallcategory.setBcid(bcid);
			newSmallcategory.setValue(smallCategory);
			newSmallcategory.setId(scid);
			articleProperty.setScid(scid);
			smallcategoryMapper.insertSelective(newSmallcategory);
		}
		int ap = articlePropertyMapper.insertSelective(articleProperty);
		// 插入文章内容---------
		ArticleContent articleContent = new ArticleContent();
		articleContent.setApid(apid);
		articleContent.setId(apid);
		articleContent.setContent(content);
		int ac = articleContentMapper.insertSelective(articleContent);
		// 插入文章数量表---------
		ArticleScale articleScale = new ArticleScale();
		articleScale.setApid(apid);
		articleScale.setId(apid);
		int as = articleScaleMapper.insertSelective(articleScale);
		if (ap != 0 && ac != 0 && as != 0) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public long countByApid(Integer apid) {
		// TODO 自动生成的方法存根
		return commentMapper.countByApid(apid);
	}

	@Override
	public int updateArticle(Integer apid, String title, String content, String describe, String imgUrl, Integer bcid,
			String smallCategory, Model model, HttpServletRequest req, HttpServletResponse resp) {
		// TODO 自动生成的方法存根
		// 更新文章属性---------
		ArticleProperty articleProperty = new ArticleProperty();
		articleProperty.setId(apid);
		articleProperty.setTitle(title);
		articleProperty.setDescribe(describe);
		articleProperty.setBcid(bcid);
		articleProperty.setImgurl("../" + imgUrl);
		Admin admin = (Admin) req.getSession().getAttribute("admin");
		articleProperty.setCreateman(admin.getName());
		Calendar calendar = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeString = dateFormat.format(calendar.getTime());
		Timestamp time = Timestamp.valueOf(timeString.toString());
		articleProperty.setCreatetime(time);
		articleProperty.setContentid(apid);
		// 更新小类别
		Smallcategory smallcategorys = smallcategoryMapper.selectByValue(smallCategory);
		if (smallcategorys != null) {
			articleProperty.setScid(smallcategorys.getId());
		} else {
			int scid = smallcategoryMapper.selectByMaxScid() + 1;
			Smallcategory newSmallcategory = new Smallcategory();
			newSmallcategory.setBcid(bcid);
			newSmallcategory.setValue(smallCategory);
			newSmallcategory.setId(scid);
			articleProperty.setScid(scid);
			smallcategoryMapper.insertSelective(newSmallcategory);
		}
		int ap = articlePropertyMapper.updateByPrimaryKeySelective(articleProperty);
		// 更新文章内容---------
		ArticleContent articleContent = new ArticleContent();
		articleContent.setApid(apid);
		articleContent.setId(apid);
		articleContent.setContent(content);
		int ac = articleContentMapper.updateByPrimaryKeySelective(articleContent);
		// 更新文章数量表---------
		ArticleScale articleScale = new ArticleScale();
		articleScale.setApid(apid);
		articleScale.setId(apid);
		int as = articleScaleMapper.updateByPrimaryKeySelective(articleScale);
		if (ap != 0 && ac != 0 && as != 0) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public int countBycreateMan(String createman) {
		// TODO 自动生成的方法存根
		return articlePropertyMapper.countBycreateMan(createman);
	}

	@Override
	public int updateByPrimaryKeySelectiveP(ArticleProperty record) {
		// TODO 自动生成的方法存根
		return articlePropertyMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<ArticleProperty> selectByExampleP(ArticlePropertyExample example) {
		// TODO 自动生成的方法存根
		return articlePropertyMapper.selectByExample(example);
	}

}
