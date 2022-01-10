package com.dharma.algogenerator.service.Algo;

import com.dharma.algogenerator.controller.admin.AsxMetaStockImport;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
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
    @Scheduled(cron = "0 15 7 ? * MON-FRI")
    public void run() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMdd-HH:mm");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
        System.out.println("------------------------ IMPORT START--1------------" + LocalDateTime.now());
        System.out.println("------------------------ IMPORT START- 2-------------" + dateFormat.format(new Date()));


        LocalDateTime start = LocalDateTime.now();
        asxMetaStockImport.importAllData();
        System.out.println("------------------------ IMPORT END--1------------" + LocalDateTime.now());
        System.out.println("------------------------ IMPORT END- 2-------------" + dateFormat.format(new Date()));

//        System.out.println("------------------------ ALGO STARTS--------------" + dateFormat.format(new Date()));
//        Unirest.get("http://ta4j:8080/scheduler/rowan");
//        System.out.println("------------------------ALGO DONE---------------");

        //   http://192.168.0.10:10100//scheduler/rowan


        try {
            System.out.println("------------------------ ALGO STARTS--------------" + dateFormat.format(new Date()));

//            HttpResponse<JsonNode> jsonNodeHttpResponse = Unirest.get("http://ta4j-nodeport:8080/scheduler/rowan").asJson();


            HttpResponse<JsonNode> jsonNodeHttpResponse = Unirest.get("http://localhost:10100/scheduler/rowan").asJson();
            System.out.println("------------------------Scheduler -1-----STATUS---------" + jsonNodeHttpResponse.getStatus());
            System.out.println("------------------------Scheduler ----2---STATUS--text------" + jsonNodeHttpResponse.getStatusText());
            System.out.println("------------------------Scheduler ----2-----------" + jsonNodeHttpResponse.getHeaders());

        } catch (Exception e) {
            System.out.println("------------------------Scheduler -- ERR-------------" + e);

        }


    }

}
