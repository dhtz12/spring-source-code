package org.springframework.applicationContext;

import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;


public class ApplicationContextTest {


	@Test
	public void testApplicationContextGetBean() {
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		User user = (User) context.getBean("user");
		System.out.println("ApplicationContextTest.testApplicationContextGetBean"+ user.toString());
	}

	@Test
	public void testLoadBeanByDefaultListableBeanFactory() {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader definitionReader = new XmlBeanDefinitionReader(beanFactory);
		definitionReader.loadBeanDefinitions(new ClassPathResource("applicationContext.xml"));
		Object user = beanFactory.getBean("user");
		System.out.println(user);

	}

}
