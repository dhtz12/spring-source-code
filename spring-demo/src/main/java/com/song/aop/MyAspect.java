package com.song.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAspect {
	@Before("execution(* com.song.aop.UserServiceImpl.*(..))")
	public void myBefore(){
		System.out.println("MyAspect.myBefore");
	}
	@After("execution(* com.song.aop.UserServiceImpl.*(..))")
	public void myAfter(){
		System.out.println("MyAspect.myAfter");
	}
}
