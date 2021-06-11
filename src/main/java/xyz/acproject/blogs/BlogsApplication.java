package xyz.acproject.blogs;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@MapperScan(value = "xyz.acproject.blogs.dao")
public class BlogsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogsApplication.class, args);
	}

	
}
