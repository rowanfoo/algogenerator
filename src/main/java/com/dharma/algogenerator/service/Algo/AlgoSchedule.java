package com.dharma.algogenerator.service.Algo;

import com.dharma.algogenerator.controller.admin.AsxMetaStockImport;
import com.mashape.unirest.http.Unirest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

@Component
public class AlgoSchedule {
    @Autowired
    AsxMetaStockImport asxMetaStockImport;

    //    @Scheduled(cron = "0 07 05 ? * MON-FRI", zone = "GMT-8")
//    @Scheduled(cron = "0 07 05 ? * MON-FRI")
    @Scheduled(cron = "0 30 14 ? * MON-FRI")
    public void run() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMdd-HH:mm");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
        System.out.println("------------------------ IMPORT ENDS--1------------" + LocalDateTime.now());
        System.out.println("------------------------ IMPORT ENDS- 2-------------" + dateFormat.format(new Date()));


        LocalDateTime start = LocalDateTime.now();
        asxMetaStockImport.importAllData();


        System.out.println("------------------------ ALGO STARTS--------------" + dateFormat.format(new Date()));
        Unirest.get("http://ta4j:8080/reset");
        System.out.println("------------------------ALGO DONE---------------");


    }

}
