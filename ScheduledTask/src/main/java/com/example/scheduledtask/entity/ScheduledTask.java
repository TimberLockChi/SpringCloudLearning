package com.example.scheduledtask.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;


@Component
public class ScheduledTask {


    private static Logger logger = LoggerFactory.getLogger(ScheduledTask.class);
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


    @Scheduled(fixedRate = 5000)
    public void getLogger(){
        logger.info("Time is {}",dateFormat.format(new Date()));
    }




}
