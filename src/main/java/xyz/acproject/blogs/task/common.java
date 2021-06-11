package xyz.acproject.blogs.task;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import xyz.acproject.blogs.entity.ArticleProperty;
import xyz.acproject.blogs.entity.LoginRecord;
import xyz.acproject.blogs.service.ArticleService;
import xyz.acproject.blogs.service.LoginRecordService;

@Component
public class common {
	@Autowired
	private LoginRecordService loginRecordService;
	@Autowired
	private ArticleService articleService;
	// 定期凌晨3點删除登陆日志
	@Scheduled(cron = "0 0 3 * * *")
	@Transactional
	public void deleteLoginRecoid() {
		// 获取时间
	    Calendar calendar = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeString = dateFormat.format(calendar.getTime());
		Timestamp time = Timestamp.valueOf(timeString.toString());
		// 删除登陆日志
		List<LoginRecord> loginRecordss = loginRecordService.selectByExample(null);
		for (LoginRecord loginRecord : loginRecordss) {
			if (time.getTime() - loginRecord.getTime().getTime() >= (1000 * 3600 * 24 * 7)) {
				loginRecordService.deleteByPrimaryKey(loginRecord.getId());
			}
		}
	}
	// 定期凌晨3點更新熱門文章
	@Scheduled(cron = "0 0 3 * * *")
	@Transactional
	public void addHotArticle() {
		List<ArticleProperty> articleProperties = articleService.selectByExampleP(null);
		for (ArticleProperty articleProperty : articleProperties) {
			if(!articleProperty.getTop().trim().equals("0")) {
				ArticleProperty a = new ArticleProperty();
				a.setId(articleProperty.getId());
				a.setTop("0".trim());
				articleService.updateByPrimaryKeySelectiveP(a);
			}
		}
		List<ArticleProperty> apList = articleService.selectListOrderByarticlePvDesc();
		if(apList!=null) {
		ArticleProperty articleProperty = new ArticleProperty();
		articleProperty.setId(apList.get(0).getId());
		articleProperty.setTop("1".trim());
		articleService.updateByPrimaryKeySelectiveP(articleProperty);
		}
	}
}
