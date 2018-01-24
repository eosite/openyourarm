package com.glaf.matrix;

import java.util.Collection;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.glaf.core.config.DBConfiguration;
import com.glaf.core.util.AnnotationUtils;
import com.glaf.core.util.ClassUtils;

public class HibernateTest {

	public static void main(String[] args) {
		Configuration configuration = new Configuration();
		Properties props = DBConfiguration.getDefaultDataSourceProperties();
		if (props != null && !props.isEmpty()) {
			configuration.setProperty("hibernate.hbm2ddl.auto", "update");
			if (StringUtils.isEmpty(configuration.getProperty("hibernate.connection.driver_class"))) {
				configuration.setProperty("hibernate.connection.driver_class",
						props.getProperty(DBConfiguration.JDBC_DRIVER));
			}
			if (StringUtils.isEmpty(configuration.getProperty("hibernate.connection.url"))) {
				configuration.setProperty("hibernate.connection.url", props.getProperty(DBConfiguration.JDBC_URL));
			}
			if (StringUtils.isEmpty(configuration.getProperty("hibernate.connection.username"))) {
				configuration.setProperty("hibernate.connection.username",
						props.getProperty(DBConfiguration.JDBC_USER));
			}
			if (StringUtils.isEmpty(configuration.getProperty("hibernate.connection.password"))) {
				configuration.setProperty("hibernate.connection.password",
						props.getProperty(DBConfiguration.JDBC_PASSWORD));
			}
			if (StringUtils.isEmpty(configuration.getProperty("hibernate.dialect"))) {
				configuration.setProperty("hibernate.dialect", DBConfiguration.getDefaultHibernateDialect());
			}
		}
		Collection<String> names = AnnotationUtils.findJPAEntity("com.glaf");
		for (String name : names) {
			System.out.println(name);
			configuration.addAnnotatedClass(ClassUtils.classForName(name));
		}
		SessionFactory sessionFactory = configuration.buildSessionFactory();
		System.out.println("sessionFactory:" + sessionFactory);
	}

}
