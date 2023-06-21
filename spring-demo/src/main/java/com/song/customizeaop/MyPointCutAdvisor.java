package com.song.customizeaop;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;

/**
 * 组装切点还有额外功能。
 */
public class MyPointCutAdvisor implements PointcutAdvisor {
	@Override
	public Pointcut getPointcut() {
		return new MyPointCut();
	}

	@Override
	public Advice getAdvice() {
		return new MyAfter();
	}

	@Override
	public boolean isPerInstance() {
		return false;
	}
}
