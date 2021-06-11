package xyz.acproject.blogs.controller;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.acproject.blogs.entity.ArticleProperty;
import xyz.acproject.blogs.entity.Bigcategory;
import xyz.acproject.blogs.entity.Comment;
import xyz.acproject.blogs.entity.Smallcategory;
import xyz.acproject.blogs.service.AnnouncementService;
import xyz.acproject.blogs.service.ArticleService;
import xyz.acproject.blogs.service.CategoryService;
import xyz.acproject.blogs.tools.DateUtil;

/**
 * @author zjian
 *
 */
@Controller
public class HomepageController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private AnnouncementService announcementService;

	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(HomepageController.class.getName());// 日志组

	/**
	 * @param model
	 * @effect {首页}
	 * @return
	 */
	@RequestMapping(value = { "/", "index" })
	public String index(Model model,HttpServletRequest req) {
		// 大小类别
		List<Bigcategory> bcList = categoryService.selectbcList();
		List<Smallcategory> scList = categoryService.selectscList();
		// 根据分页选出前5个文章属性
		List<ArticleProperty> aList = articleService.selectListByPageNum(0);
		
		// 文章点赞量&访问&评论量排行榜
		List<ArticleProperty> cnList = articleService.selectListOrderBycommentNumDesc();
		
		List<ArticleProperty> apList = articleService.selectListOrderByarticlePvDesc();
		//热门推荐文章
				List<ArticleProperty> aHotList = new ArrayList<ArticleProperty>();
				for (ArticleProperty articleProperty : apList) {
					if(!articleProperty.getTop().trim().equals("0")) {
						aHotList.add(articleProperty);
					}
				}
		List<ArticleProperty> aprList = articleService.selectListOrderByarticlePraiseDesc();
		// 分页
		long count = articleService.articlePropertyCount();
		int pageNum = (int) (count / 5);
		model.addAttribute("pageNum", pageNum);
		if (pageNum > 0) {
			model.addAttribute("nextPage", 1);
		}
		model.addAttribute("time", DateUtil.getDate("yyyy年MM月dd日") + "   " + DateUtil.getWeek());
		model.addAttribute("announce", announcementService.selectByPrimaryKey(announcementService.selectByMaxId()));
		model.addAttribute("cnList", cnList);
		model.addAttribute("apList", apList);
		model.addAttribute("aprList", aprList);
		model.addAttribute("aList", aList);
		model.addAttribute("bcList", bcList);
		model.addAttribute("scList", scList);
		if(aHotList.size()>0) {
		model.addAttribute("aHotList", aHotList);
		}
		return "homepage/index";
	}

	/**
	 * @param model
	 * @param num
	 * @effect {首页ias插件分页}
	 * @return
	 */
	@RequestMapping("/page/{num}")
	public String page(Model model, @PathVariable int num) {
		// 根据分页页码选出文章属性
		List<ArticleProperty> aList = articleService.selectListByPageNum(num);
		// 分页
		long count = articleService.articlePropertyCount();
		int pageNum = (int) (count / 5);
		model.addAttribute("pageNum", pageNum);
		if (pageNum > num) {
			model.addAttribute("nextPage", num + 1);
		}
		model.addAttribute("time", DateUtil.getDate("yyyy年MM月dd日") + "   " + DateUtil.getWeek());
		model.addAttribute("announce", announcementService.selectByPrimaryKey(announcementService.selectByMaxId()));
		model.addAttribute("aList", aList);
		return "homepage/index";
	}

	/**
	 * @param model
	 * @param id
	 * @param req
	 * @effect {文章页面}
	 * @return
	 */
	@RequestMapping("/article/{id}")
	public String article(Model model, @PathVariable int id, HttpServletRequest req) {
		// 获取文章id作为session来控制访问量
		LocalDateTime time = (LocalDateTime) req.getSession().getAttribute(id + "");
		// 大小类别
		List<Bigcategory> bcList = categoryService.selectbcList();
		List<Smallcategory> scList = categoryService.selectscList();
		// 文章点赞量&访问&评论量排行榜
		List<ArticleProperty> cnList = articleService.selectListOrderBycommentNumDesc();
		List<ArticleProperty> apList = articleService.selectListOrderByarticlePvDesc();
		List<ArticleProperty> aprList = articleService.selectListOrderByarticlePraiseDesc();
		// 根据id选出对应的文章属性
		ArticleProperty articleProperty = articleService.selectListByid(id);

		// 如果不存在该文章就返回404，存在就返回对应的信息
		if (articleProperty != null) {
			// 如果session不存在 访问量+1 并产生新session
			if (null == time) {
				req.getSession().setAttribute(id + "", LocalDateTime.now());
				articleService.updateByApidOnPv(id);
			}
			// 获取相关推荐
			List<ArticleProperty> cList = articleService.selectListByScidDescOnTime(articleProperty.getScid());
			if (cList.size() > 1) {
				// 去除本身
				Iterator<ArticleProperty> iterator = cList.iterator();
				while (iterator.hasNext()) {
					ArticleProperty articleProperty2 = (ArticleProperty) iterator.next();
					if (articleProperty2.getTitle().equals(articleProperty.getTitle())) {
						iterator.remove();
					}
				}
				model.addAttribute("cList", cList);
			} else {
				List<ArticleProperty> cList2 = articleService.selectListByBcidDescOnTime(articleProperty.getBcid());
				if (cList2 != null) {
					// 去除本身
					Iterator<ArticleProperty> iterator = cList2.iterator();
					while (iterator.hasNext()) {
						ArticleProperty articleProperty3 = (ArticleProperty) iterator.next();
						if (articleProperty3.getTitle().equals(articleProperty.getTitle())) {
							iterator.remove();
						}
					}
				}
				model.addAttribute("cList", cList2);
			}
			model.addAttribute("time", DateUtil.getDate("yyyy年MM月dd日") + "   " + DateUtil.getWeek());
			model.addAttribute("announce", announcementService.selectByPrimaryKey(announcementService.selectByMaxId()));
			model.addAttribute("article", articleProperty);
			model.addAttribute("cnList", cnList);
			model.addAttribute("apList", apList);
			model.addAttribute("aprList", aprList);
			model.addAttribute("bcList", bcList);
			model.addAttribute("scList", scList);
			return "homepage/article";
		} else {
			return "redirect:../404";
		}
	}

	/**
	 * @param model
	 * @param bcid
	 * @param req
	 * @effect {首页导航栏分类控制}
	 * @return
	 */
	@RequestMapping("/category/{bcid}")
	public String bigcategory(Model model, @PathVariable int bcid, HttpServletRequest req) {
		// 获取小类别id
		String scidString = req.getParameter("scid");
//		HomepageController.bcid = bcid;// 赋值给公告大类别id
		req.getSession().removeAttribute("bcid");
		req.getSession().setAttribute("bcid", bcid);
		model.addAttribute("bcid", req.getSession().getAttribute("bcid"));// 控制前端类别显示颜色
//		HomepageController.scidString = scidString;// 赋值给公告小类别id
		req.getSession().removeAttribute("scidString");
		req.getSession().setAttribute("scidString", scidString);
		long bigCategoryCount = categoryService.bigCategoryCount();
		// 控制url上的大类别不要超出范围 超出则返回404
		if ((int)req.getSession().getAttribute("bcid") <= bigCategoryCount) {
			// 如果小类别id获取为空 就直接查大类别，如果存在小类别id则查大小类别id都查
			if (scidString != null) {
				model.addAttribute("scid", req.getSession().getAttribute("scidString"));// 控制前端类别显示颜色
				// 小类别id转换
				Integer scid = Integer.parseInt(scidString);
				// 分页
				long count = articleService.selectListBybcidOrscidCount(bcid, scid);
				int pageNum = (int) (count / 5);
				model.addAttribute("pageNum", pageNum);
				if (pageNum > 0) {
					model.addAttribute("nextPage", 1);
				}
				// 根据大小类别id和分页选出前5个文章属性
				List<ArticleProperty> bcidList = articleService.selectListBybcidOrscid(bcid, scid, 0);
				// 根据id获取类别
				Bigcategory bigcategory = categoryService.selectByPrimaryKeybc(bcid);
				Smallcategory smallcategory = categoryService.selectByPrimaryKeysc(scid);
				// 获取大小类别
				List<Bigcategory> bcList = categoryService.selectbcList();
				List<Smallcategory> scList = categoryService.selectscList();
				// 文章点赞量&访问&评论量排行榜
				List<ArticleProperty> cnList = articleService.selectListOrderBycommentNumDesc();
				List<ArticleProperty> apList = articleService.selectListOrderByarticlePvDesc();
				List<ArticleProperty> aprList = articleService.selectListOrderByarticlePraiseDesc();
				// 如果该小类别id不存在 则返回404 存在就返回对应的值
				if (smallcategory != null) {
					model.addAttribute("time", DateUtil.getDate("yyyy年MM月dd日") + "   " + DateUtil.getWeek());
					model.addAttribute("announce",
							announcementService.selectByPrimaryKey(announcementService.selectByMaxId()));
					model.addAttribute("bigcategory", bigcategory);
					model.addAttribute("smallcategory", smallcategory);
					model.addAttribute("cnList", cnList);
					model.addAttribute("apList", apList);
					model.addAttribute("aprList", aprList);
					model.addAttribute("bcList", bcList);
					model.addAttribute("scList", scList);
					// 空值判断 如果没有值输出 就返回一个值给前端显示空
					if (bcidList.size() != 0) {
						model.addAttribute("aList", bcidList);
					} else {
						model.addAttribute("aList", bcidList);
						model.addAttribute("aListNull", 1);
					}
					return "homepage/category";
				} else {
					return "redirect:../404";
				}
				// 只有大类别
			} else {
				// 分页
				long count = articleService.selectListBybcidOrscidCount(bcid, null);
				int pageNum = (int) (count / 5);
				model.addAttribute("pageNum", pageNum);
				if (pageNum > 0) {
					model.addAttribute("nextPage", 1);
				}
				// 根据大类别id获取大类别列表
				Bigcategory bigcategory = categoryService.selectByPrimaryKeybc(bcid);
				// 根据大类别id和分页选出前5个文章属性
				List<ArticleProperty> bcidList = articleService.selectListBybcidOrscid(bcid, null, 0);
				// 获取大小类别
				List<Bigcategory> bcList = categoryService.selectbcList();
				List<Smallcategory> scList = categoryService.selectscList();
				// 文章点赞量&访问&评论量排行榜
				List<ArticleProperty> cnList = articleService.selectListOrderBycommentNumDesc();
				List<ArticleProperty> apList = articleService.selectListOrderByarticlePvDesc();
				List<ArticleProperty> aprList = articleService.selectListOrderByarticlePraiseDesc();
				// 如果该大类别id不存在 则返回404 存在就返回对应的值
				if (bigcategory != null) {
					model.addAttribute("time", DateUtil.getDate("yyyy年MM月dd日") + "   " + DateUtil.getWeek());
					model.addAttribute("announce",
							announcementService.selectByPrimaryKey(announcementService.selectByMaxId()));
					model.addAttribute("bigcategory", bigcategory);
					model.addAttribute("cnList", cnList);
					model.addAttribute("apList", apList);
					model.addAttribute("aprList", aprList);
					model.addAttribute("bcList", bcList);
					model.addAttribute("scList", scList);
					// 空值判断 如果没有值输出 就返回一个值给前端显示空
					if (bcidList.size() != 0) {
						model.addAttribute("aList", bcidList);
					} else {
						model.addAttribute("aList", bcidList);
						model.addAttribute("aListNull", 1);
					}
					return "homepage/category";
				} else {
					return "redirect:../404";
				}
			}
		} else {
			return "redirect:../404";
		}
	}

	/**
	 * @param model
	 * @param num
	 * @effect {类别筛选分页}
	 * @return
	 */
	@RequestMapping("/bpage/{num}")
	public String bpage(Model model, @PathVariable int num,HttpServletRequest req) {
		// 将公共小类别id赋值给新值(导航栏类别id——公共id——新id)
		String scidString = (String) req.getSession().getAttribute("scidString");
		// 将公共大类别id赋值给新值(导航栏类别id——公共id——新id)
		int bcid = (int) req.getSession().getAttribute("bcid");
		// 如果小类别id获取为空 就直接查大类别，如果存在小类别id则查大小类别id都查
		if (scidString != null) {
			// 分页
			Integer scid = Integer.parseInt(scidString);
			long count = articleService.selectListBybcidOrscidCount(bcid, scid);
			int pageNum = (int) (count / 5);
			model.addAttribute("pageNum", pageNum);
			if (pageNum > num) {
				model.addAttribute("nextPage", num + 1);
			}
			// 根据大小类别id和分页页码选出对应的文章属性
			List<ArticleProperty> bcidList = articleService.selectListBybcidOrscid(bcid, scid, num);
			// 根据id获取类别
			Bigcategory bigcategory = categoryService.selectByPrimaryKeybc(bcid);
			Smallcategory smallcategory = categoryService.selectByPrimaryKeysc(scid);
			// 获取大小类别
			List<Bigcategory> bcList = categoryService.selectbcList();
			List<Smallcategory> scList = categoryService.selectscList();
			// 文章点赞量&访问&评论量排行榜
			List<ArticleProperty> cnList = articleService.selectListOrderBycommentNumDesc();
			List<ArticleProperty> apList = articleService.selectListOrderByarticlePvDesc();
			List<ArticleProperty> aprList = articleService.selectListOrderByarticlePraiseDesc();
			// 如果该小类别id不存在 则返回404 存在就返回对应的值
			if (smallcategory != null) {
				model.addAttribute("time", DateUtil.getDate("yyyy年MM月dd日") + "   " + DateUtil.getWeek());
				model.addAttribute("announce",
						announcementService.selectByPrimaryKey(announcementService.selectByMaxId()));
				model.addAttribute("bigcategory", bigcategory);
				model.addAttribute("smallcategory", smallcategory);
				model.addAttribute("cnList", cnList);
				model.addAttribute("apList", apList);
				model.addAttribute("aprList", aprList);
				model.addAttribute("bcList", bcList);
				model.addAttribute("scList", scList);
				// 空值判断 如果没有值输出 就返回一个值给前端显示空
				if (bcidList.size() != 0) {
					model.addAttribute("aList", bcidList);
				} else {
					model.addAttribute("aList", bcidList);
					model.addAttribute("aListNull", 1);
				}
				return "homepage/category";
			} else {
				return "redirect:../404";
			}
		} else {
			// 分页
			long count = articleService.selectListBybcidOrscidCount(bcid, null);
			int pageNum = (int) (count / 5);
			model.addAttribute("pageNum", pageNum);
			if (pageNum > num) {
				model.addAttribute("nextPage", num + 1);
			}
			// 根据大类别id获取大类别列表
			Bigcategory bigcategory = categoryService.selectByPrimaryKeybc(bcid);
			// 根据大类别id和分页选出文章属性
			List<ArticleProperty> bcidList = articleService.selectListBybcidOrscid(bcid, null, num);
			// 获取大小类别
			List<Bigcategory> bcList = categoryService.selectbcList();
			List<Smallcategory> scList = categoryService.selectscList();
			// 文章点赞量&访问&评论量排行榜
			List<ArticleProperty> cnList = articleService.selectListOrderBycommentNumDesc();
			List<ArticleProperty> apList = articleService.selectListOrderByarticlePvDesc();
			List<ArticleProperty> aprList = articleService.selectListOrderByarticlePraiseDesc();
			// 如果该大类别id不存在 则返回404 存在就返回对应的值
			if (bigcategory != null) {
				model.addAttribute("time", DateUtil.getDate("yyyy年MM月dd日") + "   " + DateUtil.getWeek());
				model.addAttribute("announce",
						announcementService.selectByPrimaryKey(announcementService.selectByMaxId()));
				model.addAttribute("bigcategory", bigcategory);
				model.addAttribute("cnList", cnList);
				model.addAttribute("apList", apList);
				model.addAttribute("aprList", aprList);
				model.addAttribute("bcList", bcList);
				model.addAttribute("scList", scList);
				// 空值判断 如果没有值输出 就返回一个值给前端显示空
				if (bcidList.size() != 0) {
					model.addAttribute("aList", bcidList);
				} else {
					model.addAttribute("aList", bcidList);
					model.addAttribute("aListNull", 1);
				}
				return "homepage/category";
			} else {
				return "redirect:../404";
			}
		}
	}

	/**
	 * @param title
	 * @param req
	 * @param resp
	 * @param writer
	 * @effect {根据文章标题模糊查询出下拉列表(json)}
	 */
	@ResponseBody
	@RequestMapping(value = "/searchByTitle", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	public void searchByTitle(@RequestParam("title") String title, HttpServletRequest req, HttpServletResponse resp,
			PrintWriter writer) {
		articleService.selectListByTitle(title, req, resp, writer);
	}

	/**
	 * @param title
	 * @param req
	 * @param resp
	 * @param model
	 * @effect {根据文章标题模糊查询}
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(@RequestParam("title") String title, HttpServletRequest req, HttpServletResponse resp,
			Model model) {
		// 标题赋值给公共标题
//		HomepageController.title = title;
		req.getSession().removeAttribute("title");
		req.getSession().setAttribute("title",title);
		// 根据标题和页码0获取前5个文章属性
		List<ArticleProperty> aList = articleService.selectListByTitles(title, req, resp, 0);
		// 获取大小类别
		List<Bigcategory> bcList = categoryService.selectbcList();
		List<Smallcategory> scList = categoryService.selectscList();
		// 文章点赞量&访问&评论量排行榜
		List<ArticleProperty> cnList = articleService.selectListOrderBycommentNumDesc();
		List<ArticleProperty> apList = articleService.selectListOrderByarticlePvDesc();
		List<ArticleProperty> aprList = articleService.selectListOrderByarticlePraiseDesc();
		// 分页
		long count = articleService.selectListByTitlesCount(title);
		int pageNum = (int) (count / 5);
		model.addAttribute("pageNum", pageNum);
		if (pageNum > 0) {
			model.addAttribute("nextPage", 1);
		}
		// 空值判断 如果没有值输出 就返回一个值给前端显示空
		if (aList.size() != 0) {
			model.addAttribute("aList", aList);
		} else {
			model.addAttribute("aList", aList);
			model.addAttribute("aListNull", 1);
		}
		model.addAttribute("time", DateUtil.getDate("yyyy年MM月dd日") + "   " + DateUtil.getWeek());
		model.addAttribute("announce", announcementService.selectByPrimaryKey(announcementService.selectByMaxId()));
		model.addAttribute("cnList", cnList);
		model.addAttribute("apList", apList);
		model.addAttribute("aprList", aprList);
		model.addAttribute("bcList", bcList);
		model.addAttribute("scList", scList);
		return "homepage/search";
	}

	/**
	 * @param req
	 * @param resp
	 * @param model
	 * @param num
	 * @effect {根据文章标题模糊查询的分页}
	 * @return
	 */
	@RequestMapping(value = "/pages/{num}")
	public String pages(HttpServletRequest req, HttpServletResponse resp, Model model, @PathVariable int num) {
		// 公共文章标题赋值给新值(文章标题——公共标题——新值)
		String title = (String) req.getSession().getAttribute("title");;
		// 分页
		long count = articleService.selectListByTitlesCount(title);
		System.out.println("count:  ---------------:" + count);
		int pageNum = (int) (count / 5);
		model.addAttribute("pageNum", pageNum);
		if (pageNum > num) {
			model.addAttribute("nextPage", num + 1);
		}
		// 根据标题和页码获取文章属性
		List<ArticleProperty> aList = articleService.selectListByTitles(title, req, resp, num);
		// 获取大小类别
		List<Bigcategory> bcList = categoryService.selectbcList();
		List<Smallcategory> scList = categoryService.selectscList();
		// 文章点赞量&访问&评论量排行榜
		List<ArticleProperty> cnList = articleService.selectListOrderBycommentNumDesc();
		List<ArticleProperty> apList = articleService.selectListOrderByarticlePvDesc();
		List<ArticleProperty> aprList = articleService.selectListOrderByarticlePraiseDesc();
		// 空值判断 如果没有值输出 就返回一个值给前端显示空
		if (aList.size() != 0) {
			model.addAttribute("aList", aList);
		} else {
			model.addAttribute("aList", aList);
			model.addAttribute("aListNull", 1);
		}
		model.addAttribute("time", DateUtil.getDate("yyyy年MM月dd日") + "   " + DateUtil.getWeek());
		model.addAttribute("announce", announcementService.selectByPrimaryKey(announcementService.selectByMaxId()));
		model.addAttribute("cnList", cnList);
		model.addAttribute("apList", apList);
		model.addAttribute("aprList", aprList);
		model.addAttribute("bcList", bcList);
		model.addAttribute("scList", scList);
		return "homepage/search";
	}

	/**
	 * @param apid
	 * @param req
	 * @param resp
	 * @effect {文章点赞插入}
	 */
	@ResponseBody
	@RequestMapping(value = "/insertArticlePraise", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	public void insertArticlePraise(@RequestParam("apid") int apid, HttpServletRequest req, HttpServletResponse resp,
			PrintWriter writer) {
		articleService.insertArticlePraise(apid, req, resp, writer);
	}

	/**
	 * @param apid
	 * @param req
	 * @param resp
	 * @effect {文章点赞查询}
	 */
	@ResponseBody
	@RequestMapping(value = "/checkArticlePraise", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	public void selectArticlePraiseByip(@RequestParam("apid") int apid, HttpServletRequest req,
			HttpServletResponse resp, PrintWriter writer) {
		// ip获取
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
		articleService.selectArticlePraiseByNameAndApid(ip, apid, req, resp, writer);
	}

	/**
	 * @param apid
	 * @param req
	 * @param resp
	 * @param writer
	 * @param model
	 * @effect {获取文章评论列表(json)}
	 */
	@ResponseBody
	@RequestMapping(value = "/commentJson", method = RequestMethod.POST, produces = {
			"application/json;charsetcommentJ=UTF-8" })
	public void selectByApid(@RequestParam("apid") Integer apid, HttpServletRequest req, HttpServletResponse resp,
			PrintWriter writer, Model model) {
		// TODO 自动生成的方法存根
		articleService.selectCommentByApid(apid, req, resp, writer, model);
	}

	/**
	 * @param apid
	 * @param cid
	 * @param req
	 * @param resp
	 * @effect {插入评论点赞(json)}
	 */
	@ResponseBody
	@RequestMapping(value = "/goods", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public void insertByApid(@RequestParam("apid") Integer apid, @RequestParam("cid") Integer cid,
			HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) {
		// TODO 自动生成的方法存根
		articleService.insertPraiseByApid(apid, cid, req, resp, writer);
	}

	/**
	 * @param apid
	 * @param value
	 * @param record
	 * @param req
	 * @param resp
	 * @param writer
	 * @effect {插入主评论并返回插入评论(json)}
	 */
	@ResponseBody
	@RequestMapping(value = "/addComments", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	public void insertCommentSelective(@RequestParam("apid") Integer apid, @RequestParam("value") String value,
			Comment record, HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) {
		// TODO 自动生成的方法存根
		articleService.insertCommentSelective(apid, value, record, req, resp, writer);
	}

	/**
	 * @param apid
	 * @param value
	 * @param cid
	 * @param record
	 * @param req
	 * @param resp
	 * @param writer
	 * @effect {插入回复评论并返回插入评论(json)}
	 */
	@ResponseBody
	@RequestMapping(value = "/addReComments", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	public void insertReCommentSelective(@RequestParam("apid") Integer apid, @RequestParam("value") String value,
			@RequestParam("cid") Integer cid, Comment record, HttpServletRequest req, HttpServletResponse resp,
			PrintWriter writer) {
		articleService.insertReCommentSelective(apid, value, cid, record, req, resp, writer);
	}

	/**
	 * @param model
	 * @param req
	 * @effect {404}
	 * @return
	 */
	@RequestMapping("404")
	public String to404(Model model, HttpServletRequest req) {
		// 获取大小类别
		List<Bigcategory> bcList = categoryService.selectbcList();
		List<Smallcategory> scList = categoryService.selectscList();
		model.addAttribute("bcList", bcList);
		model.addAttribute("scList", scList);
		return "404";
	}
}
