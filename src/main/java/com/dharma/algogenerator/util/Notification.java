package com.dharma.algogenerator.util;

import com.dharma.algogenerator.data.entity.CoreStock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
@Component
public class Notification {


    @Value("${notification.server}") String server;

    public void sendMsg(String header , String msg){
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(server);


        Map<String, String> uriParams = new HashMap<String, String>();
        uriParams.put("header", header);
        uriParams.put("message", msg);
        uriParams .forEach((a, b) -> {
            builder.queryParam(a, b);
        });

        System.out.println("======sendMSG====="  + builder.build().toUri() );
        restTemplate.getForEntity(   builder.build().toUri()   , String.class  );









    }

}
