package org.cc.core.mybatis;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.bind.PropertiesConfigurationFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;

/**
 * 使用@MapperScan可以解决mybatis扫描所有接口映射。但是无法在配置文件中配置使用,所以添加了该类去处理问题
 */
//@MapperScan(basePackages = {"org.cc.dao"})
@Configuration
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class MyBatisMapperScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware, BeanFactoryAware{
	private static Log log = LogFactory.getLog(MyBatisMapperScannerRegistrar.class);
	
	private Environment environment;

	private BeanFactory beanFactory;

	private ResourceLoader resourceLoader;

	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

		ClassPathMapperScanner scanner = new ClassPathMapperScanner(registry);
		if (resourceLoader != null) {
			scanner.setResourceLoader(resourceLoader);
		}
		MybatisYamlParam myBatisProperties = getMyBatisProperties();
		List<String> basePackages = new ArrayList<String>();
		String[] propertiesBasePackages = myBatisProperties.getBasePackages();
		if (propertiesBasePackages != null && propertiesBasePackages.length > 0) {
			for (String basePackage : propertiesBasePackages) {
				if (StringUtils.hasText(basePackage)) {
					basePackages.addAll(StringUtils.commaDelimitedListToSet(basePackage));
				}
			}
		} else {
			basePackages.addAll(AutoConfigurationPackages.get(this.beanFactory));
		}
		try {
			scanner.registerFilters();
			scanner.doScan(StringUtils.toStringArray(basePackages));
		} catch (IllegalStateException ex) {
			log.debug("Could not determine auto-configuration " + "package, automatic mapper scanning disabled.");
		}
	}

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	
	private MybatisYamlParam getMyBatisProperties() {
		MybatisYamlParam myBatisProperties = new MybatisYamlParam();
		PropertiesConfigurationFactory<MybatisYamlParam> factory = new PropertiesConfigurationFactory<MybatisYamlParam>(
				myBatisProperties);
		factory.setConversionService(new DefaultConversionService());
		ConfigurationProperties annotation = AnnotationUtils.findAnnotation(MybatisYamlParam.class,
				ConfigurationProperties.class);
		String prefix = StringUtils.hasText(annotation.value()) ? annotation.value() : annotation.prefix();
		factory.setTargetName(prefix);

		if (environment instanceof ConfigurableEnvironment) {
			ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment) environment;
			MutablePropertySources propertySources = configurableEnvironment.getPropertySources();
			factory.setPropertySources(propertySources);
		}

		try {
			factory.bindPropertiesToTarget();
		} catch (BindException e) {
			throw new BeanCreationException("myBatisProperties",
					"Could not bind properties to " + MybatisYamlParam.class + " (" + annotation + ")", e);
		}
		return myBatisProperties;
	}
}
