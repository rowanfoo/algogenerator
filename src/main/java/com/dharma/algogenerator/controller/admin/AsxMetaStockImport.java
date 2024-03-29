package com.dharma.algogenerator.controller.admin;


import com.dharma.algogenerator.data.entity.CoreData;
import com.dharma.algogenerator.data.entity.Fundamental;
import com.dharma.algogenerator.data.repo.DataRepo;
import com.dharma.algogenerator.data.repo.FundamentalRepo;
import com.dharma.algogenerator.dto.RunningStatus;
import com.dharma.algogenerator.service.Algo.AlgoAdminDaily;
import com.dharma.algogenerator.service.admin.CalcAverage;
import com.dharma.algogenerator.service.admin.CalcRSI;
import com.dharma.algogenerator.util.Notification;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class AsxMetaStockImport {

    @Autowired
    ArrayList<String> allasxcodes;

    @Autowired
    DataRepo datarepo;
    @Autowired
    AlgoAdminDaily algo;
    @Autowired
    CalcAverage calcAverage;
    @Autowired
    CalcRSI calcRSI;


    @Autowired
    RunningStatus runningStatus;

    @Autowired
    Notification notification;
    @Autowired
    FundamentalRepo fundamentalRepo;

    //http://192.168.0.10:10700/run
    @RequestMapping(value = "/run", method = RequestMethod.GET)
    public void submit() {
        System.out.println("----------------------------WEB TRIGGER RUN");
        importAllData();


    }


//    @RequestMapping(value = "/jib", method = RequestMethod.GET)
//    public void hello() {
//        System.out.println("----------------------------WEB TRIGGER RUN  hello");
////        String s = Unirest.get("http://user:8090/hello").toString();
//        String s = null;
//        try {
////            s = Unirest.get("http://rowanfoo.ddns.net:10700/hello").asString().getBody();
//
//        } catch (UnirestException e) {
//            e.printStackTrace();
//        }
////
////
//        System.out.println("----------------------------WEB TRIGGER RUN  hello :" + s);
//
//
//    }


    @RequestMapping(value = "/calc", method = RequestMethod.GET)
    public void calc() {
        System.out.println("----------------------------WEB TRIGGER RUN  CALC");
        System.out.println("----RUN  CALC data AVERAGE --:");
        calcAverage.run();
        System.out.println("----RUN  AVERAGE DONE   --:");
        calcRSI.run();
        System.out.println("----RUN RSI DONE--:");


    }

    @RequestMapping(value = "/algo", method = RequestMethod.GET)
    public void algo() {
        System.out.println("----------------------------WEB TRIGGER RUN  ALGO");
        System.out.println("----RUN  ALGO --:");
        try {
            algo.executeAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("----RUN  ALGO DONE   --:");


    }

    private void insertdata(String code, LocalDate date) throws Exception {

        final String uri = "https://www.asx.com.au/asx/1/share/" + code;
        log.info("-----------------INSERT -------------- " + code);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        System.out.println("----ASX import RUN !!!insertdata!!  --:" + uri);
        CoreData node = null;
        node = mapper.readValue(new URL(uri), CoreData.class);
        node.setDate(date);
        System.out.println("----data done --:" + node);
        datarepo.save(node);


        Fundamental data = mapper.readValue(new URL(uri), Fundamental.class);
        fundamentalRepo.save(data);


    }

    //@Scheduled(cron = "0 15 15 ? * MON-FRI")
    public void importAllData() {

        try {
            runningStatus.setImportstatus("");
            runningStatus.setRsistatus("");
            runningStatus.setAlgostatus("");
            LocalDate date = LocalDate.now();
//FORCE TO DO THIS check , CORE_DATA  last_trade_date issue
            System.out.println("----ASX import RUN !!!!!  --:");
            log.info("-----------------IMPORT START-------------- ");
            //allasxcodes.forEach((a)-> System.out.println("----codes--:"+a));
            runningStatus.setImportstatus("running");
            allasxcodes.stream()
                       .forEach((a) -> {
                           try {
                               TimeUnit.SECONDS.sleep(20);
                               //System.out.println("----ASX import data  --:"+a);
                               insertdata(a, date);
                           } catch (Exception e) {
                               System.out.println("----ASX import data  --:" + e);
                           }

                       });
            System.out.println("----ASX import data   ALL DONE --:");
            System.out.println("----ASX import data   BYE--:");
            datarepo.flush();

            runningStatus.setImportstatus("completed");
            System.out.println("----RUN  CALC data  --:");

//            runningStatus.setAveragestatus("running");
//            calcAverage.run();
//            runningStatus.setAveragestatus("completed");
//
//
//            System.out.println("----RUN  AVERAGE DONE   --:");
//            runningStatus.setRsistatus("running");
//            calcRSI.run();
//
//            runningStatus.setRsistatus("completed");
//            System.out.println("----RUN ALGO --:");
//            runningStatus.setAlgostatus("running");
//            log.info("-----------------IMPORT DONE-------------- ");
//            //           notification.sendMsg("Algo" , " Data import ok");
//            log.info("-----------------RUN ALGO -------------- ");
//            algo.executeAll();
            log.info("-----------------ALL DONE  THANKS ,,, ZZZZZzzz -------------- ");

            System.out.println("----ASX Algo    ALL DONE --:");

            notification.sendMsg("Algo", " Algo run ok");

            runningStatus.setAlgostatus("completed");
            System.out.println("----REST --:");

            Unirest.get("http://192.168.0.10:10100/scheduler/rowan");


            System.out.println("----REST DONE--:");


        } catch (Exception e) {
            System.out.println("---Errr --:" + e);
            log.info("-----------------OUCH  Errrr !!  -------------- " + e);
            notification.sendMsg("Algo", " !! Err " + e);

        }


    }


    @RequestMapping(value = "/enddayvol", method = RequestMethod.GET)
    public String enddayvol() {


        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            algo.enddayvolgreaterfourty();
        });

        return "done";

    }

    @RequestMapping(value = "/scheduler", method = RequestMethod.GET)
    public void scheduler() {
        System.out.println("------------------------ Scheduler run now-------------");
//        GetRequest abc = Unirest.get("http://ta4j:8080/scheduler/rowan");
//        Unirest.get("http://ta4j:8080/scheduler/rowan").asJson();

        try {


//            HttpResponse<JsonNode> jsonNodeHttpResponse = Unirest.get("http://ta4j:8080/scheduler/rowan").asJson();
            HttpResponse<JsonNode> jsonNodeHttpResponse = Unirest.get("http://ta4j-nodeport:8080/scheduler/rowan").asJson();

            System.out.println("------------------------Scheduler -1--------------" + jsonNodeHttpResponse.getStatus());
            System.out.println("------------------------Scheduler ----2-----------" + jsonNodeHttpResponse.getStatusText());
            System.out.println("------------------------Scheduler ----2-----------" + jsonNodeHttpResponse.getHeaders());

        } catch (Exception e) {
            System.out.println("------------------------Scheduler -- ERR-------------" + e);

        }


        System.out.println("------------------------Scheduler DONE---------------");

    }

}
