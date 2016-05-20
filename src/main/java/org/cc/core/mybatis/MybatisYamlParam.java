package org.cc.core.mybatis;

import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 配置文件内的设置
 * 
 * @author wangwei
 * @date 2016年4月5日
 */
@Component
@ConfigurationProperties(prefix = MybatisProperties.MYBATIS_PREFIX)
public class MybatisYamlParam {

	/**
	 * Package to scan dao.
	 */
	private String[] basePackages;

	
	public String[] getBasePackages() {
		return basePackages;
	}

	public void setBasePackages(String[] basePackages) {
		this.basePackages = basePackages;
	}
}
