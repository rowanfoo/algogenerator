package com.dharma.algogenerator.util;

import com.mashape.unirest.http.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Notification {


    @Value("${notification.server}")
    String server;

    public void sendMsg(String header, String msg) {
//        RestTemplate restTemplate = new RestTemplate();
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(server);
//
//
//        Map<String, String> uriParams = new HashMap<String, String>();
//        uriParams.put("header", header);
//        uriParams.put("message", msg);
//        uriParams .forEach((a, b) -> {
//            builder.queryParam(a, b);
//        });
//
//        System.out.println("======sendMSG====="  + builder.build().toUri() );
//        restTemplate.getForEntity(   builder.build().toUri()   , String.class  );

        System.out.println("------------------call ALGO RESET-----------");
        String s = Unirest.get("http://algo:8080/reset").toString();
        System.out.println("------------------call ALGO RESET--DONE---------" + s);


    }

}
