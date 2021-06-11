package xyz.acproject.blogs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.resource.ContentVersionStrategy;
import org.springframework.web.servlet.resource.VersionResourceResolver;
import xyz.acproject.blogs.interceptor.LoginInterceptor;
import xyz.acproject.blogs.interceptor.MainInterceptor;

@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurationSupport {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/manager/*").addPathPatterns("/record/*").excludePathPatterns("/manager/login").excludePathPatterns("/manager/tologin");
		registry.addInterceptor(new MainInterceptor()).addPathPatterns("/*");
		super.addInterceptors(registry);
	}

	// 解决了拦截器拦截了静态资源的问题 和 拦截了不该拦截的东西的问题
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		VersionResourceResolver versionResourceResolver = new VersionResourceResolver()
				.addVersionStrategy(new ContentVersionStrategy(), "/**");
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/").setCachePeriod(2592000)
				.resourceChain(true).addResolver(versionResourceResolver);
		//生产环境E:\banqi\blogs\images
//		registry.addResourceHandler("/ueditor/images/**").addResourceLocations("file:/root/internet/ueditor/images/");
//		registry.addResourceHandler("/file/**").addResourceLocations("file:/root/internet/file/");
//		registry.addResourceHandler("/record/**").addResourceLocations("file:/root/internet/record/");

		registry.addResourceHandler("/ueditor/images/**").addResourceLocations("file:E:/banqi/blogs/images/ueditor/images/");
		registry.addResourceHandler("/file/**").addResourceLocations("file:E:/banqi/blogs/file/");
		registry.addResourceHandler("/record/**").addResourceLocations("file:E:/banqi/blogs/record/");
		//测试环境
//		registry.addResourceHandler("/ueditor/images/**").addResourceLocations("file:E:/ueditor/images/");
//		registry.addResourceHandler("/file/**").addResourceLocations("file:E:/file/");
		super.addResourceHandlers(registry);
	}
}
