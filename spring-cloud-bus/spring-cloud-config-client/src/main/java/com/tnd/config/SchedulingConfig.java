package com.tnd.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import com.tnd.schedule.ScheduleTask;

@Configuration
//@RefreshScope
@EnableScheduling
public class SchedulingConfig implements SchedulingConfigurer {

    @Autowired
    ApplicationContext context;

//    @Autowired
//    ScheduleTask task;

    /** don't working because @RefreshScope effect to Bean **/
//    @Value("${cron.exp}")
//    private String cron;
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        ScheduleTask task = context.getBean(ScheduleTask.class);

        taskRegistrar.addTriggerTask(
                () -> task.doTask(),
                triggerContext -> {
//                        String expression = task.getCron();
//                        CronTrigger trigger = new CronTrigger(expression);
//                        Date nextTriggerDate = trigger.nextExecutionTime(triggerContext);
                    return nextExecutionTime(triggerContext, task.getCron());
                }
        );

    }

    private Date nextExecutionTime(TriggerContext triggerContext, String expression) {
        CronTrigger trigger = new CronTrigger(expression);
        Date nextTriggerDate = trigger.nextExecutionTime(triggerContext);
        return nextTriggerDate;
    }

}
