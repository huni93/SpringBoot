package com.example.demo;

import java.util.Collections;

import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.util.descriptor.web.JspConfigDescriptorImpl;
import org.apache.tomcat.util.descriptor.web.JspPropertyGroup;
import org.apache.tomcat.util.descriptor.web.JspPropertyGroupDescriptorImpl;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import dao.LoginAdmin;
import dao.LoginUser;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = "controller, dao")
public class KicspringbootApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(KicspringbootApplication.class, args);
		
	}
	@Bean  //jsp-config
	   public ConfigurableServletWebServerFactory configurableServletWebServerFactory() {
	      return new TomcatServletWebServerFactory() {

	         @Override
	         protected void postProcessContext(Context context) {
	            super.postProcessContext(context);
	            JspPropertyGroup jspPropertyGroup = new JspPropertyGroup();
	            jspPropertyGroup.addUrlPattern("/WEB-INF/view/*");
	            jspPropertyGroup.addIncludePrelude("/common/head.jsp");

	            JspPropertyGroupDescriptorImpl jspPropertyGroupDescriptor = new JspPropertyGroupDescriptorImpl(
	                  jspPropertyGroup);
	            context.setJspConfigDescriptor(new JspConfigDescriptorImpl(
	                  Collections.singletonList(jspPropertyGroupDescriptor), Collections.emptyList()));
	         }
	      };
	   }
	
	@Bean // mybatis spring boot가 만든 mybatis db pool을 사용 한다
	   public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
	      SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
	      sessionFactory.setDataSource(dataSource);

	      sessionFactory.setConfigLocation(
	            new PathMatchingResourcePatternResolver().getResource("classpath:mybatis/mybatis-config.xml"));

	      return sessionFactory.getObject();
	   }

	   @Bean
	   public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
	      return new SqlSessionTemplate(sqlSessionFactory);
	   }
	   @Autowired
	   LoginUser loginInterceptor;
	   
	   @Autowired
	   LoginAdmin adimInterceptor;
	   
	   @Override
	   public void addInterceptors (InterceptorRegistry registry) {
	      System.out.println("interceptor");
	      registry
	      .addInterceptor(adimInterceptor)
	      .addPathPatterns("/admin/*")
	      .addPathPatterns("/member/memberList");
	      
	      
	      registry.addInterceptor(loginInterceptor)
	      .addPathPatterns("/member/memberInfo")
	      .addPathPatterns("/member/memberUpdateForm")
	      .addPathPatterns("/member/memberDelete");
	    }

}
