package com.tnd.schedule;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

//@RefreshScope
//@Component
@Slf4j
public class ScheduleTask {

    @Value("${cron.expression}")
    private String cron;

    public String getCron() {
        return cron;
    }

    public void doTask() throws InterruptedException {

        log.info("START JOB....................");
//        Thread.sleep(60000);
        log.info("FINISH JOB!");

    }

    /** schedule will not refresh, inspire of have @RefreshScope **/
//    @Scheduled(cron = "${cron.exp}")
//    public void runJob() {
//        System.out.println(new Date() + "@Scheduled configured task doing ...");
//    }

}
