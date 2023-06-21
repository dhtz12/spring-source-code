package com.song.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Component
@ComponentScan("com.song")
//将当前类型的代理对象放入ThreadLocal中
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableTransactionManagement
public class AppConfig {
}
