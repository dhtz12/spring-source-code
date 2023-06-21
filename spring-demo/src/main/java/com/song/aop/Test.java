package com.song.aop;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Test {
	public static void main(String[] args) {
//		aspectJDKProxy();
//		aspectCGLIBProxy();
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		IUserService userService = applicationContext.getBean(IUserService.class);
		userService.sayHi();
	}

	@TestAnnotation("abc")
	public void test1() {
		System.out.println("true = " + true);
	}


	public static void aspectJDKProxy() {
		IUserService userService = new UserServiceImpl();
		IUserService instance = (IUserService) Proxy.newProxyInstance(Test.class.getClassLoader(), new Class[]{IUserService.class}, (proxy, method, args) -> {
			System.out.println("Test.aspectProxy.before");
			Object invoke = method.invoke(userService, args);
			System.out.println("Test.aspectProxy.after");
			return invoke;
		});
		instance.sayHello();
	}


	public static void aspectCGLIBProxy() {
		IUserService userService = new UserServiceImpl();
		Enhancer enhancer = new Enhancer();
		enhancer.setClassLoader(Test.class.getClassLoader());
		enhancer.setSuperclass(IUserService.class);
		enhancer.setCallback((InvocationHandler) (o, method, args) -> {
			System.out.println("Test.cglib.invoke.before");
			Object invoke = method.invoke(userService, args);
			System.out.println("Test.cglib.invoke.after");
			return invoke;
		});
		IUserService userService1 = (IUserService) enhancer.create();
		userService1.sayHello();

	}


}
