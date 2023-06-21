package com.song.customizeaop;

import org.springframework.aop.PointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理调用advice pointcut.
 */
public class MyAopAspectJPostProcessor implements BeanPostProcessor {

	private final BeanFactory beanFactory;

	public MyAopAspectJPostProcessor(final BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {


		if (PointcutAdvisor.class.isAssignableFrom(bean.getClass())) {
			return bean;
		}
       //获取所有的adviser
		ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
		String[] beanNamesForType = listableBeanFactory.getBeanNamesForType(PointcutAdvisor.class);
		List<PointcutAdvisor> pointcutAdvisors = new ArrayList<>();

		for (String beanNameItem : beanNamesForType) {
			PointcutAdvisor pointcutAdvisor = beanFactory.getBean(beanNameItem, PointcutAdvisor.class);
			pointcutAdvisors.add(pointcutAdvisor);
		}

		return Proxy.newProxyInstance(MyAopAspectJPostProcessor.class.getClassLoader(), bean.getClass().getInterfaces(), new MyInvocationHandler(bean, pointcutAdvisors));

	}
}
