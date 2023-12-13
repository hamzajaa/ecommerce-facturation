package com.ecommerce.facturation.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync(proxyTargetClass = true)
// when add this annotation then tell spring framework to run your tasking background
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4); // initialize 10 thread
        executor.setMaxPoolSize(6);
        executor.setQueueCapacity(100); // the maximum number of task can be hold in the Q if the Q is full and additional task are submitted
        executor.setThreadNamePrefix("userThread-");
        executor.initialize();
        return executor;
    }

}

