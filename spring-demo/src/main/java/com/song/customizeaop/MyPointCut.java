package com.song.customizeaop;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;

import java.lang.reflect.Method;

public class MyPointCut implements Pointcut {
	@Override
	public ClassFilter getClassFilter() {
		return clazz -> {
			if (UserServiceImpl.class.isAssignableFrom(clazz)) {
				return true;
			} else {
				return false;
			}
		};
	}

	@Override
	public MethodMatcher getMethodMatcher() {
		return new MethodMatcher() {
			@Override
			public boolean matches(Method method, Class<?> targetClass) {
				if ("sayHello".equals(method.getName())) {
					return true;
				}
				return false;
			}

			@Override
			public boolean isRuntime() {
				return true;
			}

			@Override
			public boolean matches(Method method, Class<?> targetClass, Object... args) {
				return matches(method, targetClass);
			}
		};
	}
}
