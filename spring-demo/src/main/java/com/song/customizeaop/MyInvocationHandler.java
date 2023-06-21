package com.song.customizeaop;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.PointcutAdvisor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MyInvocationHandler implements InvocationHandler {

	private final Object target;

	private final List<PointcutAdvisor> advisorList;


	private List<PointcutAdvisor> methodBeforeAdvisorList = new ArrayList<>();

	private List<PointcutAdvisor> afterReturningAdvisorList = new ArrayList<>();

	public MyInvocationHandler(Object bean, List<PointcutAdvisor> advisorList) {
		this.target = bean;
		this.advisorList = advisorList;
	}

	public void setAdviceList(List<PointcutAdvisor> pointcutAdvisors) {
		for (PointcutAdvisor pointcutAdvisor : pointcutAdvisors) {
			if (pointcutAdvisor.getAdvice() instanceof MethodBeforeAdvice) {
				methodBeforeAdvisorList.add(pointcutAdvisor);
			}
			if (pointcutAdvisor.getAdvice() instanceof AfterReturningAdvice) {
				afterReturningAdvisorList.add(pointcutAdvisor);
			}
		}
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		for (PointcutAdvisor methodBeforePointcutAdvisor : methodBeforeAdvisorList) {
			if (methodBeforePointcutAdvisor.getPointcut().getClassFilter().matches(target.getClass())) {
				if (methodBeforePointcutAdvisor.getPointcut().getMethodMatcher().matches(method, target.getClass())) {
					MethodBeforeAdvice beforeAdvice = (MethodBeforeAdvice) methodBeforePointcutAdvisor.getAdvice();
					beforeAdvice.before(method, args, target);
				}
			}
		}

		Object ret = method.invoke(target, args);

		for (PointcutAdvisor afterReturningAdvisor : afterReturningAdvisorList) {
			if (afterReturningAdvisor.getPointcut().getClassFilter().matches(target.getClass())) {
				if (afterReturningAdvisor.getPointcut().getMethodMatcher().matches(method, target.getClass())) {
					AfterReturningAdvice afterReturningAdvice = (AfterReturningAdvice) afterReturningAdvisor.getAdvice();
					afterReturningAdvice.afterReturning(ret, method, args, target);
				}
			}
		}
		return ret;
	}
}
