package com.song.customizeaop;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements IUserService, ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override

	public void sayHello() {
		System.out.println("UserServiceImpl.sayHello");
	}

//	@Override
//	public void sayHi() {
//		System.out.println("UserServiceImpl.sayHi");
//		IUserService userService = (IUserService) applicationContext.getBean("userServiceImpl");
//		userService.sayHello();
//	}


	@Override
	@Transactional
	public void sayHi() {
		System.out.println("UserServiceImpl.sayHi");
		IUserService userService = (IUserService) AopContext.currentProxy();
		userService.sayHello();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
