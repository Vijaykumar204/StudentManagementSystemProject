package com.studentmanagementsystem.api.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@Configuration
@ComponentScan(basePackages = "com.studentmanagementsystem.api")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.studentmanagementsystem.api.repository")

public class JpaConfiguration {
	 @Bean
	    public DataSource dataSource() {
	        DriverManagerDataSource ds = new DriverManagerDataSource();
	        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
	        ds.setUrl("jdbc:mysql://localhost:3306/studentmanagementsystem");
	        ds.setUsername("root");
	        ds.setPassword("Vijay@204");
	        return ds;
	    }

	    @Bean
	    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
	        emf.setDataSource(dataSource());
	        emf.setPackagesToScan("com.studentmanagementsystem.api.model.entity");
	        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
	        Properties jpaProps = new Properties();
	        jpaProps.setProperty("hibernate.hbm2ddl.auto", "update");
	        jpaProps.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
	        jpaProps.setProperty("hibernate.show_sql", "true");

	        emf.setJpaProperties(jpaProps);
	        return emf;
	    }

	    @Bean
	    public JpaTransactionManager transactionManager() {
	        JpaTransactionManager txManager = new JpaTransactionManager();
	        txManager.setEntityManagerFactory(entityManagerFactory().getObject());
	        return txManager;
	    }

//	    @Bean
//	    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
//	        return new PersistenceExceptionTranslationPostProcessor();
//	    }

}
