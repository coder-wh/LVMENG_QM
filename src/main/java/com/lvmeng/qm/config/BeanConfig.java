package com.lvmeng.qm.config;

import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.lvmeng.qm.base.commons.util.CorsFilter;
import com.lvmeng.qm.base.commons.util.CustomObjectMapper;

@Configuration
public class BeanConfig extends WebMvcConfigurerAdapter {
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setForceEncoding(true);
		characterEncodingFilter.setEncoding("UTF-8");
		registrationBean.setFilter(characterEncodingFilter);
		registrationBean.setOrder(9);
		return registrationBean;
	}
	/**
	 * 跨域监听�?
	 * @return
	 */
	@Bean
	public FilterRegistrationBean CorsFilterBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		CorsFilter corsFilter = new CorsFilter();
		registrationBean.addInitParameter("allowOrigin", "*");
		registrationBean.addInitParameter("allowMethods", "GET,POST,PUT,DELETE,OPTIONS");
		registrationBean.addInitParameter("allowCredentials", "true");
		registrationBean.addInitParameter("allowHeaders", "Content-Type,X-Auth-Token");
		registrationBean.setFilter(corsFilter);
		registrationBean.setOrder(10);
		return registrationBean;
	}
	@Bean
	public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
		ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
		registration.getUrlMappings().clear();
		registration.addUrlMappings("/");
		// registration.addUrlMappings("*.json");
		registration.addUrlMappings("*.html");
		return registration;
	}
	
//	@Bean
//	public AdminInterceptor adminInterceptor() {
//		return new AdminInterceptor();
//	}
//
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		// 多个拦截器组成一个拦截器�?
//		// addPathPatterns 用于添加拦截规则
//		// excludePathPatterns 用户排除拦截
//		registry.addInterceptor(adminInterceptor()).addPathPatterns("/cal/**").excludePathPatterns("/cal/login/**");
////		registry.addInterceptor(new SysInterceptor()).addPathPatterns("/sys/**");
//		super.addInterceptors(registry);
//	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(converter());
		super.configureMessageConverters(converters);
	}

	public MappingJackson2HttpMessageConverter converter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		CustomObjectMapper objectMapper = new CustomObjectMapper();
		objectMapper.setDateFormatPattern("yyyy-MM-dd HH:mm:ss");
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setSerializationInclusion(Include.NON_NULL); 
		converter.setObjectMapper(objectMapper);
		return converter;
	}
	/**
	 * 文件上传配置
	 * @return
	 */
	@Bean
	public CommonsMultipartResolver resolver(){
		CommonsMultipartResolver bean = new CommonsMultipartResolver();
		bean.setDefaultEncoding("UTF-8");
		bean.setMaxUploadSize(209715200);//200M = 200 * 1024 *1024
		bean.setMaxInMemorySize(4096);
		bean.setResolveLazily(true);
		return bean;
	}
}