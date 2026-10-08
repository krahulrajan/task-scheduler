package com.example.userservice.scheduler;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ReportJob {

    private static final Logger log = LoggerFactory.getLogger(ReportJob.class);

    @Value("${server.port:8080}")
    private String port;

    // Runs every 10 seconds
    @Scheduled(cron = "*/10 * * * * *")
    @SchedulerLock(
        name = "daily_report_generation_job",
        lockAtLeastFor = "PT5S",  // Hold lock for AT LEAST 5s to prevent immediate re-runs
        lockAtMostFor = "PT9S"    // Release lock after AT MOST 9s if the node crashes
    )
    public void generateReport() {
        log.info(">>> [INSTANCE RUNNING ON PORT {}] Acquired lock! Executing report at: {}", port, Instant.now());
        
        try {
            // Simulate work
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        log.info("<<< [INSTANCE RUNNING ON PORT {}] Finished report job.", port);
    }
}