package org.cc.web;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cc.core.mybatis.MybatisYamlParam;
import org.cc.entity.Account;
import org.cc.entity.Book;
import org.cc.service.AccountService;
import org.cc.service.BookService;
import org.cc.service.RedisTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRest {
	
	private static Log log = LogFactory.getLog(HelloRest.class);
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private StringRedisTemplate template;
	
	@Autowired
	private MybatisYamlParam properties;
	
	@Autowired
	private RedisTemplateService redisTemplateService;
	
	@Autowired
	private BookService bookService;
	
	@RequestMapping("home")
	public List<Account> home() {
		List<Account> list = accountService.getList();
		ValueOperations<String, String> ops = this.template.opsForValue();
		String key = "com.sun.proxy.$Proxy56queryAll";
		System.out.println("Found key " + key + ", value=" + ops.get(key));
		return list;
	}
	
	@RequestMapping("mybatis")
	public MybatisYamlParam mybatis() {
		log.error("when this appered means the redis works");
		return properties;
	}
	
	@RequestMapping("redis")
	public String redis(){
		redisTemplateService.set("boot_run_test", "when this appered means the redis works");
		String result = redisTemplateService.get("boot_run_test")!=null?redisTemplateService.get("boot_run_test").toString():"error";
		redisTemplateService.delete("boot_run_test");
		return result;
	}
	
	@RequestMapping("mongo")
	public Book mongo(){
		//对象存储
		Book book = new Book("song的路", "wait", "on the way", 777);
		bookService.save(book);
		return book;
	}
}
