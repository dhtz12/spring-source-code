package com.song.customizeaop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Component
@ComponentScan("com.song")
@MyEnableAspectJAutoProxy
@EnableTransactionManagement
public class AppConfig {
}
