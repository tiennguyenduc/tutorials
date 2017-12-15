package com.tnd.schedule;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RefreshScope
@Component
public class ScheduleTask {

    @Value("${cron.exp}")
    private String cron;

    public String getCron() {
        return cron;
    }

    public void doTask() {
        System.out.println(new Date() + "Implements SchedulingConfigurer task doing ...");
    }

    /** schedule will not refresh, inspire of have @RefreshScope **/
    @Scheduled(cron = "${cron.exp}")
    public void runJob() {
        System.out.println(new Date() + "@Scheduled configured task doing ...");
    }

}
