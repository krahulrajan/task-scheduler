package com.example.userservice.config;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Spring boot scan for @EnableScheduling annotation
 * Once it found, it registers a ScheduledAnnotationBeanPostProcessor
 * This processor inspects all managed beans in the context, 
 * searches for @Scheduled annotations, 
 * and schedules those tasks with an internal thread pool executor (ThreadPoolTaskScheduler)
 * 
 * 
 * @EnableSchedulerLock registers the AOP proxy around this method.
 * Then the AOP intercepts the scheduler method and confirm if the lock can acheived or not.
 * 
 * LockProvider is the bridge between ShedLock's framework logic and your storage layer.
 * new RedisLockProvider(connectionFactory, "shedlock-env") instructs ShedLock 
 * to use Redis connection pooling (Lettuce by default in Spring Boot) 
 * and prefixes all generated lock keys with shedlock-env: to avoid namespace collisions.
 * 
 */

@Configuration
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT30S") // Safety ceiling fallback
public class SchedulerLockConfig {

    @Bean
    public LockProvider lockProvider(RedisConnectionFactory connectionFactory) {
        // Tells ShedLock to store distributed locks as keys inside Redis
        return new RedisLockProvider(connectionFactory, "shedlock-env");
    }
}