package org.cc.core.mybatis;

import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * 分页插件的配置属性
 * @author wangwei
 * @date 2016年4月5日
 */
@ConfigurationProperties(prefix = MybatisPageHelperParam.PAGEHELPER_PREFIX)
public class MybatisPageHelperParam {
	
	public static final String PAGEHELPER_PREFIX = "mybatis.pagehelper";
	
	private String dialect;
	private String reasonable;
	private String supportMethodsArguments;
	private String returnPageInfo;
	private String params;
	
	public String getDialect() {
		return dialect;
	}
	public void setDialect(String dialect) {
		this.dialect = dialect;
	}
	public String getReasonable() {
		return reasonable;
	}
	public void setReasonable(String reasonable) {
		this.reasonable = reasonable;
	}
	public String getSupportMethodsArguments() {
		return supportMethodsArguments;
	}
	public void setSupportMethodsArguments(String supportMethodsArguments) {
		this.supportMethodsArguments = supportMethodsArguments;
	}
	public String getReturnPageInfo() {
		return returnPageInfo;
	}
	public void setReturnPageInfo(String returnPageInfo) {
		this.returnPageInfo = returnPageInfo;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	
}
