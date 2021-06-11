package xyz.acproject.blogs.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hyperic.jni.ArchNotSupportedException;
import org.hyperic.sigar.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.ueditor.ActionEnter;

import jodd.io.FileUtil;
import jodd.io.StreamUtil;
import jodd.util.SystemUtil;
import xyz.acproject.blogs.entity.Admin;
import xyz.acproject.blogs.entity.Announcement;
import xyz.acproject.blogs.entity.ArticleProperty;
import xyz.acproject.blogs.entity.Bigcategory;
import xyz.acproject.blogs.entity.Comment;
import xyz.acproject.blogs.entity.CommentExample;
import xyz.acproject.blogs.entity.LoginRecord;
import xyz.acproject.blogs.entity.LoginRecordExample;
import xyz.acproject.blogs.entity.Smallcategory;
import xyz.acproject.blogs.service.AdminService;
import xyz.acproject.blogs.service.AnnouncementService;
import xyz.acproject.blogs.service.ArticleService;
import xyz.acproject.blogs.service.CategoryService;
import xyz.acproject.blogs.service.CommentService;
import xyz.acproject.blogs.service.LoginRecordService;
import xyz.acproject.blogs.tools.DateUtil;
import xyz.acproject.blogs.tools.page.page2.Page;
import xyz.acproject.blogs.tools.returnJson.FastjsonConfig.FastJsonUtil;
import xyz.acproject.blogs.tools.returnJson.FastjsonConfig.Response;

/**
 * @author zjian
 *
 */
@Controller
@RequestMapping("/manager")
public class ManagerController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private AnnouncementService announcementService;
	@Autowired
	private LoginRecordService loginRecordService;

	// 静态代码块
	static {
		try {
			initSigar();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 初始化sigar的配置文件
	private static void initSigar() throws IOException {
		SigarLoader loader = new SigarLoader(Sigar.class);
		String lib = null;

		try {
			lib = loader.getLibraryName();
		} catch (ArchNotSupportedException var7) {
//	        logger.error(var7.getMessage(), var7);
		}
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource resource = resourceLoader.getResource("classpath:/sigar/" + lib);
		if (resource.exists()) {
			InputStream is = resource.getInputStream();
			File tempDir = FileUtil.createTempDirectory();
			BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(new File(tempDir, lib), false));
			StreamUtil.copy(is, os);
			is.close();
			os.close();
			System.setProperty("org.hyperic.sigar.path", tempDir.getCanonicalPath());
		}
	}

	/**
	 * @param model
	 * @effect {管理主页面}
	 * @return
	 */
	@RequestMapping(value = { "/", "index" })
	public String index(Model model, HttpServletRequest req) {
		long acount = articleService.articlePropertyCount();
		long ccount = commentService.countByExample(null);
		long totalPv = articleService.CountByTotalArticlePv();
		long adminCount = adminService.countByExample(null);
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
		// 获取登陆记录
		List<LoginRecord> loginRecords = loginRecordService
				.selectByAidDesc(((Admin) req.getSession().getAttribute("admin")).getId());
		model.addAttribute("record", loginRecords);
		model.addAttribute("adminCount", adminCount);
		model.addAttribute("acount", acount);
		model.addAttribute("ccount", ccount);
		model.addAttribute("totalPv", totalPv);
		model.addAttribute("ip", ip);
		model.addAttribute("nowtime", time);
		Sigar sigar;
		try {
			// 应用服务器
			ServletContext servletContext = req.getSession().getServletContext();
			model.addAttribute("servletServer", servletContext.getServerInfo());
			// JDK版本
			model.addAttribute("javaVersion", SystemUtil.getJavaVersion());
			sigar = new Sigar();
			CpuInfo[] cpuInfoList = sigar.getCpuInfoList();
			// CPU信息
			model.addAttribute("cpu", cpuInfoList[0].getVendor() + "(" + cpuInfoList[0].getModel() + ") "
					+ cpuInfoList[0].getMhz() + "MHz x " + cpuInfoList.length + "核");
			// 操作系统
			String osname = System.getProperties().getProperty("os.name");
			model.addAttribute("osname", osname);
			// 服务器内存
			Mem freeMemory = sigar.getMem();
			model.addAttribute("mem", String.format("%.2fG",
					new Object[] { Double.valueOf((double) freeMemory.getTotal() / 1.073741824E9D) }));
			// JVM内存
			model.addAttribute("jvmMaxMem", String.format("%.2fM",
					new Object[] { Float.valueOf((float) Runtime.getRuntime().maxMemory() / 1048576.0F) }));
			// 服务器内存使用
			model.addAttribute("memUsedPercent",
					String.format("%.2f", new Object[] { Double.valueOf(freeMemory.getUsedPercent()) }));
			// CPU使用
			model.addAttribute("cpuUsed", String.format("%.2f", new Object[] {
					Double.valueOf(CpuPerc.format(sigar.getCpuPerc().getCombined()).replace("%", "")) }));
			long pid = sigar.getPid();
			long startTime = sigar.getProcTime(pid).getStartTime();
			// 系统运行时间
			model.addAttribute("runningTime", DateUtil.friendDuration(System.currentTimeMillis() - startTime));
			// 系统内存使用
			long maxMemory2 = Runtime.getRuntime().maxMemory();
			long freeMemory1 = Runtime.getRuntime().freeMemory();
			// JVM内存使用
			model.addAttribute("useableMemeory", String.format("%.2f", new Object[] {
					Double.valueOf((double) (maxMemory2 - freeMemory1) / ((double) maxMemory2 * 1.0D) * 100.0D) }));
		} catch (SigarException e) {
			System.out.println("logger:delete loginlog error");
		}
		return "manager/index";
	}

	/**
	 * @effect {去登陆页面}
	 * @return
	 */
	@RequestMapping("login")
	public String login() {
		return "manager/login";
	}

	/**
	 * @param req
	 * @param resp
	 * @param name
	 * @param pwd
	 * @effect {登陆入口}
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/tologin")
	public String tologin(HttpServletRequest req, HttpServletResponse resp, @RequestParam("username") String name,
			@RequestParam("password") String pwd) throws Exception {
		Admin admin = adminService.login(name, pwd, req);
		if (admin != null) {
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
			LoginRecord loginRecord = new LoginRecord();
			loginRecord.setAid(admin.getId());
			loginRecord.setIp(ip);
			loginRecord.setTime(time);
			loginRecordService.insertSelective(loginRecord);
			return "redirect:/manager/index";
		}
		// 上服务器需要加上 redirect 重定向
		return "manager/login";
	}

	/**
	 * @param name
	 * @param old_password
	 * @param password
	 * @param new_password
	 * @param req
	 * @param resp
	 * @effect {更新个人信息}
	 */
	@RequestMapping(value = "updateMessage", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })

	@ResponseBody
	public void updateMessage(@RequestParam("name") String name, @RequestParam("old_password") String old_password,

			@RequestParam("password") String password, @RequestParam("new_password") String new_password,
			HttpServletRequest req, HttpServletResponse resp) {
		if (!name.equals("")) {
			if (!old_password.equals("") && !password.equals("") && !new_password.equals("")) {
				if (old_password.equals(((Admin) (req.getSession().getAttribute("admin"))).getPassword())) {
					if (password.equals(new_password)) {
						if (!old_password.equals(new_password)) {
							Admin admin = new Admin();
							admin.setName(name);
							admin.setPassword(new_password);
							admin.setId(((Admin) (req.getSession().getAttribute("admin"))).getId());
							adminService.updateByPrimaryKeySelective(admin);
							req.getSession().removeAttribute("admin");
							try {
								resp.getWriter().write("true");// 旧密码与新密码一样错误
							} catch (IOException e) {
								// TODO 自动生成的 catch 块
								e.printStackTrace();
							}
						} else {
							try {
								resp.getWriter().write("pwderror1");// 旧密码与新密码一样错误
							} catch (IOException e) {
								// TODO 自动生成的 catch 块
								e.printStackTrace();
							}
						}
					} else {
						try {
							resp.getWriter().write("pwderror2");// 两次新密码要一样
						} catch (IOException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
					}
				} else {
					try {
						resp.getWriter().write("pwderror3");// 旧密码不相符
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}

			} else {
				try {
					resp.getWriter().write("pwdnullerror");
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		} else {
			try {
				resp.getWriter().write("nullerror");
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param model
	 * @param req
	 * @effect {文章页面入口}
	 * @return
	 */
	@RequestMapping("article")
	public String article(Model model, HttpServletRequest req) {
		String pageNow = req.getParameter("pageNow");
		long totalCount = articleService.articlePropertyCount();
		Page page = null;
		if (pageNow != null) {
			page = new Page(Integer.parseInt(pageNow), (int) totalCount);
			page.setPageSize(10);
		} else {
			page = new Page(1, (int) totalCount);
			page.setPageSize(10);
		}
		List<ArticleProperty> aList = articleService.selectListByPage2(page.getStartPos(), page.getPageSize());
		model.addAttribute("aList", aList);
		model.addAttribute("page", page);
		// 获取登陆记录
		List<LoginRecord> loginRecords = loginRecordService
				.selectByAidDesc(((Admin) req.getSession().getAttribute("admin")).getId());
		model.addAttribute("record", loginRecords);
		return "manager/article";
	}

	/**
	 * @param req
	 * @effect {管理员退出}
	 * @return
	 */
	@RequestMapping("exit")
	public String exit(HttpServletRequest req) {
		req.getSession().removeAttribute("admin");
		return "manager/login";
	}

	/**
	 * @param apid
	 * @param req
	 * @param resp
	 * @effect {删除文章}
	 */
	@ResponseBody
	@RequestMapping(value = "deleteArticle", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	public void deleteArticle(@RequestParam("apid") Integer apid, HttpServletRequest req, HttpServletResponse resp) {
		int a = articleService.deleteArticle(apid);
		if (a != 0) {
			try {
				resp.getWriter().write("true");
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		} else {
			try {
				resp.getWriter().write("false");
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param model
	 * @effect {跳转到添加文章页面}
	 * @return
	 */
	@RequestMapping("add-article")
	public String add_article(Model model, HttpServletRequest req) {
		List<Bigcategory> bcList = categoryService.selectbcList();
		List<Smallcategory> scList = categoryService.selectscList();
		model.addAttribute("bcList", bcList);
		model.addAttribute("scList", scList);
		// 获取登陆记录
		List<LoginRecord> loginRecords = loginRecordService
				.selectByAidDesc(((Admin) req.getSession().getAttribute("admin")).getId());
		model.addAttribute("record", loginRecords);
		return "manager/add-article";
	}

	/**
	 * @param apid
	 * @param title
	 * @param content
	 * @param describe
	 * @param imgUrl
	 * @param bcid
	 * @param smallCategory
	 * @param model
	 * @param req
	 * @param resp
	 * @effect {插入新的文章}
	 */
	@RequestMapping(value = "insertArticle", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public void insertArticle(Integer apid, @RequestParam("title") String title,
			@RequestParam("content") String content, @RequestParam("describe") String describe,
			@RequestParam("imgUrl") String imgUrl, @RequestParam("bcid") Integer bcid,
			@RequestParam("smallCategory") String smallCategory, Model model, HttpServletRequest req,
			HttpServletResponse resp) {
		int a = articleService.insertArticle(apid, title, content, describe, imgUrl, bcid, smallCategory, model, req,
				resp);
		if (a != 0) {
			try {
				resp.getWriter().write("true");
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		} else {
			try {
				resp.getWriter().write("false");
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param id
	 * @param model
	 * @effect {跳转到文章更新页面}
	 * @return
	 */
	@RequestMapping("update-article")
	public String update_article(@RequestParam("id") Integer id, Model model, HttpServletRequest req) {
		ArticleProperty articleProperty = articleService.selectListByid(id);
		if (articleProperty != null) {
			// 获取登陆记录
			List<LoginRecord> loginRecords = loginRecordService
					.selectByAidDesc(((Admin) req.getSession().getAttribute("admin")).getId());
			model.addAttribute("record", loginRecords);
			List<Bigcategory> bcList = categoryService.selectbcList();
			List<Smallcategory> scList = categoryService.selectscList();
			model.addAttribute("article", articleProperty);
			model.addAttribute("bcList", bcList);
			model.addAttribute("scList", scList);
			return "manager/update-article";
		} else {
			return "redirect:/404";
		}
	}

	/**
	 * @param apid
	 * @param title
	 * @param content
	 * @param describe
	 * @param imgUrl
	 * @param bcid
	 * @param smallCategory
	 * @param model
	 * @param req
	 * @param resp
	 * @effect {更新文章}
	 */
	@RequestMapping(value = "updateArticle", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public void updateArticle(@RequestParam("apid") Integer apid, @RequestParam("title") String title,
			@RequestParam("content") String content, @RequestParam("describe") String describe,
			@RequestParam("imgUrl") String imgUrl, @RequestParam("bcid") Integer bcid,
			@RequestParam("smallCategory") String smallCategory, Model model, HttpServletRequest req,
			HttpServletResponse resp) {
		int a = articleService.updateArticle(apid, title, content, describe, imgUrl, bcid, smallCategory, model, req,
				resp);
		if (a != 0) {
			try {
				resp.getWriter().write("true");
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		} else {
			try {
				resp.getWriter().write("false");
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param model
	 * @param req
	 * @effect {跳到公告页面}
	 * @return
	 */
	@RequestMapping("notice")
	public String Notice(Model model, HttpServletRequest req) {
		String pageNow = req.getParameter("pageNow");
		long totalCount = announcementService.countByExample(null);
		Page page = null;
		if (pageNow != null) {
			page = new Page(Integer.parseInt(pageNow), (int) totalCount);
			page.setPageSize(10);
		} else {
			page = new Page(1, (int) totalCount);
			page.setPageSize(10);
		}
		List<Announcement> aList = announcementService.selectListByPage2(page.getStartPos(), page.getPageSize());
		model.addAttribute("aList", aList);
		model.addAttribute("page", page);
		// 获取登陆记录
		List<LoginRecord> loginRecords = loginRecordService
				.selectByAidDesc(((Admin) req.getSession().getAttribute("admin")).getId());
		model.addAttribute("record", loginRecords);
		return "manager/notice";
	}

	/**
	 * @param apid
	 * @param req
	 * @param resp
	 * @effect {删除公告}
	 */
	@ResponseBody
	@RequestMapping(value = "deleteAnnounce", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	public void deleteAnnounce(@RequestParam("id") Integer id, HttpServletRequest req, HttpServletResponse resp) {
		int a = announcementService.deleteByPrimaryKey(id);
		if (a != 0) {
			try {
				resp.getWriter().write("true");
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		} else {
			try {
				resp.getWriter().write("false");
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}

	/**
	 * @effect {跳转到添加公告页面}
	 * @return
	 */
	@RequestMapping("add-notice")
	public String add_notice(HttpServletRequest req, Model model) {
		// 获取登陆记录
		List<LoginRecord> loginRecords = loginRecordService
				.selectByAidDesc(((Admin) req.getSession().getAttribute("admin")).getId());
		model.addAttribute("record", loginRecords);
		return "manager/add-notice";
	}

	/**
	 * @param value
	 * @param req
	 * @param resp
	 * @effect {插入新公告}
	 */
	@RequestMapping(value = "insertAnnounce", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public void insertAnnounce(@RequestParam("content") String value, HttpServletRequest req,
			HttpServletResponse resp) {
		Announcement announcement = new Announcement();
		announcement.setValue(value);
		announcement.setAid(((Admin) req.getSession().getAttribute("admin")).getId());
		Calendar calendar = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeString = dateFormat.format(calendar.getTime());
		Timestamp time = Timestamp.valueOf(timeString.toString());
		announcement.setTime(time);
		int a = announcementService.insertSelective(announcement);
		if (a != 0) {
			try {
				resp.getWriter().write("true");
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		} else {
			try {
				resp.getWriter().write("false");
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param id
	 * @param model
	 * @effect {跳转更新公告界面}
	 * @return
	 */
	@RequestMapping("update-notice")
	public String update_notice(@RequestParam("id") Integer id, Model model, HttpServletRequest req) {
		if (announcementService.selectByPrimaryKey(id) != null) {
			Announcement announcement = announcementService.selectByPrimaryKey(id);
			model.addAttribute("announce", announcement);
			// 获取登陆记录
			List<LoginRecord> loginRecords = loginRecordService
					.selectByAidDesc(((Admin) req.getSession().getAttribute("admin")).getId());
			model.addAttribute("record", loginRecords);
			return "manager/update-notice";
		} else {
			return "redirect:/404";
		}
	}

	/**
	 * @param id
	 * @param value
	 * @param req
	 * @param resp
	 * @effect {更新公告}
	 */
	@RequestMapping(value = "updateAnnounce", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public void updateAnnounce(@RequestParam("id") Integer id, @RequestParam("content") String value,
			HttpServletRequest req, HttpServletResponse resp) {

		Announcement announcement = new Announcement();
		announcement.setId(id);
		announcement.setValue(value);
		announcement.setAid(((Admin) req.getSession().getAttribute("admin")).getId());
		Calendar calendar = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeString = dateFormat.format(calendar.getTime());
		Timestamp time = Timestamp.valueOf(timeString.toString());
		announcement.setTime(time);
		int a = announcementService.updateByPrimaryKeySelective(announcement);
		if (a != 0) {
			try {
				resp.getWriter().write("true");
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		} else {
			try {
				resp.getWriter().write("false");
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param example
	 * @param model
	 * @param req
	 * @effect {跳转评论页面}
	 * @return
	 */
	@RequestMapping("comment")
	public String comment(CommentExample example, Model model, HttpServletRequest req) {
		String pageNow = req.getParameter("pageNow");
		long totalCount = commentService.countByExample(null);
		Page page = null;
		if (pageNow != null) {
			page = new Page(Integer.parseInt(pageNow), (int) totalCount);
			page.setPageSize(10);
		} else {
			page = new Page(1, (int) totalCount);
			page.setPageSize(10);
		}
		example.setOrderByClause("time desc");
		example.setOffset((long) page.getStartPos());
		example.setLimit(page.getPageSize());
		List<Comment> comments = commentService.selectByExample(example);
		model.addAttribute("comments", comments);
		model.addAttribute("page", page);
		// 获取登陆记录
		List<LoginRecord> loginRecords = loginRecordService
				.selectByAidDesc(((Admin) req.getSession().getAttribute("admin")).getId());
		model.addAttribute("record", loginRecords);
		return "manager/comment";
	}

	/**
	 * @param cid
	 * @param req
	 * @param resp
	 * @param writer
	 * @effect {获取评论详细}
	 */
	@RequestMapping(value = "commentJson", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public void commentJson(@RequestParam("cid") Integer cid, HttpServletRequest req, HttpServletResponse resp,
			PrintWriter writer) {
		Comment comment = commentService.selectByPrimaryKey(cid);
		String jsonString = FastJsonUtil.toJson(Response.success(comment, req));
		resp.setContentType("application/json;charset=UTF-8");
		writer.append(jsonString);
		writer.flush();
		writer.close();
	}

	/**
	 * @param cid
	 * @param req
	 * @param resp
	 * @effect {删除评论}
	 */
	@RequestMapping(value = "deleteComment", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public void deleteComment(@RequestParam("cid") Integer cid, HttpServletRequest req, HttpServletResponse resp) {
		// TODO 自动生成的方法存根
		Comment comment = commentService.selectByPrimaryKey(cid);
		if (comment.getCid() != null) {
			int c = commentService.deleteByPrimaryKey(cid);
			commentService.deleteByCidPraise(comment.getId());
			if (c != 0) {
				try {
					resp.getWriter().write("true");
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			} else {
				try {
					resp.getWriter().write("false");
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		} else {
			int a = commentService.deleteByPrimaryKey(cid);
			commentService.deleteByCidPraise(cid);
			List<Comment> comments = commentService.selectListByCid(cid);
			if (comments.size() > 0) {
				List<Integer> ids = comments.stream().map(Comment::getId).collect(Collectors.toList());
				int b = commentService.deleteByCid(cid);
				commentService.deleteByCidsPraise(ids);
				if (a != 0 && b != 0) {
					try {
						resp.getWriter().write("true");
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				} else {
					try {
						resp.getWriter().write("false");
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
			} else {
				if (a != 0) {
					try {
						resp.getWriter().write("true");
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				} else {
					try {
						resp.getWriter().write("false");
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
			}
		}
		long count = articleService.countByApid(comment.getApid());
		commentService.updateByApidOnCommentNum((int) count, comment.getApid());
	}

	/**
	 * @param model
	 * @param req
	 * @effect {进入管理员页面}
	 * @return
	 */
	@RequestMapping("manager-user")
	public String manager_user(Model model, HttpServletRequest req) {
		int count = 0;
		List<Admin> admins = null;
		Admin admin = (Admin) req.getSession().getAttribute("admin");
		if (admin.getRemark().equals("主")) {
			admins = adminService.selectByExample(null);
			for (Admin admin2 : admins) {
				Admin record = new Admin();
				count = articleService.countBycreateMan(admin2.getName());
				record.setId(admin2.getId());
				record.setArticlenum(count);
				adminService.updateByPrimaryKeySelective(record);
			}
			admins = adminService.selectByExample(null);
			model.addAttribute("admins", admins);
			// 获取登陆记录
			List<LoginRecord> loginRecords = loginRecordService
					.selectByAidDesc(((Admin) req.getSession().getAttribute("admin")).getId());
			model.addAttribute("record", loginRecords);
			return "manager/manager-user";
		} else {
			return "redirect:/404";
		}
	}

	/**
	 * @param name
	 * @param remark
	 * @param password
	 * @param re_password
	 * @param req
	 * @param resp
	 * @effect {插入新的管理员}
	 */
	@RequestMapping(value = "insertAdmin", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public void insertAdmin(@RequestParam("name") String name, @RequestParam("remark") String remark,
			@RequestParam("password") String password, @RequestParam("re_password") String re_password,
			HttpServletRequest req, HttpServletResponse resp) {
		Admin admins = (Admin) req.getSession().getAttribute("admin");
		if (admins.getRemark().equals("主")) {
			if (!name.trim().equals("") && !password.equals("") && !re_password.trim().equals("")) {
				if (password.equals(re_password)) {
					Admin admin = new Admin();
					admin.setName(name.trim());
					admin.setRemark(remark.trim());
					admin.setPassword(password.trim());
					admin.setHeadimg("1");
					int a = adminService.insertSelective(admin);
					if (a != 0) {
						try {
							resp.getWriter().write("true");
						} catch (IOException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
					} else {
						try {
							resp.getWriter().write("false");
						} catch (IOException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
					}
				} else {
					try {
						resp.getWriter().write("passworderror");
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
			} else {
				try {
					resp.getWriter().write("error");
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		} else {
			try {
				resp.sendRedirect("../404");
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param id
	 * @param req
	 * @param resp
	 * @param writer
	 * @effect {获取管理员详细信息}
	 */
	@RequestMapping(value = "adminJson", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public void adminJson(@RequestParam("id") Integer id, HttpServletRequest req, HttpServletResponse resp,
			PrintWriter writer) {
		Admin admin = adminService.selectByPrimaryKey(id);
		String jsonString = FastJsonUtil.toJson(Response.success(admin, req));
		resp.setContentType("application/json;charset=UTF-8");
		writer.append(jsonString);
		writer.flush();
		writer.close();
	}

	/**
	 * @param name
	 * @param remark
	 * @param headimg
	 * @param old_password
	 * @param password
	 * @param new_password
	 * @param id
	 * @param req
	 * @param resp
	 * @effect {更新管理员}
	 */
	@RequestMapping(value = "updateAdmin", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public void updateAdmin(@RequestParam("name") String name, @RequestParam("remark") String remark,
			@RequestParam("headimg") String headimg, @RequestParam("old_password") String old_password,
			@RequestParam("password") String password, @RequestParam("new_password") String new_password,
			@RequestParam("userid") Integer id, HttpServletRequest req, HttpServletResponse resp) {
		// TODO 自动生成的方法存根
		if (!name.trim().equals("") && !remark.trim().equals("") && !headimg.trim().equals("")
				&& !old_password.trim().equals("")) {
			if (!password.trim().equals("") && !old_password.trim().equals("")) {
				if (password.equals(new_password)) {
					if (!old_password.equals(password)) {
						Admin record = new Admin();
						System.out.println(id);
						record.setId(id);
						record.setName(name);
						record.setRemark(remark);
						record.setHeadimg(headimg);
						record.setPassword(new_password);
						int a = adminService.updateByPrimaryKeySelective(record);
						if (a != 0) {
							try {
								resp.getWriter().write("true");
							} catch (IOException e) {
								// TODO 自动生成的 catch 块
								e.printStackTrace();
							}
						} else {
							try {
								resp.getWriter().write("false");
							} catch (IOException e) {
								// TODO 自动生成的 catch 块
								e.printStackTrace();
							}
						}
					} else {
						try {
							resp.getWriter().write("pwderror1");
						} catch (IOException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
					}
				} else {
					try {
						resp.getWriter().write("pwderror2");
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
			} else {
				Admin record = new Admin();
				System.out.println(id);
				record.setId(id);
				record.setName(name);
				record.setRemark(remark);
				record.setHeadimg(headimg);
				int a = adminService.updateByPrimaryKeySelective(record);
				if (a != 0) {
					try {
						resp.getWriter().write("true");
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				} else {
					try {
						resp.getWriter().write("false");
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
			}
		} else {
			try {
				resp.getWriter().write("nullerror");
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param id
	 * @param req
	 * @param resp
	 * @effect {删除管理员}
	 */
	@ResponseBody
	@RequestMapping(value = "deleteAdmin", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public void deleteAdim(@RequestParam("id") Integer id, HttpServletRequest req, HttpServletResponse resp) {
		Admin admins = (Admin) req.getSession().getAttribute("admin");
		if (admins.getRemark().equals("主")) {
			int a = adminService.deleteByPrimaryKey(id);
			if (a != 0) {
				try {
					resp.getWriter().write("true");
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			} else {
				try {
					resp.getWriter().write("false");
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		} else {
			try {
				resp.sendRedirect("../404");
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param model
	 * @effect {跳转到类别页面}
	 * @return
	 */
	@RequestMapping("category")
	public String category(Model model, HttpServletRequest req) {
		List<Bigcategory> bcList = categoryService.selectbcList();
		List<Smallcategory> scList = categoryService.selectscList();
		model.addAttribute("bcList", bcList);
		model.addAttribute("scList", scList);
		// 获取登陆记录
		List<LoginRecord> loginRecords = loginRecordService
				.selectByAidDesc(((Admin) req.getSession().getAttribute("admin")).getId());
		model.addAttribute("record", loginRecords);
		return "manager/category";
	}

	/**
	 * @param id
	 * @param request
	 * @param resp
	 * @effect {删除小类别}
	 */
	@ResponseBody
	@RequestMapping(value = "deleteSmallCategory", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	public void deleteSmallCategory(@RequestParam("id") Integer id, HttpServletRequest request,
			HttpServletResponse resp) {
		if (categoryService.deleteByPrimaryKeysc(id) != 0) {
			try {
				resp.getWriter().write("true");
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		} else {
			try {
				resp.getWriter().write("false");
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param bc
	 * @param sc
	 * @param request
	 * @param resp
	 * @effect {插入小类别}
	 */
	@ResponseBody
	@RequestMapping(value = "addCategory", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public void addCategory(@RequestParam("bc") String bc, @RequestParam("sc") String sc, HttpServletRequest request,
			HttpServletResponse resp) {
		if (!bc.trim().equals("") && !sc.trim().equals("")) {
			Bigcategory bigcategory = categoryService.selectByValuebc(bc);
			if (bigcategory != null) {
				Smallcategory record = new Smallcategory();
				record.setBcid(bigcategory.getId());
				record.setValue(sc);
				int a = categoryService.insertSelectivesc(record);
				if (a != 0) {
					try {
						resp.getWriter().write("true");
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				} else {
					try {
						resp.getWriter().write("false");
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
			} else {
				try {
					resp.getWriter().write("existserror");
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		} else {
			try {
				resp.getWriter().write("nullerror");
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param model
	 * @param req
	 * @effect {跳转到登陆日志页面}
	 * @return
	 */
	@RequestMapping("loginlog")
	public String loginlog(Model model, HttpServletRequest req, LoginRecordExample example) {
		// 获取登陆记录
		if (((Admin) req.getSession().getAttribute("admin")).getRemark().equals("主")) {
			List<LoginRecord> loginRecords = loginRecordService
					.selectByAidDesc(((Admin) req.getSession().getAttribute("admin")).getId());
			String pageNow = req.getParameter("pageNow");
			long totalCount = loginRecordService.countByExample(null);
			Page page = null;
			if (pageNow != null) {
				page = new Page(Integer.parseInt(pageNow), (int) totalCount);
				page.setPageSize(10);
			} else {
				page = new Page(1, (int) totalCount);
				page.setPageSize(10);
			}
			example.setOrderByClause("time desc");
			example.setOffset((long) page.getStartPos());
			example.setLimit(page.getPageSize());
			List<LoginRecord> loginRecords2 = loginRecordService.selectByExample(example);
			model.addAttribute("record", loginRecords);
			model.addAttribute("records", loginRecords2);
			model.addAttribute("page", page);
			return "manager/loginlog";
		} else {
			return "redirect:../404";
		}
	}

	/**
	 * @param id
	 * @param req
	 * @param resp
	 * @effect {删除登陆日志}
	 */
	@ResponseBody
	@RequestMapping(value = "deleteloginlog")
	public void deleteloginlog(@RequestParam("id") Integer id, HttpServletRequest req, HttpServletResponse resp) {
		if (loginRecordService.deleteByPrimaryKey(id) != 0) {
			try {
				resp.getWriter().write("true");
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		} else {
			try {
				resp.getWriter().write("false");
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param request
	 * @param response
	 * @effect {ueditor管理器配置}
	 * @throws Exception
	 */
	@RequestMapping("ueditor")
	@ResponseBody
	public void config(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/json");
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		try {
			String exec = new ActionEnter(request, rootPath).exec();
			PrintWriter writer = response.getWriter();
			writer.write(exec);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
