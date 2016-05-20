package org.cc.core.mybatis;

import java.lang.reflect.Field;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.github.pagehelper.PageHelper;

@Import(MyBatisMapperScannerRegistrar.class)
@Configuration
@EnableConfigurationProperties(MybatisPageHelperParam.class)
public class MyBatisPageHelper {
	
	private static Log log = LogFactory.getLog(MyBatisPageHelper.class);
	
	@Autowired
	private MybatisPageHelperParam properties;
	
	/**
	 * 分页插件
	 * @return
	 */
	@Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        if(properties!=null){
        	MybatisPageHelperParam paramBean =new MybatisPageHelperParam();
        	@SuppressWarnings("unchecked")
			Class<MybatisPageHelperParam> clazz = (Class<MybatisPageHelperParam>) paramBean.getClass();
        	Field[] fieldArray = clazz.getDeclaredFields();
        	for(int i = 0; i<fieldArray.length;i++){
        		Field tmpField = fieldArray[i];
        		//过滤掉声明的公共方法
        		if(!tmpField.isAccessible()){
        			tmpField.setAccessible(true); //设置些属性是可以访问的
            		try {
    					if(tmpField.get(properties)!=null){
    						p.setProperty(tmpField.getName(), tmpField.get(properties).toString());
    					}
    				} catch (IllegalArgumentException e) {
    					log.error(e.getMessage());
    				} catch (IllegalAccessException e) {
    					log.error(e.getMessage());
    				}
        		}
        	}
        }
        pageHelper.setProperties(p);
        return pageHelper;
    }
}