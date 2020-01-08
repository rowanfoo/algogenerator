package com.dharma.algogenerator.service.Algo;

import com.dharma.algogenerator.controller.admin.AsxMetaStockImport;
import com.mashape.unirest.http.Unirest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.TimeZone;

@Component
public class AlgoSchedule {
    @Autowired
    AsxMetaStockImport asxMetaStockImport;

    @Scheduled(cron = "0 7 05 ? * MON-FRI", zone = "GMT-8")
    public void run() {

        LocalDateTime start = LocalDateTime.now();
        asxMetaStockImport.importAllData();


        SimpleDateFormat dateFormat = new SimpleDateFormat("hhhhMMddHHmm");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
        System.out.println("------------------------ IMPORT ENDS--------------" + dateFormat.format(LocalDateTime.now()));

        System.out.println("------------------------ IMPORT STARTS--------------" + dateFormat.format(start));
        Unirest.get("http://algo:8080/reset");
        System.out.println("------------------------ Uni call-------------");


    }

}
